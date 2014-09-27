/*
 * 
 * @author	Sherman
 * @version	1.0
 * 
 */
package eProctor;

import javax.swing.JOptionPane;

import com.sherminator.controller.LoginController;

public class Main {

	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "You can change the MSSQLServer IP address at com.sherminator.util and edit IP address in CWS_DBConnection.java and System_DBConnection.java");
		
		LoginController loginController = new LoginController();
		loginController.showPresentation();
	}

}	