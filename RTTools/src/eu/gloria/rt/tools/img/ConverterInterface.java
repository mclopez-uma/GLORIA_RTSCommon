package eu.gloria.rt.tools.img;

import java.io.File;

public interface ConverterInterface {
	
	public void fitstopnm(File input, File output, int maxLimit) throws Exception;
	
	public void fitstopnm(File input, File output) throws Exception;
	
	public void pnmtojpeg(File input, File output) throws Exception;
	
	public void fitstojpeg(File input, File output) throws Exception;
	
	public void fitstojpeg(File input, File output, String scale) throws Exception;
	
	public void jpegtofits(File input, File output, File workDir) throws Exception;
	
	public void jpegtopnm(File input, File output, File workDir) throws Exception;
	
	public void pnmtofits(File input, File output, File workDir) throws Exception;
	
	public void pnmtofits(File input, File output, File workDir, long minvalue, long maxvalue) throws Exception;
	
	public void bmptopnm(File input, File output, File workDir) throws Exception;

}
