/*
 * 
 * @author	Soe Lynn
 * @author	Sherman
 * @version	1.1
 * 
 */
package com.sherminator.dataaccess;

import java.util.List;

import com.sherminator.model.Exam;
import com.sherminator.model.Question;

public interface QuestionDataAccess extends DataAccess{

	public boolean createQuestion(Question question);
	public boolean updateQuestion(Question question);
	public boolean deleteQuestion(Question question);
	public boolean setQuestionID(Question question);
	public Question getQuestionByQuestionID(int questionID);
	
	public List<Question> getQuestions();
	public List<Question> getQuestionsByExam(Exam exam);
}
