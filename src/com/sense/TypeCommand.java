package com.sense;

import org.sikuli.basics.Debug;
import org.sikuli.script.FindFailed;

import edu.unh.iol.dlc.VNCScreen;


public class TypeCommand extends Command implements Executable {

	protected int modifier = 0;
	protected String focuspoint = null;
	
	public TypeCommand(Client client, String target) {
		super(client, target);
		// TODO Auto-generated constructor stub
	}

	public TypeCommand(com.sense.Client client, String target, String check) {
		super(client, target, check);
		// TODO Auto-generated constructor stub
	}

	public TypeCommand(com.sense.Client client, String target, String check, int modifier) {
		super(client, target);
		this.modifier = modifier;
		this.check = check;
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
	public void execute() throws FindFailed {
		VNCScreen screen = (VNCScreen) client.getScreen();
		screen.type(focuspoint, target, modifier);
		feedBackCheck();
	}

	@Override
	public void feedBackCheck() throws FindFailed {
		VNCScreen screen = (VNCScreen) client.getScreen();
		if( check == null){
			Debug.log(3, "Ignore the response check\n");
			return;
		}else{
			screen.wait(check, 3.0);
		}
	}
	


}