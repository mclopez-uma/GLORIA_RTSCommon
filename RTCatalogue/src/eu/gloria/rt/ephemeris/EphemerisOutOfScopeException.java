package eu.gloria.rt.ephemeris;

import eu.gloria.rt.exception.RTException;

/**
 * Exception - Out of scope.
 * 
 * @author jcabello
 *
 */
public class EphemerisOutOfScopeException extends RTException {
	
	/**
	 * Constructor.
	 * @param message Error message
	 */
	public EphemerisOutOfScopeException(String message){
		super(message); 
	} 

}
