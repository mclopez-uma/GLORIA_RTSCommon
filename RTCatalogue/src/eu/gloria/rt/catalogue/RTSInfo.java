package eu.gloria.rt.catalogue;

import java.util.Date;

/**
 * Rise, Set and Transit.
 * 
 * @author jcabello
 *
 */
public class RTSInfo {
	
	private Date rise;
	private Date set;
	private Date transit;
	
	public Date getRise() {
		return rise;
	}
	public void setRise(Date rise) {
		this.rise = rise;
	}
	public Date getSet() {
		return set;
	}
	public void setSet(Date set) {
		this.set = set;
	}
	public Date getTransit() {
		return transit;
	}
	public void setTransit(Date transit) {
		this.transit = transit;
	}
	
	public String toString(){
		return "[Rise=" + getRise() + "][Transit=" +getTransit() + "][Set=" + getSet() + "]";
	}

}
