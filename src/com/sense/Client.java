package com.sense;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.sikuli.script.IScreen;

import edu.unh.iol.dlc.VNCThread;

public class Client {
	
	public String hostname;
	private String ip;
	private int port;
	private Socket socket;
	private IScreen screen;   // Can't use VNCScreen , cuz it will invoke it's static method
	private VNCThread thread;
	
	public Client(String name, String ip, int port) {
		this.hostname = name;
		this.ip = ip;
		this.port = port;
		try {
			this.socket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			System.out.println("Error: UnknownHostException!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error: Socket connection Failed!");
			e.printStackTrace();
		}
	}
	

	public Socket getSocket(){
		return socket;
	}
	
	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public IScreen getScreen() {
		return screen;
	}

	public void setScreen(IScreen screen) {
		this.screen = screen;
	}

	public VNCThread getThread() {
		return thread;
	}

	public void setThread(VNCThread thread) {
		this.thread = thread;
	}
	
	@Override
	public String toString() {
		return "Client [hostname=" + hostname + ", ip=" + ip + ", port=" + port
				+ "]";
	}
	
	
}
