/*
 * Creator: Sherman
 * Updated by:
 * 
 */
package com.sherminator.controller;

import java.util.List;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;
import com.sherminator.presentation.ProfessorCourseDetails_UI;

public class ProfCourseDetailController extends AbstractController{

	private ProfessorCourseDetails_UI profCD_ui;
	private Exam selectedExam;
	private List<Exam> examList;
	private Course course;
	private Professor prof;

	public ProfCourseDetailController(Professor prof,Course course){
		this.course=course;
		this.prof=prof;
		this.examList = getProfExamList();
	}
	
	@Override
	public void showPresentation() {
		
		profCD_ui= new ProfessorCourseDetails_UI(prof,examList,course, this);
		profCD_ui.show();
	}
	
	public List<Exam> getProfExamList(){
		//please double check logic
		ExamManager examMgr= new ExamManager();
		examList = examMgr.getExamsByCourse(course);
		return examList;
	}
	
	//goto ProfExamDetailController, student tab by default
	@Override
	public void gotoNextController() {

		//ProfessorExamDetail UI and controller not implemented as of yet;
		ProfExamDetailController profEDCtrl= new ProfExamDetailController(prof,course,selectedExam);
		profEDCtrl.showPresentation();
		profCD_ui.close();
	}	
	
	public void gotoPreviousController(){
		ProfHomeController profHomeController = new ProfHomeController(prof);
		profHomeController.showPresentation();
		profCD_ui.close();
	}
	
	public void gotoAddExamController() {
		AddExamController addExamController = new AddExamController(prof, course);
		addExamController.showPresentation();
		profCD_ui.close();
	}
	
	public void gotoEditExamController(Exam exam) {
		
		EditExamController editExamController = new EditExamController(prof, course, exam);
		editExamController.showPresentation();
		profCD_ui.close();
	}
	
	public void getSelectedExam(Exam exam) {
		this.selectedExam = exam;
		gotoNextController();
	}
}
