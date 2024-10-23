package algorithim;


public class TableEntry {

	int v; // do we need this
	boolean known;
	double distance;
	int prev;
	
	
	
	public TableEntry(int v, boolean known, double distance, int prev) {
		this.v = v;
		this.known = known;
		this.distance = distance;
		this.prev = prev;
	}
}
