package eu.gloria.rt.tools.img;

import java.io.File;

public interface ConverterInterface {
	
	public void fitstopnm(File input, File output) throws Exception;
	
	public void pnmtojpeg(File input, File output) throws Exception;

}
