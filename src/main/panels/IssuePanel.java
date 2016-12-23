package main.panels;

import main.classes.Issue;
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
    static JScrollPane issueType1ScrollPane;
    static JScrollPane issueType1WipScrollPane;

    static JScrollPane issueType2ScrolLPane;
    static JScrollPane issueType2WipScrollPane;

    static JTableIssue jTableIssueForIssueType1;
    static JTableIssue jTableIssueForIssueType2;

    JLabel issueType1Label;
    JLabel issueType1Icon;

    JLabel issueType2Label;
    JLabel issueType2Icon;

    public IssuePanel() {
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
        issueType1WipScrollPane = new JScrollPane();

        issueType2ScrolLPane = new JScrollPane();
        issueType2WipScrollPane = new JScrollPane();

        issueType1ScrollPane.setBounds(25, 50, 800, 240);
        issueType1ScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueType1ScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        add(issueType1ScrollPane);

        issueType1WipScrollPane.setBounds(850, 50, 150, 240);
        issueType1WipScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueType1WipScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        add(issueType1WipScrollPane);

        issueType2ScrolLPane.setBounds(25, 350, 800, 240);
        issueType2ScrolLPane.setBorder(BorderFactory.createEmptyBorder());
        issueType2ScrolLPane.getViewport().setBackground(new Color(247, 249, 252));
        add(issueType2ScrolLPane);

        issueType2WipScrollPane.setBounds(850, 350, 150, 240);
        issueType2WipScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueType2WipScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        add(issueType2WipScrollPane);

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
                ((AnalyzingPanel)(MainFrame.jPanelList.get(MainFrame.ANALYZING_PANEL))).init(jTableIssueForIssueType1,jTableIssueForIssueType2);

            }
        });
    }




    public void init(ArrayList<Issue> issueType1List, ArrayList<Issue> issueType2List, JTableThroughput jTableThroughputForIssueType1,JTableThroughput jTableThroughputForIssueType2) {
       ((JFrame)getRootPane().getParent()).setTitle("İssues");
        fillScrollPanes(issueType1List, issueType2List, jTableThroughputForIssueType1, jTableThroughputForIssueType2);

        ThroughputPanel throughputPanel = (ThroughputPanel) MainFrame.jPanelList.get(MainFrame.THROUGHPUT_PANEL);
        issueType1Label.setText(throughputPanel.getIssueType1());
        try {
            URL url = new URL(DBHelper.getIssueTypeIcon(issueType1Label.getText()));
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(img);
            issueType1Icon.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (issueType2List.size() != 0) {
            issueType2Label.setText(throughputPanel.getIssueType2());
            try {
                URL url = new URL(DBHelper.getIssueTypeIcon(issueType2Label.getText()));
                BufferedImage img = ImageIO.read(url);
                ImageIcon icon = new ImageIcon(img);
                issueType2Icon.setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void changeTitle(){
        ((JFrame)getRootPane().getParent()).setTitle("İssues");
    }
    public static void fillScrollPanes(ArrayList<Issue> issueType1List, ArrayList<Issue> issueType2List, JTableThroughput jTableThroughputForIssueType1, JTableThroughput jTableThroughputForIssueType2 ) {
        jTableIssueForIssueType1 = new JTableIssue();
        jTableIssueForIssueType2 = new JTableIssue();

        issueType1ScrollPane.setViewportView(jTableIssueForIssueType1.createIssueTable(issueType1List, jTableThroughputForIssueType1.getThroughputShowTable()));
        issueType1WipScrollPane.setViewportView(jTableIssueForIssueType1.createWipTable(jTableThroughputForIssueType1.getThroughputList(), jTableThroughputForIssueType1.getThroughputShowTable()));

        if (issueType2List.size() > 0) {

            issueType2ScrolLPane.setViewportView(jTableIssueForIssueType2.createIssueTable(issueType2List, jTableThroughputForIssueType2.getThroughputShowTable()));
            issueType2WipScrollPane.setViewportView(jTableIssueForIssueType2.createWipTable(jTableThroughputForIssueType2.getThroughputList(), jTableThroughputForIssueType2.getThroughputShowTable()));
        }
    }

    public static JTableIssue getjTableIssueForIssueType1() {
        return jTableIssueForIssueType1;
    }

    public static void setjTableIssueForIssueType1(JTableIssue jTableIssueForIssueType1) {
        IssuePanel.jTableIssueForIssueType1 = jTableIssueForIssueType1;
    }

    public static JTableIssue getjTableIssueForIssueType2() {
        return jTableIssueForIssueType2;
    }

    public static void setjTableIssueForIssueType2(JTableIssue jTableIssueForIssueType2) {
        IssuePanel.jTableIssueForIssueType2 = jTableIssueForIssueType2;
    }

}
