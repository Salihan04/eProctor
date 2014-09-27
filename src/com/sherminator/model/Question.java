/*
 * @author	Nasruddin
 * @version	1.0
 * 
 */
package com.sherminator.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
	
	private int questionID;
	private String questionSentence;
	private Answer correctAnswer;
	private int examID;
	private List<Answer> answers;
	
	public Question(String questionSentence, int examID) {
		this(-1, questionSentence, null, examID);
		answers = new ArrayList<Answer>();
	}
	
	public Question(String questionSentence, Answer correctAnswer, int examID) {
		this(-1, questionSentence, correctAnswer, examID);
		answers = new ArrayList<Answer>();
	}
	
	public Question(int questionID, String questionSentence, Answer correctAnswer, int examID)
	{
		this.questionID = questionID;
		this.questionSentence = questionSentence;
		this.correctAnswer = correctAnswer;
		this.examID = examID;
		answers = new ArrayList<Answer>();
	}
	
	public int getQuestionID() {
		return questionID;
	}
	
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	
	public String getQuestionSentence() {
		return questionSentence;
	}
	
	public void setQuestionSentence(String questionID) {
		this.questionSentence = questionSentence;
	}
	
	public Answer getCorrectAnswer() {
		return correctAnswer;
	}
	
	public void setCorrectAnswer(Answer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	public int getExamID() {
		return examID;
	}
	
	public void setExamID(int examID) {
		this.examID = examID;
	}
	
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
	
	public void removeAnswer(Answer answer) {
		answers.remove(answer);
	}
	
	public List<Answer> getAnswers() {
		return answers;		
	}
}
