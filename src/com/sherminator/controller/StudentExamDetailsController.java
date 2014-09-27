/*
 * Creator: Sherman
 * Modified by: 
 * 
 * StudentExamDetails Controller can transit to TakeExamController when student starts
 * exam via public startExam() method. It also backtracks to StudentCourseDetail Controller
 * via back() method called by StudentExamDetail UI
 */
package com.sherminator.controller;

import java.sql.Date;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.businesslogic.InvigilationSessionManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.InvigilationSession;
import com.sherminator.model.Student;
import com.sherminator.presentation.StudentExamDetails_UI;

public class StudentExamDetailsController extends AbstractController{
	private Course course;
	private Student student;
	private Exam exam;
	private InvigilationSession session;
	//need to get results for that quiz too!
	private StudentExamDetails_UI studExamDet;
	
	private ExamManager examManager = new ExamManager();
	private InvigilationSessionManager invigilationSessionMgr = new InvigilationSessionManager();	
	private StudentManager studentManager = new StudentManager();
		
	public StudentExamDetailsController(Course course, Student stud, Exam exam){
		this.course=course;
		this.student=stud;
		this.exam=exam;
	}

	@Override
	public void showPresentation() {
		studExamDet = new StudentExamDetails_UI(student, course ,exam, this);
		studExamDet.show();
	}

	public void startExam(){
		gotoNextController();
	}
	
	@Override
	public void gotoNextController() {
		studExamDet.close();
		TakeExamController takeECtrl= new TakeExamController(student, exam, session);
		takeECtrl.showPresentation();
	}
	
	public void back(){
		gotoPrevController();
	}
	
	protected void gotoPrevController(){
		studExamDet.close();
		StudentCourseDetailController studCDCtrl = new StudentCourseDetailController(student, course);
		studCDCtrl.showPresentation();
	}
	
	public Date getExamBookingDateTimeByStudent(Exam exam, Student student) {
		return examManager.getExamBookingDateTimeByStudent(exam, student);
	}
	
	public InvigilationSession getAvailableInvigilator() {
		session = invigilationSessionMgr.getAvailableInvigilator();
		return session;
	}
	
	public boolean studentBookExam(Student student, Exam exam, Date date) {
		return studentManager.studentBookExam(student, exam, date);
	}
	
	public boolean studentCancelBooking(Student student, Exam exam) {
		return studentManager.studentCancelBooking(student, exam);
	}
	
	
}
