package gti310.tp4.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by César Jeanroy on 2016-03-24.
 */
public class ImageDataTest {

    int[][][] image;
    @Before
    public void setUp(){
        image = new int[3][24][24];
        int number = 1;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                for (int k = 0; k < image[i][j].length; k++) {
                    image[i][j][k] = number++;
                }
            }
        }
    }

    @Test
    public void testGetNbMatrices() throws Exception {
        ImageData imagedata = new ImageData(image);
        assertEquals(3*3*3,imagedata.getNbMatrices());
    }

    @Test
    public void testGetMatrix() throws Exception {
        ImageData imagedata = new ImageData(image);
        int[][] expected = imagedata.getMatrix(0);
        int number = 1;
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
               assertEquals(number++,expected[j][k]);
            }
            number+=(8*2);
        }
    }

    @Test
    public void testSetMatrix() throws Exception {

        ImageData imagedata = new ImageData(image);

        int[][] newMatrix = new int[8][8];

        int number = 45;
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                newMatrix[j][k]=number;
            }
            number+=(8*2);
        }

        imagedata.setMatrix(4,newMatrix);

        int[][] expected = imagedata.getMatrix(4);

        number = 45;
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                assertEquals(number,expected[j][k]);
            }
            number+=(8*2);
        }

    }

    @Test
    public void testGetYMatrices() throws Exception {

        ImageData imageData = new ImageData(image);

        ArrayList<int[][]> YMatrices = imageData.getYMatrices();
        int index = 0;
        for (int row = 0; row < 24; row+=8) {
            for (int col = 0; col < 24 ; col+=8) {
                int[][] matrixTested =  YMatrices.get(index++);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8 ; j++) {
                        assertEquals(image[0][row+i][col+j],matrixTested[i][j]);
                    }
                }
            }
        }
        assertEquals(9,index);
    }

    @Test
    public void testGetCbMatrices() throws Exception {

        ImageData imageData = new ImageData(image);

        ArrayList<int[][]> CbMatrices = imageData.getCbMatrices();
        int index = 0;
        for (int row = 0; row < 24; row+=8) {
            for (int col = 0; col < 24 ; col+=8) {
                int[][] matrixTested =  CbMatrices.get(index++);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8 ; j++) {
                        assertEquals(image[1][row+i][col+j],matrixTested[i][j]);
                    }
                }
            }
        }
        assertEquals(9,index);
    }

    @Test
    public void testGetCrMatrices() throws Exception {

        ImageData imageData = new ImageData(image);

        ArrayList<int[][]> CrMatrices = imageData.getCrMatrices();
        int index = 0;
        for (int row = 0; row < 24; row+=8) {
            for (int col = 0; col < 24 ; col+=8) {
                int[][] matrixTested =  CrMatrices.get(index++);

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8 ; j++) {
                        assertEquals(image[2][row+i][col+j],matrixTested[i][j]);
                    }
                }
            }
        }
        assertEquals(9,index);
    }

    @Test
    public void testGetImageMatrix() throws Exception {
        ImageData imageData = new ImageData(image);
        int[][][] reconvertedImage = imageData.getImageMatrix();
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                for (int k = 0; k < image[i][j].length; k++) {
                    assertEquals(image[i][j][k],reconvertedImage[i][j][k]);
                }
            }
        }

    }
}