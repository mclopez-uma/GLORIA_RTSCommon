package eu.gloria.tools.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	public static String fileNameWithoutExtension(String fileName){
		
		String result;
		
		if (!fileName.contains(".")) 
		    result = fileName;
		else {
		    result =  fileName.substring(0, fileName.lastIndexOf("."));
		    // Because extension is always after the last '.'
		}
		
		return result;
	}
	
	
	public static String readFile(String file) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		FileReader fr = null;
		BufferedReader bf = null;
		
		try{
			String input;
			fr = new FileReader(file);
			bf = new BufferedReader(fr);
			while ((input = bf.readLine())!=null) {
				sb.append(input);
			}
			
			return sb.toString();
			
		}catch(Exception ex){
			throw ex;
		}finally{
			try {
				bf.close();
			}catch(Exception ex){
				//Nothing
			}
			
		}
		
	}
	
	public static List<String> readStringLines(String content) throws Exception{
		
		List<String> result = new ArrayList<String>();
		StringReader reader = null;
		BufferedReader bf = null;
		
		try{
			
			String input;
			reader = new StringReader(content);
			bf = new BufferedReader(reader);
			
			while ((input = bf.readLine())!=null) {
				result.add(input);
			}
			
			return result;
			
		}catch(Exception ex){
			
			throw ex;
			
		}finally{
			
			try {
				bf.close();
			}catch(Exception ex){
				//Nothing
			}
			
		}
		
	}
	
	
	
	public static void save (String content, File file, String charset, boolean deleteIfExists) throws IOException{
		
	    if (file.exists() && deleteIfExists) {
	    	file.delete();
	    }
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
		
		try{
			bw.write(content);
		}finally{
			bw.close();
		}
		
	}

}
