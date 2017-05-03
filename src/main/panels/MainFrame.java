package main.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by ferhaty on 12/7/2016.
 */
public class MainFrame extends JFrame {


    public static Properties PROP;
    public static int LOGIN_PANEL = 0, CONFIGURATION_PANEL = 1, THROUGHPUT_PANEL = 2, ISSUE_PANEL = 3, ANALYZING_PANEL = 4;
    public static int WINDOW_WIDTH = 1050, WINDOW_HEIGHT=700;
    public static ArrayList<AbstractJPanel> jPanelList;
    private static JPanel mainPanel;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public MainFrame(){
        PROP = new Properties();
        try {
            PROP.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);

        jPanelList = new ArrayList<>();
        jPanelList.add(new LoginPanel());
        jPanelList.add(new ConfigurationPanel());
        jPanelList.add(new ThroughputPanel());
        jPanelList.add(new IssuePanel());
        jPanelList.add(new AnalyzingPanel());

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(230, 242, 255));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        mainPanel.setLayout(null);
        setContentPane(mainPanel);
        changePanel(LOGIN_PANEL);
        setTitle("Login");

    }

    public static void changePanel(int panelId ){
        mainPanel.invalidate();
        mainPanel.removeAll();
        mainPanel.add(jPanelList.get(panelId));
        mainPanel.revalidate();
        mainPanel.repaint();
    }



}
