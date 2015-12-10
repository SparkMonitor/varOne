package com.varone.web.util;

import java.io.File;

public class VarOneEnv {
	public static String WEBAPPROOTNAME = "varOne-web";
	
	public static String VARONE_HOME_NAME = ".varone";
	public static String CONFIG = "conf";
	public static String WAR = "war";
	public static String TEMPJAR = "jar";
	
	public static String YARNSITEFILENAME = "yarn-site.xml";
	public static String HDFSSITEFILENAME = "hdfs-site.xml";
	public static String CORESITEFILENAME = "core-site.xml";
	
	public File getVarOneHomePath(){
		return this.getVarOneHomePath(System.getProperty("user.home"), VARONE_HOME_NAME);
	}
	
	public File getVarOneHomePath(String homeDir, String homeName){
		File homePath = new File(new File(homeDir), homeName);
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
	
	public File getVarOneWarPath(){
		File warPath = new File(this.getVarOneHomePath(), WAR);
		return warPath;
	}
	
	public File createVarOneWarPath(){
		File warPath = getVarOneWarPath();
		this.mkdir(warPath);
		return warPath;
	}
	
	public File getVarOneTempJarPath(){
		File jarPath = new File(this.getVarOneHomePath(), TEMPJAR);
		return jarPath;
	}
	
	public File createVarOneTempJarPath(){
		File jarPath = getVarOneTempJarPath();
		this.mkdir(jarPath);
		return jarPath;
	}
	
	public boolean checkHadoopConfXMLFile(File confPath){
		File hdfsSite = new File(confPath, HDFSSITEFILENAME);
		File yarnSite = new File(confPath, YARNSITEFILENAME);
		File coreSite = new File(confPath, CORESITEFILENAME);
		
		if(hdfsSite.exists() && yarnSite.exists() && coreSite.exists()){
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
