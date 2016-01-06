package com.varone.exec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TomcatJarFileLauncher {
	private String warFilePath;
	private final String[] tomcatJars = {
			"libs/tomcat-catalina-7.0.57.jar",  
			"libs/tomcat-juli-7.0.57.jar",
			"libs/tomcat-util-7.0.57.jar",
			"libs/tomcat-servlet-api-7.0.57.jar",
			"libs/tomcat-api-7.0.57.jar",
			"libs/tomcat-embed-core-7.0.57.jar",
			"libs/tomcat-embed-jasper-7.0.57.jar",
			"libs/tomcat-jsp-api-7.0.57.jar",
			"libs/jersey-core-1.9.jar", 
			"libs/jersey-server-1.9.jar",
			"libs/asm-3.1.jar", 
			"libs/log4j-1.2.17.jar",
			"libs/commons-cli-1.2.jar",
			"libs/commons-io-2.4.jar"
	};
	
	private final String resourcePath = "WEB-INF/classes";

	private final String[] tomcatContextFile = {"conf/context.xml", "conf/server.xml"};
	
	public TomcatJarFileLauncher(String warFilePath){
		this.warFilePath = warFilePath;
	}
	
	public String[] copyJarFileToTemp(String tempdstJarPath) throws IOException{
		this.copyFileToTemp(tempdstJarPath, tomcatJars);
		return this.tomcatJars;
	}
	
	public String[] copyContextFileToTemp(String tempdstTomcatContextPath) throws IOException {
		this.copyFileToTemp(tempdstTomcatContextPath, tomcatContextFile);
		return this.tomcatContextFile;
	}
	
	public Object[] copyResourceFileToTemp(String tempdstResourcePath) throws IOException {
		List<String> resourceFilePath = new ArrayList<String>();
		JarFile jarFile = new JarFile(this.warFilePath);
		Enumeration<JarEntry> jarEntryList = jarFile.entries();
		while(jarEntryList.hasMoreElements()){
			JarEntry jarEntry = jarEntryList.nextElement();
			String fileName = jarEntry.getName();
			if(!jarEntry.isDirectory() && fileName.contains(resourcePath) && !fileName.endsWith(".class")){
				resourceFilePath.add(fileName);
			}
		}
		Object []resourceFileArray = resourceFilePath.toArray();
		this.copyFileToTemp(tempdstResourcePath, resourceFileArray);
		return resourceFileArray;
	}
	

	
	public void dynamicLoadTomcatResource(File tempResourcePath) throws Exception {
        URLClassLoader classLoader
               = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class clazz= URLClassLoader.class;

        // Use reflection
        Method method= clazz.getDeclaredMethod("addURL", new Class[] { URL.class });
        method.setAccessible(true);
       // method.invoke(classLoader, new Object[] { url });
       // File file = new File("/home/user1/varoneconf");
        File file = new File(tempResourcePath.getAbsoluteFile().toString());
        method.invoke(classLoader, new Object[] {file.toURI().toURL()});
       
	}
	
	public void dynamicLoadTomcatJar(File tempJarPath) throws Exception {
        URLClassLoader classLoader
               = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class clazz= URLClassLoader.class;

        // Use reflection
        Method method= clazz.getDeclaredMethod("addURL", new Class[] { URL.class });
        method.setAccessible(true);
       // method.invoke(classLoader, new Object[] { url });
        File []jarFileList = tempJarPath.listFiles();
        for(File file : jarFileList){
        	method.invoke(classLoader, new Object[] {file.toURI().toURL()});
        }
	}
	
	private void copyFileToTemp(String tempdstPath, Object[] srcJars) throws IOException{
		JarFile jarFile = new JarFile(this.warFilePath);
		for(Object tomcatJar : srcJars){
			JarEntry jarEntry = jarFile.getJarEntry(tomcatJar.toString());
			InputStream inStream = jarFile.getInputStream(jarEntry);
				
			OutputStream outStream = new FileOutputStream(new File(tempdstPath, new File(tomcatJar.toString()).getName()));
			byte[] buffer = new byte[8192];
			int readLength;
			while((readLength = inStream.read(buffer)) > 0){
				outStream.write(buffer, 0, readLength);
			}
			outStream.close();
		}
	}
	
}
