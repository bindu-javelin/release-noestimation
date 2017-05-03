package main.functions;

import java.util.Random;
/**
 * Created by ferhaty
 */
public class RandomWipPlacing {

    double temp2;
    static int randomPosition;
    public static int[] RandomizeArray(int[] array){
        Random rgen = new Random();  // Random number generator
        for (int i=0; i<array.length; i++) {
            randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }

    public static double[] RandomizeArray(double[] array){
        Random rgen = new Random();  // Random number generator
        for (int i=0; i<array.length; i++) {
            randomPosition = rgen.nextInt(array.length);
            double temp2= array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp2;
        }

        return array;
    }

}

