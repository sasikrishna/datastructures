package com.datastructures.nonlinear;

/**
 * 
 * @author Sasi on 06-May-2018, 4:40:57 pm
 */
public class BinarySearchTree {
	
	BinaryTree binaryTree;
	
	public BinarySearchTree(){
		binaryTree = new BinaryTree();
	}
	
	public int insert(int value){
		
		Node root = binaryTree.getRoot();
		
		//Checking if root is null if so create new node as root node
		if(root == null){
			binaryTree.setRoot(new Node(value));
		}else{
			binaryTree.setRoot(insert(value, root));
		}
		
		return value;
	}
	
	/**
	 * <p>Insert method recursively calls itself untill we reach to bottom of tree to insert new node.<p>
	 * 
	 * <p>Time complexity for inserting new node is O(logn) because we aren't traversing whole 
	 * tree rather based on value of node if it is less than root node traversing to left else right subtree<p> 
	 * 
	 * @param value
	 * @param root
	 * @return newly added node
	 */
	private Node insert(int value, Node root){
		if(value < root.getValue()){
			
			if(null == root.getLeft()){
				root.setLeft(new Node(value));
			}
			else{
				insert(value, root.getLeft());
			}
		}
		else if(value > root.getValue()){
			
			if(null == root.getRight()){
				root.setRight(new Node(value));
			}
			else{
				insert(value, root.getRight());
			}
		}
		
		return root;
	}
	
	/**
	 * 
	 * @param value
	 */
	public void delete(int value){
		Node root = binaryTree.getRoot();
		if(root == null)
			return;
		
		root = delete(root, value);
	}
	
	/**
	 * delete method removes the node which contains the given value.
	 * It traverses through left/right subtree depending upon the given value.
	 * Once we reached the node that has to be deleted, we'll check for below conditions and proceed for delete.
	 * <p>
	 * 	1. Check if node to be deleted(current node) is leaf node. If delete current node safely and return.
	 *  2. Check if node to be deleted(current node) has only one child node. If so delete current node and return its left/right node.
	 *  3. Node has both childs so find inorder successor for right subchild and 
	 *  	replace current node value with inorder successor value and delete it.
	 * </p>
	 * 
	 * @param root
	 * @param value
	 * @return
	 */
	private Node delete(Node root, int value){
		
		if(null == root)
			return root;
		
		//Traversing through left/right subtree untill we find the node with given value
		if(value < root.getValue()){
			root.setLeft(delete(root.getLeft(), value));
		}else if(value > root.getValue()){
			root.setRight(delete(root.getRight(), value));
		}else{
			//Checking if current node is leaf node
			if(root.getLeft() == null){
				return root.getRight();
			}else if(root.getRight() == null){
				return root.getLeft();
			}
			
			//Current node has both left and right childs. So finding inorder successor.
			Node inorderSuccessor = getInorderSuccessor(root);
			
			//Set inorder successor value to root and remove inorderSuccessor node
			root.setValue(inorderSuccessor.getValue());
			root.setRight(delete(root.getRight(), inorderSuccessor.getValue()));
		}
		
		return root;
	}
	
	public Node getInorderSuccessor(Node root){
		
		while(null != root.getRight()){
			root = root.getRight();
		}
		return root;
	}
	
	/**
	 * Search traverses through left/right subtrees untill we find value a node with given value. 
	 * If node not found it returns false.  
	 *  
	 * @param value
	 * @return
	 */
	public boolean search(int value){
		Node root = binaryTree.getRoot();
		
		while(null != root){
			if(value < root.getValue()){
				root = root.getLeft();
			}else if(value > root.getValue()){
				root= root.getRight();
			}else{
				break;
			}
		}
		
		return (root != null && root.getValue() == value);
	}
	
	public Node getRoot(){
		return binaryTree.getRoot();
	}
	
	public static void main(String[] args) {
		BinarySearchTree bst = new BinarySearchTree();
		bst.insert(14);
		bst.insert(11);
		bst.insert(10);
		bst.insert(120);
		bst.insert(23);
		bst.insert(112);
		bst.insert(12);
		bst.insert(13);
		bst.insert(9);
		Traversals.inorderTraversal(bst.getRoot());
		
		System.out.println();
		bst.delete(10);
		Traversals.inorderTraversal(bst.getRoot());
		
		System.out.println();
		bst.delete(120);
		Traversals.inorderTraversal(bst.getRoot());

		System.out.println();
		bst.delete(14);
		Traversals.inorderTraversal(bst.getRoot());
		
		System.out.println();
		System.out.println("Is 112 number exists: " + bst.search(112));
		System.out.println("Is 224 number exists: " + bst.search(224));
		System.out.println("Is 13 number exists: " + bst.search(13));
	}
}
