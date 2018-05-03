package com.datastructures.linear;

import java.util.ArrayList;

/**
 * 
 * @author Sasi on 03-May-2018, 8:08:11 pm
 */
public class HashMap<K, V> {
	
	ArrayList<HashNode<K, V>> bucketList;
	float balanceFactor;
	int numberOfBuckets;
	int size;
	
	
	public HashMap() {
		super();
		
		//Initializing with default values
		balanceFactor = 0.7f;
		numberOfBuckets = 10;
		size = 0;
		
		bucketList = new ArrayList<>();
		for(int i=0;i<numberOfBuckets;i++){
			bucketList.add(null);
		}
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public V put(K key, V value) throws Exception{
		
		//Handling special case for null key
		if(key == null){
			return putForNullKey(value);
		}

		//Getting bucket number for key
		int bucketNumber = getBucketIndex(key);

		//Checking if key already exists
		HashNode<K, V> node = bucketList.get(bucketNumber);
		while(null != node){
			if(node.key.equals(key)){
				return node.value;
			}
			
			node = node.nextNode;
		}
		
		size++;
		
		//Getting node for given bucket number
		node = bucketList.get(bucketNumber);
		
		//Creating new node for given key
		HashNode<K, V> newNode = new HashNode<K, V>(key, value);
		
		if(null == node){
			node = newNode;
		}else{
			newNode.nextNode = node;
			node = newNode;
		}
		
		//Set back updated node into buckerArray
		bucketList.set(bucketNumber, node);
		
		//Checking if map size is reached balance factor
		if(size/numberOfBuckets >= balanceFactor){
			numberOfBuckets = 2 * numberOfBuckets;
			size = 0;
			ArrayList<HashNode<K, V>> tempList = bucketList;
			
			for(int i=0;i<numberOfBuckets;i++){
				tempList.add(null);
			}
			
			for(HashNode<K, V> tempNode : tempList){
				while(tempNode != null){
					this.put(tempNode.key, tempNode.value);
					tempNode = tempNode.nextNode;
				}
			}
		}
		
		return value;
	}
	
	private V putForNullKey(V value) {
		//Storing value at index zero for null key
		HashNode<K, V> node = bucketList.get(0);
		
		if(node == null){
			bucketList.add(new HashNode<K, V>(null, value));
		}
		else{
			//Iterating through linked list for null key and replacing its value with new value
			for(;node != null; node = node.nextNode){
				if(node.key == null){
					V oldValue = node.value;
					node.value = value;
					return oldValue;
				}
			}
		}
		
		return value;
	}

	public void remove(K key){
		
	}
	
	public V get(K key){
		
		return null;
	}
	
	public int size(){
		return size;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	private int getBucketIndex(K key){
		int hashCode = key.hashCode();
		
		//Compressor --> gets index for the value to sit in hash table
		return hashCode % numberOfBuckets;
	}
	
	class HashNode<K, V>{
		K key;
		V value;
		HashNode<K, V> nextNode;
		
		public HashNode(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		
		ArrayList<HashNode<K, V>> tempList = bucketList;
		for(HashNode<K, V> tempNode : tempList){
			while(tempNode != null){
				builder.append(tempNode.key).append(" = ").append(tempNode.value).append(",");
				tempNode = tempNode.nextNode;
			}
		}
		
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) throws Exception {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("one", 1);
		map.put("two", 2);
		
		System.out.println(map);
	}
}
