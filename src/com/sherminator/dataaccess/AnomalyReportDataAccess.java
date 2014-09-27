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

import com.sherminator.model.AnomalyReport;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;

public interface AnomalyReportDataAccess extends DataAccess {
	
	public boolean createAnomalyReport(AnomalyReport anomalyReport);
	public boolean deleteAnomalyReport(AnomalyReport anomalyReport);
	public boolean updateAnomalyReport(AnomalyReport anomalyReport);

	public AnomalyReport getAnomalyReportByID(int ID);
	public AnomalyReport getAnomalyReportByStudentAndExam(Student student, Exam exam);
	public boolean setAnomalyReportID(AnomalyReport anomalyReport);
	public List<AnomalyReport> getAnomalyReportByDate(Date date);
	public List<AnomalyReport> getAnomalyReportByProctor(Proctor proctor);
	public List<AnomalyReport> getAnomalyReports();
	
}
