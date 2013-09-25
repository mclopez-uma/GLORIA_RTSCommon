package eu.gloria.rt.tools.transfer;

import java.net.URL;

/**
 * URL for Transferring process.
 * 
 * Available URL formats:
 * 
 * - file:///C:/tmp/file.ext
 * - sftp://user:pw@host:22/tmp/file.ext
 * 
 * @author jcabello
 *
 */
public class FileURL {
	
	/**
	 * File URL
	 */
	private String fileUrl;
	
	/**
	 * URL protocol
	 */
	private FileURLProtocol protocol;
	
	/**
	 * Internal URL
	 */
	private URL internalURL;
	
	/**
	 * Constructor
	 * @param fileUrl File URL
	 * @throws Exception In error case
	 */
	public FileURL(String fileUrl) throws Exception{
		this.fileUrl = fileUrl;
		
		if (fileUrl == null){
			throw new Exception("Wrong URL");
		}
		
		if (fileUrl.startsWith("http")){
			protocol = FileURLProtocol.HTTP;
			internalURL = new URL(fileUrl);
			
		}else if (fileUrl.startsWith("sftp")){
			protocol = FileURLProtocol.SFTP;
			String tmp = "ftp" + fileUrl.substring(4);
			internalURL = new URL(tmp);
			
		}else if(fileUrl.startsWith("file")){
			protocol = FileURLProtocol.FILE;
			internalURL = new URL(fileUrl);
			
		}else{
			throw new Exception("Wrong URL");
		}
		
	}
	
	/**
	 * Access method
	 * @return value
	 */
	public FileURLProtocol getProtocol(){
		return protocol;
	}
	
	/**
	 * Access method
	 * @return value
	 */
	public String getPath(){
		return internalURL.getPath();
	}
	
	/**
	 * Access method
	 * @return value
	 */
	public String getHost(){
		return internalURL.getHost();
	}
	
	/**
	 * Access method
	 * @return value
	 */
	public String getAuthority(){
		return internalURL.getAuthority();
	}
	
	/**
	 * Access method
	 * @return value
	 */
	public int getPort(){
		return internalURL.getPort();
	}
	
	/**
	 * Access method
	 * @return value
	 */
	public String getFile(){
		return internalURL.getFile();
	}
	
	/**
	 * Access method
	 * @return value
	 */
	public URL getURL() throws Exception{
		
		if (protocol == FileURLProtocol.SFTP) throw new Exception ("URL cannot support SFTP protocol");
		return internalURL;
	}
	
	/**
	 * Access method
	 * @return value
	 */
	public String getUser() throws Exception {

		String result = null;
		if (protocol == FileURLProtocol.SFTP){
			String userInfo = internalURL.getUserInfo();
			if (userInfo != null){
				String[] params = userInfo.split(":");
				if (params == null || params.length != 2){
					throw new Exception ("Invalid user information.");
				}
				result = params[0];
			}
		}else{
			throw new Exception ("No user information for this protocol.");
		}
		
		return result;
	}
	
	/**
	 * Access method
	 * @return value
	 */
	public String getUserPw() throws Exception {

		String result = null;
		if (protocol == FileURLProtocol.SFTP){
			String userInfo = internalURL.getUserInfo();
			if (userInfo != null){
				String[] params = userInfo.split(":");
				if (params == null || params.length != 2){
					throw new Exception ("Invalid user information.");
				}
				result = params[1];
			}
		}else{
			throw new Exception ("No user information for this protocol.");
		}
		
		return result;
	}

}
