package main.panels;

import main.classes.Issue;
import main.classes.Selection;
import main.database.DBHelper;
import main.view.JTableIssue;
import main.view.JTableThroughput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class IssuePanel extends AbstractJPanel {

    public IssuePanel() {
        super();
    }
    ArrayList<Selection> selectionList;
    
    public void init(ArrayList<Selection> selectionList) {
    	removeAll();
    	IssueView.resetLength();
    	
    	this.selectionList = selectionList;
       ((JFrame)getRootPane().getParent()).setTitle(MainFrame.TAG + "Issues");
        fillScrollPanes(selectionList);

        JButton backButton = new JButton("<-- Back");
        backButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        backButton.setBounds(25, 600, 100, 35);
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.changePanel(MainFrame.ISSUE_PANEL-1);
                ((ThroughputPanel)(MainFrame.jPanelList.get(MainFrame.THROUGHPUT_PANEL))).changeTitle();
            }
        });

        JButton nextButton = new JButton("Next -->");
        nextButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        nextButton.setBounds(900, 600, 100, 35);
        add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                MainFrame.changePanel(MainFrame.ANALYZING_PANEL);
                ((AnalyzingPanel)(MainFrame.jPanelList.get(MainFrame.ANALYZING_PANEL))).init(selectionList);

            }
        });
    }

    public void changeTitle(){
        ((JFrame)getRootPane().getParent()).setTitle("Issues");
    }
    public void fillScrollPanes(ArrayList<Selection> selectionList) {

        for(int i=0; i<selectionList.size(); i++){
            Selection selection = selectionList.get(i);
            selection.setTableIssue(new JTableIssue());

            IssueView issueView = new IssueView(selection.getIssueType());
            issueView.setLayout(null);
            issueView.setBackground(null);
            issueView.setBounds(0, (IssueView.length()-1)*300, 1000, 300);
            issueView.getIssueTypeScrollPane().setViewportView(selection.getTableIssue().createIssueTable(selection.getIssueList(), selection.getThrougput().getThroughputShowTable()));
            issueView.getIssueTypeSummaryScrollPane().setViewportView(selection.getTableIssue().createWipTable(selection.getThrougput().getThroughputList(), selection.getThrougput().getThroughputShowTable()));
            add(issueView);
        }
        
    }


}


class IssueView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4785543889170915642L;

	/**
	 * 
	 */
	private static int x=0;

	private JScrollPane issueTypeScrollPane,issueTypeSummaryScrollPane;
	public IssueView(String issueType) {
		
		JLabel issueTypeLabel = new JLabel();
        issueTypeLabel.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
        issueTypeLabel.setBounds(50, 25, 500, 20);
        add(issueTypeLabel);

        JLabel issueTypeIcon = new JLabel("");
        issueTypeIcon.setBounds(25, 25, 20, 20);
        add(issueTypeIcon);

        issueTypeScrollPane = new JScrollPane();
        issueTypeSummaryScrollPane = new JScrollPane();

        issueTypeScrollPane.setBounds(25, 50, 650, 240);
        issueTypeScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueTypeScrollPane.setBorder(BorderFactory.createEmptyBorder());
        //issueType1ScrollPane.setForeground(new Color(247, 249, 252));
        add(issueTypeScrollPane);

        issueTypeSummaryScrollPane.setBounds(700, 50, 300, 240);
        issueTypeSummaryScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueTypeSummaryScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(issueTypeSummaryScrollPane);
        issueTypeLabel.setText(issueType);
        try {
            URL url = new URL(DBHelper.getInstance().getIssueTypeIcon(issueType));
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
