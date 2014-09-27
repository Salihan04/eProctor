/*
 * 
 * @author	Soe Lynn
 * @author	Sherman
 * @version	1.1
 * 
 */

package com.sherminator.dataaccess;

import java.sql.Date;
import java.util.List;

import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;
import com.sherminator.model.Student;

public interface ExamDataAccess extends DataAccess{

	public boolean createExam(Exam exam);
	public boolean updateExam(Exam exam);
	public boolean deleteExam(Exam exam);
	public Exam getExamByExamID(int examID);
	public Date getExamBookingDateTimeByStudent(Exam exam, Student student);
	public boolean setExamID(Exam exam);
	public List<Exam> getExams();
	public List<Exam> getExamsByCourse(Course course);
	public List<Exam> getExamsByStudent(Student student);
	public List<Exam> getExamsBookedByStudent(Student student);
	public List<Exam> getExamsBookedByDate(Date bookedDateTime1, Date bookedDateTime2);
	public List<Exam> getExamsAttendedByStudent(Student student);
	public boolean setStudentExamCWS();
	public boolean clearStudentExamCWS();
}
