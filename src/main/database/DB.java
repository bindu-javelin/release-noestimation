package main.database;

import main.classes.Issue;
import main.connection.HttpRequest;
import main.connection.Session;
import main.functions.GlobalFunctions;
import main.panels.MainFrame;
import main.parser.SessionParser;
import main.parser.StatusParser;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by ferhaty
 */
public class DB {
    static Connection connection;
    public static void main(String[] args){
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/jiraproject.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");
            String urlString = MainFrame.PROP.getProperty("BASE_URL")+"/rest/auth/1/session";
            String postJsonData = "{\"username\":\"\",\"password\":\"\"}";
            String response = HttpRequest.postRequest(urlString,postJsonData);
            String sessionID = SessionParser.getSessionID(response);
            Session.setSessionID(sessionID);
            ArrayList<String> statusList = StatusParser.parse(HttpRequest.getRequest(MainFrame.PROP.getProperty("BASE_URL")+"/rest/api/2/issuetype"));
            for(int i =0; i<statusList.size(); i++){
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO issue_types VALUES ('"+statusList.get(i)+"');";
                statement.executeUpdate(sql);
                statement.close();
                connection.commit();
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}


