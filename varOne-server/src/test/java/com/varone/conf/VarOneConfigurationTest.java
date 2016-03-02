package com.varone.conf;

import static org.junit.Assert.*;

import org.junit.Test;

public class VarOneConfigurationTest {
	@Test
	public void testTimerIntervalConfigDefault(){
		VarOneConfiguration config = VarOneConfiguration.create();
		assertEquals("5000", config.getTimerInterval());
	}
	
	@Test
	public void testTimerIntervalConfig(){
		VarOneConfiguration config = VarOneConfiguration.create();
		config.setProperty("varOne.server.timer.interval", "1000");
		String timerInterval = config.getProperty(ConfVars.VARONE_SERVER_TIMER_INTERVAL.getVarName()).toString();
		assertEquals(timerInterval, "1000");
	}
	
	@Test
	public void testSparkDeployMode(){
		VarOneConfiguration config = VarOneConfiguration.create();
		String deployMode = config.getProperty(ConfVars.SPARK_DEPLOY_MODE.getVarName()).toString();
		assertEquals("yarn", deployMode);
		
		config.setProperty(ConfVars.SPARK_DEPLOY_MODE.getVarName(), "standalone");
		deployMode = config.getProperty(ConfVars.SPARK_DEPLOY_MODE.getVarName()).toString();
		assertEquals("standalone", deployMode);
	}
}
