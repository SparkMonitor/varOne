package com.varone.exec;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

public class TomcatServer {

	public static void main(String args[]) {
		try{
		   ProtectionDomain protectionDomain = TomcatServer.class.getProtectionDomain();
		   URL jarUrl = protectionDomain.getCodeSource().getLocation();
		   String jarFilePath = jarUrl.getFile();
		
		   
		   TomcatJarFileLauncher launcher = new TomcatJarFileLauncher(jarFilePath);
		   TomcatEnv tomcatEnv = new TomcatEnv();
		   
		   File tempdstJarPath = tomcatEnv.createVarOneTempJarPath();		   
		   launcher.copyJarFileToTemp(tempdstJarPath.getAbsolutePath());
		  
		  
		   launcher.dynamicLoadTomcatJar(tempdstJarPath);
		   
		   File tempdstResourcePath = tomcatEnv.getVarOneTempResourcePath();
		   if(tempdstResourcePath.exists()){
			   tomcatEnv.deleteVarOneResourcePath();
		   }
		   tomcatEnv.createVarOneTempResourcePath();
		   
		   launcher.copyResourceFileToTemp(tempdstResourcePath.getAbsolutePath());
		   CommandLine cmd = TomcatServer.parseCommand(args);
		   int port = Integer.parseInt(cmd.getOptionValue("p", "8080"));
		   String log4jProperties = cmd.getOptionValue("l"); 
		   if(log4jProperties != null){
		   	  launcher.dynamicLoadTomcatResource(new File(log4jProperties));
		   }
		   launcher.dynamicLoadTomcatResource(tempdstResourcePath);

		
		   
		   Tomcat tomcat = new Tomcat();
		   
		   File warPath = tomcatEnv.getVarOneWarPath();
		   if(warPath.exists()){
				tomcatEnv.deleteVarOneWarPath();
		   }
		   tomcatEnv.createVarOneWarPath();
		   
		   tomcat.getHost().setAppBase(warPath.getAbsolutePath());   
		   tomcat.setPort(port);
		   tomcat.setBaseDir(warPath.getAbsolutePath());
			   
			   
		   Context context = tomcat.addWebapp(tomcatEnv.WEBAPPROOTNAME, jarFilePath);	
		   
		   
		   
		   File tempdstTomcatContextPath = tomcatEnv.createVarOneTempTomcatConfPath();
		   launcher.copyContextFileToTemp(tempdstTomcatContextPath.getAbsolutePath());  
		   context.setConfigFile(new File(tempdstTomcatContextPath, "context.xml").toURI().toURL());
		  

		  
		   tomcat.start();
		   String warLibPath = warPath.getAbsolutePath() + File.separator + tomcatEnv.WEBAPPROOTNAME + File.separator + "WEB-INF" + File.separator + "lib";
		   launcher.dynamicLoadTomcatJar(new File(warLibPath));

		   
		   
		   ClientConsole console = new ClientConsole();
		   console.createTrayIcon();
		   console.openBrowser("http://localhost:" + port + "/" + TomcatEnv.WEBAPPROOTNAME  + "/index.html");
			   
		   tomcat.getServer().await();
		}catch(Exception e){
		   throw new RuntimeException(e);
		}
	}
	private static CommandLine parseCommand(String []args) {
		HelpFormatter formatter = null;
		Options options = null;
		try{
		   formatter = new HelpFormatter();
		   options = new Options();
		   options.addOption("p", "port", true, "enter web port number");
		   options.addOption("l", "log4j", true, "log4j.properties folder path");
		   PosixParser parser = new PosixParser();
		   return parser.parse(options, args);
		}catch(Exception e){
			formatter.printHelp(" ", options);
			System.err.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
}
