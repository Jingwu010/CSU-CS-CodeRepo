package cs455overlay.transport;

// Jim Xu
// Class Name:  TCPServerThread
// Class Intro: TCPServerThread is used for listenning incoming socket connect

import java.io.*;
import java.net.*;
import java.util.*;

import cs455overlay.node.Link;
import cs455overlay.node.Node;

// as a server thread
// one can always make connection to another server to send message
// while at the same time, 
// server must listen to any other incoming message.
// accept incoming TCP communications
public class TCPServerThread implements Runnable{
    private ServerSocket serverSocket;
    private boolean debug;
    private Socket socket;
    private Node node;
    private byte[] emptyArray;

    public TCPServerThread(ServerSocket serverSocket, Node node) { 
        this.serverSocket = serverSocket;
        this.node = node;
        this.socket = null;
        this.debug = true;
        emptyArray = new byte[0];
    } 

    //runnable thread accepting incoming connections
    public synchronized void run(){
        while(true){
            try{
                //每当一个主机建立连接后，TCP接收线程启动，一个主机只能被呼唤4次
                //每个主机建立连接后启动接收线程,接受线程不停顿的执行run方法
                socket = serverSocket.accept();
                String ip = socket.getInetAddress().getHostAddress();
                int port = socket.getPort();
                TCPReceiverThread tcpr = new TCPReceiverThread(socket,node);
                TCPSender tcps = new TCPSender(socket);
                Thread thread = new Thread(tcpr);
                thread.start();
                Link li = new Link("", ip, port, thread, tcps);
                node.onEvent(li);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    


    
}
