package com.sense;

import java.util.ArrayList;

import org.sikuli.script.FindFailed;

public class Event implements Executable{

	ArrayList<Executable> commands = new ArrayList<>();
	
	public void addCommand(Executable command){
		commands.add(command);
	}
	
	@Override
	public void execute(Client client) throws FindFailed {
		for(Executable command : commands){
			command.execute(client);
		}
	}

	@Override
	public void feedBackCheck(Client client) throws FindFailed {
		// TODO Auto-generated method stub
		
	}

}
