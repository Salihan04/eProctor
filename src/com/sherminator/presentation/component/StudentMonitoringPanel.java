/*
 * This panel include all the necessary component for showing
 * matric no, remote desktop feeds and reporting
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */

package com.sherminator.presentation.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.coobird.thumbnailator.Thumbnails;

import com.sherminator.businesslogic.AnomalyReportManager;
import com.sherminator.communication.CommunicationListener;
import com.sherminator.communication.UDPCommunicationChannel;
import com.sherminator.config.Configuration;
import com.sherminator.model.AnomalyReport;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;

public class StudentMonitoringPanel extends JPanel {
	
	private UDPCommunicationChannel videoCommunicationChannel;
	private Student student;
	private Exam exam;
	private Proctor proctor;
	
	private int incoming_port;
	private JLabel video_screen;
	
	private JPanel pnlMatricCard;
	private JPanel pnlReport;
	
	private JLabel student_image;
	private JLabel logo_image;
	private JLabel lblName;
	private JLabel lblMatricNo;
	
	private JButton btnReport;
	private JTextArea txtReport;
	
	private boolean isAvailable;
	
	private class ReportButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			AnomalyReportManager reportManager = new AnomalyReportManager();
			
			AnomalyReport report = new AnomalyReport(new java.sql.Date(Calendar.getInstance().getTime().getTime()), txtReport.getText(), proctor.getUserName(), student.getMatricNo(), exam.getExamID());
			reportManager.createAnomalyReport(report);
			
			txtReport.setEnabled(false);
			btnReport.setEnabled(false);
		}
		
	}
	
	private class VideoListener implements CommunicationListener {

		@Override
		public void onReceivedMessage(byte[] message) {
			try {
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(message));
				BufferedImage resizedImage = Thumbnails.of(image).size(400, 300).asBufferedImage();

				video_screen.setIcon(new ImageIcon(resizedImage));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
		
	public StudentMonitoringPanel(Proctor proctor, int incoming_port) {
		this.proctor = proctor;
		this.incoming_port = incoming_port;
		isAvailable = true;
		
		initialize();		
	}
	
	public void startMonitor(Student student, Exam exam) {
		this.student = student;
		this.exam = exam;
		isAvailable = false;
		
		showStudentInfo();
	}
	
	public void resetMonitor() {
		isAvailable = true;
		logo_image.setIcon(new ImageIcon(getClass().getResource("/resources/logo.jpg")));
		student_image.setIcon(null);
		lblName.setText("");
		lblMatricNo.setText("");
		
		txtReport.setText("");
		txtReport.setEnabled(false);
		btnReport.setEnabled(false);
		
		try {
			BufferedImage avatarImage = ImageIO.read(getClass().getResourceAsStream("/resources/avatar.png"));
			video_screen.setIcon(new ImageIcon(avatarImage));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize() {	
		this.setSize(400, 580);
		this.setBackground(Color.BLACK);
		this.setLayout(null);

		video_screen = new JLabel();
		video_screen.setBounds(0, 0, 400, 300);
		video_screen.setMinimumSize(new Dimension(400, 300));
		video_screen.setMaximumSize(new Dimension(400, 300));
		this.add(video_screen);
		try {
			BufferedImage avatarImage = ImageIO.read(getClass().getResourceAsStream("/resources/avatar.png"));
			video_screen.setIcon(new ImageIcon(avatarImage));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		pnlMatricCard = new JPanel();
		pnlMatricCard.setBounds(0, 300, 400, 140);
		pnlMatricCard.setBackground(Configuration.CONTENT_BG_COLOR);
		pnlMatricCard.setLayout(null);
		this.add(pnlMatricCard);
		
		logo_image = new JLabel();
		logo_image.setBounds(10, 10, 133, 48);
		logo_image.setIcon(new ImageIcon(getClass().getResource("/resources/logo.jpg")));
		pnlMatricCard.add(logo_image);
		
		student_image = new JLabel();
		student_image.setBounds(300, 20, 200, 100);
		
		pnlMatricCard.add(student_image);
		
		lblName = new JLabel();
		lblName.setBounds(25, 75, 200, 20);
		lblName.setFont(Configuration.MATRIC_CARD_TEXT_FONT);
		lblName.setForeground(Configuration.CONTENT_TEXT_COLOR);
		pnlMatricCard.add(lblName);
		
		lblMatricNo = new JLabel();
		lblMatricNo.setBounds(25, 100, 200, 20);
		lblMatricNo.setFont(Configuration.MATRIC_CARD_TEXT_FONT);
		lblMatricNo.setForeground(Configuration.CONTENT_TEXT_COLOR);
		pnlMatricCard.add(lblMatricNo);
		
		pnlReport = new JPanel();
		pnlReport.setBackground(Configuration.HEADER_BG_COLOR);
		pnlReport.setBounds(0, 440, 400, 140);
		pnlReport.setLayout(null);
		this.add(pnlReport);
		
		txtReport = new JTextArea();
		txtReport.setBounds(10, 10, 380, 80);
		JScrollPane reportScroll = new JScrollPane (txtReport, 
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		reportScroll.setBounds(10, 10, 380, 80);
		pnlReport.add(reportScroll);
		
		btnReport = new JButton("Report");
		btnReport.setBounds(10, 100, 100, 30);
		btnReport.addActionListener(new ReportButtonListener());
		pnlReport.add(btnReport);
		
		btnReport.setEnabled(false);
		txtReport.setEnabled(false);
		
		initializeCommunicationChannel();
	}
	
	public void enableReport() {
		btnReport.setEnabled(true);
		txtReport.setEnabled(true);
	}
	
	private void showStudentInfo() {
		lblName.setText("Name         : " + student.getName());
		lblMatricNo.setText("Matric No.  : " + student.getMatricNo());
		System.out.println(student.getPhoto_url());
		BufferedImage img = null;
		try {
			img = Thumbnails.of(getClass().getResource("/students/" + student.getPhoto_url())).size(200, 100).asBufferedImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		student_image.setIcon(new ImageIcon(img));
	}

	private void initializeCommunicationChannel() {
		try {
			// we are only using one way communication. i.e. student will send the desktop image to us
			videoCommunicationChannel = new UDPCommunicationChannel(incoming_port, null, -1, new VideoListener());
			videoCommunicationChannel.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// isAvailable to be used as monitoring panel
	public boolean isAvailable() {
		return isAvailable;
	}
	
}
