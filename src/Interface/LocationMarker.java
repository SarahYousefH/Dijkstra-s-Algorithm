package Interface;

import algorithim.Location;
import javafx.scene.shape.Circle;

public class LocationMarker extends Circle {

	Location location;

	// constants
	static double Wxmax = 678;// 678;
	static double Wxmin = 0;
	static double Wymax = 845;// 850
	static double Wymin = 0;

	static double Mxmax = 34.567432;
	static double Mxmin = 34.218875;

	static double Mymax = 31.593416;// 31.219861;
	static double Mymin = 31.219861;// 31.593416;

	double y;
	double x;

	public LocationMarker(Location location) {
		super();
		this.location = location;

		// System.out.println(">>"+location.getLongitude()+","+location.getLatitude());

		this.x = calculateX(location.getLongitude());
		this.y = calculateY(location.getLatitude());

		this.setCenterX(x);
		this.setCenterY(y);

	}

	public LocationMarker(double latitude, double longitude) {
		super();
		this.x = calculateX(longitude);
		this.y = calculateY(latitude);
	}

	public static double calculateY(double latitude) {
		double y = ((Mymax - latitude) * (Wymax - Wymin) / (Mymax - Mymin)) + Wymin;
		//double y = ((latitude-Mymin) * (Wymax - Wymin) / (Mymax - Mymin)) + Wymin;

		return y;

	}

	public static double calculateX(double longitude) {
		double x = Wxmin + (( longitude- Mxmin ) / ( Mxmax-Mxmin )) * (Wxmax - Wxmin);
		// System.out.println(x);
		return x;
	}

}
