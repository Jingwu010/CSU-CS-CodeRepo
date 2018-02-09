
public class BST{
	// this Binary Search Tree is used for the implementation of the 
	// Symbol Table containing Symbols: (id,value) pairs
	// A Symbol is a Comparable object containing a String Identifier  
	// and a Boolean value 
	private BSTNode<Symbol> root;

	//empty tree
	public BST(){
		this.root = null;
	}

	public boolean isEmpty(){
		return root==null;
	}

	public void insertItem(Symbol item) throws BSTException{
		root = insertItem(root, item);
	}

	private BSTNode<Symbol> insertItem(BSTNode<Symbol> node, Symbol item) throws BSTException{
		BSTNode<Symbol> newSubTree;
		if(node==null){
			newSubTree = new BSTNode<Symbol>(item);
			return newSubTree;
		}
		
		Symbol tok = node.getItem();
		if(item.getKey().compareTo(tok.getKey())<0){
			newSubTree = insertItem(node.getLeft(), item);
			node.setLeft(newSubTree);
			return node;
		}
		if(item.getKey().compareTo(tok.getKey())>0){
			newSubTree = insertItem(node.getRight(), item);
			node.setRight(newSubTree);
			return node;
		}
		// ERROR: inserting existing item
		else 
			throw new BSTException("Inserting item with existing key!");
	}

	public Symbol retrieveItem(String key){
		return retrieveItem(root,key);
	}
	
	private Symbol retrieveItem(BSTNode<Symbol> node, String key){
		Symbol treeItem;
		
		if(node==null)
			treeItem = null;
		else{
			Symbol nodeItem = node.getItem();
			if(key.compareTo(nodeItem.getKey()) == 0)
				//found
				treeItem = nodeItem;
			else if(key.compareTo(nodeItem.getKey()) < 0)
				//search left
				treeItem = retrieveItem(node.getLeft(), key);
			else
				// search right
				treeItem = retrieveItem(node.getRight(), key);
		}
		return treeItem;

	}
	

	public void preorderTraverse(){
		if (!isEmpty())
			preorderTraverse(root,"");
		else
			System.out.println("root is null");
	}

	public void preorderTraverse(BSTNode<Symbol> node, String indent){
		System.out.println(indent+node.getItem());		
		if(node.getLeft()!=null) {
			System.out.println(indent+"left");
			preorderTraverse(node.getLeft(),indent+" ");
		}

		if(node.getRight()!=null) {
			System.out.println(indent+"right");
			preorderTraverse(node.getRight(),indent+" ");
		}
	}

}
