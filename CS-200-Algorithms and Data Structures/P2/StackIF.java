
public interface StackIF {
	    // add String op to top of stack
		public void push(String op);
		
		// remove and return item from top of stack
		// throw Stack Exception when stack empty
		public String pop() throws StackException;
		
		// return, but do not remove, item from top of stack
		// throw Stack Exception when stack empty
		public String peek() throws StackException;	
		
		// return true if stack empty, false otherwise
		public boolean isEmpty();

}