package cs455overlay.node;

// Jim Xu
// Class Name: 	MessagingNode
// Class Intro:	MessagingNode is used for node-node connection and communication
// 				MessagingNode also need to communicate with Registry to listen specific commands
// 				MessagingNode only connect to a certain pairs of MessagingNode

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cs455overlay.dijkstra.*;
import cs455overlay.transport.*;
import cs455overlay.wireformats.*;

public class MessagingNode implements Node{

	private String hostName;
	private String ipAddress;
	private int portNumber;

	private String registerIPaddress;
	private int registerPort;
	
	private byte[] data;
	private Thread thread;
	private TCPSender tcps;
	private Socket reSocket;
	private ArrayList <Link> link;

	private static int maxNum = 99999;
	private int cNum;	//conNum
	private ArrayList <String> sList;
	private int[][] weightMap;
	private int[] route;

	private int numberOfmessageSent;
	private int numberOfmessageReceived;
	private int numberOfmessageRelayed;
	private long sumOfmessageSent;
	private long sumOfmessageReceived;

	private Lock dataLock = new ReentrantLock();
	private Lock initiateLock = new ReentrantLock();

	public String getipAddress(){
		return ipAddress;
	}
	public int getportNumber(){
		return portNumber;
	}

	public void trafficInfoReset(){
		numberOfmessageSent 	= 0;
		numberOfmessageReceived = 0;
		numberOfmessageRelayed 	= 0;
		sumOfmessageSent 		= 0;
		sumOfmessageReceived 	= 0;
	}
	public MessagingNode(String registerIPaddress, int registerPort){
		this.registerIPaddress = registerIPaddress;
		this.registerPort = registerPort;
		link = new ArrayList <Link> ();
		sList = new ArrayList <String>();
		trafficInfoReset();
		try{
          hostName = InetAddress.getLocalHost().getHostName();
          ipAddress = InetAddress.getLocalHost().getHostAddress();
	    } catch (UnknownHostException e){
	        System.out.println("Error in getting local host");
	    }
	    Random ran = new Random();
		portNumber = ran.nextInt(5000) + 1024;
		System.out.println("MessagingNode Created, " + hostName + " : " + ipAddress);

		
	}

	public void initiate(){
		register();

		openServer();
	}
	public void openServer(){
		try{
			ServerSocket reServer = new ServerSocket(portNumber);
          System.out.println("Node " + hostName + ":" + portNumber + " is running....");
			TCPServerThread tcpS = new TCPServerThread(reServer, this); 
			Thread thread = new Thread(tcpS);
			thread.start();
		} catch(IOException ie){
			System.out.println("Error in openServer");
		}
	}

	public void onEvent(Link li){
		
	}

	public void register(){
		
		//if(registeNode(this) == 0) System.out.println("Node " + ipAddress + " successful register");

		try{
			reSocket = new Socket(registerIPaddress, registerPort);		//register is listening on 7657
			TCPReceiverThread tcpr = new TCPReceiverThread(reSocket, this);
			Thread thread = new Thread(tcpr);
			thread.start();
			Register register = new Register(1, portNumber, ipAddress, hostName);
			byte[] bytes = register.getBytes();
			tcps = new TCPSender(reSocket);
			tcps.sendData(bytes);
		} catch(IOException ie){
			System.out.println("Error in openReceiver");
		}
		

	}

	public void deregister(){
		
		//if(registeNode(this) == 0) System.out.println("Node " + ipAddress + " successful register");
		try{
			DeRegister deregister = new DeRegister(2, portNumber, ipAddress, hostName);
			byte[] bytes = deregister.getBytes();
			tcps.sendData(bytes);
		} catch(IOException ie){
			System.out.println("Error in deregister");
		}

	}

	public void onEvent(byte[] data){
		byte[] bytes = new byte[4];
		bytes = data;
		int x = java.nio.ByteBuffer.wrap(bytes).getInt();
		try{
			if (x == 0){
				
					// ---------------------------**********---------------------------
					// -------------------------SendingMessages------------------------
					// ----------------------------Message----------------------------

					// Message Type (int): Message   // 0 
				
					Message message = new Message(data);
					int []_route = message.getRoute();
				try{
					dataLock.lock();
					numberOfmessageRelayed ++;
					// Message message = new Message(0, payload, 1 ,_route);
					if (message.getIndex() == _route.length) {
						numberOfmessageReceived++;
						sumOfmessageReceived += message.getMessage();
					}
				}finally{
					dataLock.unlock();
				}
					if(message.getIndex() < _route.length){

						Message newMessage = new Message(0, message.getMessage(), message.getIndex(), message.getRoute());

						for(int i = 0; i < link.size(); i++){
							if(sList.get(_route[message.getIndex()]).contains(Integer.toString(link.get(i).getPort())))
								if(sList.get(_route[message.getIndex()]).contains(link.get(i).getIP())){
									link.get(i).getTCPS().sendData(newMessage.getBytes());
									break;
								}
						}
					}
				
				
			}//case 0

			if (x == 1){
				// ---------------------------**********---------------------------
				// -------------------------RegisterResponse-----------------------


				// Message Type (int): REGISTER_RESPONSE   // 1 
				// Status Code (byte): SUCCESS or FAILURE 
				// Additional Info (String):

				RegisterResponse registerResponse = new RegisterResponse(data);
				if (registerResponse.getStatus() == true){
					System.out.println("Successfully registered!");
				} else {
					System.out.println("Register failed!");
				}
			}//case 1


			if (x == 2){
				// ---------------------------**********---------------------------
				// -------------------------GetPort-----------------------


				// Message Type (int): REGISTER_RESPONSE   // 1 
				// Status Code (byte): SUCCESS or FAILURE 
				// Additional Info (String):

				
			}//case 2

			if (x == 3){

				// ---------------------------**********---------------------------
				// -----------------------MessagingNodesList-----------------------

				// Message Type: MESSAGING_NODES_LIST 
				// Number of peer messaging nodes: X 
				// Messaging node1 Info
				// Messaging node2 Info
				// .....
				// Messaging nodeX Info
				// info:
				// hostname:ip:port

				//String message = new String(data);
				//System.out.println("then i get message " + message);
				MessagingNodesList mnl = new MessagingNodesList(data);
				//System.out.println("-------------------\n" + hostName + " : " + ipAddress);
				cNum = mnl.getconNum();
				String[] info = mnl.getinfo();
				String[] _hostName = new String[mnl.getconNum()];
				String[] _ipAdd = new String[mnl.getconNum()];
				int[] _port = new int[mnl.getconNum()];
				
				//对info进行处理
				for(int i = 0; i < mnl.getconNum(); i++){
					String[] parts = info[i].split(":");
					_hostName[i] = parts[0];
					_ipAdd[i] = parts[1];
					_port[i] = Integer.parseInt(parts[2]);
				}
				for(int i = 0; i < mnl.getconNum(); i++){
					boolean flag = true;
					for(int j = 0; j < link.size(); j++)
						if(link.get(j).getIP().equals(_ipAdd[i])) 
							if(link.get(j).getPort() == _port[i])
								flag = false;
					//待完成：继续判断端口是否一样
					
					if (flag == false) continue;
					Socket sock = new Socket(_ipAdd[i], _port[i]);
					
					TCPReceiverThread tcpr = new TCPReceiverThread(sock, this);
	                TCPSender ntcps = new TCPSender(sock);
	                Thread thread = new Thread(tcpr);
	                thread.start();
	                Link li = new Link(_hostName[i], _ipAdd[i], _port[i], thread, ntcps);
	                link.add(li);
	                // System.out.println("successful added:");
	                // li.list();
	                // System.out.println("______________________________");
				}
				
				try{
					Thread.sleep(1000);
				}catch (InterruptedException ie){}
				for(int i = 0; i < link.size(); i++){
					for(int j = 0; j < mnl.getconNum(); j++)
						if(link.get(i).getIP().equals(_ipAdd[j]))
							if(link.get(i).getHostName().length() == 0){
								link.get(i).renewHostName(_hostName[j]);
								link.get(i).renewPort(_port[j]);
							}
							
				}
			}// case 3

			
			if (x == 4){

				// ---------------------------**********---------------------------
				// --------------------------Link_weights--------------------------

				// Message Type: Link_Weights  // 4
				// Number of links: L
				// Link Info
				// Link Info
				// .....

				// (1) A messaging node should process this message and store its information to 
				// (1) generate routing paths for messages as explained in the following section
				// (2) Further, it should acknowledge the receipt and processing of this message by printing the message 
				// (2) “Link weights are received and processed. Ready to send messages.” 
				// (2) to the console.

				Link_weights lw = new Link_weights(data);

				String[] info = lw.getinfo();
				int nNum = lw.getconNum() * 2 / cNum;
				weightMap = new int[nNum][nNum];
				route = new int[nNum];
				for(int i = 0; i < nNum; i++){
					for(int j = 0; j < nNum; j++)
						weightMap[i][j] = maxNum;
					route[i] = -1;
				}

				int[] _length = new int[lw.getconNum()];

				//对info进行处理
				for(int i = 0; i < lw.getconNum(); i++){
					String[] parts = info[i].split("\\s+");
					if (i == 0) {
						sList.add(parts[0]);
						sList.add(parts[1]);
						weightMap[0][1] = weightMap[1][0] = Integer.parseInt(parts[2]);
						continue;
					}
					int[] index = new int[2];
					for(int j = 0; j < 2; j++){
						boolean flag = false;
						for(int k = 0; k < sList.size(); k++){
							if(sList.get(k).equals(parts[j])){
								index[j] = k;
								flag = true;
								break;
							}
						}
						//如果在sList中没有找到
						if(flag == false){
							sList.add(parts[j]);
							index[j] = sList.size()-1;
						}
					}
					weightMap[index[0]][index[1]] = weightMap[index[1]][index[0]] = Integer.parseInt(parts[2]);
					
				}

				// for(int i = 0; i < nNum; i++){
				// 	for(int j = 0; j < nNum; j++)
				// 		System.out.printf("%-6d ", weightMap[i][j]);
				// 	System.out.println();
				// }
				
				// ---------------------------**********---------------------------
				// use the weightMap to get caculate the shortest path 
				// RoutingCache(int nodeSum, int sourceNode, int sinkNode, int[][] weight, int maxweight)
				// return the shortest route to any node of the start point

				int index = 0;

				for(int k = 0; k < sList.size(); k++)
					if(sList.get(k).contains(ipAddress)
						&& sList.get(k).contains(Integer.toString(portNumber))) index = k;
				int k = 0;
				if (k == index) k++;
				RoutingCache rc = new RoutingCache(nNum, index, k, weightMap, maxNum);
				route = rc.getRoute();
				// for(int i = 0; i < nNum; i++){
				// 		System.out.printf("%-4d ", route[i]);
				// 	}
				System.out.println("Link weights are received and processed. Ready to send messages.");
				

				// ---------------------------**********---------------------------
				// Now get the shorest path array.
				// and the index is equals with index in sList(index == ipAddress)
				// return the shortest route to any node of the start point
			}//case 4

			if ( x == 5){


				// ---------------------------**********---------------------------
				// --------------------------TaskInitiate--------------------------

				// Message Type: TaskInitiate  // 5
				// Rounds: X
				try {
					initiateLock.lock();
					TaskInitiate ti = new TaskInitiate(data);
					int rounds = ti.getRounds() * 5;
					int nodeIndex = 0;
					for(int i = 0; i < sList.size(); i++){
						if(sList.get(i).contains(Integer.toString(portNumber)))
							if(sList.get(i).contains(ipAddress)){
								nodeIndex = i; break;
							}
					}

					// Start sending messages
					// each MessageNode starts x rounds
					// in each round choose an random destination
					// the route is stored in bytes array
					// a random num is stored in the fronest 
					for(int i = 0; i < rounds; i++){
						numberOfmessageSent++;
						Random ra = new Random();
						int des = 0;
						while(true){ 
							des = ra.nextInt(sList.size());
							if(des != nodeIndex) break;
						}
						// System.out.println("\nfrom " + nodeIndex + " to " + des + " : ");
						int payload = ra.nextInt();
						sumOfmessageSent += payload;
						int couts = 0;
						Stack <Integer> stack = new Stack<>();
						while(true){
							couts ++;
							stack.push(des);
							des = route[des];
							if(des == -1) break;
						}

						int[] _route = new int[couts];
						couts = 0;
						while(!stack.empty())
							_route[couts++] = stack.pop();
						
						// Message(int type, int message, int[] route)
						Message message = new Message(0, payload, 1 ,_route);

						// ERROR
						// Exception in thread "Thread-0" java.lang.ArrayIndexOutOfBoundsException: 1
						// at MessagingNode.onEvent(MessagingNode.java:383)
						// at TCPReceiverThread.run(TCPReceiverThread.java:33)
						// at java.lang.Thread.run(Thread.java:745)
						// for(int j = 0; j < couts; j++)
						// 	System.out.print(" " + _route[j]);
						// System.out.println();

						// for(int j = 0; j < sList.size(); j++)
						// 	System.out.print(" " + sList.get(j).toString());
						// System.out.println();

						for(int j = 0; j < link.size(); j++){
							if(sList.get(_route[1]).contains(link.get(j).getIP()))
								if(sList.get(_route[1]).contains(Integer.toString(link.get(j).getPort()))){
									link.get(j).getTCPS().sendData(message.getBytes());
									break;
								}
						}
						
					}
					TaskComplete tc = new TaskComplete(7, portNumber, ipAddress, hostName);
					tcps.sendData(tc.getBytes());
				}finally{
					initiateLock.unlock();
				}
			}// case 5

			if (x == 8){

				// ---------------------------**********---------------------------
				// -----------------------TaskSummaryRequest-----------------------

				// Message Type: TaskSummaryRequest  // 8 

				// private int numberOfmessageSent;
				// private int numberOfmessageReceived;
				// private int numberOfmessageRelayed;
				// private long sumOfmessageSent;
				// private long sumOfmessageReceived;

				TaskSummaryResponse tsr = 
						new TaskSummaryResponse(9, portNumber, ipAddress,
													numberOfmessageSent, numberOfmessageReceived, numberOfmessageRelayed,
														sumOfmessageSent, sumOfmessageReceived);
				tcps.sendData(tsr.getBytes());
				trafficInfoReset();
			}
		}catch (IOException ie){}
		
	}

	public void show_connection(){
		System.out.println("----------------------------");
		System.out.println(hostName + ".cs.colostate.edu:" + portNumber);
		for(int i = 0; i < link.size(); i++)
			System.out.println("connected with " + link.get(i).getHostName() + 
				".cs.colostate.edu:" + link.get(i).getPort());
		System.out.println("----------------------------");
	}

	public void print_shortest_path(){
		System.out.println("---------------------------");
		int index = 0;
		for(int i = 0; i < sList.size(); i++){
			System.out.println(i + " --> " + sList.get(i).toString());
		}
		
		System.out.println("---------------------------");
		for(int i = 0; i < route.length ; i++){
			for(int k = 0; k < sList.size(); k++)
				if(sList.get(k).contains(ipAddress))
					if(sList.get(k).contains(Integer.toString(portNumber))) index = k;

			if( i == index ) continue;
			int tmp = i;
			int couts = 0;
			Stack <Integer> stack = new Stack<>();
			while(true){
				couts ++;
				stack.push(tmp);
				tmp = route[tmp];
				if(tmp == -1) break;
			}

			int[] _route = new int[couts];
			couts = 0;
			while(!stack.empty())
				_route[couts++] = stack.pop();

			for(int j = 0; j < couts; j++)
				System.out.printf(" " + _route[j]);
			System.out.print("\n" + sList.get(index).toString());
			for(int j = 1; j < couts; j++){
				System.out.print(" --" + weightMap[index][_route[j]] + "--> ");
				System.out.print(sList.get(_route[j]).toString());
				index = _route[j];
			}
			System.out.println();
		}
		System.out.println("---------------------------");
	}
	public static void main(String[] args){
		String registerIPaddress = args[0];
		int registerPort = Integer.parseInt(args[1]);
		MessagingNode node = new MessagingNode(registerIPaddress, registerPort);
		node.initiate();

		Scanner scanner = new Scanner(System.in);
		String systeminput = "";

		while (true){
			systeminput = scanner.nextLine();
			
			if (systeminput.equals("register")){
				node.register();
			}

			if (systeminput.equals("deregister")){
				node.deregister();
			}
			
			if (systeminput.equals("show-connection")){
				node.show_connection();
			}

			if (systeminput.equals("print-shortest-path")){
				node.print_shortest_path();
			}

			if (systeminput.equals("exit-overlay")){
				node.deregister();
				break;
			}

			//System.out.println(systeminput);
		}
		scanner.close();
	}
}
