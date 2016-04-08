package gti310.tp4.logic;

import gti310.tp4.model.ImageData;
import gti310.tp4.util.PPMReaderWriter;
import gti310.tp4.util.SZLReaderWriter;
import org.junit.Test;

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

        imageData2.setACList(Entropy.getACList());
        imageData2.setDCList(Entropy.getDCList());

    }
}