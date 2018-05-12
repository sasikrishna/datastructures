package com.datastructures.nonlinear.ads;

import java.util.Arrays;

/**
 * <code>SegmentTree</code> class represents implementation of Segment tree data structure.
 * Below are use cases in which segment tree data structure is very help to achieve in good time complexity.
 * 
 *  <p>1. Finding minimum number between given two indices<br>
 *     2. Finding sum between given two indices. 
 *  </p>
 *  <p>We can achieve above requirements in O(n) time but with segment tree we can achieve in O(logn)</p>
 *  <p>This segment tree implementation is for minimum range query.</p>
 * 
 * @author Sasi on 11-May-2018, 12:00:03 am
 */
public class SegmentTree {
	
	private int treeArray[];   //Array contains constructed segment tree
	
	public SegmentTree(){
	}
	
	/**
	 *  constructTree is a initial point to start constructing segment trees
	 *  
	 * @param array
	 */
	public void constructTree(int[] array){
		treeArray = new int[2 * array.length - 1];
		constructTree(array, 0, array.length-1, 0);
	}
	
	/**
	 * constructTree is a internal method to create segment tree for given input array. Segment tree can be constructed with array data structure.<br>
	 * If there are n elements in given array then there going to 2*n-1 children in the tree. Child nodes can found in array with below formulas.<br>
	 * Left child node: 2*n+1<br>
	 * Right child node: 2*n+2<br>
	 * Parent node: (i-1)/2<br>
	 * 
	 * This method recursively calls itself to find out the value of child node and create them.<br>
	 * 
	 * <p>Because this is a range minimum query segment tree, we'll calculate minimum between two child nodes and make it as parent node.</p>
	 * <p>If they are n elements in </p>
	 * 
	 * @param array
	 * @param low
	 * @param high
	 * @param position
	 * @return
	 */
	private int constructTree(int[] array, int low, int high, int position){
		if(low == high){
			treeArray[position] = array[low]; 
			return array[low];
		}
		
		int mid = (low + high)/2;
		int leftChild = constructTree(array, low, mid, 2 * position +1);
		int rightChild = constructTree(array, mid+1, high, 2 * position +2);
		
		treeArray[position] = Math.min(leftChild, rightChild);
		return treeArray[position];
	}
	
	/**
	 * findMinimum method returns minimum value between given two indices. findMinimum uses constructed segment tree to find out the minimum.<br>
	 * We'll start at root node of tree and traverse from there based on given indices to find out the minimum. Below are the methods to find out how to traverse.<br>
	 * 
	 *  1. Traverse both left & right subtree if rootNode indices partially overlapping with query indices.<br>
	 *  2. Return some max integer value if root node indices are totally away from query indices<br>
	 *  3. Return current root node value if root node indices are overlapped with query indices<br>
	 *  
	 * @param rootPosition
	 * @param rootLow
	 * @param rootHigh
	 * @param qLow
	 * @param qHigh
	 * @return
	 */
	public int findMinimum(int rootPosition, int rootLow, int rootHigh, 
			int qLow, int qHigh){
		
		if(rootLow >= qLow && rootHigh <= qHigh){
			return treeArray[rootPosition];
		}else if(qLow > rootHigh || qHigh < rootLow){
			return Integer.MAX_VALUE;
		}else{
			int rootMid = (rootLow + rootHigh)/2;
			return Math.min(findMinimum(2 * rootPosition + 1, rootLow, rootMid, qLow, qHigh),
					findMinimum(2 * rootPosition + 2, rootMid + 1, rootHigh, qLow, qHigh));
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(Arrays.toString(treeArray));
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		int[] array = new int[]{-1, 0, -2, 6, -3};
		SegmentTree tree = new SegmentTree();
		tree.constructTree(array);
		
		System.out.println("Input array: " + Arrays.toString(array));
		System.out.println("Constructed segment tree: " + tree);
		
		System.out.println("Minimum between index 2 and 4: " + tree.findMinimum(0, 0, array.length-1, 2, 4));
		System.out.println("Minimum between index 1 and 3: " + tree.findMinimum(0, 0, array.length-1, 1, 3));
	}
}
