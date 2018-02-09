// Jim Xu

package cs455.scaling.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientCount extends Thread{
	private final Client client;
	private final boolean listSizeMessage;
	public ClientCount(Client client, boolean listSizeMessage){
		this.client = client;
		this.listSizeMessage = listSizeMessage;
	}

	public void run(){
		while (true){
			int datasent = client.getDataSentCount();
			int datareceive = client.getDataReceiveCount();
			if (listSizeMessage == true)
				System.out.println("hashList size = " + client.getLinksize());
			System.out.printf("[%s]", new SimpleDateFormat("hh.mm.ss").format(new Date()));
					System.out.printf(" Total Sent Count:%4d, Total Received Count:%4d\n",
						datasent, datareceive);
			try{
				Thread.sleep(10000);
			}catch (InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}
}