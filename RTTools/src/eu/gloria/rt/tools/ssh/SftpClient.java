package eu.gloria.rt.tools.ssh;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Sftp client.
 * 
 * @author jcabello
 *
 */
public class SftpClient {
	
	private String host;
	private int port;
	private String user;
	private String pw;
	private ChannelSftp sftpChannel;
	private Session sftpSession = null;
	
	/**
	 * Testing...
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		
		SftpClient client = new SftpClient("localhost", 22, "user", "pw");
		client.connect();
		client.cd("\tmp");
		client.upload("c:\\dummy\\device_list.xml", "/tmp/device_list.xml");
		//boolean result = client.upload("http://161.72.128.9:8080/RTD_TAD_DB/ServletImage?fileName=00000000000000010000013f57f1e5b1.jpg", "/tmp/jcabello.jpg");
		client.disconnect();
	}
	
	/**
	 * Constructor
	 * @param host Host
	 * @param port Port
	 * @param user User
	 * @param pw User password
	 */
	public SftpClient(String host, int port, String user, String pw){
		
		this.host = host;
		this.port = port;
		this.user = user;
		this.pw = pw;
		this.sftpChannel = null;
		this.sftpSession = null;
	}
	
	/**
	 * Opens the Sftp session
	 * @return true if successfull
	 */
	public boolean connect() {

		try {
			
			this.sftpChannel = null;
			this.sftpSession = null;
			
			 JSch jsch = new JSch();
			 this.sftpSession = jsch.getSession("uma", "localhost", 22);
			 this.sftpSession.setConfig("StrictHostKeyChecking", "no"); //avoid error: UnknownHostKey
			 this.sftpSession.setPassword("uma");
			 this.sftpSession.connect();
			 
			 Channel channel = this.sftpSession.openChannel("sftp");
	         channel.connect();
	         this.sftpChannel = (ChannelSftp) channel;

		} catch (Exception ex) {
			disconnect();
			System.err.println("SftpClient.connect(). " + ex.getMessage());
			return false;
		}

		return true;
	}
	
	/**
	 * Closes the session
	 */
	public void disconnect(){
		
		if (this.sftpChannel != null){
			this.sftpChannel.exit();
			this.sftpChannel = null;
		}
		
		if (this.sftpSession != null){
			this.sftpSession.disconnect();
			this.sftpSession = null;
		}
		
	}
	
	/**
	 * Returns true if the a directory path exists
	 * @param dirPath Directory path.
	 * @return boolean
	 * @throws Exception In error case
	 */
	public boolean exist(String dirPath) throws Exception {
		
		String currentDir = null;
		try {
			
			if (dirPath == null || dirPath.isEmpty()){
				return false;
			}
			
			currentDir = sftpChannel.pwd();
			try{
				//Try to move to the folder
				sftpChannel.cd(dirPath);
			}catch(SftpException ex){ //the folder does not exist
				return false;
			}
			
			return true;
			
		} catch (Exception ex) {
			
			System.err.println("SftpClient.exist(). " + ex.getMessage());
			throw ex;
			
		} finally{
			
			try {
				if (currentDir != null) {
					sftpChannel.cd(currentDir);
				}
			} catch (SftpException ex) {
				System.err.println("SftpClient.exist():: Error returning to the initial folder. Err=" + ex.getMessage());
			}
			
		}
		
	}
	
	
	/**
	 * Change the working directory.
	 * @param dirPath Directory path.
	 * @return true if the working directory changed.
	 * @throws Exception In error case
	 */
	public boolean cd(String dirPath) throws Exception {
		
		try {
			
			if (dirPath == null || dirPath.isEmpty()){
				return false;
			}
			
			try{
				//Try to move to the folder
				sftpChannel.cd(dirPath);
			}catch(SftpException ex){ //the folder does not exist
				return false;
			}
			
			return true;
			
		} catch (Exception ex) {
			
			System.err.println("SftpClient.cd(). " + ex.getMessage());
			throw ex;
		}
		
	}
	
	/**
	 * Creates the directory tree.
	 * @param dirTree full directory path
	 * @return true if successfull
	 * @throws Exception In error case.
	 */
	public boolean createDirectoryTree(String dirTree) throws Exception{
		
		String currentDir = null;
		
		try {
			
			if (dirTree == null || dirTree.isEmpty()){
				return false;
			}
			
			currentDir = sftpChannel.pwd();
			System.out.println("SftpClient.createDirectoryTree():: Current Directory::" +  sftpChannel.pwd());
			
			String[] directories = dirTree.split("/");
			if (directories != null && directories[0] != null && directories[0].isEmpty()){
				//Move to root directory
				sftpChannel.cd("/");
			}
			
			for (String dir : directories) {

				System.out.println("SftpClient.createDirectoryTree():: CurrentDirectory::" +  sftpChannel.pwd());

				if (!dir.isEmpty()) {
					try{
						//Try to move to the next folder
						sftpChannel.cd(dir);
					}catch(SftpException ex){ //the folder does not exist...create it
						try{
							sftpChannel.mkdir(dir);
						}catch(SftpException ex01){
							System.err.println("SftpClient.createDirectoryTree():: Unable to create remote directory '" + dir + "'.  error='" + ex01.getMessage() + "'");
							throw ex;
						}
						
						//Move to the created folder
						sftpChannel.cd(dir);
					}
				}
			}
		
		} catch (Exception ex) {
			
			System.err.println("SftpClient.createDirectoryTree():: SftpClient.createDirectoryTree():: Error =" + ex.getMessage());
			
			return false;
			
		} finally {
			
			try {
				if (currentDir != null) {
					sftpChannel.cd(currentDir);
					System.out.println("SftpClient.createDirectoryTree():: Restored Directory::" +  sftpChannel.pwd());
				}
			} catch (SftpException ex) {
				System.err.println("SftpClient.createDirectoryTree():: Error returning to the initial path. Err=" + ex.getMessage());
			}
			
		}
		
		return true;
	}
	
	/**
	 * Upload a file.
	 * @param source URL of a Local file.
	 * @param target URL for remote file (sftp protocol).
	 * @return true if successfull.
	 */
	public boolean upload(String source, String target) {
		
		try {
			int mode = ChannelSftp.OVERWRITE;
			this.sftpChannel.put(source, target, mode);

		} catch (Exception ex) {
			//disconnect();
			System.err.println("connect(). " + ex.getMessage());
			ex.printStackTrace();
			return false;
		}

		return true;
		
	}


}
