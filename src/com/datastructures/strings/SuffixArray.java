package com.datastructures.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <code>SuffixArray</code> class is implementation for SuffixArray data structure. Suffix Array is very useful for pattern search in given string.<br>
 * First we'll find all suffixes for the given text and store them in a list along with its starting position in initial text.<br>
 * Now we'll sort the list result it suffix array for given text.<br>
 * Time complexity for building suffix array is O(n * nLogn) - first n is for generating suffixes and nLogn is for sorting them.<br> 
 * 
 * To find the pattern in SuffixArray we'll use binary search technique. So time complexity for finding pattern takes O(mlogn) where m is length of pattern.<br>
 * 
 * @author Sasi on 19-May-2018, 10:16:21 pm
 */
public class SuffixArray {
	
	private static List<SuffixArrayNode> suffixes;
	private static Integer[] suffixArray;
	
	public static void constructSuffixArray(String text){
		suffixes = new ArrayList<SuffixArrayNode>();
		suffixArray = new Integer[text.length()];
		constructSuffixes(text, 0);
		prepareSuffixArray(suffixes);
		
		System.out.println("Suffix Array: " + Arrays.toString(suffixArray));
	}
	
	/**
	 * constructSuffixes method adds all suffixes for a given text. For e.g input text is 'test' then suffixes will contain<br>
	 * [test, est, st, t].
	 * 
	 * @param word
	 * @param startIndex
	 */
	private static void constructSuffixes(String text, int startIndex){
		
		if(startIndex == text.length()){
			return;
		}
		
		suffixes.add(new SuffixArrayNode(text.substring(startIndex, text.length()), startIndex));
		constructSuffixes(text, ++startIndex);
	}
	
	/**
	 * search performs binary search kind of operation on suffixArray.<br>
	 * If lexicographical diff between pattern and suffix string at middle is 0 then pattern found.<br>
	 * Otherwise if greater than 0 we need to look into second half of array else we'll check in first half. 
	 * 
	 * @param pattern
	 * @returns position of pattern in given test. Returns -1 if not found.  
	 */
	public static int search(String pattern){
		int left = 0, right = suffixes.size()-1;
		
		while(left <= right){
			int mid = left + (right-left)/2;
			
			SuffixArrayNode node = suffixes.get(suffixArray[mid]);
			String subString =  node.subString;
			
			if(subString.startsWith(pattern)){
				return node.position;
			}
			
			int compareToValue = pattern.compareTo(subString);
			if(compareToValue == pattern.length()){
				return suffixes.get(mid).position;
			}else if(compareToValue > 0){
				left = mid+1;
			}else{
				right = mid-1;
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		SuffixArray.constructSuffixArray("stringtosearch");
		int position = SuffixArray.search("arc");
		System.out.println(position == -1 ? "Pattern arc not found." : "Pattern arc found at position " + position);
	}
	
	/**
	 * prepareSuffixArray method sorts given array of suffixes lexicographically and stores sorted suffixes <br>
	 * positions into an array. For e.g suffixes list is [test, est, st, t] then suffixArray going to be [1, 2, 3, 0]
	 * 
	 * @param suffixes
	 */
	public static void prepareSuffixArray(List<SuffixArrayNode> suffixes){
		
		List<SuffixArrayNode> temp = new ArrayList<SuffixArrayNode>(suffixes);
		Collections.sort(temp, new Comparator<SuffixArrayNode>() {
			@Override
			public int compare(SuffixArrayNode o1, SuffixArrayNode o2) {
				return o1.subString.compareTo(o2.subString);
			}
		});
		
		int i=0;
		for(SuffixArrayNode node : temp){
			suffixArray[i++] = node.position;
		}
	}
}

/**
 * <code>SuffixArrayNode</code> represents each node in list of suffixes. Each node contains a substring from given text and its starting position.
 * 
 * @author Sasi
 */
class SuffixArrayNode{
	String subString;
	int position;

	public SuffixArrayNode(String subString, int position) {
		super();
		this.subString = subString;
		this.position = position;
	}

	@Override
	public String toString() {
		return subString + " " + position+ "\r\n";
	}
}
