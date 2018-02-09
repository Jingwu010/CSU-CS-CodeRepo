import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException, ParseException{
        Scanner scan = new Scanner( new File (args[0]));
        String line;
        boolean debug = false;
        if(args.length > 1)
            debug = true;
        // loop through lines in input file
        while ( scan.hasNextLine()){
            line = scan.nextLine();
            System.out.println("next line: " + line);
            TokenIter tokIt = new TokenIter(line);
            ParseTreeExpr buildTree = new ParseTreeExpr(tokIt, debug);
            Tree pTree = buildTree.line();
            
            System.out.println("Expression Tree:"); 
            pTree.preorderTraverse();
            
            System.out.println("result: " + pTree.postorderEval());
        }
    }
}