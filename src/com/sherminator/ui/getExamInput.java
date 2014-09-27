package com.sherminator.ui;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sherminator.model.*;
import com.sherminator.dataaccess.*;

public class getExamInput //extends JOptionPane
{
	Course course;
	Exam exam;
	private Object info[]={course,exam};
    public getExamInput() throws ParseException{
    	JFrame frame = new JFrame();
    	JPanel panel = new JPanel();
    	
    	SqlDateModel model = new SqlDateModel();
		JDatePanelImpl dPanel =new JDatePanelImpl(model);
		JFormattedTextField.AbstractFormatter formatter = new DateComponentFormatter(); 
		JDatePickerImpl dPicker = new JDatePickerImpl(dPanel, formatter);
		frame.getContentPane().add(dPicker);
    	
		JTextField txtCoursecode;
		txtCoursecode = new JTextField();
		txtCoursecode.setText("CourseCode");
		panel.add(txtCoursecode);
		
		JTextField txtCoursetitle;
		txtCoursetitle = new JTextField();
		txtCoursetitle.setText("CourseTitle");
		panel.add(txtCoursetitle);
		
		JTextField txtExamID;
		txtExamID = new JTextField();
		txtExamID.setText("ExamID");
		panel.add(txtExamID);
		
		panel.setLayout(null);
		
		//JButton btnOk = new JButton("OK");
		//btnOk.setBounds(156, 264, 93, 23);
		//panel.add(btnOk);
		
		//Object[] message = {"CourseCode","CourseTile","NoOfStudent"};
		
		 //String str = JOptionPane.showInputDialog(frame, panel, 
				 //"Input", JOptionPane.OK_CANCEL_OPTION);
		Object[] message = {  
		    "Course Code: ", txtCoursecode,
		    "Course Title: ", txtCoursetitle,
		    "Exam ID: ", txtExamID,
		    "Start Time: ", dPicker
		};  
		int option = JOptionPane.showConfirmDialog(frame, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);  
		if (option == JOptionPane.OK_OPTION)  
		{  
		    String strOfCC = txtCoursecode.getText();  
		    String strOfCT = txtCoursetitle.getText();  
		    dPicker.getJFormattedTextField().commitEdit();
		    Object examDate = dPicker.getJFormattedTextField().getValue();//getValue()
		    int strOfEID = Integer.parseInt(txtExamID.getText());  
		    CourseDAO courseDao = new CourseDAO();
	    	if(strOfCT==courseDao.getCourseByCourseCode(strOfCC).getCourseName())
	    		course = courseDao.getCourseByCourseCode(strOfCC);
	    	ExamDAO examDao = new ExamDAO();
	    	if(strOfCC==examDao.getExamByExamID(strOfEID).getCourseCode())
	    		exam = examDao.getExamByExamID(strOfEID);
		} 
    }
  
public static void main(String args[]) throws ParseException{
		
	    getExamInput frame = new getExamInput();
	    
	} 
}
