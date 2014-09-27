/*
 * @author	Nasruddin
 * @version	1.0
 * 
 */
package com.sherminator.model;

public class Proctor extends User {
	
	private String name;
	private int proctorID;

	public Proctor(String userName, String password, String name, int proctorID) {
		super(userName, password);
		this.name = name;
		this.proctorID = proctorID;
	}

	public Proctor(String userName, String password, String name) {
		this(userName, password, name, -1);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getProctorID() {
		return proctorID;
	}
	
	public void setProctorID(int proctorID) {
		this.proctorID = proctorID;
	}
}
