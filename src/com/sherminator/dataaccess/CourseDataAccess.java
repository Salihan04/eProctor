/*
 * 
 * @author	Soe Lynn
 * @author	Sherman
 * @version	1.1
 * 
 */

package com.sherminator.dataaccess;

import java.util.List;

import com.sherminator.model.Course;
import com.sherminator.model.Professor;
import com.sherminator.model.Student;

public interface CourseDataAccess extends DataAccess{

	public boolean createCourse(Course course);
	public boolean updateCourse(Course course);
	public boolean deleteCourse(Course course);
	public Course getCourseByCourseCode(String courseCode);
	public List<Course> getCoursesByStudent(Student student);
	public List<Course> getCoursesByProfessor(Professor professor);
	public List<Course> getCourses();
}
