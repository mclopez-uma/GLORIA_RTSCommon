package eu.gloria.rt.tools.img;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import eu.gloria.rt.tools.runtime.ExecResult;
import eu.gloria.rt.tools.runtime.Executor;

public class ConverterNetpbm implements ConverterInterface{
	
	public static void main(String[] args) throws Exception {
		
		ConverterNetpbm converter = new ConverterNetpbm();
		File fits = new File("M30_01.fits");
		File pnm = new File("M30_01.pnm");
		File jpg = new File("M30_01.jpg");
		
		converter.fitstopnm(fits, pnm);
		converter.pnmtojpeg(pnm, jpg);
		System.out.println(pnm.delete());
		
	}
	
	public void fitstopnm(File input, File output) throws Exception{
		
		String fileName = input.getName();
		if (!fileName.endsWith(".fits") && !fileName.endsWith(".fts")) throw new Exception("The input file is not a FITS file: " + input.getAbsolutePath());
		
		//Fits max content information
		Executor exec = new Executor();
		
		String cmd1 = "/usr/bin/fitstopnm";
		String[] params1 = {
				"-printmax",
				input.getAbsolutePath()
		};
		
		ExecResult result1 = exec.execute(cmd1, params1);
		if (result1.getCode() != 0) throw new Exception("ConverterNetpbm.fitstopnm() 1. Error:" + result1.getCode() + ", " + result1.getErr());
		
		String[] interval = result1.getOutput().split(" ");
		double min = Double.parseDouble(interval[0]);
		double max = Double.parseDouble(interval[1]);
		int minInt = (int) min;
		int maxInt = (int) max;
		maxInt = minInt + 5000;
		
		//fits -> pnm
		//EXAMPLE: /usr/bin/fitstopnm -min 3929.0 -max 65535 "M30_01.fits" > M30_01.pnm
		
		String cmd2 = "/usr/bin/fitstopnm";
		String[] params2 = { 
				"-min",
				String.valueOf(minInt),
				"-max",
				String.valueOf(maxInt),
				input.getAbsolutePath(),
		};
		
		//String cmd2 = "/mnt/default/fitstopnm.sh " + String.valueOf(minInt) + " " + String.valueOf(maxInt) + " '" + input.getAbsolutePath() + "'" + "  " + " '" + output.getAbsolutePath() +"'";
		
		OutputStream out = null;  
 
		try{  
			
			out = new FileOutputStream(output.getAbsolutePath());  
			
			ExecResult result2 = exec.execute(cmd2, params2, out);
			if (result2.getCode() != 0) throw new Exception("ConverterNetpbm.fitstopnm() 2. Error:" + result2.getCode() + ", " + result2.getErr());
			
        }catch(Exception ex){
        	
        	if(out !=null){
        		try{
        			out.close();  
        		}catch(Exception ex1){}
        	}
        	
        	if (output.exists()) output.delete();
        	
        	out = null;
        	
        }finally{
        	
        	if(out !=null){
        		try{
        			out.close();  
        		}catch(Exception ex){}
        	}
        	
		}  
		
		
		
	
	}
	
	public void pnmtojpeg(File input, File output) throws Exception{
		
		String fileName = input.getName();
		if (!fileName.endsWith(".pnm")) throw new Exception("The input file is not a PNM file: " + input.getAbsolutePath());
		
		Executor exec = new Executor();
		
		//pnm -> jpg
		//EXAMPLE:  /usr/bin/pnmtojpeg --greyscale --optimize --progressive M30_01.pnm > M30_01.jpg
				
		String cmd = "/usr/bin/pnmtojpeg";
		String[] params = { 
				"--greyscale",
				"--optimize",
				"--progressive",
				input.getAbsolutePath(),
		};
		
		OutputStream out = null;  
		 
		try{  
			out = new FileOutputStream(output.getAbsolutePath());  
			
			ExecResult result = exec.execute(cmd, params, out);
			if (result.getCode() != 0) throw new Exception("ConverterNetpbm.pnmtojpeg(). Error:" + result.getCode() + ", " + result.getErr());
		     
        }catch(Exception ex){
        	
        	if(out !=null){
        		try{
        			out.close();  
        		}catch(Exception ex1){}
        	}
        	
        	if (output.exists()) output.delete();
        	
        	out = null;
        	
        }finally{
        	 
        	if(out !=null){
        		try{
        			out.close();  
        		}catch(Exception ex){}
        	}
 		}  
		
	}

}
