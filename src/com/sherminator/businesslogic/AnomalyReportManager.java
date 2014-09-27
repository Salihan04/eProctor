/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.businesslogic;

import java.sql.Date;
import java.util.List;

import com.sherminator.dataaccess.AnomalyReportDataAccess;
import com.sherminator.dataaccessfactory.AnomalyReportDataAccessFactory;
import com.sherminator.model.AnomalyReport;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;

public class AnomalyReportManager {
	
	private AnomalyReportDataAccessFactory factory;
	private AnomalyReportDataAccess anomalyReportDataAccess;
	
	public AnomalyReportManager() {
		factory = new AnomalyReportDataAccessFactory();
		anomalyReportDataAccess = factory.getDataAccess();
	}
	
	//method to create and insert AnomalyReport info into database
	public boolean createAnomalyReport(AnomalyReport anomalyReport){
		return anomalyReportDataAccess.createAnomalyReport(anomalyReport);
	}
	
	//method to update AnomalyReport info in database
	public boolean updateAnomalyReport(AnomalyReport anomalyReport){
		return anomalyReportDataAccess.updateAnomalyReport(anomalyReport);
	}
	
	//method to delete AnomalyReport from database
	public boolean deleteAnomalyReport(AnomalyReport anomalyReport){
		return anomalyReportDataAccess.deleteAnomalyReport(anomalyReport);
	}
	
	//method to get a AnomalyReport from a from the database based on a specified ID
	public AnomalyReport getAnomalyReportByID(int ID){
		return anomalyReportDataAccess.getAnomalyReportByID(ID);
	}
	
	//method to get a AnomalyReport from a from the database based on a student matric no and exam id
	public AnomalyReport getAnomalyReportByStudentAndExam(Student student, Exam exam) {
		return anomalyReportDataAccess.getAnomalyReportByStudentAndExam(student, exam);
	}
	
	//method to get all the AnomalyReports from the database based on a specified date
	public List<AnomalyReport> getAnomalyReportByDate(Date date) {
		return anomalyReportDataAccess.getAnomalyReportByDate(date);
	}
	
	//method to get the AnomalyReports created by a particular Proctor from the database
	public List<AnomalyReport> getAnomalyReportByProctor(Proctor proctor) {
		return anomalyReportDataAccess.getAnomalyReportByProctor(proctor);
	}
	
	//method to get all the existing AnomalyReports found in the database
	public List<AnomalyReport> getAnomalyReports() {
		return anomalyReportDataAccess.getAnomalyReports();
	}
}
