package com.sherminator.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sherminator.controller.LoginController;

public class Logout_Listener implements ActionListener {

	private AbstractUI ui;
	
	public Logout_Listener(AbstractUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LoginController loginController = new LoginController();
		loginController.showPresentation();
		ui.close();
	}

}
