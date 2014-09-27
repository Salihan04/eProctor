/*
 * 
 * @author	Gou Tong
 * @author	Huiting
 * @version	1.0
 * 
 */
package com.sherminator.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sherminator.businesslogic.CourseManager;
import com.sherminator.businesslogic.ExamManager;
import com.sherminator.controller.AddExamController;
import com.sherminator.controller.ProfCourseDetailController;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;
import com.sherminator.model.Question;
import com.sherminator.model.Student;
import com.sherminator.presentation.component.TableButtonCellEditor;
import com.sherminator.presentation.component.TableButtonCellRenderer;
import com.sherminator.presentation.tablemodel.ExamTableModel;
import com.sherminator.presentation.tablemodel.QuestionTableModel;

public class ProfessorCourseDetails_UI implements AbstractUI{
	
	private Professor professor;
	private Course course;
	private List<Exam> exams;
	
	private ProfCourseDetailController profCourseDetailController;

	private JFrame frame;
	private JLabel lblCourseCode;
	private JLabel lblCourseTitle;
	private JLabel lblWelcome;
	private JTextField txtCourseCode;
	private JTextField txtCourseName;
	private JTable tblExams;
	private JButton btnLogout;
	private JButton btnBack;
	private JButton btnAddExam;
	
	private class ExamTableExamTitleButton_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnExamTitle = (JButton)e.getSource();
			JTable table = (JTable)btnExamTitle.getParent();
			ExamTableModel examTableModel = (ExamTableModel) table.getModel();
			Exam exam = examTableModel.getValueAt(table.getSelectedRow());
			
			profCourseDetailController.getSelectedExam(exam);
			// Close current UI
/*			close();
			
			// Show the next UI
			ProfessorExamDetails_UI professorExamDetails_UI = new ProfessorExamDetails_UI(professor, course, exam);
			professorExamDetails_UI.show();*/
		}		
		
	}
	
	private class AddExam_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			profCourseDetailController.gotoAddExamController();			
		}
		
	}
	
	private class ExamTableEditButton_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton btnExamTitle = (JButton)e.getSource();
			JTable table = (JTable)btnExamTitle.getParent();
			ExamTableModel examTableModel = (ExamTableModel) table.getModel();
			Exam exam = examTableModel.getValueAt(table.getSelectedRow());
			
			profCourseDetailController.gotoEditExamController(exam);
		}

	}
	
	private class Back_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CourseManager courseManager = new CourseManager();
			
			profCourseDetailController.gotoPreviousController();
		}
		
	}
	
	public ProfessorCourseDetails_UI(Professor professor,List<Exam> exams, Course course, ProfCourseDetailController profCourseDetailController){
		this.professor = professor;
		this.course = course;
		this.profCourseDetailController = profCourseDetailController;
		this.exams = exams;
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
		
		lblWelcome = new JLabel("Welcome, " + professor.getUserName());
		lblWelcome.setBounds(700, 23, 200, 14);
		frame.getContentPane().add(lblWelcome);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new Back_Listener());
		btnBack.setBounds(10, 19, 89, 23);
		frame.getContentPane().add(btnBack);
		
		btnAddExam = new JButton("Add Exam");
		btnAddExam.addActionListener(new AddExam_Listener());
		btnAddExam.setBounds(10, 130, 100, 22);
		frame.add(btnAddExam);
		
		tblExams = new JTable();
		ExamTableModel examTableModel = new ExamTableModel(exams, tblExams);
		tblExams.setModel(examTableModel);
		tblExams.setBounds(20, 150, 900 , 200);
		JScrollPane scroller = new JScrollPane(tblExams);
		scroller.setBounds(20, 170, 900 , 200);
		frame.getContentPane().add(scroller);
		
		TableColumn examTitleColumn = tblExams.getColumn("Exam Title");
		examTitleColumn.setCellRenderer(new TableButtonCellRenderer());
		examTitleColumn.setCellEditor(new TableButtonCellEditor(new ExamTableExamTitleButton_Listener()));
		
		TableColumn removeColumn = new TableColumn();
		removeColumn.setHeaderValue("");
		removeColumn.setModelIndex(tblExams.getColumnCount());
		removeColumn.setCellRenderer(new TableButtonCellRenderer("Edit"));
		removeColumn.setCellEditor(new TableButtonCellEditor("Edit", new ExamTableEditButton_Listener()));
		tblExams.addColumn(removeColumn);		
		
		System.out.println(tblExams.getColumnCount());
		System.out.println(removeColumn.getModelIndex());
		
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