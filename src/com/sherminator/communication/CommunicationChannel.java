/*
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */
package com.sherminator.communication;

import java.io.IOException;

public interface CommunicationChannel {
	
	public void start() throws IOException;
	public void send(byte[] data) throws IOException;
	public void close() throws IOException;
	
}
