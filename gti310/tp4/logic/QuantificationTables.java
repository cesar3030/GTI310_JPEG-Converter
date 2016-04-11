package gti310.tp4.logic;

public class QuantificationTables {


		private static final int[][] MATRICEQY = {
					{16, 40, 40, 40, 40, 40, 51, 61},
					{40 ,40 ,40 ,40 ,40 ,58 ,60, 55},
					{40 ,40 ,40 ,40 ,40 ,57 ,69 ,56},
					{40 ,40 ,40 ,40 ,51 ,87 ,80 ,62},
					{40 ,40 ,40 ,56 ,68 ,109 ,103 ,77},
					{40 ,40 ,55 ,64 ,81 ,104 ,113 ,92},
					{49 ,64 ,78 ,87 ,103 ,121 ,120 ,101},
					{72 ,92 ,95 ,98 ,112 ,100 ,103 ,95}};
					
				
		private static final int[][] MATRICEQCBCR = {
				{17, 40, 40, 95, 95, 95, 95, 95},
				{40, 40, 40, 95, 95, 95, 95, 95},
				{40, 40, 40, 95, 95, 95, 95, 95},
				{40, 40, 95, 95, 95, 95, 95, 95},
				{95, 95, 95, 95, 95, 95, 95, 95},
				{95, 95, 95, 95, 95, 95, 95, 95},
				{95, 95, 95, 95, 95, 95, 95, 95},
				{95, 95, 95, 95, 95, 95, 95, 95}};

		
	public int [][]getMATRIXQY(){
		
		return MATRICEQY;
	}
	
	public int [][]getMATRIXQCBCR(){
		
		return MATRICEQCBCR;
	}

public void selectIndex(){
	System.out.println(MATRICEQY[1][0]);
}
}
