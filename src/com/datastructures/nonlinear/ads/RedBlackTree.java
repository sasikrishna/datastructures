package com.datastructures.nonlinear.ads;

import java.util.concurrent.atomic.AtomicReference;

import com.datastructures.nonlinear.ads.Node.Color;

/**
 * Red black tree is self balancing binary search tree along with following properties.
 * <ul>
 * 	<li>Each node must be colored with either black or red</li>
 * 	<li>Root node must black</li>
 * 	<li>Red node shouldn't have red child node</li>
 * 	<li>Number of black nodes root to every null node should be same</li>
 * 	<li>All leaf nodes are null black nodes</li>
 * </ul>
 * 
 * Time complexity for inserting, deleting and searching can be achieved in O(logn).
 * 
 * @author Sasi on 01-May-2018, 1:06:40 pm
 *
 */
public class RedBlackTree {
	
	public Node insertNode(Node root, int data) throws IllegalAccessException{
		return insertNode(null, root, data);
	}
	
	private Node insertNode(Node parent, Node root, int data) throws IllegalAccessException {
		
		//Reached bottom of tree to add new node
		if(null == root || root.isNullLeaf()){
			//Creating black node if parent is not exists else red
			return createNode(parent, data, (null == parent) ? Color.BLACK : Color.RED);
		}
		
		if(data == root.getValue()){
			throw new IllegalAccessException("Node already exists.");
		}
		
		//isLeft is used for finding type of rotation we need to apply if we ran into null or black sibling
		boolean isLeft;
		
		if(data < root.getValue()){
			
			//Traversing left subtree if node to be inserted is less than root
			Node left = insertNode(root, root.getLeft(), data);
			
			if((root.getParent() != null) && left.getValue() == root.getParent().getValue()){
				return left;
			}
			
			root.setLeft(left);
			isLeft = true;
		}else{
			
			//Traversing right subtree if node to be inserted is greater than root
			Node right = insertNode(root, root.getRight(), data);
			
			if((root.getParent() != null) && right.getValue() == root.getParent().getValue()){
				return right;
			}
			
			root.setRight(right);
			isLeft = false;
		}
		
		if(isLeft){
			
			//Do nothing if root node is black
			if(root.getColor() == Color.BLACK){
				return root;
			}
			
			//Checking if Red-Red node connection is formed
			if(root.getColor() == Color.RED && root.getLeft().getColor() == Color.RED){
				Node sibling = findSibling(root, true);
				
				//Case 1: Checking if sibling is not present or it is black node
				if(sibling == null || sibling.getColor() == Color.BLACK){
					
					if(isLeftChild(root)){
						doRightRotation(root, true);
					}else{
						doLeftRotation(root, false);
						root=root.getParent();
						doRightRotation(root, true);
					}
				}
				//Case 2: Checking if sibling is also a red node then re-color the root, sibling nodes to balance
				else{
					root.setColor(Color.BLACK);
					sibling.setColor(Color.BLACK);
					
					if(root.getParent().getParent() != null){
						root.getParent().setColor(Color.RED);
					}
				}
			}
		}else {
            if(root.getColor() == Color.RED && root.getRight().getColor() == Color.RED) {
                Node sibling = findSibling(root, false);
                
                if(sibling ==null || sibling.getColor() == Color.BLACK) {
                	
                    if(!isLeftChild(root)) {
                    	doLeftRotation(root, true);
                    } else {
                    	doLeftRotation(root.getRight(), false);
                        root = root.getParent();
                        doRightRotation(root, true);
                    }
                } else {
                    root.setColor(Color.BLACK);
                    sibling.setColor(Color.BLACK);
                    
                    if(root.getParent().getParent() != null){
						root.getParent().setColor(Color.RED);
					}
                }
            }
        }
		
		return root;
	}
	
	public Node deleteNode(Node root, int data){
		AtomicReference<Node> rootReference = new AtomicReference<Node>();
		delete(root, data, rootReference);
		return (rootReference.get() == null) ? root : rootReference.get();
	}
	
	private void delete(Node root, int data, AtomicReference<Node> rootReference) {
		
		if(null == root || root.isNullLeaf()){
			return;
		}

		//Found node which needs to be deleted
		if(root.getValue() == data){
			
			//Finding case if node has only one child
			if(root.getLeft().isNullLeaf() || root.getRight().isNullLeaf()){
				deleteOneChild(root, rootReference);
			}
		}
		
		if(data < root.getValue()){
			delete(root.getLeft(), data, rootReference);
		}else{
			delete(root.getRight(), data, rootReference);
		}
	}

	private void deleteOneChild(Node root, AtomicReference<Node> rootReference) {
		Node child = root.getLeft().isNullLeaf() ? root.getRight() : root.getLeft();
		replaceNode(root, child, rootReference);
		
		//If node going to be deleted is black then we need to execute special cases 
		//to maintain constant black node height.
		if(root.getColor() == Color.BLACK){
			
			//if root has only one red child then 
			if(child.getColor() == Color.RED){
				child.setColor(Color.BLACK);
			}else{
				deleteCase1(child,rootReference);
			}
		}
	}

	/**
	 *  This case gets applied when double black node is parent node.
	 *  
	 * @param doubleBlackNode
	 * @param rootReference
	 */
	private void deleteCase1(Node doubleBlackNode, AtomicReference<Node> rootReference) {
		
		//Child itself is root so return child as root
		if(doubleBlackNode.getParent() == null){
			rootReference.set(doubleBlackNode);
			return;
		}
		
		deleteCase2(doubleBlackNode, rootReference);
	}

	/**
	 * This case gets applied when sibling is red and it's children are black.
	 * 
	 * Action: Do left rotation and recolor (swap parent and sibling colors and proceed to next case)
	 *  
	 * @param doubleBlackNode
	 * @param rootReference
	 */
	private void deleteCase2(Node doubleBlackNode, AtomicReference<Node> rootReference) {
		Node sibling = findSibling(doubleBlackNode, isLeftChild(doubleBlackNode));
		if(sibling.getColor() == Color.RED){
			if(isLeftChild(sibling)){
				doRightRotation(sibling, true);
			}else{
				doLeftRotation(sibling, true);
			}
			
			if(null == sibling.getParent()){
				rootReference.set(sibling);
			}
		}
		
		deleteCase3(doubleBlackNode, rootReference);
	}

	/**
	 * This case gets applied when parent, sibling is black node.
	 * After applying this case double node bubbled up to process further.
	 * 
	 * Action: Recolor sibling to red node
	 * 
	 * @param doubleBlackNode
	 * @param rootReference
	 */
	private void deleteCase3(Node doubleBlackNode, AtomicReference<Node> rootReference) {
		
		Node sibling = findSibling(doubleBlackNode, isLeftChild(doubleBlackNode));
		
		if(doubleBlackNode.getParent().getColor() == Color.BLACK && sibling.getColor() == Color.BLACK){
			if( null != sibling.getLeft() && Color.BLACK == sibling.getLeft().getColor()
					&&  null != sibling.getRight() && Color.BLACK == sibling.getRight().getColor()){
				sibling.setColor(Color.RED);
				deleteCase1(doubleBlackNode, rootReference);
			}
		}else{
			deleteCase4(doubleBlackNode, rootReference);
		}
	}

	/**
	 * This case gets applied when parent is red node and sibling is black and it's childrens are black.
	 * 
	 * Action: Swap parent and sibling colors i.e parent gets black and sibling gets red
	 * 
	 * Termination case so after performing action we'll return to main.
	 * 
	 * @param doubleBlackNode
	 * @param rootReference
	 */
	private void deleteCase4(Node doubleBlackNode, AtomicReference<Node> rootReference) {
		Node sibling = findSibling(doubleBlackNode, isLeftChild(doubleBlackNode));
		
		if(doubleBlackNode.getParent().getColor() == Color.RED 
				&& sibling.getColor() == Color.BLACK){
			if( null != sibling.getLeft() && Color.BLACK == sibling.getLeft().getColor()
					&&  null != sibling.getRight() && Color.BLACK == sibling.getRight().getColor()){
				doubleBlackNode.getParent().setColor(Color.BLACK);
				sibling.setColor(Color.RED);
				return;
			}
		}else{
			deleteCase5(doubleBlackNode, rootReference);
		}
	}

	/**
	 * This case gets applied when sibling is black and it's left children is red. 
	 * 
	 * Action: Do right rotation at sibling left child and prepare tree for deleteCase6
	 * 
	 * @param doubleBlackNode
	 * @param rootReference
	 */
	private void deleteCase5(Node doubleBlackNode, AtomicReference<Node> rootReference){
		Node sibling = findSibling(doubleBlackNode, isLeftChild(doubleBlackNode));
		
        if(sibling.getColor() == Color.BLACK) {
            if (isLeftChild(doubleBlackNode) && sibling.getRight().getColor() == Color.BLACK 
            		&& sibling.getLeft().getColor() == Color.RED) {
                doRightRotation(sibling.getLeft(), true);
            } else if (!isLeftChild(doubleBlackNode) && sibling.getLeft().getColor() == Color.BLACK 
            		&& sibling.getRight().getColor() == Color.RED) {
            	doLeftRotation(sibling.getRight(), true);
            }
        }
        deleteCase6(doubleBlackNode, rootReference);
	}
	
	/**
	 * This case get applied when sibling is black and sibling's right is red.
	 * 
	 * Action: do left rotation and recolor nodes
	 * 
	 * Termination case
	 * 
	 * @param doubleBlackNode
	 * @param rootReference
	 */
	private void deleteCase6(Node doubleBlackNode, AtomicReference<Node> rootReference) {
		Node sibling = findSibling(doubleBlackNode, isLeftChild(doubleBlackNode));
		sibling.setColor(sibling.getParent().getColor());
		sibling.getParent().setColor(Color.BLACK);
		
        if(isLeftChild(doubleBlackNode)) {
        	sibling.getRight().setColor(Color.BLACK);
        	doLeftRotation(sibling, false);
        } else {
        	sibling.getLeft().setColor(Color.BLACK);
        	doRightRotation(sibling, false);
        }
        if(sibling.getParent() == null) {
            rootReference.set(sibling);
        }
	}

	private void replaceNode(Node root, Node child, AtomicReference<Node> rootReference) {
		//Setting root's parent as parent to child node
		child.setParent(root.getParent());
		
		if(null == root.getParent()){
			rootReference.set(child);
		}
		else{
			if(isLeftChild(root)){
				root.getParent().setLeft(child);
			}else{
				root.getParent().setRight(child);
			}
		}
	}

	/**
	 * 
	 * @param root
	 * @param reColor
	 */
	private void doRightRotation(Node root, boolean reColor) {
		
		Node parent = root.getParent();
		Node parentParent = parent.getParent();
		Node right = root.getRight();
		
		//Changing roots parent to parent's parent
		root.setParent(parentParent);
		if(parentParent != null){
			
			if(parentParent.getLeft().getValue() == parent.getValue()){
				parentParent.setLeft(root);
			}else{
				parentParent.setRight(root);
			}
		}
		
		//Changing parent parent to root
		parent.setParent(root);
		root.setRight(parent);
		
		//Moving roots right childs to left of parent
		parent.setLeft(right);
		if(right != null){
			right.setParent(parent);
		}
		
		if(reColor){
			root.setColor(Color.BLACK);
			parent.setColor(Color.RED);
		}
	}

	private void doLeftRotation(Node root, boolean reColor){
		Node parent = root.getParent();
		Node parentsParent = parent.getParent();
		Node left = root.getLeft();
		
		//Changing root parents to parentsParent
		root.setParent(parentsParent);
		if(parentsParent != null){
			if(parentsParent.getRight().getValue() == parent.getValue()){
				parentsParent.setRight(root);
			}else{
				parentsParent.setLeft(root);
			}
		}
		
		//Changing parent parent to root
		parent.setParent(root);
		root.setLeft(parent);
		
		//Moving roots left child as right childs parent
		parent.setRight(left);
		if(left != null)
			left.setParent(parent);
		
		if(reColor){
			root.setColor(Color.BLACK);
			parent.setColor(Color.RED);
		}
	}
	
	private boolean isLeftChild(Node root) {
		
		Node parent = root.getParent();
		if(parent == null || parent.getLeft() == null)
			return false;
		
		if(parent.getLeft().getValue() == root.getValue())
			return true;
		return false;
	}

	/**
	 * Returns sibling for given root node based on isLeft variable. 
	 * Right sibling will be returned if isLeft is true else left sibling.
	 * 
	 * @param root
	 * @param isLeft
	 * @return {@code Node}
	 */
	public Node findSibling(Node root, boolean isLeft){
		
		if(null == root || null == root.getParent()){
			return null;
		}
		
		if(isLeft){
			return root.getParent().getRight();
		}else{
			return root.getParent().getLeft();
		}
	}
	
	public Node createNode(Node parent, int data, Color color){
		Node node = new Node(data);
		node.setColor(color);
		node.setParent(parent);
		node.setNullLeaf(false);
		node.setLeft(createNullLeafNode(node));
		node.setRight(createNullLeafNode(node));
		return node;
	}
	
	public Node createNullLeafNode(Node parent){
		Node node = new Node();
		node.setNullLeaf(true);
		node.setParent(parent);
		return node;
	}
	
	public static void main(String[] args) throws IllegalAccessException {
		RedBlackTree tree = new RedBlackTree();
		Node rootNode = null;
		
		rootNode = tree.insertNode(rootNode, 10);
		rootNode = tree.insertNode(rootNode, 15);
		rootNode = tree.insertNode(rootNode, -10);
		rootNode = tree.insertNode(rootNode, 20);
        rootNode = tree.insertNode(rootNode, 30);
        rootNode = tree.insertNode(rootNode, 40);
        rootNode = tree.insertNode(rootNode, 50);
        rootNode = tree.insertNode(rootNode, -15);
        rootNode = tree.insertNode(rootNode, 25);

		Traversals.inorderTraversalForRB(rootNode);
		rootNode = tree.deleteNode(rootNode, -10);
		System.out.println("\r\nDeleting node -10...");
		Traversals.inorderTraversalForRB(rootNode);
		
		rootNode = tree.insertNode(rootNode, 17);
		System.out.println("\r\nInserting node 17...");
		Traversals.inorderTraversalForRB(rootNode);
	}
}
