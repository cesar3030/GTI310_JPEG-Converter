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

    public static void reverse(ImageData image){
        isFirstDc=true;
        ArrayList<int[]> zigzagVectors = new ArrayList<>();

        int indexDc=0;
        int indexAc=0;

        while (indexAc < image.getACList().size() && indexDc < image.getDCList().size()){
            int dc = IDPCM(image.getDCList().get(indexDc++));
            List<Integer> ac = IRLC(image.getACList(),indexAc);
            zigzagVectors.add(toZigZagVector(dc,ac));
        }

        image.setZigzagVectors(zigzagVectors);
    }

    private static void RLC(int[] vector){

        int currentValue;
        int nbZeros=0;
        int islastAc=0; // 1 if true else 0

        for (int i = 1; i < vector.length; i++) {

            if(i==vector.length-1)islastAc=1;

            currentValue=vector[i];

            if(currentValue==0){
                nbZeros++;
            }
            else{
                ACList.add(new int[]{nbZeros,currentValue,islastAc});
                nbZeros=0;
            }
        }
    }

    private static List<Integer> IRLC(List<int[]> acRlcList, int indexAc){
        List<Integer> rowAc = new ArrayList<>();
        boolean end =false;

        while(!end){
            int[] acRlc = acRlcList.get(indexAc++);

            for (int i = 0; i < acRlc[0]; i++) {
                rowAc.add(0);
            }

            rowAc.add(acRlc[1]);

            if(acRlc[2]==1)
                end=true;
        }

        return rowAc;
    }

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

    private static int IDPCM(int dc){

        if(isFirstDc){
            isFirstDc=false;
            lastDc = dc;
            return dc;
        }
        else{
            return dc+lastDc;
        }
    }

    private static int[] toZigZagVector(int dc,List<Integer> ac){

        int size = ac.size();
        int[] vector = new int[Main.BLOCK_SIZE*Main.BLOCK_SIZE];
        int index = 0;

        vector[0]=dc;


        for (int i = 1; i < vector.length; i++){
            if(index<size)
                vector[i]=ac.get(index++);
            else
                vector[i]=0;
        }

        return vector;
    }

}
