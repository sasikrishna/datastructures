package com.datastructures.nonlinear.ads;

/**
 * AVLTree is a self balanced binary search tree with height balancing factors -1, 0 and 1.
 * 
 * @author Sasi on 29-Apr-2018, 2:10:27 pm
 * 
 */
public class AVLTree {
	
	public Node insertNode(Node root, int data){
		
		//If root is null create new node and return
		if(null == root){
			return new Node(data);
		}
		
		//Traverse to bottom of tree to insert new node
		if(data < root.getValue()){
			
			//New node to be insert is less than root so traversing left sub tree
			root.setLeft(insertNode(root.getLeft(), data));
		}else{
			
			//New node to be insert is greater than root so traversing right sub tree
			root.setRight(insertNode(root.getRight(), data));
		}
		
		//Update root height
		updateNodeHeight(root);
		
		//Getting the height diff between left and right sub trees 
		int balance = getBalance(root);
		
		//Checking if tree is satisfying left-left rotation principal
		if(balance > 1 && data < root.getLeft().getValue()){
			root = doRightRotation(root);
		}
		//Checking if tree is satisfying left-right rotation principal
		else if(balance > 1 && data > root.getLeft().getValue()){
			root = doLeftRightRotation(root);
		}
		//Checking if tree is satisfying right-right rotation principal
		else if(balance < -1 && data > root.getRight().getValue()){
			root = doLeftRotation(root);
		}
		//Checking if tree is satisfying right-left rotation principal
		else if(balance < -1 && data < root.getRight().getValue()){
			doRightLeftRotation(root);
		}
		
		return root;
	}
	
	public Node deleteNode(Node root, int data){
		
		if(root == null)
			return null;
		
		//Checking if node to be deleted is less than root node value. 
		//If so traversing left sub tree
		if(data < root.getValue()){
			root.setLeft(deleteNode(root.getLeft(), data));
		}
		//Traversing right sub tree if node to be deleted if greater than root node
		else if(data > root.getValue()){
			root.setRight(deleteNode(root.getRight(), data));
		}
		else{
			Node temp = null;
			
			//we have reached to node that has to be deleted. Checking for childrens.
			if(root.getLeft() == null || root.getRight() == null){
				
				//copying either left or right child node into temp node
				if(temp == root.getLeft()){
					temp = root.getRight();
				}else{
					temp = root.getLeft();
				}
				
				//temp will be null only if root doesn't have any child nodes.
				if(temp == null){
					root = null;
				}else{
					root = temp;
				}
			}else{
				temp = minSuccessor(root.getRight());
				root.setValue(temp.getValue());
				root.setRight(deleteNode(root.getRight(), temp.getValue()));
			}
			
			if(root == null){
				return null;
			}
		}
		
		//Verifying the balanced tree conditions
		int balance = getBalance(root);
		
		//Update root height
		updateNodeHeight(root);
		
		//Doing necessary rotations based on balance factor
		if(balance > 1 && getBalance(root.getLeft()) >= 0){
			root = doRightRotation(root);
		}else if(balance > 1 && getBalance(root.getLeft()) < 0){
			root = doLeftRightRotation(root);
		}else if(balance < -1 && getBalance(root.getRight()) <= 0){
			root = doLeftRotation(root);
		}else if(balance < -1 && getBalance(root.getRight()) > 0){
			root = doRightLeftRotation(root);
		}
		
		return root;
	}
	
	private Node minSuccessor(Node root) {
		Node current = root;
		
		while(current != null){
			current = current.getLeft();
		}
		return current;
	}

	public Node doLeftRotation(Node root){
		Node newRoot = root.getRight();
		root.setRight(newRoot.getLeft());
		newRoot.setLeft(root);
		updateNodeHeight(newRoot);
		return newRoot;
	}
	
	public Node doRightRotation(Node root){
		Node newRoot = root.getLeft();
		root.setLeft(newRoot.getRight());
		newRoot.setRight(root);
		updateNodeHeight(newRoot);
		return newRoot;
	}
	
	public Node doLeftRightRotation(Node root){
		root.setLeft(doLeftRotation(root.getLeft()));
		root = doRightRotation(root);
		return root;
	}
	
	public Node doRightLeftRotation(Node root){
		root.setRight(doLeftRotation(root.getRight()));
		root = doRightRotation(root);
		return root;
	}
	
	public int getBalance(Node node){
		if(null == node)
			return 0;
		
		return (null != node.getLeft() ? node.getLeft().getHeight() : 0) - 
				(null != node.getRight() ? node.getRight().getHeight() : 0);
	}
	
	public void updateNodeHeight(Node node){
		node.setHeight(1 + Math.max(node.getLeft() != null ? node.getLeft().getHeight() : 0,
				node.getRight() != null ? node.getRight().getHeight() : 0));
	}
	
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		
		Node rootNode = null;
		rootNode = tree.insertNode(rootNode, 10);
		rootNode = tree.insertNode(rootNode, 15);
		rootNode = tree.insertNode(rootNode, 6);
		rootNode = tree.insertNode(rootNode, 4);
		rootNode = tree.insertNode(rootNode, 5);
		rootNode = tree.insertNode(rootNode, 7);
		rootNode = tree.insertNode(rootNode, 8);
		
		Traversals.inorderTraversal(rootNode);
		
		tree.deleteNode(rootNode, 5);
		System.out.println();
		Traversals.inorderTraversal(rootNode);
		
		tree.deleteNode(rootNode, 7);
		System.out.println();
		Traversals.inorderTraversal(rootNode);
	}
}
