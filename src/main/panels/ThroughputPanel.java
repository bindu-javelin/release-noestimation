package main.panels;

import main.classes.Issue;
import main.classes.Selection;
import main.connection.HttpRequest;
import main.database.DBHelper;
import main.parser.IssueParser;
import main.view.JTableThroughput;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;


public class ThroughputPanel extends AbstractJPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2096966912383480224L;
	
	public static ArrayList<Issue> issueList;
    private String issueType1;

    //public static ArrayList<Issue> issueType1List;
    //static JScrollPane issueType1ScrollPane;
    //static JScrollPane issueType1SummaryScrollPane;

    //JLabel issueType1Label;
    //JLabel issueType1Icon;
    public ThroughputPanel() {
        super();
    }

    public void init(String jqlText, ArrayList<Selection> selectionList){
    	removeAll();
    	ThroughputView.resetLength();
    	
    	jqlText = jqlText.trim();

        
        JButton backButton = new JButton("<-- Back");
        backButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        backButton.setBounds(25, 600, 100, 35);
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.changePanel(MainFrame.THROUGHPUT_PANEL -1);
                ((ConfigurationPanel)(MainFrame.jPanelList.get(MainFrame.CONFIGURATION_PANEL))).changeTitle();
            }
        });

        JButton nextButton = new JButton("Next -->");
        nextButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        nextButton.setBounds(900, 600, 100, 35);
        add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.changePanel(MainFrame.ISSUE_PANEL);
                ((IssuePanel)(MainFrame.jPanelList.get(MainFrame.ISSUE_PANEL))).init(selectionList);

            }
        });
    	
    	System.out.println("SelectionList Size:" + selectionList.size());
    	for (int i = 0; i < selectionList.size(); i++) {
        	System.out.println("SelectionList "+(i+1)+". Issue Type:" + selectionList.get(i).getIssueType());
			
		}
        
        /*String encodedIssueType = "\""+selectionList.get(0).getIssueType()+"\"";
        selectionList.get(0).setIssueList(null);
        for (int i = 1; i < selectionList.size(); i++) {
        	encodedIssueType += ",\""+selectionList.get(i).getIssueType()+"\"";
            selectionList.get(i).setIssueList(null);
		}
        encodedIssueType = "(" + encodedIssueType + ")";

        String issueURL = MainFrame.PROP.getProperty("BASE_URL")+MainFrame.PROP.getProperty("JQL_URL")+jqlText+
        		"%20and%20type%20in%20" + encodedIssueType + "&maxResults="+MainFrame.PROP.getProperty("MAX_RESULT")+"&expand=changelog.histories";

        issueURL = issueURL.replace("\"","%22").replace(" ","%20"); */
      
    	
    	if(DBHelper.getInstance().insertProject(jqlText)){

            for(int i=0; i<selectionList.size();i++){
	    		String encodedIssueType = "(\""+selectionList.get(i).getIssueType()+"\")";
	            String issueURL = MainFrame.PROP.getProperty("BASE_URL")+MainFrame.PROP.getProperty("JQL_URL")+jqlText+
	            		"%20and%20type%20in%20" + encodedIssueType + "&maxResults="+MainFrame.PROP.getProperty("MAX_RESULT")+"&expand=changelog.histories";
	            System.out.println(issueURL.replace("\"","%22").replace(" ","%20"));
		        String issueJsonString  = HttpRequest.getRequest(issueURL.replace("\"","%22").replace(" ","%20"));
		        
		        try {
		            JSONObject obj = new JSONObject(issueJsonString);
		            if(obj.isNull("errorMessages")){
		                IssueParser.parse(issueJsonString,jqlText);
		                int projectId = DBHelper.getInstance().getProjectId(jqlText);
	                	//selectionList.get(i).setIssueList(DBHelper.getInstance().getIssues(projectId,selectionList.get(i)));
	                	selectionList.get(i).setIssueList(DBHelper.getInstance().optimizeDays(DBHelper.getInstance().getIssues(projectId,selectionList.get(i))));
		            }
		
		        } catch (JSONException e) {
		            e.printStackTrace();
		        }
            }
    	}
    	else {
    		
    		// Eger DB'de daha önce kayitli issue_type'lardan farkli bir issue_type var mi diye kontrol eder
    		// Eger varsa sunucudan verileri ceker
	        for (int i = 0; i < selectionList.size(); i++) {
	        	if(!DBHelper.getInstance().isThereIssueType(jqlText, selectionList.get(i).getIssueType())){
	        		System.out.println("Data not found. It will load: "+selectionList.get(i).getIssueType());

		    		String encodedIssueType = "(\""+selectionList.get(i).getIssueType()+"\")";
		            String issueURL = MainFrame.PROP.getProperty("BASE_URL")+MainFrame.PROP.getProperty("JQL_URL")+jqlText+
		            		"%20and%20type%20in%20" + encodedIssueType + "&maxResults="+MainFrame.PROP.getProperty("MAX_RESULT")+"&expand=changelog.histories";
			        String issueJsonString  = HttpRequest.getRequest(issueURL.replace("\"","%22").replace(" ","%20"));
			        
			        try {
			            JSONObject obj = new JSONObject(issueJsonString);
			            if(obj.isNull("errorMessages")){
			                IssueParser.parse(issueJsonString,jqlText);
			                int projectId = DBHelper.getInstance().getProjectId(jqlText);
		                	//selectionList.get(i).setIssueList(DBHelper.getInstance().getIssues(projectId,selectionList.get(i)));
		                	selectionList.get(i).setIssueList(DBHelper.getInstance().optimizeDays(DBHelper.getInstance().getIssues(projectId,selectionList.get(i))));
			            }
			
			        } catch (JSONException e) {
			            e.printStackTrace();
			        }
	        	}
			}
    		
            int projectId = DBHelper.getInstance().getProjectId(jqlText);
            
            
            for(int i=0; i<selectionList.size();i++){
            	selectionList.get(i).setIssueList(DBHelper.getInstance().optimizeDays(DBHelper.getInstance().getIssues(projectId,selectionList.get(i))));
            	//selectionList.get(i).setIssueList(DBHelper.getInstance().getIssues(projectId,selectionList.get(i)));
            }
    	}


        
        fillScrollPanes(selectionList);


    }

    
    

    //static JTableThroughput jTableThroughputForIssueType1;
    public ArrayList<Issue> allIssueList;
    public  void fillScrollPanes(ArrayList<Selection> selectionList){
    	System.out.println("Filling Scroll Panes");
        ((JFrame)getRootPane().getParent()).setTitle(MainFrame.TAG + "Throughput");
        
        for(int i=0; i<selectionList.size(); i++){
        	Selection selection = selectionList.get(i);
	        TreeSet smallestIssueTypeDay = new TreeSet();
	        selection.setThrougput(new JTableThroughput());
	        
	    	System.out.println("Item size for fillScrollPanes: " + selection.getIssueList().size() + " for " + selection.getIssueType());
	        for(int j=0; j < selection.getIssueList().size(); j++)
	        {
	            smallestIssueTypeDay.add(selection.getIssueList().get(j).getCreatedDate());
	
	        }
	        if(smallestIssueTypeDay.size()>0){
	        	ThroughputView view = new ThroughputView(selection.getIssueType());
	        	view.setLayout(null);
	        	view.setBackground(null);
	        	view.setBounds(0, (ThroughputView.length()-1)*300, 1000, 300);
	        	view.getIssueTypeScrollPane().setViewportView(selection.getThrougput().createThroughputTable(selection.getIssueList()));
	            view.getIssueTypeSummaryScrollPane().setViewportView(selection.getThrougput().createSummaryThroughputTable(selection.getThrougput().getThroughputList()));
	            add(view);
	        }
        }

    }
    public void changeTitle(){
        ((JFrame)getRootPane().getParent()).setTitle("Throughput");
    }


    public String getIssueType1() {
        return issueType1;
    }

    public void setIssueType1(String issueType1) {
        this.issueType1 = issueType1;
    }
}

class ThroughputView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6298130401804427542L;
	private static int x=0;

	private JScrollPane issueTypeScrollPane,issueTypeSummaryScrollPane;
	public ThroughputView(String issueType) {
		JLabel issueTypeLabel = new JLabel();
        issueTypeLabel.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
        issueTypeLabel.setBounds(50, 25, 500, 20);
        add(issueTypeLabel);

        JLabel issueTypeIcon = new JLabel("");
        issueTypeIcon.setBounds(25, 25, 20, 20);
        add(issueTypeIcon);

        issueTypeScrollPane = new JScrollPane();
        issueTypeSummaryScrollPane = new JScrollPane();

        issueTypeScrollPane.setBounds(25, 50, 550, 240);
        issueTypeScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueTypeScrollPane.setBorder(BorderFactory.createEmptyBorder());
        //issueType1ScrollPane.setForeground(new Color(247, 249, 252));
        add(issueTypeScrollPane);

        issueTypeSummaryScrollPane.setBounds(600, 50, 400, 240);
        issueTypeSummaryScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueTypeSummaryScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(issueTypeSummaryScrollPane);
        issueTypeLabel.setText(issueType);
        try {
            URL url = new URL(DBHelper.getInstance().getIssueTypeIcon(issueTypeLabel.getText()));
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon  = new ImageIcon(img);
            issueTypeIcon.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        x++;
	}
	
	public JScrollPane getIssueTypeScrollPane() {
		return issueTypeScrollPane;
	}
	public JScrollPane getIssueTypeSummaryScrollPane() {
		return issueTypeSummaryScrollPane;
	}
	
	public static int length(){
		return x;
	}
	
	public static void resetLength(){
		x = 0;
	}
	
	
}
