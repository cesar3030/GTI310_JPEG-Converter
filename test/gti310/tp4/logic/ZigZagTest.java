package test.gti310.tp4.logic;

import gti310.tp4.logic.ZigZag;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by César Jeanroy on 2016-03-24.
 */
public class ZigZagTest {

    @Test
    public void testConvertToZigzag() throws Exception {

        int[][] matrix = new int[][]
                {
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
                {0,0,0,0,0,0,666,0},
        };

        int[] vector = new int[]{96,6,-1,-1,0,-1,0,0,0,-1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
                ,0,0,0,0,0,0,0,0,0,0,0,0,0,666,0};

        int[][] generatedMatrix = ZigZag.convertFromZigzag(vector);

        assertEquals(expectedMatrix.length,generatedMatrix.length);
        assertEquals(expectedMatrix[0].length,generatedMatrix[0].length);
        assertArrayEquals(expectedMatrix,generatedMatrix);
    }

    @Test
    public void testTozigZagAndFromZigZag() throws Exception {
        int[][] expectedMatrix = new int[][]{
                {1,2,6,7,15,16,28,29},
                {3,5,8,14,17,27,30,43},
                {4,9,13,18,26,31,42,44},
                {10,12,19,25,32,41,45,54},
                {11,20,24,33,40,46,53,55},
                {21,23,34,39,47,52,56,61},
                {22,35,38,48,51,57,60,62},
                {36,37,49,50,58,59,63,64},
        };

        int[] vector = ZigZag.convertToZigzag(expectedMatrix);
        assertEquals(64,vector.length);
        for (int i = 0; i < vector.length ; i++) {
            assertEquals(i+1,vector[i]);
        }

    }
}