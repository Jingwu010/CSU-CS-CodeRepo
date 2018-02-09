// Palindrome.java
// Class: cs200

public class Palindrome {

    private static void printPalindromeRecursive(String s, int i) {
        // first print the character, then recurse on the next character index
        System.out.print(s.charAt(i));
        if(i < s.length() - 1) {
            // question: will i++ or ++i work on the method call below?
            // or: do we want the side-effects that the ++ operators perform?
            printPalindromeRecursive(s, i + 1);
        }
        System.out.print(s.charAt(i));
    }

    public static void printPalindromeStack(String s) {
        // print the characters in s forwards
    	System.out.print(s);
        // load them into a new Stack()
    	Stack st = new Stack();
    	for(char c : s.toCharArray())
    		st.push(c);
        // then pop and print them in a loop
    	while(!st.isEmpty()){
    		char c = st.pop();
    		System.out.print(c);
    	}
        // now you've printed forwards and backwards

        // hint: you can loop over the chars of a String with a foreach loop on an array:
        // for(char c : s.toCharArray())
    }

    public static void main(String[] args) {
        printPalindromeRecursive("abc", 0);
        System.out.println();

        printPalindromeStack("abc");
        System.out.println();
    }
}