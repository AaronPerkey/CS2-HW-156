package unl.cse.trucks;

/**
 * A linked list implementation for <code>Truck</code> instances.
 *
 */
public class TruckList {
	
	private int size;
	private TruckListNode head;

	public TruckList() {
		this.size = 0;
		this.head = null;
	}
	
	/**
	 * This function returns the size of the list, the number of elements currently
	 * stored in it.
	 * 
	 * @return
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * This function clears out the contents of the list, making it an empty list.
	 */
	public void clear() {
		
		this.head = null;
		this.size = 0;
		
	}

	/**
	 * This method adds the given {@link Truck} instance to the beginning of the
	 * list.
	 * 
	 * @param t
	 */
	public void addToStart(Truck t) {
		
		TruckListNode newHead = new TruckListNode(t);
		newHead.setNext(this.head);
		this.head = newHead;
		this.size++;
	}

	/**
	 * This method adds the given {@link Truck} instance to the end of the list.
	 * 
	 * @param t
	 */
	public void addToEnd(Truck t) {
		TruckListNode newTail = new TruckListNode(t);
		
		if(this.size == 0) {
			this.head = newTail;
			this.size++;
			return;
		}
		
		TruckListNode tail = this.getTruckListNode(this.size - 1);
		tail.setNext(newTail);
		this.size++;
		return;
	}

	/**
	 * This method removes the {@link Truck} from the given <code>position</code>,
	 * indices start at 0. Implicitly, the remaining elements' indices are reduced.
	 * 
	 * @param position
	 */
	public void remove(int position) {
		if(position >= this.size || position < 0) {
			throw new IndexOutOfBoundsException ("Invalid index: " + position);
		}else if(position == 0) {
			this.head = this.head.getNext();
			this.size--;
		} else {
		TruckListNode before = this.getTruckListNode(position - 1);
		TruckListNode current = before.getNext();
		TruckListNode after = current.getNext();
		before.setNext(after);
		this.size--;
		}
	}

	/**
	 * This is a private helper method that returns a {@link TruckListNode}
	 * corresponding to the given position. Implementing this method is optional but
	 * may help you with other methods.
	 * 
	 * @param position
	 * @return
	 */
	private TruckListNode getTruckListNode(int position) {
		if(position >= this.size || position < -this.size) {
			throw new IndexOutOfBoundsException ("Invalid index: " + position);
		}
		TruckListNode current = this.head;
		
		for(int i = 0; i < position; i++) {
			current = current.getNext();
		}
		return current;
	}

	/**
	 * Returns the {@link Truck} element stored at the given <code>position</code>.
	 * 
	 * @param position
	 * @return
	 */
	public Truck getTruck(int position) {
		 Truck current = getTruckListNode(position).getTruck();
		
		
		return current;
	}
	/**
	 * Prints this list to the standard output.
	 */
	public void print() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		TruckListNode current = this.head;
		while(current != null) {
			sb.append(current.getTruck());
			current = current.getNext();
		}
		sb.append(" ]");
		System.out.println(sb.toString());
	}

}
