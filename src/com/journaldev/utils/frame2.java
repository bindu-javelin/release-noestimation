package com.journaldev.utils;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;







import org.jfree.ui.RefineryUtilities;
import org.json.JSONArray;
import org.json.JSONException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.awt.ScrollPane;




public class frame2 extends JFrame {

	private JPanel contentPane;
	protected Object frame;
	private JTable table;
	frame1 frm1=new frame1();
	static int dates_size;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	Object [][] fila;
	Object [][] fila1;
	Object [][] fila2;
	Object [][] fila3;
	Object [][] fila5;
	double[] probability;
	double[] delivery;
	double[] cdf;
	private JScrollPane scrollPane_1;
	private JTable table_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JTable table_2;
	
	int totalday=0;
	int R_totalday=0;
	double avg=0;
	double R_avg=0;
	private JScrollPane scrollPane_4;
	private JTable table_3;
	private JScrollPane scrollPane_5;
	private JTable table_4;
	private JButton btnNewButton_2;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame2 frame = new frame2();
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
	public frame2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1172, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 134, 397);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setColumnHeaderView(table);
		
		btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				

				
				
				
				//////////////////////////table bölümü/////////////////////////
				

				table = new JTable();
				table.setFont(new Font("Verdana", Font.BOLD, 12));
				table.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Tack Time", "Resampling"
					}
				));
				
				
				int numCols = table.getModel().getColumnCount();
				dates_size=frm1.dates.size();
				fila=new Object[dates_size][numCols]; 
				
				System.out.println("dates size -->"+dates_size);
				
				 for(int i=0;i<dates_size;i++){
						
						fila[i][0] = frm1.fila1[i][1];
						fila[i][1] = frm1.T_resampling[i];
						totalday+=(Integer) fila[i][0];
						R_totalday+=(Integer) fila[i][1];
			
						}
				 
				avg=totalday/dates_size;
		
				
				
				for(int i=0;i<dates_size;i++){
			   // fila[i] = fila[issues.length() -i- 1  ];
					
					((DefaultTableModel) table.getModel()).addRow(fila[i]);
					
					
					
				}

				table.getColumnModel().getColumn(0).setPreferredWidth(91);
				table.getColumnModel().getColumn(1).setPreferredWidth(99);
				scrollPane.setViewportView(table);
				
				//////////////////////////////table_1 bölümü///////////////////////////////////////////////
				table_1 = new JTable();
				table_1.setFont(new Font("Verdana", Font.BOLD, 12));
				table_1.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Scope (N)(work items)", "Delivery time (T)(days)","Average Takt Time (TT)(days)"
					}
				));
				
				
				int numCols2 = table_1.getModel().getColumnCount();
				dates_size=frm1.dates.size();
				fila1=new Object[dates_size][numCols2]; 
				
				System.out.println("dates size -->"+dates_size);
				
			 
						fila1[0][0] = dates_size;
						fila1[0][1] = Double.valueOf(dates_size*avg);
						fila1[0][2] = avg;
				
				
				
				//for(int i=0;i<dates_size;i++){
			   // fila[i] = fila[issues.length() -i- 1  ];
					
				((DefaultTableModel) table_1.getModel()).addRow(fila1[0]);
					
					
					
				//}

				table_1.getColumnModel().getColumn(0).setPreferredWidth(33);
				table_1.getColumnModel().getColumn(1).setPreferredWidth(33);
				table_1.getColumnModel().getColumn(2).setPreferredWidth(33);
				scrollPane_1.setViewportView(table_1);
				
				/////////////////////////////////////table_2 bölümü///////////////////////////////////////////////////////////////////////////////


				table_2 = new JTable();
				table_2.setFont(new Font("Verdana", Font.BOLD, 12));
				table_2.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Scope (N)(work items)", "Delivery time (T)(days)","Average Takt Time (TT)(days)"
					}
				));
				
				
				int numCols3 = table_2.getModel().getColumnCount();
				dates_size=frm1.dates.size();
				fila2=new Object[1001][numCols2]; 
				
				System.out.println("dates size -->"+dates_size);
				/*
			 
				int[] RResampling=new int[dates_size];
						random rndArray=new random();
						for(int i=0;i<dates_size;i++){
							
							RResampling=rndArray.RandomizeArray(frm1.resampling);	
						}
						*/
						int[][] tt=new int[1001][numCols3];
						for(int i=1;i<1001;i++){

						
						fila2[i][0] = i;
						fila2[i][1] =(int)frm1.t[i];
						tt[i][1] =(int)frm1.t[i];
					//	R_avg=
						//fila2[i][2] =Double.valueOf(Integer.valueOf(fila2[i][1].toString()) / dates_size);
						fila2[i][2] =Double.valueOf(fila2[i][1].toString()) / (double)dates_size;
							
				
							}
						
						//Arrays.sort(fila2[1]);
						//System.out.println(tt);
						
					/*	MinMaxValue minmax=new MinMaxValue();
						int min = (int) Collections.min(Arrays.asList(Integer.valueOf(fila2.toString())));
						System.out.println("Maximum Value is: "+min);*/
						Arrays.sort(frm1.t);
						arraysort minmax=new arraysort();
						double min = minmax.getMin(tt);
						System.out.println("min :"+min);
						
						double max = minmax.getMax(tt);
						System.out.println("max :"+max);
						
						
					
					for(int i=1;i<1001;i++){
				   // fila[i] = fila[issues.length() -i- 1  ];
						
						((DefaultTableModel) table_2.getModel()).addRow(fila2[i]);
						
						
						
					}
						
			

				table_2.getColumnModel().getColumn(0).setPreferredWidth(33);
				table_2.getColumnModel().getColumn(1).setPreferredWidth(33);
				table_2.getColumnModel().getColumn(2).setPreferredWidth(33);
				scrollPane_2.setViewportView(table_2);
				
				
				
				
				
				////////////////////////////////////////istatistik hesapları 1//////////////////////////////////////////
				
				
				
				table_3.setFont(new Font("Verdana", Font.BOLD, 12));
				table_3.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Delivery time (T)", "Freq","PDF","CDF"
					}
				));
				
				
				
				
				
				int numCols4 = table_3.getModel().getColumnCount();
				
				fila3=new Object[11][numCols4]; 
				
				System.out.println("dates size -->"+dates_size);
				
				fila3[0][0] = (int)min;
				fila3[10][0] = (int)max;
				Double[] freq=new Double[11];
				freq[0] = (Double)min;
				freq[10] =(Double)max;
				
				
				for(int i=0;i<11;i++){
					
					if(i>=0 && i<10 ){
						
							fila3[i+1][0]=(Double)(Double.valueOf(fila3[i][0].toString())+((max-min)/10));
							freq[i+1]=(Double.parseDouble(fila3[i][0].toString()));
							
						
				   
				 
					}
					//System.out.println(freq[i]);
				
					}
				
				delivery=new double[fila3.length];
				for(int i=0;i<fila3.length;i++){
					
					delivery[i]=Double.parseDouble(fila3[i][0].toString());
				}
				
				
				////////////////////////frekans bölümü//////////////////////////
				
				
				
				Double alt=(Double)min;
				Double ust=(Double)freq[1];
				
				System.out.println("/////////////////////////////////////////////////////////");
				int a=0;
				int sayac=0;
				int diff=0;
				int last= 0;
				int x;
				int[] syc=new int[freq.length];
				
				
	
				int j= 1;
				for(int i= 0; i< freq.length; i++){
                sayac= 0;
                while(j<frm1.t.length){
                                                if(freq[i]>=frm1.t[j]){
                                                                sayac++;
                                                                j++;}
                                                else 
                                                                break;
                                              //  System.out.println(frm1.t[j]);
                }
                syc[i] = sayac;
                
                //System.out.println(syc[i]);

				}
		
				System.out.println("------------------------------------------");
				double[] frm1t=new double[frm1.t.length];
				for(int i= 1; i< frm1.t.length; i++){
					frm1t[i]=frm1.t[i];
				//	System.out.println(i+" ---  "+frm1.t[i]);
					
				}
				
				
				
				
				
			
				
				
				////////////////////freq in yazdırıldığı for////////////////////////////////////
				
			probability=new double[11];
			cdf=new double[11];
				//fila3[0][3]=(double)((syc[0]))/10;
				double cdft=0;
				for (int k=0;k<11;k++) {
					fila3[k][1]=(int)((syc[k]));
					fila3[k][2]=(double)((syc[k]))/10;
					probability[k]=(double)((syc[k]))/10;
					
					if(k==10){
						for (int i=0;i<11;i++) {
							cdft+=((double)((syc[i]))/10);
							fila3[i][3]=cdft;
							cdf[i]=cdft;
						}
						
					}
					
					
					
				}
			
				
				
				avg=totalday/dates_size;
		
				//Arrays.sort(fila3);
				
				for(int i=0;i<11;i++){
			   // fila[i] = fila[issues.length() -i- 1  ];
					
					((DefaultTableModel) table_3.getModel()).addRow((fila3[i]));
					
					
					
				}

				table_3.getColumnModel().getColumn(0).setPreferredWidth(25);
				table_3.getColumnModel().getColumn(1).setPreferredWidth(25);
				table_3.getColumnModel().getColumn(2).setPreferredWidth(25);
				table_3.getColumnModel().getColumn(3).setPreferredWidth(25);
				scrollPane_4.setViewportView(table_3);
				
				
				/////////////////////////////////////////////Median std tablosu bölümü///////////////////////////////////////////////////////////////////
				
				table_4.setFont(new Font("Verdana", Font.BOLD, 12));
				table_4.setModel(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"",""
						}
					));
				
	///////////////////////////////////////////StdDev hesabı////////////////////////////////////////////////////////////////			

		         double sumNumber =0;
		         int arrLength = frm1t.length;
		         for (int i= 1; i < arrLength; i++)
		         {
		             sumNumber+= frm1t[i];
		         }
		         double avg = sumNumber/arrLength;
				
				
				int numCols5 = table_4.getModel().getColumnCount();
				
				 
		         double standardDevNum=0;
		         for (int i= 1; i < arrLength; i++)
		         {
		             double s = frm1t[i]-avg;
		             standardDevNum += s*s;
		         }
		         double standardDev = Math.sqrt(standardDevNum/(arrLength-1));
		  
		 
/////////////////////////////////////////////////////mode mult bölümü//////////////////////////////////////////////////////////////////	 
		         
		         mode_mult mod=new mode_mult();
		         
		        
			        List<Double> md = new ArrayList<Double>();
			        md=mod.mode(frm1t);
			        
			        double[] target = new double[md.size()];
			        for (int i = 0; i < target.length; i++) {
			           target[i] = md.get(i).doubleValue();  // java 1.4 style
			           // or:
			           target[i] = md.get(i);                // java 1.5+ style (outboxing)
			         //System.out.println("modu-----------------"+target[i]);
			        }
			     
			       //System.out.println("modu-----------------"+target[0]);
		         
		         
		 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
				System.out.println("ortalama :" + avg);
				System.out.println("standart sapma :" + standardDev);
		         
				
				fila5=new Object[7][numCols2]; 
				
			 	Percentile percentile = new Percentile();
		           System.out.println( "0.85 percentile value: " + percentile.evaluate(frm1t, 85));
		           System.out.println( "0.95 percentile value: " + percentile.evaluate(frm1t, 95));
		           
		        /*   for(int i=0;i<11;i++){
		        		 System.out.println("--"+delivery[i]);
		        		
		        	}*/
			 
				fila5[0][0] = "Median";
				fila5[0][1] = String.valueOf((frm1t[frm1t.length/2]+frm1t[frm1t.length/2])/2);
				fila5[1][0] = "STD";
				fila5[1][1] = standardDev;
				fila5[2][0] = "Average T";
				fila5[2][1] = frm1.t[(frm1.t.length/2)];
				fila5[3][0] = "85 Perc";
				fila5[3][1] = percentile.evaluate(frm1t, 85);
				fila5[4][0] = "95 Perc";
				fila5[4][1] = percentile.evaluate(frm1t, 95);
				fila5[5][0] = "Mode(s)";
				fila5[5][1] = target[0];
				fila5[6][0] = "SIP size";
				fila5[6][1] = frm1t.length-1;
						
					//	fila1[0][2] = "ccc";
				
						//((DefaultTableModel) table_4.getModel()).addRow(fila1[0])
				
				for(int i=0;i<7;i++){
			   // fila[i] = fila[issues.length() -i- 1  ];
					
				((DefaultTableModel) table_4.getModel()).addRow(fila5[i]);
					
					
					
				}

				table_4.getColumnModel().getColumn(0).setPreferredWidth(50);
				table_4.getColumnModel().getColumn(1).setPreferredWidth(50);
				
				scrollPane_5.setViewportView(table_4);
				
				
				
				
				
				
				
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
				
			}
		});
		btnNewButton_1.setBounds(20, 419, 89, 23);
		contentPane.add(btnNewButton_1);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(167, 11, 181, 33);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setColumnHeaderView(table_1);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(167, 58, 181, 350);
		contentPane.add(scrollPane_2);
		
		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
		
		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(372, 62, 299, 195);
		contentPane.add(scrollPane_4);
		
		table_3 = new JTable();
		scrollPane_4.setColumnHeaderView(table_3);
		
		scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(372, 268, 208, 117);
		contentPane.add(scrollPane_5);
		
		
		

	    
	    
		table_4 = new JTable();
		scrollPane_5.setRowHeaderView(table_4);
		
		btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				
				  final OverlaidBarChartDemo demo = new OverlaidBarChartDemo("rerter");
				  demo.createchart(probability, delivery,cdf);
			        demo.pack();
			        demo.setSize(800, 600);
			        demo.setVisible(true);
			        RefineryUtilities.centerFrameOnScreen(demo);
			        demo.setVisible(true);
				
				
				
			}
		});
		btnNewButton_2.setBounds(433, 432, 89, 23);
		contentPane.add(btnNewButton_2);
		
		
		
				
				//////////////////////////////////////////////////////
				
				
				
				
		
		
	
	}
}
