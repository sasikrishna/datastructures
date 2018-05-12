package com.datastructures.linear.ads;

/**
 *  Trie class represents implementation of Trie data structure.
 *  <br>Trie data structure forms structure hierarchy by having a child array (which contains next letters) for each letter in the given key which makes it easy for searching operation.
 *  <br><br>
 *  Time complexity for adding key to Trie - O(n)<br>
 *  Time complexity for searching key - O(n) where n is length of key
 *  
 * @author Sasi on 12-May-2018, 3:58:55 pm
 */
public class Trie {
	
	static TrieNode root = new TrieNode();
	private static final int ALPHABETS_SIZE = 26;
	
	static class TrieNode{
		TrieNode[] trie = new TrieNode[ALPHABETS_SIZE];    //Initializing trieNode with total alphabets size
		boolean isEndOfWord = false;                      // Specifies this is last letter in that word
		
		public TrieNode(){
			for(int i=0;i<ALPHABETS_SIZE;i++){
				trie[i] = null;
			}
		}
	}
	
	/**
	 * 
	 * @param key
	 */
	public static void insert(String key){
		
		TrieNode tempRoot = root;
		for(int i=0;i<key.length();i++){                  // Iterating through all characters in each key and create a tries nodes if not present

			int index = key.charAt(i) - 'a';              // Getting relative index from small 'a'
			if(null ==  tempRoot.trie[index]){
				tempRoot.trie[index] = new TrieNode();    // Creating new trie node if given letter is not present in exisitng trie 
			}
			tempRoot =  tempRoot.trie[index];
		}
		
		tempRoot.isEndOfWord = true;                      // Marking last trie node as isEndOfWord true which is helpful to determine if word exsits or not 
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static boolean search(String key){
		
		TrieNode tempRoot = root;
		for(int i=0;i<key.length();i++){
			int index = key.charAt(i) - 'a';              
			if(null == tempRoot.trie[index]){
				return false;
			}
			tempRoot =  tempRoot.trie[index];
		}
		
		if(tempRoot.isEndOfWord){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param key
	 */
	public static void delete(String key){
		delete(root, key, 0, key.length());
	}
	
	private static boolean delete(TrieNode currentNode, String key, int level, int length) {
		
		if(null == currentNode){
			return false;
		}
		
		if(level == length){                 //Reached to last node in the key
			currentNode.isEndOfWord = false;
			if(hasNoChilds(currentNode)){
				return true;
			}else{
				return false;
			}
		}else{
			TrieNode childNode = currentNode.trie[key.charAt(level) - 'a'];
			boolean isChildDeleted = delete(childNode, key, level+1, length);
			if(isChildDeleted){
				return currentNode.isEndOfWord && hasNoChilds(currentNode);
			}
		}
		
		return false;
	}

	private static boolean hasNoChilds(TrieNode currentNode) {
		for(int i=0;i<currentNode.trie.length;i++){
			if(currentNode.trie[i]!=null){
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String[] keys = new String[]{"c", "cpp", "java", "python", "perl", "sql", "golang", "erlang"};
		
		for(String key:keys){
			Trie.insert(key);
		}
		
		System.out.println("Java key exists: " + Trie.search("java"));
		System.out.println("Hadoop key exists: " + Trie.search("hadoop"));
		System.out.println("Erlang key exists: " + Trie.search("erlang"));
		System.out.println("CPP key exists: " + Trie.search("cpp"));
		System.out.println("Scala key exists: " + Trie.search("scala"));
		
		System.out.println("Deleting golang");
		Trie.delete("golang");
		System.out.println("golang key exists: " + Trie.search("golang"));
	}
}
