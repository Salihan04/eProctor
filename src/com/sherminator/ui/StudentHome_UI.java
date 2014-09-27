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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.sherminator.model.Course;

public class StudentHome_UI extends JFrame{
	public StudentHome_UI(){
		super();
		this.setTitle("Student Home");
		
		//NEEDS newline renderer OR use JList renderer
		String errorStr= new String("[PLEASE PICK A DATE TO TAKE EXAM]");
		
		  Object[][] data = {
				  {"CZ2005", "Operating Systems", "Quiz 1: 90/100Quiz 2:"+errorStr},
				  {"CZ2006", "Software Engineering", "Quiz 1:"+errorStr}
		  };
		  
		  Object [] colName= { "Course Code", "Course Title", "Exams"};
		  
		    DefaultTableModel dm = new DefaultTableModel(data, colName);
		    TableWithButtons table = new TableWithButtons(dm);
		    table.setBounds(59, 158, 900 , 200);
		  	table.setColumnButton("Course Title");
			
			JScrollPane scroller = new JScrollPane(table); 
			this.add(scroller); 
			scroller.setBounds(59, 150, 900 , 200);
			
			JLabel lblWelcomeLynnnerd = new JLabel("Welcome, LynnNerd");
			lblWelcomeLynnnerd.setBounds(750, 23, 200, 14);
			this.add(lblWelcomeLynnnerd);
			
			JButton btnLogout = new JButton("Logout");
			btnLogout.setBounds(889, 19, 100, 23);
			this.add(btnLogout);
			
			this.setLayout(null);
			//this.pack();
		    setSize(1024, 768);// window size
		    setVisible(true);
	}
	
	public static void main(String args[]){
	    StudentHome_UI frame= new StudentHome_UI();
	    frame.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        System.exit(0);
	      }
	    });
	    
	  }
	
}
