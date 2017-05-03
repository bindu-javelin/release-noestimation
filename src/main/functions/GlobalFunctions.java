package main.functions;
import main.classes.Throughput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
/**
 * Created by ferhaty
 */
public class GlobalFunctions {

    public static int dayDiff(Date started, Date completed) {
        long diff = completed.getTime() - started.getTime();
        if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) == 0)
            return 1;
        else
            return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

    }

    public static Date dateFormat(String strDate) {
        Date date = null;
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dFormatFinal = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date parseDate = dFormat.parse(strDate);
            String dateString = dFormatFinal.format(parseDate);
            date = dFormatFinal.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateFormatString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.format(date);
    }

    public static ArrayList CollectionsSort(ArrayList list) {
        Collections.sort(list, new Comparator<Date>() {
            @Override
            public int compare(Date lhs, Date rhs) {
                if (lhs.getTime() < rhs.getTime())
                    return -1;
                else if (lhs.getTime() == rhs.getTime())
                    return 0;
                else
                    return 1;
            }
        });
        return list;
    }

    public static ArrayList ThroughputSort(ArrayList DataList) {
        Collections.sort(DataList, new Comparator() {
            public int compare(Object o1, Object o2) {
                Throughput p1 = (Throughput) o1;
                Throughput p2 = (Throughput) o2;
                int ret = -1;
                //business logic here
                if (p1.getDoneDate().getTime() == p2.getDoneDate().getTime()) {
                    ret = 0;
                } else if (p1.getDoneDate().getTime() > p2.getDoneDate().getTime()) {
                    ret = 1;
                } else if (p1.getDoneDate().getTime() < p2.getDoneDate().getTime()) {
                    ret = -1;
                }//end business logic
                return ret;
            }
        });
        return DataList;
    }

    public static double[] intTodouble(int [] intarray){
        double[] doubleArray=new double[intarray.length];

        for(int i=0;i<intarray.length;i++){

            doubleArray[i]=intarray[i];
        }

        return doubleArray;
    }

    public static double[] listToArray(ArrayList list){
        double temp[]=new double[list.size()];

        for(int i=0;i<list.size();i++){

            temp[i]=(double)list.get(i);
        }
        return temp;
    }

}



















/*
    public static ArrayList DeliveryTimeSort(ArrayList list) {
        Collections.sort(list, new Comparator() {

            public int compare(Object o1, Object o2) {
                DeliveryTimeSimulation p1 = (DeliveryTimeSimulation) o1;
                DeliveryTimeSimulation p2 = (DeliveryTimeSimulation) o2;
                int ret = -1;
                //business logic here
                if (p1.getDeliveryTime() == p2.getDeliveryTime()) {
                    ret = 0;
                } else if (p1.getDeliveryTime() > p2.getDeliveryTime()) {
                    ret = 1;
                } else if (p1.getDeliveryTime() < p2.getDeliveryTime()) {
                    ret = -1;
                }//end business logic
                return ret;
            }
        });
        return list;
    }

    ////////}/////////////////////////////////////////////////////////////////
    public static ArrayList CollectionsSort2(ArrayList DataList) {


        Collections.sort(DataList, new Comparator() {

            public int compare(Object o1, Object o2) {
                Throughput p1 = (Throughput) o1;
                Throughput p2 = (Throughput) o2;
                int ret = -1;
                //business logic here
                if (p1.getDoneDate().getTime() == p2.getDoneDate().getTime()) {
                    ret = 0;
                } else if (p1.getDoneDate().getTime() > p2.getDoneDate().getTime()) {
                    ret = 1;
                } else if (p1.getDoneDate().getTime() < p2.getDoneDate().getTime()) {
                    ret = -1;
                }//end business logic
                return ret;
            }
        });
        return DataList;
    }

    public static int getMax(int[][] b){
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

    public static int getMin(int[][] b){
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

    public static int getIndexOfMin(ArrayList<DeliveryTimeSimulation> data) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < data.size(); i++) {
            int f = data.get(i).getDeliveryTime();
            if (Integer.compare(f, min) < 0) {
                min = (int) f;
                index = i;
            }
        }
        return index;
    }


    public static int getMaxCollection(ArrayList<DeliveryTimeSimulation> list){
        int max = list.get(0).getDeliveryTime();
        for(int i=0; i<list.size(); i++){
            if( list.get(i).getDeliveryTime() > max){
                max = list.get(i).getDeliveryTime();
            }
        }
        return max;
    }
    public static int getMinCollection(ArrayList<DeliveryTimeSimulation> list){
        int min = list.get(0).getDeliveryTime();
        for(int i=0; i<list.size(); i++){
            if( list.get(i).getDeliveryTime() < min){
                min = list.get(i).getDeliveryTime();
            }
        }
        return min;
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

*/



