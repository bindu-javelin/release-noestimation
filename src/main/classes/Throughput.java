package main.classes;

import java.util.Date;

public class Throughput {

    private Date startDate;
    private Date doneDate;
    private int countOfDate;
    private int taktTime;
    private int inParallel;

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public int getCountOfDate() {
        return countOfDate;
    }

    public void setCountOfDate(int countOfDate) {
        this.countOfDate = countOfDate;
    }

    public int getTaktTime() {
        return taktTime;
    }

    public void setTaktTime(int taktTime) {
        this.taktTime = taktTime;
    }

    public int getInParallel() {
        return inParallel;
    }

    public void setInParallel(int inParallel) {
        this.inParallel = inParallel;
    }

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
    
    
    
}

