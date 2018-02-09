package cs455overlay.wireformats;

//Jim Xu

import java.io.*;

public class DeRegister {
	private int type;
	private int port;
	private String ipAddress;
	private String hostName;

	// ---------------------------**********---------------------------
	// DeRegister

	// Message Type: DEREGISTER_REQUEST 	// type 2
	// Node IP address:
	// Node Port number:

	// Check--
	// (1) where the message originated
	// (2) whether this node was previously registered. 
	// (2) Error messages should be returned in case of a mismatch in the addresses 
	// (2) or if the messaging node is not registered with the overlay
	
	public DeRegister(int type, int port, String ipAddress, String hostName){
		this.type = type;
		this.port = port;
		this.ipAddress = ipAddress;
		this.hostName = hostName;
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

	public String getHostName(){
		return hostName;
	}

	public void list(){
		System.out.println("Message Type: DEREGISTER_REQUEST");
		System.out.println("Host Name : " + hostName);
		System.out.println("IP Address : " + ipAddress);
		System.out.println("Port Number: " + port);
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

		identifierBytes = hostName.getBytes(); 
		elementLength = identifierBytes.length; 
		dout.writeInt(elementLength); 
		dout.write(identifierBytes);
		
		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();
		return marshalledBytes;
	} 
	
	public DeRegister(byte[] marshalledBytes) throws IOException { 
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes); 
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream)); 

		type = din.readInt();
		port = din.readInt();		

		int identifierLength = din.readInt();
		byte[] identifierBytes = new byte[identifierLength]; 
		din.readFully(identifierBytes);
		ipAddress = new String(identifierBytes); 
		

		identifierLength = din.readInt();
		identifierBytes = new byte[identifierLength]; 
		din.readFully(identifierBytes);
		hostName = new String(identifierBytes); 
		
		baInputStream.close(); 
		din.close();

	}


}