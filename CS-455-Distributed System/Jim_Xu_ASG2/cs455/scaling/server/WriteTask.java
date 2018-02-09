// Jim Xu

package cs455.scaling.server;

import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.nio.channels.SelectionKey;

public class WriteTask implements Task{
	private SelectionKey key;
	private Server server;

	public WriteTask(Server server, SelectionKey key){
		this.server = server;
		this.key = key;
	}
	
	public void func(){
		try{
		    server.write(key);
		} catch (IOException ie){
			ie.printStackTrace();
			System.out.println("IOException");
		}
	}
}