package eu.gloria.rt.catalogue.libnova;

/**
 * JNI Class implementation.
 * 
 * @author jcabello
 *
 */
public class LibNovaJNI {
	
	
	/**
	 * Recovers the object information using JNI call. 
	 * @param input Wrapper object.
	 * @param info  Output wrapper object.
	 */
	public native void getObjectInfo(LibNovaJNIInput input, LibNovaJNIObjInfo info);
	public native void getAltazByRadec(LibNovaObserver observer, LibNovaZoneDate zoneDate, LibNovaRaDecJ2000 radec, LibNovaAltaz altaz, LibNovaReturnedInfo returnedInfo);
	public native void getAngularDistance(LibNovaRaDecJ2000 obj1, LibNovaRaDecJ2000 obj2, LibNovaAngularDistance distance, LibNovaReturnedInfo returnedInfo);
	public native void getRTS(LibNovaObserver observer, LibNovaZoneDate zoneDate, String objName, LibNovaRTSInfo objRTS, LibNovaReturnedInfo returnedInfo);
	
	//public native void getAltazByName(LibNovaObserver observer, String objName, LibNovaAltaz altaz, LibNovaReturnedInfo returnedInfo);
	//public native void getRTS(LibNovaObserver observer, String objName, LibNovaRTSInfo objRTS, LibNovaReturnedInfo returnedInfo);
	//public native void getMoonAngularDistance(LibNovaRaDecJ2000 obj, LibNovaAngularDistance distance, LibNovaReturnedInfo returnedInfo);

}
