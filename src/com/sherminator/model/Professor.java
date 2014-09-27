/*
 * @author	Nasruddin
 * @version	1.0
 * 
 */
package com.sherminator.model;

import java.util.List;

public class Professor extends User {
	
	private String staffID;
	
	public Professor(String userName, String password, String staffID) {
		super(userName, password);
		this.staffID = staffID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	
}