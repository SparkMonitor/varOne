package com.var.web.util;

import java.io.File;

public class VarOneEnv {

	public static String VARONE_HOME_NAME = ".varone";
	public static String CONFIG = "conf";
	
	
	public File getVarOneHomePath(){
		File homePath = new File(new File(System.getProperty("user.home")), VARONE_HOME_NAME);
		this.mkdir(homePath);
		return homePath;
	}
	
	public File getVarOneConfPath(){
		File confPath = new File(this.getVarOneHomePath(), CONFIG);
		return confPath;
	}
	
	public File createVarOneConfPath(){
		File confPath = getVarOneConfPath();
		this.mkdir(confPath);
		return confPath;
	}
	
	public boolean checkHadoopConfXMLFile(File confPath){
		File hdfsSite = new File(confPath, "hdfs-site.xml");
		File yarnSite = new File(confPath, "yarn-site.xml");
		
		if(hdfsSite.exists() && yarnSite.exists()){
			return true;
		}else{
			return false;
		}
	}
	
	private void mkdir(File folderPath){
		if(!folderPath.exists()){
			folderPath.mkdir();
		}
	}
	
	
}
