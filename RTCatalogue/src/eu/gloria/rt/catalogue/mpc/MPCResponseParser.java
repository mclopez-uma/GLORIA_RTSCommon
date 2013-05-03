package eu.gloria.rt.catalogue.mpc;

import java.text.SimpleDateFormat;
import java.util.Date;

import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Epoch;
import eu.gloria.rt.unit.Radec;


/**
 * Parser to manage the response from Minor Planet Center Repository.
 * 
 * @author jcabello
 *
 */
public class MPCResponseParser {
	
	private String content;
	private Date date;
	
	public MPCResponseParser(String content, Date date){
		this.content = content;
		this.date = date;
	}
	
	public Radec getRadec() throws RTException{
		
		try{
			
			//************************** EXAMPLE OF RIGHT CONTENT: ***************************************
			//<pre>
			//D6108              [H= 0.1]
			//Date       UT      R.A. (J2000) Decl.    Delta     r     El.    Ph.   V     Coord Motion      Uncertainty info
			//            h m s                                                            "/min    "/min   3-sig/" P.A.
			//2012 11 23 000000 13 54 52.6 +17 26 20  51.520  50.863   47.9   0.8  17.3   +0.047   -0.002         0 332.8 / <a href="http://scully.cfa.harvard.edu/cgi-bin/uncertaintymap.cgi?Obj=D6108&JD=2456254.50000&Ext=VAR2">Map</a> / <a href="http://scully.cfa.harvard.edu/cgi-bin/uncertaintymap.cgi?Obj=D6108&JD=2456254.50000&Ext=VAR2&Form=Y&OC=500">Offsets</a>
			//</pre>
			//********************************************************************************************
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String yearString = sdf.format(date);
			
			if (content.indexOf("No object found") != -1){
				return null;
			} else if (content.indexOf("Error") != -1){
				
				throw new RTException("Error en MPC access. HTTP response=" + content);
				
			}else{
				
				int indexOfBegin = content.indexOf("<pre>") + "<pre>".length();
				int indexOfEnd =content.indexOf("</pre>");
				String data = content.substring(indexOfBegin, indexOfEnd);
				
				int yearBegin = data.indexOf(yearString);
				String dataLine = data.substring(yearBegin);
				
				String[] columns = dataLine.split(" "); 
				
				Radec radec = new Radec(
					Integer.parseInt(columns[4].replace("+", "")),	//int raHH, 
					Integer.parseInt(columns[5].replace("+", "")),	//int raMM, 
					Double.parseDouble(columns[6].replace("+", "")),	//double raSS, 
					Integer.parseInt(columns[7].replace("+", "")),	//int decDD, 
					Integer.parseInt(columns[8].replace("+", "")),	//int decMM,	
					Double.parseDouble(columns[9].replace("+", ""))	//double decSS
				);
				
				radec.setEpoch(Epoch.J2000);
				
				return radec;
				
			}
			
		}catch(Exception ex){
			
			throw new RTException("Error parsing MPMResponse. " + ex.getMessage());
			
		}
		
	}

}
