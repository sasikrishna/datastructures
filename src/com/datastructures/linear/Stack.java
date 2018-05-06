package com.datastructures.linear;

import java.util.Arrays;

/**
 * <p>
 * 	<code>Stack</code> class represents last in first out data structure implementation.
 *  Backed by array data structure for storing object, <code>Stack</code> provides couple of methods listed below.
 *  <br>
 *  Push operation is insert elements into stack, pop is to remove top element return that element and peek is just to see the top element.
 *  Stack also provides size and isEmpty util methods to check size of stack and to see if stack is empty or not. 
 * </p>  
 * <p>
 * 	When <code>Stack</code> is initialized default array size 10.
 *  If it reached max length then we are doubling the array size to accomadate new insertions.
 * </p>
 * @author Sasi on 06-May-2018, 11:10:43 am
 */
public class Stack<T> {
	
	//Base object to store all values inserted into stack
	private Object[] stack; 
	
	//Top represents index of recently added element
	private int top;
	
	private int size;
	
	@SuppressWarnings("unchecked")
	public Stack(){
		stack =   new Object[10];
		top = -1;
		size = 0;
	}
	
	/**
	 * 
	 * @param t
	 * @return
	 */
	public T push(T t){
		
		//Checking if array length reached to max
		if(top == stack.length-1){
			//Doubling the array size to accomadate new elements
			stack = Arrays.copyOf(stack, 2 * size());
		}
		
		size++;
		top++;
		stack[top] = t;
		return t;
	}
	
	public T pop(){
		T t = (T) stack[top];
		
		if(top == -1)
		{
			return null;
		}
		
		stack[top] = null;
		top--;
		size--;
		return t;
	}
	
	public T peek(){
		return (T) stack[top];
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
		builder.append(Arrays.toString(stack));
		return builder.toString();
	} 
	
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<>();
		System.out.println("Size of stack: " + stack.size());
		stack.push(12);
		stack.push(13);
		stack.push(14);
		stack.push(15);
		stack.push(16);
		stack.push(17);
		stack.push(18);
		stack.push(19);
		stack.push(20);
		stack.push(21);
		stack.push(22);
		
		System.out.println(stack);
		System.out.println("Size of stack: " + stack.size());
		System.out.println("Top element: " + stack.peek());
		
		stack.push(23);
		stack.push(24);
		System.out.println("Top element: " + stack.peek());
		
		System.out.println("Element pop: " + stack.pop());
		System.out.println(stack);
		System.out.println("Top element: " + stack.peek());
		System.out.println("Size of stack: " + stack.size());
	}
}
