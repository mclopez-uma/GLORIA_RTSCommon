package eu.gloria.rt.unit;

import java.util.StringTokenizer;

import eu.gloria.rt.exception.RTException;
import eu.gloria.tools.conversion.DegreeFormat;
import eu.gloria.tools.conversion.DegreesConverter;
import eu.gloria.tools.log.LogUtil;

/**
 * 
 * Class for horizontal coordinates.
 * 
 * @author mclopez
 *
 */
public class Altaz {
	
	private double altDecimal;
	private double azDecimal;
	
	private boolean altNegative;
	private int altDD;
	private int altMM;
	private double altSS;
	
	private boolean azNegative;
	private int azDD;
	private int azMM;
	private double azSS;
	
	
	
	public Altaz(double altDecimal, double azDecimal) throws RTException {

		//ALT
		this.setAlt(altDecimal);
		
		//AZ
		this.setAz(azDecimal);
	}
	
	
	
	/*public Altaz(boolean altNegative, int altDD, int altMM, double altSS, boolean azNegative, int azDD, int azMM, double azSS) throws RTException {
		
		this.setAlt(altNegative, altDD, altMM, altSS);
		
		this.setAz(azNegative, azDD, azMM, azSS);
		
	}*/
	
	public Altaz(String altValue, DegreeFormat raFormat, String azValue, DegreeFormat decFormat) throws RTException{
		
		this.setAlt(altValue, raFormat);
		this.setAz(azValue, decFormat);
		
	}
	
	
	/**
	 * Empty construction Altaz = 0:0:0.0, 0:0:0.0
	 * @throws RTException In error case.
	 */
	public Altaz() throws RTException {
		
		this.setAlt(0);
		
		this.setAz(0);
	}



	/**
	 * Sets Alt Value.
	 * @param comp1 DD
	 * @param comp2 MM
	 * @param comp3 SS
	 * @throws RTException In error case.
	 */
	public void setAlt(boolean negative, int comp1, int comp2, double comp3) throws RTException{
		
		double altDegree = DegreesConverter.toDegreesD(negative, comp1, comp2, comp3);
		this.setAlt(altDegree);
	}
	
	/**
	 * Sets Az Value.
	 * @param comp1 DD
	 * @param comp2 MM
	 * @param comp3 SS
	 * @throws RTException In error case.
	 */
	public void setAz(boolean negative, int comp1, int comp2, double comp3) throws RTException{
		
		double azDegree = DegreesConverter.toDegreesD(negative, comp1, comp2, comp3);
		this.setAz(azDegree);
	}
	

	
	public void setAlt(String value, DegreeFormat format) throws RTException{
		
		if (format != DegreeFormat.DDMMSS){
			throw new RTException("Degree Format supported: " + DegreeFormat.DDMMSS );
		}
		
		try{
			
			StringTokenizer st = new StringTokenizer(value, ":");
			String comp1 = st.nextToken();
			String comp2 = st.nextToken();
			String comp3 = st.nextToken();
			
			this.altNegative = comp1.startsWith("-");
			if (this.altNegative){
				this.altDD = Integer.parseInt(comp1.substring(1)); //jump -
			}else{
				this.altDD = Integer.parseInt(comp1);
			}
			
			//this.altDD = Integer.parseInt(comp1);
			this.altMM = Integer.parseInt(comp2);
			this.altSS = Double.parseDouble(comp3);
			LogUtil.info(this, "Altaz.setAlt. altDD=" + this.altDD);
			LogUtil.info(this, "Altaz.setAlt. altMM=" + this.altMM);
			LogUtil.info(this, "Altaz.setAlt. altSS=" + this.altSS);
			
			this.altDecimal = DegreesConverter.toDegreesD(this.altNegative, this.altDD, this.altMM, this.altSS);
			LogUtil.info(this, "Altaz.setAlt. altDecimal=" + this.altDecimal);
			
		}catch(Exception ex){
			throw new RTException("Invalid degree value: [" + value + "]." + ex.getMessage());
		}
		
	}
	
	public void setAlt(double value) throws RTException{
		
		try{
			
			String altS = DegreesConverter.toDDMMSS(value);
			
			this.setAlt(altS, DegreeFormat.DDMMSS);
			
		}catch(Exception ex){
			throw new RTException("Invalid degree value: [" + value + "]." + ex.getMessage());
		}
		
	}
	
	
	public void setAz(String value, DegreeFormat format) throws RTException{
		
		if (format != DegreeFormat.DDMMSS){
			throw new RTException("Degree Format supported: " + DegreeFormat.DDMMSS );
		}
		
		try{
			
			StringTokenizer st = new StringTokenizer(value, ":");
			String comp1 = st.nextToken();
			String comp2 = st.nextToken();
			String comp3 = st.nextToken();
			
			this.azNegative = comp1.startsWith("-");
			if (this.azNegative){
				this.azDD = Integer.parseInt(comp1.substring(1)); //jump -
			}else{
				this.azDD = Integer.parseInt(comp1);
			}
			
			//this.azDD = Integer.parseInt(comp1);
			this.azMM = Integer.parseInt(comp2);
			this.azSS = Double.parseDouble(comp3);
			LogUtil.info(this, "Altaz.setAz. azDD=" + this.azDD);
			LogUtil.info(this, "Altaz.setAz. azMM=" + this.azMM);
			LogUtil.info(this, "Altaz.setAz. azcSS=" + this.azSS);
			
			this.azDecimal = DegreesConverter.toDegreesD(this.azNegative, this.azDD, this.azMM, this.azSS);
			LogUtil.info(this, "Altaz.setAz. azDecimal=" + this.azDecimal);
			
		}catch(Exception ex){
			throw new RTException("Invalid degree value: [" + value + "]." + ex.getMessage());
		}
		
	}
	
	public void setAz(double value) throws RTException{
		
		try{
			
			String azS = DegreesConverter.toDDMMSS(value);
			
			this.setAz(azS, DegreeFormat.DDMMSS);
			
		}catch(Exception ex){
			throw new RTException("Invalid degree value: [" + value + "]." + ex.getMessage());
		}
		
	}
	
	private void toDDMMSS(){
		
		String az = DegreesConverter.toDDMMSS(azDecimal);
		String[] a = az.split(":");
		azDD = Integer.valueOf(a[0]);
		azMM = Integer.valueOf(a[1]);
		azSS = Double.valueOf(a[2]);
		
		String alt  = DegreesConverter.toDDMMSS(altDecimal);
		a = alt.split(":");
		altDD = Integer.valueOf(a[0]);
		altMM = Integer.valueOf(a[1]);
		altSS = Double.valueOf(a[2]);
		
	}
	
	public String getAltString(DegreeFormat format) throws RTException{
		
		if (format != DegreeFormat.DDMMSS){
			throw new RTException("Degree Format supported: " + DegreeFormat.DDMMSS );
		}
		
		try{
			
			return DegreesConverter.toDDMMSS(this.altDecimal);
			
		}catch(Exception ex){
			throw new RTException(ex);
		}
	}
	
	public String getAzString(DegreeFormat format) throws RTException{
		
		if (format != DegreeFormat.DDMMSS){
			throw new RTException("Degree Format supported: " + DegreeFormat.DDMMSS );
		}
		
		try{
			
			return DegreesConverter.toDDMMSS(this.azDecimal);
			
		}catch(Exception ex){
			throw new RTException(ex);
		}
	}
	
	
	private void toDecimal() throws RTException{
		
		azDecimal = DegreesConverter.toDegreesD(azNegative, azDD, azMM, azSS);
		altDecimal = DegreesConverter.toDegreesD(altNegative, altDD, altMM, altSS);
		
	
	}



	public double getAltDecimal() {
		return altDecimal;
	}



	public double getAzDecimal() {
		return azDecimal;
	}



	public int getAltDD() {
		return altDD;
	}



	public int getAltMM() {
		return altMM;
	}



	public double getAltSS() {
		return altSS;
	}



	public int getAzDD() {
		return azDD;
	}



	public int getAzMM() {
		return azMM;
	}



	public double getAzSS() {
		return azSS;
	}	

}
