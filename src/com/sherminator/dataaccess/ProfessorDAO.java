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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sherminator.model.Professor;
import com.sherminator.model.User;
import com.sherminator.dataaccess.CourseDAO;
import com.sherminator.dataaccessfactory.CourseDataAccessFactory;
import com.sherminator.dataaccessfactory.StudentDataAccessFactory;
import com.sherminator.util.CWS_DBConnection;
import com.sherminator.model.Course;

public class ProfessorDAO implements ProfessorDataAccess {

	public boolean createProfessor(Professor professor) {//tested
		boolean isCreated = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("INSERT INTO [User] ([username], [password]) VALUES (?, ?);");
			statement.setString(1, professor.getUserName());
			statement.setString(2, professor.getPassword());
			statement.execute();

			statement = connection.prepareStatement("INSERT INTO [Professor] ([username], [staffID]) VALUES (?, ?);");
			statement.setString(1, professor.getUserName());
			statement.setString(2, professor.getStaffID());
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

		Connection connection = CWS_DBConnection.getConnection();

		try {
			String sql = "SELECT [User].[password] FROM [dbo].[User] "
					+ "INNER JOIN [dbo].[Professor] "
					+ "ON [User].[username] = [Professor].[username] "
					+ "WHERE [Professor].[username]=?";
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

	public boolean updateProfessor(Professor professor) {//tested
		boolean isUpdated = false;

		Connection connection = CWS_DBConnection.getConnection();
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("UPDATE [User] SET [password]=? WHERE [username]=?");
			statement.setString(1, professor.getPassword());
			statement.setString(2, professor.getUserName());
			statement.execute();
			
			statement = connection.prepareStatement("UPDATE [Professor] SET [staffID]=? WHERE [username]=?");
			statement.setString(1, professor.getStaffID());
			statement.setString(2, professor.getUserName());
			statement.execute();

			connection.commit();
			connection.close();

			isUpdated = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	public boolean deleteProfessor(Professor professor) {//tested
		boolean isUpdated = false;

		Connection connection = CWS_DBConnection.getConnection();
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("DELETE FROM [Professor] WHERE [staffID]=?");
			statement.setString(1, professor.getStaffID());
			statement.execute();
			
			statement = connection.prepareStatement("DELETE FROM [User] WHERE [userName]=?");
			statement.setString(1, professor.getUserName());
			statement.execute();


			connection.commit();
			connection.close();

			isUpdated = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	@Override
	public boolean profTeachCourse(Professor professor, Course course) {
		
		
		boolean isCreated = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("INSERT INTO [ProfessorCourse] ([username], [courseCode]) VALUES (? ,?)");
			statement.setString(1, professor.getUserName());
			statement.setString(2, course.getCourseCode());			
			statement.execute();

			connection.commit();
			connection.close();

			isCreated = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			}
		}

		return isCreated;
	}

	@Override
	public boolean updateProfTeachCourse(Professor professor, Course oldCourse, Course newCourse) {
		
		
		boolean isUpdated = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("Update [ProfessorCourse] SET [courseCode] = ? "
					+ "WHERE [username] = ? AND [courseCode] = ?");
			statement.setString(1, newCourse.getCourseCode());
			statement.setString(2, professor.getUserName());
			statement.setString(3, oldCourse.getCourseCode());			
			statement.execute();

			connection.commit();
			connection.close();

			isUpdated = true;
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
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
	public boolean deleteProfTeachCourse(Professor professor, Course course) {
		
		
		boolean isDeleted = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("DELETE FROM [ProfessorCourse] WHERE [username] = ? AND [courseCode] = ?");
			statement.setString(1, professor.getUserName());
			statement.setString(2, course.getCourseCode());			
			statement.execute();

			connection.commit();
			connection.close();

			isDeleted = true;
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			}
		}

		return isDeleted;
	}

	public Professor getProfessorByUserName(String userName) {//tested
		Connection connection = CWS_DBConnection.getConnection();
		Professor professor=null;
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * "
					+ "FROM [User]"
					+ " INNER JOIN [Professor]"
					+ " ON [User].[username] = [Professor].[username]"
					+ " WHERE [Professor].[username] = ?");
			statement.setString(1, userName);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				 professor =new Professor(rs.getString("userName"), rs.getString("password"), rs.getString("staffID"));
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professor;
	}

	public Professor getProfessorByStaffID(String staffID) {//tested
		Connection connection = CWS_DBConnection.getConnection();
		Professor professor=null;
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM  [Professor] INNER JOIN [User] ON [Professor].[username]=[User].[username] WHERE [Professor].[staffID] = ?");
			statement.setString(1, staffID);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				professor =new Professor(rs.getString("username"), rs.getString("password"), rs.getString("staffID"));
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professor;
	}

	public List<Course> getCoursesByProfessorStaffID(String staffID){//tested
		CourseDataAccessFactory factory = new CourseDataAccessFactory();
		CourseDataAccess courseDataAccess = factory.getDataAccess();
		
		Connection connection = CWS_DBConnection.getConnection();
		Professor professor= this.getProfessorByStaffID(staffID);
		if(professor==null){
			return null;
		}
		List<Course> courses = new ArrayList<Course>();
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM  [ProfessorCourse] WHERE [userName] = ?");
			statement.setString(1, professor.getUserName());
			statement.execute();
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				 Course course = courseDataAccess.getCourseByCourseCode(rs.getString("courseCode"));
				 courses.add(course);
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courses;
	}
	
	
	public List<Course> getCoursesByProfessorUserName(String userName){//tested
		CourseDataAccessFactory factory = new CourseDataAccessFactory();
		CourseDataAccess courseDataAccess = factory.getDataAccess();
		
		Connection connection = CWS_DBConnection.getConnection();
		List<Course> courses = new ArrayList<Course>();
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM  [ProfessorCourse] WHERE [username] = ?");
			statement.setString(1, userName);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Course course = courseDataAccess.getCourseByCourseCode(rs.getString("courseCode"));
				courses.add(course);
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
		
	}
	
	public List<Professor> getAllProfessors(){//tested
		Connection connection = CWS_DBConnection.getConnection();
		List<Professor> professors = new ArrayList<Professor>();
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			statement = connection.prepareStatement("SELECT * "
					+ "FROM  [Professor] "
					+ "INNER JOIN [User] "
					+ "ON [Professor].[username] = [User].[username]");
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				 Professor professor =new Professor(rs.getString("username"), rs.getString("password"), rs.getString("staffID"));
				 professors.add(professor);
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professors;
	}

}