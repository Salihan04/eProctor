package com.sherminator.ui;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Student;
import com.sherminator.presentation.AbstractUI;

public class StudentCourseDetails_UI implements AbstractUI{
	
	private Student student;
	private Course course;
	private List<Exam> exams;

	private JFrame frame;
	private JLabel lblCourse;
	private JLabel lblCourseCode;
	private JLabel lblCourseTitle;
	private JLabel lblWelcome;
	private DefaultTableModel dm;
	private JScrollPane scroller;
	private JButton btnLogout;
	private JButton btnBack;

	public StudentCourseDetails_UI(Student student, Course course, List<Exam> exams) {
		
		this.student = student;
		this.course = course;
		this.exams = exams;
		
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 395);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	/*public StudentCourseDetails_UI(){
		super();
		this.setTitle("Student Course Details");

		Object[][] data = {
				  {"Quiz 1", "02.02.2014 08:30am", "09.02.2014 05:30pm", "ENDED", "View Results"},
				  {"Quiz 2", "12.02.2014 08:30am", "19.02.2014 05:30pm", "AVAILABLE", "Book Exam"}
		  };

		  Object [] colName= { "Exam Title", "Start Time", "End Time", "Status", "Option"};

			JLabel lblNewLabel = new JLabel("Course Code:");
			lblNewLabel.setBounds(59, 60, 76, 14);
			this.getContentPane().add(lblNewLabel);

			JLabel lblCourseCode = new JLabel("CZ2005");
			lblCourseCode.setBounds(145, 57, 86, 20);
			this.getContentPane().add(lblCourseCode);

			JLabel label = new JLabel("Course Title:");
			label.setBounds(59, 86, 76, 14);
			this.getContentPane().add(label);

			JLabel lblCourseTitle = new JLabel("Operating System");
			lblCourseTitle.setBounds(145, 83, 200, 20);
			this.getContentPane().add(lblCourseTitle);

		    DefaultTableModel dm = new DefaultTableModel(data, colName);
		    TableWithButtons table = new TableWithButtons(dm);
		    table.setBounds(59, 158, 900 , 200);
		  	table.setColumnButton("Option");

			JScrollPane scroller = new JScrollPane(table); 
			getContentPane().add(scroller); 
			scroller.setBounds(59, 150, 900 , 200);

			JLabel lblWelcomeLynnnerd = new JLabel("Welcome, LynnNerd");
			lblWelcomeLynnnerd.setBounds(750, 23, 200, 14);
			getContentPane().add(lblWelcomeLynnnerd);

			JButton btnLogout = new JButton("Logout");
			btnLogout.setBounds(889, 19, 100, 23);
			getContentPane().add(btnLogout);

			getContentPane().setLayout(null);

			JButton button = new JButton("< Back");
			button.setBounds(59, 25, 89, 23);
			getContentPane().add(button);
			//this.pack();
		    setSize(1024, 768);// window size
		    setVisible(true);
	}*/

	/*public static void main(String args[]){
		StudentCourseDetails_UI frame= new StudentCourseDetails_UI();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}*/

}
