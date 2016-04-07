package gti310.tp4;

import gti310.tp4.logic.*;
import gti310.tp4.model.ImageData;
import gti310.tp4.util.PPMReaderWriter;
import gti310.tp4.util.SZLReaderWriter;

/**
 * The Main class is where the different functions are called to either encode
 * a PPM file to the Squeeze-Light format or to decode a Squeeze-Ligth image
 * into PPM format. It is the implementation of the simplified JPEG block 
 * diagrams.
 * 
 * @author Fran�ois Caron
 */
public class Main {

	/*
	 * The entire application assumes that the blocks are 8x8 squares.
	 */
	public static final int BLOCK_SIZE = 8;
	
	/*
	 * The number of dimensions in the color spaces.
	 */
	public static final int COLOR_SPACE_SIZE = 3;
	
	/*
	 * The RGB color space.
	 */
	public static final int R = 0;
	public static final int G = 1;
	public static final int B = 2;
	
	/*
	 * The YUV color space.
	 */
	public static final int Y = 0;
	public static final int Cb= 1;
	public static final int Cr = 2;
	
	/**
	 * The application's entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Squeeze Light Media Codec !");

		if(args.length == 2){
			decode(args[0],args[1]);
		}
		else if(args.length == 3){
			encode(args[0],args[1],Integer.parseInt(args[2]));
		}
		else{
			System.out.println("Error: Missing arguments");
		}

		
		PPMReaderWriter readerWriter = new PPMReaderWriter();
		RGBtoYCbCr convertie = new RGBtoYCbCr();
		
		int[][][] imageRecu = readerWriter.readPPMFile(args[0]);
		
		//effectu la conversion de RGB vers YCbCr
		int[][][] ImageConverti = convertie.conversionRGBtoYCbCr(imageRecu);
		

		//effectu la conversion de YCbCr vers RGB 
		// int[][][] ImageConverti = convertie.conversionYCbCrtoRGB(imageRecu);
		
		
		//ecriture du fichier 
		readerWriter.writePPMFile(args[1], ImageConverti);
	}

	/**
	 * Encode the given file
	 * @param sourceFile
	 * @param newFile
	 * @param qualityFactor
     */
	public static void encode(String sourceFile, String newFile, int qualityFactor){
		PPMReaderWriter readerWriter = new PPMReaderWriter();
		RGBtoYCbCr yCbCrConverter = new RGBtoYCbCr();

		//We convert the file into a 3D matrix that contains the image
		int[][][] sourceImage = readerWriter.readPPMFile(sourceFile);

		//We convert the RGB matrix into a YCbCr martix
		sourceImage = yCbCrConverter.conversionRGBtoYCbCr(sourceImage);

		//We create a object imageData for an easiest manipulation of the image
		ImageData imageData = new ImageData(sourceImage);

		//We calculate the DCT
		DCT.process(imageData);

		//We do the quantification

		//We proceed zigzig mechanism on the image
		ZigZag.process(imageData);

		//We proceed the DPC and RLC on the DCs and ACs
		AcDcConversion.process(imageData);

		//We generate the new image
		generateOutputFile(imageData,sourceFile,qualityFactor);

	}

	/**
	 * decode the given file
	 * @param sourceFile
	 * @param newFile
	 */
	public static void decode(String sourceFile, String newFile){

	}


	/**
	 * Method that generate the file that contains the encoded picture
	 * @param imageData	Data of the image
     */
	private static void generateOutputFile(ImageData imageData,String fileName,int qualityFactor) {

		int height = imageData.getNbRow();
		int width = imageData.getNbColumn();

		for (int[] ac:imageData.getACList()) {
			Entropy.writeAC(ac[0],ac[1]);
		}
		for (int dc:imageData.getDCList()) {
			Entropy.writeDC(dc);
		}

		SZLReaderWriter.writeSZLFile(fileName,height,width,qualityFactor);

	}
}
