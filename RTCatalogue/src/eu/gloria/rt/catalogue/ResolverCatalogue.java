package eu.gloria.rt.catalogue;

import java.util.Date;

import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Epoch;

/**
 * Rolver Catalogue interface.
 * 
 * @author jcabello
 *
 */
public interface ResolverCatalogue {
	
	/**
	 * Returns the Object information for now.
	 * @param id Object Name.
	 * @param epoch Epoch.
	 * @return Object information.
	 * @throws RTException In error case
	 */
	public ObjInfo getObject(String id, Epoch epoch) throws RTException;
	
	/**
	 * Returns the Object information for a date.
	 * @param id Object Name.
	 * @param day Date.
	 * @param epoch Epoch.
	 * @return Object information.
	 * @throws RTException In error case.
	 */
	public ObjInfo getObject(String id, Date day, Epoch epoch) throws RTException;
	
	/**
	 * Returns the catalogue name (repository)
	 * @return String.
	 */
	public String getCatalogueName();

}
