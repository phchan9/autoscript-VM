package com.sense;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import edu.unh.iol.dlc.ConnectionController;
import edu.unh.iol.dlc.VNCScreen;

public class Server extends ConnectionController{
	
	/*
	 *   All kinds of Encoding following
	 */
	public static final int RAW = 0;
	public static final int COPYREC = 1;
	public static final int RRE = 2;
	public static final int CORRE = 4;
	public static final int HEXTILE = 5;
	
	int index = 0;
    private Map<String, Client> clients= new HashMap<>();
    private Map<String, Integer> books = new HashMap<>();
    
	public Server() {
		super();
	}

	public void newConnection(Client vm) {
		
		Socket soc = vm.getSocket();
		clients.put(vm.hostname, vm);
		books.put(vm.hostname, index);
		super.newConnection(soc);
		super.openConnection(index);
		super.setPixelFormat(index, "Truecolor", 32, 0);
		super.setEncoding(index, RAW, COPYREC);
		super.start(index);
		vm.setThread(threads.get(index));
		index++;
	}
	
	public void initClientScreens(){
		
		int idx_client = 0;
		Client vm;
		for(String name : clients.keySet()){
			idx_client = books.get(name);
			vm = clients.get(name);
			vm.setScreen(VNCScreen.getScreen(idx_client));
		}
	}
	
	public Client getClient(String name){
		return clients.get(name);
	}
	
	public void closeConnection(String name_vm){
		Client vm = clients.get(name_vm);
		int victim = threads.indexOf(vm.getThread());
		super.closeConnection(victim);
		clients.remove(name_vm);
		books.remove(name_vm);
	}
	
}
