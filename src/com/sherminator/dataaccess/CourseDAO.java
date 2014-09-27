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

import com.sherminator.model.Course;
import com.sherminator.model.Professor;
import com.sherminator.model.Student;
import com.sherminator.util.CWS_DBConnection;
import com.sherminator.util.System_DBConnection;

public class CourseDAO implements CourseDataAccess{

	@Override
	public boolean createCourse(Course course) {
		

		boolean isCreated = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("INSERT INTO [Course] ([courseCode], [courseName]) VALUES (?, ?);");
			statement.setString(1, course.getCourseCode());
			statement.setString(2, course.getCourseName());
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
	public boolean updateCourse(Course course) {
		

		boolean isUpdated = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("UPDATE [Course] "
					+ "SET [courseName] = ? "
					+ "WHERE [courseCode] = ?");
			statement.setString(1, course.getCourseName());
			statement.setString(2, course.getCourseCode());
			statement.execute();

			connection.commit();
			connection.close();

			isUpdated = true;
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

		return isUpdated;
	}
	
	

	@Override
	public boolean deleteCourse(Course course) {
		

		boolean isDeleted = false;

		Connection connection = CWS_DBConnection.getConnection();
		Connection connectionSys = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("DELETE FROM [Exam] WHERE [courseCode] = ?");
			statement.setString(1, course.getCourseCode());
			statement.execute();
			
			statement = connection.prepareStatement("DELETE FROM [Course] WHERE [courseCode] = ?");
			statement.setString(1, course.getCourseCode());
			statement.execute();

			connection.commit();
			connection.close();

			isDeleted = true;
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

		return isDeleted;
	}
	
	@Override
	public Course getCourseByCourseCode(String courseCode) {
		

		String courseName = null;
		Course course = null;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [courseName] FROM [Course] WHERE [courseCode] = ?");
			statement.setString(1, courseCode);

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				courseName = rs.getString("courseName");
			}

			if(courseName != null) {
				course = new Course(courseCode, courseName);
			}

			connection.commit();
			connection.close();

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

		return course;
	}

	@Override
	public List<Course> getCoursesByStudent(Student student) {
	
		Course course;
		String username = student.getUserName();
		String courseCode = null;
		String courseName = null;
		List<Course> courses = new ArrayList<Course>();
		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [Course].[courseCode], [Course].[courseName] "
					+ "FROM [Course] INNER JOIN [StudentCourse] "
					+ "ON [Course].[courseCode] = [StudentCourse].[courseCode] "
					+ "WHERE [StudentCourse].[username] = ?");
			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				courseCode = rs.getString("courseCode");
				courseName = rs.getString("courseName");

				course = new Course(courseCode, courseName);
				courses.add(course);
			}

			connection.commit();
			connection.close();

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

		return courses;
	}

	@Override
	public List<Course> getCoursesByProfessor(Professor professor) {
		
		Course course;
		String username = professor.getUserName();
		String courseCode = null;
		String courseName = null;
		List<Course> courses = new ArrayList<Course>();
		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [Course].[courseCode], [Course].[courseName] "
					+ "FROM [Course] INNER JOIN [ProfessorCourse] "
					+ "ON [Course].[courseCode] = [ProfessorCourse].[courseCode] "
					+ "WHERE [ProfessorCourse].[username] = ?");
			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				courseCode = rs.getString("courseCode");
				courseName = rs.getString("courseName");

				course = new Course(courseCode, courseName);
				courses.add(course);
			}

			connection.commit();
			connection.close();

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

		return courses;
	}

	@Override
	public List<Course> getCourses() {
		

		Course course = null;
		String courseCode;
		String courseName;
		List<Course> courses = new ArrayList<Course>();
		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [courseCode], [courseName] FROM [Course]");

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				courseCode = rs.getString("courseCode");
				courseName = rs.getString("courseName");

				course = new Course(courseCode, courseName);
				courses.add(course);
			}

			connection.commit();
			connection.close();

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

		return courses;
	}

}
