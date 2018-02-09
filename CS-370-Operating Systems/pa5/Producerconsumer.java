
//Jim Xu

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
public class Producerconsumer{
	public static final int numProducers = 1; //Choose 1 or 2 
	public static final int numConsumers = 2; //Choose 1 or 2
	private int proSize;
	private int procount;
	private int BUFFER_SIZE;
	private int waitTime;
	private Buffer buffer;
	private boolean debug; 		//debug
	private boolean outprint;	//control outprint
	public Producerconsumer(int BUFFER_SIZE, int waitTime, int proSize, boolean debug){
		this.BUFFER_SIZE = BUFFER_SIZE;
		this.waitTime = waitTime;
		this.proSize = proSize;
		this.procount = proSize;
		this.debug = debug;
		if(numProducers > 2) this.outprint = false;
		else this.outprint = true;
	}
	public void start() { 
		buffer = new Buffer(BUFFER_SIZE);
		Producer[] producer = new Producer[numProducers];
		Comsumer[] consumer = new Comsumer[numConsumers];
        for(int i = 0; i < numProducers; i++){
			producer[i] = new Producer();
			producer[i].setName("Producer " + i);
			System.out.println(producer[i].getName() + " starting...");
			producer[i].start();
		}
		for(int i = 0; i < numConsumers; i++){
			consumer[i] = new Comsumer();
			consumer[i].setName("Comsumer " + i);
			System.out.println(consumer[i].getName() + " starting...");
			consumer[i].start();
		} 
    }
    class Producer extends Thread {  
        public void run() {  
            while( (procount--) > 0){ 
                synchronized (buffer) {  
                    try {  
						int nextProduced = new Random().nextInt(99);
				        if (  buffer.getIn() == buffer.getOut() && buffer.getBuffercount() == BUFFER_SIZE) {
							// The buffer is full 
				        	System.out.println(Thread.currentThread().getName() +
													": Unable to insert, buffer full, at Time: " + 
														getTime());
				        	buffer.wait();
				        }
				        int loc = buffer.makeNewItem(nextProduced);
						System.out.println(Thread.currentThread().getName() +
												": Placed " + nextProduced +
													" at Location " + loc +
														" at Time: " + getTime());
				        buffer.notifyAll();
				        if (debug)
				        	System.out.println("[" + buffer.getIn() + ", " + buffer.getOut()+"]");
				        
                    } catch (InterruptedException ie) {}
                    
                }
                
                try {
		        	Thread.sleep(0, new Random().nextInt(waitTime));
		        	//inserting random delays
			    } catch(InterruptedException e) {  
			        e.printStackTrace();  
			    } 
			     
            }
            if (outprint != true) outprint = true; 
            else
            	System.out.println("Producer: Finished producing " + proSize + " items."); 
        }  
    }

   	class Comsumer extends Thread {
   		boolean isLive = true;
   		long checktime;
        public void run() {  
            while (isLive) {
                synchronized (buffer) {
                    try {

                    	if ( buffer.getIn() == buffer.getOut() && buffer.getBuffercount() == 0) {
				        	// The buffer is empty
							System.out.println(Thread.currentThread().getName() +
												": Unable to consume, buffer empty, at Time: " + 
													getTime());

							checktime = System.nanoTime();
							buffer.wait(0, 10 * waitTime);
							checktime = System.nanoTime() - checktime;
				        }

						if (checktime >= 10 * waitTime){
							System.out.println( Thread.currentThread().getName() +
								": terminated after 10 times its maximum waiting time at Time:" +
									getTime());
							//buffer.stop();
							isLive = false;
							break;
						}

				        int nextConsumed = buffer.consumeItem();
						System.out.println(Thread.currentThread().getName() +
												": Removed " + nextConsumed + " at Location " + 
														((buffer.getOut() ==  0) ? (buffer.getBuffersize() - 1)
															: (buffer.getOut() - 1)) + " at Time: " + getTime());
				    	buffer.notifyAll();
				    	if (debug)
				        	System.out.println("[" + buffer.getIn() + ", " + buffer.getOut()+"]");
				    	//notify other producer 
                    } catch (InterruptedException ie) {}
                    
                }
                
                try {  
                	//System.out.println(Thread.currentThread().getName() + " will slepp for :" + new Random().nextInt(waitTime));
		        	Thread.sleep(0, new Random().nextInt(waitTime));
		        	//inserting random delays
			    } catch(InterruptedException e) {  
			        e.printStackTrace();  
			    } 
			   
            }
  
        }
    }

    private String getTime(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSS").format(cal.getTime());
	}
	public static void main(String args[]){
		int BUFFER_SIZE, proSize, waitTime;
		boolean debug = false;
		BUFFER_SIZE = Integer.parseInt(args[0]);
		waitTime = Integer.parseInt(args[1]);
		proSize = Integer.parseInt(args[2]);
		//if (args[3] != null) debug = true;
		Producerconsumer pc = new Producerconsumer(BUFFER_SIZE, waitTime, proSize, debug);
		pc.start();
	}
}