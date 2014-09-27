/*
 * @author	Nasruddin
 * @version	1.0
 * 
 */
package com.sherminator.dataaccess.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.sherminator.dataaccess.AnswerDAO;
import com.sherminator.dataaccess.CourseDAO;
import com.sherminator.dataaccess.ExamDAO;
import com.sherminator.dataaccess.QuestionDAO;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;

public class QuestionTest extends TestCase {

	@Test
	public void test() {
		//create dates
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(2014, 3, 24, 7, 0, 0);
		java.util.Date tempDate = calendar.getTime();
		Date startDateTime = new java.sql.Date(tempDate.getTime());
		calendar.clear();
		calendar.set(2014, 4, 24, 7, 0, 0);
		tempDate = calendar.getTime();
		Date endDateTime = new java.sql.Date(tempDate.getTime());
		
		//testResult
		Boolean createQuestion = false;
		Boolean setQuestionID = false;
		Boolean getQuestionByQuestionID = false;
		Boolean updateQuestion = false;
		Boolean getQuestions = false;
		Boolean getQuestionsByExam = false;
		Boolean deleteQuestion = false;
		
		
		//create dummy objects
		CourseDAO courseDAO = new CourseDAO();
		Course course = new Course("test", "testCourse");
		courseDAO.createCourse(course);
		
		ExamDAO examDAO = new ExamDAO();
		Exam exam = new Exam("testExam", "test", startDateTime, endDateTime, 100, 45, 1);
		examDAO.createExam(exam);
		examDAO.setExamID(exam);
		
		AnswerDAO answerDAO = new AnswerDAO();
		
		QuestionDAO test = new QuestionDAO();
		Question question = new Question("test question", exam.getExamID());
		Question tempQuestion = null;
		List<Question> questions = new ArrayList<Question>();
		
		//createQuestion
		createQuestion = test.createQuestion(question);
		
		//setQuestionID
		setQuestionID = test.setQuestionID(question);
		Answer answer = new Answer("Please ignore this anwer", question.getQuestionID());
		answerDAO.createAnswer(answer);
		answerDAO.setAnswerID(answer);
		question.setCorrectAnswer(answer);
		
		//updateQuestion
		question.setQuestionSentence("test question 2");
		updateQuestion = test.updateQuestion(question);
		
		
		
		//getQuestionByQuestionID
		tempQuestion = test.getQuestionByQuestionID(question.getQuestionID());
		if(tempQuestion==null){
			fail("no question extracted from getQuestionByQuestionID");
		}else if(tempQuestion.getExamID()==question.getExamID()
				&& tempQuestion.getQuestionID()==question.getQuestionID()
				&& tempQuestion.getQuestionSentence().equals(question.getQuestionSentence())){
			getQuestionByQuestionID = true;
		}

		//getQuestions
		questions = null;
		questions = test.getQuestions();
		if(questions==null){
			fail("no questions extracted from function getQUestions()");
		}else{
			for(Question q: questions){
				if(q.getExamID()==question.getExamID()
						&& q.getQuestionID()==question.getQuestionID()
						&& q.getQuestionSentence().equals(question.getQuestionSentence())){
					getQuestions = true;
				}
			}
		}
		
		//getQuestionsByExam
		questions = null;
		questions = test.getQuestionsByExam(exam);
		if(questions==null){
			fail("no questions extracted from function getQUestionsByExam()");
		}else{
			for(Question q: questions){
				if(q.getExamID()==question.getExamID()
						&& q.getQuestionID()==question.getQuestionID()
						&& q.getQuestionSentence().equals(question.getQuestionSentence())){
					getQuestionsByExam = true;
				}
			}
		}
		
		//deleteQuestion
		answerDAO.deleteAnswer(answer);
		deleteQuestion = test.deleteQuestion(question);
		examDAO.deleteExam(exam);
		courseDAO.deleteCourse(course);
		
		
		//testResults
		assertEquals("true", createQuestion.toString());
		System.out.println("QuestionDAO.createQuestion(question) passed");
		assertEquals("true", setQuestionID.toString());
		System.out.println("QuestionDAO.setQuestionID(question) passed");
		assertEquals("true", updateQuestion.toString());
		System.out.println("QuestionDAO.updateQuestion(Question) passed");
		assertEquals("true", getQuestionByQuestionID.toString());
		System.out.println("QuestionDAO.getQuestionByQuestionID(int id) passed");
		assertEquals("true", getQuestions.toString());
		System.out.println("QuestionDAO.getQuestions() passed");
		assertEquals("true", getQuestionsByExam.toString());
		System.out.println("QuestionDAO.getQUestionsByExam(Exam) passed");
		assertEquals("true", deleteQuestion.toString());
		System.out.println("QuestionDAO.deleteQuestion(question) passed");
		System.out.println("all working in qeustionDAO");
	}

}
