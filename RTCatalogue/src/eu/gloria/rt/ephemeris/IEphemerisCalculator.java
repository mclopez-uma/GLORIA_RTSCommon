package eu.gloria.rt.ephemeris;


import eu.gloria.rt.catalogue.Observer;
import eu.gloria.rt.exception.RTException;

/**
 * Interface for all EphemerisCalculator implementations.
 * 
 * @author jcabello
 *
 */
public interface IEphemerisCalculator {
	
	public Ephemeris getEphemeris(String id, Observer observer, int days) throws RTException;
	
}
