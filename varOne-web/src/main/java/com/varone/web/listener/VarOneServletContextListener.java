package com.varone.web.listener;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.varone.node.MetricsProperties;
import com.varone.web.util.VarOneConfiguration;
import com.varone.web.util.VarOneEnv;

public class VarOneServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		VarOneEnv env = new VarOneEnv();
		File varOneConfPath = env.createVarOneConfPath();
		if(!env.checkHadoopConfXMLFile(varOneConfPath)){
			
			String requireFileNames = "";
			List<String> fileNames = env.requireConfFiles();
			
			for(int i=0;i<fileNames.size();i++){
				requireFileNames += fileNames.get(i);
				if(i+1 < fileNames.size())
					requireFileNames += ",";
			}
			
			throw new RuntimeException("Please confirm these files " + requireFileNames + " exist in the " + varOneConfPath);
		}
		
		/**
		 * For first release, the 1 seconds is a requirement for user to 
		 * define in their metrics.properties for the metrics output period  
		 * 
		 **/
		VarOneConfiguration varOneConf = new VarOneConfiguration();
		if(!varOneConf.isOneSecondsPeriod()){
			throw new RuntimeException("Please set *.sink.csv.period=1 and *.sink.csv.unit=seconds in " + VarOneEnv.METRICSPROPERTIES_FILENAME);
		}
		
		env.createVarOnePropFile();
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		
		
	}

}
