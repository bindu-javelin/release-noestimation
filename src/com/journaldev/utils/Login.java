package com.journaldev.utils;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;  
import java.io.DataOutputStream;  
import java.io.InputStream;
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.URL;  
import java.nio.charset.Charset;

import org.json.*;

import com.journaldev.utils.Connection;
import com.journaldev.utils.sendingGetRequest;
import com.journaldev.utils.sendingPostRequest;
/** 
 * @author Ferhat Yalçın
 */  

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtSdsdf;
	private JPasswordField passwordField;
	static JSONArray issues= new JSONArray();

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the frame.
	 */
	

	 public final String USER_AGENT = "Mozilla/5.0";  
	 public static final String JQL_URL = "http://jira.genband.com/rest/api/latest/search?jql=";
	 public static String username;
	 public static String password;
	
 public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 471);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    username=txtSdsdf.getText();
			    password=passwordField.getText();
				//System.out.print(username+" "+password);
			  
			    boolean responseArr;
			    Connection connect=new Connection(username,password);
			    
			    
			
			    try {
					issues=Connection.connection();
					 
					responseArr=Connection.rcode;
					if(responseArr){
				    System.out.println("Başarılı");
					frame1 frm=new frame1();
				    frm.frame.setVisible(true);
					
					//System.out.println(issues);
					
			    }
				else{
					System.out.println("Başarısız");
					
				}
			
					 
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			    
			    
			      
			    
			    
			   
			
			    
			  //  
			    
				
				
				
			
			/*
				sendingGetRequest sendGet=new sendingGetRequest();
				  sendingPostRequest sendPost=new sendingPostRequest(username, password);
				  
				  String[] responseArr;
				try {
					
					responseArr = sendPost.sendingPostRequest1();
					if(responseArr[0].equals("200")){
						System.out.println("Başarılı");
						sendGet.sendingGetRequest("http://jira.genband.com/rest/api/latest/search?jql=assignee=mkilic and resolution=unresolved",responseArr[1]);
						
						
						
						
					}else{
						System.out.println("Hata");
					}
					} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				 */
				
				
				
				
				
				

				
				
			}
		});
		btnNewButton.setBackground(SystemColor.activeCaption);
		btnNewButton.setBounds(314, 269, 155, 40);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("D:\\Users\\ferhaty\\Desktop\\1475076550_lock.png"));
		lblNewLabel.setBounds(65, 137, 144, 128);
		contentPane.add(lblNewLabel);
		
		txtSdsdf = new JTextField();
		txtSdsdf.setText("ferhaty");
		txtSdsdf.setBounds(314, 165, 155, 34);
		contentPane.add(txtSdsdf);
		txtSdsdf.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("Fds");
		passwordField.setBounds(314, 210, 155, 34);
		contentPane.add(passwordField);
		
		JLabel lblUsername = new JLabel("Username : ");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(219, 168, 85, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(219, 213, 85, 14);
		contentPane.add(lblPassword);
	}
}
