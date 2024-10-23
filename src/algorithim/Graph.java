package algorithim;

import java.util.PriorityQueue;

import DataStructures.Heap;
import DataStructures.SingleLinkedList;
import DataStructures.SortObjects;

public class Graph {

	private int numofVertices;
	private int current;

	private GeneralPair<Integer,String> [] indices;
	
	// an array to represent vertex (has location info and adjacent lists)
	private Vertex[] vertices;
	

	/**
	 * constructor given the number of vertices
	 * 
	 * @param numofVertices
	 */
	public Graph(int numofVertices) {
		this.numofVertices = numofVertices;
		this.vertices = new Vertex[numofVertices];
		indices= new GeneralPair[numofVertices];

	}

	/**
	 * add a vertex from a location
	 */
	public int addVertex(Location location) {
		vertices[current] = new Vertex(location);
		indices[current]= new GeneralPair<Integer, String>(current, location.getName());
		
		
		if(current+1==numofVertices)
			SortObjects.quickSort(indices);
		
		
		return current++;
	}

	/**
	 * add vertex from info about location
	 */
	public int addVertex(String name, double longitude, double latitude, boolean isCity) {
		return addVertex(new Location(name, longitude, latitude, isCity));
	}

	/**
	 * add adjacent to known vertex --> make this private
	 */
	public void addAdjacent(int v, int adj) {
		vertices[v].getAdjacentList().add(adj);
	}

	/**
	 * add adjacent given location names
	 */
	public void addAdjacent(String vName, String adjName) {
		int vertex = findLocation(vName);
		int adj = findLocation(adjName);

		addAdjacent(vertex, adj);
	}

	/**
	 * a helper method to find a vertex from name
	 */
	public int findLocation(String name) {
		
		int k= binarySearch(indices,name);// this takes logv
		
		// this takes O(v) --> forget it 
		/*for (int i = 0; i < numofVertices; i++) {
			// System.out.println(i);
			if (vertices[i].getLocation().getName().compareTo(name) == 0) {
				System.out.println(">> the found"+vertices[i].getLocation().getName()+"from binary "+vertices[k].getLocation().getName());

				return i;
				}
		}*/

		return k;
	}
	
	/**
	 * binary search to find name 
	 */
    public static int binarySearch(GeneralPair<Integer,String> [] indices, String locationName) {
        int left = 0;
        int right = indices.length - 1;

        while (left <= right) {
            int mid =  (right + left) / 2;

            if (indices[mid].getSecond().compareTo(locationName) == 0) {
            	//System.out.println("midlle");
                return indices[mid].getFirst();
            }

            // If the name is larger, ignore the left half
            if (indices[mid].getSecond().compareTo(locationName) <0) {
                left = mid + 1;
            }
            // If the name is smaller, ignore the right half
            else {
                right = mid - 1;
            }
        }

        // city not found
        return -1;
    }
    
    

	/**
	 * a method to find shortest path distance between two locations given their
	 * names
	 * 
	 * @return the shortest distance
	 */
	public double dijkstra(String sourceName, String targetName) {
		int source = findLocation(sourceName);
		int target = findLocation(targetName);

		TableEntry[] table = new TableEntry[numofVertices];

		return dijkstra(source, target, table);
	}

	/**
	 * a method to find shortest path distance between two locations given their
	 * names
	 * 
	 * @return the shortest distance
	 */
	public double dijkstra(int source, int target) {

		TableEntry[] table = new TableEntry[numofVertices];

		return dijkstra(source, target, table);
	}

	/**
	 * Find shortest path between two vertices
	 * 
	 * @param source : int representing the source vertex location in array
	 * @param target : int representing the target vertex location in array
	 * @return the shortest distance
	 */
	private double dijkstra(int source, int target, TableEntry[] table) {

		// TableEntry[] table = new TableEntry[numofVertices];

		// Initialize table
		for (int i = 0; i < numofVertices; i++) {
			table[i] = new TableEntry(i, false, Double.MAX_VALUE, -1);
		}

		// Initialize priority queue of pairs
		Heap PQ = new Heap();

		PQ.insert(new  VertexPair(source, 0));
		table[source].distance = 0;
		// distances[source] = 0;

		 VertexPair pair;
		int v;
		double cost;

		while (!PQ.isEmpty()) {

			// get the next pair in priority queue
			pair = ( VertexPair) PQ.removeMin();
			v = pair.getV();
			// System.out.print("vertex"+v+"is known");

			// this we need to remove probably
			if (table[v].known) // ignore it if shorter path exists(similar to known)
			{
				//System.out.print("ignored --> " + v + "\n");
				continue;
			}

			// set the status of vertex to known
			table[v].known = true;

			if (v == target) // reached target so stop
			{
				//System.out.print("reached ");
				break;

			}

			for (int w : vertices[v].getAdjacentList()) {

				cost = vertices[v].getLocation().distanceTo(vertices[w].getLocation());
				// System.out.print("cost from "+v+" to "+w+ " = " + cost+ " , ");

				if (!table[w].known)
					if (table[v].distance + cost < table[w].distance) {
						// System.out.print("yes ");

						// update table entry
						table[w].distance = table[v].distance + cost;
						table[w].prev = v;

						PQ.insert(new  VertexPair(w, table[w].distance));

					} // else
						// System.out.print("no ");

			}

			System.out.print("\n");

			System.out.println(PQ);
		}

		System.out.print("\n");

		// this printing only to check
		for (int i = 0; i < numofVertices; i++) {
			System.out.print("distance to " + (vertices[i].getLocation()) + " = ");
			if (table[i].distance == Double.MAX_VALUE)
				System.out.print("can not be reached \n");
			else if (i != source)
				System.out.print(table[i].distance + "\t" + table[i].prev + "\n");
			else
				System.out.print("source\n");

		}

		/*
		 * for (int i = 0; i < numofVertices; i++) { System.out.println(table[i].prev);
		 * 
		 * }
		 */

		if (table[target].distance == Double.MAX_VALUE)
			return -1;
		else
			return table[target].distance;
	}

	/**
	 * a method to find shortest path between two locations given their names
	 * 
	 * @return the list of vertices in shortest path
	 */
	public SingleLinkedList<Location> dijkstraPath(String source, String target) {

		int v1 = findLocation(source);
		int v2 = findLocation(target);

		return dijkstraPath(v1, v2);

	}

	/**
	 * main method to find Dijkstra path --> edit something here
	 * 
	 * @return the list of vertices in shortest path
	 */
	public SingleLinkedList<Location> dijkstraPath(int source, int target) {

		TableEntry[] table = new TableEntry[numofVertices];
		dijkstra(source, target, table);

		SingleLinkedList<Location> path = new SingleLinkedList<Location>();
		dijkstraPath(source, target, path, table);

		if (!path.isEmpty())
			path.add(vertices[target].getLocation());

		// ArrayList<String> pathNames = new ArrayList<String>();

		// if (!path.isEmpty()) {
		// path.add(target);

		/*
		 * for (int v : path) { pathNames.add(vertices[v].location.name); }
		 */

		// }
		// return pathNames;

		return path;

	}

	/**
	 * recursive function to find the path
	 */
	private void dijkstraPath(int source, int target, SingleLinkedList<Location> path, TableEntry[] table) {

		// reached source
		if (source == target) {
			return;
		}
		// this can't be reached
		if (table[target].prev == -1)
			return;

		if (target != source) {
			dijkstraPath(source, table[target].prev, path, table);
			path.add(vertices[table[target].prev].getLocation());
			System.out.print(vertices[target].getLocation().getName() + ",");
		}

	}

	@Override
	public String toString() {
		String data = "";

		data += "Graph: numofVertices=" + numofVertices + "\n\n";

		for (int i = 0; i < numofVertices; i++)
			data += vertices[i] + "\n";

		return data;
	}
}
