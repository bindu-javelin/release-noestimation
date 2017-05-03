package main.parser;

import main.classes.Issue;
import main.database.DBHelper;
import main.functions.GlobalFunctions;
import main.panels.MainFrame;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * Created by ferhaty on 11/18/2016.
 * 
 */
public class IssueParser {

	
	class ChangeLog {
		public ChangeLog(String d,String from, String to) {
			date = d;
			fromString = from;
			toString = to;
		}
		public String date;
		public String fromString, toString;
	}
	

    
    public static void parse(String text,String projectName){
        ArrayList<Issue> issueList = new ArrayList<>();
        
        System.out.println("IssueParser started");
        
        Issue issue = null;
        int projectId = DBHelper.getInstance().getProjectId(projectName);

        try {
            JSONObject obj = new JSONObject(text);
            JSONArray issues= obj.getJSONArray("issues");
            
            if(obj.getInt("total")<=0){
            	System.out.println("Json data not found");
            	JOptionPane.showMessageDialog(null, "Json data not foun");
            	return;
            }
            
            System.out.println("Size of issues from JSONText: " + issues.length());
            
            for(int i=0; i<issues.length(); i++){
            	//System.out.println("Converting json " + (i+1) + ". object" );
                JSONObject issueObj = issues.getJSONObject(i);
                String issueTypeName = issueObj.getJSONObject("fields").getJSONObject("issuetype").getString("name");
                
                
                // boolean i2 = false;	// for detecting current issue type
                
                // if current issue type is selected type, it continues
                // if(issueTypeName.equals(issueType1) || (i2 = issueTypeName.equals(issueType2))){
                   
            	issue = null;
            	issue = new Issue();
            	// assign issue object
            	issue.setIssueKey(issueObj.getString("key"));
                issue.setIssueTypeName(issueTypeName);
                
                Date createdDate = null;
                Date completedDate = null;
                try {
                	createdDate = GlobalFunctions.dateFormat((issueObj.getJSONObject("fields").getString("created").split("T")[0]).toString());
                	completedDate = GlobalFunctions.dateFormat((issueObj.getJSONObject("fields").getString("resolutiondate").split("T")[0]).toString());
				} catch (Exception e) {
					continue;
				}
                issue.setCreatedDate(createdDate);
                issue.setCompletedDate(completedDate);
                long cDate = completedDate.getTime();
                

                //TODO System.out.println("---->"+issue.getCreatedDate());
	           //TODO System.out.println("---->"+issue.getCompletedDate());
                
                if(issue.getCompletedDate()!=null  && issue.getCreatedDate() != null ){//&& !issue.getIssueKey().equals("ABE-8688")){
                    issue.setTimeCycle(GlobalFunctions.dayDiff(issue.getCreatedDate(),issue.getCompletedDate()));
                    DBHelper.getInstance().insertIssue(issue,projectId);
                    if(!DBHelper.getInstance().isThereIssueTypeList(projectId, issue.getIssueTypeName()))
                    	DBHelper.getInstance().insertIssueTypeList(projectId, issue.getIssueTypeName());
                    issueList.add(issue);
                    int lastIssueId = DBHelper.getInstance().lastIssueId();
                  //TODO System.out.println("Insert Issue to Database: " +lastIssueId + " " +  projectId);
                    

                	
                    JSONArray histories = issueObj.getJSONObject("changelog").getJSONArray("histories");
                	for(int j=0;j<histories.length();j++){
                		//if(GlobalFunctions.getTimeStamp(histories.getJSONObject(j).getString("created"))>cDate) break;
                		DBHelper.getInstance().insertHistory(lastIssueId, histories.getJSONObject(j).getString("created"));
                		int lastHistoryId = DBHelper.getInstance().lastHistoryId();
                		//TODO System.out.println("-->Insert History to Database: " +lastHistoryId + " " +  histories.getJSONObject(j).getString("created"));
                        JSONArray itemsArray = histories.getJSONObject(j).getJSONArray("items");
                        for (int k = 0; k < itemsArray.length(); k++) {
                    		if(!itemsArray.getJSONObject(k).getString("field").equals("status")) continue;
                        	String toString="null";
                			try {
                				toString = itemsArray.getJSONObject(k).getString("toString");
                			} catch (Exception e) {
                				toString = "null";
                			}
                        	String fromString="null";
                			try {
                				fromString = itemsArray.getJSONObject(k).getString("fromString");
                			} catch (Exception e) {
                				fromString = "null";
                			}
                			//if(DBHelper.getInstance().isThereStatus(toString) || DBHelper.getInstance().isThereStatus(fromString))
                				DBHelper.getInstance().insertItemForHistroies(lastHistoryId, fromString, toString);
                			//TODO System.out.println("---->Insert ItemForHistory to Database: toString:" +toString + " fromString:" +  fromString);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
			e.printStackTrace();
		}
    }
}
    
    
    //private static final byte KEY_SEARCH_TYPE_TOSTRING = 1, KEY_SEARCH_TYPE_FROMSTRING = 2;
    
    /**
     * Searching a date in items.
     * @param histories 	JSON array for <b>HISTORYIES</b>
     * @param str			Searching value
     * @param key			Searching type. Only <b>KEY_SEARCH_TYPE_TOSTRING</b> or <b>KEY_SEARCH_TYPE_FROMSTRING</b>
     * @return				Is it searching issue?
     */
    /* private static SearchData search(JSONArray histories,String str,byte key){
    	String field;
    	
    	if(key==KEY_SEARCH_TYPE_FROMSTRING)
    		field = "fromString";
    	else if(key==KEY_SEARCH_TYPE_TOSTRING)
    		field = "toString";
    	else 
        	return new SearchData(-1,false);
    	
    	for(int j=0;j<histories.length();j++){
            JSONArray itemsArray = histories.getJSONObject(j).getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
    			String value;
    			try {
    				value = itemsArray.getJSONObject(i).getString(field);
    			} catch (Exception e) {
    				value = null;
    			}
    			if(value.equals(str)) {
    		    	return new SearchData(i,true);
    			}
    		}
        }
    	return new SearchData(-1,false);
    } */
    
    /**
     * 
     * @param histories		JSON array for <b>HISTORYIES</b>
     * @param fromStr		FromString value
     * @param toStr			ToString value
     * @return				SearchData, id and state
     */
    /* private static SearchData search(JSONArray histories,String fromStr, String toStr){
    	for(int j=0;j<histories.length();j++){
            JSONArray itemsArray = histories.getJSONObject(j).getJSONArray("items");
	    	for (int i = 0; i < itemsArray.length(); i++) {
				String toString;
				String fromString;
				try {
					toString = itemsArray.getJSONObject(i).getString("toString");
				} catch (Exception e) {
					toString = null;
				}
				try {
					fromString = itemsArray.getJSONObject(i).getString("fromString");
				} catch (Exception e) {
					fromString = null;
				}
				
				if(toString.equals(toStr) && fromString.equals(fromStr)) {
					return new SearchData(i,true);
				}
			}
    	}
    	return new SearchData(-1,false);
    }
    
}

class SearchData {
	
	public SearchData(int id, boolean state) {
		this.id = id;
		this.state = state;
	}
	
	public int id;
	public boolean state;
} */