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
		//fitstopnm(input, output, 5000); //change
		
		String fileName = input.getName();
		if (!fileName.endsWith(".fits") && !fileName.endsWith(".fts")) throw new Exception("The input file is not a FITS file: " + input.getAbsolutePath());
		
		Executor exec = new Executor();
		
		//pnm -> jpg
		//EXAMPLE:  /usr/bin/fitstopnm  M30_01.fits > M30_01.jpg
				
		String cmd = "/usr/bin/fitstopnm";
		String[] params = { 
				input.getAbsolutePath(),
		};
		
		OutputStream out = null;  
		 
		try{  
			out = new FileOutputStream(output.getAbsolutePath());  
			
			ExecResult result = exec.execute(cmd, params, out);
			if (result.getCode() != 0) throw new Exception("ConverterNetpbm.fitstopnm(). Error:" + result.getCode() + ", " + result.getErr());
		     
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
	
	
	public void fitstopnm(File input, File output, int maxInc) throws Exception{
		
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
		int maxInt = minInt + maxInc;
		
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

	@Override
	public void fitstojpeg(File input, File output) throws Exception {
		
		throw new Exception("Unsuported Method: ConverterNetpbm.fitstojpeg()");
	}

	@Override
	public void fitstojpeg(File input, File output, String scale)
			throws Exception {
		throw new Exception("Unsuported Method: ConverterNetpbm.fitstojpeg(scale)");
		
	}
	
	@Override
	public void jpegtofits(File input, File output, File workDir) throws Exception{
		
		
		String fileName = input.getName();
		if (!fileName.endsWith(".jpg")) throw new Exception("The input file is not a JPG file: " + input.getAbsolutePath());
		
		File tmpFile = File.createTempFile("000", ".pnm");
		
		System.out.println("ConverterImageMagic.jpegtofits. input=" + input);
		System.out.println("ConverterImageMagic.jpegtofits. output=" + output);
		System.out.println("ConverterImageMagic.jpegtofits. tmpFile=" + tmpFile);
		try{
			
			jpegtopnm(input, tmpFile, workDir);
			pnmtofits(tmpFile, output, workDir);
			
		}catch(Exception ex){
			new Exception("ConverterImageMagic.jpegtofits(). Error:" + ex.getMessage());
		}finally{
			if (tmpFile != null && tmpFile.exists()){
				tmpFile.delete();
			}
		}
		
		
	}
	
	@Override
	public void jpegtopnm(File input, File output, File workDir) throws Exception{
		
		String fileName = input.getName();
		if (!fileName.endsWith(".jpg")) throw new Exception("The input file is not a JPG file: " + input.getAbsolutePath());
		
		Executor exec = new Executor();
		
		//pnm -> jpg
		//EXAMPLE:  /usr/bin/pnmtojpeg --greyscale --optimize --progressive M30_01.pnm > M30_01.jpg
				
		String cmd = "/usr/bin/jpegtopnm";
		String[] params = { 
				input.getAbsolutePath(),
		};
		
		OutputStream out = null;  
		 
		try{  
			out = new FileOutputStream(output.getAbsolutePath());  
			
			ExecResult result = exec.execute(cmd, params, out);
			if (result.getCode() != 0) throw new Exception("ConverterNetpbm.jpegtopnm(). Error:" + result.getCode() + ", " + result.getErr());
		     
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
	
	@Override
	public void pnmtofits(File input, File output, File workDir) throws Exception{
		
		String fileName = input.getName();
		if (!fileName.endsWith(".pnm")) throw new Exception("The input file is not a PNM file: " + input.getAbsolutePath());
		
		String[] params = { 
				input.getAbsolutePath(),
		};
		
		pnmtofitsCommon(input, output, workDir, params);
		
	}
	
	@Override
	public void pnmtofits(File input, File output, File workDir, long minvalue,	long maxvalue) throws Exception {
		
		String fileName = input.getName();
		if (!fileName.endsWith(".pnm")) throw new Exception("The input file is not a PNM file: " + input.getAbsolutePath());
		
		
		String[] params = { 
				"-min",
				String.valueOf(minvalue),
				"-max",
				String.valueOf(maxvalue),
				input.getAbsolutePath(),
		};
		
		pnmtofitsCommon(input, output, workDir, params);
		
	}
	
	private void pnmtofitsCommon(File input, File output, File workDir, String[] params) throws Exception{
		
		Executor exec = new Executor();
		
		//pnm -> jpg
		//EXAMPLE:  /usr/bin/pnmtojpeg --greyscale --optimize --progressive M30_01.pnm > M30_01.jpg
				
		String cmd = "/usr/bin/pnmtofits";
		
		OutputStream out = null;  
		 
		try{  
			out = new FileOutputStream(output.getAbsolutePath());  
			
			ExecResult result = exec.execute(cmd, params, out);
			if (result.getCode() != 0) throw new Exception("ConverterNetpbm.pnmtofits(). Error:" + result.getCode() + ", " + result.getErr());
		     
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

	@Override
	public void bmptopnm(File input, File output, File workDir) throws Exception {
		
		String fileName = input.getName();
		if (!fileName.endsWith(".bmp")) throw new Exception("The input file is not a bmp file: " + input.getAbsolutePath());
		
		Executor exec = new Executor();
		
		//bmp -> jpg
		//Example: /usr/bin/bmptopnm file.bmp > file.pnm
				
		String cmd = "/usr/bin/bmptopnm";
		String[] params = { 
				input.getAbsolutePath(),
		};
		
		OutputStream out = null;  
		 
		try{  
			out = new FileOutputStream(output.getAbsolutePath());  
			
			ExecResult result = exec.execute(cmd, params, out);
			if (result.getCode() != 0) throw new Exception("ConverterNetpbm.bmptopnm(). Error:" + result.getCode() + ", " + result.getErr());
		     
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
