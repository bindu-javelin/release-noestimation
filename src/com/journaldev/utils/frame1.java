package com.journaldev.utils;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;









import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.ImageIcon;


public class frame1 {
	static JSONArray issues= new JSONArray();
	static JSONArray histories=new JSONArray();
	static JSONObject changelog= new JSONObject();
	static JSONArray items=new JSONArray();
	static String createdlast;
	static Object[] completed_created;
	static String fromString,toString;
	
	daydiff day=new daydiff();
	
	private static boolean visible;
	JFrame frame;
	private JTable table;
	private JButton btnNewButton;
	private JTable table_1;
	private JButton btnNewButton_1;
	private JScrollPane scrollPane_1;
	private JTable table_2;
	static Object [][] fila;
	static Object [][] fila1;
	
	private JLabel label_1;
	static ArrayList<Date> dates = new ArrayList<Date>();
	static int[] resampling;
	static int[] T_resampling;
	static int dates_size;
	static int[] temp;
	static int[][] temp2;
	static double R_avg;
	static int R_totalday;
	static int temp2_count=0;
	static int[]t;
	
	/**
	 * Launch the application. 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame1 window = new frame1();
					window.frame.setVisible(true);
				
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 777, 626);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JScrollPane scrollPane = new JScrollPane();
		//////////////////////////////////////////////////
		//Login lgn=new Login();
		
		try {

					issues=Login.issues;
					System.out.println("--------------->"+ issues.length());
					
		///////////////////////////////////////////toString--->fromString///////////////////////////////////////////////////////////////
				changelog= issues.getJSONObject(1).getJSONObject("changelog");
				histories= changelog.getJSONArray("histories");
				items= histories.getJSONObject(0).getJSONArray("items");
				createdlast=histories.getJSONObject(0).getString("created");
				fromString=items.getJSONObject(0).getString("fromString");
				toString=items.getJSONObject(0).getString("toString");
				// System.out.println(histories.getJSONObject(0).getString("id"));
				//String fromString,toString;
				completed_created=new Object[issues.length()];
				
				// for(int k=0;k<issues.length();k++){
				for(int k=issues.length()-1;k>=0;k--){
				changelog= issues.getJSONObject(k).getJSONObject("changelog");
				histories= changelog.getJSONArray("histories");
				for(int i=histories.length()-1;i>=0;i--){
				//System.out.println("buraya girdi a");
				
				items= histories.getJSONObject(i).getJSONArray("items");
				//System.out.println("items uzunlugu  "+ items.length());
				for(int j=items.length()-1;j>=0;j--){
				
				fromString=items.getJSONObject(j).getString("fromString");
				toString=items.getJSONObject(j).getString("toString");
				
				if(fromString.equals("Ready to Verify") && toString.equals("Verification Passed")){
					
					  createdlast=histories.getJSONObject(i).getString("created");
				
					 break;
				} 
					 
				
				
				}
				
				
				}completed_created[k]=createdlast;
				//System.out.println(k+"---> "+completed_created[k]);
				
				
				}java.util.Date date3= new java.util.Date();
				System.out.println("tarih getirme bitişi :-->"+new Timestamp(date3.getTime()));
				//changelog= issues.getJSONObject("changelog");
				


		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	
		////////////////////////////////////////////////////
		btnNewButton = new JButton("Get Story");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
			
				
				table = new JTable();
				table.setFont(new Font("Verdana", Font.BOLD, 12));
				table.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Story", "Started Date", "Completed Date", "Time Cycle(Days)"
					}
				));
				
				
				int numCols = table.getModel().getColumnCount();

				fila=new Object[issues.length()][numCols]; 
				/*fila[0] = "unal";
				fila[1] = "420";
				fila[2] = "mundo";
				fila[3]="dfsdf";
				*/
				
				//SimpleDateFormat myFormat = new SimpleDateFormat("dd-mm-yyyy");
				
				for(int i=0;i<issues.length();i++){
					
						try {
							fila[i][0] = issues.getJSONObject(i).getString("id");
							fila[i][1] = day.dateFormat(issues.getJSONObject(i).getJSONObject("fields").getString("created").split("T")[0]);
							fila[i][2] = day.dateFormat(completed_created[i].toString().split("T")[0]);
							fila[i][3]=day.daydiff(fila[i][1], fila[i][2]);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					
					
					
					
				}
				
				
				for(int i=0;i<issues.length();i++){
			   // fila[i] = fila[issues.length() -i- 1  ];
					
					((DefaultTableModel) table.getModel()).addRow(fila[issues.length() -i- 1]);
					
					
					
				}

				table.getColumnModel().getColumn(2).setPreferredWidth(91);
				table.getColumnModel().getColumn(3).setPreferredWidth(99);
				scrollPane.setViewportView(table);
			
				
				
				
				
			}
		});
		
		btnNewButton_1 = new JButton("Wip");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				table_1 = new JTable();
				table_1.setFont(new Font("Verdana", Font.BOLD, 12));
				table_1.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Date", "WIP"
					}
				));
				
				
				int numCols = table_1.getModel().getColumnCount();

			
			
				Date IlkGun;
				Date SonGun;
				String ilk =null;
				String son = null;
		
				
				ilk=(String) fila[issues.length()-1][1];
				//son=(String) fila[0][2];
				son=(String) fila[0][1];
				System.out.println("ilk -->"+ilk);
				System.out.println("son -->"+son);
				
				//////////////////////////////////////////////////////////////////////////////////
				//ArrayList<Date> dates = new ArrayList<Date>();
				String str_date=ilk;
				String end_date=son;
				
				DateFormat formatter ; 

				formatter = new SimpleDateFormat("dd-MMM-yyyy");
				Date startDate = null;
				try {
					startDate = (Date)formatter.parse(str_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				Date endDate = null;
				try {
					endDate = (Date)formatter.parse(end_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				long interval = 24*1000 * 60 * 60; // 1 hour in millis
				long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
				long curTime = startDate.getTime();
				while (curTime <= endTime) {
				    dates.add(new Date(curTime));
				    curTime += interval;
				}
				
				fila1 = new Object[dates.size()][numCols];  
				String []ds=new String[dates.size()];
				int count=0;
		    	int j=issues.length()-1;
		    	resampling=new int[dates.size()];
		    	T_resampling=new int[dates.size()];
		    	dates_size=dates.size();
		    	//int wip_count=0;
		    	int result=0;
		    	long timeFirst,timeLast;
		    	int S_result=0;
		    	int E_result=0;
		    	int B_result=0;
		    	int currentCount= 0;
		    
				for(int i=0;i<dates.size();i++){
					boolean sameDate= true;
				    Date lDate =(Date)dates.get(i);
				     ds[i]=formatter.format(lDate);   
				    
				    try {
						fila1[i][0]=ds[i];
						
						while(j>0 && j<=issues.length() && fila1[i][0].toString().equals(fila[j][1].toString())){
						
							j--;
							currentCount++;
													
						}
						int wip_count=0;
						long ms = formatter.parse(fila1[i][0].toString()).getTime();
						for(int k=j;k<issues.length();k++){
							long startedTime= formatter.parse(fila[k][1].toString()).getTime();
							long completedTime = formatter.parse(fila[k][2].toString()).getTime();
							if(ms>=startedTime && ms<=completedTime){
								wip_count++;
								
							}
							
						}
						resampling[i]=wip_count;
						fila1[i][1]=wip_count;
						
						//System.out.println(resampling[i]);
						
					
						
						
						////sağlam wip
						
						/*while(j>=0 && fila[j][1].toString().equals(fila1[i][0].toString())){
							wip_count++;
							result+=wip_count;
							j--;
						}
				    	
						fila1[i][1]=wip_count;
					
						fila1[i][1]=wip_count;*/
						
				
				    	
						
						
						
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
				
				
				
				temp=new int[1001];
				temp2=new int[1001][dates.size()];
				Random rnd=new Random();
				random rndArray=new random();
				double avg=0;
				t=new int[1001];
				for(int k=1;k<1001;k++){
					
					for(int i=0;i<dates.size();i++){
							T_resampling=rndArray.RandomizeArray(resampling);
							temp2_count+=Integer.valueOf(T_resampling[i]);
							if(i==dates.size()-1){
								
								R_totalday+=temp2_count;
								temp2_count=0;
							}
							
					}
					
				
				//System.out.println(R_totalday);
				t[k]=(int) R_totalday;
				R_totalday=0;
				
					
				
				
				
				System.out.println(dates.size());
				System.out.println(result);
				double n=result/dates.size();
				//System.out.println("Ortalama olarak bir günde yapılan (product defect ve story sayısı) : "+n);
				 
				
				
				//////////////////////////////////////////////////////////////////////////////////
				
				
			
				
				for(int i=0;i<dates.size();i++){
			
					
					((DefaultTableModel) table_1.getModel()).addRow(fila1[i]);
					
					
					
				}

				table_1.getColumnModel().getColumn(0).setPreferredWidth(91);
				table_1.getColumnModel().getColumn(1).setPreferredWidth(99);
				scrollPane_1.setViewportView(table_1);
			
				
				
			}
		});
		
		scrollPane_1 = new JScrollPane();
		
		JLabel lblStory = new JLabel("Story and Product Defect");
		lblStory.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
		
		label_1 = new JLabel("");
		
		JButton btnNewButton_2 = new JButton("Next");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame2 frame = new frame2();
				frame.setVisible(true);
				
				
			}
		});
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblStory, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE))
					.addGap(49)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
						.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStory))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1)
						.addComponent(btnNewButton_2))
					.addGap(20))
		);
		
		table_2 = new JTable();
		scrollPane_1.setViewportView(table_2);
		

		
		frame.getContentPane().setLayout(groupLayout);
	}
}
