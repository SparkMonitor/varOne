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
			"libs/tomcat-catalina-7.0.57.jar",  
			"libs/tomcat-juli-7.0.57.jar",
			"libs/jersey-core-1.9.jar", 
			"libs/asm-3.1.jar", 
			"libs/commons-cli-1.2.jar"
	};
	private final String[] tomcatContextFile = {"conf/context.xml"};
	
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
	
	private void copyFileToTemp(String tempdstPath, String[] srcJars) throws IOException{
		JarFile jarFile = new JarFile(this.warFilePath);
		for(String tomcatJar : srcJars){
			JarEntry jarEntry = jarFile.getJarEntry(tomcatJar);
			InputStream inStream = jarFile.getInputStream(jarEntry);
				
			OutputStream outStream = new FileOutputStream(new File(tempdstPath, new File(tomcatJar).getName()));
			byte[] buffer = new byte[8192];
			int readLength;
			while((readLength = inStream.read(buffer)) > 0){
				outStream.write(buffer, 0, readLength);
			}
			outStream.close();
		}
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
