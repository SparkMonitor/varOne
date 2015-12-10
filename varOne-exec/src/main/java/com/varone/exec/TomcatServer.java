package com.varone.exec;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.apache.catalina.startup.Tomcat;

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
		   tomcat.setPort(8888);
		   
		   tomcat.setBaseDir(varoneEnv.createVarOneWarPath().getAbsolutePath());
	       tomcat.addWebapp(VarOneEnv.WEBAPPROOTNAME, warFilePath);		
		   tomcat.start();
		   tomcat.getServer().await();
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
