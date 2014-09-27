/*
 * @author	Nasruddin
 * @version	1.0
 * 
 */
package com.sherminator.dataaccess.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.sherminator.dataaccess.AnswerDAO;
import com.sherminator.dataaccess.CourseDAO;
import com.sherminator.dataaccess.ExamDAO;
import com.sherminator.dataaccess.QuestionDAO;
import com.sherminator.dataaccess.StudentDAO;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;
import com.sherminator.model.Student;

public class StudentTest extends TestCase {

	@Test
	public void test() {
		Boolean createStudent = false;
		Boolean getStudentByMatricNo = false;
		Boolean updateStudent = false;
		Boolean getStudentByName = false;
		Boolean getStudentByUsername = false;
		Boolean getStudents = false;
		Boolean studentEnrollCourse = false;
		Boolean getStudentsEnrolledInCourse = false;
		Boolean updateStudentEnrollCourse = false;
		Boolean studentBookExam = false;
		Boolean updateStudentBookExam = false;
		Boolean studentAttendExam = false;
		Boolean studentChooseAnswers = false;
		Boolean deleteStudentAttendExam = false;
		Boolean deleteStudentBookExam = false;
		Boolean deleteStudentEnrollCourse = false;
		Boolean deleteStudent = false;
		
		//start and date time for exam1
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(2014, 3, 24, 7, 0, 0);
		java.util.Date tempDate = calendar.getTime();
		Date startDateTime1 = new java.sql.Date(tempDate.getTime());
		calendar.clear();
		calendar.set(2014, 4, 24, 7, 0, 0);
		tempDate = calendar.getTime();
		Date endDateTime1 = new java.sql.Date(tempDate.getTime());
		
		//bookdateTime before and after update
		calendar.clear();
		calendar.set(2014, 3, 24, 8, 0, 0);
		Date bookDateTime1 = new java.sql.Date(tempDate.getTime());
		calendar.clear();
		calendar.set(2014, 3, 25, 8, 0, 0);
		tempDate = calendar.getTime();
		Date bookDateTime2 = new java.sql.Date(tempDate.getTime());
		
		
		//database objects
		CourseDAO courseDAO = new CourseDAO();
		ExamDAO examDAO = new ExamDAO();
		QuestionDAO questionDAO = new QuestionDAO();
		AnswerDAO answerDAO = new AnswerDAO();
		StudentDAO test = new StudentDAO();
		
		Course course1 = new Course("test1", "testCourse1");
		courseDAO.createCourse(course1);
		Course course2 = new Course("test2", "testCourse2");
		courseDAO.createCourse(course2);
		List<Course> courses = new ArrayList<Course>();
		
		Exam exam1 = new Exam("testExam1", "test2", startDateTime1, endDateTime1, 100, 45, 2 );
		examDAO.createExam(exam1);
		examDAO.setExamID(exam1);
		List<Exam> exams = new ArrayList<Exam>();
		
		Question question1 = new Question("test question1", exam1.getExamID());
		questionDAO.createQuestion(question1);
		questionDAO.setQuestionID(question1);
		Answer answer1 = new Answer("Please ignore this anwer1", question1.getQuestionID());
		answerDAO.createAnswer(answer1);
		answerDAO.setAnswerID(answer1);
		question1.setCorrectAnswer(answer1);
		questionDAO.updateQuestion(question1);
		
		
		Question question2 = new Question("test question2", exam1.getExamID());
		questionDAO.createQuestion(question2);
		questionDAO.setQuestionID(question2);
		Answer answer2 = new Answer("Please ignore this anwer2", question2.getQuestionID());
		answerDAO.createAnswer(answer2);
		answerDAO.setAnswerID(answer2);
		question2.setCorrectAnswer(answer2);
		questionDAO.updateQuestion(question2);
		
		Question question3 = new Question("test question3", exam1.getExamID());
		questionDAO.createQuestion(question3);
		questionDAO.setQuestionID(question3);
		Answer answer3 = new Answer("Please ignore this anwer3", question3.getQuestionID());
		answerDAO.createAnswer(answer3);
		answerDAO.setAnswerID(answer3);
		question3.setCorrectAnswer(answer3);
		questionDAO.updateQuestion(question3);
		List<Question> questions = new ArrayList<Question>();
		List<Question> tempQuestion = new ArrayList<Question>();
		tempQuestion.add(question1);
		tempQuestion.add(question2);
		tempQuestion.add(question3);
		
		Hashtable<Question, Answer> questionAnswers = new Hashtable<Question, Answer>();
		questionAnswers.put(question1, answer1);
		questionAnswers.put(question2, answer2);
		questionAnswers.put(question3, answer3);
		
		
		/*List<Answer> answers = new ArrayList<Answer>();
		answers.add(answer2);
		answers.add(answer3);*/
		List<Answer> tempAnswers = new ArrayList<Answer>();
		
		Student tempStudent = null;
		Student student = new Student("please ignore this person","srootj", "U91919191L", "please ignore this person", "www.youtube.com");
		List<Student> students = new ArrayList<Student>();
		
		//createStudent
		createStudent = test.createStudent(student);
		students = test.getStudents();
		if(students==null){
			fail("student not created or getStudents not working");
		}else {
			for(Student s: students){
				if(s.getMatricNo().equals(student.getMatricNo())
						&& s.getName().equals(student.getName())
						&& s.getPassword().equals(student.getPassword())
						&& s.getPhoto_url().equals(student.getPhoto_url())
						&& s.getUserName().equals(student.getUserName())){
					createStudent = true;
					getStudents=true;
				}
			}
		}
		
		//getStudentByMatricNo
		tempStudent = test.getStudentByMatricNo(student.getMatricNo());
		if(tempStudent==null){
			fail("nothing was extracted from getStudentByMatricNo");
		}else{
			if(tempStudent.getMatricNo().equals(student.getMatricNo())
					&& tempStudent.getName().equals(student.getName())
					&& tempStudent.getPassword().equals(student.getPassword())
					&& tempStudent.getPhoto_url().equals(student.getPhoto_url())
					&& tempStudent.getUserName().equals(student.getUserName())){
				getStudentByMatricNo = true;
			}
		}
		
		//updateStudent
		student.setName("update please ignore this person");
		updateStudent = test.updateStudent(student);
		students = test.getStudents();
		if(students==null){
			fail("updateStudent not working");
		}else {
			for(Student s: students){
				if(s.getMatricNo().equals(student.getMatricNo())
						&& s.getName().equals(student.getName())
						&& s.getPassword().equals(student.getPassword())
						&& s.getPhoto_url().equals(student.getPhoto_url())
						&& s.getUserName().equals(student.getUserName())){
					updateStudent = true;
				}
			}
		}
		
		//getStudentByName
		students = test.getStudentByName(student.getName());
		if(students==null){
			fail("nothing was extracted from getStudentByName");
		}else{
			for(Student s: students){
				if(s.getMatricNo().equals(student.getMatricNo())
						&& s.getName().equals(student.getName())
						&& s.getPassword().equals(student.getPassword())
						&& s.getPhoto_url().equals(student.getPhoto_url())
						&& s.getUserName().equals(student.getUserName())){
					getStudentByName = true;
					break;
				}
			}
		}
		
		//getStudentByUsername
		tempStudent = test.getStudentByUserName(student.getUserName());
		if(tempStudent==null){
			fail("nothing was extracted from getStudentByUsername");
		}else{
			if(tempStudent.getMatricNo().equals(student.getMatricNo())
					&& tempStudent.getName().equals(student.getName())
					&& tempStudent.getPassword().equals(student.getPassword())
					&& tempStudent.getPhoto_url().equals(student.getPhoto_url())
					&& tempStudent.getUserName().equals(student.getUserName())){
				getStudentByUsername = true;
			}
		}
		
		//getStudents
		students = test.getStudents();
		if(students==null){
			fail("nothing was extracted from getStudents");
		}else{
			for(Student s: students){
				if(s.getMatricNo().equals(student.getMatricNo())
						&& s.getName().equals(student.getName())
						&& s.getPassword().equals(student.getPassword())
						&& s.getPhoto_url().equals(student.getPhoto_url())
						&& s.getUserName().equals(student.getUserName())){
					getStudents = true;
					break;
				}
			}
		}
		
		//studentEnrollCourse
		studentEnrollCourse = test.studentEnrollCourse(student, course1);
		courses = courseDAO.getCoursesByStudent(student);
		if(courses==null){
			fail("student enrollcourse failure. courses extracted returned null");
		}else{
			for(Course c: courses){
				if(c.getCourseCode().equals(course1.getCourseCode())
						&& c.getCourseName().equals(course1.getCourseName()))
				{
					studentEnrollCourse = true;
				}
			}
		}
		
		//getStudentsEnrolledInCourse
		students = test.getStudentsEnrolledInCourse(course1);
		if(students==null){
			fail("nothing was extracted from getStudentsEnrolledInCourse");
		}else{
			for(Student s: students){
				if(s.getMatricNo().equals(student.getMatricNo())
						&& s.getName().equals(student.getName())
						&& s.getPassword().equals(student.getPassword())
						&& s.getPhoto_url().equals(student.getPhoto_url())
						&& s.getUserName().equals(student.getUserName())){
					getStudentsEnrolledInCourse = true;
					break;
				}
			}
		}
		
		//updateStudentEnrollCourse
		updateStudentEnrollCourse = test.updateStudentEnrollCourse(student, course1, course2);
		courses = courseDAO.getCoursesByStudent(student);
		if(courses==null){
			fail("failure in updateStudentEnrollCourse. getCoursesByStduent returned null");
		}else{
			for(Course c: courses){
				if(c.getCourseCode().equals(course2.getCourseCode())
						&& c.getCourseName().equals(course2.getCourseName()))
				{
					updateStudentEnrollCourse = true;
				}
			}
		}
		
		//studentBookExam
		studentBookExam = test.studentBookExam(student, exam1, bookDateTime1);
		exams = examDAO.getExamsBookedByStudent(student);
		if(exams == null){
			fail("studentBook exam failure. get e3xams booked by student returned null");
		} else {
			for(Exam e: exams){
				if(e.getCourseCode().equals(exam1.getCourseCode())
						&& e.getDuration()==exam1.getDuration()
						&& e.getEndDateTime().toString().equals(exam1.getEndDateTime().toString())
						&& e.getExamID()==exam1.getExamID()
						&& e.getExamTitle().equals(exam1.getExamTitle())
						&& e.getMaxMarks()==exam1.getMaxMarks()
						&& e.getStartDateTime().toString().equals(exam1.getStartDateTime().toString())){
					studentBookExam =true;
				}
			}
		}
		//updateStudentBookExam
		updateStudentBookExam = test.updateStudentBookExam(student, exam1, bookDateTime2);
		exams = examDAO.getExamsBookedByStudent(student);
		if( exams == null){
			fail("failure in update student book exam. get exams booked byu student returned null");
		} else {
			for(Exam e: exams){
				if(e.getCourseCode().equals(exam1.getCourseCode())
						&& e.getDuration()==exam1.getDuration()
						&& e.getEndDateTime().toString().equals(exam1.getEndDateTime().toString())
						&& e.getExamID()==exam1.getExamID()
						&& e.getExamTitle().equals(exam1.getExamTitle())
						&& e.getMaxMarks()==exam1.getMaxMarks()
						&& e.getStartDateTime().toString().equals(exam1.getStartDateTime().toString())){
					studentBookExam =true;
				}
			}
		}
		//studentAttendExam
		studentAttendExam = test.studentAttendExam(student, exam1, bookDateTime2);
		exams = examDAO.getExamsAttendedByStudent(student);
		if( exams.size()==0){
			fail("failure in studentAttendExam.getExamsAttendedByStudent returned null");
		} else {
			for(Exam e: exams){
				if(e.getCourseCode().equals(exam1.getCourseCode())
						&& e.getDuration()==exam1.getDuration()
						&& e.getEndDateTime().toString().equals(exam1.getEndDateTime().toString())
						&& e.getExamID()==exam1.getExamID()
						&& e.getExamTitle().equals(exam1.getExamTitle())
						&& e.getMaxMarks()==exam1.getMaxMarks()
						&& e.getStartDateTime().toString().equals(exam1.getStartDateTime().toString())){
					studentAttendExam =true;
				}
			}
		}
		
		//studentChooseAnswers
		test.studentChooseAnswers(student, tempQuestion, questionAnswers);
		tempAnswers = answerDAO.getAnswersChosenByStudentForExam(student, exam1);
		if(tempAnswers.size()==0){
			fail("studentChooseAnswer failure. getAnswersChosenByStudent returned nothing");
		}else{
			if(tempAnswers.contains(answer1)
				&& tempAnswers.contains(answer2) 
				&& tempAnswers.contains(answer3)) {
				studentChooseAnswers=true;
			}
		}
		

		//deleteStudentAttendExam
		deleteStudentAttendExam = test.deleteStudentAttendExam(student, exam1, bookDateTime2);
		
		//deleteStudentBookExam
		deleteStudentBookExam = test.deleteStudentBookExam(student, exam1, bookDateTime2);
		
		//deleteStudentEnrollCourse
		deleteStudentEnrollCourse = test.deleteStudentEnrollCourse(student, course2);

		//deleteExtras
		answerDAO.deleteAnswer(answer1);
		answerDAO.deleteAnswer(answer2);
		answerDAO.deleteAnswer(answer3);
		questionDAO.deleteQuestion(question1);
		questionDAO.deleteQuestion(question2);
		questionDAO.deleteQuestion(question3);
		examDAO.deleteExam(exam1);
		courseDAO.deleteCourse(course1);
		courseDAO.deleteCourse(course2);
		
		//deleteStudent
		deleteStudent = test.deleteStudent(student);
		
		//testResult
		assertEquals("true", createStudent.toString());
		System.out.println("StudentDAO.createStudent() passed");
		
		assertEquals("true", getStudentByMatricNo.toString());
		System.out.println("StudentDAO.getStudentByMatricNo() passed");
		
		assertEquals("true", updateStudent.toString());
		System.out.println("StudentDAO.updateStudent() passed");
		
		assertEquals("true", getStudentByName.toString());
		System.out.println("StudentDAO.getstudentByName() passed");
		
		assertEquals("true", getStudentByUsername.toString());
		System.out.println("StudentDAO.getstudentByUsername() passed");
		
		assertEquals("true", getStudents.toString());
		System.out.println("StudentDAO.getStudents() passed");
		
		assertEquals("true", studentEnrollCourse.toString());
		System.out.println("StudentDAO.studentEnrollCourse() passed");
		
		assertEquals("true", getStudentsEnrolledInCourse.toString());
		System.out.println("StudentDAO.getStudentsEnrolledInCourse() passed");
		
		assertEquals("true", updateStudentEnrollCourse.toString());
		System.out.println("StudentDAO.updateStudentEnrollCourse() passed");
		
		assertEquals("true", studentBookExam.toString());
		System.out.println("StudentDAO.studentBookExam() passed");
		
		assertEquals("true", updateStudentBookExam.toString());
		System.out.println("StudentDAO.updateStudentBookExam() passed");
		
		assertEquals("true", studentAttendExam.toString());
		System.out.println("StudentDAO.studentAttendExam() passed");
		
		assertEquals("true", studentChooseAnswers.toString());
		System.out.println("StudentDAO.studentChooseAnswers() passed");
		
		assertEquals("true", deleteStudentAttendExam.toString());
		System.out.println("StudentDAO.deleteStudentAttendExam() passed");
		
		assertEquals("true", deleteStudentBookExam.toString());
		System.out.println("StudentDAO.deleteStudentBookExam() passed");
		
		assertEquals("true", deleteStudentEnrollCourse.toString());
		System.out.println("StudentDAO.deleteStudentEnrollCourse() passed");
		
		assertEquals("true", deleteStudent.toString());
		System.out.println("StudentDAO.deleteStudent() passed");
		
		System.out.println("All working in StudentDAO!");
	}

}
