package eu.gloria.rt.ephemeris;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import eu.gloria.rt.catalogue.Catalogue;
import eu.gloria.rt.catalogue.ObjCategory;
import eu.gloria.rt.catalogue.ObjInfo;
import eu.gloria.rt.catalogue.Observer;
import eu.gloria.rt.exception.RTException;
import eu.gloria.tools.log.LogUtil;

/**
 * Ephemeris Calculator for OutsideSSystemObj.
 * 
 * @author jcabello
 *
 */
public class EphemerisCalculatorOutsideSSystemObj implements IEphemerisCalculator {
	
	private int maxCacheSize;
	private List<Ephemeris> ephemerisList;
	private boolean verbose;
	
	public EphemerisCalculatorOutsideSSystemObj(boolean verbose){
		ephemerisList = new ArrayList<Ephemeris>();
		maxCacheSize = 10;
		this.verbose = verbose;
	}

	@Override
	public Ephemeris getEphemeris(String id, Observer observer, int days)
			throws RTException {
		
		synchronized (ephemerisList) {
		
			Ephemeris  result = searchCachedEphemeris(id);
			if (result == null){
		
				boolean belongsToThisCategory = objectBelongsToThisCathegory(id, observer);
				if (belongsToThisCategory){
					result = new EphemerisOutSideSSystemObj(id, observer, days, verbose);
					LogUtil.info(this, " EphemerisTracerSesame.EphemerisCalculatorOutsideSSystemObj. Object[" + id + "] Ephemeris built and cached!!!");
					ephemerisList.add(result);
					if (ephemerisList.size() > maxCacheSize){
						ephemerisList.remove(0); //The oldest Ephemeris
					}
				}else{
					LogUtil.info(this, " EphemerisTracerSesame.EphemerisCalculatorOutsideSSystemObj. Object[" + id + "] does not belong to this cathegory");
					result = null;
				}
			}
		
			return result;
		}
		
	}
	
	private boolean objectBelongsToThisCathegory(String id, Observer observer){
		try{
			Catalogue catalogue = new Catalogue(observer.getLongitude(), observer.getLatitude(), observer.getAltitude());
			ObjInfo objInfo = catalogue.getObject(id, ObjCategory.OutsideSSystemObj);
			return (objInfo != null);
		}catch(Exception ex){
			LogUtil.severe(this, " EphemerisTracerSesame.EphemerisCalculatorOutsideSSystemObj. objectBelongsToThisCathegory. Object[" + id + "] Error: " + ex.getMessage());
			return false;
		}
		
	}
	
	private Ephemeris searchCachedEphemeris(String id){
		Ephemeris result = null;
		
		for (int x = 0; x < ephemerisList.size(); x++){
			if (ephemerisList.get(x).getObjId().equals(id)){
				result = ephemerisList.get(x);
				LogUtil.info(this, " EphemerisTracerSesame.EphemerisCalculatorOutsideSSystemObj. Object[" + id + "] Ephemeris found in cache!!!");
				break;
			}
		}
		
		return result;
	}


}
