package com.varone.exec;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class TomcatEnv {
	
	public static String VARONE_HOME_NAME = ".varone";
	public static String WEBAPPROOTNAME = "varOne-web";
	public static String WAR = "war";
	public static String TEMPJAR = "jar";
	public static String TOMCATCONF = "tomcatconf";
	public static String RESOURCE = "resource";
	
	public File getVarOneWarPath(){
		File warPath = new File(this.getVarOneHomePath(), WAR);
		return warPath;
	}
	
	public File createVarOneWarPath(){
		File warPath = getVarOneWarPath();
		this.mkdir(warPath);
		return warPath;
	}
	
	public File deleteVarOneWarPath(){
		try{
			File warPath = getVarOneWarPath();
			if(warPath.getAbsoluteFile().exists()){
				FileUtils.deleteDirectory(warPath);
			}
			return warPath;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public File deleteVarOneResourcePath() {
		try{
			File resourcePath = getVarOneTempResourcePath();
			if(resourcePath.getAbsoluteFile().exists()){
				FileUtils.deleteDirectory(resourcePath);
			}
			return resourcePath;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
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
	
	public File getVarOneTempResourcePath(){
		File resourcePath = new File(this.getVarOneHomePath(), RESOURCE);
		return resourcePath;
	}
	
	public File createVarOneTempResourcePath(){
		File resourcePath = getVarOneTempResourcePath();
		this.mkdir(resourcePath);
		return resourcePath;
	}
	
	public File getVarOneTempTomcatConf(){
		File confPath = new File(this.getVarOneHomePath(), TOMCATCONF);
		return confPath;
	}
	
	public File createVarOneTempTomcatConfPath(){
		File tomcatConfPath = getVarOneTempTomcatConf();
		this.mkdir(tomcatConfPath);
		return tomcatConfPath;
	}
	
	private String getVarOneHomePath(){
		return this.getVarOneHomePath(System.getProperty("user.home"), VARONE_HOME_NAME).toString();
	}
	
	private File getVarOneHomePath(String homeDir, String homeName){
		File homePath = new File(new File(homeDir), homeName);
		this.mkdir(homePath);
		return homePath;
	}
	
	private void mkdir(File folderPath){
		if(!folderPath.exists()){
			folderPath.mkdir();
		}
	}

}
