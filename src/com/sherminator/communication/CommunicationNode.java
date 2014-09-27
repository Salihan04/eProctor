/*
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */
package com.sherminator.communication;

public class CommunicationNode {
	private String ip_address;
	private int port;
	
	public CommunicationNode(String ip_address, int port) {
		this.ip_address = ip_address;
		this.port = port;
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
