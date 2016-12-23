package main.connection;

public class Session {
    public static String sessionID;

    public static String getSessionID() {
        return sessionID;
    }

    public static void setSessionID(String sessionID) {
        Session.sessionID = sessionID;
    }
}
