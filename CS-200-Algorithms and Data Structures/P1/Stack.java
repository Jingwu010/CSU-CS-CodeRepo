import java.util.ArrayList;

public class Stack implements StackIF{

	private ArrayList<Object> AL;
	
	public Stack(){
		// initialize theStack
		
		AL = new ArrayList<Object>();
		
	}
	
	@Override
	/*
	 * push op on Run Time Stack at end of theStack
	 */
	public void push(Object op) {
		// TODO Auto-generated method stub
		
		AL.add(op);
		
	}

	@Override
	/*
	 * pop and return top Object from Run Time Stack
	 * If Stack empty, throw StackException("Popping from empty stack!");
	 */
	public Object pop() throws StackException {
		// TODO Auto-generated method stub
		
		Object popped = null;
		int len = AL.size();
		if (len == 0){
			System.out.println("Popping from empty stack!");
			return popped;
		}
		popped = AL.get(len - 1);
		AL.remove(len - 1);
		return popped;
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		
		return AL.size() == 0;
		// AL == 0 return true;
		// else return false;
		
	}
	
	// return the String representation of theStack
	public String toString(){
		if(AL.isEmpty()) return "[]";
		return AL.toString();
		
	}
	
	public static void main(String[] args) throws StackException{
       Stack st = new Stack();
       st.push(new Frame(1,3,1,3));
       st.push(new Frame(0,2,1,2));
       System.out.println("st: " + st);
       st.pop();
       System.out.println("st: " + st);
       st.pop();
       System.out.println("st: " + st);
	}
       
}