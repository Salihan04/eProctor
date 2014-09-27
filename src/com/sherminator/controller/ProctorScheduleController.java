/*
 * @author	Salihan
 * @version	1.0
 * 
 */
package com.sherminator.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;
import com.sherminator.presentation.ProctorSchedule_UI;

public class ProctorScheduleController extends AbstractController {
	
	private Proctor proctor;
	private List<Student> studentsByBookedDate;
	private List<Exam> examsByBookedDate;
	private List<Exam> examsAttendedByStudent;
	private Date currentDateTime_sql;
	private Date bookedDateTime1;
	private Date bookedDateTime2;
	
	private ExamManager examManager = new ExamManager();
	private StudentManager studentManager = new StudentManager();
	
	private ProctorSchedule_UI proctorSchedule_ui;
	
	public ProctorScheduleController(Proctor proctor) {
		this.proctor = proctor;
		
		setBookedDateTime1();
		setBookedDateTime2();
		
		this.studentsByBookedDate = getStudentsByBookedDate();
		this.examsByBookedDate = getExamsByBookedDate();
	}

	@Override
	public void showPresentation() {
		
		proctorSchedule_ui = new ProctorSchedule_UI(proctor, this);
		proctorSchedule_ui.show();
	}

	@Override
	public void gotoNextController() {
		
		
	}
	
	public void setBookedDateTime1() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDateTime_util = calendar.getTime();
		this.currentDateTime_sql = new java.sql.Date(currentDateTime_util.getTime());
		
		calendar.clear();
		calendar.set(Calendar.YEAR, currentDateTime_sql.getYear() + 1900);
		calendar.set(Calendar.DATE, currentDateTime_sql.getDate());
		calendar.set(Calendar.MONTH, currentDateTime_sql.getMonth());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		java.util.Date bookedDateTime1_util = calendar.getTime();
		this.bookedDateTime1 = new java.sql.Date(bookedDateTime1_util.getTime());
	}
	
	public Date getBookedDateTime1() {
		return bookedDateTime1;
	}
	
	public void setBookedDateTime2() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDateTime_util = calendar.getTime();
		this.currentDateTime_sql = new java.sql.Date(currentDateTime_util.getTime());
		
		calendar.clear();
		calendar.set(Calendar.YEAR, currentDateTime_sql.getYear() + 1900);
		calendar.set(Calendar.DATE, currentDateTime_sql.getDate());
		calendar.set(Calendar.MONTH, currentDateTime_sql.getMonth());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MILLISECOND, 59);
		java.util.Date bookedDateTime2_util = calendar.getTime();
		this.bookedDateTime2 = new java.sql.Date(bookedDateTime2_util.getTime());
	}
	
	public Date getBookedDateTime2() {
		return bookedDateTime1;
	}
	
	public List<Student> getStudentsByBookedDate() {
		
		studentsByBookedDate = studentManager.getStudentsByBookedExamDate(bookedDateTime1, bookedDateTime2);
		
		return studentsByBookedDate;
	}
	
	public List<Exam> getExamsByBookedDate() {
		examsByBookedDate = examManager.getExamsBookedByDate(bookedDateTime1, bookedDateTime2);
		
		return examsByBookedDate;
	}
}
