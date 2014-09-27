/*
 * 
 * Author: Salihan
 * Updated By: Soe Lynn
 */

package com.sherminator.model;
import java.text.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class Exam {
	private int examID;
	private String examTitle;
	private String courseCode;
	private Date startDateTime;
	private Date endDateTime;
	private int maxMarks;
	private float duration;
	private int no_of_questions;
	private DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	
	public Exam(String examTitle, String courseCode, Date startDateTime, Date endDateTime, int maxMarks, float duration, int no_of_questions){
		this(-1, examTitle, courseCode, startDateTime, endDateTime, maxMarks, duration, no_of_questions);
		
	}
	
	public Exam(int examID, String examTitle, String courseCode, Date startDateTime, Date endDateTime, int maxMarks, float duration, int no_of_questions){
		this.examID = examID;
		this.examTitle = examTitle;
		this.courseCode = courseCode;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.maxMarks = maxMarks;
		this.duration = duration;
		this.no_of_questions = no_of_questions;	
	}
	
	public int getExamID(){
		return examID;
	}
	
	public void setExamID(int examID) {
		this.examID = examID;
	}
	
	public String getExamTitle() {
		return examTitle;
	}
	
	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}
	
	public String getCourseCode(){
		return courseCode;
	}
	
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public Date getStartDateTime(){
		return startDateTime;
	}
	
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public Date getEndDateTime(){
		return endDateTime;
	}
	
	public void setendDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	public int getMaxMarks(){
		return maxMarks;
	}
	
	public void setMaxMarks(int maxMarks) {
		this.maxMarks = maxMarks;
	}
	
	public float getDuration() {
		return duration;
	}
	
	public void setDuration(float duration) {
		this.duration = duration;
	}
	
	public int getNo_of_questions() {
		return no_of_questions;
	}

	public void setNo_of_questions(int no_of_questions) {
		this.no_of_questions = no_of_questions;
	}

	public boolean equals(Object o) {
		Exam e = (Exam)o;
		return examID == e.getExamID();		
	}
}
