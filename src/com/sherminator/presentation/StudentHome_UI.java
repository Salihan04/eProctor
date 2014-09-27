// Author: Soe Lynn


package com.sherminator.presentation;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import com.sherminator.businesslogic.CourseManager;
import com.sherminator.controller.StudentHomeController;
import com.sherminator.model.Course;
import com.sherminator.model.Student;
import com.sherminator.presentation.component.TableButtonCellEditor;
import com.sherminator.presentation.component.TableButtonCellRenderer;
import com.sherminator.presentation.tablemodel.CourseTableModel;
import com.sherminator.ui.TableWithButtons;

public class StudentHome_UI implements AbstractUI {

	private Student student;
	private List<Course> courses;
	private StudentHomeController studentHomeController;

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
			
			studentHomeController.gotoNextController();

			/*// Close the current UI
			close();

			// Show the next UI
			StudentCourseDetails_UI studentCourseDetails_UI = new StudentCourseDetails_UI(student, course);
			studentCourseDetails_UI.show();*/
			
			
		}

	}

	/*public static void main(String args[]){
		Student soelynn = new Student("soelynn", "sux", "U1220463J", "Soe Lynn", "u1220463j.png");
		CourseManager manager = new CourseManager();

		StudentHome_UI ui= new StudentHome_UI(soelynn, manager.getCoursesByStudent(soelynn));
		ui.show();
	}*/

	public StudentHome_UI(Student student, List<Course> courses, StudentHomeController studentHomeController) {
		this.student = student;
		this.courses = courses;
		this.studentHomeController = studentHomeController;

		initialize();		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 575, 280);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnLogout = new JButton("Logout");
		btnLogout.setBounds(460, 12, 100, 23);
		btnLogout.addActionListener(new Logout_Listener(this));
		frame.getContentPane().add(btnLogout);

		lblWelcome = new JLabel("Welcome, " + student.getUserName());
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
