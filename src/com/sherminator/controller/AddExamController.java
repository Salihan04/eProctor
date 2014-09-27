/*
 * @author	Sherman
 * @version	1.0
 * 
 */
package com.sherminator.controller;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;
import com.sherminator.presentation.AddExam_UI;

public class AddExamController extends AbstractController {
	
	private Professor professor;
	private Course course;
	
	private AddExam_UI addExamUI;
	
	public AddExamController(Professor professor, Course course) {
		this.professor = professor;
		this.course = course;
	}

	@Override
	public void showPresentation() {
		
		addExamUI = new AddExam_UI(professor, course, this);
		addExamUI.show();
	}

	@Override
	public void gotoNextController() {
		
		ProfCourseDetailController profCourseDetCtrl = new ProfCourseDetailController(professor, course);
		profCourseDetCtrl.showPresentation();
		addExamUI.close();
	}
	
	public void addExam(Exam exam) {
		ExamManager examManager = new ExamManager();
		examManager.createExam(exam);
	}

}
