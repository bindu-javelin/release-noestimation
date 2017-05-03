package main.view;

import main.classes.Issue;
import main.classes.SummaryThroughput;
import main.classes.Throughput;
import main.functions.GlobalFunctions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by ferhaty
 */
public class JTableThroughput {
    private ArrayList<Throughput> throughputList;
    private Object[][] throughputShowTable;

    public JTable createThroughputTable(ArrayList<Issue> issueList) {
        throughputList = new ArrayList<>();
        JTable throughputTable = new JTable();
        throughputTable.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = throughputTable.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.BOLD, 14));
        throughputTable.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Done date", "Count of done", "Takt Time", "In Parallel"
                }
        ));
        int numCols;
        numCols = throughputTable.getModel().getColumnCount();
        /* for (int i = 0; i < issueList.size(); i++) {
            Throughput throughput = new Throughput();
            throughput.setStartDate(issueList.get(i).getCreatedDate());
            throughput.setDoneDate(issueList.get(i).getCompletedDate());
            throughputList.add(throughput);
        }
        throughputList = GlobalFunctions.ThroughputSort(throughputList);
        throughputList.get(0).setTaktTime(GlobalFunctions.dayDiff(throughputList.get(0).getStartDate(), throughputList.get(0).getDoneDate()));
        throughputList.get(0).setCountOfDate(1); */
        
        
        /* for (int i = 1; i < throughputList.size(); i++) {
			for(int j=i+1; j < throughputList.size(); j++){
				if(sameDay(throughputList.get(i).getStartDate(),throughputList.get(j).getStartDate()) 
						&& sameDay(throughputList.get(i).getDoneDate(),throughputList.get(j).getDoneDate())){
					throughputList.get(i).setCountOfDate(throughputList.get(i).getCountOfDate()+1);
				}
				else{
	                throughputList.get(doneDateCount).setDoneDate(throughputList.get(i).getDoneDate());
	                throughputList.get(doneDateCount).setCountOfDate(1);
	                throughputList.get(doneDateCount).setTaktTime(GlobalFunctions.dayDiff(throughputList.get(doneDateCount - 1).getDoneDate(),
	                        throughputList.get(doneDateCount).getDoneDate()));
				}
			}
		} */
        

        /*  ArrayList<Throughput> throughputList2 = new ArrayList<>();
        for (int i = 0; i < throughputList.size()-1; i++) {
        	int j = i+1;
        	throughputList.get(i).setCountOfDate(1);
        	throughputList.get(i).setTaktTime(GlobalFunctions.dayDiff(throughputList.get(i).getDoneDate(),throughputList.get(i).getStartDate()));
			while(sameDay(throughputList.get(i).getDoneDate(), throughputList.get(j).getDoneDate())){
				
			}
		} */
        
        /* int doneDateCount = 0;
        for (int i = 1; i < throughputList.size(); i++) {
            if (sameDay(throughputList.get(doneDateCount).getDoneDate(),throughputList.get(i).getDoneDate())) {
                throughputList.get(doneDateCount).setCountOfDate(throughputList.get(doneDateCount).getCountOfDate() + 1);
                throughputList.remove(i);
                i--;
            } else {
                doneDateCount++;
                throughputList.get(doneDateCount).setStartDate(throughputList.get(i).getStartDate());
                throughputList.get(doneDateCount).setDoneDate(throughputList.get(i).getDoneDate());
                throughputList.get(doneDateCount).setCountOfDate(1);
                throughputList.get(doneDateCount).setTaktTime(throughputList.get(doneDateCount).getTaktTime() + GlobalFunctions.dayDiff(throughputList.get(doneDateCount).getStartDate(),
                        throughputList.get(doneDateCount).getDoneDate()));
            }
        }
        for (int i = 0; i < throughputList.size(); i++) {
            throughputList.get(i).setInParallel(throughputList.get(i).getCountOfDate() - 1);
            // getting avg tt for a group
            //throughputList.get(i).setTaktTime(throughputList.get(i).getTaktTime() / throughputList.get(i).getCountOfDate());
            if(throughputList.get(i).getTaktTime()==0) throughputList.get(i).setTaktTime(1);
        } */
        
        
        issueList = GlobalFunctions.IssueSort(issueList);

    	Throughput t = new Throughput();
        t.setStartDate(issueList.get(0).getCreatedDate());
        t.setDoneDate(issueList.get(0).getCompletedDate());
        t.setCountOfDate(1);
        t.setTaktTime(GlobalFunctions.dayDiff(issueList.get(0).getCreatedDate(),issueList.get(0).getCompletedDate()));
        throughputList.add(t);
        int tIndex = 0;
        for (int i = 1; i < issueList.size(); i++) {
        	if(sameDay(issueList.get(i).getCompletedDate(),issueList.get(i-1).getCompletedDate())){
        		throughputList.get(tIndex).setCountOfDate(throughputList.get(tIndex).getCountOfDate()+1);
        		int tt2 = GlobalFunctions.dayDiff(issueList.get(i).getCreatedDate(),issueList.get(i).getCompletedDate());
        		throughputList.get(tIndex).setTaktTime(throughputList.get(tIndex).getTaktTime()+tt2);
        		//if(tt2>throughputList.get(tIndex).getTaktTime()) throughputList.get(tIndex).setTaktTime(tt2);
        	}
        	else {
        		tIndex++;
            	t = new Throughput();
                t.setStartDate(issueList.get(i).getCreatedDate());
                t.setDoneDate(issueList.get(i).getCompletedDate());
                t.setCountOfDate(1);
                t.setTaktTime(GlobalFunctions.dayDiff(issueList.get(i).getCreatedDate(),issueList.get(i).getCompletedDate()));
                throughputList.add(t);
        	}
        }

        for (int i = 0; i < throughputList.size(); i++) {
            throughputList.get(i).setInParallel(throughputList.get(i).getCountOfDate() - 1);
            // getting avg tt for a group
            throughputList.get(i).setTaktTime(throughputList.get(i).getTaktTime() / throughputList.get(i).getCountOfDate());
            if(throughputList.get(i).getTaktTime()==0) throughputList.get(i).setTaktTime(1);
        }
        
        
        
        Object[][] showItemsOnTable;
        showItemsOnTable = new Object[throughputList.size()][numCols];
        for (int i = 0; i < throughputList.size(); i++) {
            try {
                showItemsOnTable[i][0] = GlobalFunctions.dateFormatString(throughputList.get(i).getDoneDate());
                showItemsOnTable[i][1] = throughputList.get(i).getCountOfDate();
                showItemsOnTable[i][2] = throughputList.get(i).getTaktTime();
                showItemsOnTable[i][3] = throughputList.get(i).getInParallel();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        for (int k = 0; k < throughputList.size(); k++) {
            ((DefaultTableModel) throughputTable.getModel()).addRow(showItemsOnTable[k]);
        }
        throughputShowTable = showItemsOnTable;
        throughputTable.getColumnModel().getColumn(2).setPreferredWidth(91);
        throughputTable.getColumnModel().getColumn(3).setPreferredWidth(99);
        return throughputTable;
    }
    
    private boolean sameDay(Date d1, Date d2){
    	Calendar cal1 = Calendar.getInstance();
    	Calendar cal2 = Calendar.getInstance();
    	cal1.setTime(d1);
    	cal2.setTime(d2);
    	return ( cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
    	                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) );
    }

     public JTable createSummaryThroughputTable(ArrayList<Throughput> throughputList) {
        JTable summaryThroughputTable = new JTable();
        summaryThroughputTable.setFont(new Font("Verdana", Font.BOLD, 12));
         JTableHeader tableHeader = summaryThroughputTable.getTableHeader();
         tableHeader.setBackground(new Color(230,237,248));
         tableHeader.setFont(new Font("Garamond", Font.BOLD, 14));
        summaryThroughputTable.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Takt Time", "Work Items", "Project Length"
                }
        ));
        int numCols1 = summaryThroughputTable.getModel().getColumnCount();

        ArrayList<SummaryThroughput> summaryThroughputList = new ArrayList<>();
        for(int i=0; i< throughputList.size(); i++){
            SummaryThroughput summaryThroughput = new SummaryThroughput();
            summaryThroughput.setTaktTime(throughputList.get(i).getTaktTime());
            summaryThroughputList.add(summaryThroughput);
        }

        for (int k = 0; k < summaryThroughputList.size(); k++) {
            for (int i = 0; i < throughputList.size(); i++) {
                if (throughputList.get(i).getTaktTime() == summaryThroughputList.get(k).getTaktTime()) {
                    summaryThroughputList.get(k).setWorkItems(summaryThroughputList.get(k).getWorkItems() + 1);
                }
            }
        }
        Object[][] showItemsOnTable2 = new Object[summaryThroughputList.size() + 1][numCols1];
        showItemsOnTable2[0][0] = 0;
        showItemsOnTable2[0][1] = throughputList.size();
        showItemsOnTable2[0][2] = throughputList.get(0).getInParallel() * Integer.valueOf(showItemsOnTable2[0][0].toString());
        try {
            for (int i = 0; i < summaryThroughputList.size(); i++) {

                showItemsOnTable2[i + 1][0] = summaryThroughputList.get(i).getTaktTime();
                showItemsOnTable2[i + 1][1] = summaryThroughputList.get(i).getWorkItems();
                showItemsOnTable2[i + 1][2] = summaryThroughputList.get(i).getTaktTime() * summaryThroughputList.get(i).getWorkItems();
            }
        } catch (Exception e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
        for (int i = 0; i < summaryThroughputList.size() + 1; i++) {
            ((DefaultTableModel) summaryThroughputTable.getModel()).addRow(showItemsOnTable2[i]);
        }
        summaryThroughputTable.getColumnModel().getColumn(0).setPreferredWidth(33);
        summaryThroughputTable.getColumnModel().getColumn(1).setPreferredWidth(33);
        summaryThroughputTable.getColumnModel().getColumn(2).setPreferredWidth(33);
        return summaryThroughputTable;

    }

    public ArrayList<Throughput> getThroughputList() {
        return throughputList;
    }

    public void setThroughputList(ArrayList<Throughput> throughputList) {
        this.throughputList = throughputList;
    }

    public Object[][] getThroughputShowTable() {
        return throughputShowTable;
    }

    public void setThroughputShowTable(Object[][] throughputShowTable) {
        this.throughputShowTable = throughputShowTable;
    }
}
