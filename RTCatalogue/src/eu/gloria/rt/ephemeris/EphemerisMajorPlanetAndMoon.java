package eu.gloria.rt.ephemeris;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eu.gloria.rt.catalogue.Catalogue;
import eu.gloria.rt.catalogue.CatalogueTools;
import eu.gloria.rt.catalogue.ObjCategory;
import eu.gloria.rt.catalogue.ObjInfo;
import eu.gloria.rt.catalogue.Observer;
import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Altaz;
import eu.gloria.rt.unit.Radec;
import eu.gloria.tools.log.LogUtil;
import eu.gloria.tools.time.DateIterator;
import eu.gloria.tools.time.DateTools;

/**
 * Ephemeris for a MajorPlanetAndMoon object category.
 * 
 * @author jcabello
 *
 */
public class EphemerisMajorPlanetAndMoon implements Ephemeris {
	
	private List<EphemerisDay> list;
	private String id;
	private Catalogue catalogue;
	private Observer observer;
	private Date firstDateOutOfScope;
	private int days;
	private boolean verbose;
	
	public static void main(String[] args) throws Exception {
		
		Observer observer = new Observer();
		EphemerisMajorPlanetAndMoon ep = new EphemerisMajorPlanetAndMoon("moon", observer, 3, true);
		ep.reCalculate(new Date());
		System.out.println(ep.toString());
		//ep.reCalculate(DateTools.increment(new Date(), Calendar.DATE, 1));
		//System.out.println(ep.toString());
		
		Date searchdate = DateTools.getDate("20130228001000", "yyyyMMddHHmmss");
		EphemerisData efd = ep.getObjectInfo(searchdate);
		System.out.println(efd.toString());
		
		searchdate = DateTools.getDate("20130301000100", "yyyyMMddHHmmss");
		efd = ep.getObjectInfo(searchdate);
		System.out.println(efd.toString());
		
		searchdate = DateTools.getDate("20130301230000", "yyyyMMddHHmmss");
		efd = ep.getObjectInfo(searchdate);
		System.out.println(efd.toString());
		
		searchdate = DateTools.getDate("20130301235500", "yyyyMMddHHmmss");
		efd = ep.getObjectInfo(searchdate);
		System.out.println(efd.toString());
		
	}
	
	public EphemerisMajorPlanetAndMoon(String id, Observer observer, int days, boolean verbose){
		this.list = new ArrayList<EphemerisDay>();
		this.id = id;
		this.observer = observer;
		this.catalogue = new Catalogue(observer.getLongitude(), observer.getLatitude(), observer.getAltitude());
		this.firstDateOutOfScope = null;
		this.days = days;
		this.verbose = verbose;
	}
	
	@Override
	public String getObjId() {
		return id;
	}
	

	@Override
	public EphemerisData getObjectInfo(Date date)
			throws EphemerisOutOfScopeException, RTException {
		
		synchronized (this) {
			if (firstDateOutOfScope == null || firstDateOutOfScope.compareTo(date) <= 0){
				reCalculate(new Date()); //now
			}
		}
		
		EphemerisDay day = searchEphemerisDay(date);
		
		if (day == null){
			EphemerisOutOfScopeException ex = new EphemerisOutOfScopeException("The date is out of the scope of this ephemeris. Date: " + date.toString());
			throw ex;
		}else{
			return day.searchEphemerisData(date);
		}
	}
	
	private EphemerisDay searchEphemerisDay(Date date) throws RTException{
		
		try{
		
			Date trunkDay = DateTools.trunk(date, "yyyyMMdd");
		
			for (int x = 0; x < list.size(); x++){
				if (list.get(x).getDate().compareTo(trunkDay) == 0){
					return list.get(x);
				}
			}
		
			return null;
		
		}catch(Exception ex){
			RTException e = new RTException(ex);
			throw e;
		}
	}
	
	private void reCalculate(Date date) throws RTException {
		
		try{
			
			firstDateOutOfScope = null;
			
			//Date now = new Date();
			Date now = date;
			Date today = DateTools.trunk(now, "yyyyMMdd");
			
			clearPreviousDays(today);
			
			DateIterator iterator = new DateIterator(today, Calendar.DATE, 1, Calendar.DATE, this.days);
			
			while (iterator.hasNext()){
				Date tmpDate = iterator.next();
				EphemerisDay eDay = getEphemerisDay(tmpDate);
				if (eDay == null){
					list.add(calculateEphemeris(tmpDate));
				}
			}
			
			firstDateOutOfScope = iterator.next();
			
		}catch(Exception ex){
			RTException e = new RTException(ex);
			throw e;
		}
		
		
	}
	
	private void clearPreviousDays(Date date){
		
		int deleteCount = 0;
		
		for (int x = 0; x < list.size(); x++){
			if (list.get(x).getDate().compareTo(date) < 0){
				deleteCount++;
			}else{
				break;
			}
		}
		
		if (deleteCount > 0){
			for (int x = 0; x < deleteCount; x++){
				list.remove(0);
			}
		}
	}
	
	private EphemerisDay getEphemerisDay(Date date){
		
		EphemerisDay result = null;
		
		for (int x = 0; x < list.size(); x++){
			if (list.get(x).getDate().compareTo(date) == 0){
				return list.get(x);
			}
		}
		
		return result;
	}
	
	private EphemerisDay calculateEphemeris(Date date) throws RTException{
		
		if (verbose) LogUtil.info(this, "EphemerisTracerLibNova.EphemerisMajorPlanetAndMoon.calculateEphemeris Object[" + id + "] BEGIN");
		
		EphemerisDay result = new EphemerisDay();
		result.setDate(date);
		
		DateIterator iterator = new DateIterator(date, Calendar.MINUTE, 10, Calendar.DATE, 1);
		
		int count = 0;
		
		while (iterator.hasNext()){
			
			EphemerisData data = new EphemerisData();
			Date tmpDate = iterator.next();
			
			ObjInfo info = this.catalogue.getObject(id , ObjCategory.MajorPlanetAndMoon, tmpDate);
			Radec radec = info.getPosition();
			Altaz altaz = CatalogueTools.getAltazByRadec(observer, tmpDate, radec);
			
			data.setAlt(altaz.getAltDecimal());
			data.setAz(altaz.getAzDecimal());
			data.setRa(radec.getRaDecimal());
			data.setDec(radec.getDecDecimal());
			
			data.setDate(tmpDate);
			
			result.getData().add(data);
			
			if (verbose) LogUtil.info(this, "EphemerisTracerLibNova.EphemerisMajorPlanetAndMoon.calculateEphemeris Object[" + id + "]. EphemerisData=>" + data.toString());
			
			count++;
		}
		
		if (verbose) LogUtil.info(this, "EphemerisTracerLibNova.EphemerisMajorPlanetAndMoon.calculateEphemeris Object[" + id + "] END. TimeSlots number:" + count);
		if (verbose) LogUtil.info(this, result.toString());
		
		return result;
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("EphemerisTracerLibNova.EphemerisDays:[\n");
		
		for (int x = 0; x < list.size(); x++){
			sb.append(list.get(x).toString()).append(", ");
		}
		sb.append("]");
		
		return sb.toString();
		
	}

	

}
