package com.sense;

import org.sikuli.basics.Debug;
import org.sikuli.script.FindFailed;
import org.sikuli.script.IRobot;

import edu.unh.iol.dlc.VNCScreen;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class ComboKeyCommand extends Command implements Executable {

	protected ArrayList<Integer> combos = new ArrayList<Integer>();
	
	/**
	 * This class is used to press several keys meanwhile e.g. Contorl+Alt+Del
	 * 
	 * The Constructors of ComboKeyCommand 
	 * @param target  null often
	 * @param check  the filename of the image which will be checked for response
	 * @param keys  several keys to press, using Key library of java e.g. KeyEvent.VK_CONTROL
	 */
	public ComboKeyCommand(String target, String check, int... keys ) {
		super(target, check);
		this.addComboKey(keys);
	}
	
	public ComboKeyCommand(String target, String check) {
		super(target, check);
		// TODO Auto-generated constructor stub
	}

	public ComboKeyCommand(String target) {
		super(target);
		// TODO Auto-generated constructor stub
	}
	
	public ComboKeyCommand(int... keys) {    /* often use case */
		for(int key: keys)
			combos.add(key);
	}

	public void addComboKey(int... keys){
		for(int key: keys)
			combos.add(key);
	}

	@Override
	public void execute(Client client) throws FindFailed {
		IRobot robot = ((VNCScreen) client.getScreen()).getRobot();
		
		for(int i =0 ; i< combos.size(); ++i){
			robot.keyDown(combos.get(i));
		}
		
		for(int i = combos.size()-1 ; i >= 0 ; --i){
			robot.keyUp(combos.get(i));
		}
	}

	@Override
	public void feedBackCheck(Client client) throws FindFailed {
		
		VNCScreen screen = (VNCScreen) client.getScreen();
		if( check == null){
			Debug.log(3, "Ignore the response check\n");
			return;
		}
		try {
			screen.wait(check);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			throw new FindFailed( "@FeedBackFunction() in "+ client.hostname + " " + e.getMessage());
		}
	}

}
