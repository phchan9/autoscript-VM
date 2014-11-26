package com.sense;

import org.sikuli.script.FindFailed;

public abstract interface Executable {
	
	public abstract void execute(Client client)throws FindFailed;
	public abstract void feedBackCheck(Client client) throws FindFailed;
	
}
