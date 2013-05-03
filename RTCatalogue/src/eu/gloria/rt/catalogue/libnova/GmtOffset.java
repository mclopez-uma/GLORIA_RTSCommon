package eu.gloria.rt.catalogue.libnova;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Calculates the GMT-Offset for libnova.
 * 
 * @author jcabello
 *
 */
public class GmtOffset {
	
	private static String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	private long msecOffset;
	
	/**
	 * Constructor
	 * @param date Date to process.
	 * @throws Exception In error case
	 */
	public GmtOffset(Date date) throws Exception{
		
		Date utcNow = stringDateToDate(getGmtdatetimeAsString(date));
		
		msecOffset = date.getTime() - utcNow.getTime();
		
	}
	
	/**
	 * Retieve the offset in hout.
	 * @return Seconds
	 */
	public int getOffsetHour(){
		return (int) ((msecOffset /1000) /3600);
	}
	
	/**
	 * Retieve the offset in seconds.
	 * @return Seconds
	 */
	public int getOffsetSeconds(){
		return (int) (msecOffset /1000);
	}
	
	
	private String getGmtdatetimeAsString(Date date){
	    final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
	    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	    final String utcTime = sdf.format(date);

	    return utcTime;
	}
	
	private Date stringDateToDate(String StrDate) throws ParseException{
	    Date dateToReturn = null;
	    SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
	    dateToReturn = (Date)dateFormat.parse(StrDate);
	    return dateToReturn;
	}
	
	public final static void main(String[] args) throws Exception {
		
		GmtOffset offset = new GmtOffset(new Date());
		
		System.out.println(offset.getOffsetSeconds());
	}

}
