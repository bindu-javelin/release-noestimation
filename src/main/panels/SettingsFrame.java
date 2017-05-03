package main.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;

/**
 * Created by ferhaty
 */
public class SettingsFrame extends JFrame {

    private JPanel contentPane;
    private JTextField authUrlTF;
    private JTextField jqlUrlTF;
    private JTextField maxResultTF;
    private JTextField lastPulLTF;
    private JTextField baseUrlTF;


    Properties prop = new Properties();
    InputStream input = null;

    public SettingsFrame() {
        this.setTitle("Settings");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 700, 330);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(247, 249, 252));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        baseUrlTF = new JTextField();
        baseUrlTF.setColumns(10);
        baseUrlTF.setFont(new Font("Verdana", Font.ITALIC, 14));
        baseUrlTF.setBounds(132, 40, 445, 25);
        contentPane.add(baseUrlTF);

        authUrlTF = new JTextField();
        authUrlTF.setFont(new Font("Verdana", Font.ITALIC, 14));
        authUrlTF.setBounds(132, 80, 445, 25);
        contentPane.add(authUrlTF);
        authUrlTF.setColumns(10);

        jqlUrlTF = new JTextField();
        jqlUrlTF.setFont(new Font("Verdana", Font.ITALIC, 14));
        jqlUrlTF.setBounds(132, 120, 445, 25);
        contentPane.add(jqlUrlTF);
        jqlUrlTF.setColumns(10);

        maxResultTF = new JTextField();
        maxResultTF.setColumns(10);
        maxResultTF.setFont(new Font("Verdana", Font.ITALIC, 14));
        maxResultTF.setBounds(132, 160, 86, 25);
        contentPane.add(maxResultTF);

        lastPulLTF = new JTextField();
        lastPulLTF.setColumns(10);
        lastPulLTF.setFont(new Font("Verdana", Font.ITALIC, 14));
        lastPulLTF.setBounds(132, 200, 86, 25);
        contentPane.add(lastPulLTF);

        JLabel lblBaseUrl = new JLabel("BASE URL  ");
        lblBaseUrl.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        lblBaseUrl.setBounds(26, 40, 137, 20);
        contentPane.add(lblBaseUrl);

        JLabel lblAuthUrl = new JLabel("AUTH URL  ");
        lblAuthUrl.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        lblAuthUrl.setBounds(26, 80, 137, 20);
        contentPane.add(lblAuthUrl);

        JLabel lblJqlurl = new JLabel("JQL URL  ");
        lblJqlurl.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        lblJqlurl.setBounds(26, 120, 74, 20);
        contentPane.add(lblJqlurl);

        JLabel lblMaxResult = new JLabel("MAX RESULT  ");
        lblMaxResult.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        lblMaxResult.setBounds(26, 160, 100, 20);
        contentPane.add(lblMaxResult);

        JLabel lblLastPull = new JLabel("CACHE TIME  ");
        lblLastPull.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        lblLastPull.setBounds(26, 200, 89, 20);
        contentPane.add(lblLastPull);

        try {
            File f = new File("config.properties");
            if(f.exists() && !f.isDirectory()) {
                input = new FileInputStream("config.properties");

                // load a properties file
                prop.load(input);

                baseUrlTF.setText(prop.getProperty("BASE_URL"));
                authUrlTF.setText(prop.getProperty("AUTH_URL"));
                jqlUrlTF.setText(prop.getProperty("JQL_URL"));
                maxResultTF.setText(prop.getProperty("MAX_RESULT"));
                lastPulLTF.setText(prop.getProperty("CACHE_TIME"));
            } else {
                try {
                    prop.setProperty("BASE_URL", "");
                    prop.setProperty("AUTH_URL", "");
                    prop.setProperty("JQL_URL", "");
                    prop.setProperty("MAX_RESULT", "");
                    prop.setProperty("CACHE_TIME", "");
                    prop.store(new FileOutputStream("config.properties"), null);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton saveButton = new JButton("SAVE");
        saveButton.setBounds(134,240, 170, 40);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    prop.setProperty("BASE_URL", baseUrlTF.getText());
                    prop.setProperty("AUTH_URL", authUrlTF.getText());
                    prop.setProperty("JQL_URL", jqlUrlTF.getText());
                    prop.setProperty("MAX_RESULT", maxResultTF.getText());
                    prop.setProperty("CACHE_TIME", lastPulLTF.getText());
                    prop.store(new FileOutputStream("config.properties"), null);
                    JOptionPane.showMessageDialog(null, "Settings are saved succesfully!");
                    setVisible(false);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(saveButton);

    }

}
