package gti310.tp4.logic;

import gti310.tp4.Main;

import java.util.Arrays;
import java.util.Vector;

public class RGBtoYCbCr {

	
	/**
	 * Methode permettant la conversion d'une image de format RGB vers YCbCr
	 * O(N^2)  (O(N^2 * 3))
	 */
	public int[][][] conversionRGBtoYCbCr(int[][][] imageRecu) {

		int H = imageRecu[0].length;
		int V = imageRecu[0][0].length;
		int[][][] YCbCr = new int[Main.COLOR_SPACE_SIZE][H][V];
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < V; j++) {
				YCbCr[Main.Y][i][j] = (int) ((0.299 * imageRecu[Main.R][i][j]) + (0.587 * imageRecu[Main.G][i][j])+ (0.114 * imageRecu[Main.B][i][j]));
				YCbCr[Main.Cb][i][j] = (int)((-0.168736 * imageRecu[Main.R][i][j]) - (0.331264 * imageRecu[Main.G][i][j]) + (0.5 * imageRecu[Main.B][i][j])+128);
				YCbCr[Main.Cr][i][j] = (int)((0.5 * imageRecu[Main.R][i][j]) - (0.418688 * imageRecu[Main.G][i][j]) - (0.081312 * imageRecu[Main.B][i][j])+128);
			}
		}
		System.out.println("Conversion vers YCbCr terminer");
		return YCbCr;
	}

	

	/**
	 * Methode permettant la conversion d'une image de format RGB vers YCbCr
	 * O(N^2)  (O(N^2 * 3))
	 */
	public int[][][] conversionYCbCrtoRGB(int[][][] imageRecu) {

		int H = imageRecu[0].length;
		int V = imageRecu[0][0].length;
		
		int[][][] RGB = new int[Main.COLOR_SPACE_SIZE][H][V];
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < V; j++) {
				
				double yRecu  = imageRecu[Main.Y][i][j];
				double cbRecu = imageRecu[Main.Cb][i][j];
				double crRecu = imageRecu[Main.Cr][i][j];
			
				  RGB[Main.R][i][j] = (int) (yRecu + 1.402 * (crRecu- 128));
				  RGB[Main.G][i][j] =  (int)(yRecu - 0.34414 * (cbRecu - 128) - (0.71414 *(crRecu - 128) ));
				  RGB[Main.B][i][j] =  (int)(yRecu + 1.772 * (cbRecu - 128));
				
			}
		}
		System.out.println("Conversion to RGB done");
		return RGB;
	}
	
	
	
	/*
	 * Methode permetant la lecture de la matrice
	 * 
	 * @param image
	 */
	public void lectureRGBImage(int[][][] image) {

		System.out.println(Arrays.deepToString(image));
		System.out.println(image[0][0][0]);
		System.out.println(image[1][0][0]);
		System.out.println(image[2][0][0]);
	}


}
