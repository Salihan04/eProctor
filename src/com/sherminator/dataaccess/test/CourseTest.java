/*
 * @author	Sherman
 * @version	1.0
 * 
 */
package com.sherminator.dataaccess.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.sherminator.dataaccess.CourseDAO;
import com.sherminator.dataaccess.ProfessorDAO;
import com.sherminator.dataaccess.StudentDAO;
import com.sherminator.model.Course;
import com.sherminator.model.Professor;
import com.sherminator.model.Student;

public class CourseTest extends TestCase {

	@Test
	public void test() {
		Boolean insertCourse = false;
		Boolean updateCourse= false;
		Boolean getCourseByCourseCode = false;
		Boolean getCourses = false;
		Boolean getCoursesByProfessor = false;
		Boolean getCoursesByStudent = false;

		Boolean removeCourse = false;
		
		CourseDAO test = new CourseDAO();
		Course course = new Course("test", "testCourse");
		ProfessorDAO profDAO = new ProfessorDAO();
		Professor professor = new Professor("testProf", "root", "S003");
		profDAO.createProfessor(professor);
		StudentDAO studentDAO = new StudentDAO();
		Student student = new Student("please ignore this person","srootj", "U91919191L", "please ignore this person", "www.youtube.com");
		studentDAO.createStudent(student);
		Course tempCourse = null;
		List<Course> courses = new ArrayList<Course>();
		
		
		//CreateCourse
		insertCourse = test.createCourse(course);
		profDAO.profTeachCourse(professor, course);
		studentDAO.studentEnrollCourse(student, course);
		
		//updateCourse
		course.setCourseName("testCourse2");
		updateCourse = test.updateCourse(course);
		
		//getCourseByCourseCode
		tempCourse = test.getCourseByCourseCode(course.getCourseCode());
		if(tempCourse.getCourseName().equals(course.getCourseName()) && tempCourse.getCourseCode().equals(course.getCourseCode())){
			getCourseByCourseCode = true;
		}
		
		//getCourses
		courses= test.getCourses();
		for(Course c: courses){
			if(c.getCourseCode().equals(course.getCourseCode()) && c.getCourseName().equals(course.getCourseName())){
				getCourses = true;
			}
		}
		
		//getCoursesByProfessor
		courses = test.getCoursesByProfessor(professor);
		for(Course c: courses){
			if(c.getCourseCode().equals(course.getCourseCode()) && c.getCourseName().equals(course.getCourseName())){
				getCoursesByProfessor = true;
			}
		}
		
		//getCoursesByStudents
		courses = test.getCoursesByStudent(student);
		for(Course c: courses){
			if(c.getCourseCode().equals(course.getCourseCode()) && c.getCourseName().equals(course.getCourseName())){
				getCoursesByStudent = true;
			}
		}
		
		//delete
		profDAO.deleteProfTeachCourse(professor, course);
		profDAO.deleteProfessor(professor);
		studentDAO.deleteStudentEnrollCourse(student, course);
		studentDAO.deleteStudent(student);
		removeCourse = test.deleteCourse(course);
		
		//test results
		assertEquals("true", insertCourse.toString());
		System.out.println("CourseDAO.createCourse(Course course) passed.");
		assertEquals("true", updateCourse.toString());
		System.out.println("CourseDAO.updateCourse(Course course) passed.");
		assertEquals("true", getCourseByCourseCode.toString());
		System.out.println("CourseDAO.getCourseByCourseCode(Course course) passed.");
		assertEquals("true", getCourses.toString());
		System.out.println("CourseDAO.getCourses() passed.");
		assertEquals("true", getCoursesByProfessor.toString());
		System.out.println("CourseDAO.getCoursesByProfessor(Professor professor) passed.");
		assertEquals("true", getCoursesByStudent.toString());
		System.out.println("CourseDAO.getCoursesByStudent(Student student) passed.");
		
		assertEquals("true", insertCourse.toString());
		System.out.println("CourseDAO.deleteCourse(course) passed.");
		System.out.println("all working in CourseDAO");
	}

}
