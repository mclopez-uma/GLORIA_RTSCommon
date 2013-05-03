package eu.gloria.rt.ephemeris;

import java.util.Date;

import eu.gloria.rt.exception.RTException;

/**
 * Interface to calculate the Ephemeris data for a date.
 * 
 * @author jcabello
 *
 */
public interface Ephemeris {
	
	public EphemerisData getObjectInfo(Date date) throws EphemerisOutOfScopeException, RTException;
	public String getObjId();

}
