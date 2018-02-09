public class Symbol extends Key<String> {
	private Integer val;
	public Symbol(String id, Integer val){
		super(id);
		this.val = val;
	}
	public Integer getVal(){
		return val;
	}
	public String toString(){
		return "[" + getKey() + ": " + val+ "]"; 
	}	
}