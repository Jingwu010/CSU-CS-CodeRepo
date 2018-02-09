package cs455overlay.transport;

// Jim Xu
// Class Name:  TCPSender
// Class Intro: TCPSender is used for send data into a given socket

import java.io.*;
import java.net.*;

//send the data into the socket
public class TCPSender {
    private Socket socket;
    private DataOutputStream dout;

    public TCPSender(Socket socket) throws IOException { 
        this.socket = socket;
        dout = new DataOutputStream(socket.getOutputStream());
    } 
    
    public synchronized void sendData(byte[] dataToSend) throws IOException { 
        int dataLength = dataToSend.length; 
        //send the length of message (test : 6)
        dout.writeInt(dataLength);
        // write dataToSend array into socket
        dout.write(dataToSend, 0, dataLength);
        //make the receiver get the message immediately 
        dout.flush();

        //System.out.println("????");
        // DataInputStream din = new DataInputStream(socket.getInputStream()); 
        // System.out.println( "sender !wtf" + din.available()); 
        //the message format is length + content
    }
    
}
