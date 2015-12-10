package com.varone.exec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TomcatJarFileLauncher {
	
	private String warFilePath;
	private final String[] tomcatJars = {
			"libs/tomcat-api-7.0.57.jar", "libs/tomcat-el-api-7.0.57.jar", "libs/tomcat-embed-core-7.0.57.jar",
			"libs/tomcat-embed-el-7.0.57.jar", "libs/tomcat-embed-jasper-7.0.57.jar", "libs/tomcat-embed-logging-juli-7.0.57.jar",
			"libs/tomcat-jasper-7.0.57.jar", "libs/tomcat-jasper-el-7.0.57.jar", "libs/tomcat-jsp-api-7.0.57.jar",
			"libs/tomcat-juli-7.0.57.jar", "libs/tomcat-servlet-api-7.0.57.jar", "libs/tomcat-util-7.0.57.jar",
			"libs/tomcat-catalina-7.0.57.jar"
	};
	
	public TomcatJarFileLauncher(String warFilePath){
		this.warFilePath = warFilePath;
	}
	
	public String[] copyJarToTemp(String tempFilePath) throws IOException{
		JarFile jarFile = new JarFile(this.warFilePath);
		for(String tomcatJar : this.tomcatJars){
			JarEntry jarEntry = jarFile.getJarEntry(tomcatJar);
			InputStream inStream = jarFile.getInputStream(jarEntry);
				
			OutputStream outStream = new FileOutputStream(new File(tempFilePath, new File(tomcatJar).getName()));
			byte[] buffer = new byte[8192];
			int readLength;
			while((readLength = inStream.read(buffer)) > 0){
				outStream.write(buffer, 0, readLength);
			}
			outStream.close();
		}
		return this.tomcatJars;
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
}
