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

import com.sherminator.model.AnomalyReport;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;
import com.sherminator.util.CWS_DBConnection;
import com.sherminator.util.System_DBConnection;

public class QuestionDAO implements QuestionDataAccess{

	AnswerDataAccess aDataAccess = new AnswerDAO();

	@Override
	public boolean createQuestion(Question question) {
		

		boolean isCreated = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("INSERT INTO [Question] ([questionSentence], [examID]) "
					+ "VALUES (?, ?);");
			statement.setString(1, question.getQuestionSentence());
			//statement.setInt(2, question.getCorrectAnswer().getAnswerID());
			statement.setInt(2, question.getExamID());
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
	public boolean updateQuestion(Question question) {
		

		boolean isUpdated = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("UPDATE [Question] "
					+ "SET [questionSentence] = ? , [correctAnswer] = ?, [examID] = ? "
					+ "WHERE [questionID] = ?");
			statement.setString(1, question.getQuestionSentence());
			statement.setInt(2, question.getCorrectAnswer().getAnswerID());
			statement.setInt(3, question.getExamID());
			statement.setInt(4, question.getQuestionID());
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
	public boolean deleteQuestion(Question question) {
		

		boolean isDeleted = false;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("DELETE FROM [Answer] WHERE [questionID] = ?");
			statement.setInt(1, question.getQuestionID());
			statement.execute();
			
			statement = connection.prepareStatement("DELETE FROM [Question] WHERE [questionID] = ?");
			statement.setInt(1, question.getQuestionID());
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
	
	public boolean setQuestionID(Question question){
		Connection connection = CWS_DBConnection.getConnection();
		boolean set = false;
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [questionID] "
							+ "FROM [Question] WHERE [questionSentence] = ? AND [examID] = ?");
			statement.setString(1, question.getQuestionSentence());
			statement.setInt(2, question.getExamID());
			

			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				question.setQuestionID(rs.getInt("questionID"));
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
	public Question getQuestionByQuestionID(int questionID) {
		

		String questionSentence = null;
		Answer correctAnswer = null;
		AnswerDataAccess aDataAccess = new AnswerDAO();
		int examID;
		Question question = null;

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [questionSentence], [correctAnswer], [examID] "
					+ "FROM [Question] WHERE [questionID] = ?");
			statement.setInt(1, questionID);

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				questionSentence = rs.getString("questionSentence");

				Integer correctAnswerID = (Integer) rs.getObject("correctAnswer");

				if(correctAnswerID != null)
					correctAnswer = aDataAccess.getAnswerByAnswerID(correctAnswerID);

				examID = rs.getInt("examID");

				question = new Question(questionID, questionSentence, correctAnswer, examID);
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

		return question;
	}

	@Override
	public List<Question> getQuestions() {
		

		int questionID;
		String questionSentence = null;
		Answer correctAnswer = null;
		AnswerDataAccess aDataAccess = new AnswerDAO();
		int examID;
		Question question = null;
		List<Question> questions = new ArrayList<Question>();

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [questionID], [questionSentence], [correctAnswer], [examID] "
					+ "FROM [Question]");

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				questionID = rs.getInt("questionID");
				questionSentence = rs.getString("questionSentence");

				Integer correctAnswerID = (Integer) rs.getObject("correctAnswer");

				if(correctAnswerID != null)
					correctAnswer = aDataAccess.getAnswerByAnswerID(rs.getInt("correctAnswer"));

				examID = rs.getInt("examID");

				question = new Question(questionID, questionSentence, correctAnswer, examID);

				questions.add(question);
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

		return questions;
	}

	@Override
	public List<Question> getQuestionsByExam(Exam exam) {
		

		int questionID;
		String questionSentence = null;
		Answer correctAnswer = null;
		AnswerDataAccess aDataAccess = new AnswerDAO();
		int examID = exam.getExamID();
		Question question = null;
		List<Question> questions = new ArrayList<Question>();

		Connection connection = CWS_DBConnection.getConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement;

			statement = connection.prepareStatement("SELECT [questionID], [questionSentence], [correctAnswer] "
					+ "FROM [Question] WHERE [examID] = ?");
			statement.setInt(1, examID);

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				questionID = rs.getInt("questionID");
				questionSentence = rs.getString("questionSentence");

				Integer correctAnswerID = (Integer) rs.getObject("correctAnswer");

				if(correctAnswerID != null)
					correctAnswer = aDataAccess.getAnswerByAnswerID(rs.getInt("correctAnswer"));

				question = new Question(questionID, questionSentence, correctAnswer, examID);

				questions.add(question);
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

		return questions;
	}

}
