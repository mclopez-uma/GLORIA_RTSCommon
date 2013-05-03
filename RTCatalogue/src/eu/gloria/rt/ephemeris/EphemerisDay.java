package eu.gloria.rt.ephemeris;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ephemeris data for a day.
 * 
 * @author jcabello
 *
 */
public class EphemerisDay {
	
	private Date date;
	private List<EphemerisData> data;
	
	public EphemerisDay(){
		data = new ArrayList<EphemerisData>();
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<EphemerisData> getData() {
		return data;
	}
	public void setData(List<EphemerisData> data) {
		this.data = data;
	}
	
	public EphemerisData searchEphemerisData(Date date){
		
		if (data.size() == 0) return null;
		if (date.compareTo(data.get(0).getDate()) < 0) return null;
		
		for (int x = 0 ; x < data.size(); x++){
			if (data.get(x).getDate().compareTo(date) == 0) {
				return data.get(x);
			}else if (data.get(x).getDate().compareTo(date) > 0) {
				return  data.get(x-1);
			}
		}
		
		return data.get(data.size()-1);
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("EphemerisDay:[" + date + "]{\n");
		
		for (int x = 0 ; x < data.size(); x++){
			sb.append("   " + data.get(x).toString());
		}
		sb.append("}");
		return sb.toString();
		
	}
	

}
