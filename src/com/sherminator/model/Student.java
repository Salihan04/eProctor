/*
 * @author	Nasruddin
 * @version	1.0
 * 
 */
package com.sherminator.model;

import java.util.List;

public class Student extends User {
	
	private String matricNo;
	private String photo_url;
	private String name;
	
	public Student(String userName, String password, String matricNo, String name,
			String photo_url) {
		super(userName, password);
		this.matricNo = matricNo;
		this.name = name;
		this.photo_url = photo_url;
	}

	public String getMatricNo() {
		return matricNo;
	}

	public void setMatricNo(String matricNo) {
		this.matricNo = matricNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
	
	public String toString() {
		return getMatricNo() + " : " + getName();
	}
}
