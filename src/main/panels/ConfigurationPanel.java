package main.panels;

import main.connection.HttpRequest;
import main.database.DBHelper;
import main.parser.StatusParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConfigurationPanel extends AbstractJPanel {

    public ConfigurationPanel() {
        super();
    }

    public void init(){
        ((JFrame)getRootPane().getParent()).setTitle("Configuration");
        JLabel jqlIcon = new JLabel("");
        jqlIcon.setBounds(200, 215, 15, 30);
        add(jqlIcon);

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

        JComboBox issueType1StartedFromComboBox = new JComboBox();
        issueType1StartedFromComboBox.setBounds(325, 320, 175, 25);
        add(issueType1StartedFromComboBox);

        JComboBox issueType1StartedToComboBox = new JComboBox();
        issueType1StartedToComboBox.setBounds(325, 355, 175, 25);
        add(issueType1StartedToComboBox);

        JComboBox issueType1CompletedFromComboBox = new JComboBox();
        issueType1CompletedFromComboBox.setBounds(325, 420, 175, 25);
        add(issueType1CompletedFromComboBox);

        JComboBox issueType1CompletedToComboBox = new JComboBox();
        issueType1CompletedToComboBox.setBounds(325, 455, 175, 25);
        add(issueType1CompletedToComboBox);

        JComboBox issueType2StartedFromComboBox = new JComboBox();
        issueType2StartedFromComboBox.setBounds(640, 320, 175, 25);
        add(issueType2StartedFromComboBox);

        JComboBox issueType2StartedToComboBox = new JComboBox();
        issueType2StartedToComboBox.setBounds(640, 355, 175, 25);
        add(issueType2StartedToComboBox);

        JComboBox issueType2CompletedFromComboBox = new JComboBox();
        issueType2CompletedFromComboBox.setBounds(640, 420, 175, 25);
        add(issueType2CompletedFromComboBox);

        JComboBox issueType2CompletedToComboBox = new JComboBox();
        issueType2CompletedToComboBox.setBounds(640, 455, 175, 25);
        add(issueType2CompletedToComboBox);

        ArrayList<String> statusList = DBHelper.getStatus();

        issueType1CompletedFromComboBox.addItem("-- Any --");
        issueType1CompletedToComboBox.addItem("-- Any --");
        issueType1StartedFromComboBox.addItem("-- Any --");
        issueType1StartedToComboBox.addItem("-- Any --");

        issueType2CompletedFromComboBox.addItem("-- Any --");
        issueType2CompletedToComboBox.addItem("-- Any --");
        issueType2StartedFromComboBox.addItem("-- Any --");
        issueType2StartedToComboBox.addItem("-- Any --");

        for(int i =0; i<statusList.size(); i++){
            String status = statusList.get(i);
            issueType1CompletedFromComboBox.addItem(status);
            issueType1CompletedToComboBox.addItem(status);
            issueType1StartedFromComboBox.addItem(status);
            issueType1StartedToComboBox.addItem(status);

            issueType2CompletedFromComboBox.addItem(status);
            issueType2CompletedToComboBox.addItem(status);
            issueType2StartedFromComboBox.addItem(status);
            issueType2StartedToComboBox.addItem(status);
        }



        JLabel issueTypeLabel1 = new JLabel("Issue Type");
        issueTypeLabel1.setForeground(SystemColor.inactiveCaptionText);
        //issueTypeLabel1.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 15));
        issueTypeLabel1.setFont(new Font("Verdana", Font.ITALIC, 15));
        issueTypeLabel1.setBounds(225, 260, 90, 25);
        add(issueTypeLabel1);

        JComboBox issueTypeComboBox1 = new JComboBox();
        issueTypeComboBox1.setBounds(325, 260, 200, 25);
        add(issueTypeComboBox1);

        JLabel issueType1StartedLabel = new JLabel("Started Date");
        issueType1StartedLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType1StartedLabel.setFont(new Font("Verdana", Font.ITALIC, 15));
        issueType1StartedLabel.setBounds(230, 295, 150, 25);
        add(issueType1StartedLabel);

        JLabel issueType1CreatedFromLabel = new JLabel("FROM");
        issueType1CreatedFromLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType1CreatedFromLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
        issueType1CreatedFromLabel.setBounds(240, 325, 60, 25);
        add(issueType1CreatedFromLabel);

        JLabel issueType1CreatedToLabel = new JLabel("TO");
        issueType1CreatedToLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType1CreatedToLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
        issueType1CreatedToLabel.setBounds(240, 355, 60, 25);
        add(issueType1CreatedToLabel);

        JLabel issueType1CompletedLabel = new JLabel("Completed Date");
        issueType1CompletedLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType1CompletedLabel.setFont(new Font("Verdana", Font.ITALIC, 15));
        issueType1CompletedLabel.setBounds(230, 390, 150, 25);
        add(issueType1CompletedLabel);

        JLabel issueType1CompletedFromLabel = new JLabel("FROM");
        issueType1CompletedFromLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType1CompletedFromLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
        issueType1CompletedFromLabel.setBounds(240, 420, 60, 25);
        add(issueType1CompletedFromLabel);

        JLabel issueType1CompletedToLabel = new JLabel("TO");
        issueType1CompletedToLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType1CompletedToLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
        issueType1CompletedToLabel.setBounds(240, 455, 60, 25);
        add(issueType1CompletedToLabel);

        JLabel issueTypeLabel2 = new JLabel("Issue Type");
        issueTypeLabel2.setForeground(SystemColor.inactiveCaptionText);
        issueTypeLabel2.setFont(new Font("Verdana", Font.ITALIC, 15));
        issueTypeLabel2.setBounds(545, 260, 90, 25);
        add(issueTypeLabel2);

        JComboBox issueTypeComboBox2 = new JComboBox();
        issueTypeComboBox2.setBounds(640, 260, 200, 25);
        add(issueTypeComboBox2);

        JLabel issueType2StartedLabel = new JLabel("Started Date");
        issueType2StartedLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType2StartedLabel.setFont(new Font("Verdana", Font.ITALIC, 15));
        issueType2StartedLabel.setBounds(550, 295, 150, 25);
        add(issueType2StartedLabel);

        JLabel issueType2CreatedFromLabel = new JLabel("FROM");
        issueType2CreatedFromLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType2CreatedFromLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
        issueType2CreatedFromLabel.setBounds(560, 325, 60, 25);
        add(issueType2CreatedFromLabel);

        JLabel issueType2CreatedToLabel = new JLabel("TO");
        issueType2CreatedToLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType2CreatedToLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
        issueType2CreatedToLabel.setBounds(560, 355, 60, 25);
        add(issueType2CreatedToLabel);

        JLabel issueType2CompletedLabel = new JLabel("Completed Date");
        issueType2CompletedLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType2CompletedLabel.setFont(new Font("Verdana", Font.ITALIC, 15));
        issueType2CompletedLabel.setBounds(550, 390, 150, 25);
        add(issueType2CompletedLabel);

        JLabel issueType2CompletedFromLabel = new JLabel("FROM");
        issueType2CompletedFromLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType2CompletedFromLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
        issueType2CompletedFromLabel.setBounds(560, 420, 60, 25);
        add(issueType2CompletedFromLabel);

        JLabel issueType2CompletedToLabel = new JLabel("TO");
        issueType2CompletedToLabel.setForeground(SystemColor.inactiveCaptionText);
        issueType2CompletedToLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
        issueType2CompletedToLabel.setBounds(560, 455, 60, 25);
        add(issueType2CompletedToLabel);


        JButton backButton = new JButton("Delete All Projects ");
        backButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        backButton.setBounds(25, 600, 150, 35);
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             //  DBHelper.init();
                DBHelper.deleteAll();
                //MainFrame.changePanel(MainFrame.THROUGHPUT_PANEL -1);
            }
        });

        JButton nextButton = new JButton("Next -->");
        nextButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        nextButton.setBounds(900, 600, 100, 35);
        add(nextButton);
        ArrayList<String> issueTypeList = DBHelper.getIssueTypes();
        issueTypeComboBox2.addItem("");
        for(int i =0; i<issueTypeList.size(); i++){
            String issueType = issueTypeList.get(i);
            issueTypeComboBox1.addItem(issueType);
            issueTypeComboBox2.addItem(issueType);
        }

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.changePanel(MainFrame.THROUGHPUT_PANEL);
                ArrayList<String> fromToStringList = new ArrayList<String>();
                fromToStringList.add(issueType1CompletedFromComboBox.getSelectedItem().toString());
                fromToStringList.add(issueType1CompletedToComboBox.getSelectedItem().toString());
                fromToStringList.add(issueType1StartedFromComboBox.getSelectedItem().toString());
                fromToStringList.add(issueType1StartedToComboBox.getSelectedItem().toString());

                fromToStringList.add(issueType2CompletedFromComboBox.getSelectedItem().toString());
                fromToStringList.add(issueType2CompletedToComboBox.getSelectedItem().toString());
                fromToStringList.add(issueType2StartedFromComboBox.getSelectedItem().toString());
                fromToStringList.add(issueType2StartedToComboBox.getSelectedItem().toString());
                ((ThroughputPanel)(MainFrame.jPanelList.get(MainFrame.THROUGHPUT_PANEL))).init(issueType2StartedFromComboBox.getSelectedItem().toString(),
                        issueType2StartedToComboBox.getSelectedItem().toString(),jqlField.getText(),issueTypeComboBox1.getSelectedItem().toString(),
                        issueTypeComboBox2.getSelectedItem().toString(),fromToStringList);

            }
        });

    }
    public void changeTitle(){
        ((JFrame)getRootPane().getParent()).setTitle("Configuration");
    }

}
