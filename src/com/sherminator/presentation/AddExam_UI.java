/*
 * 
 * @author	Gou Tong
 * @author	Huiting
 * @version	1.0
 * 
 */

package com.sherminator.presentation;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.sherminator.controller.AddExamController;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;

public class AddExam_UI implements AbstractUI {

	private Professor professor;
	private Course course;
	private Exam exam;
	private AddExamController addExamController;
	
	private JFrame frame;
	private JLabel lblWelcome;
	private JButton btnLogout;
	private JButton btnBack;
	private JButton btnSubmit;
	private JLabel lblCourseCode;
	private JLabel lblCourseName;
	private JLabel lblExamTitle;
	private JLabel lblMaxMarks;
	private JLabel lblDuration;
	private JLabel lblStartTime;
	private JLabel lblEndTime;
	private JLabel lblNumQusestions;
	private JTextField txtCourseCode;
	private JTextField txtCourseName;
	private JTextField txtExamTitle;
	private JTextField txtMaxMarks;
	private JTextField txtDuration;
	private JTextField txtStartTime;
	private JTextField txtEndTime;
	private JTextField txtNumQuestions;
	
	private class Back_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			addExamController.gotoNextController();
		}
		
	}
	
	private class Submit_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String examTitle = txtExamTitle.getText();
			String courseCode = course.getCourseCode();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String startDateTime_String = txtStartTime.getText();
			java.util.Date startDateTime = null;
			try {
				startDateTime = formatter.parse(startDateTime_String);
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
			java.sql.Date startDateTime_sql = new java.sql.Date(startDateTime.getTime());
			
			String endDateTime_String = txtEndTime.getText();
			java.util.Date endDateTime = null;
			try {
				endDateTime = formatter.parse(endDateTime_String);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			java.sql.Date endDateTime_sql = new java.sql.Date(endDateTime.getTime());
			
			int maxMarks = Integer.parseInt(txtMaxMarks.getText());
			float duration = Float.parseFloat(txtDuration.getText());
			int no_of_questions = Integer.parseInt(txtNumQuestions.getText());
			
			exam = new Exam(examTitle, courseCode, startDateTime_sql, endDateTime_sql, maxMarks, duration, no_of_questions);
			
			addExamController.addExam(exam);
			
			addExamController.gotoNextController();
		}
		
	}

	/**
	 * Create the application.
	 */
	public AddExam_UI(Professor professor, Course course, AddExamController addExamController) {
		
		this.professor = professor;
		this.course = course;
		this.addExamController = addExamController;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 420, 350);
		frame.setSize(430, 370);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new Logout_Listener(this));
		btnLogout.setBounds(300, 19, 100, 23);
		frame.getContentPane().add(btnLogout);
		
		lblWelcome = new JLabel("Welcome, " + professor.getUserName());
		lblWelcome.setBounds(180, 23, 200, 14);
		frame.getContentPane().add(lblWelcome);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new Back_Listener());
		btnBack.setBounds(10, 19, 89, 23);
		frame.getContentPane().add(btnBack);
		
		lblCourseCode = new JLabel("Course Code");
		lblCourseCode.setBounds(20, 60, 90, 14);
		frame.getContentPane().add(lblCourseCode);
		
		txtCourseCode = new JTextField();
		txtCourseCode.setEnabled(false);
		txtCourseCode.setBounds(120, 57, 140, 20);
		txtCourseCode.setText(course.getCourseCode());
		frame.getContentPane().add(txtCourseCode);
		
		lblCourseName = new JLabel("Course Name");
		lblCourseName.setBounds(20, 90, 90, 14);
		frame.getContentPane().add(lblCourseName);
		
		txtCourseName = new JTextField();
		txtCourseName.setEnabled(false);
		txtCourseName.setBounds(120, 87, 140, 20);
		txtCourseName.setText(course.getCourseName());
		frame.getContentPane().add(txtCourseName);
		
		lblExamTitle = new JLabel("Exam Title");
		lblExamTitle.setBounds(20, 120, 90, 14);
		frame.getContentPane().add(lblExamTitle);
		
		txtExamTitle = new JTextField();
		txtExamTitle.setEnabled(true);
		txtExamTitle.setBounds(120, 117, 140, 20);
		frame.getContentPane().add(txtExamTitle);
		
		lblMaxMarks = new JLabel("Max. Marks");
		lblMaxMarks.setBounds(20, 150, 90, 14);
		frame.getContentPane().add(lblMaxMarks);
		
		txtMaxMarks = new JTextField();
		txtMaxMarks.setEnabled(true);
		txtMaxMarks.setBounds(120, 147, 140, 20);
		frame.getContentPane().add(txtMaxMarks);
		
		lblDuration = new JLabel("Duration");
		lblDuration.setBounds(20, 180, 90, 14);
		frame.getContentPane().add(lblDuration);
		
		txtDuration = new JTextField();
		txtDuration.setEnabled(true);
		txtDuration.setBounds(120, 177, 140, 20);
		frame.getContentPane().add(txtDuration);
		
		lblStartTime = new JLabel("Start Time");
		lblStartTime.setBounds(20, 210, 90, 14);
		frame.getContentPane().add(lblStartTime);
		
		txtStartTime = new JTextField();
		txtStartTime.setEnabled(true);
		txtStartTime.setBounds(120, 207, 140, 20);
		frame.getContentPane().add(txtStartTime);
		
		lblEndTime = new JLabel("End Time");
		lblEndTime.setBounds(20, 240, 90, 14);
		frame.getContentPane().add(lblEndTime);
		
		txtEndTime = new JTextField();
		txtEndTime.setEnabled(true);
		txtEndTime.setBounds(120, 237, 140, 20);
		frame.getContentPane().add(txtEndTime);
		
		lblNumQusestions = new JLabel("No. of Questions");
		lblNumQusestions.setBounds(20, 270, 90, 14);
		frame.getContentPane().add(lblNumQusestions);
		
		txtNumQuestions = new JTextField();
		txtNumQuestions.setEnabled(true);
		txtNumQuestions.setBounds(120, 267, 140, 20);
		frame.getContentPane().add(txtNumQuestions);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new Submit_Listener());
		btnSubmit.setBounds(10, 300, 89, 23);
		frame.getContentPane().add(btnSubmit);
	}

	@Override
	public void show() {
		
		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	public void close() {
		
		frame.dispose();
	}

}
