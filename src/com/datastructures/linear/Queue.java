package com.datastructures.linear;

import java.util.Arrays;

/**
 * <p><code>Queue</code> class represents queue data structure implementation containing methods enqueue to 
 * add objects into queue, dequeue to get first added element (FIFO) and other util methods like isEmpty and size.</p>
 * 
 * @author Sasi on 06-May-2018, 12:51:12 PM
 */
public class Queue<T> {

	private Object[] queue;
	private int rear, front, size;
	
	public Queue(){
		queue = new Object[10];
		rear = -1;
		front = -1;
		size = 0;
	}
	
	public T enQueue(T t){
		
		//Checking if we reached to last in array
		if(front == (queue.length -1)){
			queue = Arrays.copyOf(queue, 2 * size);
		}
		
		queue[++front] = t;
		size++;
		return t;
	}
	
	public T deQueue(){
		
		if(rear == front || size == 0){
			return null;
		}
		
		T t =  (T) queue[++rear];
		queue[rear] = null;
		size--;
		
		//If rear is equals to front then queue has elements so reseting rear and front initial indexes
		if(rear == front){
			rear = -1;
			front = -1;
		}
		
		return t;
	}
	
	public int size(){
		return size;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(Arrays.toString(queue));
		return builder.toString();
	}

	public static void main(String[] args) {
		Queue<Integer> queue = new Queue<>();
		
		System.out.println("Queue size: " + queue.size());
		queue.enQueue(123);
		queue.enQueue(234);
		queue.enQueue(345);
		System.out.println(queue);
		
		System.out.println("Dequeued element: " + queue.deQueue());
		System.out.println(queue);
		
		System.out.println("Dequeued element: " + queue.deQueue());
		System.out.println(queue);
		
		System.out.println("Dequeued element: " + queue.deQueue());
		System.out.println(queue);
		
		System.out.println("Queue size: " + queue.size());
		queue.enQueue(678);
		System.out.println(queue);
	}
}
