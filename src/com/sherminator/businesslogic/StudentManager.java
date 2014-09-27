/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.businesslogic;

import java.sql.Date;
import java.util.Hashtable;
import java.util.List;

import com.sherminator.dataaccess.StudentDataAccess;
import com.sherminator.dataaccessfactory.StudentDataAccessFactory;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;
import com.sherminator.model.Student;
import com.sherminator.model.User;

public class StudentManager implements UserManager {
	
	private StudentDataAccessFactory factory;
	private StudentDataAccess studentDataAccess;
	
	public StudentManager() {
		factory = new StudentDataAccessFactory();
		studentDataAccess = factory.getDataAccess();
	}
	
	public boolean createStudent(Student student){
		return studentDataAccess.createStudent(student);
	}
	
	public boolean deleteStudent(Student student){
		return studentDataAccess.deleteStudent(student);
	}
	
	public boolean updateStudent(Student student){
		return studentDataAccess.updateStudent(student);
	}
	
	public boolean authenticate(User user) {		
		return studentDataAccess.authenticate(user);
	}
	
	public List<Student> getStudentsEnrolledInCourse(Course course){
		return studentDataAccess.getStudentsEnrolledInCourse(course);
	}
	
	public List<Student> getStudentByName(String name){
		return studentDataAccess.getStudentByName(name);
	}
	
	public Student getStudentByMatricNo(String matricNo){
		return studentDataAccess.getStudentByMatricNo(matricNo);
	}
	
	public Student getStudentByUserName(String userName){
		return studentDataAccess.getStudentByUserName(userName);
	}
	
	public List<Student> getStudents(){
		return studentDataAccess.getStudents();
	}
	
	public boolean studentChooseAnswer(Student student, Answer answer){
		return studentDataAccess.studentChooseAnswer(student, answer);
	}
	
	public boolean studentChooseAnswers(Student student, List<Answer> answer){
		return studentDataAccess.studentChooseAnswers(student, answer);
	}
	
	public boolean studentChooseAnswers(Student student, List<Question> questions, Hashtable<Question, Answer> answers) {
		return studentDataAccess.studentChooseAnswers(student, questions, answers);
	}
	
	public boolean studentEnrollCourse(Student student, Course course){
		return studentDataAccess.studentEnrollCourse(student, course);
	}
	
	public boolean updateStudentEnrollCourse(Student student, Course oldCourse, Course newCourse){
		return studentDataAccess.updateStudentEnrollCourse(student, oldCourse, newCourse);
	}
	
	public boolean deleteStudentEnrollCourse(Student student, Course course){
		return studentDataAccess.deleteStudentEnrollCourse(student, course);
	}
	
	public boolean studentBookExam(Student student,Exam exam,Date datetime){
		return studentDataAccess.studentBookExam(student, exam, datetime);
	}
	
	public boolean studentCancelBooking(Student student,Exam exam){
		return studentDataAccess.studentCancelBooking(student, exam);
	}
	
	public boolean updateStudentBookExam(Student student,Exam exam,Date datetime){
		return studentDataAccess.updateStudentBookExam(student, exam, datetime);
	}
	
	public boolean deleteStudentBookExam(Student student,Exam exam,Date datetime){
		return studentDataAccess.deleteStudentBookExam(student, exam, datetime);
	}
	
	public boolean studentAttendExam(Student student,Exam exam,Date datetime){
		return studentDataAccess.studentAttendExam(student, exam, datetime);
	}
	
	public List<Student> getStudentsByBookedExamDate(Date bookedDateTime1, Date bookedDateTime2) {
		return studentDataAccess.getStudentsByBookedExamDate(bookedDateTime1, bookedDateTime2);
	}
	
	public int calculateStudentResultForExam(Student student, Exam exam) {
		int result = 0;
		
		AnswerManager answerManager = new AnswerManager();
		List<Answer> chosenAnswers = answerManager.getAnswersChosenByStudentForExam(student, exam);
		List<Answer> correctAnswers = answerManager.getCorrectAnswersByExam(exam);
		
		for(Answer answer : chosenAnswers) {
			if(correctAnswers.contains(answer)) {
				result++;
			}
		}
		return result;
	}
	
}
