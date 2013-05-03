package eu.gloria.rt.ephemeris;

import java.util.Date;
import java.util.Hashtable;

import eu.gloria.rt.catalogue.Catalogue;
import eu.gloria.rt.catalogue.ObjCategory;
import eu.gloria.rt.catalogue.ObjInfo;
import eu.gloria.rt.catalogue.Observer;
import eu.gloria.rt.catalogue.ResolverCatalogue;
import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Epoch;
import eu.gloria.tools.log.LogUtil;

/**
 * Ephemeris Calculator for object cathegories: MinorPlanetOrAsteroid, OutsideSSystemObj, MajorPlanetAndMoon.
 * 
 * @author jcabello
 *
 */
public class EphemerisCalculator {
	
	/**
	 * Object Names resolvers (key=Object Category).
	 */
	private Hashtable<ObjCategory, IEphemerisCalculator> calculators;
	
	private Observer observer;
	private int days;
	private boolean verbose;
	
	public EphemerisCalculator(Observer observer, int days, boolean verbose){
		
		this.observer = observer;
		this.days = days;
		this.verbose = verbose;
		
		this.calculators = new Hashtable<ObjCategory, IEphemerisCalculator>();
		//this.calculators.put(ObjCategory.MinorPlanetOrAsteroid, new EphemerisCalculatorMinorPlanetOrAsteroid(verbose));
		this.calculators.put(ObjCategory.OutsideSSystemObj, new EphemerisCalculatorOutsideSSystemObj(verbose));
		this.calculators.put(ObjCategory.MajorPlanetAndMoon, new EphemerisCalculatorMajorPlanetAndMoon(verbose));
	
	}
	
	public EphemerisData getObjectInfo(String id, Date date) throws RTException {
		
		EphemerisData result = null;
		Ephemeris ephemeris = getEphemeris(id);
		if (ephemeris != null){
			result = ephemeris.getObjectInfo(date);
		}
		
		return result;
	}
	
	public EphemerisData getObjectInfo(String id, ObjCategory category, Date date) throws RTException {
		
		EphemerisData result = null;
		Ephemeris ephemeris = getEphemeris(id, category);
		if (ephemeris != null){
			result = ephemeris.getObjectInfo(date);
		}
		
		return result;
	}

	public Ephemeris getEphemeris(String id, ObjCategory category) throws RTException {
		
		try{
			
			IEphemerisCalculator calculator = calculators.get(category);
			if (calculator == null){
				throw new RTException("Unsupported category");
			}else{
				return calculator.getEphemeris(id, observer, days);
			}
			
		}catch(Exception ex){
			
			String[] names = {
					"Category",
					"ID"
			};
			
			String[] values = {
					category.toString(),
					id
			};
			
			LogUtil.severe(this, " EphemerisTracer.EphemerisCalculator.getEphemeris()." + LogUtil.getLog(names, values) + ". Error:" + ex.getMessage());
			ex.printStackTrace();
			
			return null;
		}

	}
	
	public Ephemeris getEphemeris(String id) throws RTException {
		
		Ephemeris result = null;
		
		Catalogue catalogue = new Catalogue(observer.getLongitude(), observer.getLatitude(), observer.getAltitude());
		ObjInfo objInfo = catalogue.getObject(id);
		if (objInfo != null){
			if (objInfo.getCategory() == ObjCategory.MajorPlanetAndMoon){
				result = getEphemeris(id, ObjCategory.MajorPlanetAndMoon);
			}else if (objInfo.getCategory() == ObjCategory.MinorPlanetOrAsteroid){
				result = getEphemeris(id, ObjCategory.MinorPlanetOrAsteroid);
			}else{
				result = getEphemeris(id, ObjCategory.OutsideSSystemObj);
			}
		}
		
		return result;
	}

}
