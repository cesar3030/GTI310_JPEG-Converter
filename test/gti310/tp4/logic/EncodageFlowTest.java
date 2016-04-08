package gti310.tp4.logic;

import gti310.tp4.Main;
import gti310.tp4.model.ImageData;
import gti310.tp4.util.PPMReaderWriter;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by César Jeanroy on 2016-04-07.
 */
public class EncodageFlowTest {

    @Test
    public void testEncodageFlow() throws Exception {

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

        //We test that the ImageObject can regenerate the same matrix that has been given for ut<s initialization
        compare3dMatrices(sourceImage,imageData.getImageMatrix());

        checkMatricesValidity(imageData);

        //We calculate the DCT
        DCT.process(imageData);
        checkMatricesValidity(imageData);
        int[][][] matrixAfterDCT = imageData.getImageMatrix();

        //We do the quantification
        Quantification.process(imageData,40);
        checkMatricesValidity(imageData);
        int[][][] matrixAfterQuantization = imageData.getImageMatrix();

        //We proceed zigzig mechanism on the image
        ZigZag.process(imageData);
        List<int[]> zigzagTmp = imageData.getZigzagVectors();
        checkZigzagVectors(imageData);


        //We proceed the DPC and RLC on the DCs and ACs
        AcDcConversion.process(imageData);
        checkAcDcLists(imageData);

        /***********
         * REVERSE *
         ***********/

        AcDcConversion.reverse(imageData);
        assertZigZagEquals(zigzagTmp,imageData.getZigzagVectors());

        ZigZag.reverse(imageData);
        compare3dMatrices(matrixAfterQuantization,imageData.getImageMatrix());

        Quantification.reverse(imageData,40);
        //compare3dMatrices(matrixAfterDCT,imageData.getImageMatrix());


    }

    @Test
    public void testOnlyColorSpaceBackAndForth() throws Exception {
        //PPMReaderWriter readerWriter = new PPMReaderWriter();
        RGBtoYCbCr yCbCrConverter = new RGBtoYCbCr();

        //We convert the file into a 3D matrix that contains the image
        int[][][] sourceImage = PPMReaderWriter.readPPMFile("test/media/lena.ppm");

        //We convert the RGB matrix into a YCbCr martix
        int[][][]YcbCrImage = yCbCrConverter.conversionRGBtoYCbCr(sourceImage);
        //We convert the YCbCr matrix into a RBG martix
        int[][][]RGBImage = yCbCrConverter.conversionYCbCrtoRGB(YcbCrImage);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 256; k++) {
                    assertTrue(Math.abs(sourceImage[i][j][k]-RGBImage[i][j][k])<4);
                }
            }
        }

        PPMReaderWriter.writePPMFile("test/media/test_RGB_to_YCbCr_to_RGB.ppm",RGBImage);
    }

    @Test
    public void testColorSpacePlusDCTBackAndForth() throws Exception {
        //PPMReaderWriter readerWriter = new PPMReaderWriter();
        RGBtoYCbCr yCbCrConverter = new RGBtoYCbCr();

        //We convert the file into a 3D matrix that contains the image
        int[][][] sourceImage = PPMReaderWriter.readPPMFile("test/media/lena.ppm");

        //We convert the RGB matrix into a YCbCr martix
        int[][][]YcbCrImage = yCbCrConverter.conversionRGBtoYCbCr(sourceImage);

        //We create a object imageData for an easiest manipulation of the image
        ImageData imageData = new ImageData(YcbCrImage);

        DCT.process(imageData);
        DCT.reverse(imageData);
        //We convert the YCbCr matrix into a RBG martix
        int[][][]RGBImage = yCbCrConverter.conversionYCbCrtoRGB(imageData.getImageMatrix());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 256; k++) {
                    //assertTrue(Math.abs(sourceImage[i][j][k]-RGBImage[i][j][k])<4);
                }
            }
        }

        PPMReaderWriter.writePPMFile("test/media/test_RGB_to_YCbCr_to_RGB_plus_DCT.ppm",RGBImage);
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

    /**
     * Method that check if there is the right number of DC
     * And check if there is the right number of AC in which there is the good number of data
     * @param imageData the ImageData instance under test
     */
    private void checkAcDcLists(ImageData imageData) {
        assertEquals(imageData.getMatricesList().size(),imageData.getDCList().size());
        int sumData=0;

        for (int[] vector: imageData.getACList()) {
            sumData+=vector[0];
            if(vector[1]!=0)//not EOB
                sumData+=1; //for the vector[1] that contains a value
        }
        sumData+=imageData.getDCList().size();

        assertEquals(imageData.getZigzagVectors().size()*Main.BLOCK_SIZE*Main.BLOCK_SIZE,sumData);
    }

    /**
     * Method that check that there is the right number of vector in the vector list
     * and that each vector has enough data inside
     * @param imageData the ImageData instance under test
     */
    private void checkZigzagVectors(ImageData imageData) {
        assertEquals(imageData.getMatricesList().size(),imageData.getZigzagVectors().size());
        for (int[] vector: imageData.getZigzagVectors()) {
            assertEquals(Main.BLOCK_SIZE*Main.BLOCK_SIZE,vector.length);
        }
    }


    /**
     * Method that compare two 3D Matrices
     * @param expected
     * @param actual
     * @throws Exception
     */
    public void compare3dMatrices(int[][][] expected,int[][][] actual) throws Exception {
        assertArrayEquals(expected,actual);
    }

    /**
     * Method to check that all the matrices of the list have 8 row and 8 columns
     * and that there is the right number of matrices in the list
     * @param image The ImageData under test
     */
    public void checkMatricesValidity(ImageData image){
        int nbMatricesExpected = (image.getNbRow()*image.getNbColumn())/(Main.BLOCK_SIZE*Main.BLOCK_SIZE);

        assertEquals(nbMatricesExpected,image.getYMatrices().size());
        assertEquals(nbMatricesExpected,image.getCbMatrices().size());
        assertEquals(nbMatricesExpected,image.getCrMatrices().size());

        for (int[][] matrix: image.getYMatrices()) {
            assertEquals(Main.BLOCK_SIZE,matrix.length);
            assertEquals(Main.BLOCK_SIZE,matrix[0].length);
        }

        for (int[][] matrix: image.getCbMatrices()) {
            assertEquals(Main.BLOCK_SIZE,matrix.length);
            assertEquals(Main.BLOCK_SIZE,matrix[0].length);
        }

        for (int[][] matrix: image.getCrMatrices()) {
            assertEquals(Main.BLOCK_SIZE,matrix.length);
            assertEquals(Main.BLOCK_SIZE,matrix[0].length);
        }

    }

}
