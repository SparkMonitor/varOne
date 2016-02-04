package com.varone.web.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.varone.conf.VarOneConfiguration;
import com.varone.web.util.VarOneEnv;

public class VarOneServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		VarOneConfiguration conf = VarOneConfiguration.create();
		VarOneEnv env = new VarOneEnv(conf);
		
//		File varOneConfPath = env.createVarOneConfPath();
		
		env.checkSpecifiedConfigExist();
		env.isEnableEventLog();
		/**
		 * For first release, the 1 seconds is a requirement for user to 
		 * define in their metrics.properties for the metrics output period  
		 * 
		 **/
		env.isOneSecondsPeriod();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		
		
	}

}
