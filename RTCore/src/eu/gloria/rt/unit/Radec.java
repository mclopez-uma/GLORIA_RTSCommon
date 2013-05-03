package eu.gloria.rt.unit;

import java.util.StringTokenizer;

import eu.gloria.rt.exception.RTException;
import eu.gloria.tools.conversion.DegreeFormat;
import eu.gloria.tools.conversion.DegreesConverter;
import eu.gloria.tools.log.LogUtil;

/**
 * Class for equatorial coordinates. 
 * 
 * 
 * @author mclopez
 *
 */
public class Radec {	
	

	private double raDecimal;
	private double decDecimal;
	
	private int raHH;
	private int raMM;
	private double raSS;
	
	private int decDD;
	private int decMM;
	private double decSS;
	
	private boolean logs = false;
	
	private Epoch epoch;
	
	/**
	 * Constructor
	 * 
	 * @param raDecimal RA in decimal degress
	 * @param decDecimal DEC in decimal degress
	 * @throws RTException In error case.
	 */
	public Radec(double raDecimal, double decDecimal) throws RTException {
		
		this.epoch = Epoch.J2000; //by default.

		//RA
		//this.raDecimal = raDecimal;
		this.setRa(raDecimal);
		
		//DEC
		//this.decDecimal = decDecimal;
		this.setDec(decDecimal);
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param raHH RA Hours
	 * @param raMM RA Minutes
	 * @param raSS RA Seconds 
	 * @param decDD DEC Degrees
	 * @param decMM DEC Minutes
	 * @param decSS DEC Seconds
	 * @throws RTException In error case.
	 */
	public Radec(int raHH, int raMM, double raSS, int decDD, int decMM,	double decSS) throws RTException {
		
		this.epoch = Epoch.J2000; //by default.
		
		//this.raHH = raHH;
		//this.raMM = raMM;
		//this.raSS = raSS;
		
		this.setRa(raHH, raMM, raSS);
		
		//this.decDD = decDD;
		//this.decMM = decMM;
		//this.decSS = decSS;
		
		this.setDec(decDD, decMM, decSS);
		
	}
	
	/**
	 * Constructor.
	 * @param raValue Right ascension string.
	 * @param raFormat Up to now (HH:MM:SS)
	 * @param decValue Declination position string.
	 * @param decFormat Up to now (DD:MM:SS)
	 * @throws RTException In error case.
	 */
	public Radec(String raValue, DegreeFormat raFormat, String decValue, DegreeFormat decFormat) throws RTException{
		
		this.epoch = Epoch.J2000; //by default.
		
		if (raValue.startsWith("+")) raValue = raValue.substring(1);
		if (decValue.startsWith("+")) decValue = decValue.substring(1);
		
		this.setRa(raValue, raFormat);
		this.setDec(decValue, decFormat);
		
	}
	
	
	/**
	 * Empty construction Radec = 0:0:0.0, 0:0:0.0
	 * @throws RTException In error case.
	 */
	public Radec() throws RTException {
		
		this.epoch = Epoch.J2000; //by default.
		
		this.setRa(0);
		
		this.setDec(0);
	}



	public int getDecGr() {
		return decDD;
	}

	public int getDecMM() {
		return decMM;
	}

	public double getDecSS() {
		return decSS;
	}

	public double getRaDecimal() {
		return raDecimal;
	}
	
	
	/**
	 * Sets RA value.
	 * @param comp1 HH
	 * @param comp2 MM
	 * @param comp3 SS
	 * @throws RTException In errror case.
	 */
	public void setRa(int comp1, int comp2, double comp3) throws RTException{
		
		double raDegree = DegreesConverter.toDegreesH(comp1, comp2, comp3);
		this.setRa(raDegree);
	}
	
	/**
	 * Sets Dec Value
	 * @param comp1 DD
	 * @param comp2 MM
	 * @param comp3 SS
	 * @throws RTException In error case.
	 */
	public void setDec(int comp1, int comp2, double comp3) throws RTException{
		
		double decDegree = DegreesConverter.toDegreesD(comp1, comp2, comp3);
		this.setDec(decDegree);
	}
	
	public double getDecDecimal() {
		return decDecimal;
	}
	
	public int getRaHH() {
		return raHH;
	}
	
	public int getRaMM() {
		return raMM;
	}
	
	public double getRaSS() {
		return raSS;
	}
	

	/**
	 * Sets the RA value to the instance.
	 * @param value Right ascension string.
	 * @param format  Up to now (HH:MM:SS)
	 * @throws RTException In error case
	 */
	public void setRa(String value, DegreeFormat format) throws RTException{
		
		if (format != DegreeFormat.HHMMSS){
			throw new RTException("Degree Format supported: " + DegreeFormat.HHMMSS );
		}
		
		try{
			
			StringTokenizer st = new StringTokenizer(value, ":");
			String comp1 = st.nextToken();
			String comp2 = st.nextToken();
			String comp3 = st.nextToken();
			
			this.raHH = Integer.parseInt(comp1);
			this.raMM = Integer.parseInt(comp2);
			this.raSS = Double.parseDouble(comp3);
			if (logs) LogUtil.info(this, "Radec.setRa. raHH=" + this.raHH);
			if (logs) LogUtil.info(this, "Radec.setRa. raMM=" + this.raMM);
			if (logs) LogUtil.info(this, "Radec.setRa. raSS=" + this.raSS);
			
			this.raDecimal = DegreesConverter.toDegreesH(this.raHH, this.raMM, this.raSS);
			if (logs) LogUtil.info(this, "Radec.setRa. raDecimal=" + this.raDecimal);
			
		}catch(Exception ex){
			throw new RTException("Invalid degree value: [" + value + "]." + ex.getMessage());
		}
		
	}
	
	/**
	 * Sets the right ascension value.
	 * @param value Double value.
	 * @throws RTException In error case.
	 */
	public void setRa(double value) throws RTException{
		
		try{
			
			String raS = DegreesConverter.toHHMMSS(value);
			
			this.setRa(raS, DegreeFormat.HHMMSS);
			
		}catch(Exception ex){
			throw new RTException("Invalid degree value: [" + value + "]." + ex.getMessage());
		}
		
	}
	
	
	/**
	 * Sets the declination position.
	 * @param value Declination position string.
	 * @param format Up to now (DD:MM:SS).
	 * @throws RTException In error case.
	 */
	public void setDec(String value, DegreeFormat format) throws RTException{
		
		if (format != DegreeFormat.DDMMSS){
			throw new RTException("Degree Format supported: " + DegreeFormat.DDMMSS );
		}
		
		try{
			
			StringTokenizer st = new StringTokenizer(value, ":");
			String comp1 = st.nextToken();
			String comp2 = st.nextToken();
			String comp3 = st.nextToken();
			
			this.decDD = Integer.parseInt(comp1);
			this.decMM = Integer.parseInt(comp2);
			this.decSS = Double.parseDouble(comp3);
			if (logs) LogUtil.info(this, "Radec.setDec. decDD=" + this.raHH);
			if (logs) LogUtil.info(this, "Radec.setDec. decMM=" + this.raMM);
			if (logs) LogUtil.info(this, "Radec.setDec. decSS=" + this.raSS);
			
			this.decDecimal = DegreesConverter.toDegreesD(this.decDD, this.decMM, this.decSS);
			if (logs) LogUtil.info(this, "Radec.setDec. decDecimal=" + this.decDecimal);
			
		}catch(Exception ex){
			throw new RTException("Invalid degree value: [" + value + "]." + ex.getMessage());
		}
		
	}
	
	/**
	 * Sets the declination position.
	 * @param value Double value.
	 * @throws RTException In error case.
	 */
	public void setDec(double value) throws RTException{
		
		try{
			
			String decS = DegreesConverter.toDDMMSS(value);
			
			this.setDec(decS, DegreeFormat.DDMMSS);
			
		}catch(Exception ex){
			throw new RTException("Invalid degree value: [" + value + "]." + ex.getMessage());
		}
		
	}
	
	/**
	 * Conversion from decimal degrees to Hour/Degrees, Minutes, Seconds
	 * 
	 */
	private void toHHMMSS(){
		
		String dec = DegreesConverter.toDDMMSS(decDecimal);
		String[] a = dec.split(":");
		decDD = Integer.valueOf(a[0]);
		decMM = Integer.valueOf(a[1]);
		decSS = Double.valueOf(a[2]);
		
		String ra  = DegreesConverter.toHHMMSS(raDecimal);
		a = dec.split(":");
		raHH = Integer.valueOf(a[0]);
		raMM = Integer.valueOf(a[1]);
		raSS = Double.valueOf(a[2]);
		
	}
	
	/**
	 * Returns the right ascension string.
	 * @param format Up to now (HH:MM:SS)
	 * @return String 
	 * @throws RTException In error case.
	 */
	public String getRaString(DegreeFormat format) throws RTException{
		
		if (format != DegreeFormat.HHMMSS){
			throw new RTException("Degree Format supported: " + DegreeFormat.HHMMSS );
		}
		
		try{
			
			return DegreesConverter.toHHMMSS(this.raDecimal);
			
		}catch(Exception ex){
			throw new RTException(ex);
		}
	}
	
	/**
	 * Returns the declination position string.
	 * @param format Up to now (DD:MM:SS)
	 * @return String 
	 * @throws RTException In error case.
	 */
	public String getDecString(DegreeFormat format) throws RTException{
		
		if (format != DegreeFormat.DDMMSS){
			throw new RTException("Degree Format supported: " + DegreeFormat.DDMMSS );
		}
		
		try{
			
			return DegreesConverter.toDDMMSS(this.decDecimal);
			
		}catch(Exception ex){
			throw new RTException(ex);
		}
	}
	
	
	/**
	 * Conversion from Hour/Degrees, Minutes, Seconds to decimal degrees
	 * @throws RTException In error case
	 * 
	 */
	private void toDecimal() throws RTException{
		
		decDecimal = DegreesConverter.toDegreesD(decDD, decMM, decSS);
		raDecimal = DegreesConverter.toDegreesH(raHH, raMM, raSS);
		
	
	}


	public Epoch getEpoch() {
		return epoch;
	}


	public void setEpoch(Epoch epoch) {
		this.epoch = epoch;
	}	
	
	public String toString(){
		return "RADEC::[ra=" + raDecimal + ", dec=" + decDecimal + ", epoch=" + epoch + "]";
	}

}
