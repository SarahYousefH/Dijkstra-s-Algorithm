package algorithim;

public class VertexPair implements Comparable< VertexPair> {

	private int  vertex;
	private double currentDistance;

	public VertexPair(int source, double currentDistance) {
		this.vertex = source;
		this.currentDistance = currentDistance;
	}

	public int getV() {
		return vertex;
	}

	public void setV(int v) {
		this.vertex = v;
	}

	public double getCurrentDistance() {
		return currentDistance;
	}

	public void setCurrentDistance(double currentDistance) {
		this.currentDistance = currentDistance;
	}

	@Override
	public String toString() {
		return "(" + vertex + " , " + currentDistance + ")";
	}

	@Override
	public int compareTo(VertexPair other) {
		return Double.compare(this.currentDistance, other.currentDistance);
	}

}
