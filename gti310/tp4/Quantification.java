package gti310.tp4;

public class Quantification {

	// Attributs de classes
	QuantificationTables t = new QuantificationTables();
	private int fQ;
	private int alpha;
	private final int FQMAX = 100;
	private final int FQMIN = 1;

	// Tableau de quantification
	private int[][] qCbCr;
	private int[][] qY;

	// tableau de retour quantifier
	private int[][] fPrime;

	// tableau de DCT
	private int[][] fDCT;

	/*
	 * Constructeur de classe
	 * 
	 */
	public Quantification(int fQ) {
		checkfQ();
		qY = t.getMATRIXQY();
		qCbCr = t.getMATRIXQCBCR();
		fPrime = new int[qY.length][qY.length];
		this.fQ = fQ;

	}

	/*
	 * Methode permetant le calcul de alpha
	 * 
	 */
	public void calculAlpha() {

		if (FQMIN < fQ && fQ < (FQMAX / (FQMIN + FQMIN))) {
			alpha = fQ / (FQMAX / (FQMIN + FQMIN));
		} else {
			alpha = ((FQMIN + FQMIN) * FQMAX - ((FQMIN + FQMIN) * fQ)) / FQMAX;
		}

	}

	/*
	 * Methode de verification si le fQ fournis est bien entre 0 et 100
	 * 
	 */
	private void checkfQ() {
		if (fQ > FQMAX || fQ < FQMIN) {
			System.out.println("Erreur de FQ");
		}
	}

	/*
	 * Methode effectuant la quantification
	 * 
	 */
	public void quantificationValeurs(int u, int v) {

		// parcours du tableau
		for (int i = 0; i < fPrime.length; i++) {
			for (int j = 0; j < fPrime.length; j++) {

				if (fQ == 100) {
					fPrime[u][v] = fDCT[u][v];
				} else {
					quantificationSelection(u, v, 1);

				}

			}
		}

	}

	/*
	 * Methode poermettant la quantification des valeur Y
	 * 
	 */
	private void quantificationSelection(int u, int v, int param) {

		if (param == 1) {
			fPrime[u][v] = Math.round((fDCT[u][v]) / alpha * qY[u][v]);
		} else {
			fPrime[u][v] = Math.round((fDCT[u][v]) / alpha * qCbCr[u][v]);
		}

	}

}
