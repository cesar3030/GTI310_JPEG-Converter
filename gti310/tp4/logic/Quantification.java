package gti310.tp4.logic;

import java.util.ArrayList;

import gti310.tp4.Main;
import gti310.tp4.model.ImageData;;

public class Quantification {

	// Attributs de classes
	private static final QuantificationTables t = new QuantificationTables();
	private static int fQ;
	private static double alpha;
	private final static int FQMAX = 100;
	private final static int FQMIN = 0;

	// tableau de DCT
	private static ArrayList<int[][]> matriceY;
	private static ArrayList<int[][]> matriceCB;
	private static ArrayList<int[][]> matriceCR;

	/*
	 * Method that calulate the alpha value
	 * O(1)
	 */
	private static void calculAlpha() {

		if (1 <= fQ && fQ <= 50.0) {
			alpha = (50.0 / fQ );
		}
		else if(50< fQ && fQ <= 99){
			alpha = (200.0 - (2.0 * fQ)) / 100.0;
		}

	}

	/*
	 * Method to check if fq is between 0 et 100
	 * O(1)
	 */
	private static void checkfQ() {
		if (fQ > FQMAX || fQ < FQMIN) {
			System.out.println("Erreur de FQ");
		}
	}

	/**
	 * Method that perform the quantization on the given ImageData object for the Quality Factor wanted
	 * O()
	 * @param img
	 * @param fQRecu
     */
	public static void process(ImageData img, int fQRecu) {

		fQ = fQRecu;
		checkfQ();
		calculAlpha();

		// Fetch les matrices
		matriceY = img.getYMatrices();
		matriceCB = img.getCbMatrices();
		matriceCR = img.getCrMatrices();

		for (int[][] m : matriceY) {
			quantificationY(m);
		}

		for (int[][] n : matriceCB) {
			quantificationCbCr(n);
		}

		for (int[][] o : matriceCR) {
			quantificationCbCr(o);
		}
	}

	/*
	 * Methode permettant la quantification des valeur Y
	 */
	private static void quantificationY(int[][] recu) {

		for (int i = 0; i < Main.BLOCK_SIZE; i++) {
			for (int j = 0; j < Main.BLOCK_SIZE; j++) {
				recu[i][j] = (int) ((recu[i][j]) / (alpha * t.getMATRIXQY()[i][j]));
			}
		}
	}

	/**
	 * Method that quantize the Cb or Cr values
	 * O(N^2)
	 * @param recu vector of Cb or Cr values
     */
	private static void quantificationCbCr(int[][] recu) {
		for (int i = 0; i < Main.BLOCK_SIZE; i++) {
			for (int j = 0; j < Main.BLOCK_SIZE; j++) {
				recu[i][j] = (int)((recu[i][j])/(alpha * t.getMATRIXQCBCR()[i][j]));
			}
		}
	}

	/*
	 * Method that perform the inverse quantization on the given ImageData object for the Quality Factor wanted
	 * O(N^2) (O(N^2 * A * 3) A: nb of matrices in the ImageData object)
	 * @param img
	 * @param fQRecu
	 */
	public static void reverse(ImageData img, int fQRecu) {
		 ArrayList<int[][]> reserveMatriceY = img.getYMatrices();
		 ArrayList<int[][]> reserveMatriceCB = img.getCbMatrices();
		 ArrayList<int[][]> reserveMatriceCR = img.getCrMatrices();
		 
		 
		 fQ = fQRecu;
			checkfQ();
			calculAlpha();
		 
		
		 for (int[][] m : reserveMatriceY) {
			 reverseQuantificationY(m);
		 }

		 for (int[][] n : reserveMatriceCB) {
			reverseQuantificationCbCr(n);
		 }

		 for (int[][] o : reserveMatriceCR) {
			reverseQuantificationCbCr(o);
		 }
	}
	/*
	 * Methode permetant la conversion inverse de la matrice Cb ou Cr inverse
	 * O(N^2)
	 */
	private static void reverseQuantificationCbCr(int[][] recu) {
		for (int i = 0; i < Main.BLOCK_SIZE; i++) {
			for (int j = 0; j < Main.BLOCK_SIZE; j++) {
				recu[i][j] = (int) ((recu[i][j]) * (alpha * t.getMATRIXQCBCR()[i][j]));
			}
		}
	}
	
	/*
	 * Methode permetant la conversion inverse de la matrice y inverse
	 * O(N^2)
	 */
	private static void reverseQuantificationY(int[][] recu) {
		for (int i = 0; i < Main.BLOCK_SIZE; i++) {
			for (int j = 0; j < Main.BLOCK_SIZE; j++) {
				recu[i][j] = (int) ((recu[i][j]) * (alpha * t.getMATRIXQY()[i][j]));
			}
		}
	}
}
