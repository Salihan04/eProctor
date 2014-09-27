/*
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */
package com.sherminator.presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.coobird.thumbnailator.Thumbnails;

import com.github.sarxos.webcam.Webcam;
import com.sherminator.communication.CommunicationListener;
import com.sherminator.communication.CommunicationNode;
import com.sherminator.communication.UDPCommunicationChannel;
import com.sherminator.config.Configuration;
import com.sherminator.util.AudioManager;
import com.sherminator.util.Recorder;

public class VideoChatting_UI implements AbstractUI {

	private CommunicationNode caller;
	private UDPCommunicationChannel videoCommunicationChannel;
	private UDPCommunicationChannel audioCommunicationChannel;
	private AudioManager audioManager;
	
	private int incoming_port;
	private JFrame frame;
	private JLabel video_screen;
	
	private VideoSenderDispatcher videoSenderDispatcher;

	private class VideoListener implements CommunicationListener {

		@Override
		public void onReceivedMessage(byte[] message) {
			try {
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(message));
				BufferedImage resizedImage = Thumbnails.of(image).size(400, 400).asBufferedImage();

				video_screen.setIcon(new ImageIcon(resizedImage));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	private class AudioListener implements CommunicationListener {

		@Override
		public void onReceivedMessage(byte[] message) {
			AudioManager.play(message);
		}

	}
	
	private class AudioRecorder implements Recorder {

		@Override
		public void record(byte[] audio_data) {
			try {
				audioCommunicationChannel.send(audio_data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	private class VideoSenderDispatcher implements Runnable {

		private volatile boolean isRunning = true;
		
		public void stop() {
			isRunning = false;
		}
		
		@Override
		public void run() {
			
			Webcam webcam = Webcam.getDefault();
			webcam.open();
			
			while(isRunning) {
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					
					BufferedImage image = webcam.getImage(); 
					BufferedImage resizedImage = Thumbnails.of(image).size(400, 400).asBufferedImage();
					
					ImageIO.write(resizedImage, "jpg", baos);
					baos.flush();
					
					videoCommunicationChannel.send(baos.toByteArray());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			webcam.close();
		}
		

	}

	public VideoChatting_UI(CommunicationNode caller, int incoming_port) {
		this.caller = caller;
		this.incoming_port = incoming_port;

		initialize();
	}

	public void initialize() {	
		frame = new JFrame();
		frame.setBackground(Configuration.CONTENT_BG_COLOR);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());

		video_screen = new JLabel();
		video_screen.setMinimumSize(new Dimension(400, 400));
		video_screen.setMaximumSize(new Dimension(400, 400));
		frame.getContentPane().add(video_screen);
		try {
			BufferedImage avatarImage = ImageIO.read(getClass().getResourceAsStream("/resources/Avatar.jpg"));
			video_screen.setIcon(new ImageIcon(avatarImage));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initializeCommunicationChannel();
	}

	private void initializeCommunicationChannel() {
		try {
			videoCommunicationChannel = new UDPCommunicationChannel(incoming_port, caller.getIp_address(), caller.getPort(), new VideoListener());
			audioCommunicationChannel = new UDPCommunicationChannel(incoming_port+1, caller.getIp_address(), caller.getPort()+1, new AudioListener());
			
			videoCommunicationChannel.start();
			audioCommunicationChannel.start();
				
			videoSenderDispatcher = new VideoSenderDispatcher();
			Thread videoSendingThread = new Thread(videoSenderDispatcher);
			videoSendingThread.start();
			
			audioManager = new AudioManager(new AudioRecorder());
			audioManager.record();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// It will stop transmitting from this. But, will still accept incoming video
	public void stopTransmission() {
		videoSenderDispatcher.stop();
	}
	
	public void hideUI() {
		frame.dispose();
	}

	@Override
	public void show() {
		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);		
	}

	@Override
	public void close() {
		try {
			// stop recording audio and video
			videoSenderDispatcher.stop();
			audioManager.stop();
			
			videoCommunicationChannel.close();
			audioCommunicationChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.dispose();
	}

}
