package DataStructures;

import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for a Single Sorted LinkedList
 * 
 * @author Sarah Hassouneh
 *
 * @param <T> refers to a generic Type, the generic type specified here must be
 *            comparable (implements the comparable interface) to allow us to
 *            sort the list. The generic type also allows us to have a list of
 *            specified matching objects in the same list.
 * 
 */
public class SingleLinkedList<T> implements Iterable<T>  {

	/**
	 * definition of the first node , last and count
	 */

	private SingleNode<T> first;
	private SingleNode<T> last;
	// count represents size of list
	private int count;

	/**
	 * default constructor of the SingleLinkedList
	 */
	public SingleLinkedList() {

	}

	// getters for properties

	/**
	 * method to get current size of list a getter for the count
	 * 
	 * @return the current number of elements in the list
	 */
	public int size() {
		return count;
		// note: no setter for count
	}

	/**
	 * method that gets the first node in the list
	 * 
	 * @return first
	 */
	public SingleNode<T> getFirstNode() {
		return first;
	}

	/**
	 * method that get the last node in the list
	 * 
	 * @return last
	 */
	public SingleNode<T> getLastNode() {
		return last;
	}

	/**
	 * get the first element stored in the list,note that the element returned is of
	 * a generic type T
	 * 
	 * @return the first element in the linked list
	 */
	public T getFirst() {
		if (count == 0)
			return null;
		else
			return first.getElement();
	}

	/**
	 * get the last element stored in the list,note that the element returned is of
	 * a generic type T
	 * 
	 * @return the last element in the list
	 */
	public T getLast() {
		if (count == 0)
			return null;
		else
			return last.getElement();
	}

	/**
	 * adds the element x at the start of the list.
	 * 
	 * @param x : the element to be added
	 */
	public void addFirst(T x) {
		if (count == 0)
			first = last = new SingleNode<T>(x);
		else {
			SingleNode<T> temp = new SingleNode<T>(x);
			temp.next = first;
			first = temp;
		}
		count++;
	}

	/**
	 * add a specific node at the start of the list, it takes constant time since
	 * the position of first is known
	 * 
	 * @param x
	 */
	public void addFirst(SingleNode<T> x) {

		if (count == 0)
			first = last = x;
		else {
			x.next = first;
			first = x;

		}

		count++;

	}

	
	/**
	 * adds the element x at the end of the list, it takes constant time since the
	 * position of last is known
	 * 
	 * @param x : the element to be added
	 */
	public void add(T x) {
		
		addLast(x);
		
		
	}

	
	
	/**
	 * adds the element x at the end of the list, it takes constant time since the
	 * position of last is known
	 * 
	 * @param x : the element to be added
	 */
	public void addLast(T x) {
		if (count == 0)
			first = last = new SingleNode<T>(x);
		else {
			SingleNode<T> temp = new SingleNode<T>(x);
			last.next = temp;
			last = temp;
		}
		count++;
	}

	/**
	 * adds a specific node at the end of the list,it takes constant time since the
	 * position of last is known
	 * 
	 * @param x
	 */
	public void addLast(SingleNode<T> x) {
		if (count == 0)
			first = last = x;
		else {
			last.next = x;
			last = x;
		}
		count++;
	}

	/**
	 * adds the element x in the specified position at index.
	 * 
	 * @param x:     element to be added
	 * @param index: index where the element to be added, the index/counting starts
	 *               at 0
	 */
	public void addAt(T x, int index) {
		if (index == 0)
			addFirst(x);
		else if (index >= count - 1)
			addLast(x);
		else {
			SingleNode<T> temp = new SingleNode<T>(x);

			SingleNode<T> current = first;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}

			temp.next = current.next;
			current.next = temp;
			count++;

		}
	}

	/**
	 * a methods that add a new node after a specific node, the current here
	 * represents the position to be added. The function takes constant time
	 * 
	 * @param newNode: node to add
	 * @param current: node to add after
	 */
	public void addNode(SingleNode<T> newNode, SingleNode<T> current) {

		if (count == 0)
			addFirst(newNode);
		if (current == last) {
			// add after last node
			addLast(newNode);

		} else {
			newNode.next = current.next;
			current.next = newNode;
			count++;
		}

	}

	/**
	 * this methods adds the object in order in this ordered LinkedList since T must
	 * implement comparable, we can use the compareTo() method to compare between
	 * the elements in the nodes and add the specific element x in the correct
	 * position.The method adds the object in ascending order.If two object are
	 * equal the new object is inserted after the previously added equal object.
	 * 
	 * @param x: the object to be added to the list
	 * @return the added node in the list
	 */
	public SingleNode<T> insertOrder(T x) {

		if (first == null || (((Comparable) first.getElement()).compareTo(x) > 0)) {
			addFirst(x);
			return first;
		} else {

			SingleNode<T> previous = first;
			SingleNode<T> current = first.next;

			while ((current != null) && ((((Comparable) current.getElement()).compareTo(x) < 0))) {
				previous = current;
				current = current.next;
			}

			// current may be null after the loop, this means the element should be added at
			// last
			if (current == null) {
				addLast(x);
				return last;
			}

			SingleNode<T> temp = new SingleNode<T>(x);
			temp.next = current;
			previous.next = temp;
			count++;
			return temp;
		}

	}

	/////

	public SingleNode<T> insertUniqueOrder(T x) {

		if (first == null || (((Comparable) first.getElement()).compareTo(x) > 0)) {
			addFirst(x);
			return first;
		} else if (first.getElement().equals(x))
			return null;

		else {

			SingleNode<T> previous = first;
			SingleNode<T> current = first.next;

			while ((current != null) && ((((Comparable) current.getElement()).compareTo(x) < 0))) {

				previous = current;
				current = current.next;
			}

			// current may be null after the loop, this means the element should be added at
			// last
			if (current == null) {
				addLast(x);
				return last;
			}

			if (current.getElement().equals(x))
				return null;
			else {

				SingleNode<T> temp = new SingleNode<T>(x);
				temp.next = current;
				previous.next = temp;
				count++;
				return temp;
			}
		}

	}

	/**
	 * This methods adds a specific node in order in the list. The comparison is
	 * done based on the stored element inside the list.It returns the inserted
	 * node.
	 * 
	 * @param temp: node to be inserted in order
	 * @return the newly inserted node -helpful when specifying the current node
	 */
	public SingleNode<T> insertOrder(SingleNode<T> temp) {

		if (first == null || (((Comparable) first.getElement()).compareTo(temp.getElement()) > 0)) {
			addFirst(temp);
			return first;
		} else {

			SingleNode<T> previous = first;
			SingleNode<T> current = first.next;

			while ((current != null) && ((((Comparable) current.getElement()).compareTo(temp.getElement()) < 0))) {
				previous = current;
				current = current.next;
			}

			// current may be null after the loop, this means the element is added at last
			if (current == null) {
				addLast(temp);
				return last;
			}

			temp.next = current;
			previous.next = temp;
			count++;
			return temp;
		}

	}

	/**
	 * this method loops over the list to find the position of Object x, it takes
	 * O(n)
	 * 
	 * @param x : object to look for
	 * @return the Node where the object is found else it return null, meaning it is
	 *         not found
	 */
	public SingleNode<T> find(T x) {
		SingleNode<T> current = first;
		for (int i = 0; i < count; i++) {
			if (current.getElement().equals(x))
				return current;
			current = current.next;
		}
		return null;

	}

	/**
	 * The method finds the previous node of a specific node, it takes time Big O(n)
	 * 
	 * @param node: the method whose previous we are looking for
	 * @return the previous node that was found, and null if no previous exists
	 */
	public SingleNode<T> findPreviousNode(SingleNode<T> node) {

		if (node == first) {
			// m is the first node, so there is no previous node
			return null;
		}

		SingleNode<T> current = first;
		while (current != null && current.next != node) {
			current = current.next;

		}
		// current here can become null
		return current;
	}

	/**
	 * the method reorders a given node in the list, this assumes the node is in the
	 * list. The use of this method would be necessary when we update the element in
	 * the node. It takes time big O(n)
	 * 
	 * @param node : node to be reordered in the list
	 */
	public void reorder(SingleNode<T> node) {
		SingleNode<T> tmp = node;
		if (removeNode(tmp) == true) {
			insertOrder(tmp);
		}

	}

	/**
	 * this methods remove the first element in the list
	 * 
	 * @return true if the first object in the list is successfully removed
	 */
	public boolean removeFirst() {
		if (count == 0)
			return false;
		else {
			if (count == 1) {
				first = last = null;
			} else {
				/*
				 * this step (temp which represent the node to be removed) is to ensure that the
				 * node is completely removed and not attached to the list by any means, even
				 * from outside--> this step could sometimes be ignored as in java the garbage
				 * collector can do the job, however if the removed node is used from outside or
				 * accessed it would still have a link with the list.So this step would
				 * explicitly remove the link from the list, to avoid any potential dangling
				 * references.
				 */
				SingleNode<T> temp = first;
				first = first.next;
				temp.next = null;
			}

			count--;
			return true;
		}

	}

	/**
	 * this methods remove the last element in the list
	 * 
	 * @return true if the last object in the list is successfully removed
	 */
	public boolean removeLast() {
		if (count == 0)
			return false;
		else {
			if (count == 1) {
				first = last = null;
			} else {
				SingleNode<T> current = first;
				for (int i = 0; i < count - 2; i++) {
					current = current.next;
				}
				current.next = null;
				last = current;
			}

			count--;
			return true;
		}

	}

	/**
	 * remove the object at the specified index
	 * 
	 * @param index : index where the object to be removed, index/counting starts at
	 *              0, so if 0 it removes first element
	 * @return true if the object is successfully removed
	 */
	public boolean removeAt(int index) {

		if (count == 0)
			return false;
		else {

			if (index == 0)
				return removeFirst();
			else if (index == count - 1)
				return removeLast();
			else if (index < 0 || index >= count)
				return false;
			else {

				SingleNode<T> current = first;
				// current represent the directly previous node of the node to be removed
				for (int i = 0; i < index - 1; i++) {
					current = current.next;
				}

				// see above note on temp(represent the node to be removed)
				SingleNode<T> temp = current.next;
				current.next = current.next.next;
				temp.next = null;

				count--;
				return true;

			}

		}

	}

	/**
	 * removes object from the list,if there are duplicates of the object, in other
	 * words two objects are equal it removes the first occurrence
	 * 
	 * @param x: the object to be removed
	 * @return true if the object is found and successfully removed
	 */
	public boolean remove(T x) {

		if (count == 0)
			return false;
		else {

			if (first.getElement().equals(x)) {
				return removeFirst();
			} else {
				SingleNode<T> previous = first;
				SingleNode<T> current = first.next;

				while (current != null && !(current.getElement().equals(x))) {
					previous = current;
					current = current.next;
				}

				if (current == last)
					return removeLast();

				/*
				 * current represent the node to be removed, if current equals null this means
				 * that the loop reached the end of the list without finding a match
				 */
				if (current != null) {
					previous.next = current.next;
					current.next = null;
					count--;
					return true;
				} else {
					return false;
				}

			}

		}
	}

	/**
	 * this method removes a specific node from the list, the node is assumed to
	 * have a known location in the list.
	 * 
	 * @param node : node to be removed
	 * @return true if successfully removed and false otherwise
	 */
	public boolean removeNode(SingleNode<T> node) {

		// SingleNode<T> tmp= node;

		if (count == 0) {
			return false;
		} else if (node == first) {
			return removeFirst();

		} else if (node == last) {
			return removeLast();

		} else {
			// find the position of the node
			// stop at the previous node of the node to be removed
			SingleNode<T> previous = first;
			while (previous != null && previous.next != node) {
				previous = previous.next;
			}

			// this means it is found
			if (previous != null) {
				previous.next = node.next;
				node.next = null;
				count--;
				return true;
			} else {
				// not found
				System.out.println("false here");
				return false;
			}
		}

	}

	/**
	 * searches for a specific object
	 * 
	 * @param x: the object to search for
	 * @return true if the object is found in the list
	 */
	public boolean contains(T x) {

		SingleNode<T> current = first;

		while (current != null) {
			if (current.getElement().equals(x)) {
				return true;
			}

			current = current.next;
		}

		return false;

	}

	/**
	 * check if the list is empty
	 * 
	 * @return true if empty
	 */
	public boolean isEmpty() {
		return count == 0;
	}

	/**
	 * a method to merge a given list with the current list
	 * 
	 * @param list2: list to be merged with the current list
	 */
	public void mergeLists(SingleLinkedList<T> list2) {

		if (list2 == null || list2.isEmpty())
			return;

		SingleNode<T> current2 = list2.getFirstNode();

		if (this.isEmpty()) {
			this.first = list2.first;
			this.count = list2.count;
			list2.first = null;
			return;

		}

		// for current list
		SingleNode<T> previous = first;
		SingleNode<T> current1 = first.next;

		while ((((Comparable) first.getElement()).compareTo(current2.getElement()) > 0)) {
			SingleNode<T> tmp = current2.next;
			addFirst(current2);
			current2 = tmp;
		}

		while (current1 != null && current2 != null) {

			if ((((Comparable) current1.getElement()).compareTo(current2.getElement()) > 0)) {

				SingleNode<T> tmp = current2.next;

				current2.next = current1;
				previous.next = current2;
				previous = previous.next;
				count++;

				current2 = tmp;
			} else {

				previous = current1;
				current1 = current1.next;
			}

		}

		while (current2 != null) {

			SingleNode<T> tmp = current2.next;
			addLast(current2);
			current2 = tmp;
		}

		list2.first = null;

	}

	/**
	 * a method to attach a given list with the current list
	 * 
	 * @param list2: list to be attached with the current list
	 */
	public void attachLists(SingleLinkedList<T> list2) {

		if (list2 == null || list2.isEmpty())
			return;

		//System.out.println(last);
		/*SingleNode<T> current2 = list2.getFirstNode();		
		while(current2!=null) {
			this.addLast(current2);
			current2=current2.next;
		}
		*/
		
		if (this.isEmpty()) {
			//System.out.println("entered here");
			first=last=list2.first;
		}else 
			this.last.next = list2.first;
		
		
        last = list2.last;
		this.count = this.count + list2.count;
		list2.first=null;

	}

	/**
	 * return the data stored in the list
	 */
	@Override
	public String toString() {
		String data = "";
		SingleNode<T> current = first;

		for (int i = 0; i < count; i++) {
			data += current.getElement() + "\t";
			current = current.next;
		}
		return data;
	}

	/**
	 * a method to directly to print the list
	 */
	public void printList() {
		System.out.println(count);

		SingleNode<T> current = first;
		for (int i = 0; i < count && current != null; i++) {
			System.out.println(current.getElement());
			current = current.next;
		}
	
	}

	public ObservableList<T> toObservable() {
		SingleNode<T> current = first;
		ObservableList<T> result = FXCollections.observableArrayList();
		while (current != null) {
			result.add(current.getElement());
			current = current.getNext();
		}

		return result;
	}

	
	
	/**
	 * implementation of the iterator as part of the Iterable interface.
	 */
	@Override
	public Iterator<T> iterator() {
		return new SingleLinkedListIterator();

	}

	private class SingleLinkedListIterator implements Iterator<T> {

		private SingleNode<T> current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if (hasNext()) {
				T data = current.getElement();
				current = current.next;
				return data;
			}
			return null;

		}

	}
}
