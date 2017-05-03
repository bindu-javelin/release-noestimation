package main.view;

import main.functions.FrequencyAndDeliveryTime;
import main.functions.GlobalFunctions;
import main.functions.RandomWipPlacing;
import main.functions.Statistics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Arrays;

/**
 * Created by ferhaty on 12/9/2016.
 */
public class JTableAnalyzing {
    int tempDoluMu=0;
    private int totalDay;
    private int randomTotalDay;
    int wipSize;
    double[] tempAverageWip;
    double avg = 0;
    double perc85 = 0;
    double perc95 = 0;
    double[] probability;
    double[] deliveryTime;
    double[] cdf;
    private  int mainScope;
    private int[] tempResampling;
    public int[]  tempShowSimulationOnTable;
    Statistics statistics = new Statistics();

    static  FrequencyAndDeliveryTime frequencyAndDeliveryTime=new FrequencyAndDeliveryTime();

    public JTable createTakTimeAndResamplingTable(JTableIssue jTableIssue) {

        JTable taktTimeAndResamplingTable = new JTable();
        taktTimeAndResamplingTable.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = taktTimeAndResamplingTable.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.ITALIC | Font.BOLD, 14));
        taktTimeAndResamplingTable.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Takt Time", "Resampling"
                }
        ));
        int numCols = taktTimeAndResamplingTable.getModel().getColumnCount();
        wipSize = jTableIssue.getShowWipOnTable().length;

       Object[][] showResamplingOnTable = new Object[wipSize][numCols];

       // tempResampling = new int[wipSize];
        tempResampling = new int[wipSize];
        tempResampling = RandomWipPlacing.RandomizeArray(jTableIssue.getResamplingWip());

        for (int i = 0; i < wipSize; i++) {

            showResamplingOnTable[i][0] = jTableIssue.getShowWipOnTable()[i][0];
            showResamplingOnTable[i][1] = tempResampling[i];
            totalDay += Integer.valueOf(showResamplingOnTable[i][0].toString());
            randomTotalDay += Integer.valueOf(showResamplingOnTable[i][1].toString());
            ((DefaultTableModel) taktTimeAndResamplingTable.getModel()).addRow(showResamplingOnTable[i]);
        }
        taktTimeAndResamplingTable.getColumnModel().getColumn(0).setPreferredWidth(91);
        taktTimeAndResamplingTable.getColumnModel().getColumn(1).setPreferredWidth(99);

        return taktTimeAndResamplingTable;
    }

    public JTable createTakTimeAndResamplingTable(JTableIssue jTableIssue,int scope) {

        JTable taktTimeAndResamplingTable = new JTable();
        taktTimeAndResamplingTable.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = taktTimeAndResamplingTable.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.ITALIC | Font.BOLD, 14));
        taktTimeAndResamplingTable.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Takt Time", "Resampling"
                }
        ));
        int numCols = taktTimeAndResamplingTable.getModel().getColumnCount();
        scope = jTableIssue.getShowWipOnTable().length;
        Object[][] showResamplingOnTable = new Object[wipSize][numCols];
        tempResampling = new int[scope];
        tempResampling = RandomWipPlacing.RandomizeArray(jTableIssue.getResamplingWip());
        for (int i = 0; i < wipSize; i++) {
            showResamplingOnTable[i][0] = jTableIssue.getShowWipOnTable()[i][0];
            showResamplingOnTable[i][1] = tempResampling[i];
            ((DefaultTableModel) taktTimeAndResamplingTable.getModel()).addRow(showResamplingOnTable[i]);
        }
        taktTimeAndResamplingTable.getColumnModel().getColumn(0).setPreferredWidth(91);
        taktTimeAndResamplingTable.getColumnModel().getColumn(1).setPreferredWidth(99);

        return taktTimeAndResamplingTable;
    }

    public JTable createSummaryDeliveryTimeTable() {
        JTable summaryDeliveryTimeTable = new JTable();
        summaryDeliveryTimeTable.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = summaryDeliveryTimeTable.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.BOLD, 14));
        summaryDeliveryTimeTable.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Scope", "Delivery Time", "Average"
                }
        ));
        double avg = (double)totalDay/(double)wipSize;
        Object[][] showOnTable = new Object[1][3];
        showOnTable[0][0] = wipSize;
        showOnTable[0][1] = totalDay;
        showOnTable[0][2] = avg;
        ((DefaultTableModel) summaryDeliveryTimeTable.getModel()).addRow(showOnTable[0]);
        totalDay=0;
        return summaryDeliveryTimeTable;
    }

    public JTable createSummaryDeliveryTimeTable(int scope) {
        setScope(scope);
        JTable summaryDeliveryTimeTable = new JTable();
        summaryDeliveryTimeTable.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = summaryDeliveryTimeTable.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.BOLD, 14));
        summaryDeliveryTimeTable.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Scope", "Delivery Time", "Average"
                }
        ));
        double totaldayForScope=statistics.getAverageArray(tempShowSimulationOnTable);
        double avg = totaldayForScope/(double)scope;
        Object[][] showOnTable = new Object[1][3];
        showOnTable[0][0] = scope;
        showOnTable[0][1] = totaldayForScope;
        showOnTable[0][2] = avg;
        ((DefaultTableModel) summaryDeliveryTimeTable.getModel()).addRow(showOnTable[0]);
        return summaryDeliveryTimeTable;


    }

    public JTable createDeliveryTimeSimulationTable(){
    	return createDeliveryTimeSimulationTable(mainScope);
    }
    public JTable createDeliveryTimeSimulationTable(int scope) {
        JTable deliveryTimeSimulation = new JTable();
        deliveryTimeSimulation.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = deliveryTimeSimulation.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.BOLD, 14));
        deliveryTimeSimulation.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Scope (N)(work items)", "Delivery time (T)(days)", "Average Takt Time (TT)(days)"
                }
        ));

        int numCols3 = deliveryTimeSimulation.getModel().getColumnCount();
        Object[][] showSimulationOnTable = new Object[1001][numCols3];
        int[] sumRandomTotalWipItems= frequencyAndDeliveryTime.findDeliveryTime(getTempResampling(),wipSize);

        //int[] sumRandomTotalWipItems= frequencyAndDeliveryTime.findDeliveryTime(tempResampling,getWipSize());

        tempAverageWip = new double[1001];
        tempShowSimulationOnTable=new int[1001];
        deliveryTimeSimulationDatas = new double[1001];

        for (int i = 1; i < 1001; i++) {

            showSimulationOnTable[i][0] = i;
            showSimulationOnTable[i][1] = (int)sumRandomTotalWipItems[i];
            showSimulationOnTable[i][2] = Double.valueOf(sumRandomTotalWipItems[i]) / (double) wipSize;
            deliveryTimeSimulationDatas[i-1] = Double.valueOf(sumRandomTotalWipItems[i]) / (double) wipSize;
            //showSimulationOnTable[i][2] = Double.valueOf(sumRandomTotalWipItems[i]) / (double) getWipSize();

            tempAverageWip[i]=(double)showSimulationOnTable[i][2];
                tempShowSimulationOnTable[i] =sumRandomTotalWipItems[i];
            ((DefaultTableModel) deliveryTimeSimulation.getModel()).addRow(showSimulationOnTable[i]);
        }

        deliveryTimeSimulation.getColumnModel().getColumn(0).setPreferredWidth(33);
        deliveryTimeSimulation.getColumnModel().getColumn(1).setPreferredWidth(33);
        deliveryTimeSimulation.getColumnModel().getColumn(2).setPreferredWidth(33);
        return deliveryTimeSimulation;
    }
    private double deliveryTimeSimulationDatas[];
    

    public double[] getDeliveryTimeSimulationDatas() {
		return deliveryTimeSimulationDatas;
	}

	public JTable createDeliveryTimeForNProjectSimulationTable(int scope) {
        setScope(scope);
        JTable deliveryTimeSimulationForNProject = new JTable();
        deliveryTimeSimulationForNProject.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = deliveryTimeSimulationForNProject.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.BOLD, 14));
        deliveryTimeSimulationForNProject.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Scope (N)(work items)", "Delivery time (T)(days)", "Average Takt Time (TT)(days)"
                }
        ));

        int numCols3 = deliveryTimeSimulationForNProject.getModel().getColumnCount();
        Object[][] showSimulationOnTable = new Object[1001][numCols3];

        double []tempAverageWipForNProject=new double[1001];
        tempAverageWipForNProject=RandomWipPlacing.RandomizeArray(tempAverageWip);
        Arrays.sort(tempAverageWipForNProject);

        for (int i = 1; i < 1001; i++) {

            showSimulationOnTable[i][0] = i;
            showSimulationOnTable[i][1] =(int)(tempAverageWipForNProject[i]*getScope());
            showSimulationOnTable[i][2] = tempAverageWipForNProject[i];

            tempShowSimulationOnTable[i] =(int)(tempAverageWipForNProject[i]*getScope());
            ((DefaultTableModel) deliveryTimeSimulationForNProject.getModel()).addRow(showSimulationOnTable[i]);
        }
        deliveryTimeSimulationForNProject.getColumnModel().getColumn(0).setPreferredWidth(33);
        deliveryTimeSimulationForNProject.getColumnModel().getColumn(1).setPreferredWidth(33);
        deliveryTimeSimulationForNProject.getColumnModel().getColumn(2).setPreferredWidth(33);

        return deliveryTimeSimulationForNProject;
    }

    public JTable createStatisticsTable() {
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
        int min = tempShowSimulationOnTable[1];
        int max = tempShowSimulationOnTable[tempShowSimulationOnTable.length-1];
        int freq[] = FrequencyAndDeliveryTime.findFrequency((tempShowSimulationOnTable));
        Object[][] showAnalyzingOnTable = new Object[11][4];
        deliveryTime=new double[11];
        double average;
        int[] counterDeliveryTime = new int[11];
        int difference = ((max - min) / 10);
        counterDeliveryTime[0] = min;
        counterDeliveryTime[10] = max;
        for (int i = 0; i < 11; i++) {
            if (i >= 0 && i < 9) {
                counterDeliveryTime[i + 1] = (counterDeliveryTime[i] + difference);
            }
            deliveryTime[i] = counterDeliveryTime[i];
        }
        probability = new double[11];
        cdf = new double[11];
        double cdft = 0;
        for (int k = 0; k < 11; k++) {
            showAnalyzingOnTable[k][0] = (int) (counterDeliveryTime[k]);
            showAnalyzingOnTable[k][1] = (int) ((freq[k]));
            showAnalyzingOnTable[k][2] = (double) ((freq[k])) / 10;
            probability[k] = (double) ((freq[k])) / 10;
            if (k == 10) {
                for (int i = 0; i < 11; i++) {
                    cdft += ((double) ((freq[i])) / 10);
                    showAnalyzingOnTable[i][3] = cdft;
                    cdf[i] = cdft;
                }
            }
        }
        average = getTotalDay()  / wipSize;
        for (int i = 0; i < 11; i++) {
            ((DefaultTableModel) simulationTable.getModel()).addRow((showAnalyzingOnTable[i]));
        }

        simulationTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        simulationTable.getColumnModel().getColumn(1).setPreferredWidth(25);
        simulationTable.getColumnModel().getColumn(2).setPreferredWidth(25);
        simulationTable.getColumnModel().getColumn(3).setPreferredWidth(25);
        return simulationTable;
    }

    public JTable createResultTable() {
        JTable tableLastStatistics = new JTable();
        tableLastStatistics.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = tableLastStatistics.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.BOLD, 14));
        tableLastStatistics.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "", ""
                }
        ));
        int numCols5 = tableLastStatistics.getModel().getColumnCount();
        Object[][] showStatisticsOnTable = new Object[7][numCols5];
        showStatisticsOnTable[0][0] = "Median";
        showStatisticsOnTable[0][1] = String.valueOf(statistics.getMedian(tempShowSimulationOnTable));
        showStatisticsOnTable[1][0] = "STD";
        showStatisticsOnTable[1][1] = statistics.std(tempShowSimulationOnTable);
        showStatisticsOnTable[2][0] = "Average T";
        showStatisticsOnTable[2][1] = statistics.getAvaregeT(tempShowSimulationOnTable);
        avg = statistics.getAvaregeT(tempShowSimulationOnTable);
        showStatisticsOnTable[3][0] = "85 Perc";
        showStatisticsOnTable[3][1] = statistics.getPerc85(GlobalFunctions.intTodouble(tempShowSimulationOnTable), 85);
        perc85 = statistics.getPerc85(GlobalFunctions.intTodouble(tempShowSimulationOnTable), 85);
        showStatisticsOnTable[4][0] = "95 Perc";
        showStatisticsOnTable[4][1] = statistics.getPerc95(GlobalFunctions.intTodouble(tempShowSimulationOnTable), 95);
        perc95 = statistics.getPerc85(GlobalFunctions.intTodouble(tempShowSimulationOnTable), 95);
        showStatisticsOnTable[5][0] = "Mode(s)";
        showStatisticsOnTable[5][1] = statistics.getMode(GlobalFunctions.intTodouble(tempShowSimulationOnTable));
        showStatisticsOnTable[6][0] = "SIP size";
        showStatisticsOnTable[6][1] = statistics.getSipSize(tempShowSimulationOnTable);

        for (int i = 0; i < 7; i++) {
            ((DefaultTableModel) tableLastStatistics.getModel()).addRow(showStatisticsOnTable[i]);
        }
        tableLastStatistics.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableLastStatistics.getColumnModel().getColumn(1).setPreferredWidth(50);

        return tableLastStatistics;
    }

    public int getScope() {
        return mainScope;
    }

    public void setScope(int scope) {
        this.mainScope = scope;
    }

    public int getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
    }

    public int getRandomTotalDay() {
        return randomTotalDay;
    }

    public void setRandomTotalDay(int randomTotalDay) {
        this.randomTotalDay = randomTotalDay;
    }

    public int[] getTempResampling() {
        return tempResampling;
    }

    public void setTempResampling(int[] tempResampling) {
        this.tempResampling = tempResampling;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getPerc85() {
        return perc85;
    }

    public void setPerc85(double perc85) {
        this.perc85 = perc85;
    }

    public double getPerc95() {
        return perc95;
    }

    public void setPerc95(double perc95) {
        this.perc95 = perc95;
    }

    public double[] getProbability() {
        return probability;
    }

    public void setProbability(double[] probability) {
        this.probability = probability;
    }

    public double[] getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(double[] deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public double[] getCdf() {
        return cdf;
    }

    public void setCdf(double[] cdf) {
        this.cdf = cdf;
    }
}
