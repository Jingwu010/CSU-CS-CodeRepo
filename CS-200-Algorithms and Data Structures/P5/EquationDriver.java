import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EquationDriver {
	public static void main(String[] args) throws FileNotFoundException, GraphException{
		Scanner scan = new Scanner( new File (args[0]));
        String line;
        boolean debug = false;
        if(args.length > 1)
        	debug = true;
        // initialize dependence graph
        DepGraph depGraph = new DepGraph(debug);
        // loop through lines in input file
        while ( scan.hasNextLine()){
        	line = scan.nextLine();
        	System.out.println("next line: " + line);
        	try{
        	TokenIter tokIt = new TokenIter(line);
        	Equation nextEq = new Equation(tokIt, debug);
    		nextEq.line(depGraph);
        	} catch (GraphException GE) {
        		System.out.println("Exception caught: " + GE.getMessage());
        	} 
	
    		if(debug){
    			System.out.println("DEPENDENCE GRAPH");
    			System.out.print(depGraph);
    			System.out.println("END DEPENDENCE GRAPH\n");
    		}
        }
		
        System.out.println();
        if(!debug)
		  System.out.println(depGraph); 
        
        System.out.println("Evaluation order of equations:");
        depGraph.topoPrint();
	}
}