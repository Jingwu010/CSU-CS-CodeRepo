package cs455overlay.transport;

// Jim Xu
// Class Name:  TCPReceiverThread
// Class Intro: TCPReceiverThread is used for listening the incoming data
//              from the given socket

import java.io.*;
import java.net.*;

import cs455overlay.node.Node;


// class for receive data
// get an agruement socket and get the data from it
public class TCPReceiverThread implements Runnable{
    private Socket socket;          //socket for transport data
    private DataInputStream din;  
    private Node node;

    // Client Handler
    public TCPReceiverThread(Socket socket, Node node) throws IOException { 
        this.socket = socket;
        this.node = node;
        din = new DataInputStream(socket.getInputStream()); 
    } 
    //当接收线程被唤起后，不断循环读取socket中内容，大部分时间卡在din.readxxx;
    //当且仅当socket被关闭后，接收线程退出
    public synchronized void run() {
        int dataLength = 0;
        try {
        while (socket != null) {
            
                //System.out.println(socket.getLocalPort() + " | " + socket.getPort() +  " is looping");
                dataLength = din.readInt();
                //if (dataLength == -1) break;
                byte[] data = new byte[dataLength]; 
                din.readFully(data, 0, dataLength);

                
                node.onEvent(data);
                //if (socket.isClosed()) break;
                // for(int i = 0; i < dataLength; i++)
                //     System.out.print(" " + data[i]);
                
                
                

                //困难是如何在tcpreceiver中选出下一个目的地
                }
            } catch (SocketException se){ 
                //System.out.println("Socket closed");
            } catch (IOException ioe) { 
                //System.out.println("Error in receiver thread");
            }
        
    }  
}  