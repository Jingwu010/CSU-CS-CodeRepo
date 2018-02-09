
public abstract class Key<KT extends Comparable<KT>> {
	private KT key;
	public Key(KT key){
		this.key = key;
	}
	public KT getKey(){
		return key;
	}	
}