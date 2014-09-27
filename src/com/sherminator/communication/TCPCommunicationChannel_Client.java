/*
 * 
 * This is the Client part of the TCPCommunicationChannel.
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */

package com.sherminator.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPCommunicationChannel_Client implements CommunicationChannel {

	private CommunicationListener listener;
	
	private Thread receiverThread;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	
	private String server_ip_address;
	private int server_port;
	
	private class ReceiverThread implements Runnable {

		@Override
		public void run() {
			
			try {
				while(!socket.isClosed()) {
					if(reader.ready()) {
						listener.onReceivedMessage(reader.readLine().getBytes());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	
		}
		
	}
	
	public TCPCommunicationChannel_Client(String server_ip_address, int server_port, CommunicationListener listener) {
		this.listener = listener;
		this.server_ip_address = server_ip_address;
		this.server_port = server_port;
		
		receiverThread = new Thread(new ReceiverThread());
	}
	
	@Override
	public void start() throws IOException {
		socket = new Socket(server_ip_address, server_port);
		writer = new PrintWriter(socket.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		receiverThread.start();
	}

	@Override
	public void send(byte[] message) throws IOException {
		send(new String(message));
	}
	
	public void send(String message) throws IOException {
		writer.print(message + "\r\n");
		writer.flush();
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}

}
