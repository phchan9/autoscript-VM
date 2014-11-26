package com.sense;

import org.sikuli.basics.Debug;
import org.sikuli.script.FindFailed;

import edu.unh.iol.dlc.VNCScreen;


public class ClickCommand extends Command implements Executable {
	
	/* Default there is only one single modifier when you click */
	protected int modifier = 0;
	protected MouseAction type = MouseAction.CLICK;
	
	public ClickCommand(String target, String check, MouseAction type) {
		super(target, check);
		this.type = type;
	}
	
	public ClickCommand(String target, MouseAction type) {
		super(target);
		this.type = type;
	}
	
	public ClickCommand(String target, String check) {
		super(target, check);
		// TODO Auto-generated constructor stub
	}

	public ClickCommand(String target) {
		super(target);
		// TODO Auto-generated constructor stub
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
	public void execute(Client client) throws FindFailed {
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
		feedBackCheck(client);
	}

	@Override
	public void feedBackCheck(Client client) throws FindFailed {
		VNCScreen screen = (VNCScreen) client.getScreen();

		if( check == null){
			Debug.log(3, "Ignore the response check\n");
			return ;
		}
		screen.wait(check, 3.0);
		
	}

}
