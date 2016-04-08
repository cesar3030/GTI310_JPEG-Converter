package gti310.tp4.logic;

import gti310.tp4.Main;
import gti310.tp4.model.ImageData;
import gti310.tp4.util.PPMReaderWriter;
import gti310.tp4.util.SZLReaderWriter;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by César Jeanroy on 2016-04-07.
 */
public class EntropyTest {

    @Test
    public void testDecodeSZLFile() throws Exception {

        /**
         * We encode the image until we need to do the entropy
         */
        //PPMReaderWriter readerWriter = new PPMReaderWriter();
        RGBtoYCbCr yCbCrConverter = new RGBtoYCbCr();

        //We convert the file into a 3D matrix that contains the image
        int[][][] sourceImage = PPMReaderWriter.readPPMFile("media/lena.ppm");

        //We convert the RGB matrix into a YCbCr martix
        sourceImage = yCbCrConverter.conversionRGBtoYCbCr(sourceImage);

        //We create a object imageData for an easiest manipulation of the image
        ImageData imageData = new ImageData(sourceImage);

        //We calculate the DCT
        DCT.process(imageData);

        //We do the quantification
        Quantification.process(imageData,40);

        int[][][] imageAfterQuatization = imageData.getImageMatrix();

        //We proceed zigzig mechanism on the image
        ZigZag.process(imageData);

        //We proceed the DPC and RLC on the DCs and ACs
        AcDcConversion.process(imageData);


        /**
         * We check if the ImageData object after entropy from a SLZ file
         * is the same that the ImageData from the same image (PPM) before the entropy
         */

        int[] header = SZLReaderWriter.readSZLFile("media/encode_sortie.szl");

        ImageData imageData2 = new ImageData();
        imageData2.setNbRow(header[0]);
        imageData2.setNbColumn(header[1]);
        imageData2.setNbColor(header[2]);

        imageData2.setDCList(Entropy.getDCList(header[1],header[0]));
        imageData2.setACList(Entropy.getACList(header[1],header[0]));

        for (int i = 0; i < imageData.getDCList().size(); i++) {
            assertEquals(imageData.getDCList().get(i),imageData2.getDCList().get(i));
        }

        for (int i = 0; i < imageData.getACList().size(); i++) {
            assertEquals(imageData.getACList().get(i)[1],imageData2.getACList().get(i)[1]);
        }

        AcDcConversion.reverse(imageData2);

        assertZigZagEquals(imageData.getZigzagVectors(),imageData2.getZigzagVectors());


        ZigZag.reverse(imageData2);
        assertArrayEquals(imageAfterQuatization,imageData2.getImageMatrix());


    }

    /**
     * Method that test if two zigzag are identical.
     * @param expectedZigzag
     * @param actualZigzag
     */
    private void assertZigZagEquals(List<int[]> expectedZigzag, List<int[]> actualZigzag) {
        assertEquals(expectedZigzag.size(),actualZigzag.size());

        for (int i = 0; i < expectedZigzag.size() ; i++) {
            int[] expected = expectedZigzag.get(i);
            int[] actual = actualZigzag.get(i);
            assertEquals(expected.length,actual.length);
            assertArrayEquals(expected,actual);

        }
    }
}