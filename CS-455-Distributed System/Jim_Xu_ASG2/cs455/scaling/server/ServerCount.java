// Jim Xu

package cs455.scaling.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ServerCount extends Thread{
	private final Server server;

	public ServerCount(Server server){
		this.server = server;
	}

	public void run(){
		while (true){
			int serverThroughput = server.getServerThroughputCount();
			int activeClient = server.getActiveClientCount();
			System.out.printf("[%s]", new SimpleDateFormat("hh.mm.ss").format(new Date()));
					System.out.printf(" Current Server Throughput:%4d messages/s, Active Client Connections:%4d\n",
						serverThroughput, activeClient);
			try{
				Thread.sleep(5000);
			}catch (InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}
}