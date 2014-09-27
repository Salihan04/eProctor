/*
 * Creator: Sherman
 * Updated by:
 * 
 * StudentCourseDetailController acts as middle-man to and helps StudentCourseDetail UI to retrieve all exams and their details
 * via getExamDetails() method
 * 
 * This controller also gets selected exam input from user and then transits to StudentExamDetails UI
 * This controller allows UI to backtrack by back()
 */
package com.sherminator.controller;

import java.util.ArrayList;
import java.util.List;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Student;
import com.sherminator.presentation.StudentCourseDetails_UI;

public class StudentCourseDetailController extends AbstractController{
	
	private Student student;
	private Course course;
	private StudentCourseDetails_UI studCourseDetUI;
	private List<Exam> exam;
	private Exam selected_exam;
	
	public StudentCourseDetailController(Student stud, Course course){
		this.student=stud;
		this.course=course;
	}

	@Override
	public void showPresentation() {
		
		studCourseDetUI= new StudentCourseDetails_UI(student,course, this);
		studCourseDetUI.show();
		
	}

	public void getSelectedExam(Exam exam){
		selected_exam= exam;
		gotoNextController();
	}
	
	public List<Exam> getExamDetails(){
		ExamManager examMgr= new ExamManager();
		return examMgr.getExamsByCourse(course);
	}
	@Override
	public void gotoNextController() {
		studCourseDetUI.close();
		StudentExamDetailsController sERCtrl=new StudentExamDetailsController(course,student,selected_exam);
		//studCourseDetUI.close();
		sERCtrl.showPresentation();
	}

	protected void gotoPrevController(){
		studCourseDetUI.close();
		StudentHomeController studHCtrl= new StudentHomeController(student);
		//studCourseDetUI.close();
		studHCtrl.showPresentation();
	}
	
	public void back(){
		gotoPrevController();
	}
}
