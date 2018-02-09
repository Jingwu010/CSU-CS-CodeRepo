package cs455overlay.wireformats;

//Jim Xu

import java.io.*;

public class TaskSummaryRequest {
	private int type;

	// ---------------------------**********---------------------------
	// -----------------------TaskSummaryRequest-----------------------

	// Message Type: TaskSummaryRequest  // 8 

	public TaskSummaryRequest(int type){
		this.type = type;	//8
	}

	public int getType(){
		return type;
	}

	public void list(){
		System.out.println("Message Type: TaskSummaryRequest");
		System.out.println("------------------------------");
	}

	public byte[] getBytes() throws IOException { 
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream)); 
		
		dout.writeInt(type);
		
		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();

		return marshalledBytes;
	} 
	
	public TaskSummaryRequest(byte[] marshalledBytes) throws IOException { 
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes); 
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream)); 

		type = din.readInt();
		
		baInputStream.close(); 
		din.close();

	}
}
