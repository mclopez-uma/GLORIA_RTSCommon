package eu.gloria.rt.catalogue;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import eu.gloria.rt.catalogue.libnova.ResolverCatalogueLibNova;
import eu.gloria.rt.catalogue.mpc.ResolverCatalogueMPC;
import eu.gloria.rt.catalogue.sesame.ResolverCatalogueSesame;
import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Epoch;
import eu.gloria.tools.conversion.DegreeFormat;
import eu.gloria.tools.log.LogUtil;

/**
 * Catalogue implementation.
 * 
 * Contains Object Name resolver in order to retrieve the current RADEC position in J2000:
 * 
 * - Minor Planet Center for minor planets and asteroids.
 * - Sesame for Solar System external objects.
 * - LibNova for Major plantes and moon.
 * 
 * @author jcabello
 *
 */
public class Catalogue {
	
	/**
	 * Object Names resolvers (key=Object Category).
	 */
	private Hashtable<ObjCategory, ResolverCatalogue> resolvers;
	
	private ResolverCatalogueMPC resolverMPC;
	private ResolverCatalogueSesame resolverSesame;
	private ResolverCatalogueLibNova resolverLibNova;
	
	private double longitude;
	private double latitude;
	private double altitude;
	
	/**
	 * Constructor.
	 */
	public Catalogue(double longitude, double latitude, double altitude){
		
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
		
		Observer observer = new Observer();
		observer.setLongitude(this.longitude);
		observer.setLatitude(this.latitude);
		observer.setAltitude(this.altitude);
		
//		resolverMPC = new ResolverCatalogueMPC(observer);
		resolverSesame = new ResolverCatalogueSesame();
		resolverLibNova = new ResolverCatalogueLibNova(longitude, latitude);
		
		resolvers = new Hashtable<ObjCategory, ResolverCatalogue>();
//		resolvers.put(ObjCategory.MinorPlanetOrAsteroid, resolverMPC);
		resolvers.put(ObjCategory.OutsideSSystemObj, resolverSesame);
		resolvers.put(ObjCategory.MajorPlanetAndMoon, resolverLibNova);
	}
	
	/**
	 * Retrieves Object information by Object name.
	 * @param id Object name.
	 * @return Object information, null if it cannot be found.
	 * @throws RTException In error case.
	 */
	public ObjInfo getObject(String id) throws RTException{
		
		ObjInfo result = null;
		String catalogueName = "unknown";
		
		try{
			
			catalogueName = this.resolverLibNova.getCatalogueName();
			result = this.resolverLibNova.getObject(id, Epoch.J2000);
			
			if (result == null && this.resolverSesame != null){
				catalogueName = this.resolverSesame.getCatalogueName();
				result = this.resolverSesame.getObject(id, Epoch.J2000);
			}
			
			if (result == null &&  this.resolverMPC != null){
				catalogueName = this.resolverMPC.getCatalogueName();
				result = this.resolverMPC.getObject(id, Epoch.J2000);
			}
			
			
		}catch(Exception ex){
			
			String[] names = {
					"CatalogueName",
					"ID"
			};
			
			String[] values = {
					catalogueName,
					id
			};
			
			LogUtil.severe(this, "Catalogue.getObject()." + LogUtil.getLog(names, values) + ". Error:" + ex.getMessage());
			
		}
		
		return result;
		
	
		/*for (ResolverCatalogue resolver : resolvers.values()) {
			
			ObjInfo result = null;
			
			try{
				
				result = resolver.getObject(id, Epoch.J2000);
				
			}catch(Exception ex){
				
				String[] names = {
						"CatalogueName",
						"ID"
				};
				
				String[] values = {
						resolver.getCatalogueName(),
						id
				};
				
				LogUtil.severe(this, "Catalogue.getObject()." + LogUtil.getLog(names, values) + ". Error:" + ex.getMessage());
				
			}
			
			if (result != null) return result;
			
		}
		
		return null;*/
	}
	
	/**
	 * Retrieves Object information by Object name.
	 * @param id Object name.
	 * @param date Date.
	 * @return Object information, null if it cannot be found.
	 * @throws RTException In error case.
	 */
	public ObjInfo getObject(String id, Date date) throws RTException{
		
			
		ObjInfo result = null;
		String catalogueName = "unknown";
		
		try{
			
			catalogueName = this.resolverLibNova.getCatalogueName();
			result = this.resolverLibNova.getObject(id, date, Epoch.J2000);
			
			if (result == null && this.resolverSesame != null ){
				catalogueName = this.resolverSesame.getCatalogueName();
				result = this.resolverSesame.getObject(id, date, Epoch.J2000);
			}
			
			if (result == null && this.resolverMPC != null ){
				catalogueName = this.resolverMPC.getCatalogueName();
				result = this.resolverMPC.getObject(id, date, Epoch.J2000);
			}
			
			
		}catch(Exception ex){
			
			String[] names = {
					"CatalogueName",
					"ID"
			};
			
			String[] values = {
					catalogueName,
					id
			};
			
			LogUtil.severe(this, "Catalogue.getObject()." + LogUtil.getLog(names, values) + ". Error:" + ex.getMessage());
			
		}
		
		return result;
		
		/*for (ResolverCatalogue resolver : resolvers.values()) {
			
			ObjInfo result = null;
			
			try{
				
				result = resolver.getObject(id, date, Epoch.J2000);
				
			}catch(Exception ex){
				
				String[] names = {
						"CatalogueName",
						"ID"
				};
				
				String[] values = {
						resolver.getCatalogueName(),
						id
				};
				
				LogUtil.severe(this, "Catalogue.getObject()." + LogUtil.getLog(names, values) + ". Error:" + ex.getMessage());
				
			}
			
			if (result != null) return result;
			
		}
		
		return null;*/
	}
	
	
	/**
	 * Retrieves Object information by Object name.
	 * @param id Object name.
	 * @param category Object category.
	 * @return Object information, null if it cannot be found.
	 * @throws RTException In error case.
	 */
	public ObjInfo getObject(String id, ObjCategory category) throws RTException{
		
		try{
			
			ResolverCatalogue resolver = resolvers.get(category);
			if (resolver == null){
				throw new RTException("Unsupported category");
			}else{
				return resolver.getObject(id, Epoch.J2000);
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
			
			LogUtil.severe(this, "Catalogue.getObject()." + LogUtil.getLog(names, values) + ". Error:" + ex.getMessage());
			ex.printStackTrace();
			
			return null;
		}
		
	}
	
	/**
	 * Retrieves Object information by Object name.
	 * @param id Object name.
	 * @param category Object category.
	 * @param date Date
	 * @return Object information, null if it cannot be found.
	 * 
	 * @throws RTException In error case.
	 */
	public ObjInfo getObject(String id, ObjCategory category, Date date) throws RTException{
		
		try{
			
			ResolverCatalogue resolver = resolvers.get(category);
			if (resolver == null){
				throw new RTException("Unsupported category");
			}else{
				return resolver.getObject(id, date, Epoch.J2000);
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
			
			LogUtil.severe(this, "Catalogue.getObject()." + LogUtil.getLog(names, values) + ". Error:" + ex.getMessage());
			ex.printStackTrace();
			
			return null;
		}
		
	}
	
	public final static void main(String[] args) throws Exception {
		
		Catalogue cat = new Catalogue(-6.734061 , 37.104019, 0);
		ObjInfo info = cat.getObject("HIP52313");
		info = cat.getObject("Haumea");
		info = cat.getObject("sdldsjflk");
		
		System.out.println("RA=" + info.getPosition().getRaString(DegreeFormat.HHMMSS));
		System.out.println("DEC=" + info.getPosition().getDecString(DegreeFormat.DDMMSS));
		
	}

}
