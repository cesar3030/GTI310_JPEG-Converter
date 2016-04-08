package test.gti310.tp4.logic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import gti310.tp4.logic.Quantification;
import gti310.tp4.logic.QuantificationTables;
import gti310.tp4.model.ImageData;

public class QuantificationTest {

	private static  int[][] f = { { 30, 50 } };
	private static  int[][] g = { { 38, 50 } };
	private static final int[][] q = { { 2, 11 } };
	private static final double alpha = 0.4;
	
	
	@Test
    public void testProcess(){
		ImageData img = new ImageData();
		
			
		int s = (int) Math.round(   f[0][0] / (alpha * q[0][0]));
		//System.out.println("quantif" + s);
		int quantif = s;
	
		
		
		
		 s = (int) Math.round(   g[0][0] * (alpha * q[0][0]));
		
		 int INVquantif = s;
		
		 System.out.println(" quantif  : " + quantif +" quantif inv  : " +  INVquantif);

}
}