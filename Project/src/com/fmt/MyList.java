package com.fmt;

public interface MyList<T> {
	
	public void add(T x);
	
	public void add(T x, int index);
	
	public void addElementToStart(T x);
	
	public T replace(T x, int index);
	
	public T remove(int index);
	
	public T get(int index);
	
	public int size();
	
}
