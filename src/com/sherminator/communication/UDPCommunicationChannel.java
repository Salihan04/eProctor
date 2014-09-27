/*
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */
package com.sherminator.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCommunicationChannel implements CommunicationChannel {

	private CommunicationListener listener;
	
	private Thread receiverThread;
	private ReceiverDispatcher receiverDispatcher;
	
	private DatagramSocket server;
	
	private InetAddress remote_ip_address;
	private int remote_port;
	
	private class ReceiverDispatcher implements Runnable {

		private volatile boolean isRunning = true;
		
		public void stop() {
			isRunning = false;
		}
		
		@Override
		public void run() {
			byte[] receivedData = new byte[60000];
			DatagramPacket dataPacket;
			
			
			try {
				while(!server.isClosed() && isRunning) {
					dataPacket = new DatagramPacket(receivedData, receivedData.length);
					server.receive(dataPacket);
					
					listener.onReceivedMessage(receivedData); 
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 			
		}
		
	}
	
	public UDPCommunicationChannel(int port, String remote_ip_address, int remote_port, CommunicationListener listener) throws IOException {
		server = new DatagramSocket(port);
		
		this.remote_ip_address = InetAddress.getByName(remote_ip_address);
		this.remote_port = remote_port;
	
		this.listener = listener;
		
		receiverDispatcher = new ReceiverDispatcher();
		receiverThread = new Thread(receiverDispatcher);		
	}
	
	@Override
	public void start() throws IOException {
		receiverThread.start();		
	}
	
	@Override
	public void close() throws IOException {
		receiverDispatcher.stop();
		server.close();	
	}

	@Override
	public void send(byte[] data) throws IOException {
		DatagramPacket dataPacket = new DatagramPacket(data, data.length, remote_ip_address, remote_port);
		
		server.send(dataPacket);
	}
	
	public void send(String message) throws IOException {
		send(message.getBytes());
	}
	
}		
