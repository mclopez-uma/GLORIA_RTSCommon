/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package eu.gloria.rti_db;

import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.tomcat.jni.Directory;

import eu.gloria.rt.db.repository.RepFile;
import eu.gloria.rt.db.repository.RepFileContentType;
import eu.gloria.rt.db.repository.FileFormat;
import eu.gloria.rt.db.repository.RepFileFormat;
import eu.gloria.rt.db.repository.RepFileType;
import eu.gloria.rt.db.repository.RepManager;
import eu.gloria.rt.db.repository.RepObservingPlan;
import eu.gloria.rt.db.repository.RepObservingPlanOwner;
import eu.gloria.rt.db.repository.RepObservingPlanType;
import eu.gloria.rt.db.repository.Repository;
import eu.gloria.rt.db.util.DBUtil;
import eu.gloria.rt.entity.db.File;
import eu.gloria.rt.entity.db.FileContentType;
import eu.gloria.rt.entity.db.FileType;
import eu.gloria.rt.entity.db.Format;
import eu.gloria.rt.entity.db.ObservingPlan;
import eu.gloria.rt.entity.db.ObservingPlanOwner;
import eu.gloria.rt.entity.db.ObservingPlanType;
import eu.gloria.rt.tools.transfer.FileUploader;
import eu.gloria.tools.uuid.file.UUID;

/**
 * This class was generated by Apache CXF 2.6.1 2013-04-01T11:21:24.504+02:00
 * Generated source version: 2.6.1
 * 
 */

@javax.jws.WebService(serviceName = "gloria_rti_db", portName = "gloria_rti_dbSOAP", targetNamespace = "http://gloria.eu/rti_db", wsdlLocation = "http://localhost:8080/RTI_DB/services/gloria_rti_dbSOAP?wsdl", endpointInterface = "eu.gloria.rti_db.GloriaRtiDb")
public class GloriaRtiDbImpl implements GloriaRtiDb {

	private static final Logger LOG = Logger.getLogger(GloriaRtiDbImpl.class
			.getName());

	private int rtsId = 1;
	private int repId = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.gloria.rti_db.GloriaRtiDb#opGet(java.lang.String uuid )*
	 */
	public eu.gloria.rt.entity.db.ObservingPlan opGet(java.lang.String uuid)
			throws RtiDbError {
		LOG.info("Executing operation opGet");
		System.out.println(uuid);
		try {

			eu.gloria.rt.entity.db.ObservingPlan result = null;

			RepManager manager = new RepManager();
			EntityManager em = DBUtil.getEntityManager();
			try {
				// Resolves the path separator in the target system.
				Repository repository = manager.getRepActive(em);
				if (repository == null)
					throw new Exception("There is not any active repository.");

				result = recoverOPFromDB(manager, em, uuid, repository);
			} finally {
				DBUtil.close(em);
			}

			return result;

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RtiDbError(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.gloria.rti_db.GloriaRtiDb#uuidCreate(*
	 */
	public java.lang.String uuidCreate() throws RtiDbError {
		LOG.info("Executing operation uuidCreate");
		try {
			UUID result = new UUID(rtsId, repId);
			return result.getValue();
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RtiDbError(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.gloria.rti_db.GloriaRtiDb#fileAddFormat(java.lang.String uuidFile
	 * ,)eu.gloria.rt.entity.db.FileFormat format ,)java.lang.String sourceURL
	 * )*
	 */
	public void fileAddFormat(java.lang.String uuidFile,
			eu.gloria.rt.entity.db.FileFormat format, java.lang.String sourceURL)
			throws RtiDbError {
		LOG.info("Executing operation fileAddFormat");
		System.out.println(uuidFile);
		System.out.println(format);
		System.out.println(sourceURL);
		try {

			RepManager manager = new RepManager();
			EntityManager em = DBUtil.getEntityManager();

			try {

				// Resolves the path separator in the target system.
				Repository repository = manager.getRepActive(em);
				if (repository == null)
					throw new Exception("There is not any active repository.");
				String pathSeparator = repository.getConnUrl().substring(
						repository.getConnUrl().length() - 1); // the last
																// character of
																// connurl.
				if (pathSeparator == null || pathSeparator.trim().length() == 0)
					throw new Exception(
							"The Repository.connUrl must end with a directory separator.");

				// DB entity
				RepFile dbFile = manager.getFile(em, uuidFile);
				if (dbFile == null)
					throw new Exception("The File does not exist. UUID="
							+ uuidFile);

				// Build target file name
				UUID fileUUID = new UUID(dbFile.getUuid());

				String targetFileName = dbFile.getUuid();
				if (format == eu.gloria.rt.entity.db.FileFormat.FITS) { // FITS
					targetFileName = targetFileName + ".fits";
				} else { // JPG by default
					targetFileName = targetFileName + ".jpg";
				}

				String targetFileURLString = repository.getConnUrl()
						+ fileUUID.getFolder() + pathSeparator + targetFileName;
				URL sourURL = new URL(sourceURL);
				URL targURL = new URL(targetFileURLString);

				// publish the file
				FileUploader uploader = new FileUploader(null);
				uploader.upload(sourURL, targURL);

				RepFileFormat dbFormat = new RepFileFormat();
				dbFormat.setRepFile(dbFile);
				dbFormat.setFormat(FileFormat.valueOf(format.toString()));

				manager.create(em, dbFormat);

			} finally {
				DBUtil.close(em);
			}

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RtiDbError(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.gloria.rti_db.GloriaRtiDb#opDelete(java.lang.String uuid )*
	 */
	public void opDelete(java.lang.String uuid) throws RtiDbError {
		LOG.info("Executing operation opDelete");
		System.out.println(uuid);
		try {

			RepManager manager = new RepManager();
			EntityManager em = DBUtil.getEntityManager();
			try {
				RepObservingPlan dbOp = manager.getOp(em, uuid);
				if (dbOp != null)
					manager.delete(em, dbOp);
			} finally {
				DBUtil.close(em);
			}

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RtiDbError(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.gloria.rti_db.GloriaRtiDb#fileDelete(java.lang.String uuid )*
	 */
	public void fileDelete(java.lang.String uuid) throws RtiDbError {
		LOG.info("Executing operation fileDelete");
		System.out.println(uuid);
		try {

			RepManager manager = new RepManager();
			EntityManager em = DBUtil.getEntityManager();
			try {
				RepFile dbFile = manager.getFile(em, uuid);
				if (dbFile != null)
					manager.delete(em, dbFile);
			} finally {
				DBUtil.close(em);
			}

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RtiDbError(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.gloria.rti_db.GloriaRtiDb#fileDeleteFormat(java.lang.String
	 * uuidFile ,)eu.gloria.rt.entity.db.FileFormat format )*
	 */
	public void fileDeleteFormat(java.lang.String uuidFile,
			eu.gloria.rt.entity.db.FileFormat format) throws RtiDbError {
		LOG.info("Executing operation fileDeleteFormat");
		System.out.println(uuidFile);
		System.out.println(format);
		try {

			RepManager manager = new RepManager();
			EntityManager em = DBUtil.getEntityManager();
			try {
				RepFile dbFile = manager.getFile(em, uuidFile);
				for (int x = 0; x < dbFile.getRepFormats().size(); x++) {
					if (dbFile.getRepFormats().get(x).getFormat().toString()
							.equals(format.toString())) {
						manager.delete(em, dbFile.getRepFormats().get(x));
						return;
					}
				}
			} finally {
				DBUtil.close(em);
			}

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RtiDbError(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.gloria.rti_db.GloriaRtiDb#fileGet(java.lang.String uuid )*
	 */
	public eu.gloria.rt.entity.db.File fileGet(java.lang.String uuid)
			throws RtiDbError {
		LOG.info("Executing operation fileGet");
		System.out.println(uuid);
		try {

			eu.gloria.rt.entity.db.File result = null;

			RepManager manager = new RepManager();
			EntityManager em = DBUtil.getEntityManager();
			try {
				// Resolves the path separator in the target system.
				Repository repository = manager.getRepActive(em);
				if (repository == null)
					throw new Exception("There is not any active repository.");

				result = recoverFileFromDB(manager, em, uuid, repository);
			} finally {
				DBUtil.close(em);
			}

			return result;

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RtiDbError(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.gloria.rti_db.GloriaRtiDb#fileCreate(java.lang.String uuidOp
	 * ,)eu.gloria.rt.entity.db.File file )*
	 */
	public java.lang.String fileCreate(java.lang.String uuidOp,
			eu.gloria.rt.entity.db.File file) throws RtiDbError {
		LOG.info("Executing operation fileCreate");
		System.out.println(uuidOp);
		System.out.println(file);
		try {

			RepManager manager = new RepManager();
			EntityManager em = DBUtil.getEntityManager();

			// UUUID generation			
			if (file.getUuid() == null)
				file.setUuid((new UUID(rtsId, repId)).getValue()) ;

			try {
				// DB entity
				RepObservingPlan dbOp = manager.getOp(em, uuidOp);
				if (dbOp == null)
					throw new Exception(
							"The Observing Plan does not exist. UUID=" + uuidOp);

				RepFile dbFile = new RepFile();
				dbFile.setContentType(RepFileContentType.valueOf(file
						.getContentType().toString()));
				dbFile.setDate(file.getDate().toGregorianCalendar().getTime());
				dbFile.setRepObservingPlan(dbOp);
				dbFile.setType(RepFileType.valueOf(file.getType().toString()));
				dbFile.setUuid(file.getUuid());

				manager.create(em, dbFile);

			} finally {
				DBUtil.close(em);
			}

			return file.getUuid();

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RtiDbError(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.gloria.rti_db.GloriaRtiDb#opCreate(eu.gloria.rt.entity.db.ObservingPlan
	 * op )*
	 */
	public java.lang.String opCreate(eu.gloria.rt.entity.db.ObservingPlan op)
			throws RtiDbError {
		LOG.info("Executing operation opCreate");
		System.out.println(op);
		
		try {

			RepObservingPlan dbOp = null;
			
			RepManager manager = new RepManager();
			EntityManager em = DBUtil.getEntityManager();
			
			try {
				
				if (manager.getOp(em, op.getUuid()) != null) {
					throw new Exception("The Observing Plan already exists into the Repository database. UUID="	+ op.getUuid());
				}

				// DB entity
				dbOp = new RepObservingPlan();
				dbOp.setOwner(RepObservingPlanOwner.valueOf(op.getOwner().toString()));
				dbOp.setType(RepObservingPlanType.valueOf(op.getType().toString()));
				dbOp.setUser(op.getUser());

				if (op.getUuid() == null) {
					// UUUID generation
					UUID result = new UUID(rtsId, repId);
					dbOp.setUuid(result.getValue());
				} else {
					dbOp.setUuid(op.getUuid());
				}

				manager.create(em, dbOp);
				
			} finally {
				DBUtil.close(em);
			}

			return dbOp.getUuid();

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RtiDbError(ex.getMessage());
		}
	}

	private eu.gloria.rt.entity.db.ObservingPlan recoverOPFromDB(
			RepManager manager, EntityManager em, String uuid,
			Repository repository) throws Exception {

		eu.gloria.rt.entity.db.ObservingPlan result = null;

		RepObservingPlan dbOp = manager.getOp(em, uuid);
		if (dbOp != null) {

			// OP
			result = new ObservingPlan();
			result.setId(dbOp.getId());
			result.setOwner(ObservingPlanOwner.valueOf(dbOp.getOwner()
					.toString()));
			result.setType(ObservingPlanType.valueOf(dbOp.getType().toString()));
			result.setUser(dbOp.getUser());
			result.setUuid(dbOp.getUuid());

			// Files
			if (dbOp.getRepFiles() != null) {
				for (int x = 0; x < dbOp.getRepFiles().size(); x++) {
					RepFile dbFile = dbOp.getRepFiles().get(x);
					eu.gloria.rt.entity.db.File file = recoverFile(dbFile,
							repository);
					if (file != null)
						result.getFiles().add(file);
				}
			}
		}

		return result;
	}

	private eu.gloria.rt.entity.db.File recoverFileFromDB(RepManager manager,
			EntityManager em, String uuid, Repository repository)
			throws Exception {

		eu.gloria.rt.entity.db.File result = null;

		RepFile dbFile = manager.getFile(em, uuid);
		if (dbFile != null) {
			result = new File();
			result.setContentType(FileContentType.valueOf(dbFile
					.getContentType().toString()));
			result.setDate(getDate(dbFile.getDate()));
			result.setId(dbFile.getId());
			result.setType(FileType.valueOf(dbFile.getType().toString()));
			// ??????result.setUrl(dbFile.get)
			result.setUuid(dbFile.getUuid());

			if (dbFile.getRepFormats() != null) {
				for (int x = 0; x < dbFile.getRepFormats().size(); x++) {
					Format format = new Format();
					format.setFileFormat(eu.gloria.rt.entity.db.FileFormat
							.valueOf(dbFile.getRepFormats().get(x).getFormat()
									.toString()));
					String url = repository.getPublicUrl() + "?" + "format="
							+ format.getFileFormat().toString() + "&uuid="
							+ dbFile.getUuid();
					format.setUrl(url);
					result.getFormats().add(format);
				}
			}
		}

		return result;
	}

	private eu.gloria.rt.entity.db.File recoverFile(RepFile dbFile,
			Repository repository) throws Exception {

		eu.gloria.rt.entity.db.File result = null;

		if (dbFile != null) {
			result = new File();
			result.setContentType(FileContentType.valueOf(dbFile
					.getContentType().toString()));
			result.setDate(getDate(dbFile.getDate()));
			result.setId(dbFile.getId());
			result.setType(FileType.valueOf(dbFile.getType().toString()));
			result.setUuid(dbFile.getUuid());

			if (dbFile.getRepFormats() != null) {
				for (int x = 0; x < dbFile.getRepFormats().size(); x++) {
					Format format = new Format();
					format.setFileFormat(eu.gloria.rt.entity.db.FileFormat
							.valueOf(dbFile.getRepFormats().get(x).getFormat()
									.toString()));
					String url = repository.getPublicUrl() + "?" + "format="
							+ format.getFileFormat().toString() + "&uuid="
							+ dbFile.getUuid();
					format.setUrl(url);
					result.getFormats().add(format);
				}
			}
		}

		return result;
	}

	private XMLGregorianCalendar getDate(Date date) throws Exception {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c);
		return xmlCalendar;
	}

}
