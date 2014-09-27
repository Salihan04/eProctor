/*
 * 
 * TakeExamController provides public functions for TakeExam UI to interact with model/presentation
 * Will pass over control to StudentHomeController when student submits answers/ time is up
 * 
 * @author	Soe Lynn
 * @version 1.2
 * 
 */
package com.sherminator.controller;

import java.util.Hashtable;
import java.util.List;

import com.sherminator.businesslogic.AnswerManager;
import com.sherminator.businesslogic.CourseManager;
import com.sherminator.businesslogic.QuestionManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.model.Answer;
import com.sherminator.model.Exam;
import com.sherminator.model.InvigilationSession;
import com.sherminator.model.Question;
import com.sherminator.model.Student;
import com.sherminator.presentation.TakeExam_UI;


public class TakeExamController extends AbstractController{

	private TakeExam_UI tExamUI;
	private Student student;
	private Exam exam;
	private InvigilationSession session;
	
	private CourseManager courseMgr = new CourseManager();
	private QuestionManager questionManager = new QuestionManager();
	private StudentManager studentManager = new StudentManager();
	private AnswerManager answerManager = new AnswerManager();
	
	public TakeExamController(Student student, Exam exam, InvigilationSession session){
		this.student= student;
		this.exam= exam;
		this.session = session;
	}

	@Override
	public void showPresentation() {
		tExamUI= new TakeExam_UI(student, exam, session, this);
	}

	@Override
	public void gotoNextController() {
		tExamUI.close();
		StudentHomeController studHCtrl= new StudentHomeController(student);
		studHCtrl.showPresentation();
	}
	
	public void exitExam(){
		gotoNextController();
	}
	
	protected void gotoPrevController(){
		//currently no use for this yet
		tExamUI.close();
		StudentCourseDetailController studCourseDetCtrl= new StudentCourseDetailController(student,courseMgr.getCourseByCourseCode(exam.getCourseCode()));
		studCourseDetCtrl.showPresentation();
	}
	
	public void back(){
		
		gotoPrevController();
	}
	
	public void studentChooseAnswers(Student student, List<Question> questions, Hashtable<Question, Answer> answers) {
		studentManager.studentChooseAnswers(student, questions, answers);
	}
	
	public List<Question> getQuestionsByExam(Exam exam) {
		return questionManager.getQuestionsByExam(exam);
	}
	
	public List<Answer> getAnswersByQuestion(Question question){
		return answerManager.getAnswersByQuestion(question);
	}

}
