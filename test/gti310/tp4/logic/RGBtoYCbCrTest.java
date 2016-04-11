package gti310.tp4.logic;

import gti310.tp4.util.PPMReaderWriter;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by César Jeanroy on 2016-04-08.
 */
public class RGBtoYCbCrTest {

    private static RGBtoYCbCr rgBtoYCbCr;


    @Test
    public void testConversionRGBtoYCbCr() throws Exception {
        RGBtoYCbCr yCbCrConverter = new RGBtoYCbCr();

        //We convert the file into a 3D matrix that contains the image
        int[][][] sourceImage = PPMReaderWriter.readPPMFile("media/lena.ppm");

        //We convert the RGB matrix into a YCbCr martix
        int[][][] convertedImage = yCbCrConverter.conversionRGBtoYCbCr(sourceImage);
        convertedImage = yCbCrConverter.conversionYCbCrtoRGB(convertedImage);

        //assertArrayEquals(sourceImage,convertedImage);

    }

}