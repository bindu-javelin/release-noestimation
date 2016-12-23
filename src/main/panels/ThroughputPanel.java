

package main.panels;

import main.classes.Issue;
import main.connection.HttpRequest;
import main.database.DBHelper;
import main.parser.IssueParser;
import main.view.JTableThroughput;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.security.auth.login.Configuration;
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
    public static ArrayList<Issue> issueList;
    private String issueType1;
    private String issueType2;

    public static ArrayList<Issue> issueType1List;
    public static ArrayList<Issue> issueType2List;
    static JScrollPane issueType1ScrollPane;
    static JScrollPane issueType1SummaryScrollPane;
    static JScrollPane issueType2ScrollPane;
    static JScrollPane issueType2SummaryScrollPane;

    static JTableThroughput jTableThroughputForIssueType1;
    static JTableThroughput jTableThroughputForIssueType2;

    JLabel issueType1Label;
    JLabel issueType1Icon;

    JLabel issueType2Label;
    JLabel issueType2Icon;
    public ThroughputPanel() {
        super();

        issueType1Label = new JLabel();
        issueType1Label.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
        issueType1Label.setBounds(50, 25, 500, 20);
        add(issueType1Label);

        issueType1Icon = new JLabel("");
        issueType1Icon.setBounds(25, 25, 20, 20);
        add(issueType1Icon);

        issueType2Label = new JLabel();
        issueType2Label.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
        issueType2Label.setBounds(50, 320, 500, 20);
        add(issueType2Label);

        issueType2Icon = new JLabel("");
        issueType2Icon.setBounds(25, 320, 20, 20);
        add(issueType2Icon);


        issueType1ScrollPane = new JScrollPane();
         issueType1SummaryScrollPane = new JScrollPane();

        issueType2ScrollPane = new JScrollPane();
        issueType2SummaryScrollPane = new JScrollPane();

        issueType1ScrollPane.setBounds(25, 50, 550, 240);
        issueType1ScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType1ScrollPane.setBorder(BorderFactory.createEmptyBorder());
       // issueType1ScrollPane.setForeground(new Color(247, 249, 252));
        add(issueType1ScrollPane);

        issueType1SummaryScrollPane.setBounds(600, 50, 400, 240);
        issueType1SummaryScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType1SummaryScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(issueType1SummaryScrollPane);

        issueType2ScrollPane.setBounds(25, 350, 550, 240);
        issueType2ScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType2ScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(issueType2ScrollPane);

        issueType2SummaryScrollPane.setBounds(600, 350, 400, 240);
        issueType2SummaryScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType2SummaryScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(issueType2SummaryScrollPane);

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
                ((IssuePanel)(MainFrame.jPanelList.get(MainFrame.ISSUE_PANEL))).init(issueType1List,issueType2List,
                        jTableThroughputForIssueType1,jTableThroughputForIssueType2);

            }
        });
    }

    public void init(String fromString, String toString,  String jqlText, String issueType1, String issueType2, ArrayList<String> fromToStringList){

        String encodedJqlText = jqlText.replace("\"","%22").replace(" ","%20");
        String tempIssueType2;
        String encodedIssueType2 ="";
        String fromToString =fromToStringList.get(0);
        for(int i =1 ; i<fromToStringList.size();i++){
            fromToString+= ","+ fromToStringList.get(i);
        }
        if(!issueType2.equals("")){
            tempIssueType2= ",%22"+issueType2+"%22";
            encodedIssueType2 = tempIssueType2.replace("\"","%22").replace(" ","%20");
        }
        String encodedIssueType1 = issueType1.replace("\"","%22").replace(" ","%20");

        String issueURL =MainFrame.PROP.getProperty("BASE_URL")+MainFrame.PROP.getProperty("JQL_URL")+encodedJqlText+"%20and%20type%20in%20(%22"+encodedIssueType1+"%22"
                +encodedIssueType2+")&maxResults="+MainFrame.PROP.getProperty("MAX_RESULT")+"&expand=changelog.histories";

        System.out.println(issueURL);
        if(DBHelper.isThereAnyIssues(issueURL,fromToString)){
            fillScrollPanes(issueType1,issueType2);

        } else{

            String issueJsonString  = HttpRequest.getRequest(issueURL);
            try {
                JSONObject obj = new JSONObject(issueJsonString);
                if(obj.isNull("errorMessages")){
                    issueList = IssueParser.parse(issueJsonString,fromString,toString,issueURL,fromToString,issueType1,issueType2,fromToStringList);
                    fillScrollPanes(issueType1,issueType2);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        issueType1Label.setText(getIssueType1());
        try {
            URL url = new URL(DBHelper.getIssueTypeIcon(issueType1Label.getText()));
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon  = new ImageIcon(img);
            issueType1Icon.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(issueType2List.size()!=0){
        issueType2Label.setText(getIssueType2());
        try {
            URL url = new URL(DBHelper.getIssueTypeIcon(issueType2Label.getText()));
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon  = new ImageIcon(img);
            issueType2Icon.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    }

    public  void fillScrollPanes(String issueType1, String issueType2 ){
        ((JFrame)getRootPane().getParent()).setTitle("Throughput");
        this.issueType1 = issueType1;
        this.issueType2 = issueType2;
        jTableThroughputForIssueType1 = new JTableThroughput();
        jTableThroughputForIssueType2 = new JTableThroughput();

        TreeSet smallestIssueType1Day= new TreeSet();
        issueType1List =DBHelper.getIssuesType1(issueType1);
        for(int i=0;i< issueType1List.size();i++)
        {
            smallestIssueType1Day.add(issueType1List.get(i).getCreatedDate());

        }
        if(smallestIssueType1Day.size()>0){
            issueType1ScrollPane.setViewportView(jTableThroughputForIssueType1.createThroughputTable(issueType1List, (Date) smallestIssueType1Day.first()));
            issueType1SummaryScrollPane.setViewportView(jTableThroughputForIssueType1.createSummaryThroughputTable(jTableThroughputForIssueType1.getThroughputList()));

        }

        //smallestIssueType1Day.clear();

        issueType2List = new ArrayList<>();
        if(!issueType2.equals("")) {
            TreeSet smallestIssueType2Day = new TreeSet();
            issueType2List = DBHelper.getIssuesType2(issueType2);
            for (int i = 0; i < issueType2List.size(); i++) {
                smallestIssueType2Day.add(issueType2List.get(i).getCreatedDate());

            }
            if (smallestIssueType2Day.size() > 0) {
                issueType2ScrollPane.setViewportView(jTableThroughputForIssueType2.createThroughputTable(issueType2List, (Date) smallestIssueType2Day.first()));
                issueType2SummaryScrollPane.setViewportView(jTableThroughputForIssueType2.createSummaryThroughputTable(jTableThroughputForIssueType2.getThroughputList()));
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

    public String getIssueType2() {
        return issueType2;
    }

    public void setIssueType2(String issueType2) {
        this.issueType2 = issueType2;
    }
}
