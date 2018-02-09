package cs455overlay.wireformats;

//Jim Xu

import java.io.*;

public class Message {
	private int type;
	private int message;
	private int index;
	private int[] route;

	// ---------------------------**********---------------------------
	// -------------------------SendingMessages------------------------
	// ----------------------------Protocol----------------------------

	// Message Type (int): Protocol   // 0 
	
	public Message(int type, int message, int index, int[] route){
		this.type = type;		//0
		this.message = message;
		this.index = index;
		this.route = route;
	}
	
	public int getType(){
		return type;
	}
	
	public int getMessage(){
		return message;
	}

	public int getIndex(){
		return index;
	}

	public int[] getRoute(){
		return route;
	}

	public byte[] getBytes() throws IOException { 
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream)); 
		
		dout.writeInt(type);
		dout.writeInt(message);
		dout.writeInt(index);
		dout.writeInt(route.length);
		
		for(int i = 0; i < route.length; i++) {
		      dout.writeInt(route[i]);
		}
		
		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();
		return marshalledBytes;
	} 
	
	public Message(byte[] marshalledBytes) throws IOException { 
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes); 
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream)); 

		type = din.readInt();
		message = din.readInt();		
		index= din.readInt()+1;
		int len = din.readInt();
		route = new int[len];
		for(int i = 0; i < len; i++) {
		    route[i] = din.readInt();
		}
		
		baInputStream.close(); 
		din.close();

	}


}