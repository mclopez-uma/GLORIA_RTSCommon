
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package eu.gloria.rti_db;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.1
 * 2013-09-05T10:52:53.588+02:00
 * Generated source version: 2.6.1
 * 
 */

@javax.jws.WebService(
                      serviceName = "gloria_rti_db",
                      portName = "gloria_rti_dbSOAP",
                      targetNamespace = "http://gloria.eu/rti_db",
                      wsdlLocation = "http://localhost:8080/RTI_DB/services/gloria_rti_dbSOAP?wsdl",
                      endpointInterface = "eu.gloria.rti_db.GloriaRtiDb")
                      
public class GloriaRtiDbImpl implements GloriaRtiDb {

    private static final Logger LOG = Logger.getLogger(GloriaRtiDbImpl.class.getName());

    /* (non-Javadoc)
     * @see eu.gloria.rti_db.GloriaRtiDb#opGet(java.lang.String  uuid )*
     */
    public eu.gloria.rt.entity.db.ObservingPlan opGet(java.lang.String uuid) throws RtiDbError    { 
        LOG.info("Executing operation opGet");
        System.out.println(uuid);
        try {
            eu.gloria.rt.entity.db.ObservingPlan _return = new eu.gloria.rt.entity.db.ObservingPlan();
            _return.setId("Id-1068742116");
            _return.setUuid("Uuid1056789212");
            eu.gloria.rt.entity.db.ObservingPlanOwner _returnOwner = eu.gloria.rt.entity.db.ObservingPlanOwner.USER;
            _return.setOwner(_returnOwner);
            eu.gloria.rt.entity.db.ObservingPlanType _returnType = eu.gloria.rt.entity.db.ObservingPlanType.FLAT;
            _return.setType(_returnType);
            _return.setUser("User156722389");
            java.util.List<eu.gloria.rt.entity.db.File> _returnFiles = new java.util.ArrayList<eu.gloria.rt.entity.db.File>();
            eu.gloria.rt.entity.db.File _returnFilesVal1 = new eu.gloria.rt.entity.db.File();
            _returnFilesVal1.setId("Id-367178883");
            _returnFilesVal1.setUuid("Uuid1547111864");
            _returnFilesVal1.setDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2013-09-05T10:52:53.602+02:00"));
            eu.gloria.rt.entity.db.FileContentType _returnFilesVal1ContentType = eu.gloria.rt.entity.db.FileContentType.OBSERVATION;
            _returnFilesVal1.setContentType(_returnFilesVal1ContentType);
            java.util.List<eu.gloria.rt.entity.db.Format> _returnFilesVal1Formats = new java.util.ArrayList<eu.gloria.rt.entity.db.Format>();
            _returnFilesVal1.getFormats().addAll(_returnFilesVal1Formats);
            eu.gloria.rt.entity.db.FileType _returnFilesVal1Type = eu.gloria.rt.entity.db.FileType.IMAGE;
            _returnFilesVal1.setType(_returnFilesVal1Type);
            _returnFiles.add(_returnFilesVal1);
            _return.getFiles().addAll(_returnFiles);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RtiDbError("rtiDbError...");
    }

    /* (non-Javadoc)
     * @see eu.gloria.rti_db.GloriaRtiDb#uuidCreate(eu.gloria.rt.entity.db.UuidType  uuidType )*
     */
    public java.lang.String uuidCreate(eu.gloria.rt.entity.db.UuidType uuidType) throws RtiDbError    { 
        LOG.info("Executing operation uuidCreate");
        System.out.println(uuidType);
        try {
            java.lang.String _return = "_return2060282896";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RtiDbError("rtiDbError...");
    }

    /* (non-Javadoc)
     * @see eu.gloria.rti_db.GloriaRtiDb#fileAddFormat(java.lang.String  uuidFile ,)eu.gloria.rt.entity.db.FileFormat  format ,)java.lang.String  sourceURL )*
     */
    public void fileAddFormat(java.lang.String uuidFile,eu.gloria.rt.entity.db.FileFormat format,java.lang.String sourceURL) throws RtiDbError    { 
        LOG.info("Executing operation fileAddFormat");
        System.out.println(uuidFile);
        System.out.println(format);
        System.out.println(sourceURL);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RtiDbError("rtiDbError...");
    }

    /* (non-Javadoc)
     * @see eu.gloria.rti_db.GloriaRtiDb#opDelete(java.lang.String  uuid )*
     */
    public void opDelete(java.lang.String uuid) throws RtiDbError    { 
        LOG.info("Executing operation opDelete");
        System.out.println(uuid);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RtiDbError("rtiDbError...");
    }

    /* (non-Javadoc)
     * @see eu.gloria.rti_db.GloriaRtiDb#fileDelete(java.lang.String  uuid )*
     */
    public void fileDelete(java.lang.String uuid) throws RtiDbError    { 
        LOG.info("Executing operation fileDelete");
        System.out.println(uuid);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RtiDbError("rtiDbError...");
    }

    /* (non-Javadoc)
     * @see eu.gloria.rti_db.GloriaRtiDb#fileDeleteFormat(java.lang.String  uuidFile ,)eu.gloria.rt.entity.db.FileFormat  format )*
     */
    public void fileDeleteFormat(java.lang.String uuidFile,eu.gloria.rt.entity.db.FileFormat format) throws RtiDbError    { 
        LOG.info("Executing operation fileDeleteFormat");
        System.out.println(uuidFile);
        System.out.println(format);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RtiDbError("rtiDbError...");
    }

    /* (non-Javadoc)
     * @see eu.gloria.rti_db.GloriaRtiDb#fileGet(java.lang.String  uuid )*
     */
    public eu.gloria.rt.entity.db.File fileGet(java.lang.String uuid) throws RtiDbError    { 
        LOG.info("Executing operation fileGet");
        System.out.println(uuid);
        try {
            eu.gloria.rt.entity.db.File _return = new eu.gloria.rt.entity.db.File();
            _return.setId("Id-372178510");
            _return.setUuid("Uuid2119534393");
            _return.setDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2013-09-05T10:52:53.606+02:00"));
            eu.gloria.rt.entity.db.FileContentType _returnContentType = eu.gloria.rt.entity.db.FileContentType.FLAT;
            _return.setContentType(_returnContentType);
            java.util.List<eu.gloria.rt.entity.db.Format> _returnFormats = new java.util.ArrayList<eu.gloria.rt.entity.db.Format>();
            eu.gloria.rt.entity.db.Format _returnFormatsVal1 = new eu.gloria.rt.entity.db.Format();
            eu.gloria.rt.entity.db.FileFormat _returnFormatsVal1FileFormat = eu.gloria.rt.entity.db.FileFormat.JPG;
            _returnFormatsVal1.setFileFormat(_returnFormatsVal1FileFormat);
            _returnFormatsVal1.setUrl("Url-1355378");
            _returnFormats.add(_returnFormatsVal1);
            _return.getFormats().addAll(_returnFormats);
            eu.gloria.rt.entity.db.FileType _returnType = eu.gloria.rt.entity.db.FileType.VIDEO;
            _return.setType(_returnType);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RtiDbError("rtiDbError...");
    }

    /* (non-Javadoc)
     * @see eu.gloria.rti_db.GloriaRtiDb#fileCreate(java.lang.String  uuidOp ,)eu.gloria.rt.entity.db.File  file )*
     */
    public java.lang.String fileCreate(java.lang.String uuidOp,eu.gloria.rt.entity.db.File file) throws RtiDbError    { 
        LOG.info("Executing operation fileCreate");
        System.out.println(uuidOp);
        System.out.println(file);
        try {
            java.lang.String _return = "_return-5433493";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RtiDbError("rtiDbError...");
    }

    /* (non-Javadoc)
     * @see eu.gloria.rti_db.GloriaRtiDb#opCreate(eu.gloria.rt.entity.db.ObservingPlan  op )*
     */
    public java.lang.String opCreate(eu.gloria.rt.entity.db.ObservingPlan op) throws RtiDbError    { 
        LOG.info("Executing operation opCreate");
        System.out.println(op);
        try {
            java.lang.String _return = "_return1507700382";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RtiDbError("rtiDbError...");
    }

}
