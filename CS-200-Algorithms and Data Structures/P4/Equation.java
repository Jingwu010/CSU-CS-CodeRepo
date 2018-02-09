
public class Equation {

	private boolean debug;

	private String nextToken;	
	private TokenIter itTokens;
	
	Tree exprTree;
    
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

	private void error(String errMess) throws ParseException{
		throw new ParseException(errMess);

	}

	// line parses a line: lhs "=" expr
	// lhs is an identifier
	// and stores an Symbol (lhs, value) in the symbol table
	public Symbol line(BST symbolTable) throws BSTException, ParseException{
		TreeNode root;
		String x;
		String lhs;
		Integer value;
		exprTree = null;
		if(nextToken==null)
			return null;
		else {
			if(debug)
				System.out.println("line");
			
			// scan left hand side String lhs
			// if it is not an identifier
			// call	error("Identifier expected");
			//nextToken = itTokens.hasIdentifier();
			if (nextToken == null)
				error("Identifier expected");
			
			x = "";
			x = nextToken;
			if (debug) System.out.println("identifier is: " + nextToken);
			// if no next token
			// call	error("unexpected end of line");
			if (!itTokens.hasNext()) 
				error("unexpected end of line");

			// scan "="
			// if not "=" call error("= expected");
			nextToken = itTokens.next();
			if (debug) System.out.println(" = is: " + nextToken);
			if (!nextToken.equals("="))
				error("= expected");

			// parse expression and create expression tree
            // evaluate Expression tree
            // create Symbol lhsVal (lhs, value)
            // insert lhsVal in symbolTable
			
			nextToken = itTokens.next();
			if(nextToken != null){
				//System.out.println("here is: " + nextToken);
				root = expr("");
				exprTree = new Tree(root);
			}

			//System.out.println("tree is :");exprTree.preorderTraverse();
			value = exprTree.postorderEval("", symbolTable);
			Symbol lhsVal = new Symbol(lhs, value);
			symbolTable.insertItem(lhsVal);
			
			if(debug)
				System.out.println("retrieving " + lhs + ": " + symbolTable.retrieveItem(lhs));
			
			if(nextToken!=null){
				error("end of line expected");
				return null; 
			} 
			else return lhsVal;

		}
	}

	// expr = term ( ("+" | "-") number )*
	private TreeNode expr(String indent) throws ParseException{
		if(debug)
			System.out.println(nextToken + " is in " + indent+"expr");

		TreeNode left = term(indent+" ");
		while(nextToken != null && "+-".contains(nextToken)){
			String op = nextToken;
			nextTok(indent+" ");
			TreeNode right = term(indent+" ");
			left = new TreeNode(op,left, right);
		}
		return left;
	}

	// term = factor ( ("*" | "/") factor )*
	private TreeNode term(String indent) throws ParseException{
		if(debug)
			System.out.println(nextToken + " is in " + indent+"term");

		TreeNode left = factor(indent+" ");
		while(nextToken != null && "*/".contains(nextToken)){
			String op = nextToken;
			nextTok(indent+" ");
			TreeNode right = factor(indent+" ");
			left = new TreeNode(op,left, right);
		}
		return left;
	}

	//factor   ::=   "-" factor | identifier | number | "(" expr ")"
	private TreeNode factor(String indent) throws ParseException{
		if(debug)
			System.out.println(nextToken + " is in " + indent + "factor");

		if (nextToken.equals("-")){
			nextTok(indent + " ");
			return new TreeNode("-", factor(indent + " "));
		} 
		if (isIdentifier(nextToken)){
			String iden = nextToken;
			nextTok(indent + " ");
			return new TreeNode(iden);
		}
		if (nextToken.equals("(")){
			nextTok(indent + " ");
			TreeNode n = expr(indent + " ");
			//System.out.println("Node is " + n.getItem());
			if (!nextToken.equals(")")){
				error("')' expected");
			}
			else {
				nextTok(indent + " ");
				return n;
			}
		} try{
			return number(indent + " ");
		} catch (ParseException e){
			error("factor expected");
		}
		error("can't find factor");
		return null;
	}

	private TreeNode number(String indent) throws ParseException{
		if(debug)
			System.out.println(nextToken + " is in " + indent + "number");
		if(Character.isDigit(nextToken.charAt(0))){
			String num = nextToken;
			//System.out.println("I get num is: " + num);
			nextTok(indent);
			return new TreeNode(num);
		}
		else {
			error("number expected");
			return null; // for java type checker
		}
	}

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
}