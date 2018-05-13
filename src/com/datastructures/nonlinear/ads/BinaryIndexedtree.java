package com.datastructures.nonlinear.ads;

import java.util.Arrays;

/**
 * 
 * @author Sasi on 13-May-2018, 9:58:37 pm
 *
 */
public class BinaryIndexedtree {
	
	private int[] tree;
	public BinaryIndexedtree(int[] array){
		tree = new int[array.length+1];
		tree[0] = 0;
		
		for(int i=1; i<=array.length; i++){
			constructTree(i, array[i-1]);
		}
	}
	
	private void constructTree(int index, int value){
		
		if(index >= tree.length){
			return;
		}
		
		tree[index] += value;
		constructTree(getNext(index), value);
	}

	public int getSum(int endIndex){
		++endIndex;
		
		if(endIndex >= tree.length){
			endIndex = tree.length-1;
		}
		
		int sum = 0;
		while(endIndex > 0){
			sum += tree[endIndex];
			endIndex = getParent(endIndex);
		}
		return sum;
	}
	
	public int getParent(int index){
		return index - (index & -index);
	}
	
	public int getNext(int index){
		return index + (index & -index);
	}
	
	
	@Override
	public String toString() {
		return "[" + Arrays.toString(tree) + "]";
	}

	public static void main(String[] args) {
		BinaryIndexedtree tree = new BinaryIndexedtree(new int[]{1,2,3,4,5});
		System.out.println(tree);
		System.out.println("Sum between 0 and 3: " + tree.getSum(3));
		System.out.println("Sum between 0 and 2: " + tree.getSum(2));
		System.out.println("Sum between 0 and 4: " + tree.getSum(4));
	}
}
