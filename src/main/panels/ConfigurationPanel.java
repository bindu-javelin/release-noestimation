package main.panels;

import main.classes.Issue;
import main.classes.Selection;
import main.connection.HttpRequest;
import main.database.DBHelper;
import main.parser.IssueParser;
import main.parser.StatusParser;

import javax.swing.*;
import javax.swing.border.Border;

import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigurationPanel extends AbstractJPanel {

    public ConfigurationPanel() {
        super();
    }
    

    ArrayList<Issue> issueList;
    ArrayList<Selection> selectionList;
    ArrayList<SelectionView> selectionViewList;

    public void init(){
    	JFrame frame = ((JFrame)getRootPane().getParent());
        frame.setTitle(MainFrame.TAG + "Configuration");
        JLabel jqlIcon = new JLabel("");
        jqlIcon.setBounds(200, 215, 15, 30);
        add(jqlIcon);
        
        selectionViewList = new ArrayList<>();
        

        JTextField jqlField = new JTextField();
        jqlField.setBounds(290, 215, 550, 30);
        add(jqlField);
        jqlField.setText("fixVersion=\"\" and module = \"\"");
        
        jqlField.setFont(new Font("Verdana", Font.ITALIC, 14));
        jqlField.setColumns(10);

        JLabel jqlLabel = new JLabel("JQL");
        jqlLabel.setForeground(SystemColor.inactiveCaptionText);
        jqlLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 15));

        jqlLabel.setBounds(225, 215, 60, 30);
        add(jqlLabel);
        
        JLabel issueTypeLabel = new JLabel("Issue Type");
		issueTypeLabel.setForeground(SystemColor.inactiveCaptionText);
		issueTypeLabel.setFont(new Font("Verdana", Font.ITALIC, 15));
		issueTypeLabel.setBounds(15, 320, 90, 25);
		add(issueTypeLabel);
		
		JLabel issueTypeStartedLabel = new JLabel("Started Date");
		issueTypeStartedLabel.setForeground(SystemColor.inactiveCaptionText);
		issueTypeStartedLabel.setFont(new Font("Verdana", Font.ITALIC, 15));
		issueTypeStartedLabel.setBounds(15, 375, 150, 25);
		add(issueTypeStartedLabel);

		JLabel issueTypeCreatedFromLabel = new JLabel("FROM");
		issueTypeCreatedFromLabel.setForeground(SystemColor.inactiveCaptionText);
		issueTypeCreatedFromLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
		issueTypeCreatedFromLabel.setBounds(155, 360, 60, 25);
		add(issueTypeCreatedFromLabel);

		JLabel issueTypeCreatedToLabel = new JLabel("TO");
		issueTypeCreatedToLabel.setForeground(SystemColor.inactiveCaptionText);
		issueTypeCreatedToLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
		issueTypeCreatedToLabel.setBounds(155, 390, 60, 25);
		add(issueTypeCreatedToLabel);

		JLabel issueTypeCompletedLabel = new JLabel("Completed Date");
		issueTypeCompletedLabel.setForeground(SystemColor.inactiveCaptionText);
		issueTypeCompletedLabel.setFont(new Font("Verdana", Font.ITALIC, 15));
		issueTypeCompletedLabel.setBounds(15, 435, 150, 25);
		add(issueTypeCompletedLabel);

		JLabel issueTypeCompletedFromLabel = new JLabel("FROM");
		issueTypeCompletedFromLabel.setForeground(SystemColor.inactiveCaptionText);
		issueTypeCompletedFromLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
		issueTypeCompletedFromLabel.setBounds(155, 430, 60, 25);
		add(issueTypeCompletedFromLabel);

		JLabel issueTypeCompletedToLabel = new JLabel("TO");
		issueTypeCompletedToLabel.setForeground(SystemColor.inactiveCaptionText);
		issueTypeCompletedToLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
		issueTypeCompletedToLabel.setBounds(155, 460, 60, 25);
		add(issueTypeCompletedToLabel);
        
		
        SelectionView sw = new SelectionView();
        sw.setBounds(SelectionView.length()*210+(SelectionView.length()-1)*15,320,200,250);
        selectionViewList.add(sw);
        add(sw);
        
        SelectionView sw2 = new SelectionView();
        sw2.setBounds(SelectionView.length()*210+(SelectionView.length()-1)*15,320,200,250);
        selectionViewList.add(sw2);
        add(sw2);
        
        SelectionView sw3 = new SelectionView();
        sw3.setBounds(SelectionView.length()*210+(SelectionView.length()-1)*15,320,200,250);
        selectionViewList.add(sw3);
        add(sw3);
        
        /* SelectionView sw3 = new SelectionView();
        sw3.setBounds(SelectionView.length()*210+(SelectionView.length()-1)*15,320,200,250);
        selectionViewList.add(sw3);
        add(sw3);
        
        SelectionView sw4 = new SelectionView();
        //sw4.setBounds(SelectionView.length()*200+(SelectionView.length()-1)*15,320,200,250);
        sw4.setBounds(SelectionView.length()*210+(SelectionView.length()-1)*15,320,200,250);
        selectionViewList.add(sw4);
        add(sw4); */


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        

        JButton backButton = new JButton("Clear Project Cache");
        backButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        backButton.setBounds(25, 600, 175, 35);
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				DBHelper.getInstance().deleteProject(jqlField.getText().toString().trim());
				 }
        });

        JButton nextButton = new JButton("Next -->");
        nextButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        nextButton.setBounds(900, 600, 100, 35);
        add(nextButton);
        
        selectionList = new ArrayList<>();
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.changePanel(MainFrame.THROUGHPUT_PANEL);
                
                selectionList = new ArrayList<>();
                
                for (int i = 0; i < selectionViewList.size(); i++) {
                Selection s = new Selection();
                	String issueType = selectionViewList.get(i).getIssueTypeComboBox().getSelectedItem().toString();
                	if(!issueType.equals(DBHelper.nullSelection)){
		                s.setIssueType(issueType);
		                s.setStartedFrom(selectionViewList.get(i).getIssueTypeStartedFromComboBox().getSelectedItem().toString());
		                s.setStartedTo(selectionViewList.get(i).getIssueTypeStartedToComboBox().getSelectedItem().toString());
		                s.setCompletedFrom(selectionViewList.get(i).getIssueTypeCompletedFromComboBox().getSelectedItem().toString());
		                s.setCompletedTo(selectionViewList.get(i).getIssueTypeCompletedToComboBox().getSelectedItem().toString());
		                selectionList.add(s);
                	}
				}
                
                ((ThroughputPanel)(MainFrame.jPanelList.get(MainFrame.THROUGHPUT_PANEL))).init(jqlField.getText(),selectionList);

            }
        });
        
        /* 
        // Load a Project
        JButton loadProjectButton = new JButton("Load Project");
        loadProjectButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        loadProjectButton.setBounds(750, 600, 100, 35);
        add(loadProjectButton);
        loadProjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            	
				String encodedJqlText = jqlField.getText().replace("\"","%22").replace(" ","%20");
				String issueURL = MainFrame.PROP.getProperty("BASE_URL")+MainFrame.PROP.getProperty("JQL_URL")+encodedJqlText+"&maxResults="+MainFrame.PROP.getProperty("MAX_RESULT")+"&expand=changelog.histories";
				String issueJsonString  = HttpRequest.getRequest(issueURL);
				try {
				    JSONObject obj = new JSONObject(issueJsonString);
				    if(obj.isNull("errorMessages")){
						issueList = IssueParser.parse(issueJsonString,issueURL);
						if(issueList==null) return;
						DBHelper.init();
						int projectId = DBHelper.lastProjectId();
						for (int i = 0; i < issueList.size(); i++) {	
							DBHelper.insertIssue(issueList.get(i),projectId);
						}
						//fillScrollPanes(issueType1,issueType2);
				     }
				} catch (JSONException jEx) {
					jEx.printStackTrace();
				}
            }
        }); */
        
    }
    public void changeTitle(){
        ((JFrame)getRootPane().getParent()).setTitle("Configuration");
    }

}

class SelectionView extends JPanel {
	private static int x = 0;
	private JComboBox issueTypeStartedFromComboBox, issueTypeStartedToComboBox, issueTypeCompletedFromComboBox,
			issueTypeCompletedToComboBox;
	private JComboBox issueTypeComboBox;

	public SelectionView() {
		setLayout(null);
		setBackground(null);
		issueTypeStartedFromComboBox = new JComboBox();
		issueTypeStartedFromComboBox.setBounds(0, 40, 175, 25);
		add(issueTypeStartedFromComboBox);

		issueTypeStartedToComboBox = new JComboBox();
		issueTypeStartedToComboBox.setBounds(0, 70, 175, 25);
		add(issueTypeStartedToComboBox);

		issueTypeCompletedFromComboBox = new JComboBox();
		issueTypeCompletedFromComboBox.setBounds(0, 110, 175, 25);
		add(issueTypeCompletedFromComboBox);

		issueTypeCompletedToComboBox = new JComboBox();
		issueTypeCompletedToComboBox.setBounds(0, 140, 175, 25);
		add(issueTypeCompletedToComboBox);

		issueTypeCompletedFromComboBox.addItem(DBHelper.nullSelection);
		issueTypeCompletedToComboBox.addItem(DBHelper.nullSelection);
		issueTypeStartedFromComboBox.addItem(DBHelper.nullSelection);
		issueTypeStartedToComboBox.addItem(DBHelper.nullSelection);

		ArrayList<String> statusList = DBHelper.getInstance().getStatus();

		for (int i = 0; i < statusList.size(); i++) {
			String status = statusList.get(i);
			issueTypeCompletedFromComboBox.addItem(status);
			issueTypeCompletedToComboBox.addItem(status);
			issueTypeStartedFromComboBox.addItem(status);
			issueTypeStartedToComboBox.addItem(status);
		}


		issueTypeComboBox = new JComboBox();
		issueTypeComboBox.setBounds(0, 0, 200, 25);
		add(issueTypeComboBox);

		

		ArrayList<String> issueTypeList = DBHelper.getInstance().getIssueTypes();

		if(length()>0) issueTypeComboBox.addItem(DBHelper.nullSelection);
		for (int i = 0; i < issueTypeList.size(); i++) {
			String issueType = issueTypeList.get(i);
			issueTypeComboBox.addItem(issueType);
		}
		if(length()==1) issueTypeComboBox.setSelectedIndex(8); // TODO DELETE
		
		
		x++;
	}
	
	public static int length(){
		return x;
	}

	public JComboBox getIssueTypeStartedFromComboBox() {
		return issueTypeStartedFromComboBox;
	}

	public JComboBox getIssueTypeStartedToComboBox() {
		return issueTypeStartedToComboBox;
	}

	public JComboBox getIssueTypeCompletedFromComboBox() {
		return issueTypeCompletedFromComboBox;
	}

	public JComboBox getIssueTypeCompletedToComboBox() {
		return issueTypeCompletedToComboBox;
	}

	public JComboBox getIssueTypeComboBox() {
		return issueTypeComboBox;
	}

}
