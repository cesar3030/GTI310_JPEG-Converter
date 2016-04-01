package gti310.tp4.logic;

import gti310.tp4.model.ImageData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by César Jeanroy on 2016-03-31.
 */
public class DCTTest {

    ImageData image;
    ImageData dctImage;
    int [][][] matrix;
    int[][][] dctMatrix;
    @Before
    public void setup(){
        int[] vectorData = {200, 202, 198, 188, 189, 175, 175, 175,200, 203, 198, 188, 189, 182, 178, 175,203, 200, 200, 195, 200, 187, 185, 175,200, 200, 200, 200, 197, 187, 187, 187,200, 205, 200, 200, 195, 188, 187, 175,200, 200, 200, 200, 200, 190, 187, 175,205, 200, 199, 200, 191, 187, 187, 175,210, 200, 200, 200, 188, 185, 187, 186};
        int[] vectorDCTData = {1539,65,-12,4,1,2,-8,5,-16,3,2,0,0,-11,-2,3,-12,6,11,-1,3,0,1,-2,-8,3,-4,2,-2,-3,-5,-2,0,-2,7,-5,4,0,-1,-4,0,-3,-1,0,4,1,-1,0,3,-2,-3,3,3,-1,-1,3,-2,5,-2,4,-2,2,-3,0};
        matrix = new int[1][8][8];
        dctMatrix = new int[1][8][8];
        int index = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matrix[0][i][j] = vectorData[index];
                dctMatrix[0][i][j] = vectorDCTData[index++];
            }
        }

        image = new ImageData(matrix);
        dctImage = new ImageData(dctMatrix);
     }

    @Test
    public void testProcessDCT() throws Exception {
        DCT.processDCT(image);
        int[][][] convertedMatrix = image.getImageMatrix();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                for (int k = 0; k < matrix[i][j].length; k++) {
                    int error = Math.abs(dctMatrix[i][j][k]-convertedMatrix[i][j][k]);

                    //We check that the error range is not > 4
                    assertTrue( error <= 4 );

                    System.out.print(convertedMatrix[i][j][k]+"|"+error+"  ");
                }
                System.out.println();
            }
        }

    }

    @Test
    public void testProcessIDCT() throws Exception {
        DCT.processIDCT(dctImage);
        int[][][] convertedMatrix = image.getImageMatrix();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                for (int k = 0; k < matrix[i][j].length; k++) {
                    int error = Math.abs(matrix[i][j][k]-convertedMatrix[i][j][k]);

                    assertEquals(matrix[i][j][k],convertedMatrix[i][j][k] );

                    System.out.print(convertedMatrix[i][j][k]+"|"+error+"  ");
                }
                System.out.println();
            }
        }
    }
}