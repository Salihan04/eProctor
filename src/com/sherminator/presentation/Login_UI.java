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

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import com.sherminator.businesslogic.CourseManager;
import com.sherminator.businesslogic.ProctorManager;
import com.sherminator.businesslogic.ProfessorManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.businesslogic.UserManager;
import com.sherminator.controller.LoginController;
import com.sherminator.controller.ProfHomeController;
import com.sherminator.model.Domain;
import com.sherminator.model.Professor;
import com.sherminator.model.Student;
import com.sherminator.model.User;

public class Login_UI implements AbstractUI {

	private LoginController loginController;

	private JFrame frame;
	private JPasswordField txtPassword;
	private JTextField txtUsername;
	private JLabel lblDomain;
	private JComboBox cboDomain;

	// Individual listener for individual button
	private class LoginButtonListener implements ActionListener {

		private UserManager getDomainManager(Domain domain){	
			UserManager manager = null;
			switch(domain) {
			case PROFESSOR:
				manager = new ProfessorManager();
				break;
			case STUDENT:
				manager = new StudentManager();
				break;
			case PROCTOR:
				manager = new ProctorManager();
				break;
			}
			return manager;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			UserManager manager = null;

			if(validateInput()) {

				String username = txtUsername.getText();
				String password = new String(txtPassword.getPassword());
				Domain domain = (Domain) cboDomain.getSelectedItem();

				manager = getDomainManager(domain);

				if(manager == null){
					System.out.println("Error, unexpected domain occured!");
					return;
				}
				User user = new User(username, password);
				loginController.authenticate(username, password, domain);
			}
		}
	}

	public Login_UI(LoginController loginController) {
		this.loginController = loginController;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 541, 395);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(244, 138, 127, 20);
		frame.getContentPane().add(txtPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(168, 141, 65, 14);
		frame.getContentPane().add(lblPassword);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(168, 104, 68, 14);
		frame.getContentPane().add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.setBounds(244, 101, 127, 20);
		frame.getContentPane().add(txtUsername);
		
		lblDomain = new JLabel("Domain");
		lblDomain.setBounds(168, 178, 65, 14);
		frame.getContentPane().add(lblDomain);

		cboDomain = new JComboBox();
		cboDomain.setModel(new DefaultComboBoxModel(Domain.values()));
		cboDomain.setBounds(244, 176, 127, 20);
		frame.getContentPane().add(cboDomain);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new LoginButtonListener());
		btnLogin.setBounds(282, 208, 89, 23);
		frame.getContentPane().add(btnLogin);
	}

	public void close(){
		frame.dispose();
	}

	public void show() {
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public boolean validateInput() {
		boolean isValidated = true;

		if(txtUsername.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Username cannot be empty.");
			txtUsername.requestFocus();
			isValidated = false;
		}
		else if(txtPassword.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null, "Password cannot be empty.");
			txtPassword.requestFocus();
			isValidated = false;
		}

		return isValidated;
	}

}
