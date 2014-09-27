/*
 * 
 * @author	Salihan
 * @version	1.0
 * 
 */
package com.sherminator.presentation;

import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.sherminator.businesslogic.ProctorManager;
import com.sherminator.controller.ProctorScheduleController;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;
import com.sherminator.presentation.tablemodel.ProctorScheduleTableModel;

public class ProctorSchedule_UI implements AbstractUI {
	
	private Proctor proctor;
	private List<Student> studentsByBookedDate;
	private List<Exam> examsByBookedDate;
	private List<Exam> examsAttendedByStudent;
	private Date currentDateTime_sql;
	private Date bookedDateTime1;
	private Date bookedDateTime2;
	private static ProctorScheduleController proctorScheduleController;

	private JFrame frame;
	private JLabel lblWelcome;
	private JPanel panel;
	private JScrollPane scroller;
	private JTable tblProctorSchedule;
	
	public ProctorSchedule_UI(Proctor proctor, ProctorScheduleController proctorScheduleController) {
		
		this.proctor = proctor;
		this.proctorScheduleController = proctorScheduleController;
		this.studentsByBookedDate = proctorScheduleController.getStudentsByBookedDate();
		this.examsByBookedDate = proctorScheduleController.getExamsByBookedDate();	
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 940, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tblProctorSchedule = new JTable();
		ProctorScheduleTableModel proctorScheduleTableModel = new ProctorScheduleTableModel(studentsByBookedDate, examsByBookedDate, tblProctorSchedule);
		tblProctorSchedule.setModel(proctorScheduleTableModel);
		tblProctorSchedule.setBounds(20, 20, 877 , 500);
		JScrollPane scroller = new JScrollPane(tblProctorSchedule, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(24, 20, 877 , 500);
		frame.getContentPane().add(scroller);

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
