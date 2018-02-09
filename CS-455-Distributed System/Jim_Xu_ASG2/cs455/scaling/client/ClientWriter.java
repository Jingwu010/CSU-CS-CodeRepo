// Jim Xu

package cs455.scaling.client;

import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.nio.channels.SelectionKey;

public class ClientWriter extends Thread{
	
	private Client client;
	private SelectionKey key;
	private int message_rate;

	public ClientWriter(Client client, int message_rate, SelectionKey key){
		this.client = client;
		this.message_rate = message_rate;
		this.key = key;
	}

	public synchronized void run(){
		while(true){
            try{
            	client.write(key);
            	Thread.sleep(1000/message_rate);
            } catch (IOException ie){
            	ie.printStackTrace();
				System.out.println("IOException");
			} catch (NoSuchAlgorithmException ae){
				ae.printStackTrace();
				System.out.println("NoSuchAlgorithmException");
			} catch(InterruptedException ie){
		    	ie.printStackTrace();
		    }
		}
	}
}