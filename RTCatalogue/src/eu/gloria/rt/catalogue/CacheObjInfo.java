package eu.gloria.rt.catalogue;

import java.util.ArrayList;
import java.util.List;

import eu.gloria.tools.log.LogUtil;

public class CacheObjInfo {
	
	private int maxCacheSize;
	private List<ObjInfo> list;
	private boolean verbose;
	
	public CacheObjInfo(int maxCacheSize, boolean verbose){
		
		this.maxCacheSize = maxCacheSize;
		this.verbose = verbose;
		this.list = new ArrayList<ObjInfo>();
		
	}
	
	public synchronized ObjInfo get(String id){
		
		ObjInfo result = null;
		
		if (verbose) LogUtil.info(this, "CacheObjInfo.get():: Cache size = " + list.size());
		
		for (int x = 0; x < list.size(); x++){
			if (list.get(x).getId().equals(id)){
				result = list.get(x);
				if (verbose) LogUtil.info(this, "CacheObjInfo.get():: Object[" + id + "] found in cache!!!");
				break;
			}
		}
		
		if (result != null){
			
			list.remove(result);
			list.add(result);
			
			if (verbose) LogUtil.info(this, "CacheObjInfo.get():: Object[" + id + "] marked as accessed.");
			if (verbose) LogUtil.info(this, "CacheObjInfo.get():: Cache size = " + list.size());
			
		}
		
		if (verbose) LogUtil.info(this, "CacheObjInfo.put():: " + this.toString());
		
		return result;
	}
	
	public synchronized void put(ObjInfo item){
		
		if (verbose) LogUtil.info(this, "CacheObjInfo.put()::Cache size = " + list.size());
		
		list.add(item);
		
		if (list.size() > maxCacheSize){
			list.remove(0); //The oldest 
		}
		
		if (verbose) LogUtil.info(this, "CacheObjInfo.put():: Cache size = " + list.size());
		
		if (verbose) LogUtil.info(this, "CacheObjInfo.put():: " + this.toString());
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("CacheObjInfo.Content[");
		
		for (int x = 0; x < list.size(); x++){
			sb.append(list.get(x).getId()).append(", ");
		}
		
		sb.append("]");
		
		return sb.toString();
	}

}
