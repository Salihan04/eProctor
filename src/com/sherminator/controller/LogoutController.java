/*Author: Sherman
 * 
 *Purpose:Logout functionality should not be handled by Logout_Listener
 *Logout_Listener just notify that logout button is clicked, let this controller handle
 *procedures
 * 
 * Pass over control to LoginController when logout() method is called from listener
 * 
 * Notes: 
 * logout() public method is used by Logout listener
 */
package com.sherminator.controller;

import com.sherminator.presentation.AbstractUI;

public class LogoutController extends AbstractController{
	
	private AbstractUI ui;
	
	public LogoutController(AbstractUI ui){
		this.ui=ui;
	}
	
	public void logout(){
		gotoNextController();
	}
	
	protected void gotoNextController(){
		//deallocate user session resources
		updateSessionStack();
		ui.close();
		LoginController loginCtrl = new LoginController();
		loginCtrl.showPresentation();
		
	}
	
	protected void updateSessionStack(){
		//deallocate singleton stack
	}

	@Override
	protected void showPresentation() {

	}


}

