/*
 * 
 * @author	Soe Lynn
 * @author	Sherman
 * @version	1.1
 * 
 */
package com.sherminator.dataaccess;

import java.sql.Date;
import java.util.Hashtable;
import java.util.List;

import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;
import com.sherminator.model.Student;
import com.sherminator.model.User;

public interface StudentDataAccess extends UserDataAccess {
	
	public boolean createStudent(Student student);
	public boolean deleteStudent(Student student);
	public boolean updateStudent(Student student);
	public boolean authenticate(User user);
	public List<Student> getStudentsEnrolledInCourse(Course course);
	public List<Student> getStudentByName(String name);
	public Student getStudentByMatricNo(String matricNo);
	public Student getStudentByUserName(String userName);
	public List<Student> getStudents();
	public boolean studentChooseAnswer(Student student, Answer answer);
	public boolean studentChooseAnswers(Student student, List<Answer> answers);
	public boolean studentChooseAnswers(Student student, List<Question> questions, Hashtable<Question, Answer> answers);
	public boolean studentEnrollCourse(Student student, Course course);
	public boolean updateStudentEnrollCourse(Student student, Course oldCourse, Course newCourse);
	public boolean deleteStudentEnrollCourse(Student student, Course course);
	public boolean studentBookExam(Student student,Exam exam,Date datetime);
	public boolean studentCancelBooking(Student student,Exam exam);
	public boolean updateStudentBookExam(Student student,Exam exam,Date datetime);
	public boolean deleteStudentBookExam(Student student,Exam exam,Date datetime);
	public boolean studentAttendExam(Student student,Exam exam,Date datetime);
	public List<Student> getStudentsByBookedExamDate(Date bookedDateTime1, Date bookedDateTime2);
	
}
