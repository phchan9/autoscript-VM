package com.sense;

import java.util.ArrayList;

import org.sikuli.script.FindFailed;

public class Event implements Executable{

	ArrayList<Executable> commands = new ArrayList<>();
	
	public void addCommand(Executable command){
		commands.add(command);
	}
	
	@Override
	public void execute() throws FindFailed {
		for(Executable command : commands){
			command.execute();
		}
	}

	@Override
	public void feedBackCheck() throws FindFailed {
		// TODO Auto-generated method stub
		
	}

}
