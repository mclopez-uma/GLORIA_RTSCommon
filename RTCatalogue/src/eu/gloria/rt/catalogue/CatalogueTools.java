package eu.gloria.rt.catalogue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import eu.gloria.rt.catalogue.libnova.GmtOffset;
import eu.gloria.rt.catalogue.libnova.LibNovaAltaz;
import eu.gloria.rt.catalogue.libnova.LibNovaAngularDistance;
import eu.gloria.rt.catalogue.libnova.LibNovaJNI;
import eu.gloria.rt.catalogue.libnova.LibNovaLibraryLoader;
import eu.gloria.rt.catalogue.libnova.LibNovaObserver;
import eu.gloria.rt.catalogue.libnova.LibNovaRTSInfo;
import eu.gloria.rt.catalogue.libnova.LibNovaRaDecJ2000;
import eu.gloria.rt.catalogue.libnova.LibNovaReturnedInfo;
import eu.gloria.rt.catalogue.libnova.LibNovaZoneDate;
import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Altaz;
import eu.gloria.rt.unit.Radec;
import eu.gloria.tools.time.DateTools;

/**
 * Catalogue class tool.
 * 
 * @author jcabello
 *
 */
public class CatalogueTools {
	
	static{
		//Load Nova library
		try{
			LibNovaLibraryLoader.loadLibrary();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public static RTSInfo getSunRTSInfo(Observer observer, Date date) throws RTException{
	
		return getSunRTSInfo(observer, date, 0, 0);
		
	}
	
	public static RTSInfo getSunRTSInfo(Observer observer, Date date, int riseOffsetSecs, int setOffsetSecs) throws RTException{
		
		try{
			
			Date trunkDate = DateTools.trunk(date, "yyyyMMdd");
			
			//TODO: It is a SIMULATION.
			/*result = new RTSInfo();
			String yyyyMMdd = DateTools.getDate(date, "yyyyMMdd");
			result.setRise(DateTools.getDate(yyyyMMdd + "063015", "yyyyMMddHHmmss"));
			result.setTransit(DateTools.getDate(yyyyMMdd + "120145", "yyyyMMddHHmmss"));
			result.setSet(DateTools.getDate(yyyyMMdd + "211530", "yyyyMMddHHmmss"));*/
			
			Date currentDate = trunkDate;
			GmtOffset utcOffset = new GmtOffset(currentDate);
			LibNovaZoneDate novaZoneDate = new LibNovaZoneDate();
			novaZoneDate.gmtOff = utcOffset.getOffsetSeconds(); // 3600 segs == 1 hour
			
			novaZoneDate.year = currentDate.getYear() + 1900;
			//System.out.println("**year=" + novaZoneDate.year);
			novaZoneDate.month = currentDate.getMonth()+1;
			//System.out.println("**month=" + novaZoneDate.month);
			novaZoneDate.day = currentDate.getDate();
			//System.out.println("**day=" + novaZoneDate.day);
			novaZoneDate.hours = currentDate.getHours();
			//System.out.println("**hours=" + novaZoneDate.hours);
			novaZoneDate.minutes = currentDate.getMinutes();
			//System.out.println("**minutes=" + novaZoneDate.minutes);
			novaZoneDate.seconds = currentDate.getSeconds();
			//System.out.println("**seconds=" + novaZoneDate.seconds);
			
			String objName = "sun";
			
			LibNovaRTSInfo objRTS = new LibNovaRTSInfo();
			
			LibNovaReturnedInfo returnedInfo = new LibNovaReturnedInfo();
			
			LibNovaJNI jni = new LibNovaJNI();
			
			LibNovaObserver observerNova = new LibNovaObserver();
			observerNova.altitude = observer.getAltitude();
			observerNova.latitude = observer.getLatitude();
			observerNova.longitude = observer.getLongitude();
			
			jni.getRTS(observerNova, novaZoneDate, objName, objRTS, returnedInfo);
			
			if (returnedInfo.error != 0){
				
				return null;
				
			}else{
				
				RTSInfo result = new RTSInfo();
				result.setRise(objRTS.getRise());
				result.setTransit(objRTS.getTransit());
				result.setSet(objRTS.getSet());
				
				if (riseOffsetSecs != 0){
					result.setRise(DateTools.increment(result.getRise(), Calendar.SECOND, riseOffsetSecs));
				}
				
				if (setOffsetSecs != 0){
					result.setSet(DateTools.increment(result.getSet(), Calendar.SECOND, setOffsetSecs));
				}
				
				return result;
			}
			
		}catch(Throwable ex){
			RTException e = new RTException(ex.getMessage());
			throw e;
		}
		
	}
	
	
	public static Altaz getAltazByRadec(Observer observer, Date zoneDate, Radec radec)  throws RTException{
		
		try{
			
			LibNovaJNI nova = new LibNovaJNI();
			
			LibNovaObserver novaObserver = new LibNovaObserver();
			novaObserver.latitude = observer.getLatitude();
			novaObserver.longitude = observer.getLongitude();
			
			GmtOffset utcOffset = new GmtOffset(zoneDate);
			
			LibNovaZoneDate novaZoneDate = new LibNovaZoneDate();
			novaZoneDate.year = zoneDate.getYear() + 1900;
			novaZoneDate.month = zoneDate.getMonth() + 1;
			novaZoneDate.day = zoneDate.getDate();
			novaZoneDate.hours = zoneDate.getHours();
			novaZoneDate.minutes = zoneDate.getMinutes();
			novaZoneDate.seconds = zoneDate.getSeconds();
			novaZoneDate.gmtOff = utcOffset.getOffsetSeconds();
			
			//System.out.println("      AstronomicalTimeFrameLocator.altaz::year:" + novaZoneDate.year);
			//System.out.println("      AstronomicalTimeFrameLocator.altaz::month:" + novaZoneDate.month);
			//System.out.println("      AstronomicalTimeFrameLocator.altaz::day:" + novaZoneDate.day);
			//System.out.println("      AstronomicalTimeFrameLocator.altaz::hours:" + novaZoneDate.hours);
			//System.out.println("      AstronomicalTimeFrameLocator.altaz::minutes:" + novaZoneDate.minutes);
			//System.out.println("      AstronomicalTimeFrameLocator.altaz::seconds:" + novaZoneDate.seconds);
			
			LibNovaRaDecJ2000 novaRadec = new LibNovaRaDecJ2000();
			novaRadec.dec = radec.getDecDecimal();
			novaRadec.ra = radec.getRaDecimal();
			
			LibNovaAltaz novaAltaz = new LibNovaAltaz();
			LibNovaReturnedInfo novaReturnedInfo = new LibNovaReturnedInfo();
			
			nova.getAltazByRadec(novaObserver, novaZoneDate, novaRadec, novaAltaz, novaReturnedInfo);
			
			if (novaReturnedInfo.error > 0){
				throw new Exception("Error retrieving the Altaz from Radec.");
			}else{
				Altaz result = new Altaz();
				result.setAlt(novaAltaz.alt);
				result.setAz(novaAltaz.az);
				return result;
			}
			
		}catch(Throwable ex){
			RTException e = new RTException(ex.getMessage());
			throw e;
		}
		
	}
	
	public static double getAngularDistance(Radec radec1, Radec radec2) throws RTException{
		
		try{
			
			LibNovaRaDecJ2000 novaRadec1 = new LibNovaRaDecJ2000();
			novaRadec1.dec = radec1.getDecDecimal();
			novaRadec1.ra = radec1.getRaDecimal();
			LibNovaRaDecJ2000 novaRadec2 = new LibNovaRaDecJ2000(); 
			novaRadec2.dec = radec2.getDecDecimal();
			novaRadec2.ra = radec2.getRaDecimal();
			LibNovaAngularDistance novaDistance = new LibNovaAngularDistance();
			LibNovaReturnedInfo novaReturnedInfo = new LibNovaReturnedInfo();
			
			LibNovaJNI nova = new LibNovaJNI();
			nova.getAngularDistance(novaRadec1, novaRadec2, novaDistance, novaReturnedInfo);
			
			if (novaReturnedInfo.error > 0){
				throw new Exception("Error calculating the angular distance.");
			}else{
				return novaDistance.degrees;
			}
			
		}catch(Throwable ex){
			RTException e = new RTException(ex.getMessage());
			throw e;
		}
		
		
	}

}
