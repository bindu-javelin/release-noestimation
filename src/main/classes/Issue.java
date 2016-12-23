package main.classes;

import java.util.Date;

public class Issue {

    private Date createdDate;
    private Date completedDate;
    private String issueKey;
    private int timeCycle;
    private String issueTypeName;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public int getTimeCycle() {
        return timeCycle;
    }

    public void setTimeCycle(int timeCycle) {
        this.timeCycle = timeCycle;
    }

    public String getIssueTypeName() {
        return issueTypeName;
    }

    public void setIssueTypeName(String issueTypeName) {
        this.issueTypeName = issueTypeName;
    }
}
