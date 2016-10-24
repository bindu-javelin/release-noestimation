package com.journaldev.utils;

import java.io.BufferedReader;  
import java.io.DataOutputStream;  
import java.io.InputStream;
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.URL;  
import java.nio.charset.Charset;
import java.sql.Timestamp;

import org.json.*;
/** 
 * @author Ferhat Yalçın
 */  
public class Connection { 
	static JSONObject changelog= new JSONObject();
	static JSONArray histories=new JSONArray();
	static JSONArray items=new JSONArray();
	static String createdlast;
	static Object[] completed_created;
	static JSONArray issues= new JSONArray();
	static String fromString,toString;
    public final String USER_AGENT = "Mozilla/5.0";  
 
    public static String user;
    public static String pass;
    public static String[] responseArr;
    public static boolean rcode;
    public Connection(String username, String password) {
    	user=username;
    	pass=password;
	}

    public static JSONArray connection() throws Exception {  
	
	  sendingGetRequest sendGet=new sendingGetRequest();
	
	   sendingPostRequest sendPost=new sendingPostRequest(user, pass);
	  sendPost.sendingPostRequest1();
	
	  
	 try {
			
			responseArr = sendPost.sendingPostRequest1();
			rcode=responseArr[0].equals("200");
			System.out.println("------------->"+rcode);
			if(responseArr[0].equals("200")){
				System.out.println("Başarılı");
				
				java.util.Date date= new java.util.Date();
				  System.out.println("post başlangıcı :-->"+new Timestamp(date.getTime()));
				String jsonString = sendGet.sendingGetRequest("---",session);
				 java.util.Date date1= new java.util.Date();
				  System.out.println("post bitişi :-->"+new Timestamp(date1.getTime()));
	
				JSONObject obj = new JSONObject(jsonString);
				
				issues= obj.getJSONArray("issues");
				
				
			
				
			}else{
				System.out.println("Hata");
			}
			} catch (Exception e1) {
		
			e1.printStackTrace();
		}
	return issues;
	
		
		




	
	}  
   
}  