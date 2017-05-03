package main.classes;

import java.util.ArrayList;

import main.view.JTableAnalyzing;
import main.view.JTableIssue;
import main.view.JTableThroughput;

public class Selection {
	
	String issueType,startedTo,startedFrom,completedTo,completedFrom;
	ArrayList<Issue> issueList;
	JTableThroughput througput;
	JTableIssue tableIssue;
	JTableAnalyzing tableAnalyzing;
	
	double projectPercentage = 0;
	
	public Selection(){
		
	}

	public Selection(String issueType, String startedTo, String startedFrom, String completedTo, String completedFrom) {
		this.issueType = issueType;
		this.startedTo = startedTo;
		this.startedFrom = startedFrom;
		this.completedTo = completedTo;
		this.completedFrom = completedFrom;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getStartedTo() {
		return startedTo;
	}

	public void setStartedTo(String startedTo) {
		this.startedTo = startedTo;
	}

	public String getStartedFrom() {
		return startedFrom;
	}

	public void setStartedFrom(String startedFrom) {
		this.startedFrom = startedFrom;
	}

	public String getCompletedTo() {
		return completedTo;
	}

	public void setCompletedTo(String completedTo) {
		this.completedTo = completedTo;
	}

	public String getCompletedFrom() {
		return completedFrom;
	}

	public void setCompletedFrom(String completedFrom) {
		this.completedFrom = completedFrom;
	}
	
	public ArrayList<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(ArrayList<Issue> issueList) {
		this.issueList = issueList;
	}

	public JTableThroughput getThrougput() {
		return througput;
	}

	public void setThrougput(JTableThroughput througput) {
		this.througput = througput;
	}

	public JTableIssue getTableIssue() {
		return tableIssue;
	}

	public void setTableIssue(JTableIssue tableIssue) {
		this.tableIssue = tableIssue;
	}

	public JTableAnalyzing getTableAnalyzing() {
		return tableAnalyzing;
	}

	public void setTableAnalyzing(JTableAnalyzing tableAnalyzing) {
		this.tableAnalyzing = tableAnalyzing;
	}

	public double getProjectPercentage() {
		return projectPercentage;
	}

	public void setProjectPercentage(double projectPercentage) {
		this.projectPercentage = projectPercentage;
	}

}
