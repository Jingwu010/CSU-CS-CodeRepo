
public interface StackIF {
	    // add String op to top of stack
		public void push(Object op);
		
		// remove and return item from top of stack
		// throw Stack Exception when stack empty
		public Object pop() throws StackException;
				
		// return true if stack empty, false otherwise
		public boolean isEmpty();

}
