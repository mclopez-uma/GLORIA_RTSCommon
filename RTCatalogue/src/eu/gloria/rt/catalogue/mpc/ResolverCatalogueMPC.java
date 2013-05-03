package eu.gloria.rt.catalogue.mpc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import eu.gloria.rt.catalogue.ObjCategory;
import eu.gloria.rt.catalogue.ObjInfo;
import eu.gloria.rt.catalogue.Observer;
import eu.gloria.rt.catalogue.ResolverCatalogue;
import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Epoch;
import eu.gloria.rt.unit.Radec;
import eu.gloria.tools.conversion.DegreeFormat;
import eu.gloria.tools.file.FileUtil;
import eu.gloria.tools.time.DateTools;


/**
 * Resolver for Minor Planet Center repository.
 * 
 * http://www.minorplanetcenter.net/iau/MPEph/MPEph.html
 * 
 * @author jcabello
 *
 */
public class ResolverCatalogueMPC implements ResolverCatalogue {
	
	private static String URL = "http://scully.cfa.harvard.edu/cgi-bin/mpeph2.cgi";
	
	private Date lastAccess;
	
	private Observer observer;
	
	public ResolverCatalogueMPC(Observer observer){
		this.observer = observer;
	}
	

	@Override
	public ObjInfo getObject(String id, Epoch epoch) throws RTException {
		return getObject(id, new Date(), epoch);
	}

	@Override
	public ObjInfo getObject(String id, Date date, Epoch epoch)  throws RTException {
		
		Date now = new Date();
		if (lastAccess != null){
			Date minimalNewAccess = DateTools.increment(lastAccess, Calendar.MINUTE, 5);
			if (now.compareTo(minimalNewAccess) < 0){
				System.out.println((new Date()) + "ResolverCatalogueMPC.getObject. TOO soon (<5min) to access to MPC.");
				return null;
			}
		}
		lastAccess = now;
		
		
		/*System.out.println((new Date()) + "ResolverCatalogueMPC.accessing....sleep 5 seconds.");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		String response = httpReadResponse(id, date, epoch);
		
		MPCResponseParser parser = new MPCResponseParser(response, date);
		
		Radec radec = parser.getRadec();
		
		System.out.println((new Date()) + "ResolverCatalogueMPC.accessed.");
		
		if (radec == null){
			
			return null;
			
		}else{
			
			ObjInfo result = new ObjInfo();
			result.setCategory(ObjCategory.MinorPlanetOrAsteroid);
			result.setId(id);
			result.setPosition(radec);
			
			return result;
		}
		
	}
	
	@Override
	public String getCatalogueName() {
		return "MinorPlanetCenter";
	}
	
	private String httpReadResponse(String id, Date date, Epoch epoch) throws RTException{
		
		 HttpClient httpclient = new DefaultHttpClient();
	        
	        try {
	        	
	        	String dateS = "";
	        	if (date != null){
	        		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
	        		dateS = sd.format(date);
	        	}
	        	
	        	List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	        	formparams.add(new BasicNameValuePair("ty", "e")); //return efemerides
	        	formparams.add(new BasicNameValuePair("TextArea", id)); //name
	        	formparams.add(new BasicNameValuePair("i", "")); //Ephemeris interval **15
	        	formparams.add(new BasicNameValuePair("d",dateS)); //efemerides inicio: yyyy/MM/dd **
	        	formparams.add(new BasicNameValuePair("l", "1")); //numero de dias
	        	
	        	formparams.add(new BasicNameValuePair("m", "m")); //Display motions as min
	        	formparams.add(new BasicNameValuePair("adir", "S")); //westwards from the south meridian
	        	formparams.add(new BasicNameValuePair("e", "-2")); //format-> none
	        	
	        	formparams.add(new BasicNameValuePair("u", "d")); //Ephemeris interval units: d=days, h=hours, m=minutes **m
	        	formparams.add(new BasicNameValuePair("uto", "0")); //UT offset (hours) **1	
	        	formparams.add(new BasicNameValuePair("c", "")); //xxxx
	        	formparams.add(new BasicNameValuePair("long", String.valueOf(observer.getLongitude()))); //observer-longitude **
	        	formparams.add(new BasicNameValuePair("lat", String.valueOf(observer.getLatitude()))); //observer-latitude **
	        	formparams.add(new BasicNameValuePair("alt", String.valueOf(observer.getAltitude()))); //observer-altitude **
	        	//formparams.add(new BasicNameValuePair("long", "")); //observer-longitude **
	        	//formparams.add(new BasicNameValuePair("lat", "")); //observer-latitude **
	        	//formparams.add(new BasicNameValuePair("alt", "")); //observer-altitude **
	        	formparams.add(new BasicNameValuePair("raty", "a")); //xxxx
	        	formparams.add(new BasicNameValuePair("s", "c")); //Separate R.A. and Decl. coordinate motions
	        	formparams.add(new BasicNameValuePair("oed", "")); //xxxx
	        	formparams.add(new BasicNameValuePair("resoc", "")); //xxxx
	        	formparams.add(new BasicNameValuePair("tit", "")); //xxxx
	        	formparams.add(new BasicNameValuePair("bu", "")); //xxxx
	        	formparams.add(new BasicNameValuePair("ch", "c")); //xxxx
	        	formparams.add(new BasicNameValuePair("ce", "f")); //xxxx
	        	formparams.add(new BasicNameValuePair("js", "f")); //xxxx
	        	
	        	UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "ISO-8859-1");
	        	HttpPost httppost = new HttpPost(URL);
	        	httppost.setEntity(entity);
	        	
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity2 = response.getEntity();

	            String result = EntityUtils.toString(entity2);
	            
	            return result;
	        }catch(Exception ex){
	        	throw new RTException("Error accessing to MPC." + ex.getMessage());
	        } finally {
	            // When HttpClient instance is no longer needed,
	            // shut down the connection manager to ensure
	            // immediate deallocation of all system resources
	            httpclient.getConnectionManager().shutdown();
	        }
		
	}
	
	
	
	public final static void main(String[] args) throws Exception {
		
		Observer observer = new Observer();
		observer.setAltitude(0);
		observer.setLatitude(37.104019);
		observer.setLongitude(-6.734061);
		
		ResolverCatalogueMPC resolver = new ResolverCatalogueMPC(observer);
		//ObjInfo info = resolver.getObject("2012 DA14", Epoch.J2000);
		ObjInfo info = resolver.getObject("ceres", Epoch.J2000);
		
		System.out.println("RA=" + info.getPosition().getRaString(DegreeFormat.HHMMSS));
		System.out.print("DEC=" + info.getPosition().getDecString(DegreeFormat.DDMMSS));
		
		//Radec ra = new Radec(61.717171, 20.174977);
		//System.out.println("RA=" + ra.getRaString(DegreeFormat.HHMMSS));
		//System.out.print("DEC=" + ra.getDecString(DegreeFormat.DDMMSS));
		System.out.print("");
		
		
	}
	
	

}
