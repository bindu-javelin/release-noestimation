package main.view;

import main.classes.Issue;
import main.classes.Throughput;
import main.functions.GlobalFunctions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ferhaty
 */
public class JTableIssue {
    private Object[][] showWipOnTable;
    private int[] resamplingWip;
    public Object[][] getShowWipOnTable() {
        return showWipOnTable;
    }

    public void setShowWipOnTable(Object[][] showWipOnTable) {
        this.showWipOnTable = showWipOnTable;
    }

    public int[] getResamplingWip() {
        return resamplingWip;
    }

    public void setResamplingWip(int[] resamplingWip) {
        this.resamplingWip = resamplingWip;
    }

    public JTable createIssueTable(ArrayList<Issue> list, Object[][] showItemsOnTable) {
        JTable issuesTable = new JTable();
        issuesTable.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = issuesTable.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.BOLD, 14));
        issuesTable.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Issue Key", "Started Date", "Completed Date", "Time Cycle(Days)"
                }
        ));
        int numCols = issuesTable.getModel().getColumnCount();
        Object[][] showIssuesOnTable = new Object[list.size()][numCols];
        for (int i = 0; i < list.size(); i++) {
            try {
                showIssuesOnTable[i][0] = list.get(i).getIssueKey();
                showIssuesOnTable[i][1] = GlobalFunctions.dateFormatString(list.get(i).getCreatedDate());
                showIssuesOnTable[i][2] = GlobalFunctions.dateFormatString(list.get(i).getCompletedDate());
                //TODO showIssuesOnTable[i][3] = GlobalFunctions.dayDiff(list.get(i).getCreatedDate(), list.get(i).getCompletedDate());
                showIssuesOnTable[i][3] = list.get(i).getTimeCycle();
                
                /* for (int j = 0; j < showItemsOnTable.length; j++) {
                    if (showIssuesOnTable[i][2].equals(showItemsOnTable[j][0])) {
                        if (Integer.valueOf(String.valueOf(showIssuesOnTable[i][3])) / Integer.valueOf(showItemsOnTable[j][1].toString()) != 0)
                            showIssuesOnTable[i][3] = Integer.valueOf(String.valueOf(showIssuesOnTable[i][3])) / Integer.valueOf(showItemsOnTable[j][1].toString());
                    }
                } */
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < list.size(); i++) {
            ((DefaultTableModel) issuesTable.getModel()).addRow(showIssuesOnTable[list.size() - i - 1]);
        }

        issuesTable.getColumnModel().getColumn(2).setPreferredWidth(91);
        issuesTable.getColumnModel().getColumn(3).setPreferredWidth(99);
        return issuesTable;
    }

    public JTable createWipTable(ArrayList<Throughput> throughputList, Object[][] showItemsOnTable) {
        int wipSize;
        JTable wipTable = new JTable();
        wipTable.setFont(new Font("Verdana", Font.BOLD, 12));
        JTableHeader tableHeader = wipTable.getTableHeader();
        tableHeader.setBackground(new Color(230,237,248));
        tableHeader.setFont(new Font("Garamond", Font.BOLD, 14));
        wipTable.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "WIP"
                }
        ));
        int sumCountOfDate =0;
        for(int i =0; i<throughputList.size();i++){
            sumCountOfDate+= throughputList.get(i).getCountOfDate();
        }
       resamplingWip = new int[sumCountOfDate];
        showWipOnTable = new Object[sumCountOfDate][1];
        for(int i =0; i<throughputList.size(); i++){
            resamplingWip[i] = Integer.valueOf(showItemsOnTable[i][2].toString());
            showWipOnTable[i][0] = showItemsOnTable[i][2];
        }

        for (int i = throughputList.size(); i < sumCountOfDate; i++) {
                resamplingWip[i] = 0;
                showWipOnTable[i][0] = 0;

        }
        for (int i = 0; i < sumCountOfDate; i++) {
            ((DefaultTableModel) wipTable.getModel()).addRow(showWipOnTable[i]);
        }
        setShowWipOnTable(showWipOnTable);
        wipTable.getColumnModel().getColumn(0).setPreferredWidth(91);
        return wipTable;
    }


}
