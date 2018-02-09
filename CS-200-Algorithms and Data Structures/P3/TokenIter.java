
import java.util.Iterator;


public class TokenIter implements Iterator<String>{

	//input line to be tokenized
	private String line;

	// the next Token, null if no next Token
	private String nextToken;

	private int index;
	
	public TokenIter(String line){
		index = 0;
		this.line = line.replaceAll("\\s","");
	}


	@Override
	public void remove() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	
	public static void main(String[] args){
		String line;
		if(args.length>0)
			line = args[0];
		else
		    line = "   15 * (26+37)- 489/5*61-(723-8456789)  ";
			//line = "1 + 2 * 3";
		System.out.println("line: [" + line + "]");
		//line = 
		TokenIter tokIt = new TokenIter(line);
		while(tokIt.hasNext()){
			String s = tokIt.next();
			//System.out.println(s);
			//if (!s.isEmpty())
				System.out.println("next token: [" + s + "]");
		}
	}


	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return index < line.length();
	}


	@Override
	public String next() {
		nextToken = "";
		//if (hasNext() == false) return "no";
		char tmp = line.charAt(index++);
		//if (tmp == ' ' && hasNext() == true) return next();
		if ("+-*/()".indexOf(tmp) >= 0) return nextToken = Character.toString(tmp);
		while(Character.isDigit(tmp)){
			nextToken += Character.toString(tmp);
			if (hasNext() == false) return nextToken;
			tmp = line.charAt(index++);
			if(Character.isDigit(tmp) == false){
				index --;
				return nextToken;
				//break;
			}
		}
		return nextToken;
	// TODO Auto-generated method stub
		
}

}