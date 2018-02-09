
public interface QueueIF {

	//enqueue String token at the end of the Linked List
	public void enqueue(Object token);
	
        // dequeue token from the front of the Linked List	 
	public Object dequeue() throws QueueException;
	
	// returns size (#elements) of the queue
	public int size();	
}