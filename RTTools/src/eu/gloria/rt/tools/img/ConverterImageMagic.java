package eu.gloria.rt.tools.img;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import eu.gloria.rt.tools.runtime.ExecResult;
import eu.gloria.rt.tools.runtime.Executor;

public class ConverterImageMagic implements ConverterInterface {

	@Override
	public void fitstopnm(File input, File output, int maxLimit)
			throws Exception {
		
		throw new Exception("Unsuported Method: ConverterImageMagic.fitstopnm()");

	}

	@Override
	public void fitstopnm(File input, File output) throws Exception {
		
		throw new Exception("Unsuported Method: ConverterImageMagic.fitstopnm()");

	}

	@Override
	public void pnmtojpeg(File input, File output) throws Exception {
		
		throw new Exception("Unsuported Method: ConverterImageMagic.pnmtojpeg()");

	}

	@Override
	public void fitstojpeg(File input, File output) throws Exception {
		
		String fileName = input.getName();
		if (!fileName.endsWith(".fits") && !fileName.endsWith(".fts")) throw new Exception("The input file is not a FITS file: " + input.getAbsolutePath());
		
		Executor exec = new Executor();
		
		//fits -> jpg
		//EXAMPLE:  /usr/bin/convert -normalize M30_01.fits > M30_01.jpg
				
		String cmd = "/usr/bin/convert";
		String[] params = { 
				"-normalize",
				input.getAbsolutePath(),
				output.getAbsolutePath()
		};
		
		ExecResult result = exec.execute(cmd, params);
		 
		if (result.getCode() != 0) throw new Exception("ConverterImageMagic.fitstojpeg(). Error:" + result.getCode() + ", " + result.getErr());
		     


	}
	
	@Override
	public void fitstojpeg(File input, File output, String scale) throws Exception {
		
		String fileName = input.getName();
		if (!fileName.endsWith(".fits") && !fileName.endsWith(".fts")) throw new Exception("The input file is not a FITS file: " + input.getAbsolutePath());
		
		Executor exec = new Executor();
		
		//fits -> jpg
		//EXAMPLE:  /usr/bin/convert -normalize M30_01.fits > M30_01.jpg
				
		String cmd = "/usr/bin/convert";
		String[] params = { 
				"-normalize",
				"-scale",
				scale,
				input.getAbsolutePath(),
				output.getAbsolutePath()
		};
		
		ExecResult result = exec.execute(cmd, params);
		 
		if (result.getCode() != 0) throw new Exception("ConverterImageMagic.fitstojpeg(). Error:" + result.getCode() + ", " + result.getErr());
		     


	}

}
