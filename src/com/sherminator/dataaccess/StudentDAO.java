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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.dataaccessfactory.CourseDataAccessFactory;
import com.sherminator.dataaccessfactory.StudentDataAccessFactory;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;
import com.sherminator.model.Question;
import com.sherminator.model.Student;
import com.sherminator.model.User;
import com.sherminator.util.CWS_DBConnection;
import com.sherminator.util.System_DBConnection;

public class StudentDAO implements StudentDataAccess {

	public boolean createStudent(Student student) {//tested
		boolean isCreated = false;

		Connection connection = CWS_DBConnection.getConnection();
		Connection connectionSystem = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("INSERT INTO [User] ([username], [password]) VALUES (?, ?);");
			statement.setString(1, student.getUserName());
			statement.setString(2, student.getPassword());
			statement.execute();

			statement = connection.prepareStatement("INSERT INTO [Student] ([username], [photo_url], [name], [matricNo]) VALUES (?, ?, ?, ?);");
			statement.setString(1, student.getUserName());
			statement.setString(2, student.getPhoto_url());
			statement.setString(3, student.getName());
			statement.setString(4, student.getMatricNo());
			statement.execute();

			statement = connectionSystem.prepareStatement("INSERT INTO [Student]([matricNo],[name]) VALUES ( ? , ? )");
			statement.setString(1, student.getMatricNo());
			statement.setString(2, student.getName());
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

	public boolean authenticate(User user) {
		boolean isAuthenticated = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			String sql = "SELECT [User].[password] FROM [dbo].[User] "
					+ "INNER JOIN [dbo].[Student] "
					+ "ON [User].[username] = [Student].[username] "
					+ "WHERE [Student].[username]=?";
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

	public List<Student> getStudentsEnrolledInCourse(Course course) {//tested
		List<Student> students = new ArrayList<Student>();
		StudentDataAccessFactory factory = new StudentDataAccessFactory();
		StudentDataAccess studentDataAccess = factory.getDataAccess();

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [username] FROM [StudentCourse] WHERE [courseCode] =?");
			statement.setString(1, course.getCourseCode());

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Student student = studentDataAccess.getStudentByUserName(rs.getString("username")) ;
				students.add(student);
			}

			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	@Override
	public boolean deleteStudent(Student student) {//tested
		boolean isDeleted = false;

		Connection connection = CWS_DBConnection.getConnection();
		Connection connectionSystem = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("DELETE FROM [StudentAnswer] WHERE [username] = ?");
			statement.setString(1, student.getUserName());
			statement.execute();

			statement = connection.prepareStatement("DELETE FROM [StudentExam] WHERE [username] = ?");
			statement.setString(1, student.getUserName());
			statement.execute();

			statement = connection.prepareStatement("DELETE FROM [StudentCourse] WHERE [username] = ?");
			statement.setString(1, student.getUserName());
			statement.execute();

			statement = connection.prepareStatement("DELETE FROM [Student] WHERE [username]=?");
			statement.setString(1, student.getUserName());
			statement.execute();

			statement = connection.prepareStatement("DELETE FROM [User] WHERE [username]=?");
			statement.setString(1, student.getUserName());
			statement.execute();

			statement = connectionSystem.prepareStatement("DELETE FROM [StudentExamAttend] WHERE [matricNo] = ?");
			statement.setString(1,student.getMatricNo());
			statement.execute();

			statement = connectionSystem.prepareStatement("DELETE FROM [StudentExamBooking] WHERE [matricNo] = ?");
			statement.setString(1,student.getMatricNo());
			statement.execute();

			statement = connectionSystem.prepareStatement("DELETE FROM [Student] WHERE [matricNo] = ?");
			statement.setString(1,student.getMatricNo());
			statement.execute();


			connection.commit();
			connection.close();

			isDeleted = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isDeleted;
	}


	public boolean updateStudent(Student student) {//tested
		boolean isUpdated = false;

		Connection connection = CWS_DBConnection.getConnection();
		Connection connectionSystem = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("UPDATE [User] SET [password]=? WHERE [username]=?");
			statement.setString(1, student.getPassword());
			statement.setString(2, student.getUserName());
			statement.execute();

			statement = connection.prepareStatement("UPDATE [Student] SET [matricNo]=?,[name]=?,[photo_url]=? WHERE [username]=?");
			statement.setString(1, student.getMatricNo());
			statement.setString(2, student.getName());
			statement.setString(3, student.getPhoto_url());
			statement.setString(4, student.getUserName());
			statement.execute();

			statement = connectionSystem.prepareStatement("UPDATE [Student] SET [name] = ? WHERE [matricNo] = ?");
			
			statement.setString(1, student.getName());
			statement.setString(2, student.getMatricNo());
			statement.execute();

			connection.commit();
			connection.close();

			isUpdated = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	public List<Student> getStudentByName(String name) {//tested
		Connection connection = CWS_DBConnection.getConnection();
		List<Student> students = new ArrayList<Student>();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT * "
					+ "FROM [User]"
					+ " INNER JOIN [Student]"
					+ " ON [User].[username] = [Student].[username]"
					+ " WHERE [Student].[name] = ?");
			statement.setString(1, name);

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Student student = new Student(rs.getString("username"), rs.getString("password"), rs.getString("matricNo"),rs.getString("name"),rs.getString("photo_url"));
				students.add(student);
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	public Student getStudentByMatricNo(String matricNo) {//tested
		Connection connection = CWS_DBConnection.getConnection();
		Student student = null;

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT * "
					+ "FROM [User]"
					+ " INNER JOIN [Student]"
					+ " ON [User].[username] = [Student].[username]"
					+ " WHERE [Student].[matricNo] = ?");
			statement.setString(1, matricNo);

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				student = new Student(rs.getString("username"), rs.getString("password"), rs.getString("matricNo"),rs.getString("name"),rs.getString("photo_url"));
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return student;
	}

	public Student getStudentByUserName(String userName) {//tested
		Connection connection = CWS_DBConnection.getConnection();
		Student student = null;

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT * "
					+ "FROM [User]"
					+ " INNER JOIN [Student]"
					+ " ON [User].[username] = [Student].[username]"
					+ " WHERE [Student].[username] = ?");
			statement.setString(1, userName);

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				student = new Student(rs.getString("username"), rs.getString("password"), rs.getString("matricNo"),rs.getString("name"),rs.getString("photo_url"));
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return student;
	}

	public List<Student> getStudents(){//tested
		List<Student> students=new ArrayList<Student>();
		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT * "
					+ "FROM [User]"
					+ " INNER JOIN [Student]"
					+ " ON [User].[username] = [Student].[username]");

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Student student = new Student(rs.getString("username"), rs.getString("password"), rs.getString("matricNo"), rs.getString("name"),
						rs.getString("photo_url"));
				students.add(student);
			}
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	public void resetStudentDB(){//for resetting
		List<Student> students = new ArrayList<Student>();
		StudentDAO studentDAO = new StudentDAO();
		students = studentDAO.getStudents();

		for(Student student: students){
			studentDAO.deleteStudent(student);
		}
	}

	@Override
	public boolean studentChooseAnswer(Student student, Answer answer) {
		boolean isCreated = false;
		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("INSERT INTO [StudentAnswer] (answerID,userName) VALUES (?,?)");
			statement.setInt(1, answer.getAnswerID());
			statement.setString(2, student.getUserName());
			statement.execute();

			connection.commit();
			connection.close();
			
			isCreated =true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isCreated;
	}
	
	public boolean studentChooseAnswers(Student student, List<Answer> answers) {
		boolean isCreated = false;
		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			for(Answer answer: answers) {
				statement = connection.prepareStatement("INSERT INTO [StudentAnswer] (answerID,userName) VALUES (?,?)");
				statement.setInt(1, answer.getAnswerID());
				statement.setString(2, student.getUserName());
				statement.execute();
			}
			connection.commit();
			connection.close();

			isCreated =true;
		} catch (SQLException e) {
			e.printStackTrace();
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return isCreated;
	}
	
	public boolean studentChooseAnswers(Student student, List<Question> questions, Hashtable<Question, Answer> answers) {
		boolean isCreated = false;
		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			for(Question question: questions) {
				Answer answer = answers.get(question);
								
				statement = connection.prepareStatement("INSERT INTO [StudentAnswer] (answerID, userName, questionID) VALUES (?, ?, ?);");
				
				if(answer == null) {
					statement.setNull(1, java.sql.Types.INTEGER);
				}
				else {
					statement.setInt(1, answer.getAnswerID());
				}
				
				statement.setString(2, student.getUserName());
				statement.setInt(3, question.getQuestionID());
				statement.execute();
			}
			
			connection.commit();
			connection.close();

			isCreated =true;
		} catch (SQLException e) {
			e.printStackTrace();
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return isCreated;
	}

	@Override
	public boolean studentEnrollCourse(Student student, Course course) {
		

		boolean isCreated = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("INSERT INTO [StudentCourse] ([username], [courseCode]) VALUES (? ,?)");
			statement.setString(1, student.getUserName());
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
	public boolean updateStudentEnrollCourse(Student student, Course oldCourse, Course newCourse) {
		

		boolean isUpdated = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("Update [StudentCourse] SET [courseCode] = ? "
					+ "WHERE [username] = ? AND [courseCode] = ?");
			statement.setString(1, newCourse.getCourseCode());
			statement.setString(2, student.getUserName());
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
	
	public boolean deleteStudentAttendExam(Student student, Exam exam,
			Date dateTime) {
		boolean isDeleted = false;
		Connection connection = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("DELETE FROM [StudentExamAttend] "
					+ "WHERE [matricNo]=? AND [examID]=? AND [startDateTime]=?");
			statement.setString(1, student.getMatricNo());
			statement.setInt(2, exam.getExamID());
			statement.setTimestamp(3, new Timestamp(dateTime.getTime()));
			statement.execute();

			connection.commit();
			connection.close();
			
			isDeleted =true;
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

	@Override
	public boolean deleteStudentEnrollCourse(Student student, Course course) {
		
		
		boolean isDeleted = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement statement;

			statement = connection.prepareStatement("DELETE FROM [StudentCourse] WHERE [username] = ? AND [courseCode] = ?");
			statement.setString(1, student.getUserName());
			statement.setString(2, course.getCourseCode());			
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
	public boolean studentBookExam(Student student, Exam exam, Date datetime) {
		boolean isCreated = false;
		Connection connection = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("INSERT INTO [StudentExamBooking] (matricNo,examID,bookedDateTime) "
					+ "VALUES (?,?,?)");
			statement.setString(1, student.getMatricNo());
			statement.setInt(2, exam.getExamID());
			statement.setTimestamp(3, new Timestamp(datetime.getTime()));
			statement.execute();

			connection.commit();
			connection.close();
			
			isCreated =true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isCreated;
	}

	public boolean studentCancelBooking(Student student,Exam exam) {
		boolean isCreated = false;
		Connection connection = System_DBConnection.getConnection();

		try {
			PreparedStatement statement;

			statement = connection.prepareStatement("DELETE FROM [StudentExamBooking]"
					+ " WHERE matricNo = ? AND examID = ?");
			statement.setString(1, student.getMatricNo());
			statement.setInt(2, exam.getExamID());
			statement.execute();
			connection.close();
			
			isCreated =true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isCreated;
	}
	
	@Override
	public boolean updateStudentBookExam(Student student, Exam exam, Date datetime) {
		boolean isUpdated = false;
		Connection connection = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("Update [StudentExamBooking] "
					+ "SET [matricNo]=?,[examID]=?,[bookedDateTime]=? "
					+ "WHERE examID=? AND matricNo=?");
			statement.setString(1, student.getMatricNo());
			statement.setInt(2, exam.getExamID());
			statement.setTimestamp(3, new Timestamp(datetime.getTime()));
			statement.setInt(4, exam.getExamID());
			statement.setString(5, student.getMatricNo());
			statement.execute();

			connection.commit();
			connection.close();
			
			isUpdated =true;
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
	public boolean deleteStudentBookExam(Student student, Exam exam, Date datetime) {
		boolean isDeleted = false;
		Connection connection = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("DELETE FROM [StudentExamBooking] "
					+ "WHERE [matricNo]=? AND [examID]=? AND [bookedDateTime]=?");
			statement.setString(1, student.getMatricNo());
			statement.setInt(2, exam.getExamID());
			statement.setTimestamp(3, new Timestamp(datetime.getTime()));
			statement.execute();

			connection.commit();
			connection.close();
			
			isDeleted =true;
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

	@Override
	public boolean studentAttendExam(Student student, Exam exam, Date datetime) {
		boolean isCreated = false;
		Connection connection = System_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("INSERT INTO [StudentExamAttend] (matricNo,examID,startDateTime) "
					+ "VALUES (?,?,?)");
			statement.setString(1, student.getMatricNo());
			statement.setInt(2, exam.getExamID());
			statement.setTimestamp(3, new Timestamp(datetime.getTime()));
			statement.execute();

			connection.commit();
			connection.close();
			
			isCreated =true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isCreated;
	}

	@Override
	public List<Student> getStudentsByBookedExamDate(Date bookedDateTime1, Date bookedDateTime2) {
		
		
		Student student = null;
		List<Student> students = new ArrayList<Student>();
		String matricNo;		
		
		Connection connSys = System_DBConnection.getConnection();

		try {
			connSys.setAutoCommit(false);

			PreparedStatement statement;

			statement = connSys.prepareStatement("SELECT [matricNo] FROM [StudentExamBooking] WHERE [bookedDateTime] "
					+ "BETWEEN ? AND ?");
			statement.setTimestamp(1, new Timestamp(bookedDateTime1.getTime()));
			statement.setTimestamp(2, new Timestamp(bookedDateTime2.getTime()));

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				matricNo = rs.getString("matricNo");
				student = this.getStudentByMatricNo(matricNo);

				students.add(student);
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
		
		return students;
	}

}

