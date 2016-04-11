package test.gti310.tp4.logic;

import gti310.tp4.logic.AcDcConversion;
import gti310.tp4.logic.ZigZag;
import gti310.tp4.model.ImageData;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by César Jeanroy on 2016-04-06.
 */
public class AcDcConversionTest {

    @Test
    public void testProcess() throws Exception {

        ImageData image =new ImageData();

        ArrayList<int[]> zigzagVectors = new ArrayList<>();
        zigzagVectors.add(new int[]{96,6,-1,-1,0,-1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0});

        image.setZigzagVectors(zigzagVectors);

        AcDcConversion.process(image);

        //WE TEST THE AC CONVERSION AND MAKE SURE THAT THERE IS ONLY ONE DC
        assertEquals(1,image.getDCList().size());
        assertEquals(96,(int)image.getDCList().get(0));

        assertEquals(7,image.getACList().size());
        assertEquals(0,image.getACList().get(0)[0]);
        assertEquals(6,image.getACList().get(0)[1]);
        assertEquals(0,image.getACList().get(0)[2]);
        assertEquals(0,image.getACList().get(1)[0]);
        assertEquals(-1,image.getACList().get(1)[1]);
        assertEquals(0,image.getACList().get(1)[2]);
        assertEquals(0,image.getACList().get(2)[0]);
        assertEquals(-1,image.getACList().get(2)[1]);
        assertEquals(0,image.getACList().get(2)[2]);
        assertEquals(1,image.getACList().get(3)[0]);
        assertEquals(-1,image.getACList().get(3)[1]);
        assertEquals(0,image.getACList().get(3)[2]);
        assertEquals(3,image.getACList().get(4)[0]);
        assertEquals(1,image.getACList().get(4)[1]);
        assertEquals(0,image.getACList().get(4)[2]);
        assertEquals(2,image.getACList().get(5)[0]);
        assertEquals(1,image.getACList().get(5)[1]);
        assertEquals(0,image.getACList().get(5)[2]);


        //WE TEST THE DC CONVERSION
        zigzagVectors = new ArrayList<>();
        zigzagVectors.add(new int[]{150,0});
        zigzagVectors.add(new int[]{155,0});
        zigzagVectors.add(new int[]{149,0});
        zigzagVectors.add(new int[]{152,0});
        zigzagVectors.add(new int[]{144,0});

        image.setZigzagVectors(zigzagVectors);

        AcDcConversion.process(image);

        assertEquals(5,image.getDCList().size());
        assertEquals(150,(int)image.getDCList().get(0));
        assertEquals(5,(int)image.getDCList().get(1));
        assertEquals(-6,(int)image.getDCList().get(2));
        assertEquals(3,(int)image.getDCList().get(3));
        assertEquals(-8,(int)image.getDCList().get(4));

    }

    @Test
    public void testProcessAndReverse() throws Exception {
        ImageData image =new ImageData();

        ArrayList<int[]> zigzagVectors = new ArrayList<>();
        zigzagVectors.add(new int[]{96,6,-1,-1,0,-1,0,0,0,-1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        image.setZigzagVectors(zigzagVectors);

        assertEquals(64,zigzagVectors.get(0).length);


        AcDcConversion.process(image);
        AcDcConversion.reverse(image);

        //we check DC is the same
        assertEquals(zigzagVectors.get(0)[0],(int)image.getDCList().get(0));

        //We check AC are the sames
        for (int i = 1; i < zigzagVectors.get(0).length; i++) {
            assertEquals(zigzagVectors.get(0)[i],image.getZigzagVectors().get(0)[i]);
        }


    }
}