package com.datastructures.nonlinear;


/**
 * <p>
 * 	<code>BinaryTree</code> represents binary data structure implementation in which each node can have atmost two children.
 *  Starting node we'll call as root node and from there tree starts growing down. Each node will have two pointers its left and right child.
 * </p>
 * <p>Time complexity for insertion, deletion and searching takes O(n) (because of no ordering).</p>
 * <p>
 * 	With binary tree we can build Binary Search Tree, AVL Trees, Red-Black Trees which offer good complexity (O(logn)) for insertion, deletion and search.
 *</p>
 * 
 * @author Sasi on 06-May-2018, 4:03:18 pm
 */
public class BinaryTree {
	
	private Node root;
	
	public BinaryTree() {
		
	}
	
	public static void main(String[] args) {
		BinaryTree binaryTree = new BinaryTree();
		
		//Setting root value - level 1
		binaryTree.root = new Node(1);
		
		//Setting values to level 2 nodes
		binaryTree.root.setLeft(new Node(10));
		binaryTree.root.setRight(new Node(13));
		
		//Setting values to level 3 nodes
		binaryTree.root.getLeft().setLeft(new Node(114));
		binaryTree.root.getLeft().setRight(new Node(115));
		binaryTree.root.getRight().setLeft(new Node(117));
		binaryTree.root.getRight().setRight(new Node(116));
		
		Traversals.inorderTraversal(binaryTree.root);
	}
}
