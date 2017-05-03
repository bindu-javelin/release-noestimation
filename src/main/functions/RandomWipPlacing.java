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

    public static double[] RandomizeArray(double[] array1){
        Random rgen = new Random();  // Random number generator
        for (int i=0; i<array1.length; i++) {
            randomPosition = rgen.nextInt(array1.length);
            double temp2= array1[i];
            array1[i] = array1[randomPosition];
            array1[randomPosition] = temp2;
        }

        return array1;
    }

}

