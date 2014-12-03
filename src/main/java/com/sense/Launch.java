package com.sense;

import java.awt.event.KeyEvent;
import org.sikuli.script.ImagePath;

public class Launch {

	public static void main(String[] args) throws InterruptedException {
		Server server = new Server();
		Client vm3 =  new Client( "VM3", "localhost", 5902);
//		Client vm2 =  new Client( "VM2", "localhost", 5901);
//		Client vm1 =  new Client( "VM1", "localhost", 5900);
		server.newConnection(vm3);
//		server.newConnection(vm2);
//		server.newConnection(vm1);
//		ImagePath.add("com.sense.Launch");
//		System.out.println("[*]"+ImagePath.getPaths());
		server.initClientScreens();
		
		System.out.println("Note: time of initialization 2 secs");
		Thread.sleep(2000);    /* To Make sure the VNCThread is created */
		
//		server.getHandler("VM3").addService(event_openNotePad_win8);
//		server.getHandler("VM3").addService(nsLookup());
		server.getHandler("VM3").addService(openBrowser());
//		server.getHandler("VM2").addService(wget_malware());
		
		server.doService();
		server.closeAllConnection();
		server.listConnection();
		System.exit(0);
		
//		Client vm2 =  new Client( "VM2", "localhost", 5901);
//		server.newConnection(vm2);
//		server.getHandler("VM2").addService(event_openNotePad);
//		server.closeConnection("VM2");
	}

	private static Executable wget_malware() {
		Event event = new Event();
		ClickCommand clickcmd1 = new ClickCommand("image/IE_icon.png","image/IE_showup.png");
		TypeCommand typecmd1 = new TypeCommand(
				"192.168.1.12/file/malware4.exe\n", null, "image/IE_url_line.png");
		ClickCommand clickcmd2 = new ClickCommand("image/malware_execute.png");
		ClickCommand clickcmd3 = new ClickCommand("image/malware_exe_confirm.png");
		ClickCommand clickcmd4 = new ClickCommand("image/malware_close.png");
		ClickCommand clickcmd5 = new ClickCommand("image/malware_close_confirm.png");
		
		event.addCommand(clickcmd1);
		event.addCommand(typecmd1);
		event.addCommand(clickcmd2);
		event.addCommand(clickcmd3);
		event.addCommand(clickcmd4);
		event.addCommand(clickcmd5);
		
		
		return event;
	}

	private static Event nsLookup(){
		Event event = new Event();
		ClickCommand clickcmd1 = new ClickCommand("image/window8_cmd.png","image/Command_prompt.png");
		TypeCommand typecmd1 = new TypeCommand("nslookup www.nctu.edu.tw\n");
		event.addCommand(clickcmd1);
		event.addCommand(typecmd1);
		return event;
		
	}
	
	private static Event openNotePad_win7() {
		Event event = new Event();
		ClickCommand clickcmd1 = new ClickCommand("image/window_icon.png", "image/search_icon_showup.png");
//		ClickCommand clickcmd1 = new ClickCommand("image/trash_icon.png", "image/search_icon_showup.png");
		TypeCommand typecmd1 = new TypeCommand("notepad");
		ClickCommand clickcmd2 = new ClickCommand("image/notepad_in_search.png", "image/notepad_showup.png");
		TypeCommand typecmd2 = new TypeCommand("This is OpenNotepad Test case in Win7\n");
		event.addCommand(clickcmd1);
		event.addCommand(typecmd1);
		event.addCommand(clickcmd2);
		event.addCommand(typecmd2);
 		return event;
	}

	private static Event openNotePad_win8(){
		Event event = new Event();
//		ClickCommand clickcmd = new ClickCommand(
//				"image/texteditor.png", "image/notepad_showup_win8.png", MouseAction.DOUBLECLICK);
		ClickCommand clickcmd = new ClickCommand("image/test@202.png", MouseAction.DOUBLECLICK);
		TypeCommand typecmd = new TypeCommand("This is open Notepad test!\n");
		WaitCommand wcmd = new WaitCommand(1.0);
		ComboKeyCommand combocmd1 = new ComboKeyCommand(KeyEvent.VK_CONTROL, KeyEvent.VK_S);
		ComboKeyCommand combocmd2 = new ComboKeyCommand(KeyEvent.VK_ALT, KeyEvent.VK_F4);
		event.addCommand(clickcmd);
		event.addCommand(typecmd);
		event.addCommand(wcmd);
		event.addCommand(combocmd1);
		event.addCommand(wcmd);
		event.addCommand(combocmd2);
		return event;
	}
	
	private static Event openBrowser() {
		Event event = new Event();
		TypeCommand typecommand1 = new TypeCommand("Youtube\n");
		typecommand1.setFocuspoint("image/focus_url_pos.png");
//		event.addCommand(
//				new ClickCommand("image/chrome_icon_win8.png", MouseAction.DOUBLECLICK));
		event.addCommand(
				new ClickCommand("image/chrome_icon_line.png"));
		
		event.addCommand(typecommand1);
		return event;
	}
	
	private static Event Control_Alt_Del_Combo(){
		Event event = new Event();
		ComboKeyCommand combo = new ComboKeyCommand(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_DELETE);
		event.addCommand(combo);
		return event;
	}
}
