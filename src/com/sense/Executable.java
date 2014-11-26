package com.sense;

import org.sikuli.script.FindFailed;

public abstract interface Executable {
	
	public abstract void execute()throws FindFailed;
	public abstract void feedBackCheck() throws FindFailed;
	
}
