/*
 * @author	Salihan
 * @version	1.0
 * 
 */
package com.sherminator.controller;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;
import com.sherminator.presentation.EditExam_UI;

public class EditExamController extends AbstractController {
	
	private Professor professor;
	private Course course;
	private Exam exam;

	private EditExam_UI editExamUI;
	
	public EditExamController(Professor professor, Course course, Exam exam) {
		this.professor = professor;
		this.course = course;
		this.exam = exam;
	}
	
	@Override
	public void showPresentation() {
		
		editExamUI = new EditExam_UI(professor, course, exam, this);
		editExamUI.show();
	}

	@Override
	public void gotoNextController() {
		
		ProfCourseDetailController profCourseDetCtrl = new ProfCourseDetailController(professor, course);
		profCourseDetCtrl.showPresentation();
		editExamUI.close();
	}
	
	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public void updateExam(Exam exam) {
		this.exam = exam;
		
		ExamManager examManager = new ExamManager();
		examManager.updateExam(exam);		
	}

}
