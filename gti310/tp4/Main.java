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
		else if(args.length == 4){

			System.out.println("--------------------------------");
			System.out.println("Test encode and decode one shot");
			System.out.println("--------------------------------");

			encode("media/lena.ppm","media/encode_sortie.szl",2);
			decode("media/encode_sortie.szl","media/quantization/lena_2.ppm");
			System.out.println("encode/decode Fq 2 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",10);
			decode("media/encode_sortie.szl","media/quantization/lena_10.ppm");
			System.out.println("encode/decode Fq 10 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",20);
			decode("media/encode_sortie.szl","media/quantization/lena_20.ppm");
			System.out.println("encode/decode Fq 20 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",30);
			decode("media/encode_sortie.szl","media/quantization/lena_30.ppm");
			System.out.println("encode/decode Fq 30 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",30);
			decode("media/encode_sortie.szl","media/quantization/lena_30.ppm");
			System.out.println("encode/decode Fq 30 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",40);
			decode("media/encode_sortie.szl","media/quantization/lena_40.ppm");
			System.out.println("encode/decode Fq 40 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",50);
			decode("media/encode_sortie.szl","media/quantization/lena_50.ppm");
			System.out.println("encode/decode Fq 50 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",60);
			decode("media/encode_sortie.szl","media/quantization/lena_60.ppm");
			System.out.println("encode/decode Fq 60 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",70);
			decode("media/encode_sortie.szl","media/quantization/lena_70.ppm");
			System.out.println("encode/decode Fq 70 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",80);
			decode("media/encode_sortie.szl","media/quantization/lena_80.ppm");
			System.out.println("encode/decode Fq 80 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",90);
			decode("media/encode_sortie.szl","media/quantization/lena_90.ppm");
			System.out.println("encode/decode Fq 90 Done!");

			Entropy.flushBuffers();

			encode("media/lena.ppm","media/encode_sortie.szl",99);
			decode("media/encode_sortie.szl","media/quantization/lena_99.ppm");
			System.out.println("encode/decode Fq 99 Done!");

			System.out.println("Finish !");
		}
		else{
			System.out.println("Error: Missing arguments");
		}
		
	}

	/**
	 * Encode the given file
	 * @param sourceFile
	 * @param newFile
	 * @param qualityFactor
     */
	public static void encode(String sourceFile, String newFile, int qualityFactor){
		//PPMReaderWriter readerWriter = new PPMReaderWriter();
		RGBtoYCbCr yCbCrConverter = new RGBtoYCbCr();

		//We convert the file into a 3D matrix that contains the image
		int[][][] sourceImage = PPMReaderWriter.readPPMFile(sourceFile);

		//We convert the RGB matrix into a YCbCr martix
		sourceImage = yCbCrConverter.conversionRGBtoYCbCr(sourceImage);

		//We create a object imageData for an easiest manipulation of the image
		ImageData imageData = new ImageData(sourceImage);

		//We calculate the DCT
		DCT.process(imageData);

		//We do the quantification
		Quantification.process(imageData,qualityFactor);

		//We proceed zigzig mechanism on the image
		ZigZag.process(imageData);

		//We proceed the DPC and RLC on the DCs and ACs
		AcDcConversion.process(imageData);

		//We generate the new image
		generateOutputFile(imageData,newFile,qualityFactor);

	}

	/**
	 * decode the given file
	 * @param sourceFile
	 * @param newFile
	 */
	public static void decode(String sourceFile, String newFile){
		RGBtoYCbCr yCbCrConverter = new RGBtoYCbCr();
		int[] header = SZLReaderWriter.readSZLFile(sourceFile);

		ImageData imageData = new ImageData();
		imageData.setNbRow(header[0]);
		imageData.setNbColumn(header[1]);
		imageData.setNbColor(header[2]);

		imageData.setDCList(Entropy.getDCList(header[1],header[0]));
		imageData.setACList(Entropy.getACList(header[1],header[0]));

		AcDcConversion.reverse(imageData);

		ZigZag.reverse(imageData);

		Quantification.reverse(imageData,header[3]);

		DCT.reverse(imageData);

		int[][][] rbgImage = yCbCrConverter.conversionYCbCrtoRGB(imageData.getImageMatrix());

		PPMReaderWriter.writePPMFile(newFile,rbgImage);
	}


	/**
	 * Method that generate the file that contains the encoded picture
	 * @param imageData	Data of the image
     */
	private static void generateOutputFile(ImageData imageData,String fileName,int qualityFactor) {

		int height = imageData.getNbRow();
		int width = imageData.getNbColumn();

		for (int dc:imageData.getDCList()) {
			Entropy.writeDC(dc);
		}
		for (int[] ac:imageData.getACList()) {

			if(ac[1]==0){
				Entropy.writeAC(0,0);
			}
			else{
				Entropy.writeAC(ac[0],ac[1]);
			}
		}

		SZLReaderWriter.writeSZLFile(fileName,height,width,qualityFactor);

	}
}
