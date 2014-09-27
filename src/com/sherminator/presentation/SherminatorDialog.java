package com.sherminator.presentation;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.sherminator.model.Student;
import com.sherminator.presentation.comboboxmodel.DateComboBoxModel;
import com.sherminator.presentation.comboboxmodel.TimeComboBoxModel;

public class SherminatorDialog {
	
	public static Student showStudentsDialog(String title, List<Student> students) {
		Student student_return;
		
		JComboBox cboStudents = new JComboBox(students.toArray());
		
		int choice = JOptionPane.showConfirmDialog(null, cboStudents, title, JOptionPane.OK_CANCEL_OPTION);
		
		switch(choice) {
		case JOptionPane.OK_OPTION:
			student_return = (Student) cboStudents.getSelectedItem();
			break;
		default:
			student_return = null;
		}
				
		return student_return;	
	}
	
	public static Date showDateTimeDialog(String title, Date startDate, Date endDate, int startTime, int endTime) {
		Date date;
		
		DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm a");
		
		JLabel lblDate = new JLabel("Date");
		JLabel lblTime = new JLabel("Time");
		JComboBox cboDate = new JComboBox(new DateComboBoxModel(startDate, endDate));
		JComboBox cboTime = new JComboBox(new TimeComboBoxModel(startTime, endTime));
		
		int choice = JOptionPane.showConfirmDialog(null, new JComponent[] { lblDate, cboDate, lblTime, cboTime}, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		switch(choice) {
		case JOptionPane.OK_OPTION:
			String date_str = cboDate.getSelectedItem().toString();
			String time_str = cboTime.getSelectedItem().toString();
			java.util.Date utilDate;
			try {
				utilDate = dateFormat.parse(date_str + " " + time_str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				utilDate = null;
			}
			
			date = new Date(utilDate.getTime());
			break;
		default:
			date = null;
		}
		
		return date;
	}

//	public static Question showQuestionDialog(String title) {
//		return showQuestionDialog(title);
//	}
//	
//	public static Question showQuestionDialog(String title, Question question) {
//		Question question_return;
//		
//		JPanel panel = new JPanel();
//		JLabel lblQuestion = new JLabel("Question");
//		JLabel lblCorrectAnswer = new JLabel("Answer");
//		JTextField txtQuestion = new JTextField();
//		JComboBox cboAnswer = new JComboBox(question.getAnswers().toArray());
//		
//		if(question != null) {
//			txtQuestion.setTe
//		}
//		
//		return question_return;
//	}

}
