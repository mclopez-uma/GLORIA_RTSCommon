package eu.gloria.rt.catalogue.libnova;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Rise, Transit and Set information Wrapper.
 * 
 * @author jcabello
 *
 */
public class LibNovaRTSInfo {
	
	public int rise_year;
	public int rise_month;
	public int rise_day;
	public int rise_hours;
	public int rise_minutes;
	public int rise_seconds;
	
	public int transit_year;
	public int transit_month;
	public int transit_day;
	public int transit_hours;
	public int transit_minutes;
	public int transit_seconds;
	
	public int set_year;
	public int set_month;
	public int set_day;
	public int set_hours;
	public int set_minutes;
	public int set_seconds;
	
	public int circumpolar;
	
	private String formatNumber(int number, int lenght){
		
		String result = String.valueOf(number);
		while (result.length() < lenght){
			result = "0" + result;
		}
		
		return result;
	}
	
	public Date getRise() throws ParseException {
		String dateS = formatNumber(rise_year, 4) + formatNumber(rise_month, 2) + formatNumber(rise_day, 2) + formatNumber(rise_hours, 2) + formatNumber(rise_minutes, 2) + formatNumber(rise_seconds, 2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.parse(dateS);
	}
	
	public Date getTransit() throws ParseException {
		String dateS = formatNumber(transit_year, 4) + formatNumber(transit_month, 2) + formatNumber(transit_day, 2) + formatNumber(transit_hours, 2) + formatNumber(transit_minutes, 2) + formatNumber(transit_seconds, 2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.parse(dateS);
	}
	
	public Date getSet() throws ParseException {
		String dateS = formatNumber(set_year, 4) + formatNumber(set_month, 2) + formatNumber(set_day, 2) + formatNumber(set_hours, 2) + formatNumber(set_minutes, 2) + formatNumber(set_seconds, 2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.parse(dateS);
	}

}
