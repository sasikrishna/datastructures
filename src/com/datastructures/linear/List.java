package com.datastructures.linear;

/**
 * 
 * @author Sasi on 03-May-2018, 8:15:47 pm
 *
 */
public interface List<T> {
	public T add(T t);
	public T get(int index) throws ArrayIndexOutOfBoundsException;
	public boolean remove(T t) throws Exception;
}
