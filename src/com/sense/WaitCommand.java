package com.sense;

import org.sikuli.script.FindFailed;

import edu.unh.iol.dlc.VNCScreen;

public class WaitCommand extends Command implements Executable{
	
	private double timeout = 0;
	
	public WaitCommand(String target, double timeout){
		super(target);
		this.timeout = timeout;
	}
	
	public WaitCommand(String target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	public WaitCommand(double timeout) {
		super();
		this.timeout = timeout;
	}

	@Override
	public void execute(Client client) throws FindFailed {
		VNCScreen screen = (VNCScreen) client.getScreen();
		if( timeout == 0.0){
			screen.wait(target);
		}else{
			if(target == null){
				screen.wait(timeout);
			}else{
				screen.wait(target, timeout);
			}
		}
	}

	@Override
	public void feedBackCheck(Client client) throws FindFailed {
		// TODO Auto-generated method stub
		
	}


}
