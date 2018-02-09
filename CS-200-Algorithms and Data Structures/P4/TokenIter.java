
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
	public String toString(){
		return line;
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
		char tmp = line.charAt(index++);
		if ("+-*/()=".indexOf(tmp) >= 0) {
			return nextToken = Character.toString(tmp);
		}
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
		index --;
		//System.out.println("Check hasIdentifier!");
		if (hasIdentifier() != null) return nextToken;
		return nextToken;
	// TODO Auto-generated method stub
	}


	private boolean isLetter(char c){
		if (c - 'a' < 0 && c - 'a' >= 26)
			if (c - 'A' < 0 && c - 'A' >= 26)
				return false;
		return true;
	}
	private boolean isDigit(char c){
		return Character.isDigit(c);
	}
	public String hasIdentifier() {
		nextToken = "";
		char tmp = line.charAt(index++);
		while("+-*/()=".indexOf(tmp) < 0){
			//System.out.println(" is : " + (!isLetter(tmp) && index == 1) );
			if (!isLetter(tmp) && index == 0) return null;
			//System.out.println(" is : " + (!isLetter(tmp) || !isDigit(tmp)));
			if (!isLetter(tmp) && !isDigit(tmp)) return null;
			nextToken += tmp;
			if (hasNext() == false) return nextToken;
			tmp = line.charAt(index++);
		}
		index --;
		return nextToken;
	// TODO Auto-generated method stub
	}
	public String nextAll(){
		nextToken = "";
		int k = index;
		while(k < line.length()){
			nextToken += line.charAt(k++);
		}

		return nextToken;
	}

}