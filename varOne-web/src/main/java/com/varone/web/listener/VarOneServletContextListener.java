package com.varone.web.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.varone.web.util.VarOneEnv;

public class VarOneServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		VarOneEnv env = new VarOneEnv();
		File varOneConfPath = env.createVarOneConfPath();
		if(!env.checkHadoopConfXMLFile(varOneConfPath)){
			throw new RuntimeException("Please confirm you hdfs-site.xml, yarn-site.xml, core-site file to the " + varOneConfPath);
		}
		
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		
		
	}

}
