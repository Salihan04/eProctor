/*
 * 
 * @author	Gou Tong
 * @author	Huiting
 * @version	1.0
 * 
 */
package com.sherminator.presentation;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sherminator.businesslogic.CourseManager;
import com.sherminator.businesslogic.ProfessorManager;
import com.sherminator.controller.ProfHomeController;
import com.sherminator.model.Course;
import com.sherminator.model.Professor;
import com.sherminator.model.Student;
//import com.sherminator.presentation.ProfessorHome_UI.CourseTableCourseNameButton_Listener;
import com.sherminator.presentation.component.TableButtonCellEditor;
import com.sherminator.presentation.component.TableButtonCellRenderer;
import com.sherminator.presentation.tablemodel.CourseTableModel;

public class ProfessorHome_UI implements AbstractUI{

	private Professor professor;
	private List<Course> courses;
	
	private ProfHomeController profHomeController;

	private JFrame frame;
	private JButton btnLogout;
	private JLabel lblWelcome;
	private JTable tblCourse;

	private class CourseTableCourseNameButton_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnCourseName = (JButton)e.getSource();
			JTable table = (JTable)btnCourseName.getParent();
			CourseTableModel courseTableModel = (CourseTableModel) table.getModel();
			Course course = courseTableModel.getValueAt(table.getSelectedRow());
			
			//go to next UI for a particular Course
			profHomeController.getSelectedCourse(course);
		}

	}

	public ProfessorHome_UI(Professor professor,List<Course> courses,ProfHomeController profHomeController){
		this.professor= professor;
		this.courses= courses;
		this.profHomeController = profHomeController;
		
		initialize();
	}

	private void initialize(){
		frame = new JFrame();
		frame.setBounds(100, 100, 575, 280);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);


		btnLogout = new JButton("Logout");
		btnLogout.setBounds(460, 12, 100, 23);
		btnLogout.addActionListener(new Logout_Listener(this));
		frame.getContentPane().add(btnLogout);

		lblWelcome = new JLabel("Welcome, " + professor.getUserName());
		lblWelcome.setBounds(320, 15, 220, 14);
		frame.getContentPane().add(lblWelcome);

		tblCourse = new JTable();
		CourseTableModel courseTableModel = new CourseTableModel(courses, tblCourse);
		tblCourse.setModel(courseTableModel);
		tblCourse.setBounds(20, 45, 535, 200);
		JScrollPane scroller = new JScrollPane(tblCourse);
		scroller.setBounds(20, 45, 535, 200);
		frame.getContentPane().add(scroller);

		TableColumn courseNameColumn = tblCourse.getColumn("Course Name");
		courseNameColumn.setCellRenderer(new TableButtonCellRenderer());
		courseNameColumn.setCellEditor(new TableButtonCellEditor(new CourseTableCourseNameButton_Listener()));
	}

	public void close(){
		frame.dispose();
	}

	public void show() {
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
