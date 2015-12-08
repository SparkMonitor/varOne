package com.varone.web.listener;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
		
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		
		
	}

}
