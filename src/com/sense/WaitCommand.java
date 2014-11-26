package com.sense;

import org.sikuli.script.FindFailed;

import edu.unh.iol.dlc.VNCScreen;

public class WaitCommand extends Command implements Executable{
	
	private double timeout;
	
	public WaitCommand(Client client, double timeout){
		this.client = client;
		this.timeout = timeout;
	}
	
	public WaitCommand(Client client, String target) {
		super(client, target);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws FindFailed {
		VNCScreen screen = (VNCScreen) client.getScreen();
		screen.wait(timeout);
	}

	@Override
	public void feedBackCheck() throws FindFailed {
		// TODO Auto-generated method stub
		
	}


}
