package com.datastructures.linear;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Sasi on 01-Jun-2018, 8:54:06 am
 */
public class DisjointSets<T> {
	
	Map<T, Node<T>> disjointSet;
	
	public DisjointSets(){
		disjointSet = new HashMap<T, Node<T>>();
	}
	
	/**
	 * 
	 * @param nodeId
	 */
	public void makeSet(T name){
		Node<T> newNode = new Node<T>(name);
		newNode.parent = newNode;
		disjointSet.put(name, newNode);
	};
	
	/**
	 * 
	 * @param node1Id
	 * @param node2Id
	 */
	public void union(T node1Id, T node2Id){
		
		Node<T> parent = disjointSet.get(node1Id);
		Node<T> childNodeParent = disjointSet.get(node2Id);
		
		while(parent.name != parent.parent.name){
			parent = parent.parent;
		}
		
		childNodeParent.parent = parent;
	}
	
	/**
	 * 
	 * @param nodeId
	 */
	public T findSet(T name){
		Node<T> node = disjointSet.get(name);
		if(node.parent.name == name){						//Checking if this element is alone in the subset
			return name;
		}
		
		while(node.name != node.parent.name){
			node = node.parent;
		}
		
		return node.name;
	}
	
	class Node<T>{										//Node instance represents each element in the set
		T name;
		Node<T> parent;

		public Node(T name) {
			super();
			this.name = name;
		}
		
		public Node(T name, Node<T> parent) {
			super();
			this.name = name;
			this.parent = parent;
		}

		@Override
		public String toString() {
			return name + " " + parent.name; 
		}
	}
	
	public Map<T, Node<T>> getDisjointSet(){
		return disjointSet;
	}
	
	public static void main(String[] args) {
		DisjointSets<Character> set = new DisjointSets<Character>();
		set.makeSet('A');
		set.makeSet('B');
		set.makeSet('C');
		set.makeSet('D');
		set.makeSet('E');
		
		set.union('A', 'B');
		set.union('B', 'C');
		set.union('C', 'D');
		set.union('D', 'E');
		
		System.out.println(set.disjointSet);
		
		System.out.println(set.findSet('C'));
		System.out.println(set.findSet('D'));
		System.out.println(set.findSet('E'));
	}
}
