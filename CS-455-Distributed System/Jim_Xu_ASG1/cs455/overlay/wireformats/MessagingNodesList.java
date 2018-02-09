package cs455overlay.wireformats;

//Jim Xu

import java.io.*;

public class MessagingNodesList {

	private int type;
	private int conNum;
	private String[] info;

	// ---------------------------**********---------------------------
	// MessagingNodesList

	// Message Type: MESSAGING_NODES_LIST  // 3
	// Number of peer messaging nodes: X 
	// Messaging node1 Info
	// Messaging node2 Info
	// .....
	// Messaging nodeX Info

	// Check--
	// (1) If the node had previously registered and has a valid entry in its registry.
	// (2) If there is a mismatch in the address that is specified in the registration request 
	// (2) and the IP address of the request (the socket’s input stream).

	public MessagingNodesList(int type, int conNum, String[] info){
		this.type = type; // 2
		this.conNum = conNum;
		this.info = new String[conNum];
		
		for(int i = 0; i < conNum; i++){
			this.info[i] = info[i];
		}
	}

	public int getType(){
		return type;
	}
	
	public int getconNum(){
		return conNum;
	}

	public String[] getinfo(){
		return info;
	}

	public void list(){
		System.out.println("Message Type: MESSAGING_NODES_LIST");
		System.out.println("Number of peer messaging nodes: " + conNum);
		for(int i = 0; i < conNum; i++){
			System.out.println(info[i].toString());
		}
	}
	public byte[] getBytes() throws IOException { 
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream)); 
		
		dout.writeInt(type);
		dout.writeInt(conNum);

		for(int i = 0; i < conNum; i++){
			byte[] identifierBytes = info[i].getBytes(); 
			int elementLength = identifierBytes.length; 
			dout.writeInt(elementLength); 
			dout.write(identifierBytes);
		}

		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();

		return marshalledBytes;
	} 
	
	public MessagingNodesList(byte[] marshalledBytes) throws IOException { 
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes); 
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream)); 

		type = din.readInt();
		conNum = din.readInt();		
		info = new String[conNum];
		for(int i = 0; i < conNum; i++){
			int identifierLength = din.readInt();
			byte[] identifierBytes = new byte[identifierLength]; 
			din.readFully(identifierBytes);
			info[i] = new String(identifierBytes); 
		}
		
		baInputStream.close(); 
		din.close();

	}
}
