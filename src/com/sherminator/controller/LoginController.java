/*
 * Author: Sherman
 * 
 * Description:
 * LoginController handles user authentication functionality (by delegating
 * to business logic layer), instantiates presentation(UI), and transitions on 
 * to next controller
 */
package com.sherminator.controller;

import javax.swing.JOptionPane;

import com.sherminator.businesslogic.ProctorManager;
import com.sherminator.businesslogic.ProfessorManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.businesslogic.UserManager;
import com.sherminator.model.Domain;
import com.sherminator.model.Proctor;
import com.sherminator.model.Professor;
import com.sherminator.model.Student;
import com.sherminator.model.User;
import com.sherminator.presentation.Login_UI;

public class LoginController extends AbstractController{
	private Login_UI ui;
	
	//details needed for transition to next UI
	private Domain userSessionDomain=null;
	private Proctor proctor;
	private Professor professor;
	private Student student;
	
	public LoginController(){
		
	}
	
	public void showPresentation(){
		ui = new Login_UI(this);
		ui.show();
	}
	
	private UserManager getDomainManager(Domain dom){	
		UserManager manager=null;
		switch(dom) {
		case PROFESSOR:
			manager =new ProfessorManager();
			break;
		case STUDENT:
			manager= new StudentManager();
			break;
		case PROCTOR:
			manager= new ProctorManager();
			break;
		}
		return manager;
	}
		
	public void authenticate(String username, String pass,Domain dom){
		//fetch appropriate manager then initialize user for manager to check
		UserManager manager= getDomainManager(dom);
		if(manager==null) {
			System.out.println("Error, unexpected domain occured!");
			return;
		}
		User user = new User(username, pass);
		
		if(manager.authenticate(user)) {
			switch(dom) {
			case PROCTOR:
				ProctorManager proctorManager = new ProctorManager();
				proctor = proctorManager.getProctorByUserName(user.getUserName());
				
				if(proctor != null) { // Proctor info is retrieved successfully
					userSessionDomain= Domain.PROCTOR;
					gotoNextController();
					
				} else { // Proctor info is not retrieved :(
					JOptionPane.showMessageDialog(null, "Opps! Something went wrong. Please try again.");
				}
				
				break;
			case PROFESSOR:
				ProfessorManager professorManager = new ProfessorManager();
				professor = professorManager.getProfessorByUserName(user.getUserName());
				
				if(professor != null) { 
					userSessionDomain= Domain.PROFESSOR;
					
					gotoNextController();
				} else { // Professor info is not retrieved :(
					JOptionPane.showMessageDialog(null, "Opps! Something went wrong. Please try again.");
				}
				
				break;
			case STUDENT:
				
				StudentManager studentManager = new StudentManager();				
				student = studentManager.getStudentByUserName(user.getUserName());
				
				if(student != null) { // Student info is retrieved successfully
					userSessionDomain= Domain.STUDENT;

					gotoNextController();
					
				} else { // Student info is not retrieved :(
					JOptionPane.showMessageDialog(null, "Opps! Something went wrong. Please try again.");
				}
				break;
			default:
				
				JOptionPane.showMessageDialog(null, "Opps! Something went wrong. Please try again.");break;
			}
		
		}
		else {
			JOptionPane.showMessageDialog(null, "Username or Password is incorrect. Please try again.");
		}
	}

	@Override
	public void gotoNextController() {
			
		ui.close();
		
		//pass in respective details
		switch(userSessionDomain){
		case STUDENT:	StudentHomeController studHCtrl= new StudentHomeController(student);
						studHCtrl.showPresentation();
			break;
		case PROFESSOR: ProfHomeController profHCtrl=new ProfHomeController(professor);
						profHCtrl.showPresentation();
			break;
		case PROCTOR:	ProctorHomeController proctorHCtrl = new ProctorHomeController(proctor);
						proctorHCtrl.showPresentation();
			break;
		default: JOptionPane.showMessageDialog(null, "Error,LoginController failed to transition. Please try again.");
		}
	}
	
}
