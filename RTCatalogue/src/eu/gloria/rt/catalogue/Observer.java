package eu.gloria.rt.catalogue;

/**
 * Observer latitude/longitude wrapper.
 * 
 * @author jcabello
 *
 */
public class Observer {
	
	private double latitude;
	private double longitude;
	private double altitude;
	
	public Observer(){
		latitude = 0;
		longitude = 0;
		altitude = 0;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	
	public String toString(){
		return "Observer::[latitude=" + latitude + ", longitude=" + longitude + ", altitude=" + altitude + "]";
	}

}
