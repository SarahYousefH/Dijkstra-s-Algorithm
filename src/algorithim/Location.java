package algorithim;

public class Location implements Comparable<Location>{

	/**
	 * Field variables
	 */
	private String name;
	private double longitude; // like x
	private double latitude; // like y
	private boolean isCity;

	/**
	 * Constructors
	 */
	public Location(String name, double latitude, double longitude, boolean isCity) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.isCity = isCity;
	}

	/**
	 * methods:
	 */

	/**
	 * A method to find distance between two points
	 * 
	 * @param target : a point to find distance to
	 * @return the distance in km
	 */
	public double distanceTo(Location target) {

		double deltaX = this.longitude - target.longitude;
		double deltaY = this.latitude - target.latitude;

		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		//return distance;
		return convertDegreesToKilometers(distance);
	}

	/**
	 * A method to convert distance from degrees to km
	 * 
	 * @param distanceInDegrees
	 * @return the distance in km
	 */
	public double convertDegreesToKilometers(double distanceInDegrees) {
		// This represent what is one degree in km
		final double degreeInKm = 111.3194907933;

		double distanceInKilometers = distanceInDegrees * degreeInKm;

		return distanceInKilometers;
	}

	/**
	 * setters and getters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public boolean isCity() {
		return isCity;
	}

	public void setCity(boolean isCity) {
		this.isCity = isCity;
	}

	@Override
	public String toString() {
		return "Location [name=" + name + ", longitude=" + longitude + ", latitude=" + latitude + ", isCity=" + isCity
				+ "]\n";

		// return name;
	}

	@Override
	public int compareTo(Location o) {
		return this.name.compareTo(o.name);
	}

}
