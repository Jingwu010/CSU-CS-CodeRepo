import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class Stack implements ICharStack {
    private ArrayList<Character> list;

    public Stack() {
        // bonus: find the name for the "<>" used below
        // this is a tough one to google
    	list = new ArrayList<>();
    }

    // question: why must we implement isEmpty here?
    // (we didn't have to before when we were extending ArrayList)
    @Override
    public boolean isEmpty() {
    	return list.isEmpty();
        // basically reuse your code from ExtendingStack
    }

    @Override
    public void push(char c) {
    	list.add(c);
    	return ;
        // basically reuse your code from ExtendingStack
    }

    @Override
    public char peek() throws NoSuchElementException {
    	if (list.isEmpty()) 
    		throw new NoSuchElementException();
    	char c = list.get(list.size()-1);
    	return c;
        // basically reuse your code from ExtendingStack
    }

    @Override
    public char pop() throws NoSuchElementException {
    	if (list.isEmpty()) 
    		throw new NoSuchElementException();
    	char c = list.remove(list.size()-1);
    	return c;
        // basically reuse your code from ExtendingStack
    }


    @Override
    public String toString() {
    	return list.toString();
        // create a reasonable string representation
        // don't work too hard, call somebody else's toString
    }

    
    
    public boolean equals(Object o) {
    	Stack st = (Stack) o; 
    	while (!st.isEmpty() && !list.isEmpty()){
    		if (st.pop() != pop())
    			return false;
    	}
    	if (!st.isEmpty()) return false;
    	if (!list.isEmpty()) return false;
    	return true;
        //   check if its an instance of a Stack
        //   if it is, cast it to a Stack and store
        //   it in a local variable
        //   then compare the lists of this and the other
        //   stack for equality
        //   (don't work too hard here either)

        // if it isn't an instance of a Stack,
        // return false
    }

    public void swapTopTwo() {
    	char c1 = pop();
    	char c2 = pop();
    	push(c1);
    	push(c2);
        // if it was [a, b, c, d] before
        // it should be [a, b, d, c] after
    }

    public void copyTopNTimes(int n) {
    	char c = peek();
    	for (int i = 0; i < n; i++) push(c);
        // if it was [a, b, c] before
        // myStack.copyTopNTimes(3) should make it
        // [a, b, c, c, c, c]
    }

    public Stack reversed() {
    	Stack st = new Stack();
    	while (!list.isEmpty()) st.push(pop());
    	return st;
        // create and return a Stack that contains
        // the same characters as this stack, but in
        // reverse order
        // hint: all collections (including arraylist)
        // have a constructor that takes another collection
        // hint: if you have a list called myList, call
        // Collections.reverse(myList);
        // to reverse the elements in it
    }
	
    public static void main (String[] args) {
        ICharStack s = new LinkedStack();
        System.out.println("Pushing characters: a, b, and c onto the stack.");
        s.push('a');
        s.push('b');
        s.push('c');

        System.out.println("Peeking at the to character of the stack (Should be c).");
        System.out.println(s.peek());
        System.out.println("Popping all characters off the stack.");
        while(!s.isEmpty()) {
            System.out.println(s.pop());
        }

    }
}


// question: this implementation and the ExtendingStack both use ArrayList
// to do the actual work. One extends, one contains a private member.
// How are these two approaches different? Can you do some things with
// an ExtendingStack that you couldn't with this Stack class?
// Google java Stack and click on the javadoc for the built in Stack class.
// Which approach did they take? (Vector is just a thread safe version of ArrayList)


// bonus: create a constructor that takes two chars representing a range,
// and loads that range in to the stack. Ex.
// new Stack('a', 'd')
// would result in a stack containing [a, b, c, d]