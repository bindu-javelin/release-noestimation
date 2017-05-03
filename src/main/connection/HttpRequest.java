package main.connection;

import main.panels.MainFrame;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

public class HttpRequest {
    private static String TAG="HttpRequest.java";
    public static final String USER_AGENT = "Mozilla/5.0";

    public static String getRequest(String urlString){
        String sessionID = Session.getSessionID();
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //By default it is GET request
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("Cookie","JSESSIONID="+sessionID);
            int responseCode = con.getResponseCode();
            // Reading response from input Stream
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
            String output;
            StringBuffer response = new StringBuffer();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
            con.disconnect();
            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage().split("for")[0]);
            MainFrame.changePanel(MainFrame.CONFIGURATION_PANEL);
        }

        return null;
    }

    public static String postRequest(String urlString, String data){
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // Setting basic post request
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type","application/json");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(data);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            System.out.println(TAG + " \nSending 'POST' request to URL : " + url);
            System.out.println(TAG + " Post Data : " + data);
            System.out.println(TAG + " Response Code : " + responseCode);

            if(responseCode < 300){
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String output;
                StringBuffer response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();
                con.disconnect();
                return response.toString();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    
    public static String postRequestLogin(String urlString, String username, String password){
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // Setting basic post request
            byte[] encodedBytes = Base64.getEncoder().encode((username+":"+password).getBytes());
            con.setRequestProperty("Authorization", "Basic" + new String(encodedBytes));
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type","application/json");
            


            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            String data = "{\"username\":\""+username +"\",\"password\":\""+password +"\"}";
            wr.writeBytes(data);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            System.out.println(TAG + " \nSending 'POST' request to URL : " + url);
            System.out.println(TAG + " Post Data : " + data);
            System.out.println(TAG + " Response Code : " + responseCode);

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
                return response.toString();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

}



