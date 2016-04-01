package gti310.tp4.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by César Jeanroy on 2016-03-24.
 * Class used to store the matrix that contains the image being converted.
 * This class provide methods to manipulate the image through 8x8 matrices instead of the 3 dimensions int array
 */
public class ImageData {

    private final static int MATRIX_SIZE = 8;
    //Arraylist of 8x8 matrices
    private List<int[][]> matricesList = null;

    //Attributes use to rebuild the original matrix from the matricesList
    private int nbColor;
    private int nbRow;
    private int nbColumn;


    /**
     * Constructor that takes a 3 dimensions int array
     * @param image the image
     */
    public ImageData(int[][][] image){

        this.matricesList = new ArrayList<int[][]>();

        int[][] tmp = new int[MATRIX_SIZE][MATRIX_SIZE];

        nbColor = image.length;
        nbRow = image[0].length;
        nbColumn = image[0][0].length;

        //for each color (RGB or YUV)
        for (int c = 0; c < nbColor; c++) {
            for (int row = 0; row < nbRow ; row+=MATRIX_SIZE) {
                for (int col = 0; col < image[c][row].length ; col+=MATRIX_SIZE) {

                    //We set the value into the temp matrix
                    for (int i = 0; i < MATRIX_SIZE; i++) {
                        for (int j = 0; j < MATRIX_SIZE ; j++) {
                            tmp[i][j]=image[c][row+i][col+j];
                        }
                    }

                    //we add the sub Matrix in the list of sub matrices
                    matricesList.add(tmp);
                    tmp = new int[MATRIX_SIZE][MATRIX_SIZE];
                }
            }

        }
    }

    /**
     * Method that returns the matrix stored at the given index
     * @param index Index of the matrix
     * @return the matrix
     * @throws IndexOutOfBoundsException
     */
    public int[][] getMatrix(int index) throws IndexOutOfBoundsException{
        if(index<matricesList.size()){
            return matricesList.get(index);
        }

        throw new IndexOutOfBoundsException();
    }

    /**
     * Method that returns the number of matices stored inside the list of matrices
     * @return nb matrices
     */
    public int getNbMatrices(){
        return matricesList.size();
    }

    /**
     * Method that set the given martix into the list of matrices at the given index.
     * @param index     the index where it needs to be inserted
     * @param matrix    the matrix to be inserted
     * @throws IndexOutOfBoundsException
     */
    public void setMatrix(int index,int[][] matrix){
        if(index<matricesList.size()){
            matricesList.set(index,matrix);
            return;
        }

        throw new IndexOutOfBoundsException();
    }

    /**
     * Method that returns the sub matrices that contains Y values
     * @return ArrayList<int[][]> Y values in matrices
     */
    public ArrayList<int[][]> getYMatrices(){
        ArrayList<int[][]> Y_Matrices = new ArrayList<int[][]>();

        for (int i = 0; i < matricesList.size()/3; i++) {
            Y_Matrices.add(matricesList.get(i));
        }

        return Y_Matrices;
    }

    /**
     * Method that returns the sub matrices that contains Cb values
     * @return ArrayList<int[][]> Cb values in matrices
     */
    public ArrayList<int[][]> getCbMatrices(){
        ArrayList<int[][]> Cb_Matrices = new ArrayList<int[][]>();

        for (int i = (matricesList.size()/3); i < 2*(matricesList.size()/3); i++) {
            Cb_Matrices.add(matricesList.get(i));
        }

        return Cb_Matrices;
    }

    /**
     * Method that returns the sub matrices that contains Cr values
     * @return ArrayList<int[][]> Cr values in matrices
     */
    public ArrayList<int[][]> getCrMatrices(){
        ArrayList<int[][]> Cr_Matrices = new ArrayList<int[][]>();
        for (int i = (2*(matricesList.size()/3)); i < matricesList.size(); i++) {
            Cr_Matrices.add(matricesList.get(i));
        }
        return Cr_Matrices;
    }

    public List<int[][]> getMatricesList() {
        return matricesList;
    }

    /**
     * Method that convert the Matrices list into a 3 dimensions Matrix.
     */
    public int[][][] getImageMatrix(){

        int[][][] matrix = new int[nbColor][nbRow][nbColumn];

        //index of the current matrix in the matricesList
        int index = 0;
        //tmp matrix to manipulate the current matrix extracted from the matricesList
        int[][] tmp =null;

        //for each color (RGB or YUV)
        for (int c = 0; c < nbColor; c++) {
            for (int row = 0; row < nbRow; row+=MATRIX_SIZE) {
                for (int col = 0; col < nbColumn ; col+=MATRIX_SIZE) {
                    tmp = getMatrix(index++);
                    //We set the value into the temp matrix
                    for (int i = 0; i < MATRIX_SIZE; i++) {
                        for (int j = 0; j < MATRIX_SIZE ; j++) {
                            matrix[c][row+i][col+j] = tmp[i][j];
                        }
                    }
                }
            }

        }
        return matrix;
    }
}
