/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.dataaccess.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.sherminator.dataaccess.CourseDAO;
import com.sherminator.dataaccess.ExamDAO;
import com.sherminator.dataaccess.StudentDAO;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Student;

public class ExamTest extends TestCase {

	@Test
	public void test() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(2014, 3, 24, 7, 0, 0);
		java.util.Date tempDate = calendar.getTime();
		Date startDateTime = new java.sql.Date(tempDate.getTime());
		
		calendar.clear();
		calendar.set(2014, 4, 24, 7, 0, 0);
		tempDate = calendar.getTime();
		Date endDateTime = new java.sql.Date(tempDate.getTime());
		
		CourseDAO courseDAO = new CourseDAO();
		Course course = new Course("test", "testCourse");
		courseDAO.createCourse(course);
		
		StudentDAO studentDAO = new StudentDAO();
		Student student = new Student("please ignore this person","srootj", "U91919191L", "please ignore this person", "www.youtube.com");
		studentDAO.createStudent(student);
		
		
		List<Exam> exams = new ArrayList<Exam>();
		Exam tempExam = null;
		
		ExamDAO test = new ExamDAO();
		Exam exam = new Exam("testExam", "test", startDateTime, endDateTime, 100, 45, 0);
		
		Boolean createExam = false;
		Boolean setExamID = false;
		//Boolean setStudentExam = false;
		Boolean getExamByExamID = false;
		Boolean getExams = false;
		Boolean getExamsAttendedByStudent = false;
		Boolean getExamsBookedByDate = false;
		Boolean getExamsBookedByStudent = false;
		Boolean getExamsByCourse = false;
		Boolean getExamsByStudent = false;
		Boolean updateExam = false;
		Boolean deleteExam = false;
		
		//createExam
		createExam = test.createExam(exam);
		
		studentDAO.studentEnrollCourse(student, course);
		
		//set exam id
		setExamID = test.setExamID(exam);
		
		studentDAO.studentBookExam(student, exam, startDateTime);
		studentDAO.studentAttendExam(student, exam, startDateTime);
				
		//getExamByExamID
		tempExam = test.getExamByExamID(exam.getExamID());
		if(tempExam.getExamID()==exam.getExamID() 
				&& tempExam.getCourseCode().equals(exam.getCourseCode()) 
				&& tempExam.getDuration()==exam.getDuration()
				&& tempExam.getEndDateTime().toString().equals(exam.getEndDateTime().toString())
				&& tempExam.getExamTitle().equals(exam.getExamTitle())
				&& tempExam.getMaxMarks() == exam.getMaxMarks()
				&& tempExam.getStartDateTime().toString().equals(exam.getStartDateTime().toString())
				){
			getExamByExamID = true;
		}
		
		//getExams
		exams = test.getExams();
		for(Exam e: exams){
			if(e.getExamID()==exam.getExamID() 
					&& e.getCourseCode().equals(exam.getCourseCode()) 
					&& e.getDuration()==exam.getDuration()
					&& e.getEndDateTime().toString().equals(exam.getEndDateTime().toString())
					&& e.getExamTitle().equals(exam.getExamTitle())
					&& e.getMaxMarks() == exam.getMaxMarks()
					&& e.getStartDateTime().toString().equals(exam.getStartDateTime().toString())
					){
				getExams = true;
			}
		}
		
		//getExamsAttendedByStudent
		exams = test.getExamsAttendedByStudent(student);
		for(Exam e: exams){
			if(e.getExamID()==exam.getExamID() 
					&& e.getCourseCode().equals(exam.getCourseCode()) 
					&& e.getDuration()==exam.getDuration()
					&& e.getEndDateTime().toString().equals(exam.getEndDateTime().toString())
					&& e.getExamTitle().equals(exam.getExamTitle())
					&& e.getMaxMarks() == exam.getMaxMarks()
					&& e.getStartDateTime().toString().equals(exam.getStartDateTime().toString())
					){
				getExamsAttendedByStudent = true;
			}
		}
		
		//getExamsBookedByDate
		exams = test.getExamsBookedByDate(startDateTime, endDateTime);
		for(Exam e: exams){
			if(e.getExamID()==exam.getExamID() 
					&& e.getCourseCode().equals(exam.getCourseCode()) 
					&& e.getDuration()==exam.getDuration()
					&& e.getEndDateTime().toString().equals(exam.getEndDateTime().toString())
					&& e.getExamTitle().equals(exam.getExamTitle())
					&& e.getMaxMarks() == exam.getMaxMarks()
					&& e.getStartDateTime().toString().equals(exam.getStartDateTime().toString())
					){
				getExamsBookedByDate = true;
			}
		}
		
		//getExamsBookedByStudent
		exams = test.getExamsBookedByStudent(student);
		for(Exam e: exams){
			if(e.getExamID()==exam.getExamID() 
					&& e.getCourseCode().equals(exam.getCourseCode()) 
					&& e.getDuration()==exam.getDuration()
					&& e.getEndDateTime().toString().equals(exam.getEndDateTime().toString())
					&& e.getExamTitle().equals(exam.getExamTitle())
					&& e.getMaxMarks() == exam.getMaxMarks()
					&& e.getStartDateTime().toString().equals(exam.getStartDateTime().toString())
					){
				getExamsBookedByStudent = true;
			}
		}
		
		//getExamsByCourse
		exams = test.getExamsByCourse(course);
		for(Exam e: exams){
			if(e.getExamID()==exam.getExamID() 
					&& e.getCourseCode().equals(exam.getCourseCode()) 
					&& e.getDuration()==exam.getDuration()
					&& e.getEndDateTime().toString().equals(exam.getEndDateTime().toString())
					&& e.getExamTitle().equals(exam.getExamTitle())
					&& e.getMaxMarks() == exam.getMaxMarks()
					&& e.getStartDateTime().toString().equals(exam.getStartDateTime().toString())
					){
				getExamsByCourse = true;
			}
		}
		
		//getExamsByStudent
		exams = test.getExamsByStudent(student);
		for(Exam e: exams){
			if(e.getExamID()==exam.getExamID() 
					&& e.getCourseCode().equals(exam.getCourseCode()) 
					&& e.getDuration()==exam.getDuration()
					&& e.getEndDateTime().toString().equals(exam.getEndDateTime().toString())
					&& e.getExamTitle().equals(exam.getExamTitle())
					&& e.getMaxMarks() == exam.getMaxMarks()
					&& e.getStartDateTime().toString().equals(exam.getStartDateTime().toString())
					){
				getExamsByStudent = true;
			}
		}
		
		//updateCWS
		exam.setExamTitle("testExam2");
		updateExam = test.updateExam(exam);
		
		
		//deleteExam
		studentDAO.deleteStudentBookExam(student, exam, startDateTime);
		studentDAO.deleteStudentEnrollCourse(student, course);
		studentDAO.deleteStudent(student);
		deleteExam = test.deleteExam(exam);
		deleteExam = courseDAO.deleteCourse(course);
		
		
		//testResults
		assertEquals("true", createExam.toString());
		System.out.println("ExamDAO.createExam(Exam exam) passed");
		assertEquals("true", setExamID.toString());
		System.out.println("ExamDAO.setExamID(Exam exam) passed");
		/*assertEquals("true", setStudentExam.toString());
		System.out.println("ExamDAO.setStudentExam(Exam exam) passed");*/
		assertEquals("true", getExamByExamID.toString());
		System.out.println("ExamDAO.getExamByExamID(int ID) passed");
		assertEquals("true", getExams.toString());
		System.out.println("ExamDAO.getExams() passed");
		assertEquals("true", getExamsAttendedByStudent.toString());
		System.out.println("ExamDAO.getExamsAttendedByStudent(Student student) passed");
		assertEquals("true", getExamsBookedByDate.toString());
		System.out.println("ExamDAO.getExamsBookedByDate(Student student) passed");
		assertEquals("true", getExamsBookedByStudent.toString());
		System.out.println("ExamDAO.getExamsBookedByStudent(Student student) passed");
		assertEquals("true", getExamsByCourse.toString());
		System.out.println("ExamDAO.getExamsByCourse(Course course) passed");
		assertEquals("true", getExamsByStudent.toString());
		System.out.println("ExamDAO.getExamsByStudent(Student student) passed");
		assertEquals("true", updateExam.toString());
		System.out.println("ExamDAO.updateExam(Course course) passed");
		
		assertEquals("true", deleteExam.toString());
		System.out.println("ExamDAO.deleteExam(Exam exam) passed");
		System.out.println("All working in ExamDAO");
	}

}
