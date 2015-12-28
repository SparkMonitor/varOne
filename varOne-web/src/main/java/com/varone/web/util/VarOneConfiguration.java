/**
 * 
 */
package com.varone.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import com.varone.node.MetricsProperties;
import com.varone.node.utils.MetricsPropertiesParser;

/**
 * @author allen
 *
 */
public class VarOneConfiguration {
	
	private	VarOneEnv env;
	
	public VarOneConfiguration(){
		this.env = new VarOneEnv();
	}
	
	public Configuration loadHadoopConfiguration(){
		Configuration config = new Configuration();
		File varOneConfPath = this.env.getVarOneConfPath();
		for(File file : varOneConfPath.listFiles()){
			if(file.getName().endsWith(".xml")){
				config.addResource(new Path(file.getAbsolutePath()));
			}
		}
		this.checkConfig(config, varOneConfPath, "fs.default.name");
		this.checkConfig(config, varOneConfPath, "yarn.resourcemanager.address");
		this.checkConfig(config, varOneConfPath, "spark.eventLog.dir");
		
		return config;
	}
	
	public MetricsProperties loadMetricsConfiguration(){
		File varOneConfPath = this.env.getVarOneConfPath();
		String metricsPropertiesFileName = varOneConfPath.list(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				return name.equals(VarOneEnv.METRICSPROPERTIES_FILENAME);
			}
			
		})[0];
		
		MetricsProperties loadProperties = MetricsPropertiesParser.loadProperties(
				new File(varOneConfPath, metricsPropertiesFileName).getAbsolutePath());
		return loadProperties;
	}
	
	public String getVarOneNodePort() throws IOException {
		Properties readVarOneProperties = this.readVarOneProperties();
		return (String) readVarOneProperties.get("varOne.node.port");
	}
	
	public boolean isOneSecondsPeriod(){
		MetricsProperties metricsConfiguration = this.loadMetricsConfiguration();
		return metricsConfiguration.getCsvPeriod().equals("1") 
				&& metricsConfiguration.getCsvUnit().equals("seconds");
	}
	
	private void checkConfig(Configuration config, File varOneConfPath, String key){
		String value = config.get(key);
		if(value == null){
			throw new RuntimeException(varOneConfPath.getAbsolutePath() + "/*.xml not set " + key + " property");
		}
	}
	
	private Properties readVarOneProperties() throws IOException {
		Properties properties = null;
		File varOnePropties = new File(this.env.getVarOneConfPath(), VarOneEnv.VARONE_PROPTIES);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(varOnePropties);
			properties = new Properties();
			properties.load(fis);
		} catch (IOException e){
			throw e;
		} finally {
			if(null != fis) fis.close();
		}
		return properties;
	}
}
