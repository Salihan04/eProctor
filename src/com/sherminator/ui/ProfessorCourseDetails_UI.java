package com.sherminator.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sherminator.model.Course;

public class ProfessorCourseDetails_UI extends JFrame{
	public ProfessorCourseDetails_UI(ArrayList<Course> courseList){//need to pass in exam details as well
		super();
		this.setTitle("Professor's course details");
		/*
		  Object[][] data = new Object[1][7]; //no. of rows=2= no of exam title records
		  
		  for(int i=0;i<courseList.size();i++){
			  Course course = courseList.get(i);
			  data[i][0] = course.getCourseCode();
			  data[i][1] = course.getCourseName();
			  data[i][2] = "100";
		  }
		  */
		  Object [][]data = {
				  {"Quiz 1", "12.02.2014 08:30 a.m.", "19.02.2014 05:30 p.m.", "40/50", "98/100", "Edit", "View Results"}
		  
		  };
		  
		  Object [] colName= { "Exam Title", "Start Time", "End Time", "Passing Marks", "Participants", 
				  "Edit Exam Details", "View Result for Exam"};
		  
		    DefaultTableModel dm = new DefaultTableModel(data, colName);
		    TableWithButtons table = new TableWithButtons(dm);
		    table.setBounds(59, 158, 900 , 200);
		  	table.setColumnButton("Edit Exam Details");
		  	table.setColumnButton("View Result for Exam");
		  	
			JScrollPane scroller = new JScrollPane(table); 
			this.add(scroller); 
			scroller.setBounds(59, 150, 900 , 200);

			//objects in table
			JButton button = new JButton("< Back");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			button.setBounds(59, 26, 89, 23);
			this.getContentPane().add(button);
			
			JLabel lblWelcomesherminator = new JLabel("Welcome,Sherminator");
			lblWelcomesherminator.setBounds(700, 30, 150, 14);
			this.getContentPane().add(lblWelcomesherminator);
			
			JButton btnLogOut = new JButton("Logout");
			btnLogOut.setBounds(850, 26, 89, 23);
			this.getContentPane().add(btnLogOut);
			
			JLabel lblNewLabel = new JLabel("Course Code:");
			lblNewLabel.setBounds(59, 60, 76, 14);
			this.getContentPane().add(lblNewLabel);
			
			JLabel label = new JLabel("Course Title:");
			label.setBounds(59, 86, 76, 14);
			this.getContentPane().add(label);
			
			JLabel label_1 = new JLabel("No of Students:");
			label_1.setBounds(59, 114, 76, 14);
			this.getContentPane().add(label_1);
			
			JTextField txtCoursecode;
			
			txtCoursecode = new JTextField();
			txtCoursecode.setText("CourseCode");
			txtCoursecode.setBounds(145, 57, 86, 20);
			this.getContentPane().add(txtCoursecode);
			txtCoursecode.setColumns(10);
			
			JTextField txtCoursetitle;
			txtCoursetitle = new JTextField();
			txtCoursetitle.setText("CourseTitle");
			txtCoursetitle.setBounds(145, 83, 86, 20);
			this.getContentPane().add(txtCoursetitle);
			txtCoursetitle.setColumns(10);
			
			JTextField txtNoofstudents;
			txtNoofstudents = new JTextField();
			txtNoofstudents.setText("NoOfStudents");
			txtNoofstudents.setBounds(145, 111, 86, 20);
			this.getContentPane().add(txtNoofstudents);
			txtNoofstudents.setColumns(10);
			
			JButton btnEdit = new JButton("Edit");
			btnEdit.setBounds(241, 56, 89, 23);
			this.getContentPane().add(btnEdit);
			
			JButton button_1 = new JButton("Edit");
			button_1.setBounds(241, 82, 89, 23);
			this.getContentPane().add(button_1);
			
			JButton button_2 = new JButton("Edit");
			button_2.setBounds(241, 110, 89, 23);
			this.getContentPane().add(button_2);
			
			//
			this.setLayout(null);
			//this.pack();
		    setSize(1024, 400);// window size
		    setVisible(true);
	}
	
	public static void main(String args[]){
		
		ArrayList<Course> courses = new ArrayList<Course>();
		courses.add(new Course("CZ1005", "Digital Logic"));
		
	    ProfessorCourseDetails_UI frame= new ProfessorCourseDetails_UI(courses);
	    frame.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        System.exit(0);
	      }
	    });
	    
	  }
}

