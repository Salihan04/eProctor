/*
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */
package com.sherminator.presentation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.businesslogic.InvigilationSessionManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.communication.CommunicationListener;
import com.sherminator.communication.CommunicationNode;
import com.sherminator.communication.TCPCommunicationChannel_Server;
import com.sherminator.controller.LoginController;
import com.sherminator.controller.ProctorHomeController;
import com.sherminator.controller.ProctorScheduleController;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;
import com.sherminator.presentation.component.StudentMonitoringPanel;

public class ProctorHome_UI implements AbstractUI {

	private Proctor proctor;
	private ProctorHomeController proctorHomeController;

	private JFrame frame;
	private StudentMonitoringPanel[] studentMonitoringPanels;

	private JButton btnLogout;
	private JButton btnSchedule;
	private JButton[] btnStartExam;
	private JButton[] btnStopExam;
	private JButton[] btnWarn;
	private JTextField[] txtWarn;

	private VideoChatting_UI videoChattingUI;
	
	private TCPCommunicationChannel_Server[] commandCommunicationChannels;

	private Student[] students;
	private Exam[] exams;
	
	private class StartExamButtonListener implements ActionListener {

		private int index;
		
		public StartExamButtonListener(int index) {
			this.index = index;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				commandCommunicationChannels[index].send("start");
				
				proctorHomeController.verifiedStudentExam(proctor, students[index], exams[index]);
				
				videoChattingUI.close();
				
				btnStartExam[index].setEnabled(false);
				btnStopExam[index].setEnabled(true);
				btnWarn[index].setEnabled(true);
				
				studentMonitoringPanels[index].enableReport();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	
		}
		
	}
	
	private class StopExamButtonListener implements ActionListener {

		private int index;
		
		public StopExamButtonListener(int index) {
			this.index = index;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				commandCommunicationChannels[index].send("stop");
				
				btnStopExam[index].setEnabled(false);
				
				videoChattingUI.close();
				studentMonitoringPanels[index].resetMonitor();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	
		}
		
	}
	
	private class WarnButtonListener implements ActionListener {

		private int index;
		
		public WarnButtonListener(int index) {
			this.index = index;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				commandCommunicationChannels[index].send(txtWarn[index].getText());
								
				videoChattingUI.close();
				
				btnStartExam[index].setEnabled(false);
				btnStopExam[index].setEnabled(false);
				
				studentMonitoringPanels[index].enableReport();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private class CommandListener implements CommunicationListener {

		@Override
		public void onReceivedMessage(byte[] message) {
			
			String command = new String(message);
			
			if(command.contains("#end:")) {
				String[] str = command.split(":");
				int port = Integer.parseInt(str[1]);
				
				switch(port) {
				case 9000:
					students[0] = null;
					exams[0] = null;
					
					btnStartExam[0].setEnabled(false);
					btnStopExam[0].setEnabled(false);
					btnWarn[0].setEnabled(false);
					studentMonitoringPanels[0].resetMonitor();
					break;
				case 9004:
					students[1] = null;
					exams[1] = null;
					
					btnStartExam[1].setEnabled(false);
					btnStopExam[1].setEnabled(false);
					btnWarn[1].setEnabled(false);
					studentMonitoringPanels[1].resetMonitor();
					break;
				}
			}
			else {			
				String[] s = command.split(",");
	
				String ip_address = s[0];
				String matric_no = s[1];
				int exam_id = Integer.parseInt(s[2]);
				int port = Integer.parseInt(s[3]);
	
				Student student = proctorHomeController.getStudent(matric_no);
				Exam exam = proctorHomeController.getExam(exam_id);
	
				switch(port) {
				case 9000:
					students[0] = student;
					exams[0] = exam;
					
					studentMonitoringPanels[0].startMonitor(student, exam);
					proctorHomeController.disablePort(proctor, 9000);
					btnStartExam[0].setEnabled(true);
					btnWarn[0].setEnabled(true);
					txtWarn[0].setEnabled(true);
					
					videoChattingUI = new VideoChatting_UI(new CommunicationNode(ip_address, 9000), 9000);
					videoChattingUI.show();
					break;
				case 9004:
					students[1] = student;
					exams[1] = exam;
					
					studentMonitoringPanels[1].startMonitor(student, exam);
					proctorHomeController.disablePort(proctor, 9004);
					btnStartExam[1].setEnabled(true);
					btnWarn[1].setEnabled(true);
					txtWarn[1].setEnabled(true);
					
					videoChattingUI = new VideoChatting_UI(new CommunicationNode(ip_address, 9000), 9004);
					videoChattingUI.show();
					break;
				default:
					System.out.println("Invalid port");
				}
			}
		}

	}
	
	private class ScheduleButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			proctorHomeController.gotoNextController();
		}
		
	}

	private class LogoutButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			proctorHomeController.makeUnavailable(proctor);

			proctorHomeController.goToPreviousController();
		}

	}

	public ProctorHome_UI(Proctor proctor, ProctorHomeController proctorHomeController) {
		this.proctor = proctor;
		this.proctorHomeController = proctorHomeController;

		btnStartExam = new JButton[2];
		btnStopExam = new JButton[2];
		btnWarn = new JButton[2];
		txtWarn = new JTextField[2];
		students = new Student[2];
		exams = new Exam[2];
		
		commandCommunicationChannels = new TCPCommunicationChannel_Server[2];
		try {
			commandCommunicationChannels[0] = new TCPCommunicationChannel_Server(9003, new CommandListener());
			commandCommunicationChannels[1] = new TCPCommunicationChannel_Server(9007, new CommandListener());
			
			commandCommunicationChannels[0].start();
			commandCommunicationChannels[1].start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			proctorHomeController.makeAvailable(proctor, InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		initialize();
	}

	public void initialize() {
		frame = new JFrame();
		frame.setLayout(null);

		studentMonitoringPanels = new StudentMonitoringPanel[2];
		studentMonitoringPanels[0] = new StudentMonitoringPanel(proctor, 9002);
		studentMonitoringPanels[0].setBounds(10, 30, 400, 580);

		studentMonitoringPanels[1] = new StudentMonitoringPanel(proctor, 9006);
		studentMonitoringPanels[1].setBounds(500, 30, 400, 580);

		frame.add(studentMonitoringPanels[0]);
		frame.add(studentMonitoringPanels[1]);

		btnLogout = new JButton("Logout");
		btnLogout.setBounds(460, 5, 80, 20);
		btnLogout.addActionListener(new LogoutButtonListener());
		frame.add(btnLogout);
		
		btnStartExam[0] = new JButton("Start");
		btnStartExam[0].setBounds(10, 5, 80, 20);
		btnStartExam[0].setEnabled(false);
		btnStartExam[0].addActionListener(new StartExamButtonListener(0));
		frame.add(btnStartExam[0]);
		
		btnSchedule = new JButton("Schedule");
		btnSchedule.setBounds(370, 5, 87, 20);
		btnSchedule.addActionListener(new ScheduleButtonListener());
		frame.add(btnSchedule);
		
		btnStartExam[1] = new JButton("Start");
		btnStartExam[1].setBounds(820, 5, 80, 20);
		btnStartExam[1].setEnabled(false);
		btnStartExam[1].addActionListener(new StartExamButtonListener(1));
		frame.add(btnStartExam[1]);
		
		btnStopExam[0] = new JButton("Stop");
		btnStopExam[0].setBounds(100, 5, 80, 20);
		btnStopExam[0].setEnabled(false);
		btnStopExam[0].addActionListener(new StopExamButtonListener(0));
		frame.add(btnStopExam[0]);
		
		btnStopExam[1] = new JButton("Stop");
		btnStopExam[1].setBounds(730, 5, 80, 20);
		btnStopExam[1].setEnabled(false);
		btnStopExam[1].addActionListener(new StopExamButtonListener(1));
		frame.add(btnStopExam[1]);
		
		btnWarn[0] = new JButton("Warn");
		btnWarn[0].setBounds(10, 620, 80, 20);
		btnWarn[0].setEnabled(false);
		frame.add(btnWarn[0]);
		
		btnWarn[1] = new JButton("Warn");
		btnWarn[1].setBounds(500, 620, 80, 20);
		btnWarn[1].setEnabled(false);
		frame.add(btnWarn[1]);
		
		btnWarn[0].addActionListener(new WarnButtonListener(0));
		btnWarn[1].addActionListener(new WarnButtonListener(1));
		
		txtWarn[0] = new JTextField();
		txtWarn[0].setBounds(100, 620, 150, 20);
		txtWarn[0].setEnabled(false);
		
		txtWarn[1] = new JTextField();
		txtWarn[1].setBounds(600, 620, 150, 20);
		txtWarn[1].setEnabled(false);
		
		frame.add(txtWarn[0]);
		frame.add(txtWarn[1]);
	}

	@Override
	public void show() {
		frame.setSize(920, 680);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
	}

	@Override
	public void close() {
		frame.dispose();
	}

}
