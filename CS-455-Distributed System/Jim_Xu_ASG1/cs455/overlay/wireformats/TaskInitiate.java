package cs455overlay.wireformats;

//Jim Xu

import java.io.*;

public class TaskInitiate {
	private int type;
	private int rounds;

	// ---------------------------**********---------------------------
	// --------------------------TaskInitiate--------------------------

	// Message Type: TaskInitiate  // 5
	// Rounds: X

	public TaskInitiate(int type, int rounds){
		this.type = type;	//5
		this.rounds = rounds;
	}

	public int getType(){
		return type;
	}
	
	public int getRounds(){
		return rounds;
	}

	public void list(){
		System.out.println("Message Type: TASK_INITIATE");
		System.out.println("Rounds : " + rounds);
		System.out.println("------------------------------");
	}

	public byte[] getBytes() throws IOException { 
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream)); 
		
		dout.writeInt(type);
		dout.writeInt(rounds);
		
		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();

		return marshalledBytes;
	} 
	
	public TaskInitiate(byte[] marshalledBytes) throws IOException { 
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes); 
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream)); 

		type = din.readInt();
		rounds = din.readInt();		
		
		baInputStream.close(); 
		din.close();

	}
}
