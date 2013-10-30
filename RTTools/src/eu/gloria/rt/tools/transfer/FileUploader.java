package eu.gloria.rt.tools.transfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import eu.gloria.rt.tools.ssh.SftpClient;

/**
 * File Uploader tool.
 * 
 * @author jcabello
 *
 */
public class FileUploader {
	
	public static void main(String[] args) throws Exception{
		
		File file = new File("c:\\dummy\\kk.txt");
		System.out.println("filename=" + file.getName());
		System.out.println("lastModification=" + new Date(file.lastModified()));
		
		//URL url = new URL("ftp://user:password@localhost:23/tmp/kk.txt");
		//URL url = new URL("sftp://user:password@localhost:23/tmp/kk.txt"
		URL url = new URL("file:///C:/dummy/tmp.txt");
		//URL url = new URL("file:///tmp/kk/");
		
		//URL url = new URL("file:///usr/share/gloria/rts/repositories/rep01/");
		
		System.out.println("url=" + url.toString());
		System.out.println("host=" + url.getHost());
		System.out.println("file=" + url.getPath());
		System.out.println("auth=" + url.getAuthority());
		System.out.println("path=" + url.getPath());
		System.out.println("port=" + url.getPort());
		System.out.println("protocol=" + url.getProtocol());
		System.out.println("file=" + url.getFile());
		System.out.println("userInfo=" + url.getUserInfo());
		
		//URL urlSource = new URL("file:///C:/dummy/cp1/tmp.txt");
		//URL urlTarget = new URL("file:///C:/dummy/cp2/f1/tmp.txt");
		
		//FileUploader uploader = new FileUploader("c:\\dummy\\cp1");
		//uploader.upload(urlSource, urlTarget);
		
		
		//-----------------------------------------
		FileURL source = new FileURL("file:///C:/dummy/jcabello.jpg");
		FileURL target = new FileURL("sftp://uma:uma@localhost:22/tmp/kk1/kk2/jcabello2.jpg");
		
		FileUploader uploader = new FileUploader("c:\\dummy\\cp1");
		uploader.upload(source, target);
		
		
	}
	
	private String tmpPath;
	
	public FileUploader(String tmpPath){
		this.tmpPath = tmpPath;
	}
	
	
	/**
	 * Upload a file from source to target
	 * @deprecated
	 * @param source Source file URL
	 * @param target Target fille URL
	 * @throws Exception In error case
	 */
	public void upload(URL source, URL target) throws Exception{
		
		if (source.getProtocol().equals("file") && target.getProtocol().equals("file")){ //local->local
			moveLocalFile(source, target);
		} else if (source.getProtocol().equals("file") && target.getProtocol().equals("ftp")){//local->ftp
		} else{
			throw new Exception("Impossible to transfer the file.");
		}
		
	}
	
	public void upload(FileURL source, FileURL target) throws Exception{
		
		if (source.getProtocol() == FileURLProtocol.FILE && target.getProtocol() == FileURLProtocol.FILE){ //local->local
			moveLocalFile(source.getURL(), target.getURL());
		} else if (source.getProtocol() == FileURLProtocol.FILE && target.getProtocol() == FileURLProtocol.SFTP){//local->sftp
			moveSftp(source.getURL(), target);
		} else{
			throw new Exception("Impossible to transfer the file.");
		}
		
	}
	
	private void moveSftp(URL source, FileURL target) throws Exception{
		
		File sFile = new File(source.getPath());
		File tFile = new File(target.getPath());
		
		if (!sFile.exists()){
			throw new Exception("FileUploader::The source local file does not exist. SourceFile=" + tFile.toString());
		}
		
		SftpClient client = new SftpClient(target.getHost(), target.getPort(), target.getUser(), target.getUserPw());
		client.connect();
		
		try{
			
			//Verify and create the target directory
			String targetFolders = tFile.getParentFile().getPath().replace('\\', '/');
			boolean existPath = client.createDirectoryTree(targetFolders);
			if (!existPath){
				throw new Exception("FileUploader::Impossible to create the target directory. TargetFile=" + tFile.toString());
			}
			
			//upload the file	
			if (!client.upload(source.getPath(), target.getPath())){
				throw new Exception("The file cannot be uploaded.");
			}
			
			//Remove local file
			
			sFile.delete();
			
			
		}finally{
			client.disconnect();
		}
		
	}
	
	private void moveLocalFile(URL source, URL target) throws Exception{
		
		File sFile = new File(source.getPath());
		File tFile = new File(target.getPath());
		
		if (!sFile.exists()){
			throw new Exception("FileUploader::The source local file does not exist. SourceFile=" + tFile.toString());
		}
		
		if (!tFile.getParentFile().exists()){
			if (!tFile.getParentFile().mkdirs()){
				throw new Exception("FileUploader::Impossible to create the target directory. TargetFile=" + tFile.toString());
			}
			//tFile.getParentFile().setExecutable(true, false);
			//tFile.getParentFile().setWritable(true, false);
			//tFile.getParentFile().setReadable(true, false);
		}
		
		
		//1) try to move the file....
		if (!sFile.renameTo(tFile)){
			//2)Imposible to move....try to copy.
			//throw new Exception("FileUploader:: Impossible to MOVE the file. [source=" +sFile.toString() + ", target=" + tFile.toString() + "]");
			try{
				copyFileUsingFileStreams(sFile, tFile);
			}catch (Exception e) {
				throw new Exception("FileUploader:: Impossible to COPY OR MOVE the file. [source=" +sFile.toString() + ", target=" + tFile.toString() + "]. " + e.getMessage());
			}
		}
		
	}
	
	
	private static void copyFileUsingFileStreams(File source, File dest)
	        throws IOException {
	    InputStream input = null;
	    OutputStream output = null;
	    try {
	        input = new FileInputStream(source);
	        output = new FileOutputStream(dest);
	        byte[] buf = new byte[1024];
	        int bytesRead;
	        while ((bytesRead = input.read(buf)) > 0) {
	            output.write(buf, 0, bytesRead);
	        }
	    } finally {
	        input.close();
	        output.close();
	    }
	}

}
