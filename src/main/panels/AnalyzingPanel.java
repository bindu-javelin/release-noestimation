

package main.panels;

import main.database.DBHelper;
import main.functions.GlobalFunctions;
import main.view.CreateChart;
import main.view.JTableAnalyzing;
import main.view.JTableIssue;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class AnalyzingPanel extends AbstractJPanel {
    JLabel issueType1Label;
    JLabel issueType1Icon;

    JLabel issueType2Label;
    JLabel issueType2Icon;

    JLabel issueType1ScopeLabel;
    JLabel issueType2ScopeLabel;

    JTextField issueType1ScopeText;
    JTextField issueType2ScopeText;

    JScrollPane issueType1ScrollPane;
    JScrollPane issueType2ScrollPane;

    JScrollPane issueType1SummaryScrolLPane;
    JScrollPane issueType2SummaryScrolLPane;

    JScrollPane issueType1DeliveryTimeScrollPane;
    JScrollPane issueType2DeliveryTimeScrollPane;

    JScrollPane issueType1SummaryDeliveryScrollPane;
    JScrollPane issueType2SummaryDeliveryScrollPane;

    JScrollPane issueType1ResultPane;
    JScrollPane issueType2ResultPane;

    JTableIssue jTableIssueForIssueType1;
    JTableIssue jTableIssueForIssueType2;
    JTableAnalyzing jTableAnalyzingForIssueType1;
    JTableAnalyzing jTableAnalyzingForIssueType2;

    JScrollPane jScrollPaneResultForBoth;
    double avg = 0;
    double perc85 = 0 ;
    double perc95 = 0;

    ChartFrame chartFrame;

    public AnalyzingPanel() {

        issueType1Label = new JLabel();
        issueType1Label.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
        issueType1Label.setBounds(50, 25, 500, 20);
        add(issueType1Label);

        issueType1Icon = new JLabel("");
        issueType1Icon.setBounds(25, 25, 20, 20);
        add(issueType1Icon);

        issueType1ScrollPane = new JScrollPane();
        issueType1ScrollPane.setBounds(25, 50, 150, 245);
        issueType1ScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueType1ScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        add(issueType1ScrollPane);

        issueType1SummaryScrolLPane = new JScrollPane();
        issueType1SummaryScrolLPane.setBorder(BorderFactory.createEmptyBorder());
        issueType1SummaryScrolLPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType1SummaryScrolLPane.setBounds(200, 50, 180, 35);
        add(issueType1SummaryScrolLPane);

        issueType1DeliveryTimeScrollPane = new JScrollPane();
        issueType1DeliveryTimeScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueType1DeliveryTimeScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType1DeliveryTimeScrollPane.setBounds(200, 100, 180, 195);
        add(issueType1DeliveryTimeScrollPane);

         issueType1SummaryDeliveryScrollPane = new JScrollPane();
        issueType1SummaryDeliveryScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueType1SummaryDeliveryScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType1SummaryDeliveryScrollPane.setBounds(560, 50, 250, 245);
        add(issueType1SummaryDeliveryScrollPane);

        issueType1ResultPane = new JScrollPane();
        issueType1ResultPane.setBorder(BorderFactory.createEmptyBorder());
        issueType1ResultPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType1ResultPane.setBounds(850,50,150,245);
        add(issueType1ResultPane);

        issueType1ScopeLabel = new JLabel("Scope");
        issueType1ScopeLabel.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
        issueType1ScopeLabel.setBounds(440, 125, 120, 20);
        add(issueType1ScopeLabel);

        issueType2ScopeLabel = new JLabel("Scope");
        issueType2ScopeLabel.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
        issueType2ScopeLabel.setBounds(440, 420, 120, 20);
        add(issueType2ScopeLabel);

        issueType1ScopeText = new JTextField();
        issueType1ScopeText.setBounds(400, 155, 140, 35);
        issueType1ScopeText.setHorizontalAlignment(JTextField.CENTER);
        issueType1ScopeText.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 18));
        add(issueType1ScopeText);

        issueType2ScopeText = new JTextField();
        issueType2ScopeText.setBounds(400, 450, 140, 35);
        issueType2ScopeText.setHorizontalAlignment(JTextField.CENTER);
        issueType2ScopeText.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 18));
        add(issueType2ScopeText);


        issueType2Label = new JLabel();
        issueType2Label.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
        issueType2Label.setBounds(50, 320, 500, 20);
        add(issueType2Label);

        issueType2Icon = new JLabel("");
        issueType2Icon.setBounds(25, 320, 20, 20);
        add(issueType2Icon);

        issueType2ScrollPane = new JScrollPane();
        issueType2ScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueType2ScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType2ScrollPane.setBounds(25, 345, 150, 245);
        add(issueType2ScrollPane);

        issueType2SummaryScrolLPane = new JScrollPane();
        issueType2SummaryScrolLPane.setBorder(BorderFactory.createEmptyBorder());
        issueType2SummaryScrolLPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType2SummaryScrolLPane.setBounds(200, 345, 180, 35);
        add(issueType2SummaryScrolLPane);

        issueType2DeliveryTimeScrollPane = new JScrollPane();
        issueType2DeliveryTimeScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueType2DeliveryTimeScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType2DeliveryTimeScrollPane.setBounds(200, 395, 180, 195);
        add(issueType2DeliveryTimeScrollPane);

         issueType2SummaryDeliveryScrollPane = new JScrollPane();
        issueType2SummaryDeliveryScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueType2SummaryDeliveryScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType2SummaryDeliveryScrollPane.setBounds(560, 345, 250, 245);
        add(issueType2SummaryDeliveryScrollPane);

        issueType2ResultPane = new JScrollPane();
        issueType2ResultPane.setBorder(BorderFactory.createEmptyBorder());
        issueType2ResultPane.getViewport().setBackground(new Color(247, 249, 252));
        issueType2ResultPane.setBounds(850,345,150,245);
        add(issueType2ResultPane);


        jScrollPaneResultForBoth = new JScrollPane();
        jScrollPaneResultForBoth.setBorder(BorderFactory.createEmptyBorder());
        jScrollPaneResultForBoth.getViewport().setBackground(new Color(247, 249, 252));
        jScrollPaneResultForBoth.setBounds(395, 295, 150, 55);
        add(jScrollPaneResultForBoth);

        JButton backButton = new JButton("<-- Back");
        backButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        backButton.setBounds(25, 600, 100, 35);
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //control=true;
                avg= 0;
                perc85=0;
                perc95=0;
               MainFrame.changePanel(MainFrame.ANALYZING_PANEL -1);
                ((IssuePanel)(MainFrame.jPanelList.get(MainFrame.ISSUE_PANEL))).changeTitle();


            }
        });

        JButton createHistogramButton = new JButton("Create Histogram");
        createHistogramButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        createHistogramButton.setBounds(850, 600, 150, 35);
        add(createHistogramButton);

        createHistogramButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createHistogram();
            }
        });


        JButton reCalculateButton = new JButton("Re-Calculate");
        reCalculateButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        reCalculateButton.setBounds(420, 600, 120, 35);
        add(reCalculateButton);

        reCalculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reCalculate(issueType1ScopeText,issueType2ScopeText);
                ((JFrame)getRootPane().getParent()).setTitle("Analyzing");
            }
        });




    }

    public void init(JTableIssue jTableIssueForIssueType1,JTableIssue jTableIssueForIssueType2){
        ((JFrame)getRootPane().getParent()).setTitle("Analyzing");
        this.jTableIssueForIssueType1 = jTableIssueForIssueType1;
        this.jTableIssueForIssueType2 = jTableIssueForIssueType2;
        chartFrame = new ChartFrame();
        chartFrame.setVisible(false);
        ThroughputPanel throughputPanel = (ThroughputPanel) MainFrame.jPanelList.get(MainFrame.THROUGHPUT_PANEL);

        issueType1Label.setText(throughputPanel.getIssueType1());
        try {
            URL url = new URL(DBHelper.getIssueTypeIcon(issueType1Label.getText()));
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon  = new ImageIcon(img);
            issueType1Icon.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (throughputPanel.issueType2List.size() != 0) {
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

        if(throughputPanel.getIssueType2().equals("")){
            issueType2Icon.setVisible(false);
            issueType2ScopeLabel.setVisible(false);
            issueType2ScopeText.setVisible(false);
        }else if(throughputPanel.issueType2List.size() == 0){
            issueType2Icon.setVisible(false);
            issueType2ScopeLabel.setVisible(false);
            issueType2ScopeText.setVisible(false);
        }else{
            issueType2Icon.setVisible(true);
            issueType2ScopeLabel.setVisible(true);
            issueType2ScopeText.setVisible(true);

        }
        jTableAnalyzingForIssueType1 = new JTableAnalyzing();
        issueType1ScrollPane.setViewportView(jTableAnalyzingForIssueType1.createTakTimeAndResamplingTable(jTableIssueForIssueType1));
        issueType1SummaryScrolLPane.setViewportView(jTableAnalyzingForIssueType1.createSummaryDeliveryTimeTable(1));
        issueType1DeliveryTimeScrollPane.setViewportView(jTableAnalyzingForIssueType1.createDeliveryTimeSimulationTable());
        issueType1SummaryDeliveryScrollPane.setViewportView(jTableAnalyzingForIssueType1.createStatisticsTable());
        issueType1ResultPane.setViewportView(jTableAnalyzingForIssueType1.createResultTable());


            avg+= jTableAnalyzingForIssueType1.getAvg();
            perc85 += jTableAnalyzingForIssueType1.getPerc85();
            perc95 += jTableAnalyzingForIssueType1.getPerc95();

        if(jTableIssueForIssueType2.getShowWipOnTable()!=null){

            jTableAnalyzingForIssueType2 = new JTableAnalyzing();
            issueType2ScrollPane.setViewportView(jTableAnalyzingForIssueType2.createTakTimeAndResamplingTable(jTableIssueForIssueType2));
            issueType2SummaryScrolLPane.setViewportView(jTableAnalyzingForIssueType2.createSummaryDeliveryTimeTable(2));
            issueType2DeliveryTimeScrollPane.setViewportView(jTableAnalyzingForIssueType2.createDeliveryTimeSimulationTable());
            issueType2SummaryDeliveryScrollPane.setViewportView(jTableAnalyzingForIssueType2.createStatisticsTable());
            issueType2ResultPane.setViewportView(jTableAnalyzingForIssueType2.createResultTable());


                avg+= jTableAnalyzingForIssueType2.getAvg();
                perc85 += jTableAnalyzingForIssueType2.getPerc85();
                perc95 += jTableAnalyzingForIssueType2.getPerc95();


        }

        jScrollPaneResultForBoth.setViewportView(createResultTableForBoth(String.valueOf(avg),String.valueOf(perc85),String.valueOf(perc95)));

    }
    public void reCalculate(JTextField issueType1ScopeText,JTextField issueType2ScopeText){

        avg = 0;
        perc85 = 0;
        perc95 = 0;
        if(!issueType1ScopeText.getText().equals("")){
            int issueType1Scope= Integer.parseInt(issueType1ScopeText.getText());
            issueType1ScrollPane.setViewportView(jTableAnalyzingForIssueType1.createTakTimeAndResamplingTable(jTableIssueForIssueType1,issueType1Scope));
            issueType1SummaryScrolLPane.setViewportView(jTableAnalyzingForIssueType1.createSummaryDeliveryTimeTable(1,issueType1Scope));
            issueType1DeliveryTimeScrollPane.setViewportView(jTableAnalyzingForIssueType1.createDeliveryTimeForNProjectSimulationTable(issueType1Scope));
            issueType1SummaryDeliveryScrollPane.setViewportView(jTableAnalyzingForIssueType1.createStatisticsTable());
            issueType1ResultPane.setViewportView(jTableAnalyzingForIssueType1.createResultTable());
            avg+= jTableAnalyzingForIssueType1.getAvg();
            perc85+= jTableAnalyzingForIssueType1.getPerc85();
            perc95+= jTableAnalyzingForIssueType1.getPerc95();
        }

        if(jTableIssueForIssueType2.getShowWipOnTable()!=null){
            if(!issueType2ScopeText.getText().equals("")) {
                int issueType2Scope= Integer.parseInt(issueType2ScopeText.getText());
                issueType2ScrollPane.setViewportView(jTableAnalyzingForIssueType2.createTakTimeAndResamplingTable(jTableIssueForIssueType2,issueType2Scope));
                issueType2SummaryScrolLPane.setViewportView(jTableAnalyzingForIssueType2.createSummaryDeliveryTimeTable(2,issueType2Scope));
                issueType2DeliveryTimeScrollPane.setViewportView(jTableAnalyzingForIssueType2.createDeliveryTimeForNProjectSimulationTable(issueType2Scope));
                issueType2SummaryDeliveryScrollPane.setViewportView(jTableAnalyzingForIssueType2.createStatisticsTable());
                issueType2ResultPane.setViewportView(jTableAnalyzingForIssueType2.createResultTable());
                avg+= jTableAnalyzingForIssueType2.getAvg();
                perc85+= jTableAnalyzingForIssueType2.getPerc85();
                perc95+= jTableAnalyzingForIssueType2.getPerc95();
            }

        }
        jScrollPaneResultForBoth.setViewportView(createResultTableForBoth(String.valueOf(avg),String.valueOf(perc85),String.valueOf(perc95)));
        createHistogram();
    }

    public void createHistogram(){
        chartFrame.setVisible(true);
        CreateChart createChart = new CreateChart();
        ThroughputPanel throughputPanel = (ThroughputPanel) MainFrame.jPanelList.get(MainFrame.THROUGHPUT_PANEL);
        chartFrame.getContentPane().removeAll();
        chartFrame.add(createChart.createChartPanel(jTableAnalyzingForIssueType1.getProbability(),
                jTableAnalyzingForIssueType1.getDeliveryTime(),jTableAnalyzingForIssueType1.getCdf(),throughputPanel.getIssueType1(),1));

        if(!throughputPanel.getIssueType2().equals("")){

            chartFrame.add(createChart.createChartPanel(jTableAnalyzingForIssueType2.getProbability(),
                    jTableAnalyzingForIssueType2.getDeliveryTime(),jTableAnalyzingForIssueType2.getCdf(),throughputPanel.getIssueType2(),2));

        }
        chartFrame.getContentPane().invalidate();
        chartFrame.getContentPane().validate();
    }

    public JTable createResultTableForBoth(String avg, String perc85, String perc95) {
        JTable tableLastStatistics = new JTable();
        tableLastStatistics.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = tableLastStatistics.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.ITALIC | Font.BOLD, 14));
        tableLastStatistics.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "", ""
                }
        ));

        int numCols5 = tableLastStatistics.getModel().getColumnCount();
        Object[][] showStatisticsOnTable = new Object[3][numCols5];
        showStatisticsOnTable[0][0] = "Average T";
        showStatisticsOnTable[0][1] = avg;
        showStatisticsOnTable[1][0] = "85 Perc";
        showStatisticsOnTable[1][1] = perc85;
        showStatisticsOnTable[2][0] = "95 Perc";
        showStatisticsOnTable[2][1] = perc95;
        for (int i = 0; i < 3; i++) {

            ((DefaultTableModel) tableLastStatistics.getModel()).addRow(showStatisticsOnTable[i]);

        }
        tableLastStatistics.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableLastStatistics.getColumnModel().getColumn(1).setPreferredWidth(50);
        return tableLastStatistics;
    }

}
