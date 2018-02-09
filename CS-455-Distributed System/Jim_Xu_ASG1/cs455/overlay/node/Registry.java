package cs455overlay.node;

// Jim Xu
// Class Name: 	Registry
// Class Intro:	Registry is mainly used to connect with and send request to messagingnode
// 				Registry connect with all nodes, and create overlay and list-weights
// 				Registry then send start request, retrieve traffic info

import java.io.*;
import java.util.*;
import java.net.*;

import cs455overlay.transport.*;
import cs455overlay.util.*;
import cs455overlay.wireformats.*;

public class Registry implements Node{
	private ArrayList <Register> nodeList; 
	private ArrayList <Link> link;
	private ArrayList <TaskSummaryResponse> output;
	private String[] lwlist;
	private int[][] overlayMap;
	private int conNum;
	private int portNumber;

	public Registry(int portNumber){
		nodeList = new ArrayList <Register>();
		link = new ArrayList <Link>();
		output = new ArrayList <TaskSummaryResponse>();
		this.portNumber = portNumber;
		openServer(portNumber);
		
	}

	public void openServer(int portNumber){
		try{
			ServerSocket reServer = new ServerSocket(portNumber);
          System.out.println("Registry is running....");
			TCPServerThread tcps = new TCPServerThread(reServer, this); 
			Thread thread = new Thread(tcps);
			thread.start();
		} catch(IOException ie){
			System.out.println("Error in openServer");
		}
	}

	public void list_sockets(){
		for(int i = 0; i < link.size(); i++){
			link.get(i).list();
		}
	}
	
	public void list_messaging_nodes(){
		if( nodeList.size() == 0)
			System.out.println("No Messaging Node Now!");
		else{
			System.out.println(nodeList.size() + " nodes connected to the Registry!");
			for(int i = 0; i < 40; i++) System.out.print("-");
			System.out.printf("\n%20s%10s\n", "hostName", "port");
			for(int i = 0; i < nodeList.size(); i++){
				nodeList.get(i).list();
			}
		}
	}

	// 这个是服务器返回的 link
	public void onEvent(Link li){
		//  缓存link
		
		// 判断是否重复
		link.add(li);
		//System.out.println("I have connection with " + li.getHostName() + ":" + li.getIP() + " : " + li.getPort());
	}

	public boolean registerNode(Register register){
		if(nodeList.size() == 0){
			nodeList.add(register);
			return true;
		}

		boolean flag = true;
		for(int i = 0; i < nodeList.size(); i++){
			if(nodeList.get(i).getIP().equals(register.getIP()))
				if(nodeList.get(i).getPort() == register.getPort())
					flag = false;
		}

		if(flag == true){
			nodeList.add(register);
			return flag;
		}
		return flag;
	}

	public boolean deregisterNode(DeRegister dre){
		if(nodeList.size() == 0){
			return false;
		}

		for(int i = 0; i < nodeList.size(); i++){
			if(nodeList.get(i).getIP().equals(dre.getIP()))
				if(nodeList.get(i).getPort() == dre.getPort()){
					nodeList.remove(i);
				}
		}
		for(int i = 0; i < link.size(); i++){
			if(link.get(i).getIP().equals(dre.getIP()))
				if(link.get(i).getPort() == dre.getPort()){
					link.remove(i);
					return true;
				}
		}
		return false;
	}

	// 这个是 receiver 返回的数据
	public synchronized void onEvent(byte[] data){
		// 当注册表得到信息后
		// 是否是注册
		// 是否是注销
		
		// for (int i = 0; i < data.length; i++)
		// 	System.out.print(data[i] + " ");
		// System.out.println();
		byte[] bytes = new byte[4];
		bytes = data;
		int x = java.nio.ByteBuffer.wrap(bytes).getInt();

		try{
			
			

			if (x == 1){

				// ---------------------------**********---------------------------
				// Register

				// Message Type: REGISTER_REQUEST 	// type 1
				// Node IP address:
				// Node Port number:

				// Check--
				// (1) If the node had previously registered and has a valid entry in its registry.
				// (2) If there is a mismatch in the address that is specified in the registration request 
				// (2) and the IP address of the request (the socket’s input stream).

				Register register = new Register(data);
				
				//System.out.println("Registry got node message :");
				//register.list();
				boolean flag = true;
				if (registerNode(register) == false){
					flag = false;
					System.out.println("Node " + register.getHostName() +  " register failed!");
				} else {
					System.out.println("Node " + register.getHostName() +  " register success!");
				}

				for(int i = 0; i < link.size(); i++){
					if (link.get(i).getIP().equals(register.getIP())) {
						if(!link.get(i).getHostName().equals("")) continue;
							link.get(i).renewHostName(register.getHostName());
							link.get(i).renewPort(register.getPort());
							RegisterResponse registerresponse;
							if (flag == true) registerresponse = new RegisterResponse(1, flag, " ");
							else registerresponse = new RegisterResponse(1, flag, " ");
							
							link.get(i).getTCPS().sendData(registerresponse.getBytes());
						}
				}
			}

			if (x == 2){

				// ---------------------------**********---------------------------
				// DeRegister

				// Message Type: DEREGISTER_REQUEST 	// type 2
				// Node IP address:
				// Node Port number:

				// Check--
				// (1) where the message originated
				// (2) whether this node was previously registered. 
				// (2) Error messages should be returned in case of a mismatch in the addresses 
				// (2) or if the messaging node is not registered with the overlay

				DeRegister deregister = new DeRegister(data);

				boolean flag = true;
				if (deregisterNode(deregister) == false){
					flag = false;
					System.out.println("Node " + deregister.getHostName() +  " deregister failed!");
				} else System.out.println("Node " + deregister.getHostName() +  " deregister success!");

			}

			if (x == 7){

				// ---------------------------**********---------------------------
				// --------------------------TaskComplete--------------------------

				// Message Type: TASK_COMPLETE // type 7
				// Node Host Name:
				// Node IP address:
				// Node Port number:

				TaskComplete tc = new TaskComplete(data);
				tc.list();
			}

			if (x == 9){

				// ---------------------------**********---------------------------
				// -----------------------TaskSummaryResponse----------------------

				// Message Type: TRAFFIC_SUMMARY // 9
				// Node IP address:
				// Node Port number:
				// Number of messages sent 
				// Summation of sent messages 
				// Number of messages received 
				// Summation of received messages 
				// Number of messages relayed

				TaskSummaryResponse tcpR = new TaskSummaryResponse(data);
				output.add(tcpR);
				if(output.size() == nodeList.size())
					outPrint();
				// tc.list();
			}//case 9

		}catch (IOException ie){}

	}
	public void outPrint(){
		long numberOfmessageSent_avg 	= 0;
		long numberOfmessageReceived_avg= 0;
		double sumOfmessageSent_avg		= 0;
		double sumOfmessageReceived_avg	= 0;
		for(int i = 0; i < 120; i++)
			System.out.print("-");
		System.out.printf("\n%6s %10s %20s %20s %20s %30s\n",
							 " ", "    Number of     ", "    Number of     ",
							 	  "  Summation of    ", "  Summation of    ",
							 	  "    Number of     ");
		System.out.printf("%6s %10s %20s %20s %20s %30s\n",
							 " ", "  mesages sent    ", " mesages received ",
							 	  "   sent mesages   ", " received mesages ",
							 	  "  mesages relayed ");
		for(int i = 0; i < output.size(); i++){
			System.out.printf("%4s%-2d", "Node", i+1);
			System.out.printf("%10d", output.get(i).getNumberOfmessageSent());
			System.out.printf("%20d", output.get(i).getNumberOfmessageReceived());
			System.out.printf("%26d", output.get(i).getSumOfmessageSent());
			System.out.printf("%26d", output.get(i).getSumOfmessageReceived());
			System.out.printf("%20d\n", output.get(i).getNumberOfmessageRelayed());

			numberOfmessageSent_avg 	+= output.get(i).getNumberOfmessageSent();
			numberOfmessageReceived_avg += output.get(i).getNumberOfmessageReceived();
			sumOfmessageSent_avg 		+= output.get(i).getSumOfmessageSent();
			sumOfmessageReceived_avg	+= output.get(i).getSumOfmessageReceived();
		}

		System.out.printf("%6s%10d%20d%28.2f%28.2f\n",
							 "Sum", numberOfmessageSent_avg, numberOfmessageReceived_avg,
							 	sumOfmessageSent_avg, sumOfmessageReceived_avg);
		for(int i = 0; i < 120; i++)
			System.out.print("-");
		System.out.println();
	}

	public void setup_overlay(int conNum){



		// ---------------------------**********---------------------------
		// s-------------------------setup_overlay-------------------------

		// Check--
		// (1) it must ensure that the number of links to/from (the links are bidirectional) 
		// (1) every messaging node in the overlay is identical
		// (2) the registry must ensure that there is no partition within the overlay
		// (3) rule out some impossible solutions

		this.conNum = conNum;

		if ((nodeList.size()  % 2 == 1) && (conNum % 2 == 1) ||
		 	conNum >= nodeList.size() ){
			System.out.println("Not Possible Solution, Please Input Again:");
			return;
		}

		overlayMap = new StatisticsCollectorAndDisplay(nodeList.size(), conNum).retrieveMap();
		// for(int i = 0; i < nodeList.size(); i++){
		// 	for (int j = 0; j < nodeList.size(); j++)
		// 		System.out.print(" " + overlayMap[i][j]);
		// 	System.out.println();
		// }
		for(int i = 0; i < nodeList.size(); i++){
			String[] info = new String[conNum];
			int count = 0;
			for(int j = 0; j < nodeList.size(); j++){
				if(overlayMap[i][j] == 1) {
					info[count++] = nodeList.get(j).getHostName() + ":" 
										+ nodeList.get(j).getIP() + ":"
											+ Integer.toString(nodeList.get(j).getPort());
				}
			}
			Link l = findlink(nodeList.get(i));
			MessagingNodesList messagingnodeslist = new MessagingNodesList(3, conNum, info);
			//System.out.println("what i send is");
			//mn.list();
			try{
				l.getTCPS().sendData(messagingnodeslist.getBytes());
			}catch(IOException ie){}

		}
		System.out.println("Overlay created!");
	}

	public Link findlink(Register re){
		for(int i = 0; i < link.size(); i++){
			if (link.get(i).getIP().equals(re.getIP())
				 && link.get(i).getPort() == re.getPort()){
					return link.get(i);
				}
		}
		return null;
	}

	public void list_weights(){
		for (int i = 0; i < lwlist.length; i++){
			System.out.println(lwlist[i].toString());
		}
	}

	public void assign_weight(){
		//overlayMap
		int link_counter = 0;
		int nodeSum = nodeList.size();
		String[] info = new String[nodeSum * conNum / 2];
		lwlist = new String[(conNum * nodeList.size()) / 2];
		int cnt = 0;
		for(int i = 0; i < nodeSum; i++){
			for(int j = i; j < nodeSum; j++){
				if(overlayMap[i][j] == 1){
					Random ran = new Random();
					overlayMap[i][j] = overlayMap[j][i] = 1 + ran.nextInt(9);
					// System.out.println(link.get(i).getIP() + ":" + link.get(i).getPort()
					// 					+ " "
					// 						+link.get(j).getIP() + ":" + link.get(j).getPort()
					// 							+ " " + overlayMap[i][j]);
					info[link_counter++] = link.get(i).getHostName() + ":" + link.get(i).getIP() + ":" + link.get(i).getPort()
										+ " "
											+ link.get(j).getHostName() + ":" + link.get(j).getIP() + ":" + link.get(j).getPort()
												+ " " + overlayMap[i][j];
					lwlist[cnt++] = link.get(i).getHostName() + ".cs.colostate.edu:" + link.get(i).getPort()
										+ " "
											+ link.get(j).getHostName() + ".cs.colostate.edu:" + link.get(j).getPort()
												+ " " + overlayMap[i][j];
				}
			}
		}
		Link_weights lw = new Link_weights(4, link_counter, info);
		try{
			for(int i = 0; i < link.size(); i++){
				link.get(i).getTCPS().sendData(lw.getBytes());
			}
		}catch(IOException ie){}
	}

	public void taskStart(int rounds){
		output = new ArrayList <TaskSummaryResponse>();
		TaskInitiate ti = new TaskInitiate(5, rounds);
		try{
			for(int i = 0; i < link.size(); i++){
				link.get(i).getTCPS().sendData(ti.getBytes());
			}
		}catch(IOException ie){}

	}

	public void pull_summary(){
		TaskSummaryRequest tsr = new TaskSummaryRequest(8);
		try{
			for(int i = 0; i < link.size(); i++){
				link.get(i).getTCPS().sendData(tsr.getBytes());
			}
		}catch(IOException ie){}
	}

	public static void main(String[] args){
		int portNumber = Integer.parseInt(args[0]);
		Registry re = new Registry(portNumber);
		Scanner scanner = new Scanner(System.in);
		int step = 0;
		String systeminput = "";
		while (true){
			systeminput = scanner.nextLine();
			if (systeminput.equals("list-sockets")){
				re.list_sockets();
			}

			if (systeminput.equals("list-messaging nodes")){
				re.list_messaging_nodes();
			}

			if (systeminput.equals("list-weights")){
				re.list_weights();
			}
			 
			if (systeminput.contains("setup-overlay")){
				if(systeminput.equals("setup-overlay")){
					System.out.println("Error reading numbers!");
					break;
				}
				step = 1;
				String[] parts = systeminput.split("\\s+");
				re.setup_overlay(Integer.parseInt(parts[1]));
			}

			if (systeminput.equals("send-overlay-link-weights")){
				if (step < 1){
					System.out.println("Do setup_overlay first!");
					break;
				}
				step = 2;
				re.assign_weight();
				System.out.println("Weights assigned!");
			}
			 
			if (systeminput.contains("start")){
				if(systeminput.equals("start")){
					System.out.println("Error reading numbers!");
					break;
				}
				if (step < 2){
					System.out.println("Do list-weights first!");
					break;
				}
				step = 3;
				String[] parts = systeminput.split("\\s+");
				re.taskStart(Integer.parseInt(parts[1]));
				 // Inform messaging node to start n rounds
			}

			if (systeminput.toUpperCase().equals("PULL_TRAFFIC_SUMMARY")){
				if (step < 3){
					System.out.println("Do start first!");
					break;
				}
				step = 4;
				 re.pull_summary();
			}

			if (systeminput.equals("exit")) break;
			//System.out.println(systeminput);
		}
		scanner.close();
	}
}
