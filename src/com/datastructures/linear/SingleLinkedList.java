package com.datastructures.linear;

/**
 * {@code SingleLinkedList} class represents implementation of single linked list contains basic operations like add, remove and get.
 * <br><br>
 * Time Complexities:<br>
 *  adding node    : O(1)<br>
 *  removing node  : O(n)<br>
 * 
 * @author Sasi on 03-May-2018, 8:11:27 pm
 * @param <T>
 */
public class SingleLinkedList<T> implements List<T>{
	
	Node head;
	
	public SingleLinkedList() {
		super();
		head = null;
	}

	class Node{
		T key;
		Node nextNode;
		
		public Node(T key) {
			super();
			this.key = key;
		}

		public Node getNextNode() {
			return nextNode;
		}

		public void setNextNode(Node nextNode) {
			this.nextNode = nextNode;
		}
	}

	@Override
	public T add(T key) {
		
		//Checking if head is null if so create new node and return
		if(null == head){
			head = new Node(key);
			return key;
		}
		
		//If head is not null then linked list already exists.
		//So create new node and append at the start of linked list and make it as head.
		Node newNode = new Node(key);
		newNode.setNextNode(head);
		head= newNode;
		
		return key;
	}

	@Override
	public boolean remove(T key) throws Exception{
		
		if(null == head){
			throw new Exception("Linked list is empty.");
		}
		
		if(null == key){
			throw new Exception("Key is empty");
		}
		
		Node current = head;
		
		//Verifying with root node key
		if(key.equals(head.key)){
			
			//Pointing head to next node
			head = head.nextNode;
			current = null;
			return true;
		}
		
		//Iterate through linked and remove the key when match is found
		Node prevNode = null;
		prevNode = current;
		current = current.nextNode;
		while(current != null){
			
			//Checking for the match
			if(key.equals(current.key)){
				
				//Breaking the link between prev and current. Setting next node reference into prev node.
				prevNode.setNextNode(current.nextNode);
				current = null;
				return true;
			}
			
			prevNode = current;
			current = current.nextNode;
		}
		
		return false;
	}

	@Override
	public T get(int index) throws ArrayIndexOutOfBoundsException{
		
		if(index == 0){
			return head.key;
		}
		
		Node current = head; 
		int i=0;
		while(current != null){
			if(i == index){
				return current.key; 
			}
			i++;
			current = current.nextNode;
		}
		
		throw new ArrayIndexOutOfBoundsException();
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ ");
		
		Node current = head;
		while(current != null){
			builder.append(current.key).append(" ");
			current = current.nextNode;
		}
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) throws Exception {
		List<Integer> linkedList = new SingleLinkedList<Integer>();
		linkedList.add(23);
		linkedList.add(56);
		linkedList.add(99);
		linkedList.add(-12);
		System.out.println(linkedList);
		
		linkedList.remove(99);
		System.out.println(linkedList);
		
		System.out.println("Key at index 2: " + linkedList.get(2));
	}
}
