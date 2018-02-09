 package cs455overlay.node;

// Jim Xu
// Class Name: 	Link
// Class Intro:	Link is used for combining hostname, ip, port and correspondingly
// 				tcps: TCPSender instance
// 				tcpr: TCPReceiver instance

 
import cs455overlay.transport.*;

public class Link {
	private String hostname;
	private String ip;
	private int port;
	private TCPSender tcps;
	private Thread tcpr;

	public Link(String hostname, String ip, int port, Thread tcpr, TCPSender tcps){
		this.hostname = hostname;
		this.ip = ip;
		this.port = port;
		this.tcpr = tcpr;
		this.tcps = tcps;
	}

	public void renewHostName(String hostname){
		this.hostname = hostname;
	}

	public void renewPort(int port){
		this.port = port;
	}

	public String getHostName(){
		return hostname;
	}

	public String getIP(){
		return ip;
	}

	public int getPort(){
		return port;
	}

	public Thread getTCPR(){
		return tcpr;
	}

	public TCPSender getTCPS(){
		return tcps;
	} 

	public void list(){
		System.out.println("Connected with " + hostname + ".cs.colostate.edu:" + port);
	}
}