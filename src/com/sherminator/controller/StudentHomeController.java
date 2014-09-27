/*
 * Creator: Sherman
 * Modified by: 
 * 
 * StudentHome Controller helps StudentHome UI to dynamically get courses that student
 * has from database via managers.by the private method getCoursesOfStudent()
 * 
 * getChoice() is called by listener when student selects a course. The controller will then
 * pass over control to StudentCourseDetailController with the selected_course;
 */
package com.sherminator.controller;

import java.util.ArrayList;
import java.util.List;

import com.sherminator.businesslogic.CourseManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.model.Course;
import com.sherminator.model.Student;
import com.sherminator.presentation.Student_Home_UI;

public class StudentHomeController extends AbstractController{

	private Student student;
	private List<Course> courseList;
	private Course selected_course;
	private Student_Home_UI studUI;
	
	public StudentHomeController(Student stud){
		student=stud;
	}
	
	public void getChoice(Course course, Student_Home_UI studHUI){
		selected_course= course;
		this.studUI = studHUI;
		gotoNextController();
	}
	
	@Override
	public void showPresentation() {
		
		//note: use function return instead of private var; acts as a data re-fetch
		studUI= new Student_Home_UI(student,getCoursesOfStudent(), this);
		studUI.show();
	}

	private List<Course> getCoursesOfStudent(){
		//use manager to get courses store in courseList and return
		courseList= new ArrayList<Course>();
		CourseManager courseMang= new CourseManager();
		courseList=courseMang.getCoursesByStudent(student);
		
		return courseList;
	}
	
	@Override
	public void gotoNextController() {

		studUI.close();
		StudentCourseDetailController studCourseDetCtrl= new StudentCourseDetailController(student,selected_course);
		studCourseDetCtrl.showPresentation();
		
	}

}
