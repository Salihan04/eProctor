/*
 * 
 * This is the server part of the TCPCommunicationChannel
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
import java.net.ServerSocket;
import java.net.Socket;

public class TCPCommunicationChannel_Server implements CommunicationChannel {

	private CommunicationListener listener;
	
	private Thread receiverThread;
	
	private ServerSocket server;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	
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
			
			try {
				start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public TCPCommunicationChannel_Server(int port, CommunicationListener listener) throws IOException {
		server = new ServerSocket(port);
		
		this.listener = listener;
		
		receiverThread = new Thread(new ReceiverThread());		
	}
	
	@Override
	public void start() throws IOException {
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					socket = server.accept();
					
					writer = new PrintWriter(socket.getOutputStream());
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					receiverThread.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		t.start();
				
	}
	
	@Override
	public void close() throws IOException {
		socket.close();	
	}

	@Override
	public void send(byte[] message) throws IOException {
		send(new String(message));
	}
	
	public void send(String message) throws IOException {
		writer.print(message + "\r\n");
		writer.flush();
	}

}