/*
 * @author	Nasruddin
 * @version	1.0
 * 
 */

package com.sherminator.model;

import java.sql.Date;

public class AnomalyReport {
	private int reportID;
	private String username;
	private int examID;
	private Date start_date_time;
	private String notes;
	private String matricNo;
	
	public AnomalyReport(int reportID, Date start_date_time, String notes, String username, String matricNo, int examID){
		this.reportID = reportID;
		this.start_date_time = start_date_time;
		this.notes = notes;
		this.username = username;
		this.matricNo = matricNo;
		this.examID = examID;
	}
	public AnomalyReport(Date start_date_time, String notes, String username, String matricNo, int examID){
		this(-1, start_date_time, notes, username, matricNo,examID);
	}
	
	public int getReportID(){
		return reportID;
	}
	
	public void setReportID(int reportID){
		this.reportID = reportID;
	}
	
	public Date getStartDateTime(){
		return start_date_time;
	}
	
	public void setStartDateTime(Date start_date_time){
		this.start_date_time = start_date_time;
	}
	
	public String getNotes(){
		return notes;
	}
	
	public void setNotes(String notes){
		this.notes = notes;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getMatricNo(){
		return matricNo;
	}
	
	public void setMatricNo(String matricNo){
		this.matricNo = matricNo;
	}
	
	public int getExamID(){
		return examID;
	}
	
	public void setExamId(int examID){
		this.examID = examID;
	}
}
