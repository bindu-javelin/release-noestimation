package main.functions;

import main.classes.Resampling;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Created by ferhaty
 */
public class FrequencyAndDeliveryTime {

    public ArrayList<Resampling> resamplingList = new ArrayList<>();

    private static int[] freq = new int[11];
    private static int[] counterDeliveryTime = new int[11];

    public static int[] findFrequency(int[] totalWip) {
        Arrays.sort(totalWip);
        counterDeliveryTime[0] = totalWip[0];
        counterDeliveryTime[10] = totalWip[1000];
        int min = counterDeliveryTime[0];
        int max = counterDeliveryTime[10];
        int difference = ((max - min) / 10);
        System.out.println("min    ----  "+counterDeliveryTime[0]);
        System.out.println("max    ----  "+counterDeliveryTime[10]);

        for(int i=0;i<11;i++){
            if(i>=0 && i<9 ){
                counterDeliveryTime[i+1]=(counterDeliveryTime[i]+difference);
            }
            System.out.println(counterDeliveryTime[i]);
        }
        int count = 0;
        int j = 0;
        for (int i = 1; i < counterDeliveryTime.length; i++) {
            count = 0;
            while (j <totalWip.length ) {
                if (counterDeliveryTime[i] >= totalWip[j]) {
                    count++;
                    j++;
                } else
                    break;
            }
            freq[i]=count;
        }
        return freq;
    }

    public int[] findDeliveryTime(int[] resamplingWip,int wipSize) {
        int[] sumRandomTotalWipItems=new int[1001];
        int [] tempResampling =new int[wipSize];
        int tempSumWipCount=0;
        int randomTotalWipItems=0;
        sumRandomTotalWipItems =new int[1001];
        tempResampling =new int[wipSize];
        RandomWipPlacing randomWipPlacing=new RandomWipPlacing();

        for(int k=1;k<1001;k++){

            for(int i=0;i<wipSize;i++){
                tempResampling =randomWipPlacing.RandomizeArray(resamplingWip);
                Resampling resampling = new Resampling();
                resampling.setResampling(tempResampling[i]);

                resamplingList.add(resampling);
                tempSumWipCount +=Integer.valueOf(tempResampling[i]);

                if(i==wipSize-1){

                    randomTotalWipItems += tempSumWipCount;
                    tempSumWipCount =0;
                }
            }
            sumRandomTotalWipItems[k]= randomTotalWipItems;
            randomTotalWipItems =0;
            System.out.println(sumRandomTotalWipItems[k]);
        }

        Arrays.sort(sumRandomTotalWipItems);

     return sumRandomTotalWipItems;
    }

}


