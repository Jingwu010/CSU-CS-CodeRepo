import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ExtendingStack extends ArrayList<Character> implements ICharStack {
    // question: why don't we implement isEmpty? Isn't it required by the interface?

    @Override
    public void push(char c) {
    	add(c);
    	// equals this.add(c);
        // add to the end (find the arraylist method that adds to the end)
    }

    @Override
    public char peek() throws NoSuchElementException {
        if(isEmpty())
            throw new NoSuchElementException();
        return get(size()-1);
        // return the last char
    }

    @Override
    public char pop() throws NoSuchElementException {
        char ret = peek();
        // equals ret = this.peek();
        // remove the last char (find the arraylist method to remove at a certain position)
        remove(size() -1);
        return ret;
    }
}

// question: how does this array backed implementation differ
// from our linked implementation? Do they use different amounts of
// cpu time or memory? What must happen when the array gets full but we
// add another character? Is the memory used for the Nodes in the
// linked version stored all together or scattered? Would this effect how
// fast it is to retrieve data from these memory locations?