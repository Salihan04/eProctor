/*
 * Author: Sherman
 * 
 * Abstract class for controllers for organization and code maintenance.
 * Used especially by LogoutController
 * 
 * Notes on controllers in general:
 * 
 * Using a variant of PAC (Presentation-Abstraction-Control) design pattern
 * One node of PAC consist of one P-A-C set.
 * Our entire UI subsystem consists of many such nodes.
 * 
 * Controller is the intelligence of the Presentation(or view in MVC) in the PAC model.
 * In PAC, the Presentation is dumb unlike view in MVC
 * Abstraction is same as model in MVC
 * 
 * Main Controller acts a Mediator for Presentation, Abstraction
 * Main Controller in a node can communicate with another controller from another node
 * Sub-controller can be created inside node for specific complex functions in a PAC node.
 * Abstraction and presentation is totally decoupled due to controller.
 */
package com.sherminator.controller;

public abstract class AbstractController {
	
	public abstract void showPresentation();
	public abstract void gotoNextController();
	
}
