/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.businesslogic;

import java.util.List;

import com.sherminator.dataaccess.CourseDataAccess;
import com.sherminator.dataaccessfactory.CourseDataAccessFactory;
import com.sherminator.model.Course;
import com.sherminator.model.Professor;
import com.sherminator.model.Student;

public class CourseManager {
	private CourseDataAccessFactory factory;
	private CourseDataAccess courseDataAccess;
	
	public CourseManager() {
		factory = new CourseDataAccessFactory();
		courseDataAccess = factory.getDataAccess();
	}
	
	public boolean createCourse(Course course){
		return courseDataAccess.createCourse(course);
	}
	
	public boolean updateCourse(Course course){
		return courseDataAccess.updateCourse(course);
	}
	
	public boolean deleteCourse(Course course){
		return courseDataAccess.deleteCourse(course);
	}
	
	public Course getCourseByCourseCode(String courseCode){
		return courseDataAccess.getCourseByCourseCode(courseCode);
	}
	
	public List<Course> getCoursesByStudent(Student student){
		return courseDataAccess.getCoursesByStudent(student);
	}
	
	public List<Course> getCoursesByProfessor(Professor professor){
		return courseDataAccess.getCoursesByProfessor(professor);
	}
	
	public List<Course> getCourses(){
		return courseDataAccess.getCourses();
	}
	
}
