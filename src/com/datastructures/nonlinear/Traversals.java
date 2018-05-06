package com.datastructures.nonlinear;


/**
 * <code>Traversals</code> class contains utility methods to traverse trees.
 * 	Traversal techniques includes inorder, pre-order, post-order and level order traversals.
 * 
 * @author Sasi on 29-Apr-2018, 2:16:48 pm
 */
public class Traversals {
	
	/**
	 *	Inorder traversals processes left node first then root node and then right node.   
	 *
	 * @param root
	 */
	public static void inorderTraversal(Node root){
		if(root != null){
			inorderTraversal(root.getLeft());
			System.out.print(root + " ");
			inorderTraversal(root.getRight());
		}
	}
	
	/**
	 * This utiltiy method is only meant for traversing Red-Black trees.
	 * 
	 * @param root
	 */
	public static void inorderTraversalForRB(Node root){
		if(root != null && !root.isNullLeaf()){
			inorderTraversalForRB(root.getLeft());
			System.out.print(root + " ");
			inorderTraversalForRB(root.getRight());
		}
	}
}
