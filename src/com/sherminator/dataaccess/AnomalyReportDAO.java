/*
 * 
 * @author	Ang Weeliang
 * @author	Nasruddin
 * @author	Salihan
 * @version	2.4
 * 
 */

package com.sherminator.dataaccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sherminator.model.AnomalyReport;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;
import com.sherminator.util.CWS_DBConnection;
import com.sherminator.util.System_DBConnection;

public class AnomalyReportDAO implements AnomalyReportDataAccess{

	@Override
	public boolean createAnomalyReport(AnomalyReport anomalyReport) {
		boolean isCreated = false;
		Connection connection = System_DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("INSERT INTO [AnomalyReport] ([notes], [startDateTime], [username],[matricNo],[examID])"
					+ "VALUES ( ?, ? , ? , ? , ? );");
			statement.setString(1, anomalyReport.getNotes());
			statement.setDate(2, anomalyReport.getStartDateTime());
			statement.setString(3, anomalyReport.getUsername());

			statement.setString(4, anomalyReport.getMatricNo());
			statement.setInt(5, anomalyReport.getExamID());
			
			statement.execute();
			
			connection.commit();
			connection.close();
			isCreated = true;
			
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			
		}
		
		
		return isCreated;
	}

	@Override
	public boolean deleteAnomalyReport(AnomalyReport anomalyReport) {
		boolean isDeleted = false;
		Connection connection = System_DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("DELETE FROM [AnomalyReport] WHERE [reportID] = ?");
			statement.setInt(1, anomalyReport.getReportID());
			statement.execute();
			
			connection.commit();
			connection.close();
			isDeleted = true;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return isDeleted;
	}
	
	public boolean setAnomalyReportID(AnomalyReport anomalyReport){
		Connection connection = System_DBConnection.getConnection();
		boolean set = false;
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [reportID] "
					+ "FROM [AnomalyReport] WHERE [notes] = ? AND [startDateTime] = ? AND [matricNo] = ? AND [examID] = ? AND [username] = ? ");
			statement.setString(1, 	anomalyReport.getNotes());
			statement.setDate(2, 	anomalyReport.getStartDateTime());
			statement.setString(3,	anomalyReport.getMatricNo());
			statement.setInt(4, 	anomalyReport.getExamID());
			statement.setString(5,	anomalyReport.getUsername());
			

			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				anomalyReport.setReportID(rs.getInt("reportID"));
				set = true;
				break;
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return set;
	}

	@Override
	public boolean updateAnomalyReport(AnomalyReport anomalyReport) {
		boolean isCreated = false;
		Connection connection = System_DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("UPDATE [AnomalyReport] SET [startDateTime]=?, [notes] =?, [username] =?, [matricNo]=?, [examID]=? WHERE [reportID] = ?");
			statement.setDate(1, anomalyReport.getStartDateTime());
			statement.setString(2, anomalyReport.getNotes());
			statement.setString(3, anomalyReport.getUsername());
			statement.setString(4, anomalyReport.getMatricNo());
			statement.setInt(5, anomalyReport.getExamID());
			statement.setInt(6, anomalyReport.getReportID());
			statement.execute();
			
			connection.commit();
			connection.close();
			isCreated = true;
			
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}

		}		
		return isCreated;
	}

	@Override
	public AnomalyReport getAnomalyReportByID(int ID) {
		Connection connection = System_DBConnection.getConnection();
		AnomalyReport anomalyReport = null;

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT * "
					+ "FROM [AnomalyReport]"
					+ " WHERE [reportID] = ?");
			statement.setInt(1, ID);

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				anomalyReport = new AnomalyReport(rs.getInt("reportID"), rs.getDate("startDateTime"), rs.getString("notes"), rs.getString("username"),rs.getString("matricNo"), rs.getInt("examID"));
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return anomalyReport;
	}
	
	@Override
	public AnomalyReport getAnomalyReportByStudentAndExam(Student student, Exam exam) {
		Connection connection = System_DBConnection.getConnection();
		AnomalyReport anomalyReport = null;

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT * "
					+ "FROM [AnomalyReport]"
					+ " WHERE [matricNo]=? AND [examID]=?");
			statement.setString(1, student.getMatricNo());
			statement.setInt(2, exam.getExamID());

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				anomalyReport = new AnomalyReport(rs.getInt("reportID"), rs.getDate("startDateTime"), rs.getString("notes"), rs.getString("username"),rs.getString("matricNo"), rs.getInt("examID"));
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return anomalyReport;
	}

	@Override
	public List<AnomalyReport> getAnomalyReportByDate(Date date) {
		Connection connection = System_DBConnection.getConnection();
		List<AnomalyReport> anomalies = new ArrayList<AnomalyReport>();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT * "
					+ "FROM [AnomalyReport]"
					+ " WHERE [startDateTime] = ?");
			statement.setDate(1, date);

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				AnomalyReport anomalyReport = new AnomalyReport(rs.getInt("reportID"), rs.getDate("startDateTime"), rs.getString("notes"), rs.getString("username"),rs.getString("matricNo"), rs.getInt("examID"));
				anomalies.add(anomalyReport);
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return anomalies;
	}

	@Override
	public List<AnomalyReport> getAnomalyReportByProctor(Proctor proctor) {
		Connection connection = System_DBConnection.getConnection();
		List<AnomalyReport> anomalies = new ArrayList<AnomalyReport>();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT * "
					+ "FROM [AnomalyReport]"
					+ " WHERE [username] = ?");
			statement.setString(1, proctor.getUserName());

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				AnomalyReport anomalyReport = new AnomalyReport(rs.getInt("reportID"), rs.getDate("startDateTime"), rs.getString("notes"), rs.getString("username"),rs.getString("matricNo"), rs.getInt("examID"));
				anomalies.add(anomalyReport);
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return anomalies;
	}
	
	

	@Override
	public List<AnomalyReport> getAnomalyReports() {
		Connection connection = System_DBConnection.getConnection();
		List<AnomalyReport> anomalies = new ArrayList<AnomalyReport>();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT * "
					+ "FROM [AnomalyReport]");

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				AnomalyReport anomalyReport = new AnomalyReport(rs.getInt("reportID"), rs.getDate("startDateTime"), rs.getString("notes"), rs.getString("username"),rs.getString("matricNo"), rs.getInt("examID"));
				anomalies.add(anomalyReport);
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return anomalies;
	}
	
}
