package com.journaldev.utils;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
/** 
 * @author Ferhat Yalçın
 */  
public class arraysort {
	

 int getMax(int[][] b){ 
	 Integer[][] a= new Integer[b.length][3];
	 for(int i=0;i<b.length;i++)                   // 
	        for(int j=0;j<a[i].length;j++){
	        	a[i][j]=b[i][j];
	        	
	        }
	
	//System.out.println("önce: " + Arrays.deepToString(a));
	Arrays.sort(a, new ColumnComparator(1, SortingOrder.ASCENDING));
	//System.out.println("sonra " + Arrays.deepToString(a));
	int maxValue=a[b.length-1][1];
    
    return maxValue; 
  }

 int getMin(int[][] b){ 
	 Integer[][] a= new Integer[b.length][3];
	 for(int i=0;i<b.length;i++)                   // 
	        for(int j=0;j<a[i].length;j++){
	        	a[i][j]=b[i][j];
	        	
	        }
	
	//System.out.println("önce: " + Arrays.deepToString(a));
	Arrays.sort(a, new ColumnComparator(1, SortingOrder.ASCENDING));
	//System.out.println("sonra " + Arrays.deepToString(a));
	int minValue=a[1][1];
    
    return minValue; 
  } 
}


	enum SortingOrder{ ASCENDING, DESCENDING; };

	class ColumnComparator implements Comparator<Comparable[]> { 
	private final int iColumn; 
	private final SortingOrder order;
		public ColumnComparator(int column, SortingOrder order) { 
		this.iColumn = column; this.order = order; 
		} 
	@Override 
	public int compare(Comparable[] c1, Comparable[] c2) {
		int result = c1[iColumn].compareTo(c2[iColumn]);
		return order==SortingOrder.ASCENDING ? result : -result;
		} 
	} 
class Course implements Comparable<Course>{ 
	int id; String name; int price;
	public Course(int id, String name, int price){
		this.id = id; this.name = name; this.price = price;
		} 
	@Override
	public int compareTo(Course c) { return this.id - c.id; }
	@Override 
	public String toString() { 
		return String.format("#%d %s@%d ", id, name, price);
		} 
	public static class PriceComparator implements Comparator<Course>{
		@Override
		public int compare(Course c1, Course c2) { return c1.price - c2.price; }
		} 
	public static class NameComparator implements Comparator<Course>{ 
		@Override
		public int compare(Course c1, Course c2) {
			return c1.name.compareTo(c2.name); 
			} } 
	}



	
        }
        return minValue ;
                
            }      
            }