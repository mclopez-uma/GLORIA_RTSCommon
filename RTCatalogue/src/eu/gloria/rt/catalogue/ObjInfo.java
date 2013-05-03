package eu.gloria.rt.catalogue;

import eu.gloria.rt.unit.Radec;

/**
 * Object information.
 * 
 * @author jcabello
 *
 */
public class ObjInfo {
	
	/**
	 * Object category.
	 */
	private ObjCategory category;
	
	/**
	 * Radec position.
	 */
	private Radec position;
	
	/**
	 * String Object name.
	 */
	private String id;
	
	
	/**
	 * Access method.
	 * @return value.
	 */
	public ObjCategory getCategory() {
		return category;
	}
	
	/**
	 * Access method.
	 * @param category value.
	 */
	public void setCategory(ObjCategory category) {
		this.category = category;
	}
	
	/**
	 * Access method.
	 * @return value.
	 */
	public Radec getPosition() {
		return position;
	}
	
	/**
	 * Access method
	 * @param position value.
	 */
	public void setPosition(Radec position) {
		this.position = position;
	}
	
	/**
	 * Access method.
	 * @return value.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Access method.
	 * @param id value.
	 */
	public void setId(String id) {
		this.id = id;
	}

	

}
