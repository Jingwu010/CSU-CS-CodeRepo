import java.util.NoSuchElementException;

public interface ICharStack {
    public boolean isEmpty();
    public void push(char c);
    public char peek() throws NoSuchElementException;
    public char pop() throws NoSuchElementException;
}