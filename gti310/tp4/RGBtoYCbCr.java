package gti310.tp4;
import java.util.Vector;

public class RGBtoYCbCr {
	
	
	
	
	public int[][][] conversion(int[][][] image){

		//fonction inspirée de readPPMFile() de la classe PPMReaderWriter
		int H = image[0].length;
		int V = image[0][0].length;
		int[][][] YCBR = new int[Main.COLOR_SPACE_SIZE][H][V];
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < V ; j++) {
				YCBR[Main.Y][i][j] =  (int)((0.299 * image[Main.R][i][j]) + (0.587 * image[Main.G][i][j]) + (0.114 * image[Main.B][i][j]));
				YCBR[Main.U][i][j] = (int)((-0.168736 * image[Main.R][i][j]) + (-0.331264 * image[Main.G][i][j]) + (0.5 * image[Main.B][i][j]));
				YCBR[Main.V][i][j] = (int)((0.5 * image[Main.R][i][j]) + (-0.418688 * image[Main.G][i][j]) + (-0.081312 * image[Main.B][i][j]));
				//YCBR[Main.U][i][j] = (int) (0.492 * (image[Main.B][i][j] - YCBR[Main.Y][i][j]));
				//YCBR[Main.U][i][j] = (int) (0.877 * (image[Main.R][i][j] - YCBR[Main.Y][i][j]));
			}
		}
		return YCBR;
	}
}
