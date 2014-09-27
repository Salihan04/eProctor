package com.sherminator.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

public class ProfessorExamDetail extends JFrame{
	private JTable table1;
	private JTable table2;
	private JTable table3;
	public ProfessorExamDetail(){
	  super();
	  this.setTitle("Professor Exam Detail");
	  
	  Object[][] data1 = {
			  {"Why chicken cross the road", "1) To buy an egg", "1) To buy an egg 2)The chicken is bored 3)None of the above"},
			  {"Why chicken cross the road", "1) To buy an egg", "1) To buy an egg 2)The chicken is bored 3)None of the above"}
	  };
	  Object [] colName1= { "Question", "Correct Answer", "Choices","Edit","Delete"};
	  Object[][] data2={
			  {"U1220463J","Soe Lynn"},
			  {"U1221211K","Nasruddin"}
	  };
	  Object [] colName2= { "Matric NO", "Student Name", "Remove"};
	  Object[][] data3={
			  {"U1220463J","Soe Lynn","90/100"},
			  {"U1221211K","Nasruddin","95/100"}
	  };
	  Object [] colName3= { "Matric NO", "Student Name", "Marks"};
	   
	    DefaultTableModel dm1 = new DefaultTableModel(data1, colName1);
	    TableWithButtons table1 = new TableWithButtons(dm1);
	    table1.setSize(2000, 2000);
	  	table1.setColumnButton("Edit");
	  	table1.setColumnButton("Delete");
	  	table1.getColumnModel().getColumn(0).setPreferredWidth(200);
	  	table1.getColumnModel().getColumn(1).setPreferredWidth(200);
	  	table1.getColumnModel().getColumn(2).setPreferredWidth(200);
	  	table1.getColumnModel().getColumn(3).setPreferredWidth(90);
	  	table1.getColumnModel().getColumn(4).setPreferredWidth(90);
	  	table1.getColumnModel().getColumn(0).setCellRenderer(
	  	      new TextAreaRenderer());
	  	table1.getColumnModel().getColumn(1).setCellRenderer(
		  	      new TextAreaRenderer());
	  	table1.getColumnModel().getColumn(2).setCellRenderer(
		  	      new TextAreaRenderer());
	  	table1.setRowHeight(0, 100);
	    table1.setRowHeight(1, 100);
	  	table1.getModel().setValueAt(data1[0][0], 0, 0);
	  	table1.getModel().setValueAt(data1[0][1], 0, 1);
	  	table1.getModel().setValueAt(data1[0][2], 0, 2);
	  	table1.getModel().setValueAt(data1[1][0], 0, 0);
	  	table1.getModel().setValueAt(data1[1][1], 0, 1);
	  	table1.getModel().setValueAt(data1[1][2], 0, 2);
	  	
	  	DefaultTableModel dm2 = new DefaultTableModel(data2, colName2);
	    TableWithButtons table2 = new TableWithButtons(dm2);
	    table2.setSize(2000,2000);
	    table2.setColumnButton("Remove");
	    table2.getColumnModel().getColumn(0).setPreferredWidth(200);
	  	table2.getColumnModel().getColumn(1).setPreferredWidth(200);
	  	table2.getColumnModel().getColumn(2).setPreferredWidth(90);
	  	
	  	DefaultTableModel dm3 = new DefaultTableModel(data3, colName3);
	    TableWithButtons table3 = new TableWithButtons(dm3);
	    table3.setSize(2000,2000);
	    table3.getColumnModel().getColumn(0).setPreferredWidth(200);
	  	table3.getColumnModel().getColumn(1).setPreferredWidth(200);
	  	table3.getColumnModel().getColumn(2).setPreferredWidth(150);

	    JScrollPane scroller1 = new JScrollPane(table1);
	    scroller1.setSize(2000,2000);
	    JScrollPane scroller2 = new JScrollPane(table2);
	    scroller1.setSize(2000,2000);
	    JScrollPane scroller3 = new JScrollPane(table3);
	    scroller1.setSize(2000,2000);
	  	JTabbedPane jtp = new JTabbedPane();
		getContentPane().add(jtp);
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();
        jp1.add(label1);
        jp2.add(label2);
        jp2.add(label3);
        //add these three tables in the three tabs;
        jp1.add(scroller1);
        jp2.add(scroller2);
        jp3.add(scroller3);
        jtp.addTab("Question", jp1);
        jtp.addTab("Student", jp2);
        jtp.addTab("Result", jp3);
        
        jp1.setLayout(new FlowLayout());
        jp1.setSize(new Dimension(2000,2000));
        jp2.setLayout(new FlowLayout());
        jp1.setSize(new Dimension(2000,2000));
        jp3.setLayout(new FlowLayout());
        jp1.setSize(new Dimension(2000,2000));
        
        JScrollPane scroller = new JScrollPane(jtp); 
		this.add(scroller); 
		scroller.setBounds(59, 150, 900 , 500);
		
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
		
	    JLabel lbExamTitle = new JLabel("Exam Title:");
		lbExamTitle.setBounds(59, 60, 100, 14);
		this.getContentPane().add(lbExamTitle);
		
		JLabel lbExamTitle1 = new JLabel("Quiz1");
		lbExamTitle1.setBounds(180, 57, 86, 20);
		this.getContentPane().add(lbExamTitle1);
		
		JLabel lbCourseCode = new JLabel("Course Code:");
		lbCourseCode.setBounds(59, 86, 100, 14);
		this.getContentPane().add(lbCourseCode);
		
		JLabel lbCourseCode1 = new JLabel("CZ2005");
		lbCourseCode1.setBounds(180, 83, 200, 20);
		this.getContentPane().add(lbCourseCode1);
		
		JLabel lbStartTime = new JLabel("Exam Start Time:");
		lbStartTime.setBounds(59, 112, 100, 14);
		this.getContentPane().add(lbStartTime);
		
		JLabel lbStartTime1 = new JLabel("02/12/2013");
		lbStartTime1.setBounds(180, 109, 314, 20);
		this.getContentPane().add(lbStartTime1);
		
		JLabel lbEndTime = new JLabel("Exam End Time:");
		lbEndTime.setBounds(59, 138, 100, 14);
		this.getContentPane().add(lbEndTime);
		
		JLabel lbEndTime1 = new JLabel("02/12/2013");
		lbEndTime1.setBounds(180, 135, 428, 20);
		this.getContentPane().add(lbEndTime1);
		
		JLabel lbNull = new JLabel("");
		lbNull.setBounds(180, 135, 428, 20);
		this.getContentPane().add(lbNull);
		
	}
	
	
	public static void main(String args[]){
	    ProfessorExamDetail frame= new ProfessorExamDetail();
	    frame.setVisible(true);
	    frame.pack();
	    frame.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	    	  System.out.println("Hi");
	        System.exit(0);
	      }
	    });
	    
	  }

}