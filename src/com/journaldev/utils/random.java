package com.journaldev.utils;

import java.util.Random;
/** 
 * @author Ferhat Yalçın
 */  

public class random {

	public static int[] RandomizeArray(int[] array){
		Random rgen = new Random();  // Random number generator			
		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		return array;
	}

}
