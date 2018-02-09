import java.util.ArrayList;

public class Stack implements StackIF {

	// Use opStack to push and pop operators
	private ArrayList<String> opStack;
	
	// debug controls debug reporting 
    private boolean debug;
    
    public Stack(boolean debug){
    	this.debug = debug;
    	opStack = new ArrayList<String>();
    }


    public static void main(String[] args) throws StackException{
    	Stack s = new Stack(false);
    	s.pop();
    }


	@Override
	public void push(String op) {
		opStack.add(op);
		// TODO Auto-generated method stub
		
	}


	@Override
	public String pop() throws StackException {
		if (opStack.isEmpty()) throw new StackException();
		// TODO Auto-generated method stub
		return opStack.remove(opStack.size() - 1);
	}


	@Override
	public String peek() throws StackException {
		if (opStack.isEmpty()) throw new StackException("Deleting from an empty stack!");
		// TODO Auto-generated method stub
		return opStack.get(opStack.size() - 1);
	}


	@Override
	public boolean isEmpty() {
		if(opStack.size() == 0) return true;
		// TODO Auto-generated method stub
		return false;
	}
	
	public String toString(){
		if(opStack.isEmpty()) return "[]";
		return opStack.toString();
	}
}