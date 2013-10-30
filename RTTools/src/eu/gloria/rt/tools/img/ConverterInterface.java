package eu.gloria.rt.tools.img;

import java.io.File;

public interface ConverterInterface {
	
	public void fitstopnm(File input, File output, int maxLimit) throws Exception;
	
	public void fitstopnm(File input, File output) throws Exception;
	
	public void pnmtojpeg(File input, File output) throws Exception;
	
	public void fitstojpeg(File input, File output) throws Exception;
	
	public void fitstojpeg(File input, File output, String scale) throws Exception;

}
