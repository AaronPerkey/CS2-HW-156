package unl.cse;

import java.util.LinkedList;

public class Queue<T> {

	private final LinkedList<T> list;
	
	public Queue() {
		this.list = new LinkedList<T>();
	}
	
	public T dequeue() {
		return this.list.remove(this.list.size()-1);
	}
	
	public void enqueue(T x) {
		this.list.add(0, x);
	}

	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return this.list.size() == 0;
	}
	
}
