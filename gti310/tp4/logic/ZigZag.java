package gti310.tp4.logic;

import gti310.tp4.model.ImageData;

import java.util.ArrayList;

/**
 * Created by César Jeanroy on 2016-03-24.
 */
public class ZigZag {

    /**
     * Method that convert the list of matrix of the ImageData object into a list of ZigZag vectors
     * @param image the ImageData object
     */
    public static void process(ImageData image){
        ArrayList<int[]> zigzagVectors = new ArrayList<int[]>();

        for (int i = 0; i < image.getNbMatrices(); i++) {
            zigzagVectors.add(convertToZigzag(image.getMatrix(i)));
        }

        image.setZigzagVectors(zigzagVectors);
    }

    /**
     * Method that convert the list of matrix of the ImageData object into the state they were before to be converted into ZigZag format
     * @param image the ImageData object
     */
    public static void reverse(ImageData image){

        ArrayList<int[][]> matrices = new ArrayList<int[][]>();

        for (int i = 0; i < image.getZigzagVectors().size(); i++) {
            matrices.add(convertFromZigzag(image.getZigzagVectors().get(i)));
        }

        image.setMatricesList(matrices);
    }

    /**
     * Method that convert a matrix into a vector using the zigzag method
     * O(N^2) N: scare matrix size
     * @param matrix The matrix we want to convert
     * @return  int[] The vector that contain the matrix extracted with the zigzag method
     */
    public static int[] convertToZigzag(int[][] matrix){
        int width = matrix.length;
        int height = matrix[0].length;
        int[] vector = new int[width*height];
        int index = 0;

        int mvtI = -1;//1; //Variable use to know if the next iteration, i has to move to the right (mvtI=1) or to the left (mvtI=-1)
        int mvtJ = -1; //Variable use to know if the next iteration, j has to move up (mvtJ=-1) or down (mvtI=1)

        //vector[index++] = matrix[0][0];
        int i=0; //index of the matrix row
        int j=0; //index of the matrix column

        while(index<vector.length) { //While we haven't reach the bottom right cell of the matrix

            vector[index++]=matrix[i][j];

            if((i==0 && mvtI==-1)||(i==height-1 && mvtI==1)){

                if(i==height-1){
                    j++;
                    mvtI = -1;
                    mvtJ = +1;
                }
                else{
                    j++;
                    mvtI = 1;
                    mvtJ = -1;
                }
            }
            else if((j==0 && mvtJ==-1)|| (j==width-1 && mvtJ==1)){

                if(j==width-1){
                    i++;
                    mvtI = +1;
                    mvtJ = -1;
                }
                else{
                    i++;
                    mvtI = -1;
                    mvtJ = +1;
                }
            }
            else{
                i+=mvtI;
                j+=mvtJ;
            }
        }

        return vector;

    }

    /**
     * Method that convert a zigzag vector into matrix
     * O(N^2) N: scare matrix size
     * @param vector The vector we want to convert
     * @return  int[][] The matrix that contain the vector data
     */
    public static int[][] convertFromZigzag(int[] vector){

        int length = vector.length;
        int height = (int) Math.sqrt(length);
        int width = (int) Math.sqrt(length);
        int[][] matrix = new int[height][width];
        int index = 0;

        int mvtI = -1; //Variable use to know if the next iteration, i has to move to the right (mvtI=1) or to the left (mvtI=-1)
        int mvtJ = -1; //Variable use to know if the next iteration, j has to move up (mvtJ=-1) or down (mvtI=1)

        //matrix[0][0] = vector[index++];
        int i=0; //index of the matrix row
        int j=0; //index of the matrix column

        while(index<vector.length) { //While we haven't reach the bottom right cell of the matrix

            matrix[i][j]=vector[index++];

            if((i==0 && mvtI==-1)||(i==height-1 && mvtI==1)){

                if(i==height-1){
                    j++;
                    mvtI = -1;
                    mvtJ = +1;
                }
                else{
                    j++;
                    mvtI = 1;
                    mvtJ = -1;
                }
            }
            else if((j==0 && mvtJ==-1)|| (j==width-1 && mvtJ==1)){

                if(j==width-1){
                    i++;
                    mvtI = +1;
                    mvtJ = -1;
                }
                else{
                    i++;
                    mvtI = -1;
                    mvtJ = +1;
                }
            }
            else{
                i+=mvtI;
                j+=mvtJ;
            }


        }

        return matrix;

    }
}
