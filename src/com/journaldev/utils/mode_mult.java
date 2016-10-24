package com.journaldev.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/** 
 * @author Ferhat Yalçın
 */  
public class mode_mult {

	     List mode(double[] frm1t) {
	        HashMap<Double, Double> freqs = new HashMap<Double, Double>();
	        for (double d : frm1t) {
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
	        
	        return mode;
	    }
	
}
	


