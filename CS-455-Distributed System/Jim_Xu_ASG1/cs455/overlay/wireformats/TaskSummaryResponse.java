package cs455overlay.wireformats;

//Jim Xu

import java.io.*;

public class TaskSummaryResponse {
	private int type;
	private int port;
	private String ipAddress;
	private int numberOfmessageSent;
	private int numberOfmessageReceived;
	private int numberOfmessageRelayed;
	private long sumOfmessageSent;
	private long sumOfmessageReceived;

	// ---------------------------**********---------------------------
	// -----------------------TaskSummaryResponse----------------------

	// Message Type: TRAFFIC_SUMMARY // 9
	// Node IP address:
	// Node Port number:
	// Number of messages sent 
	// Summation of sent messages 
	// Number of messages received 
	// Summation of received messages 
	// Number of messages relayed

	public TaskSummaryResponse(int type, int port, String ipAddress,
									int numberOfmessageSent, int numberOfmessageReceived, int numberOfmessageRelayed,
										long sumOfmessageSent, long sumOfmessageReceived){
		this.type = type;	//9
		this.port = port;
		this.ipAddress = ipAddress;
		this.numberOfmessageSent 	= numberOfmessageSent;
		this.numberOfmessageReceived= numberOfmessageReceived;
		this.numberOfmessageRelayed = numberOfmessageRelayed;
		this.sumOfmessageSent 		= sumOfmessageSent;
		this.sumOfmessageReceived 	= sumOfmessageReceived;
	}

	public int getType(){
		return type;
	}
	
	public int getPort(){
		return port;
	}

	public String getIP(){
		return ipAddress;
	}

	public int getNumberOfmessageSent(){
		return numberOfmessageSent;
	}
	public int getNumberOfmessageReceived(){
		return numberOfmessageReceived;
	}
	public int getNumberOfmessageRelayed(){
		return numberOfmessageRelayed;
	}
	public long getSumOfmessageSent(){
		return sumOfmessageSent;
	}
	public long getSumOfmessageReceived(){
		return sumOfmessageReceived;
	}
	public synchronized void list(){
		System.out.println("Message Type: TRAFFIC_SUMMARY");
		System.out.println("IP Address : " + ipAddress);
		System.out.println("Port Number: " + port);
		System.out.println("Number of message sent : " 			+ numberOfmessageSent);
		System.out.println("Summation of sent messages: "		+ sumOfmessageSent);
		System.out.println("Number of message received : " 		+ numberOfmessageReceived);
		System.out.println("Summation of received messages : "	+ sumOfmessageReceived);
		System.out.println("Number of message relayed: " 		+ numberOfmessageRelayed);
		System.out.println("------------------------------");
	}

	public byte[] getBytes() throws IOException { 
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream)); 
		
		dout.writeInt(type);
		dout.writeInt(port);
		
		byte[] identifierBytes = ipAddress.getBytes(); 
		int elementLength = identifierBytes.length; 
		dout.writeInt(elementLength); 
		dout.write(identifierBytes);

		dout.writeInt(numberOfmessageSent);
		dout.writeInt(numberOfmessageReceived);
		dout.writeInt(numberOfmessageRelayed);
		
		dout.writeLong(sumOfmessageSent);
		dout.writeLong(sumOfmessageReceived);

		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();

		return marshalledBytes;
	} 
	
	public TaskSummaryResponse(byte[] marshalledBytes) throws IOException { 
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes); 
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream)); 

		type = din.readInt();
		port = din.readInt();		

		int identifierLength = din.readInt();
		byte[] identifierBytes = new byte[identifierLength]; 
		din.readFully(identifierBytes);
		
		ipAddress = new String(identifierBytes); 

		numberOfmessageSent 	= din.readInt();
		numberOfmessageReceived = din.readInt();
		numberOfmessageRelayed 	= din.readInt();
		sumOfmessageSent 		= din.readLong();
		sumOfmessageReceived 	= din.readLong();
		
		baInputStream.close(); 
		din.close();

	}
}
