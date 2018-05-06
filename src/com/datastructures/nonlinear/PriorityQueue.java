package com.datastructures.nonlinear;

import java.util.Arrays;

/**
 * 
 * @author Sasi on 06-May-2018, 11:36:04 pm
 */
public class PriorityQueue {
	
	int[] array;
	int count;
	
	public PriorityQueue(){
		array = new int[10];
		count = 0;
	}
	
	public int insert(int value){
		//Checking if array is reached to max length
		if(count == array.length){
			resizeHeap();
		}
		
		int index = this.count++;
		this.array[index] = value;
		percolateUp(index);
		return value;
	}
	
	private void resizeHeap() {
		this.array = Arrays.copyOf(this.array, this.array.length * 2);
	}

	public int deleteMax() throws Exception{
		if(this.count == 0)
			throw new Exception("Queue is empty");
		
		int max = getMax();
		
		this.array[0] = this.array[this.count--];
		percolateDown(0);
		
		return max;
	}

	private void percolateUp(int index){
		int parentIndex = getParentIndex(index);
		
		if(index >= 0 && parentIndex != -1 && (array[index] > array[parentIndex])){
			int temp = array[parentIndex];
			array[parentIndex] = array[index];
			array[index] = temp;
			
			percolateUp(parentIndex);
		}
	}

	private void percolateDown(int index) {
		
		if(index == this.count)
			return;
		
		int leftIndex = getLeftChildIndex(index), 
				rightIndex = getRightChildIndex(index), maxIndex;
		
		if(leftIndex != -1 && this.array[index] < this.array[leftIndex]){
			maxIndex = leftIndex;
		}else{
			maxIndex = index;
		}
		
		if(rightIndex != -1 && this.array[maxIndex] < this.array[rightIndex]){
			maxIndex = rightIndex;
		}
		
		if(maxIndex != index){
			//Swap elements at index and maxIndex
			int temp = this.array[index];
			this.array[index] = this.array[maxIndex];
			this.array[maxIndex] = temp;
			percolateDown(maxIndex);
		}
	}

	public int getMax() throws Exception{
		
		if(this.count == 0)
			throw new Exception("Queue is empty");
		
		return this.array[0]; 
	}
	
	private int getLeftChildIndex(int parentIndex){
		
		if(parentIndex <= 0){
			return -1;
		}
		
		return (2 * parentIndex + 1);
	}
	
	private int getRightChildIndex(int parentIndex){
		
		if(parentIndex <= 0){
			return -1;
		}
		
		return (2 * parentIndex + 2);
	}
	
	private int getParentIndex(int childIndex){
		
		if(childIndex <= 0)
			return -1;
		
		return (childIndex-1)/2;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(Arrays.toString(array));
		return builder.toString();
	}

	public static void main(String[] args) throws Exception {
		PriorityQueue queue = new PriorityQueue();
		queue.insert(10);
		queue.insert(20);
		queue.insert(5);
		queue.insert(30);
		queue.insert(8);
		queue.insert(60);
		queue.insert(1);
		System.out.println(queue);
		System.out.println("Max element: " + queue.getMax());
		
		System.out.println("Deleting max element: " + queue.deleteMax());
		System.out.println(queue);
	}
}
