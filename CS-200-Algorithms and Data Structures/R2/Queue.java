
public class Queue implements QueueIF{
	private static final Object QueueException = null;
	private LinkedList queueLL;
	boolean debug;
	
	public Queue(boolean debug){
		queueLL = new LinkedList();
		this.debug = debug;
	}
	
	public static void main(String[] args) throws QueueException{
		Queue intQueue = new Queue(true);
		System.out.println("create Queue of Integers 12 34 56");
		intQueue.enqueue(new Integer(12));	
		intQueue.enqueue(new Integer(34));
		intQueue.enqueue(new Integer(56));
				
		if(intQueue.debug)
			System.out.println("intQueue: " + intQueue.queueLL.toString());	
		Object o12 = intQueue.dequeue();
		if(intQueue.debug)
			System.out.println("o12: " + o12);		
		Object o34 = intQueue.dequeue();
		if(intQueue.debug){
			System.out.println("o34: " + o34);		
			System.out.println("intQueue: " + intQueue.queueLL.toString() + 
				           ", size: " + intQueue.size());
		}
		intQueue.dequeue();
		// test Exception handling
		intQueue.dequeue();
	}

	@Override
	public void enqueue(Object token) {
		// TODO Auto-generated method stub
		queueLL.add(token);
		
	}

	@Override
	public Object dequeue() throws QueueException {
		// TODO Auto-generated method stub
		
		System.out.printf("\n----\n" + queueLL.size());
		if (queueLL.size() <= 0) throw new QueueException();
		System.out.printf("\n??\n");
		Object o = queueLL.remove(0);
		return o;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return queueLL.size();
	}


}