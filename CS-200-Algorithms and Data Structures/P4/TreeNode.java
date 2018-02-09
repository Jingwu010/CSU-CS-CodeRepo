
public class TreeNode {
	
	private String item;
	private TreeNode left;
	private TreeNode right;
	
	// leaf
	public TreeNode(String item){
		this.item = item;
		left = null;
		right = null;
	}

	// unary tree, left is the child tree
	public TreeNode(String item, TreeNode child){
		this.item = item;
		this.left = child;
		this.right = null;
	}
	
	// binary tree
	public TreeNode(String item, TreeNode left, TreeNode right){
		this.item = item;
		this.left = left;
		this.right = right;
	}
	
	String getItem(){
		return item;
	}
	
	TreeNode getLeft(){
		return left;
	}
	
	TreeNode getRight(){
		return right;
	}
	
	public String toString(){
		if(left == null && right == null)
				return "leaf: " + item;
			else
				return "internal: " + item;		
	}
	
	public static void main(String[] args){
		TreeNode tn1 = new TreeNode("1");
		TreeNode tn2 = new TreeNode("2");
		TreeNode root = new TreeNode("+",tn1,tn2);
		
		System.out.println("root: " + root);
		System.out.println("  left:  " + root.getLeft());
		System.out.println("  right: " + root.getRight());

		TreeNode uroot = new TreeNode("-",new TreeNode("4"));
		
		System.out.println("uroot: " + uroot);
		System.out.println("  left:  " + uroot.getLeft());
		System.out.println("  right: " + uroot.getRight());		
	}
	
}