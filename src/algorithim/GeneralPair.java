package algorithim;

/**
 * This class represent a pair which can be anything, using generics
 * 
 * @param <G1> : Generic for the first attribute in the pair
 * @param <G2> : Generic for the second attribute in the pair , two pairs will
 *             be compared based on this attribute
 */
public class GeneralPair<G1, G2 extends Comparable<G2>> implements Comparable<GeneralPair<G1, G2>> {

	private G1 first;
	private G2 second;

	public GeneralPair(G1 first, G2 second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * setters and getter
	 * @return
	 */
	public G1 getFirst() {
		return first;
	}

	public void setFirst(G1 first) {
		this.first = first;
	}

	public G2 getSecond() {
		return second;
	}

	public void setSecond(G2 second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "(" + first + " , " + second + ")";
	}

	@Override
	public int compareTo(GeneralPair<G1, G2> other) {
		return this.second.compareTo(other.second);
	}
}
