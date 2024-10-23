package algorithim;

import DataStructures.SingleLinkedList;

/**
 * This is a vertex class that has city and an adjacent list of vertices, it is
 * like a vertex node or box to hold the adjacent and location information
 */
public class Vertex implements Comparable<Vertex> {

	// city properties
	private Location location;
	private SingleLinkedList<Integer>adjacentList;

	public Vertex(Location location) {
		this.location = location;
		adjacentList = new SingleLinkedList<Integer>();
	}

	/**
	 * setters and getters
	 */
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public SingleLinkedList<Integer> getAdjacentList() {
		return adjacentList;
	}

	public void setAdjacentList(SingleLinkedList<Integer> adjacentList) {
		this.adjacentList = adjacentList;
	}

	@Override
	public String toString() {
		// return "Vertex [location=" + location + ", adjacentList=" + adjacentList
		// +"]";
		return location.toString() + adjacentList.toString();
	}

	@Override
	public int compareTo(Vertex o) {
		return this.location.compareTo(o.location);
	}

}
