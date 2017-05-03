package main.database;

import main.classes.Issue;
import main.classes.Selection;
import main.connection.HttpRequest;
import main.functions.GlobalFunctions;
import main.panels.MainFrame;
import main.parser.StatusParser;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DBHelper {
    
    private Connection getConnection(){
    	try {
        	if(connection==null || connection.isClosed())
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/jiraproject.db");
                    connection.setAutoCommit(false);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
		} catch (Exception e) {
			e.printStackTrace();
		}
         return connection;
    }
    
    private void closeConnection(){
    	if(connection!=null) 
    		try {
    			connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    }

    private static Connection connection=null;
    private static DBHelper dbHelper = null;
    public static DBHelper getInstance(){
    	if(dbHelper!=null){
    		return dbHelper;
    	}
    	else {
    		dbHelper = new DBHelper();
    		return dbHelper;
    	}
    }

    public void insertIssue(Issue issue, int projectId){
        try {
                Statement statement = getConnection().createStatement();
                String sql = "INSERT INTO issues(project_id,issue_key,created_date,completed_Date,time_cycle,issue_type) VALUES ("+projectId+",'"+issue.getIssueKey()+"','"+GlobalFunctions.dateFormatString(issue.getCreatedDate())+"'," +
                        "'"+GlobalFunctions.dateFormatString(issue.getCompletedDate())+"',"+issue.getTimeCycle()+",'"+issue.getIssueTypeName()+"');";
                System.out.println(sql);
                statement.executeUpdate(sql);
                statement.close();
                getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
    }

    public void insertIssueTypeList(int projectId, String issue){
        try {
                Statement statement = getConnection().createStatement();
                String sql = "INSERT INTO issue_type_list VALUES ("+projectId+",'"+issue+"');";
                System.out.println(sql);
                statement.executeUpdate(sql);
                statement.close();
                getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
    }

    public void insertStatuses(){
        try {
            Statement statement2 = getConnection().createStatement();
            String sql1 = "DELETE FROM status;";
            statement2.executeUpdate(sql1);
            ArrayList<String> statusList = StatusParser.parse(HttpRequest.getRequest(MainFrame.PROP.getProperty("BASE_URL")+"/rest/api/2/status"));
            statusList.addAll(StatusParser.parse(HttpRequest.getRequest(MainFrame.PROP.getProperty("BASE_URL")+"/rest/api/2/statuscategory")));

            for(int i =0; i<statusList.size(); i++){
                Statement statement = getConnection().createStatement();
                String sql = "INSERT INTO status VALUES ('"+statusList.get(i)+"');";
                statement.executeUpdate(sql);
                statement.close();
                getConnection().commit();
            }
            // }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
    }

    public void insertIssueType(){
        try {
            Statement statement2 = getConnection().createStatement();
            String sql1 = "DELETE FROM issue_types;";
            statement2.executeUpdate(sql1);

            ArrayList<String> statusList = StatusParser.parse(HttpRequest.getRequest(MainFrame.PROP.getProperty("BASE_URL")+"/rest/api/2/issuetype"));
            HashMap<Integer, ArrayList<String>> issueTypeHashMap= StatusParser.parseIssueType(HttpRequest.getRequest(MainFrame.PROP.getProperty("BASE_URL")+"/rest/api/2/issuetype"));

            for(int i =0; i<statusList.size(); i++){
                ArrayList<String> issueTypeList = issueTypeHashMap.get(i);
                Statement statement = getConnection().createStatement();
                String sql = "INSERT INTO issue_types VALUES ('"+issueTypeList.get(0)+"','"+issueTypeList.get(1)+"');";
                statement.executeUpdate(sql);
                statement.close();
                getConnection().commit();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
    }

    public String getIssueTypeIcon(String issueTypeName){
        String iconUrl= "";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery( "SELECT icon_url FROM issue_types where name='"+issueTypeName+"';" );
            if(rs.next()){
                iconUrl = rs.getString("icon_url");
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
        return iconUrl;
    }
    public HashMap<String, String> getIssueTypeIconUrl(String selectedIssueType1){
        HashMap<String, String> iconUrlMap=new HashMap<String,String>();
        try {
            Statement statement = getConnection().createStatement();
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
        finally {
			closeConnection();
		}
        return iconUrlMap;
    }

    public static final String nullSelection = "-- Any --";
    public ArrayList<Issue> getIssues(int projectId, Selection selection){
        ArrayList<Issue> issueList = new ArrayList<>();

        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM issues WHERE project_id ="+projectId+" and issue_type='"+selection.getIssueType()+"';");
           // System.out.println("2"+"SELECT * FROM issues WHERE project_id ="+projectId+" and issue_type='"+selection.getIssueType()+"';");
            while(rs.next()){
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                Issue issue = new Issue();
                String issueKey = rs.getString("issue_key");
                int issueId = rs.getInt("issueId");
                Date createdDate = dateFormat.parse(rs.getString("created_date"));
                Date completedDate =  dateFormat.parse(rs.getString("completed_date"));
                int timeCycle = rs.getInt("time_cycle");
                String issueType = rs.getString("issue_type");
                issue.setIssueKey(issueKey);
                issue.setId(issueId);
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
        finally {
			closeConnection();
		}

        String selectClause="",whereClause="";
        boolean filter = false;
    	ArrayList<Issue> temp = new ArrayList<>();
        
        if(selection.getStartedFrom().equals(nullSelection) && selection.getStartedTo().equals(nullSelection)){
        	selectClause="min";
        	whereClause = "";
        }
        else if(selection.getStartedFrom().equals(nullSelection)){
        	selectClause="min";
        	whereClause = "and ifh.toString='"+selection.getStartedTo()+"'";
        }
        else if(selection.getStartedTo().equals(nullSelection)){
        	selectClause="min";
        	whereClause = "and ifh.fromString='"+selection.getStartedFrom()+"'";
        	filter = true;
        }
        else{
        	selectClause="min";
        	whereClause = "and ifh.toString='"+selection.getStartedTo()+"' and " + "ifh.fromString='"+selection.getStartedFrom()+"'";
        	filter = true;
        }

        int x=0,y=0;
        if(filter){
	    	System.out.println("1)Checking: from: "+selection.getStartedFrom()+" ,to: " +selection.getStartedTo());
        	
	    	long date;
	    	for (int i = 0; i < issueList.size(); i++) {
				date = getDate(issueList.get(i).getId(),selectClause,whereClause);
				x++;
				//System.out.println(issueList.get(i).getIssueKey()+"----->"+date);
	    		if(date>0){
	    			y++;
	    			issueList.get(i).setCreatedDate(GlobalFunctions.dateFormat(date));
					temp.add(issueList.get(i));
				}
	    		issueList.get(i).setTimeCycle(GlobalFunctions.dayDiff(issueList.get(i).getCreatedDate(), issueList.get(i).getCompletedDate()));
			}
	    	System.out.println("1)Total checked item:"+x+" for " + selection.getIssueType());
	    	System.out.println("1)Geting item:"+y);
	    	issueList = new ArrayList<>();
	    	for (int i = 0; i < temp.size(); i++) {
				issueList.add(temp.get(i));
			}
	    	filter = false;
        }
        
        
        

        if(selection.getCompletedFrom().equals(nullSelection) && selection.getCompletedTo().equals(nullSelection)){
        	selectClause="max";
        	whereClause = "";
        }
        else if(selection.getCompletedFrom().equals(nullSelection)){
        	selectClause="min";
        	whereClause = "and ifh.toString='"+selection.getCompletedTo()+"'";
        	filter = true;
        }
        else if(selection.getCompletedTo().equals(nullSelection)){
        	selectClause="min";
        	whereClause = "and ifh.fromString='"+selection.getCompletedFrom()+"'";
        	filter = true;
        }
        else{
        	selectClause="min";
        	whereClause = "and ifh.toString='"+selection.getCompletedTo()+"' and " + "ifh.fromString='"+selection.getCompletedFrom()+"'";
        	filter = true;
        }
        
        if(filter){
        	temp = new ArrayList<>();
        	x=0; y=0;
	    	System.out.println("2)Checking: from: "+selection.getCompletedFrom()+" ,to: " +selection.getCompletedTo());
        	long date;
        	for (int i = 0; i < issueList.size(); i++) {
				date = getDate(issueList.get(i).getId(),selectClause,whereClause);
				x++;
        		if(date>0){
        			y++;
        			issueList.get(i).setCompletedDate(GlobalFunctions.dateFormat(date));
					temp.add(issueList.get(i));
				}
	    		issueList.get(i).setTimeCycle(GlobalFunctions.dayDiff(issueList.get(i).getCreatedDate(), issueList.get(i).getCompletedDate()));
			}
	    	System.out.println("2)Total checked item:"+x);
	    	System.out.println("2)Geting item:"+y);
	    	issueList = new ArrayList<>();
	    	for (int i = 0; i < temp.size(); i++) {
				issueList.add(temp.get(i));
			}
        }
        
        
        return issueList;
    }
    
    public long getDate(int issueId, String selectClause, String whereClause){
    	long date=-1;
        try {
        	
        	String sql = "select * from histories as h INNER JOIN itemsForHistories as ifh on h.historyId = ifh.historyId where h.issueId="+issueId+" " + whereClause;
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
        	//System.out.println(sql);
            
            if(rs.next()){
            	sql = "select "+selectClause+"(hDate) as D from histories as h INNER JOIN itemsForHistories as ifh on h.historyId = ifh.historyId where h.issueId="+issueId+" " + whereClause;
            	//System.out.println(sql);
            	Statement statement2 = getConnection().createStatement();
                ResultSet rs2 = statement.executeQuery(sql);
            	rs2 = statement2.executeQuery(sql);
            	if(rs.next())
            		date = rs.getLong("D");
                rs2.close();
                statement2.close();
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
        return date;
    }

    public ArrayList<Issue> optimizeDays(ArrayList<Issue> list,int A){
    	return list;
    }
    private final int max_days = 30;
    public ArrayList<Issue> optimizeDays(ArrayList<Issue> list){
    	 for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getTimeCycle()>max_days){
				long[] dates = getHistoriesDate(list.get(i));
				if(dates==null) continue;
				for (int j = 0; j < dates.length-1; j++) {
					
					int diff = GlobalFunctions.dayDiff(dates[j],dates[j+1]);
					if(diff > max_days){
						System.out.println("Opt. IssueKey:"+list.get(i).getIssueKey() + " TimeCyle:"+list.get(i).getTimeCycle()+"---Deleted:"+diff+"---Diff:"+ ((list.get(i).getTimeCycle()-diff)));
						list.get(i).setTimeCycle(list.get(i).getTimeCycle()-diff);
					}
					
				}
				/*Calendar cal = Calendar.getInstance();
				cal.setTime(list.get(i).getCompletedDate());
				cal.add(Calendar.DAY_OF_YEAR, list.get(i).getTimeCycle() * -1 );
				//System.out.println("--IssueKey:"+list.get(i).getIssueKey() + " TimeCyle:" + list.get(i).getTimeCycle());
				Date newCreatedDate = cal.getTime();
				list.get(i).setCreatedDate(newCreatedDate);*/

				Calendar cal = Calendar.getInstance();
				cal.setTime(list.get(i).getCreatedDate());
				cal.add(Calendar.DAY_OF_YEAR, list.get(i).getTimeCycle());
				//System.out.println("--IssueKey:"+list.get(i).getIssueKey() + " TimeCyle:" + list.get(i).getTimeCycle());
				Date newCreatedDate = cal.getTime();
				list.get(i).setCompletedDate(newCreatedDate);
				
			}
		}
    	return list;
    }
    
    public long[] getHistoriesDate(Issue issue) {
    	long[] dates = null;
         try {
             Statement statement = getConnection().createStatement();
             ResultSet rs = statement.executeQuery( "select count(hDate) as c from histories h INNER JOIN itemsForHistories i on h.historyId = i.historyId where hDate>="+ issue.getCreatedDate().getTime()  +" and hDate<= "+issue.getCompletedDate().getTime()+"  and h.issueId="+issue.getId()+" order by hDate;" );
             int count = 0;
             if(rs.next())
            	 count = rs.getInt("c");

             if(count>0){

                 Statement statement2 = getConnection().createStatement();
                 ResultSet rs2 = statement2.executeQuery( "select hDate as d from histories h INNER JOIN itemsForHistories i on h.historyId = i.historyId where hDate>="+ issue.getCreatedDate().getTime()  +" and hDate<= "+issue.getCompletedDate().getTime()+"  and h.issueId="+issue.getId()+" order by hDate asc;" );
             	
            	 dates = new long[count];
            	 int i = 0;
	             while(rs2.next()){
	                 dates[i] = rs2.getLong("d");
	                 i++;
	             }
	             rs2.close();
	             statement2.close();
             }
             rs.close();
             statement.close();
         }catch (SQLException e) {
             e.printStackTrace();
         }
         finally {
 			closeConnection();
 		}
    	 //System.out.println("--------->"+dates[1]);
    	return dates;
    }
    
    public void insertItemForHistroies(int historyId, String fromString, String toString){
        try {
        	PreparedStatement statement = getConnection().prepareStatement("INSERT INTO itemsForHistories(historyId,fromString,toString) VALUES (?,?,?);");
        	
            //String sql = "INSERT INTO itemsForHistories(historyId,fromString,toString) VALUES ("+historyId+",'"+fromString+"','"+toString+"');";
        	
            statement.setInt(1, historyId);
            statement.setString(2, fromString);
            statement.setString(3, toString);
            
            statement.executeUpdate();
            statement.close();
            getConnection().commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
    }

    public void insertHistory(int issueId, String date){
        try {
            //Statement statement = connection.createStatement();
            //String sql = "INSERT INTO histories(issueId,hDate) VALUES ("+issueId+","+GlobalFunctions.getTimeStamp(date)+");";
            
            PreparedStatement statement = getConnection().prepareStatement("INSERT INTO histories(issueId,hDate) VALUES (?,?)");
            statement.setInt(1, issueId);
            statement.setLong(2, GlobalFunctions.getTimeStamp(date));

            statement.executeUpdate();
            statement.close();
            getConnection().commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
    }
    


    public boolean isThereIssueTypeList(int projectId, String issue){
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM issue_type_list where project_id="+projectId+" and issue='"+issue+"';" );
            if(rs.next()){
                rs.close();
                statement.close();
                return true;
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
        return false;
    }
    
    public boolean isThereStatus(String statusName){
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT name FROM status where name = ?;");
            statement.setString(1, statusName);
            ResultSet rs = statement.executeQuery(  );
            if(rs.next()){
                rs.close();
                statement.close();
            	System.out.println("---->Status name:" + statusName +" added");
                return true;
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
    	System.out.println("---->Status name:" + statusName +" deleted");
        return false;
    }

    public boolean isThereAnyIssues(String projectName){
        try {
            int projectId = getProjectId(projectName);
            Statement statement = getConnection().createStatement();

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
        finally {
			closeConnection();
		}
        return false;
    }

    public ArrayList<String> getStatus(){
        ArrayList<String> statusList = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM status ORDER BY name;" );
            while(rs.next()){
                String statusName = rs.getString("name");
                statusList.add(statusName);
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
        return statusList;
    }

    public ArrayList<String> getIssueTypes(){
        ArrayList<String> issueTypeList = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM issue_types" );
            while(rs.next()){
                String issueType = rs.getString("name");
                issueTypeList.add(issueType);
            }
            rs.close();
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
        return issueTypeList;
    }

    public boolean insertProject(String issueUrl){
        //int id = lastProjectId()+1;
        try {
        	if(getProjectId(issueUrl)==-1){
	            long lastPullingDate = System.currentTimeMillis();
	            Statement statement = getConnection().createStatement();
	            String sql = "INSERT INTO projects(project_name,created_date) VALUES ('"+issueUrl+"',"+lastPullingDate+");";
	            statement.executeUpdate(sql);
	            statement.close();
	            getConnection().commit();
        		System.out.println("Created new project: " + issueUrl);
        		closeConnection();
        		return true;
        	}
        	else {
        		System.out.println("Database Cache Time calculating...");
        		long currentDate = System.currentTimeMillis();
        		long projectDate = getProjectDate(issueUrl);
        		int CACHE_TIME = 60*1000*Integer.valueOf(MainFrame.PROP.getProperty("CACHE_TIME"));

        		System.out.println("Current Time: "+currentDate);
        		System.out.println("Project Time: "+projectDate);
        		System.out.println("Creating new project: " + ((((projectDate+CACHE_TIME) < currentDate))? "true" : "false"));
        		
        		if((projectDate+CACHE_TIME) < currentDate) {
        			deleteProject(issueUrl);
            		return insertProject(issueUrl);
        		}
        		return false;
        	}
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    public void deleteProject(String projectName){
    	int id=getProjectId(projectName);
    	try {
            Statement statement2 = getConnection().createStatement();
            
            String sql = "DELETE FROM itemsForHistories WHERE historyId IN ( SELECT historyId from histories where issueId IN ( SELECT issueId from issues where project_Id="+id+" ) );";
            statement2.executeUpdate(sql);
            statement2 = getConnection().createStatement();
            
            sql = "DELETE FROM histories where issueId IN ( SELECT issueId from issues where project_Id="+id+" );";
            statement2.executeUpdate(sql);
            statement2 = getConnection().createStatement();
            
            sql = "DELETE FROM projects WHERE id = "+id+" ;";
            statement2.executeUpdate(sql);
            statement2 = getConnection().createStatement();
            
            sql = "DELETE FROM issues WHERE project_id = "+id+" ;";
            statement2.executeUpdate(sql);
            statement2.close();
            
            sql = "DELETE FROM issue_type_list WHERE project_id = "+id+" ;";
            statement2.executeUpdate(sql);
            statement2.close();
            
            getConnection().commit();
            
            System.out.println("Deleted project: " +projectName );
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
        finally {
			closeConnection();
		}
    }

    public boolean isThereProjectName(String issueURL, String fromToString){
        try {
            Statement statement = getConnection().createStatement();
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
        finally {
			closeConnection();
		}
        return  false;
    }
    
    public boolean isThereIssueType(String projectName, String issueType){
        try {
        	int pId = getProjectId(projectName);
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM issues WHERE issue_type ='"+issueType+"' AND project_id = "+pId+";" );
            if(rs.next()) {
                rs.close();
                statement.close();
                closeConnection();
                return true;
            } else{
                rs.close();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
        return  false;
    }

    public int lastProjectId(){
        try {
            int lastProjectId =0;
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM projects;" );
            while(rs.next()) {
                lastProjectId = rs.getInt("id");
            }
            closeConnection();
            return  lastProjectId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
        return -1;
    }

    public int lastHistoryId(){
        try {
            int lastHistoryId = 0;
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery( "SELECT historyId FROM histories;" );
            while(rs.next()) {
            	lastHistoryId = rs.getInt("historyId");
            }
            closeConnection();
            return  lastHistoryId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
        return -1;
    }
    public int lastIssueId(){
        try {
            int lastIssueId =0;
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery( "SELECT issueId FROM issues;" );
            while(rs.next()) {
            	lastIssueId = rs.getInt("issueId");
            }
            closeConnection();
            return  lastIssueId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
        return -1;
    }

    public int lastItemForHistoriesId(){
        try {
            int lastItemId =0;
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery( "SELECT itemId FROM itemsForHistories;" );
            while(rs.next()) {
            	lastItemId = rs.getInt("itemId");
            }
            closeConnection();
            return  lastItemId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
        return -1;
    }
    
    public int getProjectId(String projectName){
        try {
            int projectId=-1;
            Statement statement = getConnection().createStatement();
            
            ResultSet rs = statement.executeQuery( "SELECT id FROM projects WHERE project_name ='"+projectName+"';" );
            //System.out.println("SELECT id FROM projects WHERE project_name ='"+projectName+"';");
            if(rs.next()){
            	projectId = rs.getInt("id");
            	rs.close();
            }
            closeConnection();
            return projectId;
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        finally {
			closeConnection();
		}
    }
    
    public long getProjectDate(String projectName){
        try {
            long projectId=-1;
            Statement statement = getConnection().createStatement();
            
            ResultSet rs = statement.executeQuery( "SELECT created_date FROM projects WHERE project_name ='"+projectName+"';" );
            if(rs.next()){
            	projectId = rs.getLong("created_date");
            	rs.close();
            }
            closeConnection();
            return projectId;
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        finally {
			closeConnection();
		}
    }

    public void deleteAll(){

        try {
            Statement statement2 = getConnection().createStatement();
            String sql = "DELETE FROM projects;";
            statement2.executeUpdate(sql);
            statement2 = getConnection().createStatement();
            sql = "DELETE FROM issues;";
            statement2.executeUpdate(sql);
            statement2.close();
            getConnection().commit();
            System.out.println("Database deleted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			closeConnection();
		}
    }
    

    public void renewProject(String projectName){
    	deleteProject(projectName);
    	insertProject(projectName);
    }

    

    public String getExperimentalDate(String field){
    	String result = "null";
        try {
            
            Statement statement = getConnection().createStatement();
            
            ResultSet rs = statement.executeQuery( "SELECT value FROM experimental where field='"+field+"'");
            if(rs.next()){
            	result = rs.getString("value");
            	rs.close();
            }
            closeConnection();
            return result;
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        finally {
			closeConnection();
		}
    }

}
