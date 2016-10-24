package com.journaldev.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class daydiff {
	public long daydiff(Object fila,Object fila2) throws ParseException{
		SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy");

		
    	
    	    Date date1 = myFormat.parse((String) fila);
    	    Date date2 = myFormat.parse((String) fila2);
    	    long diff = date2.getTime() - date1.getTime();
    	    //System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    	
    	 return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		
		
	}
/////////////////////////////////////////////////////////////////////////////////	
	public static String dateFormat(String strDate) throws Exception {
		 String ddate = null;
		 try {
		 
		//input date format
		 SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		 
		//output date format
		 SimpleDateFormat dFormatFinal = new SimpleDateFormat("dd-MMM-yyyy");
		 Date date = dFormat.parse(strDate);
		 ddate = dFormatFinal.format(date);
		 } catch (Exception e) {
		 throw new Exception("Invalid Date!!!!", e);
		 }
		 return ddate;
		 }
	
	///////////////////////////////////////////////////////////////////////////////
	public static String dateFormat2(String strDate) throws Exception {
		 String ddate = null;
		 try {
		 
		//input date format
		 SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd");
		 
		//output date format
		 SimpleDateFormat dFormatFinal = new SimpleDateFormat("dd-MMM");
		 Date date = dFormat.parse(strDate);
		 ddate = dFormatFinal.format(date);
		 } catch (Exception e) {
		 throw new Exception("Invalid Date!!!!", e);
		 }
		 return ddate;
		 }
	/////////////////////////////////////////////////////////////////////////////////
	public void dateDifferent(String d1,String d2){
		ArrayList<Date> dates = new ArrayList<Date>();
/*
		String str_date ="10 06 2016";
		String end_date ="25 07 2016";
*/
		String str_date=d1;
		String end_date=d2;
		
		
		DateFormat formatter ; 

		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date startDate = null;
		try {
			startDate = (Date)formatter.parse(str_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Date endDate = null;
		try {
			endDate = (Date)formatter.parse(end_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long interval = 24*1000 * 60 * 60; // 1 hour in millis
		long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
		    dates.add(new Date(curTime));
		    curTime += interval;
		}
		for(int i=0;i<dates.size();i++){
		    Date lDate =(Date)dates.get(i);
		    String ds = formatter.format(lDate);    
		    System.out.println(" Date is ..." + ds);
		}
		System.out.println(dates.size());
		
		
		
		
	}
	
	public int compare(String a,String b){
		int result=0;
		try{
		 
    		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        	Date date1 = sdf.parse(a);
        	Date date2 = sdf.parse(b);
        	
        	
/*			
        	System.out.println(sdf.format(date1));
        	System.out.println(sdf.format(date2));*/

        	if(date1.compareTo(date2)>0){
        		//System.out.println("Date1 is after Date2");
        		result=1;
        	}else if(date1.compareTo(date2)<0){
        		//System.out.println("Date1 is before Date2");
        		result=-1;
        	}else if(date1.compareTo(date2)==0){
        		//System.out.println("Date1 is equal to Date2");
        		result=0;
        	}

    	}catch(ParseException ex){
    		ex.printStackTrace();
    	}
		return result;
		
		
		
	}
	

}
