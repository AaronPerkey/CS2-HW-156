package unl.cse;

import java.util.LinkedList;

public class Stack<T> {
	
	private int maxCapacity = 0;
	private final LinkedList<T> list;
	
	public Stack() {
		list = new LinkedList<>();
	}

	
	public Stack(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		list = new LinkedList<>();
	}
	
	public T pop() {
		if (this.isEmpty()) {
			// return null;
			throw new IllegalStateException("You cannot pop from an empty stack!");
		}
		return this.list.remove(this.list.size() - 1);
	}
	
	public void push(T x) {
		// if we don't wanna support nulls:
		if (x == null) {
			// 1. NOOP = No Operation
			// return;
			throw new IllegalArgumentException("this stack does not support nulls");
		} else if (this.isFull()) {
			throw new IllegalArgumentException("this stack is full");
		}
		this.list.add(x);
	}

	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return this.list.size() == 0;
	}

	public boolean isFull() {
		return (this.maxCapacity != 0 && this.list.size() == this.maxCapacity);
	}
	
}
