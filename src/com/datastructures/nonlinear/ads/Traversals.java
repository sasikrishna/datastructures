package com.datastructures.nonlinear.ads;

/**
 * 
 * @author Sasi on 29-Apr-2018, 2:16:48 pm
 *
 */
public class Traversals {
	
	public static void inorderTraversal(Node root){
		if(root != null){
			inorderTraversal(root.getLeft());
			System.out.print(root + " ");
			inorderTraversal(root.getRight());
		}
	}
	
	public static void inorderTraversalForRB(Node root){
		if(root != null && !root.isNullLeaf()){
			inorderTraversalForRB(root.getLeft());
			System.out.print(root + " ");
			inorderTraversalForRB(root.getRight());
		}
	}
}
