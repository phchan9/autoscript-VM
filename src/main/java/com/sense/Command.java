package com.sense;


public abstract class Command {
	
	protected String target;
	protected String check = null;

	public Command(){
		target = null;
	}
	
	public Command(String target) {
		this.target = target;
	}

	public Command(String target, String check) {
		this.target = target;
		this.check = check;
	}
	


}
