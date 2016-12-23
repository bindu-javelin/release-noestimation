package main.panels;


import main.connection.HttpRequest;
import main.connection.Session;
import main.database.DBHelper;
import main.parser.SessionParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class LoginPanel extends AbstractJPanel {

    private JTextField usernameField;
    private JTextField passwordField;
    public LoginPanel(){

        super();


        JButton settingsIcon = new JButton();
        settingsIcon.setIcon(new ImageIcon("D:\\Users\\ferhaty\\Desktop\\settings.png"));
        settingsIcon.setBounds(970, 25, 32, 32);
        settingsIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsFrame settingsFrame = new SettingsFrame();
                settingsFrame.setVisible(true);
            }
        });
        add(settingsIcon);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe Print", Font.PLAIN, 14));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String urlString = MainFrame.PROP.getProperty("BASE_URL") + MainFrame.PROP.getProperty("AUTH_URL");
                String postJsonData = "{\"username\":\""+usernameField.getText() +"\",\"password\":\""+passwordField.getText() +"\"}";
                String response = HttpRequest.postRequest(urlString,postJsonData);
                String sessionID = SessionParser.getSessionID(response);
                Session.setSessionID(sessionID);
                DBHelper.init();
                DBHelper.insertStatuses();
                DBHelper.insertIssueType();
                MainFrame.changePanel(MainFrame.CONFIGURATION_PANEL);
                MainFrame.jPanelList.get(MainFrame.CONFIGURATION_PANEL).init();
            }
        });
        loginButton.setBackground(SystemColor.inactiveCaption);
        loginButton.setBounds(555, 345, 155, 40);
        add(loginButton);

        JLabel loginIcon = new JLabel("");
        loginIcon.setIcon(new ImageIcon("D:\\Users\\ferhaty\\Desktop\\1475076550_lock.png"));
        loginIcon.setBounds(285, 245, 150, 130);
        add(loginIcon);

        usernameField = new JTextField();

        usernameField.setBounds(555, 255, 160, 35);
        usernameField.setFont(new Font("Verdana", Font.ITALIC, 15));
        add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Verdana", Font.ITALIC, 15));
        passwordField.setToolTipText("Fds");

        passwordField.setBounds(555, 300, 160, 35);
        add(passwordField);

        JLabel usernameLabel = new JLabel("Username : ");
        usernameLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
        usernameLabel.setBounds(445, 255, 100, 34);
        add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setFont(new Font("Verdana", Font.ITALIC, 14));
        passwordLabel.setBounds(445, 300, 100, 34);
        add(passwordLabel);
    }
}
