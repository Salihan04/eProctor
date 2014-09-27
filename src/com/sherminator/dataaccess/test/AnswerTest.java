/*
 * @author	Gou Tong
 * @version	1.0
 * 
 */

package com.sherminator.dataaccess.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.sql.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.sherminator.dataaccess.AnswerDAO;
import com.sherminator.dataaccess.CourseDAO;
import com.sherminator.dataaccess.ExamDAO;
import com.sherminator.dataaccess.ProfessorDAO;
import com.sherminator.dataaccess.QuestionDAO;
import com.sherminator.dataaccess.StudentDAO;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;
import com.sherminator.model.Question;
import com.sherminator.model.Student;


public class AnswerTest extends TestCase {

	@Test
	public void test() {
		
		ProfessorDAO profDAO = new ProfessorDAO();
		Professor professor = new Professor("testProf", "root", "S003");
		profDAO.createProfessor(professor);
		
		QuestionDAO questDAO = new QuestionDAO();
		Question question = new Question("please ignore this qustino", 2);
		questDAO.createQuestion(question);
		questDAO.setQuestionID(question);
		
		StudentDAO studentDAO = new StudentDAO();
		Student student = new Student("please ignore this person","srootj", "U91919191L", "please ignore this person", "www.youtube.com");
		studentDAO.createStudent(student);
		
		CourseDAO courseDAO = new CourseDAO();
		Course course = new Course("testing", "testing");
		courseDAO.createCourse(course);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 3, 16, 8, 30, 0);
		Date tempDate = new Date(calendar.getTime().getTime());
		Date startDateTime = new java.sql.Date(tempDate.getTime());
		calendar.set(2014, 4, 16, 8, 30, 0);
		tempDate = new Date(calendar.getTime().getTime());
		Date endDateTime = new java.sql.Date(tempDate.getTime());
		ExamDAO examDAO = new ExamDAO();
		Exam exam = new Exam("testing", "testing", startDateTime, endDateTime, 100, 45f, 1);
		examDAO.createExam(exam);
		examDAO.setExamID(exam);
		
		AnswerDAO test = new AnswerDAO();
		List<Answer> answerList = new ArrayList<Answer>();
		
		Boolean deleteAnswers = false;
		Boolean createAnswer = false;
		Boolean setAnswerID = false;
		String updateAnswer = "nope";
		Boolean getAnswerByAnswerID = false;
		Boolean getAnswersByQuestion = false;
		Boolean getAnswersChosenByStudentForExam = false;
		Boolean getCorrectAnswersByExam = false;
		
		//create answers
		Answer answer = new Answer("Please ignore this anwer", question.getQuestionID());
		createAnswer = test.createAnswer(answer);
		
		//setAnwerID
		test.setAnswerID(answer);
		if(answer.getAnswerID()!=-1){
			setAnswerID = true;
		}
		
		//getAnswerByAnswerID
		getAnswerByAnswerID = (answer.getAnswerSentence().equals(test.getAnswerByAnswerID(answer.getAnswerID()).getAnswerSentence()));
		
		//updateAnswer
		answer.setAnswerSentence("Please ignore this answer 2");
		test.updateAnswer(answer);
		updateAnswer = test.getAnswerByAnswerID(answer.getAnswerID()).getAnswerSentence();
		
		//get answers by question
		answerList = test.getAnswersByQuestion(question);
		if(answerList==null){
			fail("getAnswersByQuestion failed");
		}
		for(Answer ans: answerList){
			if(ans.getAnswerID()==answer.getAnswerID()&& ans.getAnswerSentence().equals(answer.getAnswerSentence())){
				getAnswersByQuestion = true;
			}
		
		}
		
		question.setCorrectAnswer(answer);
		question.setExamID(exam.getExamID());
		questDAO.updateQuestion(question);
		
		//getAnswerByStudent choose answer
		List<Question> questions = new ArrayList<Question>();
		questions.add(question);
		Hashtable<Question, Answer> questionAnswers = new Hashtable<Question, Answer>();
		questionAnswers.put(question, answer);
		studentDAO.studentChooseAnswers(student, questions, questionAnswers);
		answerList = test.getAnswersChosenByStudentForExam(student, exam);
		if(answerList==null){
			fail("studentChooseAnswer failed");
		}
		for(Answer ans: answerList){
			if(ans.getAnswerID()==answer.getAnswerID()&& ans.getAnswerSentence().equals(answer.getAnswerSentence())){
				getAnswersChosenByStudentForExam = true;
			}
		}
		
		//get correct answers by exam
		answerList = test.getCorrectAnswersByExam(exam);
		if(answerList==null){
			fail("getCorrectAnswersByExam failed");
		}
		for(Answer ans: answerList){
			if(ans.getAnswerID()==answer.getAnswerID()&& ans.getAnswerSentence().equals(answer.getAnswerSentence())){
				getCorrectAnswersByExam = true;
			}
		}
		
		//delete
		deleteAnswers = test.deleteAnswer(answer);
		questDAO.deleteQuestion(question);
		examDAO.deleteExam(exam);
		courseDAO.deleteCourse(course);
		studentDAO.deleteStudent(student);
		profDAO.deleteProfessor(professor);
		
		assertEquals("true", createAnswer.toString());
		System.out.println("AnswerDAO.createAnswer passed");
		assertEquals("true", setAnswerID.toString());
		System.out.println("AnswerDAO.setAnswerID passed");
		assertEquals("Please ignore this answer 2", updateAnswer);
		System.out.println("AnswerDAO.updateAnswer passed");
		assertEquals("true", getAnswerByAnswerID.toString());
		System.out.println("AnswerDAO.getAnswerByAnswerID passed");
		assertEquals("true", getAnswersByQuestion.toString());
		System.out.println("AnswerDAO.getAnswersByQuestion passed");
		assertEquals("true", getAnswersChosenByStudentForExam.toString());
		System.out.println("AnswerDAO.getAnswersChosenByStudentForExam passed");
		assertEquals("true", getCorrectAnswersByExam.toString());
		System.out.println("AnswerDAO.getCorrectAnswersByExam passed");
		assertEquals("true", deleteAnswers.toString());
		System.out.println("AnswerDAO.deleteAnswers passed");
		System.out.println("all working in AnswerDAO");
		
	}

}
