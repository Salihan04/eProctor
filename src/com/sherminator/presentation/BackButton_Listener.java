package com.sherminator.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BackButton_Listener implements ActionListener {

	private static BackButton_Listener listener;
	private static Stack<AbstractUI> ui_stack;
	private static AbstractUI current_ui;
	
	private BackButton_Listener() {
		ui_stack = new Stack<AbstractUI>();
	}
	
	public static BackButton_Listener getInstance() {
		if(listener == null) {
			listener = new BackButton_Listener();
		}
		
		return listener;
	}
	
	public void addToBackStack(AbstractUI ui) {
		ui_stack.add(ui);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		AbstractUI ui = ui_stack.pop();
		
		
	}
	
}
