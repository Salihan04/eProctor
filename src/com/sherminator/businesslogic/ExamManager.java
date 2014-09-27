/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.businesslogic;

import java.sql.Date;
import java.util.List;

import com.sherminator.dataaccess.ExamDataAccess;
import com.sherminator.dataaccessfactory.ExamDataAccessFactory;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Student;

public class ExamManager {
	private ExamDataAccessFactory factory;
	private ExamDataAccess examDataAccess;
	
	public ExamManager() {
		factory = new ExamDataAccessFactory();
		examDataAccess = factory.getDataAccess();
	}
	
	public boolean createExam(Exam exam){
		boolean iscreated = examDataAccess.createExam(exam);
		return iscreated;
	}
	
	public boolean updateExam(Exam exam){
		boolean isupdated = examDataAccess.updateExam(exam);
		return isupdated;
	}
	
	public boolean deleteExam(Exam exam){
		boolean isdeleted = examDataAccess.deleteExam(exam);
		return isdeleted;
	}
	
	public Date getExamBookingDateTimeByStudent(Exam exam, Student student) {
		return examDataAccess.getExamBookingDateTimeByStudent(exam, student);
	}
	
	public Exam getExamByExamID(int examID){
		return examDataAccess.getExamByExamID(examID);
	}
	
	public List<Exam> getExams(){
		return examDataAccess.getExams();
	}
	
	public List<Exam> getExamsByCourse(Course course){
		return examDataAccess.getExamsByCourse(course);
	}
	
	public List<Exam> getExamsByStudent(Student student){
		return examDataAccess.getExamsByStudent(student);
	}
	
	public List<Exam> getExamsBookedByStudent(Student student){
		return examDataAccess.getExamsBookedByStudent(student);
	}
	
	public List<Exam> getExamsBookedByDate(Date bookedDateTime1, Date bookedDateTime2){
		return examDataAccess.getExamsBookedByDate(bookedDateTime1, bookedDateTime2);
	}
	
	public List<Exam> getExamsAttendedByStudent(Student student){
		return examDataAccess.getExamsAttendedByStudent(student);
	}
	
	public boolean setStudentExamCWS(){
		 return examDataAccess.setStudentExamCWS();
	}
	
	public boolean clearStudentExamCWS(){
		return examDataAccess.clearStudentExamCWS();
	}
	
}
