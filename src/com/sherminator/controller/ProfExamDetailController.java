/*
 * @author	Huiting
 * @version	1.0
 * 
 */
package com.sherminator.controller;

import java.util.List;

import com.sherminator.businesslogic.AnomalyReportManager;
import com.sherminator.businesslogic.AnswerManager;
import com.sherminator.businesslogic.ExamManager;
import com.sherminator.businesslogic.QuestionManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.model.AnomalyReport;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;
import com.sherminator.model.Question;
import com.sherminator.model.Student;
import com.sherminator.presentation.ProfessorExamDetails_UI;

public class ProfExamDetailController extends AbstractController{
	private Professor professor;
	private Course course;
	private Exam exam;
	private List<Question> questions;
	private List<Student> students;
	
	private ProfessorExamDetails_UI profExam_ui;
	
	private AnomalyReportManager anomalyReportManager = new AnomalyReportManager();
	private QuestionManager questionManager = new QuestionManager();
	private StudentManager studentManager = new StudentManager();
	
	public ProfExamDetailController(Professor professor, Course course, Exam exam){
		this.professor = professor;
		this.course = course;
		this.exam = exam;
		this.questions = getQuestions();
		this.students = getStudents();
	}

	public void showPresentation() {
		//by default, show the results tab
		profExam_ui = new ProfessorExamDetails_UI(professor, course, exam, questions, students, this);
		profExam_ui.show();
	}
	
	public List<Question> getQuestions(){
		questions = questionManager.getQuestionsByExam(exam);
		return questions;
	}
	
	public List<Student> getStudents(){
		students = studentManager.getStudentsEnrolledInCourse(course);
		return students;
	}
	
	public List<Student> getAllStudents(){
		students = studentManager.getStudents();
		return students;
	}
	
	public void gotoNextController() {
		//go to previous page
		
	}
	
	public void gotoPreviousController(Professor professor,Course course){
		ProfCourseDetailController profCourseDetailController = new ProfCourseDetailController(professor,course);
		profCourseDetailController.showPresentation();
		profExam_ui.close();
	}
	
	public List<Question> removeQuestion(Question question){
		AnswerManager answerManager = new AnswerManager();
		List<Answer> answers = answerManager.getAnswersByQuestion(question);
		for(Answer answer : answers){
			answerManager.deleteAnswer(answer);
		}
		QuestionManager questionManager = new QuestionManager();
		questionManager.deleteQuestion(question);
		
		return getQuestions();
	}
	
	public List<Student> removeStudent(Student student){
		StudentManager studentManager = new StudentManager();
		studentManager.deleteStudentEnrollCourse(student, course);
		ExamManager examManager = new ExamManager();
		examManager.clearStudentExamCWS();
		examManager.setStudentExamCWS();
		
		return getStudents();
	}
	
	public List<Student> addStudent(Student student, Course course) {
		StudentManager studentManager = new StudentManager();
		studentManager.studentEnrollCourse(student, course);
		
		ExamManager examManager = new ExamManager();
		examManager.clearStudentExamCWS();
		examManager.setStudentExamCWS();
		
		return getStudents();
	}
	
	public AnomalyReport getAnomalyReportByStudentAndExam(Student student, Exam exam) {
		return anomalyReportManager.getAnomalyReportByStudentAndExam(student, exam);
	}
	
}
