package gti310.tp4.logic;

import gti310.tp4.Main;
import gti310.tp4.model.ImageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by César Jeanroy on 2016-04-06.
 * Class that calculate the RLC and the DPCM on image's matrices
 */
public class AcDcConversion {

    private static ArrayList<Integer> DCList = new ArrayList<>();
    private static ArrayList<int[]> ACList = new ArrayList<>();
    private static int lastDc;
    //used by IDPMC
    private static boolean isFirstDc;

    /**
     * Method that process to the AC/DC conversion
     * O(N^2)
     * @param image
     */
    public static void process(ImageData image){

        DCList = new ArrayList<>();
        ACList = new ArrayList<>();

        for (int i = 0; i < image.getZigzagVectors().size(); i++) {
            RLC(image.getZigzagVectors().get(i));
            DPCM(image.getZigzagVectors().get(i));
        }

        image.setACList(ACList);
        image.setDCList(DCList);

        ACList=null;
        DCList=null;
    }

    /**
     * Method that reverse the process of AC DC conversion
     * O(N^2)
     * @param image
     */
    public static void reverse(ImageData image){
        isFirstDc=true;
        ArrayList<int[]> zigzagVectors = new ArrayList<>();

        int index=0;
        List<List<Integer>> acList = IRLC(image.getACList());
        while (index < acList.size() && index < image.getDCList().size()){
            int dc = IDPCM(image.getDCList().get(index));
            List<Integer> ac = acList.get(index);
            zigzagVectors.add(toZigZagVector(dc,ac));
            index++;
        }

        image.setZigzagVectors(zigzagVectors);
    }

    /**
     * Calculate the RLC of the given vector and store the result in the ACList
     * O(N)
     * @param vector
     */
    private static void RLC(int[] vector){

        int currentValue;
        int nbZeros=0;
        for (int i = 1; i < vector.length; i++) {

            currentValue=vector[i];

            if(currentValue==0){
                nbZeros++;
            }
            else{
                ACList.add(new int[]{nbZeros,currentValue,0});
                nbZeros=0;
            }
        }

        //If the vector ends with a zero(s), we add an EOB
        if(nbZeros!=0){
            ACList.add(new int[]{nbZeros,0,0}); //EOB => int[1]=0
        }

        if(ACList.size()>0){
            //We set to the last int[] AC the value 1 to the index 2 to show that it's the last of the sequence
            ACList.get(ACList.size()-1)[2]=1;
        }

    }

    /**
     * Method that returns a list of list of int (list of int = 63 numbers that we are gonna use to build a 8x8 matrix) .
     * O(N^2)
     * @param acRlcList
     * @return
     */
    private static List<List<Integer>> IRLC(List<int[]> acRlcList){
        List<List<Integer>> completeAcList = new ArrayList<>();
        List<Integer> currentACList = new ArrayList<>();

        int nbData = ((Main.BLOCK_SIZE*Main.BLOCK_SIZE)-1);
        int nbDataMissing = nbData;
        int index = 0;

        while (nbDataMissing>0 && index < acRlcList.size()){
            int[] acRlc = acRlcList.get(index++);

            for (int j = 0; j < acRlc[0]; j++) {
                currentACList.add(0);
                nbDataMissing--;
            }

            if(acRlc[1]==0){//EOB
                while(currentACList.size()<=nbData)
                    currentACList.add(0);
                nbDataMissing = 0;
            }
            else{
                currentACList.add(acRlc[1]);
                nbDataMissing--;
            }

            if(nbDataMissing==0){
                completeAcList.add(currentACList);
                currentACList = new ArrayList<>();
                nbDataMissing = nbData;
            }

        }

        return completeAcList;
    }

    /**
     * Method that calculate the DPCM of the given vector
     * 0(1)
     * @param vector
     */
    private static void DPCM(int[]vector){
        int nbDc = DCList.size();
        if(nbDc>0){
            DCList.add(vector[0]-lastDc);
        }
        else{
            DCList.add(vector[0]);
        }
        lastDc=vector[0];
    }

    /**
     * Method that return the IDPCM using the last dc calculate
     * 0(1)
     * @param dc
     */
    private static int IDPCM(int dc){

        if(isFirstDc){
            isFirstDc=false;
            lastDc = dc;
        }
        else{
            lastDc=dc+lastDc;
        }
        return lastDc;
    }

    /**
     * Method that convert a DC and a list of AC into a ZigZag vector
     * O(N)
     * @param dc
     * @param ac
     * @return
     */
    private static int[] toZigZagVector(int dc,List<Integer> ac){

        int[] vector = new int[Main.BLOCK_SIZE*Main.BLOCK_SIZE];
        int index = 0;

        vector[0]=dc;

        for (int i = 1; i < vector.length; i++){
                vector[i]=ac.get(index++);
        }

        return vector;
    }

}
