package com.fmt;

import java.util.Comparator;

public class MyLinkedList<T> implements MyList<T> {

	public int size;
	private Node<T> head;
	private final Comparator<T> comparator;
	

	public MyLinkedList(Comparator<T> comparator) {
		this.size = 0;
		this.comparator = comparator;
		head = null;
	}

	private Node<T> getNode(int index) {
		Node<T> current = this.head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current;
	}

	@Override
	public void add(T x) {

//		Node<T> prev = this.getNodeAtIndex(this.size-1);
		
		Node<T> newTail = new Node<T>(x);
		if (this.size == 0) {
			this.head = newTail;
			this.size++;
			return;
		}

		Node<T> tail = this.getNode(this.size - 1);
		tail.setNext(newTail);
		this.size++;
		//this.comparator.compare(curr.getValue(), item) > 0
		for (int i = 1; i < this.size(); i++) {
			T y = this.get(i);
			int j = i - 1;
			while (j >= 0 && (this.comparator.compare(get(j), y)) >= 1) {
				this.replace(this.get(j), j + 1);
				j--;
			}
			this.replace(y, j + 1);
		}
		
		
		return;
	}

	@Override
	public void addElementToStart(T x) {
		Node<T> newHead = new Node<T>(x);
		newHead.setNext(this.head);
		this.head = newHead;
		this.size++;

	}

	@Override
	public T replace(T x, int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("Invalid index: " + index + "for size " + this.size);
		} else if (index == 0) {
			T y = head.getElement();
			Node<T> newNode = new Node<>(x);
			newNode.setNext(this.head.getNext());
			this.head = newNode;
			return y;
		} else {
		
		
			Node<T> prev = this.getNode(index-1);
			Node<T> curr = prev.getNext();
			Node<T> newNode = new Node<>(x);
			prev.setNext(newNode);
			newNode.setNext(curr.getNext());
			return curr.getElement();
		}
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("Invalid index: " + index + "for size " + this.size);
		} else if (index == 0) {
			T y = head.getElement();
			this.head = this.head.getNext();
			return y;
		} else {
		
		
			Node<T> prev = this.getNode(index-1);
			Node<T> curr = prev.getNext();
			Node<T> next = curr.getNext();
			prev.setNext(next);
			return curr.getElement();
		}
	}
	
	@Override
	public void add(T x, int index) {
		if (index < 0 || index > this.size) {
			throw new IllegalArgumentException("Invalid index: " + index + "for size " + this.size);
		} else if (index == 0) {
			this.addElementToStart(x);
		} else {
		
			Node<T> prev = this.getNode(index-1);
			Node<T> newNode = new Node<>(x);
			Node<T> next = prev.getNext();
			prev.setNext(newNode);
			newNode.setNext(next);
		}

	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("Invalid index: " + index + "for size " + this.size);
		} else {
			return this.getNode(index).getElement();
		}
	}

	@Override
	public int size() {
		return this.size;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");

		Node<T> current = this.head;
		for (int i = 0; i < this.size - 1; i++) {
			sb.append(current.getElement() + ", ");
			current = current.getNext();
		}
		sb.append(current.getElement());
		current = current.getNext();
		sb.append(" ]");
		return sb.toString();
	}

}
