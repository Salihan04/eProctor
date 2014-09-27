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

import com.sherminator.model.Answer;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;
import com.sherminator.model.Student;
import com.sherminator.util.CWS_DBConnection;

public class AnswerDAO implements AnswerDataAccess{

	@Override
	public boolean createAnswer(Answer answer) {
		boolean isCreated = false;
		Connection connection = CWS_DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("INSERT INTO [Answer] ([answerSentence], [questionID]) "
					+ "VALUES (?, ?);");
			statement.setString(1, answer.getAnswerSentence());
			statement.setInt(2, answer.getQuestionID());
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
	public boolean updateAnswer(Answer answer) {
		boolean isUpdated = false;
		Connection connection = CWS_DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("UPDATE [Answer] SET [answerSentence] = ?, [questionID] = ? "
					+ "WHERE answerID = ?;");
			statement.setString(1, answer.getAnswerSentence());
			statement.setInt(2, answer.getQuestionID());
			statement.setInt(3, answer.getAnswerID());
			statement.execute();

			connection.commit();
			connection.close();
			isUpdated = true;

		}catch (SQLException e) {
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
	public boolean deleteAnswer(Answer answer) {
		
		boolean isDeleted = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;
			
			statement = connection.prepareStatement("DELETE FROM [StudentAnswer] WHERE [answerID] = ?");
			statement.setInt(1, answer.getAnswerID());
			statement.execute();

			statement = connection.prepareStatement("DELETE FROM [Answer] WHERE [answerID] = ?");
			statement.setInt(1, answer.getAnswerID());
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
	public Answer getAnswerByAnswerID(int answerID) {
		String answerSentence = null;
		int questionID;
		Answer answer = null;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT "
					+ "[answerSentence], [questionID] "
					+ "FROM [Answer] WHERE [answerID] = ?");
			statement.setInt(1, answerID);

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				answerSentence = rs.getString("answerSentence");
				questionID = rs.getInt("questionID");

				answer = new Answer(answerID, answerSentence, questionID);
			}

			connection.commit();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return answer;
	}
	
	public boolean setAnswerID(Answer answer){
		Connection connection = CWS_DBConnection.getConnection();
		Boolean set = false;

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [answerID] "
					+ "FROM [Answer] WHERE [answerSentence] = ? AND [questionID] = ?;");
			statement.setString(1, answer.getAnswerSentence());
			statement.setInt(2, answer.getQuestionID());
			

			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				answer.setAnswerID(rs.getInt("answerID"));
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
	public List<Answer> getAnswersByQuestion(Question question) {
		
		int answerID;
		String answerSentence = null;
		int questionID;
		Answer answer = null;
		List<Answer> answers = new ArrayList<Answer>();

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [answerID], [answerSentence] "
					+ "FROM [Answer] WHERE [questionID] = ?");
			statement.setInt(1, question.getQuestionID());

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				answerID = rs.getInt("answerID");
				answerSentence = rs.getString("answerSentence");
				questionID = question.getQuestionID();

				answer = new Answer(answerID, answerSentence, questionID);

				answers.add(answer);
			}

			connection.commit();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return answers;
	}

	//Get list of answers chosen by a particular student during a specified exam
	public List<Answer> getAnswersChosenByStudentForExam(Student student, Exam exam) {

		int answerID;
		Answer answer = null;
		List<Answer> answers = new ArrayList<Answer>();

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [StudentAnswer].[answerID] FROM [StudentAnswer] INNER JOIN [Answer] "
					+ "ON [StudentAnswer].[answerID] = [Answer].[answerID] INNER JOIN [Question] "
					+ "ON [Answer].[questionID] = [Question].[questionID] WHERE [username] = ? AND [examID] = ?");
			statement.setString(1, student.getUserName());
			statement.setInt(2, exam.getExamID());

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				answerID = rs.getInt("answerID");
				answer = this.getAnswerByAnswerID(answerID);

				answers.add(answer);
			}

			connection.commit();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return answers;
	}

	//Get list of correct answers for a particular exam
	public List<Answer> getCorrectAnswersByExam(Exam exam) {

		int correctAnswerID;
		Answer correctAnswer = null;
		List<Answer> correctAnswers = new ArrayList<Answer>();

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [correctAnswer] FROM [Question] WHERE [examID] = ?");
			statement.setInt(1, exam.getExamID());

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				correctAnswerID = rs.getInt("correctAnswer");
				correctAnswer = this.getAnswerByAnswerID(correctAnswerID);

				correctAnswers.add(correctAnswer);
			}

			connection.commit();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return correctAnswers;
	}
}