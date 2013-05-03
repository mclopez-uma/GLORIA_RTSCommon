package eu.gloria.rt.catalogue.libnova;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LibNovaJNITest {

	public static LibNovaJNIObjInfo getObject(String id, Date day)
			throws Exception {

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(day);

			GmtOffset utcOffset = new GmtOffset(day);

			LibNovaJNIInput input = new LibNovaJNIInput();
			input.date = date;
			input.epoch = 2000;
			input.gmtOff = utcOffset.getOffsetSeconds(); // 3600 segs == 1 hour
			input.latitude = 37.2;
			input.longitude = -7.216667;
			input.name = id;

			LibNovaJNIObjInfo output = new LibNovaJNIObjInfo();

			LibNovaJNI jni = new LibNovaJNI();
			jni.getObjectInfo(input, output);

			if (output.found == 0) { // unfound
				System.out.println("No object found");
				return null;
			} else {

				System.out.println("[" + day + "]::Found Object: ra="
						+ output.ra + ", dec=" + output.dec);
				return output;

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public static void test2(String dateString) throws Exception{
		
		//LibNovaObserver observer, LibNovaZoneDate zoneDate, String objName, LibNovaRTSInfo objRTS, LibNovaReturnedInfo returnedInfo
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date currentDate = sdf.parse(dateString);
		
		LibNovaObserver novaObserver = new LibNovaObserver();
		novaObserver.latitude = 37.2;
		novaObserver.longitude = -7.216667;
		
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
		
		String objName = "jupiter";
		
		LibNovaRTSInfo objRTS = new LibNovaRTSInfo();
		
		LibNovaReturnedInfo returnedInfo = new LibNovaReturnedInfo();
		
		LibNovaJNI jni = new LibNovaJNI();
		
		LibNovaJNIObjInfo output = new LibNovaJNIObjInfo();
		
		LibNovaJNIInput input = new LibNovaJNIInput();
		input.date = dateString;
		input.epoch = 2000;
		input.gmtOff = utcOffset.getOffsetSeconds(); // 3600 segs == 1 hour
		input.latitude = 37.2;
		input.longitude = -7.216667;
		input.name = objName;
		
		jni.getObjectInfo(input, output);

		if (output.found == 0) { // unfound
			System.out.println("No object found");
		} else {

			System.out.println("[" + currentDate + "]::Found Object: ra=" + output.ra + ", dec=" + output.dec);
			
			LibNovaRaDecJ2000 radec = new LibNovaRaDecJ2000();
			radec.dec = output.dec;
			radec.ra = output.ra;
			LibNovaAltaz altaz = new LibNovaAltaz();
			
			jni.getAltazByRadec(novaObserver, novaZoneDate, radec, altaz, returnedInfo);
			
			System.out.println("[" + currentDate + "]::Alt=" + altaz.alt);
			
			
			jni.getRTS(novaObserver, novaZoneDate, objName, objRTS, returnedInfo);
			
			System.out.println("error=" + returnedInfo.error);
			System.out.print("rise=" + objRTS.getRise());
			System.out.print("transit=" + objRTS.getTransit());
			System.out.print("set=" + objRTS.getSet());

		}
		
		
		
		
		
	}

	public static void test1() {

		try {

			// Altitude
			LibNovaObserver novaObserver = new LibNovaObserver();
			novaObserver.latitude = 37.2;
			novaObserver.longitude = -7.216667;

			String dateS = "20130226000000";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = sdf.parse(dateS);
			
			GmtOffset utcOffset = new GmtOffset(date);

			LibNovaJNIInput input = new LibNovaJNIInput();
			//input.date = dateS;
			input.epoch = 2000;
			input.gmtOff = utcOffset.getOffsetSeconds(); // 3600 segs == 1 hour
			input.latitude = 37.2;
			input.longitude = -7.216667;
			input.name = "moon";

			GregorianCalendar calendar;
			calendar = new GregorianCalendar();
			calendar.setTime(date);
			
			LibNovaJNIObjInfo output = new LibNovaJNIObjInfo();
			LibNovaJNI jni = new LibNovaJNI();
			
			LibNovaRaDecJ2000 novaRadec = new LibNovaRaDecJ2000();
			
			LibNovaZoneDate novaZoneDate = new LibNovaZoneDate();
			novaZoneDate.gmtOff = utcOffset.getOffsetSeconds(); // 3600 segs == 1 hour
			
			LibNovaAltaz novaAltaz = new LibNovaAltaz();
			LibNovaReturnedInfo novaReturnedInfo = new LibNovaReturnedInfo();

			for (int x = 0; x < 1440; x++) {
				
				calendar.add(Calendar.MINUTE, 1);
				Date currentDate = calendar.getTime();
				
				dateS = sdf.format(currentDate);
				input.date = dateS;
				
				jni.getObjectInfo(input, output);
				
				novaRadec.dec = output.dec;
				novaRadec.ra = output.ra;
				
				// ---------------------------

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

				jni.getAltazByRadec(novaObserver, novaZoneDate, novaRadec,
						novaAltaz, novaReturnedInfo);

				System.out.println("[" + currentDate + "::Found Object: ra=" + output.ra
						+ ", dec=" + output.dec + ", alt=" + novaAltaz.alt);
				
			}

			

			
			

			

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}
	
	public static void test3() throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		for (int x = 0; x < 100; x++){

			test2(sdf.format(calendar.getTime()));
			calendar.add(Calendar.MINUTE, 10);
			//System.gc();
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		LibNovaLibraryLoader.loadLibrary();

		//test2("20130316052521");
		
		test3();

		/*
		 * System.out.println("Date: " + new Date()); for (int x = 0; x < 672;
		 * x++) {
		 * 
		 * // System.out.println("Count=" + x);
		 * 
		 * LibNovaJNI lib = new LibNovaJNI();
		 * 
		 * // Altitude LibNovaObserver novaObserver = new LibNovaObserver();
		 * novaObserver.latitude = 37.2; novaObserver.longitude = -7.216667;
		 * 
		 * 
		 * // 2013/2/18 12:45 LibNovaZoneDate novaZoneDate = new
		 * LibNovaZoneDate(); novaZoneDate.year = 2013; novaZoneDate.month = 2;
		 * novaZoneDate.day = 18; novaZoneDate.hours = 12; novaZoneDate.minutes
		 * = 45; novaZoneDate.seconds = 0; novaZoneDate.gmtOff = 3600;
		 * 
		 * int targetNumber = 3;
		 * 
		 * for (int x1 = 0; x1 < (targetNumber + 1); x1++) { // Search the moon
		 * LibNovaJNIObjInfo objInfo = getObject("jupiter", new Date());
		 * 
		 * //Calculate de alt LibNovaRaDecJ2000 novaRadec = new
		 * LibNovaRaDecJ2000(); novaRadec.dec = objInfo.dec; novaRadec.ra =
		 * objInfo.ra; LibNovaAltaz novaAltaz = new LibNovaAltaz();
		 * LibNovaReturnedInfo novaReturnedInfo = new LibNovaReturnedInfo();
		 * lib.getAltazByRadec(novaObserver, novaZoneDate, novaRadec, novaAltaz,
		 * novaReturnedInfo);
		 * 
		 * System.out.println("ob1.alt=" + novaAltaz.alt); }
		 * 
		 * for (int x2 = 0; x2 < targetNumber; x2++) { // AngularDistance
		 * LibNovaRaDecJ2000 novaRadec1 = new LibNovaRaDecJ2000();
		 * novaRadec1.dec = 20.024712; novaRadec1.ra = 65.066636;
		 * LibNovaRaDecJ2000 novaRadec2 = new LibNovaRaDecJ2000();
		 * novaRadec2.dec = 20.919193; novaRadec2.ra = 64.996123;
		 * LibNovaAngularDistance novaDistance = new LibNovaAngularDistance();
		 * LibNovaReturnedInfo novaReturnedInfo = new LibNovaReturnedInfo();
		 * 
		 * lib.getAngularDistance(novaRadec1, novaRadec2, novaDistance,
		 * novaReturnedInfo);
		 * 
		 * // System.out.println("AngularDistance:: Value=" + //
		 * novaDistance.degrees + " Must be: 0.896916"); }
		 * 
		 * 
		 * 
		 * for (int x3 = 0; x3 < targetNumber; x3++) {
		 * 
		 * 
		 * 
		 * LibNovaRaDecJ2000 novaRadec = new LibNovaRaDecJ2000(); novaRadec.dec
		 * = 20.024712; novaRadec.ra = 65.066636;
		 * 
		 * LibNovaAltaz novaAltaz = new LibNovaAltaz();
		 * 
		 * LibNovaReturnedInfo novaReturnedInfo2 = new LibNovaReturnedInfo();
		 * lib.getAltazByRadec(novaObserver, novaZoneDate, novaRadec, novaAltaz,
		 * novaReturnedInfo2);
		 * 
		 * // System.out.println("ob1.alt=" + novaAltaz.alt); }
		 * 
		 * }
		 * 
		 * System.out.println("Date: " + new Date());
		 */

	}

}
