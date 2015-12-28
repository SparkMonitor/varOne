package com.varone.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class VarOneEnv {
	
	
	public static String VARONE_HOME_NAME = ".varone";
	public static String CONFIG = "conf";
	
	public static String VARONE_PROPTIES = "varOne.properties";
	
	public static String YARNSITEFILENAME = "yarn-site.xml";
	public static String HDFSSITEFILENAME = "hdfs-site.xml";
	public static String CORESITEFILENAME = "core-site.xml";
	public static String METRICSPROPERTIES_FILENAME = "metrics.properties";
	
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
	
	public void createVarOnePropFile() {
		File varOnePropties = new File(this.getVarOneConfPath(), VARONE_PROPTIES);
		if(!varOnePropties.exists()){
				
			InputStream input = null;
			OutputStream output = null;
			
			try{
				ClassLoader classLoader = getClass().getClassLoader();
				File source = new File(classLoader.getResource(VARONE_PROPTIES).getFile());
				input = new FileInputStream(source);
				output = new FileOutputStream(varOnePropties);
				
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buffer)) > 0) {
					output.write(buffer, 0, bytesRead);
				}
				
				output.flush();
			} catch(IOException e){
				throw new RuntimeException(e);
			} finally {
				try{
					if(input != null) input.close();
					if(output != null) output.close();
				} catch(IOException e){}
			}
		}
	}
	

	public boolean checkHadoopConfXMLFile(File confPath){
		File hdfsSite = new File(confPath, HDFSSITEFILENAME);
		File yarnSite = new File(confPath, YARNSITEFILENAME);
		File coreSite = new File(confPath, CORESITEFILENAME);
		File metriesProperties = new File(confPath, METRICSPROPERTIES_FILENAME);
		
		return (hdfsSite.exists() && yarnSite.exists() 
				&& coreSite.exists() && metriesProperties.exists());
	}
	
	private void mkdir(File folderPath){
		if(!folderPath.exists()){
			folderPath.mkdir();
		}
	}

	public List<String> requireConfFiles() {
		List<String> fileNames = new ArrayList<String>();
		fileNames.add(CORESITEFILENAME);
		fileNames.add(HDFSSITEFILENAME);
		fileNames.add(YARNSITEFILENAME);
		fileNames.add(METRICSPROPERTIES_FILENAME);
		return fileNames;
	}
	
	
}
