package com.sense;

import java.util.ArrayList;

import org.sikuli.script.FindFailed;

public class ServiceHandler {
	
	ArrayList<Executable> services = new ArrayList<>();
			
	void addService(Executable service){
		services.add(service);
	}
	
	void doService() throws FindFailed{
		
		for(Executable service : services){
			service.execute();
		}
	}
}
