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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;
import com.sherminator.model.Student;
import com.sherminator.util.CWS_DBConnection;
import com.sherminator.util.System_DBConnection;

public class ExamDAO implements ExamDataAccess{

	@Override
	public boolean createExam(Exam exam) {

		boolean isCreated = false;

		Connection connection = CWS_DBConnection.getConnection();
		Connection connectionSystem = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			connectionSystem.setAutoCommit(false);

			PreparedStatement statement;

			//Insert Exam record in NasCWS
			statement = connection.prepareStatement("INSERT INTO [Exam] "
					+ "([examTitle], [courseCode], [start_date_time], [end_date_time], [maxMarks], [duration], [no_of_questions]) VALUES (?, ?, ?, ?, ?, ?, ?);");
			statement.setString(1, exam.getExamTitle());
			statement.setString(2, exam.getCourseCode());
			statement.setTimestamp(3, new Timestamp(exam.getStartDateTime().getTime()));
			statement.setTimestamp(4, new Timestamp(exam.getEndDateTime().getTime()));
			statement.setInt(5, exam.getMaxMarks());
			statement.setFloat(6, exam.getDuration());
			statement.setInt(7, exam.getNo_of_questions());
			statement.execute();

			//Insert Exam record into NasSystem
			statement = connectionSystem.prepareStatement("INSERT INTO [Exam] ([duration]) VALUES (?);");
			statement.setFloat(1, exam.getDuration());			
			statement.execute();

			connection.commit();
			connection.close();

			connectionSystem.commit();
			connectionSystem.close();

			isCreated = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
				connectionSystem.rollback();
			} catch (SQLException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			}
		}

		return isCreated;
	}

	public boolean setExamID(Exam exam){
		Connection connection = CWS_DBConnection.getConnection();
		boolean set = false;
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [examID] "
					+ "FROM [Exam] WHERE [courseCode] = ? AND [maxMarks] = ? AND [duration] = ? AND [examTitle] = ? AND [no_of_questions] = ?");
			statement.setString(1, exam.getCourseCode());
			statement.setInt(2, exam.getMaxMarks());
			statement.setFloat(3, exam.getDuration());
			statement.setString(4,  exam.getExamTitle());
			statement.setInt(5, exam.getNo_of_questions());

			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				exam.setExamID(rs.getInt("examID"));
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
	public boolean updateExam(Exam exam) {
		

		boolean isUpdated = false;

		Connection connection = CWS_DBConnection.getConnection();
		Connection connectionSystem = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			connectionSystem.setAutoCommit(false);

			PreparedStatement statement;

			//Update Exam table in NasCWS
			statement = connection.prepareStatement("UPDATE [Exam] "
					+ "SET [examTitle] = ? , [courseCode] = ? , [start_date_time] = ?, [end_date_time] = ?, [maxMarks] = ?, [duration] = ?, [no_of_questions] = ? "
					+ "WHERE [examID] = ?");
			statement.setString(1, exam.getExamTitle());
			statement.setString(2, exam.getCourseCode());
			statement.setTimestamp(3, new Timestamp(exam.getStartDateTime().getTime()));
			statement.setTimestamp(4, new Timestamp(exam.getEndDateTime().getTime()));
			statement.setInt(5, exam.getMaxMarks());
			statement.setFloat(6, exam.getDuration());
			statement.setInt(7, exam.getExamID());
			statement.setInt(8, exam.getNo_of_questions());
			statement.execute();

			//Update Exam table in NasSystem
			statement = connectionSystem.prepareStatement("UPDATE [Exam] "
					+ "SET [duration] = ? WHERE [examID] = ?");
			statement.setFloat(1, exam.getDuration());
			statement.setFloat(2, exam.getExamID());
			statement.execute();

			connection.commit();
			connection.close();

			connectionSystem.commit();
			connectionSystem.close();

			isUpdated = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
				connectionSystem.rollback();
			} catch (SQLException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			}
		}

		return isUpdated;
	}

	@Override
	public boolean deleteExam(Exam exam) {
		

		boolean isDeleted = false;

		Connection connection = CWS_DBConnection.getConnection();
		Connection connectionSystem =  System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			connectionSystem.setAutoCommit(false);

			PreparedStatement statement;

			//Remove Exam record from NasCWS
			statement = connection.prepareStatement("DELETE FROM [Exam] WHERE [examID] = ?");
			statement.setInt(1, exam.getExamID());
			statement.execute();

			//Remove Exam record from NasSystem
			statement  = connectionSystem.prepareStatement("DELETE FROM [Exam] WHERE [examID] = ?");
			statement.setInt(1,  exam.getExamID());
			statement.execute();

			connection.commit();
			connection.close();

			connectionSystem.commit();
			connectionSystem.close();

			isDeleted = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
				connectionSystem.rollback();
			} catch (SQLException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			}
		}

		return isDeleted;
	}

	/**
	 * Retrieve the booking datetime of a exam booked by a student.
	 * Returns null if there is no booking done by a student for that exam.
	 * 
	 * @author Soe Lynn
	 * @param exam
	 * @param student
	 * @return Date
	 */
	public Date getExamBookingDateTimeByStudent(Exam exam, Student student) {
		Date result = null;

		Connection connection = System_DBConnection.getConnection();
		PreparedStatement statement;
		ResultSet resultSet;

		try {
			statement = connection.prepareStatement("SELECT [bookedDateTime] FROM [StudentExamBooking]"
					+ "WHERE matricNo = ? AND examID = ?;");
			statement.setString(1, student.getMatricNo());
			statement.setInt(2, exam.getExamID());

			resultSet = statement.executeQuery();

			if(resultSet.next()) { // There is a record
				result = resultSet.getDate("bookedDateTime");
			}			
		} catch(SQLException se) {
			se.printStackTrace();
		}

		return result;
	}

	@Override
	public Exam getExamByExamID(int examID) {
		

		String examTitle = null;
		String courseCode = null;
		Date startDateTime = null;
		Date endDateTime = null;
		int maxMarks = 0;
		float duration = 0;
		int no_of_questions = 0;
		Exam exam = null;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT "
					+ "[examTitle], [courseCode], [start_date_time], [end_date_time], [maxMarks], [duration], [no_of_questions] "
					+ "FROM [Exam] WHERE [examID] = ?");
			statement.setInt(1, examID);

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				examTitle = rs.getString("examTitle");
				courseCode = rs.getString("courseCode");
				startDateTime = rs.getDate("start_date_time");
				endDateTime = rs.getDate("end_date_time");				
				maxMarks = rs.getInt("maxMarks");
				duration = rs.getFloat("duration");
				no_of_questions = rs.getInt("no_of_questions");

				exam = new Exam(examID, examTitle, courseCode, startDateTime, endDateTime, maxMarks, duration, no_of_questions);
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

		return exam;
	}

	@Override
	public List<Exam> getExams() {
		

		Exam exam = null;
		String examTitle;
		String courseCode;
		Date startDateTime;
		Date endDateTime;				
		int maxMarks;
		float duration;
		int no_of_questions;
		int examID;
		List<Exam> exams = new ArrayList<Exam>();

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT "
					+ "[courseCode], [start_date_time], [end_date_time], [maxMarks], [examTitle], [examID], [duration], [no_of_questions] "
					+ "FROM [Exam]");

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				examTitle = rs.getString("examTitle");
				courseCode = rs.getString("courseCode");
				startDateTime = rs.getDate("start_date_time");
				endDateTime = rs.getDate("end_date_time");				
				maxMarks = rs.getInt("maxMarks");
				examID = rs.getInt("examID");
				duration = rs.getFloat("duration");
				no_of_questions = rs.getInt("no_of_questions");

				exam = new Exam(examID, examTitle, courseCode, startDateTime, endDateTime, maxMarks, duration, no_of_questions);
				exams.add(exam);
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

		return exams;
	}

	@Override
	public List<Exam> getExamsByCourse(Course course) {
		

		Exam exam = null;
		String examTitle;
		String courseCode = course.getCourseCode();
		Date startDateTime;
		Date endDateTime;				
		int maxMarks = 0;
		float duration = 0;
		int no_of_questions= 0;
		int examID;
		List<Exam> exams = new ArrayList<Exam>();

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT "
					+ "[examTitle], [start_date_time], [end_date_time], [maxMarks], [examID], [duration], [no_of_questions] "
					+ "FROM [Exam] WHERE [courseCode] = ?");
			statement.setString(1, courseCode);

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				examTitle = rs.getString("examTitle");
				startDateTime = rs.getDate("start_date_time");
				endDateTime = rs.getDate("end_date_time");				
				maxMarks = rs.getInt("maxMarks");
				examID = rs.getInt("examID");
				no_of_questions = rs.getInt("no_of_questions");


				duration = rs.getFloat("duration");

				exam = new Exam(examID, examTitle, courseCode, startDateTime, endDateTime, maxMarks, duration, no_of_questions);
				exams.add(exam);
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

		return exams;
	}

	@Override
	public List<Exam> getExamsByStudent(Student student) {
		

		Exam exam = null;
		String username = student.getUserName();
		String examTitle;
		String courseCode;
		Date startDateTime;
		Date endDateTime;				
		int maxMarks;
		float duration = 0;
		int no_of_questions=0;
		int examID;
		List<Exam> exams = new ArrayList<Exam>();

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT "
					+ "[Exam].[examTitle], [Exam].[courseCode], [Exam].[start_date_time], [Exam].[end_date_time], "
					+ "[Exam].[maxMarks], [Exam].[examID], [Exam].[duration], [Exam].[no_of_questions] "
					+ "FROM [Exam] INNER JOIN [StudentExam] "
					+ "ON [Exam].[examID] = [StudentExam].[examID] "
					+ "WHERE [StudentExam].[username] = ?");
			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				examTitle = rs.getString("examTitle");
				courseCode = rs.getString("courseCode");
				startDateTime = rs.getDate("start_date_time");
				endDateTime = rs.getDate("end_date_time");				
				maxMarks = rs.getInt("maxMarks");
				examID = rs.getInt("examID");
				duration = rs.getFloat("duration");
				no_of_questions = rs.getInt("no_of_questions"); 

				exam = new Exam(examID, examTitle, courseCode, startDateTime, endDateTime, maxMarks, duration, no_of_questions);
				exams.add(exam);
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

		return exams;
	}

	@Override
	public List<Exam> getExamsBookedByStudent(Student student) {
		

		String matricNo = student.getMatricNo();

		Exam exam = null;
		int examID;
		List<Exam> exams = new ArrayList<Exam>();

		Connection connSys = System_DBConnection.getConnection();

		try {
			connSys.setAutoCommit(false);

			PreparedStatement statement;

			statement = connSys.prepareStatement("SELECT [examID] FROM [StudentExamBooking] WHERE [matricNO] = ?");
			statement.setString(1, matricNo);

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				examID = rs.getInt("examID");
				exam = this.getExamByExamID(examID);

				exams.add(exam);
			}

			connSys.commit();
			connSys.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connSys.rollback();
			} catch (SQLException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			}
		}

		return exams;
	}

	@Override
	public List<Exam> getExamsBookedByDate(Date bookedDateTime1, Date bookedDateTime2) {
		

		Exam exam = null;
		int examID;
		List<Exam> exams = new ArrayList<Exam>();

		Connection connSys = System_DBConnection.getConnection();

		try {
			connSys.setAutoCommit(false);

			PreparedStatement statement;

			statement = connSys.prepareStatement("SELECT [examID] FROM [StudentExamBooking] WHERE [bookedDateTime] "
					+ "BETWEEN ? AND ?");
			statement.setTimestamp(1, new Timestamp(bookedDateTime1.getTime()));
			statement.setTimestamp(2, new Timestamp(bookedDateTime2.getTime()));

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				examID = rs.getInt("examID");
				exam = this.getExamByExamID(examID);

				exams.add(exam);
			}

			connSys.commit();
			connSys.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connSys.rollback();
			} catch (SQLException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			}
		}

		return exams;
	}

	@Override
	public List<Exam> getExamsAttendedByStudent(Student student) {
		

		String matricNo = student.getMatricNo();

		Exam exam = null;
		int examID;
		List<Exam> exams = new ArrayList<Exam>();

		Connection connSys = System_DBConnection.getConnection();

		try {
			connSys.setAutoCommit(false);

			PreparedStatement statement;

			statement = connSys.prepareStatement("SELECT [examID] FROM [StudentExamAttend] WHERE [matricNO] = ?");
			statement.setString(1, matricNo);

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				examID = rs.getInt("examID");
				exam = this.getExamByExamID(examID);

				exams.add(exam);
			}

			connSys.commit();
			connSys.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connSys.rollback();
			} catch (SQLException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
			}
		}

		return exams;
	}

	@Override
	public boolean setStudentExamCWS() {
		

		boolean isSet = false;
		String username = null;
		StudentDataAccess sDataAccess = new StudentDAO();
		CourseDataAccess cDataAccess = new CourseDAO();
		Student student = null;
		Course course = null;
		Exam exam = null;
		List<Student> students = new ArrayList<Student>();
		List<Course> courses = new ArrayList<Course>();
		List<Exam> exams = new ArrayList<Exam>();

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT DISTINCT(username) FROM [StudentCourse]");

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				username = rs.getString("username");
				student = sDataAccess.getStudentByUserName(username);

				students.add(student);
			}

			for(Student s : students) {
				courses = cDataAccess.getCoursesByStudent(s);

				for(Course c : courses) {
					exams = this.getExamsByCourse(c);

					for(Exam e : exams) { 
						statement = connection.prepareStatement("INSERT INTO [StudentExam] "
								+ "([username], [examID]) VALUES (?, ?);");
						statement.setString(1, s.getUserName());
						statement.setInt(2, e.getExamID());
						statement.execute();
					}
				}
			}

			connection.commit();
			connection.close();

			isSet = true;
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

		return isSet;
	}

	@Override
	public boolean clearStudentExamCWS() {
		

		boolean isCleared = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("TRUNCATE TABLE [StudentExam]");
			statement.execute();

			connection.commit();
			connection.close();

			isCleared = true;
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

		return isCleared;
	}

}
