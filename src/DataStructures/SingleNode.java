package DataStructures;

/**
 * The class for the single nodes used in creating the SingleLInkedList
 * 
 * @author Sarah Hassouneh
 *
 * @param <T> to specifiy the type of object stored in the Single Node
 */
public class SingleNode<T> {

	/**
	 * properties for the single node, the single node specified here takes data of
	 * a generic type T. The data inside node is made private for more security.
	 */
	private T element;
	SingleNode<T> next;

	/**
	 * default constructor
	 */
	public SingleNode() {

	}

	/**
	 * constructor with the element
	 * 
	 * @param element: element in node
	 */
	public SingleNode(T element) {
		this.element = element;
	}

	// setters and getters
	
	/**
	 * gets the element stored in node
	 * 
	 * @return the element stored in the node
	 */
	public T getElement() {
		return element;
	}

	/**
	 * change the element in the node
	 * 
	 * @param element: to be set in the node
	 */
	public void setElement(T element) {
		this.element = element;
	}
	
	
	public SingleNode<T> getNext() {
		return next;
	}
	
	public void setNext(SingleNode<T> next) {
		this.next = next;
	}

	

	

}
