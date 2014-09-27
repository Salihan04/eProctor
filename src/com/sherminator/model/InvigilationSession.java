/*
 * @author	Nasruddin
 * @version	1.0
 * 
 */
package com.sherminator.model;

public class InvigilationSession {

	private String username;
	private String ip_address;
	private int port;
	
	public InvigilationSession(String username, String ip_address, int port) {
		this.username = username;
		this.ip_address = ip_address;
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
