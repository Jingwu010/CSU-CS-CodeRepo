
public class test {
	public static void main(String args[]){
		TokenIter it = new TokenIter("1 + 2");
		while(it.hasNext()){
			String s = it.next();
			//System.out.println(s);
			//if (!s.isEmpty())
				System.out.println("next token: [" + s + "]");
		}
	}
}
