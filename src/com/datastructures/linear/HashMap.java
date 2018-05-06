package com.datastructures.linear;

import java.util.ArrayList;

/**
 * <p>HashMap class is a implementation of HashMap data structure.
 * Balance factor has been set to 0.7 so when size reaches to 70 percent of number of buckets, buckets list will be doubled and keys will be re-created.</p>
 *
 * <p>This HashMap implements separate chaining technique to handle collisions. That means each bucket contains linked list to store actual values to handle collisions.</p>
 * 
 * <p>Time complexities: adding - O(1), deletion - O(1) and remove - O(1) but in worst cases each operation may take O(n) because of collisions. 
 * This can be reduced to O(logn) if we use AVL tree or RED-Black trees instead of linked lists to store values.</p>
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
			bucketList.set(0, new HashNode<K, V>(null, value));
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
		//Handling special case for null key to remove
		if(null == key)
		{
			removeForNullKey(key);
			return;
		}
		
		int index = getBucketIndex(key);
		HashNode<K, V> current = bucketList.get(index);
		current = removeNode(current, key);
		
		//Set back head node into bucket
		bucketList.set(index, current);
	}
	
	private void removeForNullKey(K key) {
		HashNode<K, V> current = bucketList.get(0);
		
		current = removeNode(current, key);
		
		//Set back head node into bucket
		bucketList.set(0, current);
	}
	
	/**
	 * Internal method which actually removes node which matches to given key.
	 * 
	 * @param current
	 * @return
	 */
	private HashNode<K, V> removeNode(HashNode<K, V> current, K k){
		
		if(null == current)
			return current;
		
		HashNode<K, V> prev = null;
		//Iterating through linked list for null key and replacing its value with new value
		for(HashNode<K, V> temp = current;temp != null; temp = temp.nextNode){
			
			if(k == temp.key){
				
				if(prev != null){
					prev.nextNode =temp.nextNode;
				}else{
					current = temp.nextNode;
				}
				temp = null;
				break;
			}
			
			prev = temp;
		}
		
		this.size--;
		
		return current;
	}

	public V get(K key){
		
		if(null == key){
			HashNode<K, V> node =  bucketList.get(0);
			for(;node != null;node=node.nextNode){
				if(null == node.key){
					return node.value;
				}
			}
		}
		
		int index = getBucketIndex(key);
		HashNode<K, V> node = bucketList.get(index);
		
		for(;null != node;node=node.nextNode){
			if(key.equals(node.key)){
				return node.value;
			}
		}
		
		return null;
	}
	
	public int size(){
		return size;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	private int getBucketIndex(K key){
		int hashCode = Math.abs(key.hashCode());
		
		//Compressor --> gets index for the value to sit in hash table
		return hashCode % numberOfBuckets;
	}
	
	@SuppressWarnings("hiding")
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
		map.put(null, 123);
		System.out.println(map);
		
		map.remove(null);
		System.out.println(map);
		
		map.put("adam", 11);
		map.put("pete", 22);
		map.put("john", 33);
		map.put("rachel", 44);
		map.put("smith", 55);
		map.put("doug", 66);
		map.put("hall", 77);
		map.put("adrene", 88);
		map.put("ajay", 99);
		System.out.println(map);
		
		System.out.println("smith value: " + map.get("smith"));
		System.out.println("ajay value: " + map.get("ajay"));
		
		map.remove("hall");
		map.remove("adrene");
		System.out.println(map);
	}
}
