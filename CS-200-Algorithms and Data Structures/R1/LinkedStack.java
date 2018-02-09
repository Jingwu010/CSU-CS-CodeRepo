import java.util.NoSuchElementException;

public class LinkedStack implements ICharStack {
    private class Node {
        public char c;
        public Node next;

        public Node(char c, Node next) {
            this.next = next; this.c = c;
        }
        public Node(char c) {
            this(c, null);
        }
    }
    // question: can code outside the LinkedStack class use the Node class?

    // private member to hold first node reference
    private Node top;
    // constructor of zero args
    public LinkedStack(){
    	top = null;
    }

    @Override
    public boolean isEmpty() {
    	return top == null;
        // code here
    }

    @Override
    public void push(char c) {
    	top = new Node(c, top);
        // code here
    }

    @Override
    public char peek() throws NoSuchElementException {
        if(isEmpty())
            throw new NoSuchElementException();
        return top.c;
        // else return the top char
    }

    @Override
    public char pop() throws NoSuchElementException {
        char ret = peek();
        top = top.next;
        return ret;
    }
    // question: why doesn't pop need to do any error checking?
    // (what would happen if you tried to pop an empty stack?)
}