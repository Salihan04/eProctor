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
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sherminator.businesslogic.CourseManager;
import com.sherminator.businesslogic.ExamManager;
import com.sherminator.businesslogic.InvigilationSessionManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.communication.CommunicationListener;
import com.sherminator.communication.TCPCommunicationChannel_Client;
import com.sherminator.config.Configuration;
import com.sherminator.controller.StudentExamDetailsController;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.InvigilationSession;
import com.sherminator.model.Student;
import com.sherminator.presentation.component.SherminatorDialog;

public class StudentExamDetails_UI implements AbstractUI {

	private Student student;
	private Course course;
	private Exam exam;
	private Date bookedDateTime;
	
	private StudentExamDetailsController controller;

	private JFrame frame;
	private JLabel lblWelcome;
	private JButton btnLogout;
	private JButton btnBack;
	private JButton btnTakeExam;
	private JButton btnBookExam;
	private JButton btnCancelBooking;
	private JLabel lblCourseCode;
	private JLabel lblCourseName;
	private JLabel lblExamTitle;
	private JLabel lblMaxMarks;
	private JLabel lblDuration;
	private JLabel lblStartTime;
	private JLabel lblEndTime;
	private JTextField txtCourseCode;
	private JTextField txtCourseName;
	private JTextField txtExamTitle;
	private JTextField txtMaxMarks;
	private JTextField txtDuration;
	private JTextField txtStartTime;
	private JTextField txtEndTime;

	private class CancelBookingButton_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel booking?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				controller.studentCancelBooking(student, exam);
				
				refreshUI();
				
				JOptionPane.showMessageDialog(null, "Booking has been cancelled.");
			}
		}
		
	}
	
	private class BookExamButton_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Date date = SherminatorDialog.showDateTimeDialog("Choose exam date and time", exam.getStartDateTime(), exam.getEndDateTime(), Configuration.START_TIME, Configuration.END_TIME);

			if(date != null) {
				boolean isSuccessfullyBooked = controller.studentBookExam(student, exam, date);

				if(isSuccessfullyBooked) {
					JOptionPane.showMessageDialog(null, "Exam has been booked successfully.", "", JOptionPane.INFORMATION_MESSAGE);
					refreshUI();
				}
				else {
					JOptionPane.showMessageDialog(null, "Something went terribly wrong. Please try again later.", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private class TakeExamButton_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			InvigilationSession session = controller.getAvailableInvigilator();

			if(session == null) {
				JOptionPane.showMessageDialog(null, "There is no proctor available at the moment. Please try again later.");
			}
			else {
				controller.startExam();
			}

		}

	}

	private class Back_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.back();
		}

	}

	public StudentExamDetails_UI(Student student, Course course, Exam exam, StudentExamDetailsController controller) {
		this.student = student;
		this.course = course;
		this.exam = exam;
		this.controller = controller;

		bookedDateTime = controller.getExamBookingDateTimeByStudent(exam, student);

		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 420, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new Logout_Listener(this));
		btnLogout.setBounds(310, 19, 100, 23);
		frame.getContentPane().add(btnLogout);

		lblWelcome = new JLabel("Welcome, " + student.getUserName());
		lblWelcome.setBounds(180, 23, 200, 14);
		frame.getContentPane().add(lblWelcome);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new Back_Listener());
		btnBack.setBounds(10, 19, 89, 23);
		frame.getContentPane().add(btnBack);

		if(bookedDateTime == null) { // Exam is not booked. So, show BookExam Button
			btnBookExam = new JButton("Book Exam");
			btnBookExam.setBounds(290, 60, 100, 100);
			btnBookExam.addActionListener(new BookExamButton_Listener());
			frame.getContentPane().add(btnBookExam);
		} else { // Exam is booked. So, show TakeExam Button
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Calendar calendar = Calendar.getInstance();
			Date currentDate = new java.sql.Date(calendar.getTime().getTime());
			
			// Checking whether the booked date time is today
			if(sdf.format(currentDate).equals(sdf.format(bookedDateTime))) {
				// Today is the booked date
				btnTakeExam = new JButton("Take Exam");
				btnTakeExam.setBounds(290, 60, 100, 100);
				btnTakeExam.addActionListener(new TakeExamButton_Listener());
				frame.getContentPane().add(btnTakeExam);
			}
			else {
				btnCancelBooking = new JButton("<html>Cancel<br/>Booking</html>");
				btnCancelBooking.setBounds(290, 60, 100, 100);
				btnCancelBooking.addActionListener(new CancelBookingButton_Listener());
				frame.getContentPane().add(btnCancelBooking);
			}
		}

		lblCourseCode = new JLabel("Course Code");
		lblCourseCode.setBounds(20, 60, 90, 14);
		frame.getContentPane().add(lblCourseCode);

		txtCourseCode = new JTextField();
		txtCourseCode.setEnabled(false);
		txtCourseCode.setBounds(120, 57, 140, 20);
		frame.getContentPane().add(txtCourseCode);

		lblCourseName = new JLabel("Course Name");
		lblCourseName.setBounds(20, 90, 90, 14);
		frame.getContentPane().add(lblCourseName);

		txtCourseName = new JTextField();
		txtCourseName.setEnabled(false);
		txtCourseName.setBounds(120, 87, 140, 20);
		frame.getContentPane().add(txtCourseName);

		lblExamTitle = new JLabel("Exam Title");
		lblExamTitle.setBounds(20, 120, 90, 14);
		frame.getContentPane().add(lblExamTitle);

		txtExamTitle = new JTextField();
		txtExamTitle.setEnabled(false);
		txtExamTitle.setBounds(120, 117, 140, 20);
		frame.getContentPane().add(txtExamTitle);

		lblMaxMarks = new JLabel("Max. Marks");
		lblMaxMarks.setBounds(20, 150, 90, 14);
		frame.getContentPane().add(lblMaxMarks);

		txtMaxMarks = new JTextField();
		txtMaxMarks.setEnabled(false);
		txtMaxMarks.setBounds(120, 147, 140, 20);
		frame.getContentPane().add(txtMaxMarks);

		lblDuration = new JLabel("Duration");
		lblDuration.setBounds(20, 180, 90, 14);
		frame.getContentPane().add(lblDuration);

		txtDuration = new JTextField();
		txtDuration.setEnabled(false);
		txtDuration.setBounds(120, 177, 140, 20);
		frame.getContentPane().add(txtDuration);

		lblStartTime = new JLabel("Start Time");
		lblStartTime.setBounds(20, 210, 90, 14);
		frame.getContentPane().add(lblStartTime);

		txtStartTime = new JTextField();
		txtStartTime.setEnabled(false);
		txtStartTime.setBounds(120, 207, 140, 20);
		frame.getContentPane().add(txtStartTime);

		lblEndTime = new JLabel("End Time");
		lblEndTime.setBounds(20, 240, 90, 14);
		frame.getContentPane().add(lblEndTime);

		txtEndTime = new JTextField();
		txtEndTime.setEnabled(false);
		txtEndTime.setBounds(120, 237, 140, 20);
		frame.getContentPane().add(txtEndTime);

		updateData();
	}

	public void refreshUI() {
		close();
		
		this.bookedDateTime = controller.getExamBookingDateTimeByStudent(exam, student);
		
		initialize();
		show();
	}

	public void updateData() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		txtCourseCode.setText(course.getCourseCode());
		txtCourseName.setText(course.getCourseName());
		txtExamTitle.setText(exam.getExamTitle());
		txtMaxMarks.setText(exam.getMaxMarks() + "");
		txtDuration.setText(exam.getDuration() + "");
		txtStartTime.setText(format.format(exam.getStartDateTime()));
		txtEndTime.setText(format.format(exam.getEndDateTime()));
	}

	public void close(){
		frame.dispose();
	}

	public void show() {
		frame.setVisible(true);
		frame.setResizable(false);
	}

}