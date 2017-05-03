package main.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ferhaty on 12/7/2016.
 */
public class StatusParser {
    public static ArrayList<String> parse(String jsonString){
        ArrayList<String> statusList = new ArrayList<>();
        try {
            JSONArray statusArray = new JSONArray(jsonString);
            for(int i =0; i<statusArray.length();i++){
                String statusName = statusArray.getJSONObject(i).getString("name");
                statusList.add(statusName);
            }
            return statusList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<Integer,ArrayList<String>> parseIssueType(String jsonString){
        HashMap<Integer,ArrayList<String>> hashMap = new HashMap<>();
        try {
            JSONArray statusArray = new JSONArray(jsonString);
            for(int i =0; i<statusArray.length();i++){
                ArrayList<String> issueTypeList = new ArrayList<>();
                String issueTypeName = statusArray.getJSONObject(i).getString("name");
                String iconUrl = statusArray.getJSONObject(i).getString("iconUrl");
                issueTypeList.add(issueTypeName);
                issueTypeList.add(iconUrl);
                hashMap.put(i,issueTypeList);
            }
            return hashMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
