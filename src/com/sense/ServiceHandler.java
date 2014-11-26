package com.sense;

import java.util.ArrayList;

import org.sikuli.script.FindFailed;

public class ServiceHandler implements Runnable{
	
	ArrayList<Executable> services = new ArrayList<>();
	Client client;
			
	public ServiceHandler(Client vm) {
		client = vm;
	}

	public void addService(Executable service){
		services.add(service);
	}
	
	private void doService() throws FindFailed{
		for(Executable service : services){
			service.execute(client);
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
			doService();
		} catch (FindFailed e) {
//			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
