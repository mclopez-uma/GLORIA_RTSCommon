package eu.gloria.rt.catalogue.sesame;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import Sesame_pkg.SesameProxy;

import eu.gloria.rt.catalogue.ObjCategory;
import eu.gloria.rt.catalogue.ObjInfo;
import eu.gloria.rt.catalogue.ResolverCatalogue;
import eu.gloria.rt.catalogue.mpc.ResolverCatalogueMPC;
import eu.gloria.rt.entity.environment.config.device.DeviceList;
import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Epoch;
import eu.gloria.rt.unit.Radec;
import eu.gloria.tools.conversion.DegreeFormat;

/**
 * Resolver for Sesame repository (Simbad).
 * 
 * @author jcabello
 *
 */
public class ResolverCatalogueSesame  implements ResolverCatalogue {
	
	@Override
	public ObjInfo getObject(String id, Epoch epoch)   throws RTException {
		return getObject(id, new Date(), epoch);
	}

	@Override
	public ObjInfo getObject(String id, Date day, Epoch epoch) throws RTException {
		
		try{
			
			/*System.out.println((new Date()) + "ResolverCatalogueSesame.accessing....sleep 5 seconds.");
			Thread.sleep(5000);*/
		
			SesameProxy proxy = new SesameProxy();
			String response = proxy.getSesame().sesameXML(id);
			
			Radec radec = parse(response);
			
			System.out.println((new Date()) + "ResolverCatalogueSesame.Accessed.");
			
			ObjInfo result = null;
			if (radec != null){
				result = new ObjInfo();
				result.setCategory(ObjCategory.OutsideSSystemObj);
				result.setId(id);
				result.setPosition(radec);
			}
			
			return result;
			
			
			/*File schemaFile = new File("C:\\repositorio\\workspace\\tmp\\sesame_4x.xsd");
			URL url = new URL("http://vizier.u-strasbg.fr/xml/sesame_4x.xsd");
			boolean kk = schemaFile.exists();
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(url);

			JAXBContext context = JAXBContext.newInstance(DeviceList.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);
			
			File file = new File("c:\\repositorio\\workspace\\tmp\\sesame_4x.xsd");
			
			InputStream inputStream = new ByteArrayInputStream(response.getBytes("UTF-8"));
			
			
			Object obj = unmarshaller.unmarshal(inputStream);
			
			inputStream.close();*/
			
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
        	throw new RTException("Error accessing to Sessame." + ex.getMessage());
		}
		
		
	}
	
	@Override
	public String getCatalogueName() {
		return "Sesame(Simbad,NED,VizieR)";
	}
	
	private Radec parse(String content) throws RTException{
		
		boolean found = content.indexOf("<jpos>") > -1;
		
		int indexOfBegin = content.indexOf("<jpos>") + "<jpos>".length();
		int indexOfEnd =content.indexOf("</jpos>");
		
		if (!found){
			
			return null;
			
		}else{
			
			String data = content.substring(indexOfBegin, indexOfEnd);
			String[] coord = data.split(" "); 
			
			//RA
			String[] ra = coord[0].split(":"); 
			
			//DEC
			String[] dec = coord[1].split(":"); 
			
			Radec radec = new Radec(
					Integer.parseInt(ra[0].replace("+", "")),	//int raHH, 
					Integer.parseInt(ra[1].replace("+", "")),	//int raMM, 
					Double.parseDouble(ra[2].replace("+", "")),	//double raSS, 
					Integer.parseInt(dec[0].replace("+", "")),	//int decDD, 
					Integer.parseInt(dec[1].replace("+", "")),	//int decMM,	
					Double.parseDouble(dec[2].replace("+", ""))	//double decSS
				);
			
			radec.setEpoch(Epoch.J2000);
			
			return radec;
		}
		
		
	}
	
	public final static void main(String[] args) throws Exception {
		
		ResolverCatalogueSesame resolver = new ResolverCatalogueSesame();
		//ObjInfo info = resolver.getObject("HIP42313" /*"moon"*/ /* "Cycnos"*/, Epoch.J2000);
		//ObjInfo info = resolver.getObject("HIP42662" /*"moon"*/ /* "Cycnos"*/, Epoch.J2000);
		//ObjInfo info = resolver.getObject("HIP42662" /*"moon"*/ /* "Cycnos"*/, Epoch.J2000);
		ObjInfo info = resolver.getObject("HIP30428" /*"moon"*/ /* "Cycnos"*/,  Epoch.J2000);
		
		System.out.println("RA=" + info.getPosition().getRaString(DegreeFormat.HHMMSS));
		System.out.print("DEC=" + info.getPosition().getDecString(DegreeFormat.DDMMSS));
		
	}


}
