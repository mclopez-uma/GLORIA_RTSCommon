package eu.gloria.rt.ephemeris;

import java.util.Date;

import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Altaz;
import eu.gloria.rt.unit.Radec;


/**
 * Minimal ephemeris data.
 * 
 * @author jcabello
 *
 */
public class EphemerisData {
	
	private Date date;
	private double ra;
	private double dec;
	private double alt;
	private double az;
	
	public double getRa() {
		return ra;
	}
	public void setRa(double ra) {
		this.ra = ra;
	}
	public double getDec() {
		return dec;
	}
	public void setDec(double dec) {
		this.dec = dec;
	}
	public double getAlt() {
		return alt;
	}
	public void setAlt(double alt) {
		this.alt = alt;
	}
	public double getAz() {
		return az;
	}
	public void setAz(double az) {
		this.az = az;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Radec getRadec() throws RTException{
		Radec result = new Radec();
		result.setDec(this.dec);
		result.setRa(this.ra);
		return result;
	}
	
	public Altaz getAltaz() throws RTException{
		Altaz result = new Altaz();
		result.setAlt(this.alt);
		result.setAz(this.az);
		return result;
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("EphemerisData:[");
		sb.append(", date=").append(date.toString());
		sb.append(", ra=").append(ra);
		sb.append(", dec=").append(dec);
		sb.append(", alt=").append(alt);
		sb.append(", az=").append(az);
		sb.append("]\n");
		return sb.toString();
		
	}

}
