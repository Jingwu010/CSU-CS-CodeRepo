package cs455overlay.wireformats;

//Jim Xu

import java.io.*;

public class TaskComplete {
	private int type;
	private int port;
	private String ipAddress;
	private String hostName;


	// ---------------------------**********---------------------------
	// --------------------------TaskComplete--------------------------

	// Message Type: TASK_COMPLETE // type 7
	// Node Host Name:
	// Node IP address:
	// Node Port number:


	public TaskComplete(int type, int port, String ipAddress, String hostName){
		this.type = type;	//7
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
		System.out.println(hostName + ":" + port + " : " + "Message Type: TASK_COMPLETE");
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
	
	public TaskComplete(byte[] marshalledBytes) throws IOException { 
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
