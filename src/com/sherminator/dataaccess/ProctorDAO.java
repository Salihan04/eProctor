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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sherminator.dataaccessfactory.ProctorDataAccessFactory;
import com.sherminator.dataaccessfactory.StudentDataAccessFactory;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Professor;
import com.sherminator.model.Student;
import com.sherminator.model.User;
import com.sherminator.util.CWS_DBConnection;
import com.sherminator.util.System_DBConnection;

public class ProctorDAO implements ProctorDataAccess {

	public boolean createProctor(Proctor proctor) {//tested
		boolean isCreated = false;

		Connection connection = System_DBConnection.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO [Proctor] ([username], [name]) VALUES (?, ?);");
			statement.setString(1, proctor.getUserName());
			statement.setString(2, proctor.getName());
			statement.execute();
			
			statement = connection.prepareStatement("INSERT INTO [User] ([username], [password]) VALUES (?, ?);");
			statement.setString(1, proctor.getUserName());
			statement.setString(2, proctor.getPassword());
			statement.execute();
			
			
			connection.commit();
			connection.close();

			isCreated = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isCreated;
	}

	public boolean authenticate(User user) {
		boolean isAuthenticated = false;

		Connection connection = System_DBConnection.getConnection();

		try {
			String sql = "SELECT [User].[password] FROM [dbo].[User] "
					+ "INNER JOIN [dbo].[Proctor] "
					+ "ON [User].[username] = [Proctor].[username] "
					+ "WHERE [Proctor].[username]=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUserName());

			ResultSet resultset = statement.executeQuery();

			// Is there any record with the username?
			if(resultset.next()) {
				// YES!!! found the user
				String password = resultset.getString("password");

				// Is the password match?
				if(user.getPassword().equals(password)) {
					// YES!!!
					isAuthenticated = true;
				}
			}

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isAuthenticated;
	}
	
	@Override
	public boolean updateProctor(Proctor proctor) {//tested
		boolean isUpdated = false;

		Connection connection = System_DBConnection.getConnection();
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("UPDATE [User] SET [password]=? WHERE [username]=?");
			statement.setString(1, proctor.getPassword());
			statement.setString(2, proctor.getUserName());
			statement.execute();
			
			statement = connection.prepareStatement("UPDATE [Proctor] SET [name]=? WHERE [username]=?");
			statement.setString(1, proctor.getName());
			statement.setString(2, proctor.getUserName());
			statement.execute();

			connection.commit();
			connection.close();

			isUpdated = true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			}
		}
		return isUpdated;
	}

	@Override
	public boolean deleteProctor(Proctor proctor) {
		boolean isUpdated = false;

		Connection connection = System_DBConnection.getConnection();
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("DELETE FROM [User] WHERE [userName]=?");
			statement.setString(1, proctor.getUserName());
			statement.execute();

			statement = connection.prepareStatement("DELETE FROM [Proctor] WHERE [username]=?");
			statement.setString(1, proctor.getUserName());
			statement.execute();

			connection.commit();
			connection.close();

			isUpdated = true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return isUpdated;
	}
	
	@Override
	public boolean verifiedStudentExam(Proctor proctor, Student student, Exam exam) {
		boolean isCreated = false;

		Connection connection = System_DBConnection.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO [ProctorStudentExamVerify] ([proctorID], [examID], [matricNo]) VALUES (?, ?, ?);");
			statement.setInt(1, proctor.getProctorID());
			statement.setInt(2, exam.getExamID());
			statement.setString(3, student.getMatricNo());
			statement.execute();
	
			connection.close();

			isCreated = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isCreated;
	}

	@Override
	public Proctor getProctorByUserName(String userName) {//tested
		Connection connection = System_DBConnection.getConnection();
		Proctor proctor = null;
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * "
					+ "FROM [User]"
					+ " INNER JOIN [Proctor]"
					+ " ON [User].[username] = [Proctor].[username]"
					+ " WHERE [Proctor].[username] = ?");
			statement.setString(1, userName);
			
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				 proctor = new Proctor(rs.getString("username"), rs.getString("password"), rs.getString("name"), rs.getInt("proctorID"));
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return proctor;
	}

	@Override
	public List<Proctor> getProctorByName(String name) {//tested
		Connection connection = System_DBConnection.getConnection();
		List<Proctor> proctors = new ArrayList<Proctor>();
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * "
					+ "FROM [User]"
					+ " INNER JOIN [Proctor]"
					+ " ON [User].[username] = [Proctor].[username]"
					+ " WHERE [Proctor].[name] = ?");
			statement.setString(1, name);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				 Proctor proctor = new Proctor(rs.getString("username"), rs.getString("password"), rs.getString("name"));
				 proctors.add(proctor);
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return proctors;
	}

	public List<Proctor> getProctors() {//tested
		List<Proctor> proctors=new ArrayList<Proctor>();
		Connection connection = System_DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * "
					+ "FROM [User]"
					+ " INNER JOIN [Proctor]"
					+ " ON [User].[username] = [Proctor].[username]");
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Proctor proctor = new Proctor(rs.getString("username"), rs.getString("password"), rs.getString("name"));
				proctors.add(proctor);
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return proctors;
	}
	
}
