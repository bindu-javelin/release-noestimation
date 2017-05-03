

package main.panels;

import main.classes.Selection;
import main.database.DBHelper;
import main.functions.FrequencyAndDeliveryTime;
import main.functions.GlobalFunctions;
import main.functions.Statistics;
import main.view.CreateChart;
import main.view.JTableAnalyzing;
import main.view.JTableIssue;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AnalyzingPanel extends AbstractJPanel {

    JScrollPane jScrollPaneResultForBoth;
    double avg = 0;
    double perc85 = 0 ;
    double perc95 = 0;

    ChartFrame chartFrame;

    public AnalyzingPanel() {

    }

    AnalysisView [] viewList;
    ArrayList<Selection> selectionList;
    public void init(ArrayList<Selection> selectionList){
    	removeAll();
    	AnalysisView.resetLength();

        


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
        
        
        JButton createMultipleButton = new JButton("Multiple");
        createMultipleButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        createMultipleButton.setBounds(690, 600, 150, 35);
        add(createMultipleButton);

        createMultipleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Multiple m = new Multiple(selectionList);
            	m.createMultiple();
            }
        });


        JButton reCalculateButton = new JButton("Re-Calculate");
        reCalculateButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
        reCalculateButton.setBounds(420, 600, 120, 35);
        add(reCalculateButton);

        reCalculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reCalculate();
                ((JFrame)getRootPane().getParent()).setTitle("Analyzing");
            }
        });
    	
    	viewList = new AnalysisView[selectionList.size()];
    	
        ((JFrame)getRootPane().getParent()).setTitle("Analyzing");
        this.selectionList = selectionList;
        chartFrame = new ChartFrame();
        chartFrame.setVisible(false);
        ThroughputPanel throughputPanel = (ThroughputPanel) MainFrame.jPanelList.get(MainFrame.THROUGHPUT_PANEL);
        
      //  avg = 0;
      //  perc85 = 0;
      //  perc95 = 0;
        
        for (int i = 0; i < selectionList.size(); i++) {
            Selection selection = selectionList.get(i);
            selection.setTableAnalyzing(new JTableAnalyzing());
            
            viewList[i] = new AnalysisView(selection.getIssueType(),selection.getTableIssue().getShowWipOnTable().length);
            viewList[i].setLayout(null);
            viewList[i].setBackground(null);
            viewList[i].setBounds(0, (AnalysisView.length()-1)*300, 1000, 300);
            
            viewList[i].getIssueScrollPane().setViewportView(selection.getTableAnalyzing().createTakTimeAndResamplingTable(selectionList.get(i).getTableIssue()));
            viewList[i].getIssueSummaryScrolLPane().setViewportView(selection.getTableAnalyzing().createSummaryDeliveryTimeTable(1));
            viewList[i].getIssueDeliveryTimeScrollPane().setViewportView(selection.getTableAnalyzing().createDeliveryTimeSimulationTable());
            
            viewList[i].getIssueSummaryDeliveryScrollPane().setViewportView(selection.getTableAnalyzing().createStatisticsTable());
            viewList[i].getIssueResultPane().setViewportView(selection.getTableAnalyzing().createResultTable());
            avg+= selection.getTableAnalyzing().getAvg();
            perc85+= selection.getTableAnalyzing().getPerc85();
            perc95+= selection.getTableAnalyzing().getPerc95();
            
            add(viewList[i]);
		}
        jScrollPaneResultForBoth.setViewportView(createResultTableForBoth(String.valueOf(avg),String.valueOf(perc85),String.valueOf(perc95)));

    }
    
    
    
    
    
    
    /* private double[] calculateMultiple(){
    	double[] d = new double[20000];
    	double perc1 = 40;
    	for (int i = 0; i < d.length; i++) {
			double d2=0;
			for (int j = 0; j < selectionList.size(); j++) {
				d2 += perc1 * randomFromArray(selectionList.get(j).getTableAnalyzing().getDeliveryTimeSimulationDatas());
			}
			d[i] = d2;
		}
    	return d;
    } */
    
    public void reCalculate(){

        avg = 0;
        perc85 = 0;
        perc95 = 0;
        
        for (int i = 0; i < selectionList.size(); i++) {
            Selection selection = selectionList.get(i);
            //selection.setTableAnalyzing(new JTableAnalyzing());
            
            viewList[i].getIssueScrollPane().setViewportView(selection.getTableAnalyzing().createTakTimeAndResamplingTable(selection.getTableIssue(),viewList[i].getScope()));
            viewList[i].getIssueSummaryScrolLPane().setViewportView(selection.getTableAnalyzing().createSummaryDeliveryTimeTable(viewList[i].getScope()));
            viewList[i].getIssueDeliveryTimeScrollPane().setViewportView(selection.getTableAnalyzing().createDeliveryTimeSimulationTable(viewList[i].getScope()));
            viewList[i].getIssueSummaryDeliveryScrollPane().setViewportView(selection.getTableAnalyzing().createStatisticsTable());
            viewList[i].getIssueResultPane().setViewportView(selection.getTableAnalyzing().createResultTable());
            avg+= selection.getTableAnalyzing().getAvg();
            perc85+= selection.getTableAnalyzing().getPerc85();
            perc95+= selection.getTableAnalyzing().getPerc95();
		}
        
        jScrollPaneResultForBoth.setViewportView(createResultTableForBoth(String.valueOf(avg),String.valueOf(perc85),String.valueOf(perc95)));
        createHistogram();
    }

    public void createHistogram(){
        chartFrame.setVisible(true);
        CreateChart createChart = new CreateChart();
        chartFrame.getContentPane().removeAll();

        for (int i = 0; i < selectionList.size(); i++) {
            chartFrame.add(createChart.createChartPanel(selectionList.get(i).getTableAnalyzing().getProbability(),
            		selectionList.get(i).getTableAnalyzing().getDeliveryTime(),selectionList.get(i).getTableAnalyzing().getCdf(),selectionList.get(i).getIssueType(),1));
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
    


class AnalysisView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4785543889170915642L;

	/**
	 * 
	 */
	private static int x=0;

	private JScrollPane issueScrollPane,issueSummaryScrolLPane,issueDeliveryTimeScrollPane,issueSummaryDeliveryScrollPane,issueResultPane;
	int scope;
	JTextField issueScopeText;
	//private JLabel issueLabel,issueIcon,issueScopeLabel;
	//private JTextField issueScopeText;
	public AnalysisView(String issueType, int scope) {
        
		JLabel issueLabel = new JLabel();
        issueLabel.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
        issueLabel.setBounds(50, 25, 500, 20);
        add(issueLabel);

        JLabel issueIcon = new JLabel("");
        issueIcon.setBounds(25, 25, 20, 20);
        add(issueIcon);

        issueScrollPane = new JScrollPane();
        issueScrollPane.setBounds(25, 50, 150, 245);
        issueScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        add(issueScrollPane);

        issueSummaryScrolLPane = new JScrollPane();
        issueSummaryScrolLPane.setBorder(BorderFactory.createEmptyBorder());
        issueSummaryScrolLPane.getViewport().setBackground(new Color(247, 249, 252));
        issueSummaryScrolLPane.setBounds(200, 50, 180, 35);
        add(issueSummaryScrolLPane);

        issueDeliveryTimeScrollPane = new JScrollPane();
        issueDeliveryTimeScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueDeliveryTimeScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueDeliveryTimeScrollPane.setBounds(200, 100, 180, 195);
        add(issueDeliveryTimeScrollPane);

        issueSummaryDeliveryScrollPane = new JScrollPane();
        issueSummaryDeliveryScrollPane.setBorder(BorderFactory.createEmptyBorder());
        issueSummaryDeliveryScrollPane.getViewport().setBackground(new Color(247, 249, 252));
        issueSummaryDeliveryScrollPane.setBounds(560, 50, 250, 245);
        add(issueSummaryDeliveryScrollPane);

        issueResultPane = new JScrollPane();
        issueResultPane.setBorder(BorderFactory.createEmptyBorder());
        issueResultPane.getViewport().setBackground(new Color(247, 249, 252));
        issueResultPane.setBounds(850,50,150,245);
        add(issueResultPane);

        JLabel issueScopeLabel = new JLabel("Scope");
        issueScopeLabel.setFont(new Font("Mongolian Baiti", Font.BOLD | Font.ITALIC, 20));
        issueScopeLabel.setBounds(440, 125, 120, 20);
        add(issueScopeLabel);

        issueScopeText = new JTextField(""+scope);
        issueScopeText.setBounds(400, 155, 140, 35);
        issueScopeText.setHorizontalAlignment(JTextField.CENTER);
        issueScopeText.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 18));
        add(issueScopeText);
        
        issueLabel.setText(issueType);
        try {
            URL url = new URL(DBHelper.getInstance().getIssueTypeIcon(issueType));
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon  = new ImageIcon(img);
            issueIcon.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        x++;
	}
	
	public static int length(){
		return x;
	}
	
	public static void resetLength(){
		x = 0;
	}

	public JScrollPane getIssueScrollPane() {
		return issueScrollPane;
	}

	public JScrollPane getIssueSummaryScrolLPane() {
		return issueSummaryScrolLPane;
	}

	public JScrollPane getIssueDeliveryTimeScrollPane() {
		return issueDeliveryTimeScrollPane;
	}

	public JScrollPane getIssueSummaryDeliveryScrollPane() {
		return issueSummaryDeliveryScrollPane;
	}

	public JScrollPane getIssueResultPane() {
		return issueResultPane;
	}

	public int getScope() {
		return Integer.parseInt(issueScopeText.getText());
	}
}



class Multiple {

	private ArrayList<Selection> selectionList;
    private final int multipleSIP = 20000;
    private double[] projectsTaktTime = new double[multipleSIP];
    
    public Multiple(ArrayList<Selection> selectionList) {
		this.selectionList = selectionList;
	}
    
	public void createMultiple(){
    	JFrame frame = new JFrame("Analysis");
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	frame.setBounds(100, 100, 1024, 768);
    	frame.setVisible(true);
    	frame.setLayout(null);
    	
    	JScrollPane sPane = new JScrollPane();
    	sPane.setBorder(BorderFactory.createEmptyBorder());
    	sPane.getViewport().setBackground(new Color(247, 249, 252));
    	sPane.setBounds(15, 15, 400 + (selectionList.size()-1)*100, 250);
        frame.add(sPane);
        sPane.setViewportView(createProjectTaktTimeTable());
        

    	JScrollPane sPane2 = new JScrollPane();
    	sPane2.setBorder(BorderFactory.createEmptyBorder());
    	sPane2.getViewport().setBackground(new Color(247, 249, 252));
    	sPane2.setBounds(15, 300, 400, 200);
        frame.add(sPane2);
        sPane2.setViewportView(createStatisticResult());
 
        

    	JScrollPane sPane3 = new JScrollPane();
    	sPane3.setBorder(BorderFactory.createEmptyBorder());
    	sPane3.getViewport().setBackground(new Color(247, 249, 252));
    	sPane3.setBounds(15, 550, 400, 120);
        frame.add(sPane3);
        sPane3.setViewportView(createResultTable());

    	JScrollPane sPane4 = new JScrollPane();
    	sPane4.setBorder(BorderFactory.createEmptyBorder());
    	sPane4.getViewport().setBackground(new Color(247, 249, 252));
    	sPane4.setBounds(500, 350, 400, 350);
        frame.add(sPane4);
        sPane4.setViewportView(createChartPanel());
	}
	
	
	
	 public ChartPanel createChartPanel() {
			DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
			for(int i=0;i<delivery.length;i++){
			dataset1.addValue(probability[i], "Delivery time (days)",String.valueOf(delivery[i]));
			}
			
			final CategoryItemRenderer renderer = new BarRenderer();
			renderer.setSeriesPaint(0, Color.GREEN);
			
			renderer.setItemLabelsVisible(true);
			
			final CategoryPlot plot = new CategoryPlot();
			plot.setDataset(dataset1);
			plot.setRenderer(renderer);
			
			plot.setDomainAxis(new CategoryAxis("Delivery time (days)"));
			plot.setRangeAxis(new NumberAxis("PDF"));
			
			plot.setOrientation(PlotOrientation.VERTICAL);
			plot.setRangeGridlinesVisible(true);
			plot.setDomainGridlinesVisible(true);
			
			
			/*DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
			for(int i=0;i<delivery.length;i++){
			dataset2.addValue(cdf[i], "CDF", String.valueOf(delivery[i]));
			
			}*/
			final CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
			//plot.setDataset(1, dataset2);
			plot.setRenderer(1, renderer2);
			
			final CategoryItemRenderer renderer3 = new LineAndShapeRenderer();
			plot.setRenderer(2, renderer3);
			
			plot.mapDatasetToRangeAxis(2, 1);
			
			plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
			
			plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
			final JFreeChart chart = new JFreeChart(plot);
			
			chart.setTitle("High Level Project Planning");
			final ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new java.awt.Dimension(400, 350));
			return chartPanel;
	 }
      
	private JTable createProjectTaktTimeTable(){
		String[] labels = new String[selectionList.size() + 2];
    	
    	labels[0] = "";
    	for (int i = 0; i < selectionList.size(); i++) {
			labels[i+1] = selectionList.get(i).getIssueType();
		}
    	labels[selectionList.size()+1] = "Projected Time";

        JTable table = new JTable();
        table.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond",Font.BOLD, 14));
        table.setModel(new DefaultTableModel(
                new Double[][]{
                },
                labels
        ));
        

        Object[][] o = getMultipleDatas();
        
        for (int i = 0; i < multipleSIP; i++) {
            ((DefaultTableModel) table.getModel()).addRow((o[i]));
        }
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(25);
		}
        
        return table;
	}
    
	private JTable createResultTable() {
		double median=0, std=0, averageT=0, p85=0, p95=0, mode=0;
		int sip=0;
		
		Statistics statistics = new Statistics();
		median = statistics.getMode(projectsTaktTime);
		std = statistics.std(projectsTaktTime);
		averageT = statistics.getAvaregeT(projectsTaktTime);
		p85 = statistics.getPerc85(projectsTaktTime, 85);
		p95 = statistics.getPerc85(projectsTaktTime, 95);
		mode = statistics.getMode(projectsTaktTime);
		sip = projectsTaktTime.length;
		
		JTable table = new JTable();
        table.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.BOLD, 14));
        table.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "", ""
                }
        ));
        
        int numCols5 = table.getModel().getColumnCount();
        Object[][] showStatisticsOnTable = new Object[7][numCols5];
        showStatisticsOnTable[0][0] = "Median";
        showStatisticsOnTable[0][1] = median;
        showStatisticsOnTable[1][0] = "STD";
        showStatisticsOnTable[1][1] = std;
        showStatisticsOnTable[2][0] = "Average T";
        showStatisticsOnTable[2][1] = averageT;
        showStatisticsOnTable[3][0] = "85 Perc";
        showStatisticsOnTable[3][1] = p85;
        showStatisticsOnTable[4][0] = "95 Perc";
        showStatisticsOnTable[4][1] = p95;
        showStatisticsOnTable[5][0] = "Mode(s)";
        showStatisticsOnTable[5][1] = mode;
        showStatisticsOnTable[6][0] = "SIP size";
        showStatisticsOnTable[6][1] = sip;

        for (int i = 0; i < 7; i++) {
            ((DefaultTableModel) table.getModel()).addRow(showStatisticsOnTable[i]);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				DecimalFormat df = new DecimalFormat("#.00"); 
				Double a = Double.parseDouble(value.toString());
				JLabel jLabel = new JLabel(df.format(a));
				return jLabel;
			}
		});
        
        return table;
  	}  
   
	double[] probability;
	double[] delivery;
	double[] cdf;
	
    private JTable createStatisticResult() {
    	JTable simulationTable = new JTable();
        simulationTable.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = simulationTable.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond",Font.BOLD, 14));
        simulationTable.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Delivery time (T)", "Freq", "PDF", "CDF"
                }
        ));
        
        
        int numCols4 = simulationTable.getModel().getColumnCount();
        int min = 1;
    	for (int i = 0; i < projectsTaktTime.length; i++) {
			if(projectsTaktTime[i]>0) {
				min = (int) projectsTaktTime[i];
				break;
			}
		}
    	
        int max = (int) projectsTaktTime[projectsTaktTime.length-1];
        int freq[] = FrequencyAndDeliveryTime.findFrequency((projectsTaktTime));
        Object[][] showAnalyzingOnTable = new Object[11][numCols4];
        delivery = new double[11];
        double average;
        ////////////////////freq in yazdirildigi for///////////////////////////////////
        int[] counterDeliveryTime = new int[11];
        int difference = ((max - min) / 10);
        counterDeliveryTime[0] = min;
        counterDeliveryTime[10] = max;
        for (int i = 0; i < 11; i++) {
            if (i < 9) {
                counterDeliveryTime[i + 1] = (counterDeliveryTime[i] + difference);
            }
            //System.out.println(counterDeliveryTime[i]);
            delivery[i] = counterDeliveryTime[i];
        }
        probability = new double[11];
        cdf = new double[11];
        double cdft = 0;
        for (int k = 0; k < 11; k++) {
            showAnalyzingOnTable[k][0] = (int) (counterDeliveryTime[k]);
            showAnalyzingOnTable[k][1] = (int) ((freq[k]));
            
            double pdf = (freq[k] / (double)multipleSIP) * 100;
            probability[k] = pdf;
            showAnalyzingOnTable[k][2] = pdf;
            
            cdft+=pdf;
            cdf[k] = cdft;
            showAnalyzingOnTable[k][3] = cdft;
        }
        for (int i = 0; i < 11; i++) {

            ((DefaultTableModel) simulationTable.getModel()).addRow((showAnalyzingOnTable[i]));
        }

        simulationTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        simulationTable.getColumnModel().getColumn(1).setPreferredWidth(25);
        simulationTable.getColumnModel().getColumn(2).setPreferredWidth(25);
        simulationTable.getColumnModel().getColumn(3).setPreferredWidth(25);
        return simulationTable;
  	}  
	

    private Double[][] getMultipleDatas(){
    	Double[][] d = new Double[multipleSIP][selectionList.size()+2];
    	
    	
    	double totalPerc = 0;
    	for (int i = 0; i < selectionList.size(); i++) {
			totalPerc+= selectionList.get(i).getTableIssue().getResamplingWip().length;
		}
		System.out.println("Total perc: " + totalPerc);
    	
    	for (int i = 0; i < selectionList.size(); i++) {
    		int a = selectionList.get(i).getTableIssue().getResamplingWip().length;
			selectionList.get(i).setProjectPercentage((a*100)/totalPerc);
			System.out.println(selectionList.get(i).getIssueType() +" perc= %" + selectionList.get(i).getProjectPercentage());
		}
    	
    	
    	for (int i = 0; i < 1000; i++) {
			d[i][0] = (double) i+1;
			double d2 = 0;
			for (int j = 0; j < selectionList.size(); j++) {
				d[i][j+1] = (Double) selectionList.get(j).getTableAnalyzing().getDeliveryTimeSimulationDatas()[i];
				d2+= randomFromArray(selectionList.get(j).getTableAnalyzing().getDeliveryTimeSimulationDatas()) * selectionList.get(j).getProjectPercentage();
			}
			projectsTaktTime[i] = d2;
			d[i][selectionList.size()+1]=d2;
		}
    	for (int i = 1000; i < multipleSIP; i++) {
			double d2 = 0;
			d[i][0] = (double) i+1;
			for (int j = 0; j < selectionList.size(); j++) {
				d2+= randomFromArray(selectionList.get(j).getTableAnalyzing().getDeliveryTimeSimulationDatas()) * selectionList.get(j).getProjectPercentage();
			}
			projectsTaktTime[i] = d2;
			d[i][selectionList.size()+1]=d2;
		}
    	Arrays.sort(projectsTaktTime);
    	return d;
    }
    

    
    Random rgen = new Random();
    private double randomFromArray(double[] d){
        return d[rgen.nextInt(d.length)];
    }
    
    class Math {
    	/*static public int[] findFrequency(double[] array){
    		int[] a = new int[10];
    		
    		return a;
    	}*/
    }
}