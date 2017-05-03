package main.database;

import main.classes.Issue;
import main.connection.HttpRequest;
import main.functions.GlobalFunctions;
import main.panels.MainFrame;
import main.parser.StatusParser;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by ferhaty
 */
public class DBHelper {
    static Connection connection;

    public static void init() {
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/jiraproject.db");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIssue(Issue issue, int projectId){
        try {
           // if(!isThereAnyIssues(projectId)){
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO issues VALUES ("+projectId+",'"+issue.getIssueKey()+"','"+GlobalFunctions.dateFormatString(issue.getCreatedDate())+"'," +
                        "'"+GlobalFunctions.dateFormatString(issue.getCompletedDate())+"',"+issue.getTimeCycle()+",'"+issue.getIssueTypeName()+"');";
                System.out.println(sql);
                statement.executeUpdate(sql);
                statement.close();
                connection.commit();
           // }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertStatuses(){
        try {
            Statement statement2 = connection.createStatement();
            String sql1 = "DELETE FROM status;";
            statement2.executeUpdate(sql1);
            ArrayList<String> statusList = StatusParser.parse(HttpRequest.getRequest(MainFrame.PROP.getProperty("BASE_URL")+"/rest/api/2/status"));
            statusList.addAll(StatusParser.parse(HttpRequest.getRequest(MainFrame.PROP.getProperty("BASE_URL")+"/rest/api/2/statuscategory")));

            for(int i =0; i<statusList.size(); i++){
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO status VALUES ('"+statusList.get(i)+"');";
                statement.executeUpdate(sql);
                statement.close();
                connection.commit();
            }
            // }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIssueType(){
        try {
            Statement statement2 = connection.createStatement();
            String sql1 = "DELETE FROM issue_types;";
            statement2.executeUpdate(sql1);

            ArrayList<String> statusList = StatusParser.parse(HttpRequest.getRequest(MainFrame.PROP.getProperty("BASE_URL")+"/rest/api/2/issuetype"));
            HashMap<Integer, ArrayList<String>> issueTypeHashMap= StatusParser.parseIssueType(HttpRequest.getRequest(MainFrame.PROP.getProperty("BASE_URL")+"/rest/api/2/issuetype"));

            for(int i =0; i<statusList.size(); i++){
                ArrayList<String> issueTypeList = issueTypeHashMap.get(i);
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO issue_types VALUES ('"+issueTypeList.get(0)+"','"+issueTypeList.get(1)+"');";
                statement.executeUpdate(sql);
                statement.close();
                connection.commit();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getIssueTypeIcon(String issueTypeName){
        String iconUrl= "";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT icon_url FROM issue_types where name='"+issueTypeName+"';" );
            if(rs.next()){
                iconUrl = rs.getString("icon_url");
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return iconUrl;
    }
    public static HashMap<String, String> getIssueTypeIconUrl(String selectedIssueType1){
        HashMap<String, String> iconUrlMap=new HashMap<String,String>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT iconUrl FROM issue_types where name="+selectedIssueType1+";;" );
            while(rs.next()){
                String issueType = rs.getString("name");
                String issueTypeIconUrl = rs.getString("iconUrl");
                iconUrlMap.put(issueType,issueTypeIconUrl);
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return iconUrlMap;
    }


    public static ArrayList<Issue> getIssues(int projectId){
        ArrayList<Issue> issueList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM issues WHERE project_id ="+projectId+";" );
            while(rs.next()){
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                Issue issue = new Issue();
                String issueKey = rs.getString("issue_key");
                Date createdDate = dateFormat.parse(rs.getString("created_date"));
                Date completedDate =  dateFormat.parse(rs.getString("completed_date"));
                int timeCycle = rs.getInt("time_cycle");
                String issueType = rs.getString("issue_type");
                issue.setIssueKey(issueKey);
                issue.setCreatedDate(createdDate);
                issue.setCompletedDate(completedDate);
                issue.setTimeCycle(timeCycle);
                issue.setIssueTypeName(issueType);
                issueList.add(issue);
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return issueList;
    }

    public static ArrayList<Issue> getIssuesType1(String selectedIssueType1){
        ArrayList<Issue> issueType1List = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM issues  WHERE issue_type ='"+selectedIssueType1+"';" );
            while(rs.next()){
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                Issue issue = new Issue();
                String issueKey = rs.getString("issue_key");
                Date createdDate = dateFormat.parse(rs.getString("created_date"));
                Date completedDate =  dateFormat.parse(rs.getString("completed_date"));
                int timeCycle = rs.getInt("time_cycle");
                String issueType = rs.getString("issue_type");
                issue.setIssueKey(issueKey);
                issue.setCreatedDate(createdDate);
                issue.setCompletedDate(completedDate);
                issue.setTimeCycle(timeCycle);
                issue.setIssueTypeName(issueType);
                issueType1List.add(issue);
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return issueType1List;
    }

    public static ArrayList<Issue> getIssuesType2(String selectedIssueType2){
        ArrayList<Issue> issueType2List = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM issues  WHERE issue_type ='"+selectedIssueType2+"';");
            while(rs.next()){
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                Issue issue = new Issue();
                String issueKey = rs.getString("issue_key");
                Date createdDate = dateFormat.parse(rs.getString("created_date"));
                Date completedDate =  dateFormat.parse(rs.getString("completed_date"));
                int timeCycle = rs.getInt("time_cycle");
                String issueType = rs.getString("issue_type");
                issue.setIssueKey(issueKey);
                issue.setCreatedDate(createdDate);
                issue.setCompletedDate(completedDate);
                issue.setTimeCycle(timeCycle);
                issue.setIssueTypeName(issueType);
                issueType2List.add(issue);
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return issueType2List;
    }

    public static boolean isThereAnyIssues(String projectName,String fromToString){
        try {
            int projectId = getProjectId(projectName, fromToString);
            Statement statement = connection.createStatement();

            if(projectId!=-1){
                ResultSet rs = statement.executeQuery( "SELECT * FROM issues WHERE project_id ="+projectId+";" );
                if(rs.next()){
                    rs.close();
                    statement.close();
                    return true;
                } else{
                    rs.close();
                    statement.close();
                    return  false;
                }
            } else{
                statement.close();
                return  false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<String> getStatus(){
        ArrayList<String> statusList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM status;" );
            while(rs.next()){
                String statusName = rs.getString("name");
                statusList.add(statusName);
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return statusList;
    }

    public static ArrayList<String> getIssueTypes(){
        ArrayList<String> issueTypeList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM issue_types;" );
            while(rs.next()){
                String issueType = rs.getString("name");
                issueTypeList.add(issueType);
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return issueTypeList;
    }

    public static void insertProject(String issueURL, String fromToString){
        int id = lastProjectId()+1;
        try {
            long lastPullingDate = System.currentTimeMillis();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO projects VALUES ("+id+",'"+issueURL+"','"+fromToString+"',"+lastPullingDate+");";
            statement.executeUpdate(sql);
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean isThereProjectName(String issueURL, String fromToString){
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM projects WHERE project_name ='"+issueURL+"' AND fromToString = '"+fromToString+"';" );
            if(rs.next()) {
                rs.close();
                statement.close();
                return true;
            } else{
                rs.close();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  false;
    }

    public static int lastProjectId(){
        try {
            int lastProjectId =0;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM projects;" );
            while(rs.next()) {
                lastProjectId = rs.getInt("id");
            }
            return  lastProjectId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getProjectId(String projectName, String fromToString){
        try {
            int projectId;
            Statement statement = connection.createStatement();
            long currentDate = System.currentTimeMillis();
            currentDate -= 1000*60*60*24*Integer.valueOf(MainFrame.PROP.getProperty("LAST_PULL"));
            ResultSet rs = statement.executeQuery( "SELECT * FROM projects WHERE project_name ='"+projectName+"' AND fromToString ='"+fromToString+"';" );
            if(rs.next()) {
                Statement statementForDate = connection.createStatement();
                ResultSet rs2 = statementForDate.executeQuery( "SELECT * FROM projects WHERE project_name ='"+projectName+"' AND fromToString ='"+fromToString+"' AND created_date >"+currentDate+";" );
                projectId = rs.getInt("id");
                rs.close();
                statement.close();
                if(rs2.next()) {
                    rs2.close();
                    statementForDate.close();
                    return projectId;
                } else {
                    Statement statement2 = connection.createStatement();
                    String sql = "DELETE FROM projects WHERE id = "+projectId+" ;";
                    statement2.executeUpdate(sql);
                    statement2 = connection.createStatement();
                    sql = "DELETE FROM issues WHERE project_id = "+projectId+" ;";
                    statement2.executeUpdate(sql);
                    statement2.close();
                    rs2.close();
                    statementForDate.close();
                    return -1;
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void deleteAll(){

        try {
            Statement statement2 = connection.createStatement();
            String sql = "DELETE FROM projects;";
            statement2.executeUpdate(sql);
            statement2 = connection.createStatement();
            sql = "DELETE FROM issues;";
            statement2.executeUpdate(sql);
            statement2.close();
            connection.commit();
            System.out.println("Database deleted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
