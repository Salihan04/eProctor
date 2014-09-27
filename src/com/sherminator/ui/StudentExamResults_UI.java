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

//actually this table don't need TableWithButtons class but I just follow format for other UIs
public class StudentExamResults_UI extends JFrame{
	public StudentExamResults_UI(){
		super();
		this.setTitle("Student View Results");
		
		  Object [][]data = {
				  {"Quiz 1", "02.02.2014 08:30 a.m.", "09.02.2014 05:30 p.m.", "ENDED", "90/100"},
				  {"Quiz 2", "09.02.2014 08:30 a.m.", "19.02.2014 05:30 p.m.", "AVAILABLE", "-"}
		  };
		  
		  Object [] colName= { "Exam Title", "Start Time", "End Time", "Status", "Marks" };
		    DefaultTableModel dm = new DefaultTableModel(data, colName);
		    TableWithButtons table = new TableWithButtons(dm);
		    table.setBounds(59, 158, 900 , 200);
		  	
			JScrollPane scroller = new JScrollPane(table); 
			getContentPane().add(scroller); 
			scroller.setBounds(59, 150, 900 , 200);
		
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
			
			getContentPane().setLayout(null);
			
			JButton button = new JButton("Logout");
			button.setBounds(859, 27, 100, 23);
			getContentPane().add(button);
			
			JButton button_1 = new JButton("< Back");
			button_1.setBounds(59, 24, 89, 23);
			getContentPane().add(button_1);
			
			JLabel label_1 = new JLabel("Welcome, LynnNerd");
			label_1.setBounds(716, 30, 200, 14);
			getContentPane().add(label_1);
			//this.pack();
		    setSize(1024, 768);// window size
		    setVisible(true);
	}
	
	public static void main(String args[]){
	    StudentExamResults_UI frame= new StudentExamResults_UI();
	    frame.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        System.exit(0);
	      }
	    });
	    
	  }
}
