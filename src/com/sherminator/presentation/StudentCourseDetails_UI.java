/*
 * 
 * @author	Gou Tong
 * @author	Huiting
 * @version	1.0
 * 
 */
package com.sherminator.presentation;

import javax.swing.JScrollPane;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sherminator.businesslogic.CourseManager;
import com.sherminator.businesslogic.ExamManager;
import com.sherminator.controller.StudentCourseDetailController;
import com.sherminator.controller.StudentExamDetailsController;
import com.sherminator.controller.StudentHomeController;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Student;
import com.sherminator.presentation.component.TableButtonCellEditor;
import com.sherminator.presentation.component.TableButtonCellRenderer;
import com.sherminator.presentation.tablemodel.CourseTableModel;
import com.sherminator.presentation.tablemodel.ExamTableModel;

public class StudentCourseDetails_UI implements AbstractUI{
	
	private Student student;
	private Course course;
	private List<Exam> exams;

	private JFrame frame;
	private JLabel lblCourseCode;
	private JLabel lblCourseTitle;
	private JLabel lblWelcome;
	private JTextField txtCourseCode;
	private JTextField txtCourseName;
	private JTable tblExams;
	private JButton btnLogout;
	private JButton btnBack;
	
	private StudentCourseDetailController studCDCtrl;
	
	private class ExamTableExamTitleButton_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnExamTitle = (JButton)e.getSource();
			JTable table = (JTable)btnExamTitle.getParent();
			ExamTableModel examTableModel = (ExamTableModel) table.getModel();
			Exam exam = examTableModel.getValueAt(table.getSelectedRow());
			
			//getSelectedExam will get exam chosen by student and controller will transit to next UI 
			studCDCtrl.getSelectedExam(exam);
		}		
		
	}
	
	private class Back_Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//close();
			studCDCtrl.back();
		}
	}
	
	public StudentCourseDetails_UI(Student student, Course course, StudentCourseDetailController studCDCtrl) {
		
		this.student = student;
		this.course = course;
		this.studCDCtrl= studCDCtrl;
		this.exams= this.studCDCtrl.getExamDetails();
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 940, 370);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblCourseCode = new JLabel("Course Code:");
		lblCourseCode.setBounds(20, 60, 90, 14);
		frame.getContentPane().add(lblCourseCode);

		txtCourseCode = new JTextField();
		txtCourseCode.setEnabled(false);
		txtCourseCode.setBounds(111, 57, 170, 20);
		frame.getContentPane().add(txtCourseCode);

		lblCourseTitle = new JLabel("Course Title:");
		lblCourseTitle.setBounds(20, 96, 80, 14);
		frame.getContentPane().add(lblCourseTitle);

		txtCourseName = new JTextField();
		txtCourseName.setEnabled(false);
		txtCourseName.setBounds(111, 93, 170, 20);
		frame.getContentPane().add(txtCourseName);
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new Logout_Listener(this));
		btnLogout.setBounds(825, 19, 100, 23);
		frame.getContentPane().add(btnLogout);
		
		lblWelcome = new JLabel("Welcome, " + student.getUserName());
		lblWelcome.setBounds(700, 23, 200, 14);
		frame.getContentPane().add(lblWelcome);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new Back_Listener());
		btnBack.setBounds(10, 19, 89, 23);
		frame.getContentPane().add(btnBack);
		
		tblExams = new JTable();
		ExamTableModel examTableModel = new ExamTableModel(exams, tblExams);
		tblExams.setModel(examTableModel);
		tblExams.setBounds(20, 130, 900 , 200);
		JScrollPane scroller = new JScrollPane(tblExams);
		scroller.setBounds(20, 130, 900 , 200);
		frame.getContentPane().add(scroller);
		
		TableColumn examTitleColumn = tblExams.getColumn("Exam Title");
		examTitleColumn.setCellRenderer(new TableButtonCellRenderer());
		examTitleColumn.setCellEditor(new TableButtonCellEditor(new ExamTableExamTitleButton_Listener()));
				
		updateCourseData();
	}
	
	private void updateCourseData() {
		txtCourseCode.setText(course.getCourseCode());
		txtCourseName.setText(course.getCourseName());
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