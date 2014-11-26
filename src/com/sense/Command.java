package com.sense;


public abstract class Command {
	
	protected Client client;   /* why not IScreen, Cuz we don't use functions of Iscreen, instead of Region*/
	protected String target;
	protected String check = null;
	
	public Command(){
		client = null;
		target = null;
	}
	
	public Command(Client client, String target) {
		this.client = client;
		this.target = target;
	}
	
	public Command(Client client, String target, String check) {
		this.client = client;
		this.target = target;
		this.check = check;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
