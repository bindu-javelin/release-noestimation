package main.parser;

import main.classes.Issue;
import main.database.DBHelper;
import main.functions.GlobalFunctions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ferhaty on 11/18/2016.
 */
public class IssueParser {

    public static ArrayList<Issue> parse(String text,String selectedFromString, String selectedToString, String issueURL, String fromToString, String issueType1,
                                         String issueType2, ArrayList<String> fromToStringList){
        ArrayList<Issue> issueList = new ArrayList<>();

        if(!DBHelper.isThereProjectName(issueURL,fromToString)){
            DBHelper.insertProject(issueURL,fromToString);
        }
        int projectId = DBHelper.getProjectId(issueURL,fromToString);

        try {
            JSONObject obj = new JSONObject(text);
            JSONArray issues= obj.getJSONArray("issues");
            for(int i=0; i<issues.length(); i++){
                JSONObject issueObj = issues.getJSONObject(i);

                Issue issue = new Issue();

                String key = issueObj.getString("key");

                String issueTypeName = issueObj.getJSONObject("fields").getJSONObject("issuetype").getString("name");
                issue.setIssueKey(key);

                issue.setIssueTypeName(issueTypeName);

                JSONArray histories = issueObj.getJSONObject("changelog").getJSONArray("histories");


                for(int j = histories.length()-1; j>=0; j--){
                    boolean completedDateDone = false;

                    JSONObject history = histories.getJSONObject(j);
                    JSONArray itemsArray = histories.getJSONObject(j).getJSONArray("items");

                    for(int k = itemsArray.length()-1; k>=0; k--){
                        String toString = itemsArray.getJSONObject(k).getString("toString");
                        String fromString = itemsArray.getJSONObject(k).getString("fromString");
                        if(issue.getIssueTypeName().equals(issueType1)){
                            if(fromToStringList.get(0).equals("-- Any --") & fromToStringList.get(1).equals("-- Any --")){
                                if(!issueObj.getJSONObject("fields").isNull("resolutiondate")){
                                    Date completedDate = GlobalFunctions.dateFormat((issueObj.getJSONObject("fields").getString("resolutiondate").split("T")[0]).toString());
                                    issue.setCompletedDate(completedDate);
                                    completedDateDone = true;
                                    break;
                                } else {
                                    completedDateDone = false;
                                    break;
                                }
                            } else if(fromToStringList.get(0).equals("-- Any --")){
                                if(toString.equals(fromToStringList.get(1))){
                                    Date completedDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                    issue.setCompletedDate(completedDate);
                                    completedDateDone = true;
                                    break;
                                }
                            }  else if (fromToStringList.get(1).equals("-- Any --")){
                                if(fromString.equals(fromToStringList.get(0))){
                                    Date completedDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                    issue.setCompletedDate(completedDate);
                                    completedDateDone = true;
                                    break;
                                }
                            }  else {
                                if(fromString.equals(fromToStringList.get(0))){
                                    if(toString.equals(fromToStringList.get(1))){
                                        Date completedDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                        issue.setCompletedDate(completedDate);
                                        completedDateDone = true;
                                        break;
                                    }
                                }
                            }
                        } else if(issue.getIssueTypeName().equals(issueType2)){
                            if(fromToStringList.get(4).equals("-- Any --") & fromToStringList.get(5).equals("-- Any --")){
                                if(!issueObj.getJSONObject("fields").isNull("resolutiondate")){
                                    Date completedDate = GlobalFunctions.dateFormat((issueObj.getJSONObject("fields").getString("resolutiondate").split("T")[0]).toString());
                                    issue.setCompletedDate(completedDate);
                                    completedDateDone = true;
                                    break;
                                } else {
                                    completedDateDone = false;
                                    break;
                                }

                            } else if(fromToStringList.get(4).equals("-- Any --")){
                                if(toString.equals(fromToStringList.get(5))){
                                    Date completedDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                    issue.setCompletedDate(completedDate);
                                    completedDateDone = true;
                                    break;
                                }
                            }  else if (fromToStringList.get(5).equals("-- Any --")){
                                if(fromString.equals(fromToStringList.get(4))){
                                    Date completedDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                    issue.setCompletedDate(completedDate);
                                    completedDateDone = true;
                                    break;
                                }
                            } else {
                                if(fromString.equals(fromToStringList.get(4))){
                                    if(toString.equals(fromToStringList.get(5))){
                                        Date completedDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                        issue.setCompletedDate(completedDate);
                                        completedDateDone = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(completedDateDone){
                        break;
                    }

                }

                for(int j =0 ; j<histories.length(); j++){

                    boolean createdDateDone = false;
                    JSONObject history = histories.getJSONObject(j);
                    JSONArray itemsArray = histories.getJSONObject(j).getJSONArray("items");

                    for(int k = 0; k<itemsArray.length(); k++){
                        String toString = itemsArray.getJSONObject(k).getString("toString");
                        String fromString = itemsArray.getJSONObject(k).getString("fromString");
                        if(issue.getIssueTypeName().equals(issueType1)){
                            if(fromToStringList.get(2).equals("-- Any --") & fromToStringList.get(3).equals("-- Any --")){
                                Date createdDate = GlobalFunctions.dateFormat((issueObj.getJSONObject("fields").getString("created").split("T")[0]).toString());
                                issue.setCreatedDate(createdDate);
                                createdDateDone = true;
                                break;
                            } else if(fromToStringList.get(2).equals("-- Any --")){
                                if(toString.equals(fromToStringList.get(3))){
                                    Date createdDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                    issue.setCreatedDate(createdDate);
                                    createdDateDone = true;
                                    break;
                                }
                            }  else if (fromToStringList.get(3).equals("-- Any --")){
                                if(fromString.equals(fromToStringList.get(2))){
                                    Date createdDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                    issue.setCreatedDate(createdDate);
                                    createdDateDone = true;
                                    break;
                                }
                            } else {
                                if(fromString.equals(fromToStringList.get(2))){
                                    if(toString.equals(fromToStringList.get(3))){
                                        Date createdDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                        issue.setCreatedDate(createdDate);
                                        createdDateDone = true;
                                        break;
                                    }
                                }
                            }
                        } else if(issue.getIssueTypeName().equals(issueType2)) {
                            if(fromToStringList.get(6).equals("-- Any --") & fromToStringList.get(7).equals("-- Any --")){
                                Date createdDate = GlobalFunctions.dateFormat((issueObj.getJSONObject("fields").getString("created").split("T")[0]).toString());
                                issue.setCreatedDate(createdDate);
                                createdDateDone = true;
                                break;
                            } else if(fromToStringList.get(6).equals("-- Any --")){
                                if(toString.equals(fromToStringList.get(7))){
                                    Date createdDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                    issue.setCreatedDate(createdDate);
                                    createdDateDone = true;
                                    break;
                                }
                            }  else if (fromToStringList.get(7).equals("-- Any --")){
                                if(fromString.equals(fromToStringList.get(6))){
                                    Date createdDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                    issue.setCreatedDate(createdDate);
                                    createdDateDone = true;
                                    break;
                                }
                            }  else {
                                if(fromString.equals(fromToStringList.get(6))){
                                    if(toString.equals(fromToStringList.get(7))){
                                        Date createdDate = GlobalFunctions.dateFormat((history.getString("created").split("T")[0]).toString());
                                        issue.setCreatedDate(createdDate);
                                        createdDateDone = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(createdDateDone){
                        break;
                    }

                }



                if(issue.getCompletedDate()!=null  && issue.getCreatedDate() != null ){//&& !issue.getIssueKey().equals("ABE-8688")){
                    issue.setTimeCycle(GlobalFunctions.dayDiff(issue.getCreatedDate(),issue.getCompletedDate()));
                    DBHelper.insertIssue(issue,projectId);
                    issueList.add(issue);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return issueList;
    }
}
