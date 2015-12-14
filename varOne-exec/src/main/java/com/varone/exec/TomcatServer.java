package com.varone.exec;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {

	public static void main(String args[]){
		try{
			
		   ProtectionDomain protectionDomain = TomcatServer.class.getProtectionDomain();
		   URL warUrl = protectionDomain.getCodeSource().getLocation();
		   String warFilePath = warUrl.getFile();
		
		   
		   TomcatJarFileLauncher launcher = new TomcatJarFileLauncher(warFilePath);
		   TomcatEnv tomcatEnv = new TomcatEnv();
		   
		   File tempdstJarPath = tomcatEnv.createVarOneTempJarPath();		   
		   launcher.copyJarFileToTemp(tempdstJarPath.getAbsolutePath());
		   launcher.dynamicLoadTomcatJar(tempdstJarPath);
		   
		   
		   Tomcat tomcat = new Tomcat();
		   File warPath = tomcatEnv.createVarOneWarPath();
		
		   tomcat.getHost().setAppBase(warPath.getAbsolutePath());   
		   tomcat.setPort(8080);
		   tomcat.setBaseDir(warPath.getAbsolutePath());
		   
		   
	       Context context = tomcat.addWebapp(tomcatEnv.WEBAPPROOTNAME, warFilePath);	
	       File tempdstTomcatContextPath = tomcatEnv.createVarOneTempTomcatConfPath();
	       launcher.copyContextFileToTemp(tempdstTomcatContextPath.getAbsolutePath());  
	       System.out.println(tempdstTomcatContextPath);
	       context.setConfigFile(new File(tempdstTomcatContextPath, "context.xml").toURI().toURL());
	      
	       
		   tomcat.start();
		   String warLibPath = warPath.getAbsolutePath() + File.separator + tomcatEnv.WEBAPPROOTNAME + File.separator + "WEB-INF" + File.separator + "lib";
		   launcher.dynamicLoadTomcatJar(new File(warLibPath));
		   
		   tomcat.getServer().await();
		   
		
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
