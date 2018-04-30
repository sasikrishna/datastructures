package com.datastructures.nonlinear.ads;

/**
 * 
 * @author Sasi on 29-Apr-2018, 2:06:42 pm
 *
 */
public class Node {
	
	private int value;
	private Node left;
	private Node right;
	
	/**Auxiliary members for some data structures **/
	private int height; //Incase of AVL trees

	public Node(){
		
	}
	
	public Node(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return  String.valueOf(value);
	}
}
