/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.businesslogic;

import java.util.List;

import com.sherminator.dataaccess.AnswerDataAccess;
import com.sherminator.dataaccessfactory.AnswerDataAccessFactory;
import com.sherminator.model.Answer;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;
import com.sherminator.model.Student;

public class AnswerManager {
	private AnswerDataAccessFactory factory;
	private AnswerDataAccess answerDataAccess;
	
	public AnswerManager() {
		factory = new AnswerDataAccessFactory();
		answerDataAccess = factory.getDataAccess();
	}
	
	public boolean createAnswer(Answer answer){
		return answerDataAccess.createAnswer(answer);
	}
	
	public boolean updateAnswer(Answer answer){
		return answerDataAccess.updateAnswer(answer);
	}
	
	public boolean deleteAnswer(Answer answer){
		return answerDataAccess.deleteAnswer(answer);
	}
	
	public Answer getAnswerByAnswerID(int answerID){
		return answerDataAccess.getAnswerByAnswerID(answerID);
	}
	
	public List<Answer> getAnswersByQuestion(Question question){
		return answerDataAccess.getAnswersByQuestion(question);
	}
	
	public List<Answer> getAnswersChosenByStudentForExam(Student student, Exam exam) {
		return answerDataAccess.getAnswersChosenByStudentForExam(student, exam);
	}
	
	public List<Answer> getCorrectAnswersByExam(Exam exam) {
		return answerDataAccess.getCorrectAnswersByExam(exam);
	}
}
