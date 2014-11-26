package com.sense;

import java.awt.event.KeyEvent;

import org.sikuli.script.FindFailed;

public class Launch {

	public static void main(String[] args) throws InterruptedException {
		Server server = new Server();
		ServiceHandler handler = new ServiceHandler();
		Client vm3 =  new Client( "VM3", "localhost", 5902);
		server.newConnection(vm3);
		server.initClientScreens();
		Event event_browser = openBrowser(vm3);
//		Event event_combo = Control_Alt_Del_Combo(vm3);
//		Event event_editor = openEditor(vm3);
		
		Thread.sleep(2000);    /* To Make sure the VNCThread is created */
		
		handler.addService(event_browser);
//		handler.addService(event_combo);
//		handler.addService(event_editor);
		try {
			handler.doService();
		} catch (FindFailed e) {
			
			e.printStackTrace();
		}
		server.closeConnection("VM3");
	}

	private static Event openEditor(Client vm){
		Event event = new Event();
		ClickCommand clickcmd = new ClickCommand(
				vm, "image/texteditor.png", "image/notepad_showup_win8.png", MouseAction.DOUBLECLICK);
		TypeCommand typecmd = new TypeCommand(
				vm, "This is open Notepad test!\n");
		WaitCommand wcmd = new WaitCommand(vm, 1.0);
		ComboKeyCommand combocmd1 = new ComboKeyCommand(vm, null, null, KeyEvent.VK_CONTROL, KeyEvent.VK_S);
		ComboKeyCommand combocmd2 = new ComboKeyCommand(vm, null, null, KeyEvent.VK_ALT, KeyEvent.VK_F4);
		event.addCommand(clickcmd);
		event.addCommand(typecmd);
		event.addCommand(wcmd);
		event.addCommand(combocmd1);
		event.addCommand(wcmd);
		event.addCommand(combocmd2);
		return event;
	}
	
	private static Event openBrowser(Client vm) {
		Event event = new Event();
		TypeCommand typecommand1 = new TypeCommand(vm, "Youtube\n");
		typecommand1.setFocuspoint("image/focus_url_pos.png");
		event.addCommand(
				new ClickCommand( vm, "image/chrome_icon_win8.png", MouseAction.DOUBLECLICK));
		
		event.addCommand(typecommand1);
		return event;
	}
	
	private static Event Control_Alt_Del_Combo(Client vm){
		Event event = new Event();
		ComboKeyCommand combo = new ComboKeyCommand(vm, null, null);
		combo.addComboKey(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_DELETE);
		event.addCommand(combo);
		return event;
	}
}
