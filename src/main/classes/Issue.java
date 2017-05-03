package main.classes;

import java.util.Date;

public class Issue {

	private int id;
    private Date createdDate;
    private Date completedDate;
    private String issueKey;
    private int timeCycle;
    private String issueTypeName;
    private long dateCache;

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
    	if(timeCycle!=0)
    		this.timeCycle = timeCycle;
    	else
    		this.timeCycle = 1;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIssueTypeName() {
        return issueTypeName;
    }

    public void setIssueTypeName(String issueTypeName) {
        this.issueTypeName = issueTypeName;
    }

	public long getDateCache() {
		return dateCache;
	}

	public void setDateCache(long dateCache) {
		this.dateCache = dateCache;
	}
    
    
}
