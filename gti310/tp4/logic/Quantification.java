package gti310.tp4.logic;

import java.util.ArrayList;

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
	 * Methode permetant le calcul de alpha
	 */
	private static void calculAlpha() {

		if (1 < fQ && fQ < 50.0) {
			alpha = (fQ / 50.0);
		} else {
			alpha = (200.0 - (2.0 * fQ)) / 100.0;
		}

	}

	/*
	 * Methode de verification si le fQ fournis est bien entre 0 et 100
	 */
	private static void checkfQ() {
		if (fQ > FQMAX || fQ < FQMIN) {
			System.out.println("Erreur de FQ");
		}
	}

	/*
	 * Methode effectuant la quantification
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

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				recu[i][j] = (int) Math.round((recu[i][j]) / (alpha * t.getMATRIXQY()[i][j]));
			}
		}
	}

	/*
	 * Methode permettant la quantification des valeur CbCr
	 */
	private static void quantificationCbCr(int[][] recu) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				recu[i][j] = (int) Math.round((recu[i][j]) / (alpha * t.getMATRIXQCBCR()[i][j]));
			}
		}
	}
	/*
	 * Methode permetant le process de la quantification inverse
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
	 */
	private static void reverseQuantificationCbCr(int[][] recu) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				recu[i][j] = (int) Math.round((recu[i][j]) * (alpha * t.getMATRIXQY()[i][j]));
			}
		}
	}
	
	/*
	 * Methode permetant la conversion inverse de la matrice y inverse
	 */
	private static void reverseQuantificationY(int[][] recu) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				recu[i][j] = (int) Math.round((recu[i][j]) * (alpha * t.getMATRIXQCBCR()[i][j]));
			}
		}
	}
}
