package com.datastructures.linear;

import java.util.Random;


/**
 * 
 * @author Sasi on 09-May-2018, 12:53:45 am
 *
 */
public class SkipList<K extends Comparable<K>, V> {

	private Node head;
	private Random random;
	private int size;
	private double probability;
	
	private class Node{
		private K key;
		private V value;
		private long level;
		private Node next;
		private Node down;
		public Node(K key, V value, long level, SkipList<K, V>.Node next, SkipList<K, V>.Node down) {
			super();
			this.key = key;
			this.value = value;
			this.level = level;
			this.next = next;  
			this.down = down;
		}
	}
	
	public SkipList(){
		head = new Node(null, null, 0, null, null);
		random = new Random();
		size = 0;
		probability = 0.5;
	}

	private long getLevel(){
		long level = 0;
		while(level <= size && random.nextDouble() < probability){
			level++;
		}
		
		return level;
	}
	
	public void insert(K key, V value){
		
		long level = getLevel();
		System.out.println("getLevel(): " + level);
		if(level > head.level){
			head = new Node(null, null, level, null, head);
		}
		
		Node current = head, last = null;
		while(null != current){
			if(current.next == null || current.next.key.compareTo(key) > 0){
				if(level >= current.level){
					Node newNode = new Node(key, value, current.level, current.next, null);
					
					if(last != null){
						last.down= newNode;
					}
					
					current.next = newNode;
					//System.out.println(newNode.value + " inserted after " + current.value + " at level " + current.level);
					last = newNode;
				}
				
				current = current.down;
				continue;
			}else if(current.next.key.compareTo(key) == 0){
				current.next.value = value;
				return;
			}
			current = current.next;
		}
		size++;
	}
	
	public V get(K key){
		Node current = head;
		while(null != current){
			if(null == current.next || current.next.key.compareTo(key) > 0){
				current = current.down;
				continue;
			}else if(current.next.key.compareTo(key) < 0){
				current = current.next;
			}else if(current.next.key.equals(key))
			{
				return current.next.value;
			}
		}
		return null;
	}
	
	public V delete(K key){
		Node current = head;
		V value = null;
		while(null != current){
			if(null == current.next || current.next.key.compareTo(key) > 0){
				current = current.down;
				continue;
			}else if(current.next.key.compareTo(key) < 0){
				current = current.next;
			}else if(current.next.key.equals(key))
			{
				value = current.next.value;
				current.next =  current.next.next;
				current = current.down;
			}
		}
		return value;
	}
	
	public String toString(){
		Node current = head;
		
		StringBuffer sb = new StringBuffer();
		sb.append("[").append("\r\n");
		while(null != current){
			Node down = current.down;
			
			sb.append("Level:" + current.level).append(" ");
			while(current != null){
				
				if(current.key == null && current.value == null){
					//Do nothing if it infinite node
				}else{
					sb.append(current.value).append(" ");
				}
				current = current.next;
			}
			sb.trimToSize();
			sb.append("\r\n");
			current = down;
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		SkipList<Integer, Integer> skipList = new SkipList<>();
		skipList.insert(1, 1);
		skipList.insert(10, 10);
		skipList.insert(30, 30);
		skipList.insert(50, 50);
		skipList.insert(40, 40);
		System.out.println(skipList);
		
		System.out.println("Value for key: 50 is " + skipList.get(50));
		System.out.println("Value for key: 40 is " + skipList.get(40));
		System.out.println("Deleting key: 30 is " + skipList.delete(30));
		System.out.println(skipList);
	}
}
