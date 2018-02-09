
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EquationDriver {
	public static void main(String[] args) throws FileNotFoundException, BSTException, ParseException{
		Scanner scan = new Scanner( new File (args[0]));
        String line;
        boolean debug = false;
        if(args.length > 1)
        	debug = true;
        // initialize symTab
        BST symbolTable = new BST();
        // loop through lines in input file
        while ( scan.hasNextLine()){
        	line = scan.nextLine();
        	System.out.println("next line: " + line);
        	try{
            	TokenIter tokIt = new TokenIter(line);
            	Equation nextEq = new Equation(tokIt, debug);
        		Symbol res = nextEq.line(symbolTable);
    	        System.out.println("result: " + res);
        	} catch (ParseException PE) {
        		System.out.println("Parse Exception caught: " + PE.getMessage());
        	} catch (BSTException PE) {
        		System.out.println("BST Exception caught: " + PE.getMessage());
    	    } 
	
    		if(debug){
    			System.out.println("SYMBOL TABLE");
    			symbolTable.preorderTraverse();
    			System.out.println("END SYMBOL TABLE\n");
    		}
        }
	}
}