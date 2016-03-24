package gti310.tp4;

import java.util.Arrays;
import java.util.Vector;

public class RGBtoYUV {

	public int[][][] conversionRGBtoYUV(int[][][] imageRecu) {

		// fonction inspirée de readPPMFile() de la classe PPMReaderWriter
		int H = imageRecu[0].length;
		int V = imageRecu[0][0].length;
		int[][][] YCBR = new int[Main.COLOR_SPACE_SIZE][H][V];
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < V; j++) {
				YCBR[Main.Y][i][j] = (int) ((0.299 * imageRecu[Main.R][i][j]) + (0.587 * imageRecu[Main.G][i][j])+ (0.114 * imageRecu[Main.B][i][j]));
				YCBR[Main.Cb][i][j] = (int)((-0.168736 * imageRecu[Main.R][i][j]) + (-0.331264 * imageRecu[Main.G][i][j]) + (0.5 * imageRecu[Main.B][i][j]));
				YCBR[Main.Cr][i][j] = (int)((0.5 * imageRecu[Main.R][i][j]) + (-0.418688 * imageRecu[Main.G][i][j]) + (-0.081312 * imageRecu[Main.B][i][j]));
				
			}
		}
		System.out.println("Conversion vers YUV terminer");
		return YCBR;
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
