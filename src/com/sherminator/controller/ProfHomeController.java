/*
 * Creator: Sherman
 * Modified:
 * 
 * Description:
 * Controller to fetch dynamic info for ProfessorHome_UI and handle other functionality
 */
package com.sherminator.controller;

import java.util.ArrayList;
import java.util.List;

import com.sherminator.businesslogic.ProfessorManager;
import com.sherminator.model.Course;
import com.sherminator.model.Professor;
import com.sherminator.presentation.ProfessorHome_UI;

public class ProfHomeController extends AbstractController{
	private Professor prof;
	private ProfessorHome_UI profUI;
	private Course selected_course;
	private List<Course> courseList;
	
	public ProfHomeController(Professor prof){
		this.prof=prof;
		this.courseList = getProfCourses();
	}
	@Override
	public void gotoNextController() {
		//goes to ProfCourseDetailController
		ProfCourseDetailController profCourseDetCtrl = new ProfCourseDetailController(prof,selected_course);
		profCourseDetCtrl.showPresentation();
		profUI.close();
		
	}
	@Override
	public void showPresentation() {
		profUI= new ProfessorHome_UI(prof,getProfCourses(),this);
		profUI.show();
	}
	
	public List<Course> getProfCourses(){
		//to get respective course details from DB, store in courseList
		ProfessorManager profMang= new ProfessorManager();
		courseList=profMang.getCoursesByProfessorUserName(prof.getUserName());
		return courseList;
	}

	public void getSelectedCourse(Course course){
		//get user input from listener, update selected_course variable
		this.selected_course = course;
		gotoNextController();
	}
}
