package gti310.tp4.logic;

import gti310.tp4.model.ImageData;

/**
 * Created by César Jeanroy on 2016-03-31.
 * Class to calculate the DCT of a 8x8 matrix
 */
public class DCT {

    private static final int MATRIX_SIZE = 8;

    /**
     * Method to calculate the DCT of the given image
     * @param image
     */
    public static void processDCT(ImageData image){

        int[][] tmpMatrix = null;

        //for each matrix of the image, we calculate the DCT
        for (int i = 0; i < image.getNbMatrices() ; i++) {

            tmpMatrix = image.getMatrix(i);

            //we make sure that the matrix has the good size
            if(tmpMatrix.length != MATRIX_SIZE && tmpMatrix[0].length != MATRIX_SIZE)
                break;
            //We convert the matrix
            int[][] convertedMatrix = convertToDCTMatrix(tmpMatrix);
            //We set the converted matrix in the image
            image.setMatrix(i,convertedMatrix);
        }
    }

    /**
     * Method to calculate the IDCT of the given image
     * @param image
     */
    public static void processIDCT(ImageData image){
        int[][] tmpMatrix = null;

        //for each matrix of the image, we calculate the DCI
        for (int i = 0; i < image.getNbMatrices() ; i++) {

            tmpMatrix = image.getMatrix(i);

            //we make sure that the matrix has the good size
            if(tmpMatrix.length != MATRIX_SIZE && tmpMatrix[0].length != MATRIX_SIZE)
                break;
            //We convert the matrix
            int[][] convertedMatrix = convertToIDCTMatrix(tmpMatrix);
            //We set the converted matrix in the image
            image.setMatrix(i,convertedMatrix);
        }
    }

    /**
     * Method that return the corresponding DCT matrix
     * @param matrix the matrix we want to convert
     * @return The matrix converted
     */
    private static int[][] convertToDCTMatrix(int[][] matrix){
        int [][] dctMatrix = new int[8][8];

        for (int u = 0; u < MATRIX_SIZE; u++) {
            for (int v = 0; v < MATRIX_SIZE; v++) {
                dctMatrix[u][v] = (int) DCTFormula(u,v,matrix);
            }
        }

        return dctMatrix;
    }

    /**
     * Method that return the corresponding IDCT matrix
     * @param matrix the matrix we want to convert
     * @return The matrix converted
     */
    private static int[][] convertToIDCTMatrix(int[][] matrix){
        int [][] dctMatrix = new int[8][8];

        for (int u = 0; u < MATRIX_SIZE; u++) {
            for (int v = 0; v < MATRIX_SIZE; v++) {
                dctMatrix[u][v] = (int) IDCTFormula(u,v,matrix);
            }
        }

        return dctMatrix;
    }

    private static double DCTFormula(int u,int v,int [][] matrix){
        double sum = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                sum += (cosFormula(i,u)*cosFormula(j,v)*matrix[i][j]);
            }
        }

        return ((C(u)*C(v))/4)*sum;
    }

    private static double IDCTFormula(int u,int v,int [][] matrix){
        double sum = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                sum += ((C(u)*C(v))/4)*(cosFormula(i,u)*cosFormula(j,v)*matrix[i][j]);
            }
        }

        return sum;
    }

    private static double C(int i){
        return i == 0 ? (1/Math.sqrt(2)) : 1;
    }

    private static double cosFormula(int iteration,int matrixPosition){
        return Math.cos((((2*iteration)+1)*matrixPosition*Math.PI)/16);
    }


}
