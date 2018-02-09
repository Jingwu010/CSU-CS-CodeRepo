import java.util.ArrayList;


public class Queue implements QueueIF {

	// maintains expression
	private ArrayList<String> expQueue;
	
	// debug controls debug reporting
	private boolean debug;
	
	// constructor
	public Queue(boolean debug){
		this.debug = debug;
		expQueue = new ArrayList<String>();
	}
	
    public static void main(String[] args) throws QueueException{
    	Queue q = new Queue(false);
    	q.dequeue();
    }

	@Override
	public void enqueue(String token) {
		// TODO Auto-generated method stub
		expQueue.add(token);
	}

	@Override
	public String dequeue() throws QueueException {
		if (expQueue.size() <= 0) throw new QueueException("Deleting from an empty queue!");
		// TODO Auto-generated method stub
		return expQueue.remove(0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return expQueue.size();
	}
	
	@Override
	public String toString(){
		if(expQueue.isEmpty()) return "";
		String s = new String();
		try {
			s += dequeue();
		} catch (QueueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!expQueue.isEmpty())
			try {
				s += " " + dequeue();
			} catch (QueueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return s;
		
	}
}