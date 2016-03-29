package gti310.tp4;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by César Jeanroy on 2016-03-24.
 */
public class ZigZagTest {

    @Test
    public void testConvertToZigzag() throws Exception {

        int[][] matrix = new int[][]{
                {96,6,-1,0,0,0,0,0},
                {-1,0,0,0,0,0,0,0},
                {-1,0,1,0,0,0,0,0},
                {-1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
        };

        int[] expectedVector = new int[]{96,6,-1,-1,0,-1,0,0,0,-1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
                ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        int[] generatedVector = ZigZag.convertToZigzag(matrix);

        assertEquals(expectedVector.length,generatedVector.length);

        for (int i = 0; i < generatedVector.length ; i++) {
            assertEquals(expectedVector[i],generatedVector[i]);
        }
    }

    @Test
    public void testConvertFromZigzag() throws Exception {

        int[][] expectedMatrix = new int[][]{
                {96,6,-1,0,0,0,0,0},
                {-1,0,0,0,0,0,0,0},
                {-1,0,1,0,0,0,0,0},
                {-1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
        };

        int[] vector = new int[]{96,6,-1,-1,0,-1,0,0,0,-1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
                ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        int[][] generatedMatrix = ZigZag.convertFromZigzag(vector);

        assertEquals(expectedMatrix.length,generatedMatrix.length);
        assertEquals(expectedMatrix[0].length,generatedMatrix[0].length);

        for (int i = 0; i < expectedMatrix.length ; i++) {
            for (int j = 0; j < expectedMatrix[0].length; j++) {
                assertEquals(expectedMatrix[i][j],generatedMatrix[i][j]);
            }

        }
    }
}