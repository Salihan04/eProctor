/*
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */

package com.sherminator.presentation;

import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;

import com.github.sarxos.webcam.Webcam;
import com.sherminator.businesslogic.AnswerManager;
import com.sherminator.businesslogic.QuestionManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.communication.CommunicationListener;
import com.sherminator.communication.CommunicationNode;
import com.sherminator.communication.TCPCommunicationChannel_Client;
import com.sherminator.communication.UDPCommunicationChannel;
import com.sherminator.config.Configuration;
import com.sherminator.controller.TakeExamController;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.InvigilationSession;
import com.sherminator.model.Question;
import com.sherminator.model.Student;

public class TakeExam_UI implements AbstractUI {

	private Student student;
	private Exam exam;
	private InvigilationSession session;

	private TakeExamController controller;

	private JFrame frame;
	private JPanel pnlHeader;
	private JPanel pnlQuestions;
	private JLabel lblTimeRemaining;
	private JLabel lblTimerLabel;
	private JButton btnSubmit;
	private JButton btnPrevious;
	private JButton btnNext;

	private Hashtable<Question, Answer> studentAnswers;
	private List<Question> questions;

	private VideoChatting_UI videoChatting_UI;

	private UDPCommunicationChannel videoCommunicationChannel;
	private TCPCommunicationChannel_Client commandChannel;

	private VideoSenderDispatcher videoSenderDispatcher;

	private boolean enablePNP = false;

	private class CommandListener implements CommunicationListener {

		@Override
		public void onReceivedMessage(byte[] message) {
			String command = new String(message);

			if(command.equals("start")) {
				initialize();
				show();
				
				videoChatting_UI.close();

				enablePNP = true;
				
				StudentManager studentMgr = new StudentManager();
				studentMgr.studentAttendExam(student, exam, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			} else if(command.equals("stop")) {
				JOptionPane.showMessageDialog(null, "You cheated!!!!");

				controller.studentChooseAnswers(student, questions, studentAnswers);
				videoChatting_UI.close();
				controller.back();
			} else if(command.equals("warned")) {
				JOptionPane.showMessageDialog(null, "You have been warned.");
			} else {
				JOptionPane.showMessageDialog(null, command);
			}

		}

	}

	private class VideoSenderDispatcher implements Runnable {

		private volatile boolean isRunning = true;

		private boolean firstTime = true;
		
		public void stop() {
			isRunning = false;
		}

		@Override
		public void run() {

			try {
				Robot robot = new Robot();
				Webcam webcam = Webcam.getDefault();
				
				while(isRunning) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					if(enablePNP) {
						
						if(firstTime) {
							webcam.getLock().unlock();
							webcam.open();
							firstTime = false;
						}
						
						BufferedImage resizedWebcamImage = Thumbnails.of(webcam.getImage()).size(150, 150).asBufferedImage();
						
						BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
						BufferedImage resizedImage = Thumbnails.of(screenShot).size(400, 400).watermark(Positions.BOTTOM_RIGHT, resizedWebcamImage, 0.9f).asBufferedImage();

						ImageIO.write(resizedImage, "jpg", baos);
						baos.flush();


						videoCommunicationChannel.send(baos.toByteArray());
					} else {
						BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
						BufferedImage resizedImage = Thumbnails.of(screenShot).size(400, 400).asBufferedImage();

						ImageIO.write(resizedImage, "jpg", baos);
						baos.flush();

						videoCommunicationChannel.send(baos.toByteArray());
					}
				}
				
				webcam.close();
				
			} catch(IOException e) {
				e.printStackTrace();
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	private class NextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout)pnlQuestions.getLayout();
			cardLayout.next(pnlQuestions);
		}		

	}

	private class PreviousButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout)pnlQuestions.getLayout();
			cardLayout.previous(pnlQuestions);
		}	

	}

	private class SubmitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if(JOptionPane.showConfirmDialog(null, "Are you sure you want to submit?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				try {
					// Tell the proctor the student finished the exam
					commandChannel.send("#end:" + session.getPort());
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				controller.studentChooseAnswers(student, questions, studentAnswers);
				videoChatting_UI.close();
				controller.back();
			}

		}

	}

	private class AnswerRadioButtonListener implements ActionListener {

		private Question question;
		private List<Answer> answers;

		public AnswerRadioButtonListener(Question question, List<Answer> answers) {
			this.question = question;
			this.answers = answers;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = Integer.parseInt(e.getActionCommand());
			studentAnswers.put(question, answers.get(index));	
		}

	}

	private class CountDownTimerListener implements ActionListener {

		private int examDuration = (int)(exam.getDuration() * 3600);

		@Override
		public void actionPerformed(ActionEvent arg0) {

			//Calculate the seconds based on the exam duration
			String seconds;
			if((examDuration % 60) < 10) {
				seconds = "0" + examDuration % 60;
			}
			else {
				seconds = "" + examDuration % 60;
			}

			//Calculate the minutes based on the exam duration
			String minutes = ""+(examDuration / 60) % 60;
			if(((examDuration / 60) % 60) < 10) {
				minutes = "0" + (examDuration / 60) % 60;
			}
			else {
				minutes = "" + (examDuration / 60) % 60;
			}

			//Calculate the hours based on the exam duration
			String hours;
			if(((examDuration / 3600) % 24) < 10) {
				hours = "0" + (examDuration / 3600) % 24;
			}
			else {
				hours = "" + (examDuration / 3600) % 24;
			}

			//Display the time remaining
			lblTimerLabel.setText(hours + ":" + minutes + ":" + seconds);

			//Pop-up window appears to tell user that time is up
			if(examDuration == 0){
				controller.studentChooseAnswers(student, questions, studentAnswers);

				try {
					// Tell the proctor the student finished the exam
					commandChannel.send("#end:" + session.getPort());
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "Times UP HAHA YOU'RE SCREWED");
				videoChatting_UI.close();
				controller.back();
			}

			//decrement the the exam duration
			examDuration--;
		}

	}

	public TakeExam_UI(Student student, Exam exam, InvigilationSession session, TakeExamController controller) {
		
		this.student = student;
		this.exam = exam;
		this.session = session;
		this.controller = controller;
		this.questions = controller.getQuestionsByExam(exam);	
		
		// this is only for sending data. so i didn't initialize the listener
		try {
			videoCommunicationChannel = new UDPCommunicationChannel(10000, session.getIp_address(), session.getPort()+2, null);
			videoCommunicationChannel.start();

			commandChannel = new TCPCommunicationChannel_Client(session.getIp_address(), session.getPort()+3, new CommandListener());				
			commandChannel.start();

			String ip_address = InetAddress.getLocalHost().getHostAddress();
			int port = session.getPort();

			commandChannel.send(ip_address + "," + student.getMatricNo() + "," + exam.getExamID() + "," + port);

			videoChatting_UI = new VideoChatting_UI(new CommunicationNode(session.getIp_address(), session.getPort()), 9000);
			videoChatting_UI.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		videoSenderDispatcher = new VideoSenderDispatcher();
		Thread t = new Thread(videoSenderDispatcher);
		t.start();

//		initialize();
	}

	private void initialize() {
		studentAnswers = new Hashtable<Question, Answer>();

		//The window
		frame = new JFrame();
		frame.setBounds(0, 0, 800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//Header Panel
		pnlHeader = new JPanel();
		pnlHeader.setBounds(0, 0, 800, 70);
		pnlHeader.setLayout(null);
		frame.getContentPane().add(pnlHeader);

		lblTimeRemaining = new JLabel("Time Remaining :");
		lblTimeRemaining.setFont(Configuration.IMPORTANT_TEXT_FONT);
		lblTimeRemaining.setBounds(10, 15, 300, 36);
		pnlHeader.add(lblTimeRemaining);

		//Label for the time remaining
		lblTimerLabel = new JLabel("--:--:--", JLabel.CENTER);
		lblTimerLabel.setFont(Configuration.IMPORTANT_TEXT_FONT);
		lblTimerLabel.setBounds(290, 15, 140, 36);
		pnlHeader.add(lblTimerLabel);

		// Setting Color
		pnlHeader.setBackground(Configuration.HEADER_BG_COLOR);
		lblTimeRemaining.setForeground(Configuration.HEADER_TEXT_COLOR);
		lblTimerLabel.setForeground(Configuration.HEADER_TEXT_COLOR);

		//Countdown Timer for Exam according to the duration set by Professor
		Timer swingTimer = new Timer(1000, new CountDownTimerListener());
		swingTimer.start();

		//Display Exam Title
		frame.setTitle(exam.getCourseCode() + " : " + exam.getExamTitle());
		//		lblExamTitle = new JLabel("Exam Title : " + exam.getCourseCode() + " Quiz : " + exam.getExamID());
		//		lblExamTitle.setBounds(250, 40, 201, 14);
		//		pnlHeader.add(lblExamTitle);

		btnPrevious = new JButton("Previous");
		btnPrevious.setBounds(600, 525, 85, 23);
		btnPrevious.setForeground(Configuration.BUTTON_NORMAL_TEXT_COLOR);
		btnPrevious.setBackground(Configuration.BUTTON_NORMAL_BG_COLOR);
		btnPrevious.addActionListener(new PreviousButtonListener());
		frame.getContentPane().add(btnPrevious);

		btnNext = new JButton("Next");
		btnNext.setBounds(700, 525, 85, 23);
		btnNext.setForeground(Configuration.BUTTON_NORMAL_TEXT_COLOR);
		btnNext.setBackground(Configuration.BUTTON_NORMAL_BG_COLOR);
		btnNext.addActionListener(new NextButtonListener());
		frame.getContentPane().add(btnNext);

		//Submit button
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new SubmitButtonListener());
		btnSubmit.setBounds(600, 10, 89, 23);
		pnlHeader.add(btnSubmit);

		//Display all questions in the question list
		initializeQuestionPanel();

	}

	//method to display the questions
	private void initializeQuestionPanel(){
		final int SPACING = 20;
		int questionNo = 1;

		//Define Questions panel
		pnlQuestions = new JPanel();
		pnlQuestions.setLayout(new CardLayout());
		pnlQuestions.setBounds(0, 70, 800, 500);

		pnlQuestions.setBackground(Configuration.CONTENT_BG_COLOR);

		for(Question question: questions) {
			JPanel questionPanel = new JPanel();
			questionPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
			questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

			JLabel lblQuestionLabel = new JLabel("Question " + questionNo++);
			lblQuestionLabel.setFont(Configuration.QUESTION_TEXT_FONT);

			JLabel lblQnSentence = new JLabel("<html><p>" + question.getQuestionSentence() + "</p></html>");
			lblQnSentence.setFont(Configuration.QUESTION_TEXT_FONT);

			questionPanel.add(lblQuestionLabel);
			questionPanel.add(Box.createVerticalStrut(SPACING));
			questionPanel.add(lblQnSentence);

			// Setting Color
			questionPanel.setBackground(Configuration.CONTENT_BG_COLOR);
			lblQuestionLabel.setForeground(Configuration.CONTENT_TEXT_COLOR);
			lblQnSentence.setForeground(Configuration.CONTENT_TEXT_COLOR);

			ButtonGroup answerButtonGroup = new ButtonGroup();
			List<Answer> answers = controller.getAnswersByQuestion(question);
			AnswerRadioButtonListener answerRadioButtonListener = new AnswerRadioButtonListener(question, answers);

			for(int i=0;i<answers.size();i++) {
				Answer answer = answers.get(i);

				JRadioButton rdoAnswer = new JRadioButton("<html><p>" + answer.getAnswerSentence() + "</p></html>");
				rdoAnswer.setFont(Configuration.ANSWER_TEXT_FONT);
				rdoAnswer.setForeground(Configuration.CONTENT_TEXT_COLOR);
				rdoAnswer.setBackground(Configuration.CONTENT_BG_COLOR);
				rdoAnswer.setActionCommand(i + "");
				rdoAnswer.addActionListener(answerRadioButtonListener);
				answerButtonGroup.add(rdoAnswer);

				questionPanel.add(Box.createVerticalStrut(SPACING));
				questionPanel.add(rdoAnswer);
			}

			pnlQuestions.add(questionPanel, ""); 
		}

		frame.getContentPane().add(pnlQuestions);
	}

	public void close(){
		videoSenderDispatcher.stop();
		frame.dispose();
	}

	public void show() {
		frame.setVisible(true);
		frame.setResizable(false);
	}

}