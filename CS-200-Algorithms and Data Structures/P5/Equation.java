
//Jim

public class Equation {

	private boolean debug;

	private String nextToken;	
	private TokenIter itTokens;
	public Equation(TokenIter iter, boolean debug){
		itTokens = iter;
		this.debug = debug;
                // put the first token from iter in nextToken
                // initial indent ""
		nextTok("");
	}

	private void nextTok(String indent){
		if(itTokens.hasNext())
			nextToken = itTokens.next();
		else 
			nextToken = null;
		if(debug)
			System.out.println(indent+"next token: " + nextToken);
	}

	// line parses a line: lhs "=" expr
	// lhs is an identifier
	// and stores an IdVal (lhs, value) in the symbol table
	//The line method in equation must now, 
	//for each identifier x in the right hand side expression, 
	//add an edge x --> lhs in the dependence graph.
	private boolean isIdentifier(String indent){
		for(int i = 0; i < nextToken.length(); i++){
			if (i == 0 && !isLetter(nextToken.charAt(i))) return false;
			if (!isLetter(nextToken.charAt(i)) && !isDigit(nextToken.charAt(i))) return false;
		}
		return true;
	}

	private boolean isLetter(char c){
		if (c - 'a' < 0 || c - 'a' >= 26)
			if (c - 'A' < 0 || c - 'A' >= 26){
				
				return false;
			}
		return true;
	}
	private boolean isDigit(char c){
		return Character.isDigit(c);
	}

	public void line(DepGraph dG) throws GraphException{
		String x;
		String lhs;
		if(nextToken==null)
			return;
		else {
			if(debug)
				System.out.println("line");

			x = "";
			x = nextToken;
			if (debug) System.out.println("identifier is: " + nextToken);
			dG.addAdjList(x);

			nextToken = itTokens.next();
			if (debug) System.out.println(" = is: " + nextToken);

			while(itTokens.hasNext()){
				nextToken = itTokens.next();
				if(isIdentifier(nextToken)){
					lhs = nextToken;
					dG.addDest(lhs, x);
				}
			}
		}
	}
}