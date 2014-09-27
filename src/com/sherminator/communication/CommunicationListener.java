/*
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */
package com.sherminator.communication;

public interface CommunicationListener {

	public void onReceivedMessage(byte[] message);
	
}
