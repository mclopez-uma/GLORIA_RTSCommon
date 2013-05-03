package eu.gloria.rt.ephemeris;

import java.util.HashMap;

import eu.gloria.rt.catalogue.Catalogue;
import eu.gloria.rt.catalogue.ObjCategory;
import eu.gloria.rt.catalogue.ObjInfo;
import eu.gloria.rt.catalogue.Observer;
import eu.gloria.rt.exception.RTException;
import eu.gloria.tools.log.LogUtil;

/**
 * Ephemeris Calculator for MajorPlanetAndMoon.
 * 
 * @author jcabello
 *
 */
public class EphemerisCalculatorMajorPlanetAndMoon implements
		IEphemerisCalculator {
	
	private static HashMap<String, Ephemeris> ephemerisList;
	
	private boolean verbose;
	
	static{
		ephemerisList = new HashMap<String, Ephemeris>();
	}
	
	public static void main(String[] args) throws Exception {
		
		EphemerisCalculatorMajorPlanetAndMoon calculator = new EphemerisCalculatorMajorPlanetAndMoon(true);
		Observer observer = new Observer();
		calculator.getEphemeris("moon", observer, 3);
		calculator.getEphemeris("jupiter", observer, 3);
		calculator.getEphemeris("saturn", observer, 3);
		calculator.getEphemeris("jupiter", observer, 3);
	}
	
	
	
	public EphemerisCalculatorMajorPlanetAndMoon(boolean verbose){
		this.verbose = verbose;
	}

	@Override
	public Ephemeris getEphemeris(String id, Observer observer, int days) throws RTException {
		
		synchronized (ephemerisList) {
			
			Ephemeris result = ephemerisList.get(id);
			if (result == null){
				boolean belongsToThisCategory = objectBelongsToThisCathegory(id, observer);
				if (belongsToThisCategory){
					result = new EphemerisMajorPlanetAndMoon(id, observer, days, verbose);
					ephemerisList.put(id, result);
					LogUtil.info(this, "EphemerisTracerLibNova.EphemerisCalculatorMajorPlanetAndMoon. Object[" + id + "] Ephemeris built and cached!!!");
				}else{
					LogUtil.info(this, "EphemerisTracerLibNova.EphemerisCalculatorMajorPlanetAndMoon. Object[" + id + "] does not belong to this cathegory");
					result = null;
				}
			}else{
				LogUtil.info(this, "EphemerisTracerLibNova.EphemerisCalculatorMajorPlanetAndMoon. Object[" + id + "] Ephemeris found in cache!!!");
			}
			
			return result;
		}
	}
	
	private boolean objectBelongsToThisCathegory(String id, Observer observer){
		try{
			Catalogue catalogue = new Catalogue(observer.getLongitude(), observer.getLatitude(), observer.getAltitude());
			ObjInfo objInfo = catalogue.getObject(id, ObjCategory.MajorPlanetAndMoon);
			return (objInfo != null);
		}catch(Exception ex){
			LogUtil.severe(this, "EphemerisTracerLibNova.EphemerisCalculatorMajorPlanetAndMoon. Error: " + ex.getMessage());
			return false;
		}
		
	}


}
