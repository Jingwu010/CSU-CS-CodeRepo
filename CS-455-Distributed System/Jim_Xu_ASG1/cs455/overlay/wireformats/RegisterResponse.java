package cs455overlay.wireformats;

//Jim Xu

import java.io.*;

public class RegisterResponse {
	private int type;
	private boolean status;
	private String info;

	// ---------------------------**********---------------------------
	// RegisterResponse

	// Message Type (int): REGISTER_RESPONSE   // 1 
	// Status Code (byte): SUCCESS or FAILURE 
	// Additional Info (String):

	public RegisterResponse(int type, boolean status, String info){
		this.type = type;
		this.status = status;
		this.info = info;
	}

	public int getType(){
		return type;
	}
	
	public boolean getStatus(){
		return status;
	}

	public String getInfo(){
		return info;
	}

	public void list(){
		System.out.println("Message Type: REGISTER_RESPONSE");
		if (status == true) System.out.println("Status Code : SUCCESS");
		else if (status == false) System.out.println("Status Code : FAILURE");
		System.out.println("Additional Info : " + info);
		System.out.println("------------------------------");
	}

	public byte[] getBytes() throws IOException { 
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream)); 
		
		dout.writeInt(type);
		dout.writeBoolean(status);
		
		byte[] identifierBytes = info.getBytes(); 
		int elementLength = identifierBytes.length; 
		dout.writeInt(elementLength); 
		dout.write(identifierBytes);

		dout.write(identifierBytes);
		
		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();

		return marshalledBytes;
	} 
	
	public RegisterResponse(byte[] marshalledBytes) throws IOException { 
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes); 
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream)); 

		type = din.readInt();
		status = din.readBoolean();		

		int identifierLength = din.readInt();
		byte[] identifierBytes = new byte[identifierLength]; 
		din.readFully(identifierBytes);
		info = new String(identifierBytes); 
		
		baInputStream.close(); 
		din.close();

	}
}