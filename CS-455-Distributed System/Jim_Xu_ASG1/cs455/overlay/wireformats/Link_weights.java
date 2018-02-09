package cs455overlay.wireformats;

//Jim Xu

import java.io.*;

public class Link_weights {

	private int type;
	private int conNum;
	private String[] info;


	// ---------------------------**********---------------------------
	// --------------------------Link_weights--------------------------

	// Message Type: Link_Weights  // 4
	// Number of links: L
	// Link Info
	// Link Info
	// .....

	public Link_weights(int type, int conNum, String[] info){
		this.type = type; // 4
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
		System.out.println("Number of Links: " + conNum);
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
	
	public Link_weights(byte[] marshalledBytes) throws IOException { 
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
