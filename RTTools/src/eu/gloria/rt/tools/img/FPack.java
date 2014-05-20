package eu.gloria.rt.tools.img;

import java.io.File;

import eu.gloria.rt.tools.runtime.ExecResult;
import eu.gloria.rt.tools.runtime.Executor;

public class FPack {
	
	private String cmdPath;
	
	public FPack(String cmdPath) throws Exception{
		
		this.cmdPath = cmdPath;
		
		File tmpFile = new File(cmdPath);
		if (!tmpFile.exists()){
			throw new Exception("FPack file does not exist:" + cmdPath);
		}
	}
	
	public void generate(File fitsFile, File fzFile) throws Exception{
		
		String fileName = fitsFile.getName();
		if (!fileName.endsWith(".fits") && !fileName.endsWith(".fts")) throw new Exception("The input file is not a FITS file: " + fitsFile.getAbsolutePath());
		
		//Fits max content information
		Executor exec = new Executor();
		
		String cmd1 = this.cmdPath;
		String[] params1 = {
				fileName
		};
		
		ExecResult result1 = exec.execute(cmd1, params1);
		if (result1.getCode() != 0) {
			throw new Exception("FPack.generate(). Error:" + result1.getCode() + ", " + result1.getErr());
		}
		

		File outputFile = new File(fileName + ".fz");
		if (!outputFile.exists()){
			throw new Exception("The ouput FZ file is not generated. File: " + outputFile.getAbsolutePath());
		}
		
		if (!outputFile.renameTo(fzFile)){
			outputFile.delete();
			throw new Exception("Error renaming the ouputFile to:" + fzFile + ". Target File: " + outputFile.getAbsolutePath());
		}
		
		
	}

}
