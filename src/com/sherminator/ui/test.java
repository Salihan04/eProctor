package com.sherminator.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class test {
	int x = 1;

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//System.out.println("...");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JLabel lblSum = new JLabel("Sum = "+ x);
		lblSum.setBounds(71, 21, 89, 14);
		frame.getContentPane().add(lblSum);
		
		JButton btnButton = new JButton("button");
		// here to end of // is the mouse event handler
		btnButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				x++;
				lblSum.setText("Sum = " + x);
			}
		});
		//end of mouse event handler
		btnButton.setBounds(47, 62, 89, 23);
		frame.getContentPane().add(btnButton);
		

	}
}
