package com.datastructures.nonlinear.ads;

/**
 * Instance of Node represents node of a tree.
 * 
 * @author Sasi on 29-Apr-2018, 2:06:42 pm
 *
 */
public class Node {
	
	private int value;
	private Node left;
	private Node right;
	
	/**Auxiliary members for some advanced data structures **/
	private int height; //Incase of AVL trees
	
	private Node parent; //Incase of RB trees
	private boolean isNullLeaf; //Incase of RB trees
	public enum Color{RED, BLACK}; //Incase of RB trees
	private Color color;
	
	public Node(){
		
	}
	
	public Node(int value) {
		super();
		this.value = value;
	}

	
	public Node(int value, Color color) {
		super();
		this.value = value;
		this.color = color;
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean isNullLeaf() {
		return isNullLeaf;
	}

	public void setNullLeaf(boolean isNullLeaf) {
		this.isNullLeaf = isNullLeaf;
	}

	@Override
	public String toString() {
		return  String.valueOf(value);
	}
}
