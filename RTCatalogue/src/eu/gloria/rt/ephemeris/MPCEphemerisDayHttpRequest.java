package eu.gloria.rt.ephemeris;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.catalina.util.DateTool;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import eu.gloria.rt.catalogue.Observer;
import eu.gloria.rt.catalogue.libnova.GmtOffset;
import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Epoch;
import eu.gloria.rt.unit.Radec;
import eu.gloria.tools.log.LogUtil;
import eu.gloria.tools.time.DateTools;

/**
 * HTTP access to MPC web site.
 *
 */
public class MPCEphemerisDayHttpRequest{
	
	private static String URL = "http://scully.cfa.harvard.edu/cgi-bin/mpeph2.cgi";
	
	private boolean validContent;
	private List<EphemerisData> items;
	private Observer observer;
	private boolean verbose;
	
	
	public static void main(String[] args) throws Exception{		
		
		/*FileInputStream fis = new FileInputStream("C:\\Users\\jcabello.ISA\\Desktop\\MPC\\resultado.htm");
		
		String content = readContent(fis);
		System.out.println(content);
		
		Date date = DateTools.getDate("2013/01/03", "yyyy/MM/dd");
		
		Observer observer = new Observer();
		
		MPCEphemerisDayHttpRequest httpRequest = new MPCEphemerisDayHttpRequest(observer, "2012 DA14", date);
		
		System.out.println("Valid content: " + httpRequest.isValidContent());
		for (int x = 0; x < httpRequest.getItems().size(); x++){
			System.out.print(httpRequest.getItems().get(x));
		}*/
		
		
		Observer observer = new Observer();
		observer.setLatitude(37.2);
		observer.setLongitude(-7.21666);
		MPCEphemerisDayHttpRequest requ =  new MPCEphemerisDayHttpRequest(observer, "Ceres", new Date(), true);
	}	
	
	private static String readContent( FileInputStream dis) throws Exception{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte buffer[] = new byte[1024];
		
		int readBytes = dis.read(buffer);
		while (readBytes > 0){
			
			baos.write(buffer, 0, readBytes);
			readBytes = dis.read(buffer);
		}
		
		String result = new String(baos.toByteArray(), "UTF8");
		return result;

	}
	
	public MPCEphemerisDayHttpRequest(Observer observer, String id, Date date, boolean verbose) throws RTException{
		
		try{
			
			this.observer = observer;
			this.items = new ArrayList<EphemerisData>();
			this.verbose = verbose;
			
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.constructor. BEGIN. Object[" + id + "]. Date[" + date + "]. PROCESS BEGIN");
			
			build(observer, id, date);
			
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.constructor. BEGIN. Object[" + id + "]. Date[" + date + "]. PROCESS END");
			
			
		}catch(RTException ex){
			throw ex;
		}catch(Exception ex){
			validContent = false;
			throw new RTException(ex);
		}
		
	}
	
	/*public MPCEphemerisDayHttpRequest(Observer observer, String id, String content, Date date) throws RTException{
		processContent(observer, id, content, date);
	}*/
	
	private void build(Observer observer, String id, Date date) throws RTException{
		
		try{
			
			//Days: prev, current, pos
			Date prevDate = DateTools.increment(date, Calendar.DATE, -1);
			Date posDate  = DateTools.increment(date, Calendar.DATE, 1);
			
			//Access to MPC page.
			String[] content = new String[3];
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.build. MCP HTTP ACCESS. Object[" + id + "]. prevDate[" + prevDate + "]. BEGIN");
			content[0] = httpReadResponse(id, prevDate);
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.build. MCP HTTP ACCESS. Object[" + id + "]. prevDate[" + prevDate + "]. END");
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.build. MCP HTTP ACCESS. Object[" + id + "]. currentDate[" + date + "]. BEGIN");
			content[1] = httpReadResponse(id, date);
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.build. MCP HTTP ACCESS. Object[" + id + "]. currentDate[" + date + "]. END");
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.build. MCP HTTP ACCESS. Object[" + id + "]. posDate[" + posDate + "]. BEGIN");
			content[2] = httpReadResponse(id, posDate);
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.build. MCP HTTP ACCESS. Object[" + id + "]. posDate[" + posDate + "]. END");
			
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.build. PARSE TEXT. Object[" + id + "]. BEGIN");
			
			List<EphemerisData>  tmpList  =  new ArrayList<EphemerisData>();
			
			//MPC rows for prevDate
			String[] rows = getRows(content[0], prevDate);
			tmpList.addAll(getValidData(date, rows));
			
			//MPC rows for currentDate
			rows = getRows(content[1], date);
			tmpList.addAll(getValidData(date, rows));
			
			//MPC rows for posDate
			rows = getRows(content[2], posDate);
			tmpList.addAll(getValidData(date, rows));
			
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.build. PARSE TEXT. Object[" + id + "]. END");
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.build. FILTERING TIME SLOTS. Object[" + id + "]. BEGIN");
			
			Date lastDate = null;
			boolean addItem = false;
			
			for (int x = 0; x <  tmpList.size(); x++){
				
				addItem = false;
				
				if (lastDate == null){
					addItem = true;
				}else if (lastDate.compareTo(tmpList.get(x).getDate()) < 0){
					addItem = true;
				}
					
				if (addItem) {
					items.add(tmpList.get(x));
					lastDate = tmpList.get(x).getDate();
				}
			}
			
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.build. FILTERING TIME SLOTS. Object[" + id + "]. END");
			
		}catch(RTException ex){
			throw ex;
		}catch(Exception ex){
			validContent = false;
			throw new RTException(ex);
		}
		
	}
	
	private List<EphemerisData> getValidData(Date localDate, String[] rows) throws RTException{
		
		try{
			List<EphemerisData> result = new ArrayList<EphemerisData>();
			
			int gmtOffset = DateTools.getGMTOffsetHour();
			Date localDateIni = DateTools.trunk(localDate, "yyyyMMdd");
			Date localDateEnd = DateTools.increment(localDateIni, Calendar.DATE, 1);
			localDateEnd = DateTools.increment(localDateEnd, Calendar.MINUTE, -1);
			
			//----------------------
			
			Radec radec = new Radec();
			radec.setEpoch(Epoch.J2000);
			for (int x = 0; x < rows.length; x++){
				
				rows[x] = trimSpaces(rows[x]);
				
				String[] columns = rows[x].split(" "); 
				
				Date tmpDate = DateTools.getDate(columns[0] + columns[1] + columns[2] + columns[3], "yyyyMMddHHmmss"); //in the right offset
				tmpDate = DateTools.increment(tmpDate, Calendar.HOUR, gmtOffset);
				
				if (tmpDate.compareTo(localDateIni) >= 0 && tmpDate.compareTo(localDateEnd) <= 0){ //Right day
					
					radec.setDec(Integer.parseInt(columns[7].replace("+", "")), Integer.parseInt(columns[8].replace("+", "")), Double.parseDouble(columns[9].replace("+", "")));
					radec.setRa(Integer.parseInt(columns[4].replace("+", "")), Integer.parseInt(columns[5].replace("+", "")), Double.parseDouble(columns[6].replace("+", "")));
				
					EphemerisData item = new EphemerisData();
					item.setDate(tmpDate);
					item.setRa(radec.getRaDecimal());
					item.setDec(radec.getDecDecimal());
					item.setAz(Double.parseDouble(columns[17].replace("+", "")));
					item.setAlt(Double.parseDouble(columns[18].replace("+", "")));
				
					result.add(item);
					
				}
				
			}
			//----------------------
			
			
			return result;
			
		}catch(RTException ex){
			throw ex;
		}catch(Exception ex){
			validContent = false;
			throw new RTException(ex);
		}
	
	}
	
	private String[] getRows(String content, Date date) throws RTException{
		
		try{
			
			if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.getRows. TEXT ROWS ISOLATING. BEGIN");
			
			if (content.indexOf("No object found") != -1){ //Not object found
				validContent = false;
				throw new RTException("The object does not exist in MPC.");
			} else if (content.indexOf("Error") != -1){ //Error response
				validContent = false;
				throw new RTException("Error en MPC access. HTTP response=" + content);
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				String yearString = sdf.format(date);
				
				int indexOfBegin = content.indexOf("<pre>") + "<pre>".length();
				int indexOfEnd =content.indexOf("</pre>");
				String data = content.substring(indexOfBegin, indexOfEnd);
				
				int yearBegin = data.indexOf(yearString);
				data = data.substring(yearBegin);
				if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.getRows. DATA:[" + data + "]");
				
				String[] rows = data.split("\n"); 
				
				if (verbose) LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.getRows. TEXT ROWS ISOLATING. END");
				
				return rows;
				
			}
			
		}catch(RTException ex){
			throw ex;
		}catch(Exception ex){
			validContent = false;
			throw new RTException(ex);
		}
		
	}
	
	
	

	
	/*private void processContent(Observer observer, String id, String content, Date date) throws RTException{
		
		try{
			
			this.content = content;
			this.date = date;
			this.validContent = true;
			this.items = new ArrayList<EphemerisData>();
			this.observer = observer;
			
			if (content.indexOf("No object found") != -1){ //Not object found
				validContent = false;
				throw new RTException("The object does not exist in MPC.");
			} else if (content.indexOf("Error") != -1){ //Error response
				validContent = false;
				throw new RTException("Error en MPC access. HTTP response=" + content);
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				String yearString = sdf.format(date);
				
				int indexOfBegin = content.indexOf("<pre>") + "<pre>".length();
				int indexOfEnd =content.indexOf("</pre>");
				String data = content.substring(indexOfBegin, indexOfEnd);
				
				int yearBegin = data.indexOf(yearString);
				data = data.substring(yearBegin);
				System.out.println("[" + data + "]");
				
				String[] rows = data.split("\n"); 
				String prevDay = null;
				if (rows != null){
					
					Radec radec = new Radec();
					radec.setEpoch(Epoch.J2000);
					for (int x = 0; x < rows.length; x++){
						
						rows[x] = trimSpaces(rows[x]);
						
						String[] columns = rows[x].split(" "); 
						
						//If day change... break;
						if (prevDay != null && !prevDay.equals(columns[2])) {
							break; //The day changes->stop
						}
						prevDay = columns[2];
						
						Date tmpDate = DateTools.getDate(columns[0] + columns[1] + columns[2] + columns[3], "yyyyMMddHHmmss"); //in the right offset
						
						radec.setDec(Integer.parseInt(columns[7].replace("+", "")), Integer.parseInt(columns[8].replace("+", "")), Double.parseDouble(columns[9].replace("+", "")));
						radec.setRa(Integer.parseInt(columns[4].replace("+", "")), Integer.parseInt(columns[5].replace("+", "")), Double.parseDouble(columns[6].replace("+", "")));
						
						EphemerisData item = new EphemerisData();
						item.setDate(tmpDate);
						item.setRa(radec.getRaDecimal());
						item.setDec(radec.getDecDecimal());
						item.setAz(Double.parseDouble(columns[17].replace("+", "")));
						item.setAlt(Double.parseDouble(columns[18].replace("+", "")));
						
						System.out.println((new Date()) + "MPCEphemerisDayHttpRequest.processContent. EphemerisData=>" + item.toString());
						
						items.add(item);
						
					}
				}
			}
			
		}catch(RTException ex){
			throw ex;
		}catch(Exception ex){
			validContent = false;
			throw new RTException(ex);
		}
		
	}*/
	
	
	
	private String httpReadResponse(String id, Date date) throws RTException{
		
		 HttpClient httpclient = new DefaultHttpClient();
	        
	        try {
	        	
	        	String dateS = "";
	        	if (date != null){
	        		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
	        		dateS = sd.format(date);
	        	}
	        	
	        	//GmtOffset offset = new GmtOffset(new Date());
	        	
	        	List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	        	formparams.add(new BasicNameValuePair("ty", "e")); //return efemerides
	        	formparams.add(new BasicNameValuePair("TextArea", id)); //name
	        	formparams.add(new BasicNameValuePair("i", "15")); //Ephemeris interval **15
	        	formparams.add(new BasicNameValuePair("d",dateS)); //efemerides inicio: yyyy/MM/dd **
	        	formparams.add(new BasicNameValuePair("l", "")); //numero de dias
	        	
	        	formparams.add(new BasicNameValuePair("m", "m")); //Display motions as min
	        	formparams.add(new BasicNameValuePair("adir", "S")); //westwards from the south meridian
	        	formparams.add(new BasicNameValuePair("e", "-2")); //format-> none
	        	
	        	formparams.add(new BasicNameValuePair("u", "m")); //Ephemeris interval units: d=days, h=hours, m=minutes **m
	        	//String offsetHour = String.valueOf(offset.getOffsetHour());
	        	//formparams.add(new BasicNameValuePair("uto", offsetHour)); //UT offset (hours) **1	
	        	formparams.add(new BasicNameValuePair("uto", "")); //UT offset (hours) 
	        	formparams.add(new BasicNameValuePair("c", "")); //xxxx
	        	formparams.add(new BasicNameValuePair("long", String.valueOf(observer.getLongitude()))); //observer-longitude **
	        	formparams.add(new BasicNameValuePair("lat", String.valueOf(observer.getLatitude()))); //observer-latitude **
	        	formparams.add(new BasicNameValuePair("alt", String.valueOf(observer.getAltitude()))); //observer-altitude **
	        	formparams.add(new BasicNameValuePair("raty", "a")); //xxxx
	        	formparams.add(new BasicNameValuePair("s", "c")); //Separate R.A. and Decl. coordinate motions
	        	formparams.add(new BasicNameValuePair("oed", "")); //xxxx
	        	formparams.add(new BasicNameValuePair("resoc", "")); //xxxx
	        	formparams.add(new BasicNameValuePair("tit", "")); //xxxx
	        	formparams.add(new BasicNameValuePair("bu", "")); //xxxx
	        	formparams.add(new BasicNameValuePair("ch", "c")); //xxxx
	        	formparams.add(new BasicNameValuePair("ce", "f")); //xxxx
	        	formparams.add(new BasicNameValuePair("js", "f")); //xxxx
	        	
	        	//if ("1".equals("1")) throw new RuntimeException("Toma ya!!!");
	        	
	        	
	        	LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.httpReadResponse. OUTDOOR ACCESS MPC. BEGIN. [id=" + id + "]. [date=" + date + "]");
	        	/*Thread.sleep(2000);*/
	        	
	        	UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "ISO-8859-1");
	        	HttpPost httppost = new HttpPost(URL);
	        	httppost.setEntity(entity);
	        	
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity2 = response.getEntity();

	            String result = EntityUtils.toString(entity2);
	            
	            LogUtil.info(this, "EphemerisTracerMPC.MPCEphemerisDayHttpRequest.httpReadResponse. OUTDOOR ACCESS MPC. END. [id=" + id + "]. [date=" + date + "]");
	            
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
	
	private String trimSpaces(String input){
		
		int preLenght = 0;
		int posLenght = -1;
		while (preLenght != posLenght){
			preLenght = input.length();
			input = input.replace("  ", " ");
			posLenght = input.length();
		};
		
		return input;
		
	}

	public boolean isValidContent() {
		return validContent;
	}

	public List<EphemerisData> getItems() {
		return items;
	}

}
