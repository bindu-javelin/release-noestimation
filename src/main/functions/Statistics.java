package main.functions;
import java.util.*;
/**
 * Created by ferhaty
 */
public class Statistics {
    private double median;

    Percentile percentile = new Percentile();

    public double getMedian(int [] SumRandomTotalWipItems ){
    	if(SumRandomTotalWipItems.length%2==0)
    		median=SumRandomTotalWipItems[SumRandomTotalWipItems.length/2]+(SumRandomTotalWipItems[(SumRandomTotalWipItems.length/2) - 1])/2;
    	else
    		median=SumRandomTotalWipItems[((SumRandomTotalWipItems.length+1)/2)-1];
        return median;
    }

    public double std(int [] SumRandomTotalWipItems ){
        double sumNumber =0;
        int arrLength = SumRandomTotalWipItems.length;
        for (int i= 1; i < arrLength; i++)
        {
            sumNumber+= SumRandomTotalWipItems[i];
        }
        double avg = sumNumber/arrLength;


        double standardDevNum=0;
        for (int i= 1; i < arrLength; i++)
        {
            double s = SumRandomTotalWipItems[i]-avg;
            standardDevNum += s*s;
        }
        double standardDev = Math.sqrt(standardDevNum/(arrLength-1));
        return standardDev;
    }
    
    public double std(double [] SumRandomTotalWipItems ){
        double sumNumber =0;
        int arrLength = SumRandomTotalWipItems.length;
        for (int i= 1; i < arrLength; i++)
        {
            sumNumber+= SumRandomTotalWipItems[i];
        }
        double avg = sumNumber/arrLength;


        double standardDevNum=0;
        for (int i= 1; i < arrLength; i++)
        {
            double s = SumRandomTotalWipItems[i]-avg;
            standardDevNum += s*s;
        }
        double standardDev = Math.sqrt(standardDevNum/(arrLength-1));
        return standardDev;
    }

    public double getAvaregeT(int [] SumRandomTotalWipItems ){
        return getAverageArray(SumRandomTotalWipItems);
    }
    
    public double getAvaregeT(double [] SumRandomTotalWipItems ){
    	return getAverageArray(SumRandomTotalWipItems);
    }

    public double getPerc85(double [] SumRandomTotalWipItems,int value ){

        return percentile.evaluate(SumRandomTotalWipItems, value);
    }

    public double getPerc95(double [] SumRandomTotalWipItems,int value ){

        return percentile.evaluate(SumRandomTotalWipItems, value);
    }

    public double getMode(double [] SumRandomTotalWipItems ){
            HashMap<Double, Double> freqs = new HashMap<Double, Double>();
            for (double d : SumRandomTotalWipItems) {
                Double freq = freqs.get(d);
                freqs.put(d, (freq == null ? 1 : freq + 1));
            }
            List<Double> mode = new ArrayList<Double>();
            List<Double> frequency = new ArrayList<Double>();
            List<Double> values = new ArrayList<Double>();

            for (Map.Entry<Double, Double> entry : freqs.entrySet()) {
                frequency.add(entry.getValue());
                values.add(entry.getKey());
            }
            double max = Collections.max(frequency);
            for(int i=0; i< frequency.size();i++)
            {
                double val =frequency.get(i);
                if(max == val )
                {
                    mode.add(values.get(i));
                }
            }
        double[] modeMult = new double[mode.size()];
        for (int i = 0; i < modeMult.length; i++) {
            modeMult[i] = mode.get(i).doubleValue();  // java 1.4 style
            // or:
            modeMult[i] = mode.get(i);                // java 1.5+ style (outboxing)
            //System.out.println("modu-----------------"+target[i]);
        }
            return modeMult[0];
        }

    public int getSipSize(int [] SumRandomTotalWipItems ){

        return SumRandomTotalWipItems.length-1;
    }

    public int getAverageArray(int [] array){
        double average=0; int sum=0; int count=0;
        for (int i=0;i<array.length;i++){
            sum+=array[i];
            count++;
        }
        average=sum/count;
        return (int)average;
    }
    

    public double getAverageArray(double [] array){
        double average=0,sum=0, count=0;
        for (int i=0;i<array.length;i++){
            sum+=array[i];
            count++;
        }
        average=sum/count;
        return average;
    }

    public double getAverage(int [] array){
        double average=0,sum=0, count=0;
        for (int i=0;i<array.length;i++){
            sum+=array[i];
            count++;
        }
        average=sum/count;
        return average;
    }
}













