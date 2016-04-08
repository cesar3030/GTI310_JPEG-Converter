package gti310.tp4.logic;

import gti310.tp4.model.ImageData;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by César Jeanroy on 2016-04-08.
 */
public class QuantificationTest {


    int[][][] dctMatrix;
    ImageData dctImage;
    int[] vectorBefore;
    int[] vectorAfter;

    @Before
    public void setUp(){
        vectorBefore = new int[]{1539,65,-12,4,1,2,-8,5,-16,3,2,0,0,-11,-2,3,-12,6,11,-1,3,0,1,-2,-8,3,-4,2,-2,-3,-5,-2,0,-2,7,-5,4,0,-1,-4,0,-3,-1,0,4,1,-1,0,3,-2,-3,3,3,-1,-1,3,-2,5,-2,4,-2,2,-3,0};
        vectorAfter = new int[]{96,6,-1,-1,0,-1,0,0,0,-1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        dctMatrix = new int[3][8][8];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                dctMatrix[0][i][j] = vectorBefore[index++];
            }
        }

        dctImage = new ImageData(dctMatrix);
    }

    @Test
    public void testProcess() throws Exception {

        Quantification.process(dctImage,50);
        Quantification.reverse(dctImage,50);

        int index = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                int error = Math.abs(vectorBefore[index++]-dctImage.getImageMatrix()[0][i][j]);
                System.out.println("error = " + error);
                //assertTrue(error<10);
                //assertEquals(vectorBefore[index++],dctImage.getImageMatrix()[0][i][j]);
            }
        }


    }

    @Test
    public void testReverse() throws Exception {

    }
}