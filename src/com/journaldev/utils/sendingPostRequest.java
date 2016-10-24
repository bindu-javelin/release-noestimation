package com.journaldev.utils;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
/** 
 * @author Ferhat Yalçın
 */  

public class sendingPostRequest {
	public static final String USER_AGENT = "Mozilla/5.0";
	private static String Username;
	private static String Password;
	public String[] responseArr= new String[2];
	public String sessionValue;
	public static boolean control;

	public sendingPostRequest(String user, String pass) {
		 this.setUsername(user);
		 this.setPassword(pass);
		
	}
	

	
	// HTTP Post request  
	 public  String[] sendingPostRequest1() throws Exception {  
		
		 
	  String url = "<<<base url>>>";  
	  URL obj = new URL(url);  
	  HttpURLConnection con = (HttpURLConnection) obj.openConnection();  
	  
	        // Setting basic post request  
	  con.setRequestMethod("POST");  
	  con.setRequestProperty("User-Agent", USER_AGENT);  
	  con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");  
	  con.setRequestProperty("Content-Type","application/json");  
	  
	
	 
	 String postJsonData = "{<<<data>>>}";  
	  
	 //String[] responseArr= new String[2];
	  
	  try{
		  
		// Send post request  
		  con.setDoOutput(true);  
		  DataOutputStream wr = new DataOutputStream(con.getOutputStream());  
		  wr.writeBytes(postJsonData);  
		  wr.flush();  
		  wr.close();  
		  
		  int responseCode = con.getResponseCode();  
		  
		  System.out.println("\nSending 'POST' request to URL : " + url);  
		  System.out.println("Post Data : " + postJsonData);  
		  System.out.println("Response Code : " + responseCode); 
		  
		  
		 
		  if(responseCode < 300){
			  BufferedReader in = new BufferedReader(  
			          new InputStreamReader(con.getInputStream()));  
			  String output;  
			  StringBuffer response = new StringBuffer();  
			  
			  while ((output = in.readLine()) != null) {  
			   response.append(output);  
			  }  
			  in.close();  
			  con.disconnect();
			
			  JSONObject jsonObj = new JSONObject(response.toString());
			  sessionValue = jsonObj.getJSONObject("session").getString("value");
			
		
			  
			  
		  }
		  		responseArr[0]=String.valueOf(responseCode);
		  		responseArr[1]= sessionValue;
		  		
		  		
		  
		  
		 return responseArr;
	  }catch(IOException e){
		  responseArr[0]= "400";

		  return responseArr;
	  }
	 }


	public static String getUsername() {
		return Username;
	}


	public static void setUsername(String username) {
		Username = username;
	}


	public static String getPassword() {
		return Password;
	}


	public static void setPassword(String password) {
		Password = password;
	}  
	
	

}
