/*
 * @author	Goutong
 * @version	1.0
 * 
 */
package com.sherminator.controller;

import java.util.ArrayList;
import java.util.List;

import com.sherminator.businesslogic.QuestionManager;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;

public class QuestionTabController extends AbstractController{
	
	public QuestionTabController(){
		
	}
	
	public List<Question> getQuestion(Exam exam){
		List<Question> questionList= new ArrayList<Question>();
		QuestionManager questionMgr=new QuestionManager();
		questionList=questionMgr.getQuestionsByExam(exam);
		return questionList;
	}
	
	public void deleteQuestion(Question question){
		QuestionManager questionMgr= new QuestionManager();
		questionMgr.deleteQuestion(question);
	}

	public void gotoNextController(){
		//goto add question form
		
	}
	protected void gotoNextController(Question question){
		//goto edit question
	}

	@Override
	public void showPresentation() {
		
		
	}

}