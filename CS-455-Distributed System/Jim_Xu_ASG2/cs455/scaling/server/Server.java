// Jim Xu

package cs455.scaling.server;

import java.lang.System;
import java.math.BigInteger;
import java.util.Arrays;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.locks.ReentrantLock;
import java.nio.channels.CancelledKeyException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.SelectionKey;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.io.IOException;
import java.util.*;


public class Server{

	private final int portnum;
	private final int thread_pool_size;
	private Selector selector;
	private final int buffSize = 8 * 1024;
	private ThreadPool threadpool;
	private ServerSocketChannel serverSocketChannel;
	private int serverThroughput;
	private int activeClient;
	private final ReentrantLock clientlock = new ReentrantLock();
	private final ReentrantLock serverlock = new ReentrantLock();

	public Server(int portnum, int thread_pool_size){
		this.portnum = portnum;
		this.thread_pool_size = thread_pool_size;
	}

	public void initiate() throws IOException, NoSuchAlgorithmException{
		threadpool = new ThreadPool(thread_pool_size);
		threadpool.initiate();
		threadpool.setName("ThreadPool");
		threadpool.start();
		this.selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(portnum)); 
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  

		startServer();
	} 

	private void startServer() throws IOException, NoSuchAlgorithmException {
		System.out.println("Server Running!"); 
		Thread serverCount = new Thread(new ServerCount(this));
		serverCount.setName("ServerCount");
		serverCount.start();
		while (true) {
			this.selector.select();
			Iterator <SelectionKey> keyIterator = 
						this.selector.selectedKeys().iterator(); 
			while (keyIterator.hasNext()) {
				SelectionKey key = keyIterator.next();
                keyIterator.remove();
                if (key.isAcceptable()) { 
					this.accept(key);
				} else if (key.isReadable()) {
					key.interestOps(key.interestOps()&(~SelectionKey.OP_READ));
			        Task newTask = new ReadTask(this, key);
			        threadpool.addTask(newTask);
			    } else if (key.isWritable()) {
			    	key.interestOps(key.interestOps()&(~SelectionKey.OP_WRITE));
			    	Task newTask = new WriteTask(this, key);
			        threadpool.addTask(newTask);
			    }
			}
		}
	}

	public int getServerThroughputCount(){
		int value = serverThroughput;
		value /= 5;
		serverThroughput = 0;
		return value;
	}

	public int getActiveClientCount(){
		return activeClient;
	}

	private void accept(SelectionKey key) throws IOException { 
		ServerSocketChannel servSocket = (ServerSocketChannel) key.channel(); 
		SocketChannel channel = servSocket.accept();
		channel.configureBlocking(false); 
		channel.register(selector, SelectionKey.OP_READ);
		try{
			clientlock.lock();
			activeClient ++;
		}finally{
			clientlock.unlock();
		}
	}

	public void read(SelectionKey key) throws IOException, NoSuchAlgorithmException { 
		SocketChannel channel = (SocketChannel) key.channel(); 
		ByteBuffer buffer = ByteBuffer.allocate(buffSize);
		int read = 0;
		try {
			while (buffer.hasRemaining() && read != -1) {
				read = channel.read(buffer); 
				byte[] data = buffer.array();
				String hash = SHA1FromBytes(data);
				key.attach((String)hash);
			}
		} catch (IOException e) {
			channel.close();
			try{
				clientlock.lock();
				activeClient --;
			}finally{
				clientlock.unlock();
			}
			return; 
		}
		if (read == -1) {
			try{
				clientlock.lock();
				activeClient --;
			}finally{
				clientlock.unlock();
			}
			channel.close();
			return;
		} 
		try {
	        key.interestOps(SelectionKey.OP_WRITE);
	    } catch (CancelledKeyException e){
	    	e.printStackTrace();
			channel.close();
			return;
	    }
	    key.selector().wakeup();
	}

	public void write(SelectionKey key) throws IOException { 
		SocketChannel channel = (SocketChannel) key.channel();
		String hash = (String)key.attachment();
		int hashlen = hash.length();
		String hashes = String.valueOf(hashlen) + hash;
		ByteBuffer buffer = ByteBuffer.wrap(hashes.getBytes()); 
		int number = channel.write(buffer); 
		if (number < hash.length()){
			Task newTask = new WriteTask(this, key);
	        threadpool.addTask(newTask);
			return;
		}
		try {
	        key.interestOps(SelectionKey.OP_READ);
	    } catch (CancelledKeyException e){
	    	e.printStackTrace();
	    	channel.close();
	    	return;
	    }
		try{
			serverlock.lock();
			serverThroughput ++;
		}finally{
			serverlock.unlock();
		}
		key.selector().wakeup();
	}

	public static String SHA1FromBytes(byte[] data) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("SHA1"); 
		byte[] hash = digest.digest(data);
		BigInteger hashInt = new BigInteger(1, hash);
		return hashInt.toString(16); 
	}

	public static void main(String[] args){
		if (args.length < 2){
			System.out.println("Error in parameters");
		}
		int portnum = Integer.parseInt(args[0]);
		int thread_pool_size = Integer.parseInt(args[1]);
		Server server = new Server(portnum, thread_pool_size);
		try{
			server.initiate();
		} catch (IOException ie){
			ie.printStackTrace();
			System.out.println("IOException");
		} catch (NoSuchAlgorithmException ae){
			ae.printStackTrace();
			System.out.println("NoSuchAlgorithmException");
		}
	}
}