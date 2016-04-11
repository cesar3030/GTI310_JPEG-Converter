package gti310.tp4.logic;

import gti310.tp4.Main;
import gti310.tp4.model.ImageData;

/**
 * Created by César Jeanroy on 2016-03-31.
 * Class to calculate the DCT of a  Main.BLOCK_SIZEx Main.BLOCK_SIZE matrix
 */
public class DCT {

    private static double[][] COS = null;

    /**
     * Method to calculate the DCT of the given image
     * O(N^6) (= O(N^6 * k) C: nb matrices in the ImageData object)
     * @param image
     */
    public static void process(ImageData image){

        int[][] tmpMatrix = null;

        //for each matrix of the image, we calculate the DCT
        for (int i = 0; i < image.getNbMatrices() ; i++) {

            tmpMatrix = image.getMatrix(i);

            //we make sure that the matrix has the good size
            if(tmpMatrix.length != Main.BLOCK_SIZE && tmpMatrix[0].length != Main.BLOCK_SIZE)
                break;
            //We convert the matrix
            int[][] convertedMatrix = convertToDCTMatrix(tmpMatrix);
            //We set the converted matrix in the image
            image.setMatrix(i,convertedMatrix);
        }
    }

    /**
     * Method to calculate the IDCT of the given image
     * O(N^6) (= O(N^6 * k) C: nb matrices in the ImageData object)
     * @param image
     */
    public static void reverse(ImageData image){
        int[][] tmpMatrix = null;

        //for each matrix of the image, we calculate the DCI
        for (int i = 0; i < image.getNbMatrices() ; i++) {

            tmpMatrix = image.getMatrix(i);

            //we make sure that the matrix has the good size
            if(tmpMatrix.length != Main.BLOCK_SIZE && tmpMatrix[0].length != Main.BLOCK_SIZE)
                break;
            //We convert the matrix
            int[][] convertedMatrix = convertToIDCTMatrix(tmpMatrix);
            //We set the converted matrix in the image
            image.setMatrix(i,convertedMatrix);
        }
    }

    /**
     * Method that return the corresponding DCT matrix
     * O(N^6)
     * @param matrix the matrix we want to convert
     * @return The matrix converted
     */
    private static int[][] convertToDCTMatrix(int[][] matrix){
        int [][] dctMatrix = new int[Main.BLOCK_SIZE][Main.BLOCK_SIZE];

        for (int u = 0; u < Main.BLOCK_SIZE; u++) {
            for (int v = 0; v < Main.BLOCK_SIZE; v++) {
                dctMatrix[u][v] = DCTFormula(u,v,matrix);
            }
        }

        return dctMatrix;
    }

    /**
     * Method that return the corresponding IDCT matrix
     * O(N^6)
     * @param matrix the matrix we want to convert
     * @return The matrix converted
     */
    private static int[][] convertToIDCTMatrix(int[][] matrix){
        int [][] dctMatrix = new int[Main.BLOCK_SIZE][Main.BLOCK_SIZE];

        for (int u = 0; u < Main.BLOCK_SIZE; u++) {
            for (int v = 0; v < Main.BLOCK_SIZE; v++) {
                dctMatrix[u][v] = IDCTFormula(u,v,matrix);
            }
        }

        return dctMatrix;
    }

    /**
     * DCT Formula
     * O(N^4)
     * @param u
     * @param v
     * @param matrix
     * @return
     */
    private static int DCTFormula(int u,int v,int [][] matrix){
        double sum = 0.0;
        for (int i = 0; i < Main.BLOCK_SIZE; i++) {
            for (int j = 0; j < Main.BLOCK_SIZE; j++) {
                sum += (cosFormula(i,u)*cosFormula(j,v)*matrix[i][j]);
            }
        }

        return (int)Math.round((C(u)*C(v)/4)*sum);
    }

    /**
     * IDCT Formula
     * O(N^4)
     * @param i
     * @param j
     * @param matrix
     * @return
     */
    private static int IDCTFormula(int i,int j,int [][] matrix){
        double sum = 0.0;
        for (int u = 0; u < Main.BLOCK_SIZE; u++) {
            for (int v = 0; v < Main.BLOCK_SIZE; v++) {
                sum += (C(u)*C(v)/4)*(cosFormula(i,u)*cosFormula(j,v)*matrix[u][v]);
            }
        }

        return (int)Math.round(sum);
    }

    /**
     * Method that return the C value corresponding
     * O(1)
     * @param i
     * @return
     */
    private static double C(int i){
        return i == 0 ? (1/Math.sqrt(2)) : 1.0;
    }

    /**
     * O(N^2)
     * @param iteration
     * @param matrixPosition
     * @return
     */
    private static double cosFormula(int iteration,int matrixPosition){
        if(COS==null){
            COS = new double[8][8];
            for (int i=0;i<8;i++){
                for (int j=0;j<8;j++){
                    COS[i][j]=Math.cos(((2*i+1)*j*Math.PI/16));
                }
            }
        }
        return COS[iteration][matrixPosition];
    }


}
