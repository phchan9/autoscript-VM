#Secmon_automation
 

This project is a automation tool based on SikuliX-2014 opensource project. The main usage of this project is to manipulate the keyboard and the mouse through VNC connection so that we can execuate some activities automatically. There are a lot of examples in [SikuliX official Website](http://www.sikulix.com/), if we just use it to finish some regular work on our PC.When we come to virtualization environment ( like Xen or Kvm), could we still use *silulix* to go through VNC connection to maniplate a pair of mouse and keyboard which is might be emulated by QEMU? In this project, we solve this problem by the VNC support of SikuliX.  Thus ,***Secmom_automation*** contains two module : 
> * SikuliX-2014  
* automation_script

## SikuliX-2014
In this tool, we mainly use the *sikulixapi* this module to make us work easier. We can reference the documentation rom [SiuliX-2014](https://github.com/RaiMan/SikuliX-2014). There are plenty of information about how it works. In SikuliX-2014, we use the package of *edu.unh.iol.dlc*(which is the packaging way of Java), which contains the following source code:
> * ConnectionController.java
* FBConfig.java
* Frambebuffer.java
* VNCClient.java
* VNCRobot.java
* VNCScreen.java
* VNCThread.java    

Using them above to make VNC connection , encoding negotiation,authentication and the most important part : execuate mouse and keyboard events. 



## automate_script
This part consists of serveral objects to wrap the API of *SikuliX* . The major components of this part are following:  
> * Server  
* Client 
* Command
* Event
* ServiceHandler

Briefly, there are serveral ***clients*** (VMs , every VM has its own vncserver so we use this to push our command into it.) and our ***Server*** is located in DOM0 (This is the way what we talk in Xen virtualization). Here are examples :

```java
Server server = new Server();
Client vm = new Client("vm1","localhost", 5902);  
/* first argument is self-defined name of client, second one is IP , third one is port */
server.newConnection(vm3);   /* buld the connection */
server.initClientScreens();  /* build the Screen object 
( main manipulated object in SikuliX )of Client */

```

Then, we create the ***Event*** ( or ***Command***) object to define the keyboard or mouse event. After defining the event, we put the event to ***ServiceHandler*** which can execuate with ***multithreading*** the Command or Event and also can handle the Error or Exception when it happened at runtime. Here are examples:

```java
    /* each client has its corresponding servicehandler */
    server.getHandler("vm1").addService(nsLookup()); 
    /* first argument is the name you made for Client, 
     * second one is the function return a event, which makes browser download the malware,
     * we talk this later. */
    server.getHandler("vm3").addService(wget_malware());
    server.doService();
```

Lastly, close the VNC connection.
```java
    server.closeAllConnection();
    /* or we can server.closeConnection("vm1") to close specific connection */
    /* we can use listconnection to show the active connections */
    server.listConnection();
```

The following is the example about how does wget_malware() function defined:
```java
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
```
The following is the constructor and the setter of ClickCommand class:
```java 
    /** 
	 * Constructor of ClickCommand
	 * 
	 * @param target  the filename of the images which will be clicked
	 * @param check  the filename of the images which will be checked for response
	 * @param type  Doubleclick, RightClick, (Left)Click  
	 */
	public ClickCommand(String target, String check, MouseAction type) {
		super(target, check);
		this.type = type;
	}
	
	/**
	 * Set the modifer which key will be pressed when you click the image at the same time
	 * @param modifier  like Control,Shift, Alt
	 */
	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
```
The following is the constructor of TypeCommand class:
```java
	/**
	 * The Constructor of TypeCommand 
	 * @param target  the string you expect to type 
	 * @param check  the filename of the image which will be checked for response
	 * @param focuspoint  the point you click before you type the sring
	 */
	public TypeCommand(String target, String check, String focuspoint) {
		super(target, check);
		this.focuspoint = focuspoint;
	}
    /* just ignore the setModifier() ,the same as Clickcommand */
```
The following is the constructor of ComboKeyCommand class:
```java
	/**
	 * This class is used to press several keys meanwhile e.g. Contorl+Alt+Del
	 * 
	 * The Constructors of ComboKeyCommand 
	 * @param target  null often
	 * @param check  the filename of the image which will be checked for response
	 * @param keys  several keys to press, using Key library of java e.g. KeyEvent.VK_CONTROL
	 */
	public ComboKeyCommand(String target, String check, int... keys ) {
		super(target, check);
		this.addComboKey(keys);
	}

```
The following is the feedBackCheck() source code:
```java
    /**
     * This function is used to check the response after you type or click,
     * and called by execuate() which is method of Command class
     * /
	public void feedBackCheck(Client client) throws FindFailed {
		
		VNCScreen screen = (VNCScreen) client.getScreen();
		if( check == null){
			Debug.log(3, "Ignore the response check\n");
			return;
		}
		try {
			screen.wait(check);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			throw new FindFailed( "@FeedBackFunction() in "+ client.hostname + " " + e.getMessage());
		}
	}
```
Recap of the above all:
```java
    public static void main(String[] args) throws InterruptedException{
        Server server = new Server();
        Client vm = new Client("vm1", "localhost", 5901);
        server.newConnection(vm);
        server.initClientScreens();
        
        Thread.sleep(2000);  /* To Make sure the VNCThread is created */
        server.getHandler("vm1").addService(openBrowser());
        server.doService();
        server.closeConnection("vm1");
        System.exit(0);
    }
```

##Installation and Usage guide  
###Prerequisite
Because SikuliX is based on OpenCV and Tesseract OCR , you should install the following:
> * OpenCV 2.3.1  --released by [Official Website](http://opencv.org/downloads.html)  ,but I found some bug when compile , so I put my fixed source code in the folder.
    (if yours is 2.4 , you need to build antoher one libVision.so ,use this [file](https://launchpad.net/sikuli/sikulix/1.0.1/+download/Sikuli-1.0.1-Supplemental-LinuxVisionProxy.zip) , there is a guide in it.) 
* Tesseact 3.0.2(+)
* cmake  (used to compile the OpenCV)    
  If you have any problem about how to build the opencv from source code, reference
  [Official Installation Guide](http://docs.opencv.org/doc/tutorials/introduction/linux_install/linux_install.html#linux-installation)

###Installation and Usage

To compile and generate code:
> $ cd secmon_automation  
 $ mvn clean install    
  
To Execuate the automate_script project
> $ cd automate_script  
$ mvn exec:java  


###Image location  
The default image path under the automate_script/ when you execuate in automate_script/ folder ,so I recommand that put your image files into automate_script/image/ folder. Of course, if you are a expert in Java , feel free to modify this path.


###Some Bugs Info
When you get the Headlessexception, it means you might be in the pure commandline mode.
Add this sytem.property to avoid it.
>  mvn exec:java -Djava.awt.headless=false

You can open the debug level ifo to check more problem when you develop it
>  mvn exec:java -Dsikuli.Debug=3

There are some error info everytime like below , you can ignore it ,cuz it can't interrupt your runtime.
> [error] ImagePath: setBundlePath: invalid BundlePath: null 

