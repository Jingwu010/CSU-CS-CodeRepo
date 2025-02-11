
public class ParseTreeExpr {

	private boolean debug;

	private String nextToken;	
	private TokenIter itTokens;

	public ParseTreeExpr(TokenIter iter, boolean debug){
		itTokens = iter;
		this.debug = debug;
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

	
	public Tree line() throws ParseException{
		TreeNode root;
		Tree tree = new Tree();
		if(nextToken!=null){
			root = expr("");
			tree = new Tree(root);
		}
		//System.out.println("next Token is" + nextToken);
		if(nextToken!=null)
			error("end of line expected");
		return tree;
	}

	// expr = term ( ("+" | "-") number )*
	private TreeNode expr(String indent) throws ParseException{
		if(debug)
			System.out.println(indent+"expr");

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
			System.out.println(indent+"term");

		TreeNode left = factor(indent+" ");
		while(nextToken != null && "*/".contains(nextToken)){
			String op = nextToken;
			nextTok(indent+" ");
			TreeNode right = factor(indent+" ");
			left = new TreeNode(op,left, right);
		}
		return left;
	}

	//factor = "-" factor | number | "(" expr ")"
	private TreeNode factor(String indent) throws ParseException{
		if(debug)
			System.out.println(indent+"factor");

		if (nextToken.equals("-")){
			nextTok(indent + " ");
			return new TreeNode("-", factor(indent + " "));
		} if (nextToken.equals("(")){
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
			System.out.println(indent + "number");
		if(Character.isDigit(nextToken.charAt(0))){
			String num = nextToken;
			System.out.println("I get num is: " + num);
			nextTok(indent);
			return new TreeNode(num);
		}
		else {
			error("number expected");
			return null; // for java type checker
		}
	}
}
