/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.businesslogic;

import java.util.List;

import com.sherminator.dataaccess.QuestionDataAccess;
import com.sherminator.dataaccessfactory.QuestionDataAccessFactory;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;

public class QuestionManager {
	private QuestionDataAccessFactory factory;
	private QuestionDataAccess questionDataAccess;
	
	public QuestionManager() {
		factory = new QuestionDataAccessFactory();
		questionDataAccess = factory.getDataAccess();
	}
	
	public boolean createQuestion(Question question){
		//implement
		return questionDataAccess.createQuestion(question);
	}
	
	public boolean updateQuestion(Question question){
		//implement
		return questionDataAccess.updateQuestion(question);
	}
	
	public boolean deleteQuestion(Question question){
		//implement
		return questionDataAccess.deleteQuestion(question);
	}
	
	public Question getQuestionByQuestionID(int questionID){
		return questionDataAccess.getQuestionByQuestionID(questionID);
	}
	
	public List<Question> getQuestions() {
		return questionDataAccess.getQuestions();
	}
	
	public List<Question> getQuestionsByExam(Exam exam) {
		return questionDataAccess.getQuestionsByExam(exam);
	}
}
