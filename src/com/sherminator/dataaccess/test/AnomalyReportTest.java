/*
 * @author	Soe Lynn
 * @version	1.0
 * 
 */

package com.sherminator.dataaccess.test;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import com.sherminator.dataaccess.AnomalyReportDAO;
import com.sherminator.dataaccess.CourseDAO;
import com.sherminator.dataaccess.ExamDAO;
import com.sherminator.dataaccess.ProctorDAO;
import com.sherminator.dataaccess.StudentDAO;
import com.sherminator.model.AnomalyReport;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;

import junit.framework.TestCase;

public class AnomalyReportTest extends TestCase {
	public void testCreateAnomalyReport(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 3, 24, 7, 29, 0);
		java.util.Date tempDate = calendar.getTime();
		Date startDateTime = new java.sql.Date(tempDate.getTime());
		calendar.set(2014, 4, 24, 7, 29, 0);
		tempDate = calendar.getTime();
		Date endDateTime = new java.sql.Date(tempDate.getTime());
		
		
		AnomalyReportDAO test = new AnomalyReportDAO();
		ProctorDAO proct = new ProctorDAO();
		StudentDAO studentDAO = new StudentDAO();
		ExamDAO examDAO = new ExamDAO();
		CourseDAO courseDAO = new CourseDAO();
		
		Boolean createAnomalyReport = false;
		Boolean getReportID = false;
		Boolean updateAnomalyReport = false;
		Boolean getAnomalyReportByDate = false;
		Boolean getAnomalyReportByProctor = false;
		
		Boolean getAnomalyReports = false;
		Boolean deleteAnomalyReport = false;
		
		//dummy test variables
		Proctor proctor = new Proctor("Limbo", "root", "Limbo Jack");
		proct.createProctor(proctor);
		
		Course course = new Course("test", "testCourse");
		courseDAO.createCourse(course);
		
		Exam exam = new Exam("testExam", course.getCourseCode(), startDateTime, endDateTime, 100, 45, 0);
		examDAO.createExam(exam);
		examDAO.setExamID(exam);
		
		Student student = new Student("please ignore this person","srootj", "U91919191L", "please ignore this person", "www.youtube.com");
		studentDAO.createStudent(student);
		studentDAO.studentEnrollCourse(student, course);
		studentDAO.studentBookExam(student, exam, startDateTime);
		studentDAO.studentAttendExam(student, exam, startDateTime);

		
		AnomalyReport anom1 = new AnomalyReport(startDateTime, "please ignore this report", proctor.getUserName(), student.getMatricNo(), exam.getExamID());
		createAnomalyReport = test.createAnomalyReport(anom1);
		
		//setAnomalyReportID
		test.setAnomalyReportID(anom1);
		getReportID = (anom1.getReportID()!=-1);
		
		//updateAnomalyReport
		anom1.setUsername("Limbo");
		updateAnomalyReport = test.updateAnomalyReport(anom1);
		
		
		//getAnomalyReportByDate
		List<AnomalyReport> anomList = test.getAnomalyReportByDate(startDateTime);
		for(AnomalyReport anom: anomList){
			if(anom.getReportID()==anom1.getReportID()){
				getAnomalyReportByDate=true;
			}
		}
		
		
		//getAnomalyReportByProctor
		getAnomalyReportByProctor = true;
		anomList = test.getAnomalyReportByProctor(proctor);
		for(AnomalyReport anom: anomList){
			if(!anom.getUsername().equals(proctor.getUserName())){
				getAnomalyReportByProctor=false;
				break;
			}
		}
		
		//getanomalyReports

		anomList = test.getAnomalyReports();
		for(AnomalyReport anom: anomList){
			if(anom.getReportID()==anom1.getReportID()){
				getAnomalyReports=true;
			}
		}
		
		//delete anomaly report
		deleteAnomalyReport = test.deleteAnomalyReport(anom1);
		studentDAO.deleteStudentAttendExam(student, exam, startDateTime);
		studentDAO.deleteStudentBookExam(student, exam, startDateTime);
		studentDAO.deleteStudentEnrollCourse(student, course);
		studentDAO.deleteStudent(student);
		examDAO.deleteExam(exam);
		courseDAO.deleteCourse(course);
		proct.deleteProctor(proctor);
		
		
		assertEquals("true", createAnomalyReport.toString());
		System.out.println("anomalyReportDAO.createAnomalyReport passed");
		assertEquals("true", getReportID.toString());
		System.out.println("anomalyReportDAO.getReportID passed");
		assertEquals("true", updateAnomalyReport.toString());
		System.out.println("anomalyReportDAO.updateAnomalyReport passed");
		assertEquals("true", getAnomalyReportByDate.toString());
		System.out.println("anomalyReportDAO.getAnomalyReportByDate passed");
		assertEquals("true", getAnomalyReportByProctor.toString());
		System.out.println("anomalyReportDAO.getAnomalyReportByProctor passed");
		assertEquals("true", getAnomalyReports.toString());
		System.out.println("anomalyReportDAO.getAnomalyReports passed");
		assertEquals("true", deleteAnomalyReport.toString());
		System.out.println("anomalyReportDAO.deleteAnomalyReport passed");
		System.out.println("all working in anomalyReportDAO");
		
	}
}
