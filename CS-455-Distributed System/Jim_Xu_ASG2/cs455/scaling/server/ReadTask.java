// Jim Xu

package cs455.scaling.server;

import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.nio.channels.SelectionKey;

public class ReadTask implements Task{
	private SelectionKey key;
	private Server server;

	public ReadTask(Server server, SelectionKey key){
		this.server = server;
		this.key = key;
	}
	
	public void func(){
		try{
			server.read(key);
		} catch (IOException ie){
			ie.printStackTrace();
			System.out.println("IOException");
		} catch (NoSuchAlgorithmException ae){
			ae.printStackTrace();
			System.out.println("NoSuchAlgorithmException");
		}
	}
}