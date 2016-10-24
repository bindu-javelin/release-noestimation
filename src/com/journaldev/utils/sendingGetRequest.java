package com.journaldev.utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.*;

import com.google.gson.Gson;
/** 
 * @author Ferhat Yalçın
 */  

public class sendingGetRequest {
public String session;
String result;
JSONObject obj;
JSONArray issues;
String key;
String[] created;
String[] updated;
String started;
JSONArray fields;
int key_count=0;
int s_count=0;




public sendingGetRequest() {
	
}
	 public static final String USER_AGENT = "Mozilla/5.0";

	// HTTP GET request  
	 public String sendingGetRequest(String urlString,Object session2) throws Exception {  
	  this.session=(String) session2;
	   
	    
	  URL url = new URL(urlString);  
	  HttpURLConnection con = (HttpURLConnection) url.openConnection();  
	  //By default it is GET request  
	  con.setRequestMethod("GET"); 
	  //add request header  
	 con.setRequestProperty("User-Agent", USER_AGENT);  
	 con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");  
	 con.setRequestProperty("Content-Type","application/json");
	 System.out.println("Session ID => "+ session);
	 con.setRequestProperty("Cookie","JSESSIONID="+session);
	 
	 
	 
	  
	  int responseCode = con.getResponseCode();  
	  System.out.println("Sending get request : "+ url);  
	  
	  // Reading response from input Stream  
	  BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
	  String output;  
	  StringBuffer response = new StringBuffer();  
	  
	  while ((output = in.readLine()) != null) {  
	   response.append(output);  
	  }  
	  in.close();  
	  
	  //printing result from response  
	 //System.out.println(response.toString());  
	 System.out.println("/////////////////////////////////////////////////////////////////////////////////");  
	
	 
     obj = new JSONObject(response.toString());
	 issues = obj.getJSONArray("issues");
	  
	 
	 
	  con.disconnect();
	  return response.toString();
	  
	 }  
	 
	 
	 public int  getid() throws JSONException{
		 
		 for(int i=0; i < issues.length();i++){
				key = issues.getJSONObject(i).getString("key");
				key_count++;
				//System.out.println(key_count+".Key ---> "+key);
				
				 
			}
		 
		return key_count;
		 
		 
	 }
	 
	
public String[] getCreated() throws JSONException{
		 
	int c_count=0;
	String[] dizi = new String[issues.length()];  
	String[] dizi2 = new String[issues.length()];  
	created = new String[issues.length()];  
	updated = new String[issues.length()]; 
	for(int i=0; i < issues.length();i++){
		created[i]=obj.getJSONArray("issues").getJSONObject(i).getJSONObject("fields").getString("created");
		dizi[i]=created[i];
	
	}
		return dizi;
		 
		 
	 }
	 
			 

}
