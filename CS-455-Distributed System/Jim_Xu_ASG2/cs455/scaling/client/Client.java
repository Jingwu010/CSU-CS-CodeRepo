// Jim Xu

package cs455.scaling.client;

import java.math.BigInteger;
import java.util.Random;
import java.util.Arrays;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.locks.ReentrantLock;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.*;

public class Client{
	private static String payload;
	private final String server_host;
	private final int server_port;
	private final int message_rate;
	private final boolean listSizeMessage;
	private final int buffSize = 8 * 1024;
	private Selector selector;
	private String hash = "";
	private LinkedList<String> hashList;  
	private int datasent;
	private int datareceive;
	private ClientWriter clientWriter;
	
	public Client(String server_host, int server_port, int message_rate, boolean listSizeMessage){
		this.server_host = server_host;
		this.server_port = server_port;
		this.message_rate = message_rate;
		this.listSizeMessage = listSizeMessage;
		this.hashList = new LinkedList<String>();
	}

	public void initiate() throws IOException {
		SocketChannel channel = SocketChannel.open(); 
		this.selector = Selector.open();
		datasent = 0;
		datareceive = 0;
		channel.configureBlocking(false); 
		channel.register(selector, SelectionKey.OP_CONNECT); 
		channel.connect(new InetSocketAddress(this.server_host, this.server_port));
	}

	private void startClient() throws IOException, NoSuchAlgorithmException{
		System.out.println("Client Running!");
		while (true) {
			this.selector.select();
			Iterator <SelectionKey> keyIterator = 
						this.selector.selectedKeys().iterator(); 
			while (keyIterator.hasNext()) {
				SelectionKey key = keyIterator.next();  
                keyIterator.remove();
				if(key.isConnectable()){ 
					this.connect(key);
				} else if (key.isReadable()) {
			        this.read(key);
			    }
			}
		}
	}

	public int getLinksize(){
		return hashList.size();
	}

	public int getDataSentCount(){
		return datasent;
	}

	public int getDataReceiveCount(){
		return datareceive;
	}

	private void connect(SelectionKey key) throws IOException { 
		SocketChannel channel = (SocketChannel) key.channel();
		channel.finishConnect();
		Thread clientCount = new Thread(new ClientCount(this, listSizeMessage));
        clientCount.setName("clientCount");
        clientCount.start();
		Thread clientWriter = new ClientWriter(this, message_rate, key);
		clientWriter.setName("clientWriter");
		clientWriter.start();
	}

	private void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel(); 
		ByteBuffer buffer = ByteBuffer.allocate(buffSize);
		int read = 0;
		try {
			read = channel.read(buffer); 
			byte[] data = buffer.array();
			String string = new String(data);
			int len = Integer.parseInt(string.substring(0,2));
			String hashes = string.substring(2,2+len);
			this.hashList.remove(hashes);
		} catch (IOException e) {
			e.printStackTrace();
			channel.close();
			return; 
		}
		if (read == -1) {
			channel.close();
			return;
		} 
		key.interestOps(SelectionKey.OP_WRITE);
		datareceive ++;
	}

	public void write(SelectionKey key) throws IOException, NoSuchAlgorithmException {  
		SocketChannel channel = (SocketChannel) key.channel();
		byte[] data = new byte[buffSize];
		data = createDataSize(buffSize);
		hash = SHA1FromBytes(data);
		this.hashList.add(hash);
		ByteBuffer buffer = ByteBuffer.wrap(data); 
		channel.write(buffer); 
		key.interestOps(SelectionKey.OP_READ);
		datasent ++;
		key.selector().wakeup();
	}

	private static byte[] createDataSize(int msgSize) {
		byte[] bytes = new byte[msgSize];
		new Random().nextBytes(bytes);
		return bytes;
	}

	private static String SHA1FromBytes(byte[] data) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("SHA1"); 
		byte[] hash = digest.digest(data);
		BigInteger hashInt = new BigInteger(1, hash);
		return hashInt.toString(16); 
	}

	public static void main(String args[]){
		if (args.length < 3){
			System.out.println("Error in parameters");
		}
		String server_host = args[0];
		int server_port = Integer.parseInt(args[1]);
		int message_rate = Integer.parseInt(args[2]);
		boolean listSizeMessage = false;
		if(args.length == 4){
			String string = args[3];
			if(string.equals("-i")) listSizeMessage = true;
		}
		Client client = new Client(server_host, server_port, message_rate, listSizeMessage);
		try{
			client.initiate();
			client.startClient();
		} catch (IOException ie){
			ie.printStackTrace();
			System.out.println("IOException");
		} catch (NoSuchAlgorithmException ae){
			ae.printStackTrace();
			System.out.println("NoSuchAlgorithmException");
		}
	}
}