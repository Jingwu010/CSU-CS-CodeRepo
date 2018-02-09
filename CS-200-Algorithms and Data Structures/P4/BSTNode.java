

public class BSTNode<Comparable> {

	private Comparable item;
	private BSTNode<Comparable> left;
	private BSTNode<Comparable> right;
	
	// one node, empty children
	public BSTNode(Comparable item){
		this.item = item;
		left = null;
		right = null;
	}
	
	public Comparable getItem(){
		return item;
	}

	public BSTNode<Comparable> getLeft(){
		return left;
	}
	public void setLeft(BSTNode<Comparable> newLeft){
		left = newLeft;
	}
	
	public BSTNode<Comparable> getRight(){
		return right;
	}
	public void setRight(BSTNode<Comparable> newRight){
		right = newRight;
	}
	
	public String toString(){
		if(left==null && right == null)
			return "BST leaf: " + item;
		else
			return "BST internal: " + item;
	}	
}