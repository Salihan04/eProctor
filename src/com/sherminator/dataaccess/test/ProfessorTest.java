/*
 * @author	Salihan
 * @version	1.0
 * 
 */
package com.sherminator.dataaccess.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.sherminator.dataaccess.CourseDAO;
import com.sherminator.dataaccess.ProfessorDAO;
import com.sherminator.model.Course;
import com.sherminator.model.Professor;

public class ProfessorTest extends TestCase {

	@Test
	public void test() {
		Boolean createProfessor = false;
		Boolean profTeachCourse = false;
		Boolean getProfessorByUsername = false;
		Boolean updateProfessor = false;
		Boolean getProfessorByStaffID = false;
		Boolean getCoursesByProfessorStaffID = false;
		Boolean updateProfTeachCourse = false;
		Boolean getCoursesByProfessorUsername = false;
		Boolean deleteProfTeachCourse = false;
		Boolean deleteProfessor = false;
		
		CourseDAO courseDAO = new CourseDAO();
		Course course = new Course("test", "testCourse");
		courseDAO.createCourse(course);
		Course course2 = new Course("test2", "testCourse2");
		courseDAO.createCourse(course2);
		List<Course> courses = new ArrayList<Course>();
		
		ProfessorDAO test = new ProfessorDAO();
		List<Professor> professors = new ArrayList<Professor>();
		Professor professor = new Professor("testProf", "root", "S003");
		Professor tempProfessor = null;
		
		
		
		//createProfessor
		createProfessor = test.createProfessor(professor);
		
		//profTeachCourse
		profTeachCourse = test.profTeachCourse(professor, course);
		
		//getProfessorByUsername
		tempProfessor = test.getProfessorByUserName(professor.getUserName());
		if(tempProfessor==null){
			fail("extracted nothing from getProfessorByUsername");
		}else{
			if(tempProfessor.getUserName().equals(professor.getUserName())
					&& tempProfessor.getStaffID().equals(professor.getStaffID())
					&& tempProfessor.getUserName().equals(professor.getUserName())){
				getProfessorByUsername = true;
			}
		}
		
		//updateProfessor
		professor.setPassword("root2");
		test.updateProfessor(professor);
		tempProfessor = test.getProfessorByUserName(professor.getUserName());
		if(tempProfessor.getUserName().equals(professor.getUserName())
				&& tempProfessor.getStaffID().equals(professor.getStaffID())
				&& tempProfessor.getPassword().equals(professor.getPassword())){
			updateProfessor = true;
		}
		//getProfessorByStaffID
		professors = test.getAllProfessors();
		if(professors==null){
			fail("no professors extracted frome getAllProfessors");
		}else{
			for(Professor p: professors){
				if(p.getUserName().equals(professor.getUserName())
						&& p.getStaffID().equals(professor.getStaffID())
						&& p.getPassword().equals(professor.getPassword())){
					getProfessorByStaffID = true;
					break;
				}
			}
		}
		
		
		//getCoursesByProfessorStaffID
		courses = test.getCoursesByProfessorStaffID(professor.getStaffID());
		if(courses==null){
			fail("no courses extracted from getCoursesByPrfoessorStaffID");
		}else{
			for(Course c: courses){
				if(c.getCourseCode().equals(course.getCourseCode())
						&& c.getCourseName().equals(course.getCourseName())){
					getCoursesByProfessorStaffID=true;
					break;
				}
			}
		}
		
		//updateProfTeachCourse
		updateProfTeachCourse = test.updateProfTeachCourse(tempProfessor, course, course2);
		
		//getCoursesByProfessorUsername
		courses = test.getCoursesByProfessorUserName(professor.getUserName());
		if(courses==null){
			fail("no courses extracted frome getCoursesByProfessorUserName");
		}else{
			for(Course c: courses){
				if(c.getCourseCode().equals(course2.getCourseCode())
						&& c.getCourseName().equals(course2.getCourseName())){
					getCoursesByProfessorUsername=true;
					break;
				}
			}
		}
		
		
		//deleteProfessorTeachCourse
		deleteProfTeachCourse = test.deleteProfTeachCourse(professor, course2);
		
		//deleteProfessor
		deleteProfessor = test.deleteProfessor(professor);
		courseDAO.deleteCourse(course);
		courseDAO.deleteCourse(course2);
		
		
		//test Results
		assertEquals("true", createProfessor.toString());
		System.out.println("ProfessorDAO.createProfessor(Professor professor)");
		assertEquals("true", profTeachCourse.toString());
		System.out.println("ProfessorDAO.profTeachCourse()");
		assertEquals("true", getProfessorByUsername.toString());
		System.out.println("ProfessorDAO.getProfessorByUsername(String username)");
		assertEquals("true", updateProfessor.toString());
		System.out.println("ProfessorDAO.updateProfessor(String username)");
		assertEquals("true", getProfessorByStaffID.toString());
		System.out.println("ProfessorDAO.getProfessorByStaffID(String username)");
		assertEquals("true", getCoursesByProfessorStaffID.toString());
		System.out.println("ProfessorDAO.getCoursesByProfessorByStaffID(String username)");
		assertEquals("true", updateProfTeachCourse.toString());
		System.out.println("ProfessorDAO.updateProfTeachCourse(String username)");
		assertEquals("true", getCoursesByProfessorUsername.toString());
		System.out.println("ProfessorDAO.getCoursesByProfessorUsername(String username)");
		assertEquals("true", updateProfTeachCourse.toString());
		System.out.println("ProfessorDAO.updateProfTeachCourse()");
		assertEquals("true", deleteProfTeachCourse.toString());
		System.out.println("ProfessorDAO.deleteProfTeachCourse()");
		assertEquals("true", deleteProfessor.toString());
		System.out.println("ProfessorDAO.deleteProfessor(Professor professor)");
		System.out.println("all working in ProfessorDAO");
	}

}
