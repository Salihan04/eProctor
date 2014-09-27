/*
 * 
 * @author	Soe Lynn
 * @author	Sherman
 * @version	1.1
 * 
 */

package com.sherminator.dataaccess;

import java.util.List;

import com.sherminator.model.Answer;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;
import com.sherminator.model.Student;

public interface AnswerDataAccess extends DataAccess{

	public boolean createAnswer(Answer answer);
	public boolean updateAnswer(Answer answer);
	public boolean deleteAnswer(Answer answer);
	public Answer getAnswerByAnswerID(int answerID);
	public boolean setAnswerID(Answer answer);
	public List<Answer> getAnswersByQuestion(Question question);
	public List<Answer> getAnswersChosenByStudentForExam(Student student, Exam exam);
	public List<Answer> getCorrectAnswersByExam(Exam exam);
}
