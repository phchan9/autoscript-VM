package com.sense;

import org.sikuli.basics.Debug;
import org.sikuli.script.FindFailed;

import edu.unh.iol.dlc.VNCScreen;


public class ClickCommand extends Command implements Executable {
	
	/* Default there is only one single modifier when you click */
	protected int modifier = 0;
	protected MouseAction type = MouseAction.CLICK;
	
	public ClickCommand(Client client, String target) {
		super(client, target);
	}
	
	public ClickCommand(Client client, String target, MouseAction type) {
		super(client, target);
		this.type = type;
	}

	public ClickCommand(Client client, String target, String check, MouseAction type) {
		super(client, target, check);
		this.type = type;
	}

	public ClickCommand(Client client, String target, String check,
			MouseAction type, int modifier) {
		super(client, target, check);
		this.modifier = modifier;
		this.type = type;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public MouseAction getType() {
		return type;
	}

	public void setType(MouseAction type) {
		this.type = type;
	}

	@Override
	public void execute() throws FindFailed {
		VNCScreen screen = (VNCScreen) client.getScreen();
		switch(type){
			case CLICK:
				screen.click(target, modifier);
				break;
			case RIGHTCLICK:
				screen.rightClick(target, modifier);
				break;
			case DOUBLECLICK:
				screen.doubleClick(target, modifier);
				break;
		}
		feedBackCheck();
	}

	@Override
	public void feedBackCheck() throws FindFailed {
		VNCScreen screen = (VNCScreen) client.getScreen();

		if( check == null){
			Debug.log(3, "Ignore the response check\n");
			return ;
		}
		screen.wait(check, 3.0);
		
	}

}
