import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TestDriver {
	public static void main(String[] args) throws StackException, QueueException, FileNotFoundException{
		// if a second argument exists debug is true, else false
		// create scanner that reads from file named in args[0]
		// and exercises In2Post with each line
		Scanner scan = new Scanner( new File (args[0]));
		String line;
		boolean debug = false;
		if(args.length > 1)
			debug = true;
		// loop through lines in input file
		while (scan.hasNextLine()){
			line = scan.nextLine();
			// print line with square braces to show white space
			System.out.println("next line: [" + line + "]");
			In2Post ex = new In2Post(line, debug);
			System.out.println(ex.convertIn2Post());
		}	

	}
}