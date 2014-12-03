package com.sense;

import org.sikuli.basics.Debug;
import org.sikuli.script.FindFailed;

import edu.unh.iol.dlc.VNCScreen;


public class TypeCommand extends Command implements Executable {

	protected int modifier = 0;
	protected String focuspoint = null;
	
	public TypeCommand(String target, String check, String focuspoint) {
		super(target, check);
		this.focuspoint = focuspoint;
	}

	public TypeCommand(String target, String check) {
		super(target, check);
		// TODO Auto-generated constructor stub
	}

	public TypeCommand(String target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public String getFocuspoint() {
		return focuspoint;
	}

	public void setFocuspoint(String focuspoint) {
		this.focuspoint = focuspoint;
	}

	@Override
	public void execute(Client client) throws FindFailed {
		VNCScreen screen = (VNCScreen) client.getScreen();
		screen.type(focuspoint, target, modifier);
		feedBackCheck(client);
	}

	@Override
	public void feedBackCheck(Client client) throws FindFailed {
		VNCScreen screen = (VNCScreen) client.getScreen();
		if( check == null){
			Debug.log(3, "Ignore the response check\n");
			return;
		}else{
			
			try {
				screen.wait(check);
			} catch (FindFailed e) {
				// TODO Auto-generated catch block
				throw new FindFailed( "@FeedBackFunction() in "+ client.hostname + " " + e.getMessage());
			}
		}
	}
	


}