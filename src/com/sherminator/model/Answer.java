/*
 * @author	Nasruddin
 * @version	1.0
 * 
 */
package com.sherminator.model;

public class Answer {

	private int answerID;
	private String answerSentence;
	private int questionID;
	
	public Answer(String answerSentence, int questionID) {
		this(-1, answerSentence, questionID);
	}
	
	public Answer(int answerID, String answerSentence, int questionID) {
		this.answerID = answerID;
		this.answerSentence = answerSentence;
		this.questionID = questionID;
	}
	
	public int getAnswerID() {
		return answerID;
	}
	
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
	
	public String getAnswerSentence() {
		return answerSentence;
	}
	
	public void setAnswerSentence(String answerSentence) {
		this.answerSentence = answerSentence;
	}
	
	public int getQuestionID() {
		return questionID;
	}
	
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	
	public boolean equals(Object o) {
		Answer answer = (Answer) o;
		return getAnswerID() == answer.getAnswerID();
	}
}
