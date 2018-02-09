package cs455overlay.wireformats;

//Jim Xu

import java.io.*;

public class Register {
	private int type;
	private int port;
	private String ipAddress;
	private String hostName;

	// ---------------------------**********---------------------------
	// Register

	// Message Type: REGISTER_REQUEST 	// type 1
	// Node IP address:
	// Node Port number:
	// Node name: 

	// Check--
	// (1) If the node had previously registered and has a valid entry in its registry.
	// (2) If there is a mismatch in the address that is specified in the registration request 
	// (2) and the IP address of the request (the socket’s input stream).

	public Register(int type, int port, String ipAddress, String hostName){
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
		System.out.printf("%20s%10d\n", hostName, port);
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
	
	public Register(byte[] marshalledBytes) throws IOException { 
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