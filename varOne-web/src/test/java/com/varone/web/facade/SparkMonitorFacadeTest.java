package com.varone.web.facade;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.junit.Test;

import com.varone.web.util.VarOneEnv;

public class SparkMonitorFacadeTest {
	
	@Test
	public void testConfiguration(){
		try{
			String coreSiteFileName = "core-site.xml";
			
			File hadoopConf = new File("hadoopConf", coreSiteFileName);
			
			
			VarOneEnv env = new VarOneEnv();
			File homePath = env.getVarOneHomePath(System.getProperty("user.home"), ".testVarOne");
			File configPath = new File(homePath, "config");
			if(!configPath.exists()){
				configPath.mkdir();
			}
			FileUtils.copyFile(hadoopConf, new File(configPath, coreSiteFileName));
	
			SparkMonitorFacade facade = new SparkMonitorFacade();
	
			Configuration config = facade.loadConfiguration(homePath);
			
			assertEquals("hdfs://server-a1:9000", config.get("fs.defaultFS"));
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}
