package com.sense;

import java.net.Socket;
import java.util.ArrayList;
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
    private Map<String, ServiceHandler> handlers = new HashMap<>();
    
	public Server() {
		super();
	}

	public void newConnection(Client vm) {
		
		Socket soc = vm.getSocket();
		clients.put(vm.hostname, vm);
		books.put(vm.hostname, index);
		handlers.put(vm.hostname, new ServiceHandler(vm));
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
		VNCScreen screen;
		for(String name : clients.keySet()){
			idx_client = books.get(name);
			vm = clients.get(name);
			screen = VNCScreen.getScreen(idx_client);
			screen.setAutoWaitTimeout(40.0);    /* make longer timeout to ensure feedbackcheck*/
			vm.setScreen(screen);
		}
	}
	
	public ServiceHandler getHandler(String name_vm) {
		return handlers.get(name_vm);
	}
	
	public ServiceHandler getHandler(Client vm){
		return handlers.get(vm.hostname);
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
		handlers.remove(name_vm);
	}
	
	public void closeConnection(Client vm){
		String name_vm = vm.hostname;
		int victim = threads.indexOf(vm.getThread());
		super.closeConnection(victim);
		clients.remove(name_vm);
		books.remove(name_vm);
		handlers.remove(name_vm);
	}
	
	public void listConnection(){
		System.out.println("Info of Connections:");
		if(clients.isEmpty()){
			System.out.println("\tAll connection is closed!");
		}else{
			for(String name: clients.keySet()){
				Client vm = clients.get(name);
				System.out.println("\thost "+ vm.hostname+" "+ vm.getIp()+" "+vm.getPort());
			}
		}
	}
	public void doService(){
		
		ArrayList<Thread> threads = new ArrayList<>();
		
		for(String name : handlers.keySet()){
			Thread handle = new Thread(handlers.get(name));
			threads.add(handle);
			handle.start();
		}
		
		for(Thread thread : threads){
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: Unexpectedly Interrupted!");
				e.printStackTrace();
			}
		}
		
		System.out.println("Info: all works are done!\n");
		
	}
}
