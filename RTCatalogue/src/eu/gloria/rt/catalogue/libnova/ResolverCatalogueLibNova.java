package eu.gloria.rt.catalogue.libnova;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;


import eu.gloria.rt.catalogue.ObjCategory;
import eu.gloria.rt.catalogue.ObjInfo;
import eu.gloria.rt.catalogue.RTSInfo;
import eu.gloria.rt.catalogue.ResolverCatalogue;
import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.exception.UnsupportedOpException;
import eu.gloria.rt.unit.Epoch;
import eu.gloria.rt.unit.Radec;
import eu.gloria.tools.conversion.DegreeFormat;
import eu.gloria.tools.log.LogUtil;
import eu.gloria.tools.time.DateTools;

/**
 * Resolver for libnova solution (Major planets and moon).
 * 
 * @author jcabello
 *
 */
public class ResolverCatalogueLibNova implements ResolverCatalogue {
	
	static{
		LibNovaLibraryLoader.loadLibrary();
	}
	
	private Vector<String> names;
	private double longitude;
	private double latitude;
	
	public ResolverCatalogueLibNova(double longitude, double latitude){
		
		this.longitude = longitude;
		this.latitude = latitude;
		
		names = new Vector<String>();
		names.add("moon");
		names.add("jupiter");
		names.add("mars");
		names.add("mercury");
		names.add("neptune");
		names.add("pluto");
		names.add("saturn");
		names.add("uranus");
		names.add("venus");
		names.add("sun");
		
	}

	@Override
	public ObjInfo getObject(String id, Epoch epoch) throws RTException {
		
		return getObject(id, new Date(), epoch);
	}

	@Override
	public ObjInfo getObject(String id, Date day, Epoch epoch) throws RTException {
		
		
		
		if (epoch != Epoch.J2000){
			throw new UnsupportedOpException("Only J2000 epoch");
		}
		
		try{
			if (!names.contains(id)) return null;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(day);
			
			GmtOffset utcOffset = new GmtOffset(day);
			
			String[] names = {
					"date",
					"epoch",
					"gmtOff",
					"longitude",
					"latitude",
					"objName",
					"PosRa",
					"PosDec"
			};
			
			String[] values = {
					date,
					"2000",
					String.valueOf(utcOffset.getOffsetSeconds()),
					String.valueOf(longitude),
					String.valueOf(latitude),
					id,
					"-",
					"-"
			};
			
			LogUtil.info(this, "ResolverCatalogueLibNova.getObject(). Resolving. " + LogUtil.getLog(names, values));
			
			LibNovaJNIInput input = new LibNovaJNIInput();
			input.date = date;
			input.epoch = 2000;
			input.gmtOff = utcOffset.getOffsetSeconds(); //3600 segs == 1 hour
			input.latitude = latitude;
			input.longitude = longitude;
			input.name = id;
			
			LibNovaJNIObjInfo output = new LibNovaJNIObjInfo();
			
			LibNovaJNI jni = new LibNovaJNI();
			jni.getObjectInfo(input, output);
			
			if (output.found == 0){ //unfound
				LogUtil.info(this, "ResolverCatalogueLibNova.getObject(). UnResolved. " + LogUtil.getLog(names, values));
				return null;
			}else{
				ObjInfo info = new ObjInfo();
				info.setCategory(ObjCategory.MajorPlanetAndMoon);
				info.setId(id);
				Radec pos = new Radec(output.ra, output.dec);
				pos.setEpoch(Epoch.J2000);
				info.setPosition(pos);
				//values[6] = pos.getRaString(DegreeFormat.HHMMSS);
				//values[7] = pos.getDecString(DegreeFormat.DDMMSS);
				
				values[6] = output.ra + "";
				values[7] = output.dec + "";
				
				LogUtil.info(this, "ResolverCatalogueLibNova.getObject(). Resolved. " + LogUtil.getLog(names, values));
				return info;
			}
		}catch(Throwable ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public String getCatalogueName() {
		return "libNovaJNI";
	}
	

}
