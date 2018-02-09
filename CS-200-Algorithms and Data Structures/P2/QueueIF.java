
public interface QueueIF {

	//enqueue String token at the end of expQueue
	// In debug mode print "enqueue: " + enqueued value
	public void enqueue(String token);
	
	/* dequeue token from the front of the expQueue
	 * If expQueue empty, return null
	 * In debug mode print "deque: " + dequed value
	 */ 
	public String dequeue() throws QueueException;
	
	// returns size (#elements) of the queue
	public int size();	
}