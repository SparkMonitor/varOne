package com.varone.exec;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.apache.catalina.Context;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;

import com.varone.web.util.VarOneEnv;

public class TomcatServer {

	public static void main(String args[]){
		try{
			
		   ProtectionDomain protectionDomain = TomcatServer.class.getProtectionDomain();
		   URL warUrl = protectionDomain.getCodeSource().getLocation();
		   String warFilePath = warUrl.getFile();
		
		   
		   TomcatJarFileLauncher launcher = new TomcatJarFileLauncher(warFilePath);
		   VarOneEnv varoneEnv = new VarOneEnv();
		   
		   File tempJarPath = varoneEnv.createVarOneTempJarPath();		   
		   launcher.copyJarToTemp(tempJarPath.getAbsolutePath());
		   launcher.dynamicLoadTomcatJar(tempJarPath);
		   
		   
		   Tomcat tomcat = new Tomcat();
		   File warPath = varoneEnv.createVarOneWarPath();
		
		   tomcat.getHost().setAppBase(warPath.getAbsolutePath());   
		   tomcat.setPort(8080);
		   tomcat.setBaseDir(warPath.getAbsolutePath());
		   
	       Context context = tomcat.addWebapp(VarOneEnv.WEBAPPROOTNAME, warFilePath);	
	       context.setConfigFile(new File("/home/user1/context.xml").toURI().toURL());
	       
		   tomcat.start();
		   
		   launcher.dynamicLoadTomcatJar(new File("/home/user1/.varone/war/varOne-web/WEB-INF/lib"));
		   
		   tomcat.getServer().await();
		   
		
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
