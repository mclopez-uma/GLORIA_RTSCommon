package eu.gloria.rt.ephemeris;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eu.gloria.rt.catalogue.Catalogue;
import eu.gloria.rt.catalogue.ObjCategory;
import eu.gloria.rt.catalogue.ObjInfo;
import eu.gloria.rt.catalogue.Observer;
import eu.gloria.rt.exception.RTException;
import eu.gloria.tools.log.LogUtil;
import eu.gloria.tools.time.DateIterator;
import eu.gloria.tools.time.DateTools;

/**
 * Ephemeris for a MinorPlanetOrAsteroid object category.
 * 
 * @author jcabello
 *
 */
public class EphemerisMinorPlanetOrAsteroid implements Ephemeris {
	
	private List<EphemerisDay> list;
	private String id;
	private Catalogue catalogue;
	private Observer observer;
	private Date firstDateOutOfScope;
	private int days;
	private Date lastRecalculation;
	private int mpcAccessCount;
	private boolean verbose;
	
	public EphemerisMinorPlanetOrAsteroid(String id, Observer observer, int days, boolean verbose){
		this.list = new ArrayList<EphemerisDay>();
		this.id = id;
		this.observer = observer;
		this.catalogue = new Catalogue(observer.getLongitude(), observer.getLatitude(), observer.getAltitude());
		this.firstDateOutOfScope = null;
		this.days = days;
		this.mpcAccessCount = 0;
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
				boolean recalculation = true;
				Date now = new Date();
				if (lastRecalculation != null){
					Date minimalRecalculation = DateTools.increment(lastRecalculation, Calendar.MINUTE, 5);
					if (now.compareTo(minimalRecalculation) > 0){
						
						if (verbose) LogUtil.info(this, "EphemerisTracerMPC.EphemerisMinorPlanetOrAsteroid. Object[" + id + "]. Date[" + date + "]  Accessing to MPC. 5 minutes checked!!");
						
						recalculation = true;
						lastRecalculation = now;
						mpcAccessCount = 1;
						
					}else{
						
						if (mpcAccessCount < 10){
							recalculation = true;
							mpcAccessCount++;
							if (verbose) LogUtil.info(this, "EphemerisTracerMPC.EphemerisMinorPlanetOrAsteroid. Object[" + id + "] Accessing to MPC. Access count is=" + mpcAccessCount);
						}else{
							LogUtil.severe(this, "EphemerisTracerMPC.EphemerisMinorPlanetOrAsteroid. Object[" + id + "] TOO soon (<5min) to recalculate ephemeris and access count is=" + mpcAccessCount);
							recalculation = false;
						}
						
					}
				}
				
				if (recalculation) {
					reCalculate(now); //now
					
				}
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
			
			/*if (verbose) LogUtil.info(this, "EphemerisTracerMPC.EphemerisMinorPlanetOrAsteroid.reCalulating. BEGIN. Object[" + id + "]. Date[" + date + "] .... sleep 5 seconds.");
			Thread.sleep(5000);*/
			
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
			
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.EphemerisMinorPlanetOrAsteroid.reCalulated. END. Object[" + id + "]. Date[" + date + "] .... sleep 5 seconds.");
			
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
		
		if (verbose) LogUtil.info(this, "EphemerisTracerMPC.EphemerisMinorPlanetOrAsteroid.calculateEphemeris. BEGIN. Object[" + id + "]. Date[" + date + "]");
		
		MPCEphemerisDayHttpRequest httpRequest = new MPCEphemerisDayHttpRequest(observer, this.id, date, verbose);
		EphemerisDay result = new EphemerisDay();
		result.setDate(date);
		result.setData(httpRequest.getItems());
		
		if (verbose) LogUtil.info(this, "EphemerisTracerMPC.EphemerisMinorPlanetOrAsteroid.calculateEphemeris. BEGIN. Object[" + id + "]. Date[" + date + "]. TimeSlots number:" + httpRequest.getItems().size());
		

		return result;
		
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("EphemerisTracerMPC.EphemerisDays:[\n");
		
		for (int x = 0; x < list.size(); x++){
			sb.append(list.get(x).toString()).append(", ");
		}
		sb.append("]");
		
		return sb.toString();
		
	}

}
