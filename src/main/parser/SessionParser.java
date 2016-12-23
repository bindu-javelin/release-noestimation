package main.parser;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionParser {
    public static String getSessionID(String text){
        String sessionID = null;

        try {
            JSONObject jsonObj = new JSONObject(text);
            sessionID = jsonObj.getJSONObject("session").getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sessionID;
    }
}
