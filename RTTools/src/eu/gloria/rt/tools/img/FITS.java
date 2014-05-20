package eu.gloria.rt.tools.img;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import nom.tam.fits.Fits;

/**
 * FITS wrapper class.
 * 
 * @author jcabello
 *
 */
public class FITS {
	
	private Fits fits;
	private File file;
	
	public FITS(File file) throws Exception{
		
		this.file= file;
		this.fits = new Fits(file);
		
		int number = fits.size(); //getNumberOfHDUs does not work.
		
		if (number == 0) throw new Exception("FITS without HDU");
		if (number > 1) throw new Exception("FITS containing more tha one HDU");
		
	}
	
	public FITS(Fits fits) throws Exception{
		
		this.fits = fits;
		this.file = null;
		
		if (fits.getNumberOfHDUs() == 0) throw new Exception("FITS without HDU");
		if (fits.getNumberOfHDUs() > 1) throw new Exception("FITS containing more tha one HDU");
		
	}
	
	public String getHeaderParameter(String keyword) throws Exception{
		
		return fits.getHDU(0).getTrimmedString(keyword);
		
	}
	
	public void setHeaderParameter(String key, boolean value, String comment) throws Exception{
		fits.getHDU(0).addValue(key, value, comment);
	}
	
	public void setHeaderParameter(String key, double value, String comment) throws Exception{
		fits.getHDU(0).addValue(key, value, comment);
	}
	
	public void setHeaderParameter(String key, int value, String comment) throws Exception{
		fits.getHDU(0).addValue(key, value, comment);
	}
	
	public void setHeaderParameter(String key, String value, String comment) throws Exception{
		fits.getHDU(0).addValue(key, value, comment);
	}
	
	public void save(File target, boolean overwrite) throws Exception{
		
		if (target.exists() && !overwrite){
			throw new Exception("The file cannot be overwrite (by sw). File=" + target);
		}
		
		DataOutputStream dos = null;
		try{
			
			dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(target)));
			fits.write(dos);
			
		}finally{
			
			if (dos != null){
				dos.close();
			}
			
		}
	}
	
	public void save() throws Exception{
		
		if (this.file == null) {
			throw new Exception("The target file must be set");
		}
		
		save(this.file, true);
	}

}
