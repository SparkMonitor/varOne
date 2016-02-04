package com.varone.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import com.varone.conf.ConfVars;
import com.varone.conf.VarOneConfiguration;
import com.varone.node.MetricsProperties;
import com.varone.node.utils.MetricsPropertiesParser;

public class VarOneEnv {
	public static String CONFIG = "conf";
	
	public static String VARONE_PROPTIES = "varOne.properties";
	public static String VARONE_NODE_PORT = "varOne.node.port";
	
	public static String YARNSITEFILENAME = "yarn-site.xml";
	public static String HDFSSITEFILENAME = "hdfs-site.xml";
	public static String CORESITEFILENAME = "core-site.xml";
	public static String METRICSPROPERTIES_FILENAME = "metrics.properties";
	public static String SPARK_DEFAULT_CONF_FILENAME = "spark-defaults.conf";
	
	private VarOneConfiguration conf;
	
	public VarOneEnv(VarOneConfiguration conf) {
		this.conf = conf;
	}
	
//	public File getVarOneHomePath(){
//		File sysDir = new File(this.conf.getString(ConfVars.VARONE_SYS_DIR));
//		this.mkdir(sysDir);
//		return sysDir;
//	}
//	
//	public File getVarOneHomePath(String homeDir, String homeName){
//		File homePath = new File(new File(homeDir), homeName);
//		this.mkdir(homePath);
//		return homePath;
//	}
//	
//	public File getVarOneConfPath(){
//		File confPath = new File(this.getVarOneHomePath(), CONFIG);
//		return confPath;
//	}
//	
//	public File createVarOneConfPath(){
//		File confPath = getVarOneConfPath();
//		this.mkdir(confPath);
//		return confPath;
//	}
	
//	public void createVarOnePropFile() {
//		File varOnePropties = new File(this.getVarOneConfPath(), VARONE_PROPTIES);
//		if(!varOnePropties.exists()){
//				
//			InputStream input = null;
//			OutputStream output = null;
//			
//			try{
//				ClassLoader classLoader = getClass().getClassLoader();
//				File source = new File(classLoader.getResource(VARONE_PROPTIES).getFile());
//				input = new FileInputStream(source);
//				output = new FileOutputStream(varOnePropties);
//				
//				byte[] buffer = new byte[1024];
//				int bytesRead;
//				while ((bytesRead = input.read(buffer)) > 0) {
//					output.write(buffer, 0, bytesRead);
//				}
//				
//				output.flush();
//			} catch(IOException e){
//				throw new RuntimeException(e);
//			} finally {
//				try{
//					if(input != null) input.close();
//					if(output != null) output.close();
//				} catch(IOException e){}
//			}
//		}
//	}
	
	public MetricsProperties loadMetricsConfiguration(){
		File sparkConfDir = new File(this.conf.getSparkHome(), "conf");
		MetricsProperties loadProperties = MetricsPropertiesParser.loadProperties(
				new File(sparkConfDir, METRICSPROPERTIES_FILENAME).getPath());
		return loadProperties;
	}
	
	public void isOneSecondsPeriod(){
		MetricsProperties metricsConfiguration = this.loadMetricsConfiguration();
		if( !(metricsConfiguration.getCsvPeriod().equals("1") 
				&& metricsConfiguration.getCsvUnit().equals("seconds"))) {
			File sparkConfDir = new File(this.conf.getSparkHome(), "conf");
			
			throw new RuntimeException(
					"Please set *.sink.csv.period=1 and *.sink.csv.unit=seconds in " + 
					new File(sparkConfDir, METRICSPROPERTIES_FILENAME).getPath());
		}
	}
	
	public void isEnableEventLog() throws RuntimeException {
		File sparkConfDir = new File(this.conf.getSparkHome(), "conf");
		File defaultConf = new File(sparkConfDir, SPARK_DEFAULT_CONF_FILENAME);
		Properties prop;
		try {
			InputStream input = new FileInputStream(defaultConf);
			prop = new Properties();
			prop.load(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String enable = prop.getProperty("spark.eventLog.enabled");
		String dir    = prop.getProperty("spark.eventLog.dir");
		
		if (enable == null || enable.length() == 0) {
			throw new RuntimeException("Make sure you have enabled event log in "  +defaultConf.getPath());
		}
		
		if (dir == null || dir.length() == 0) {
			throw new RuntimeException("Make sure you have set the hdfs dir of event log in " + defaultConf.getPath());
		}
	}
	
	public String getEventLogDir() throws IOException {
		File sparkConfDir = new File(this.conf.getSparkHome(), "conf");
		File defaultConf = new File(sparkConfDir, SPARK_DEFAULT_CONF_FILENAME);
		
		InputStream input = new FileInputStream(defaultConf);
		Properties prop = new Properties();
		prop.load(input);

		return prop.getProperty("spark.eventLog.dir");
	}

	public void checkSpecifiedConfigExist(){
		String hadoopConfDir = this.conf.getHadoopConfDir();
		
		File hdfsSite = new File(hadoopConfDir, HDFSSITEFILENAME);
		if(!hdfsSite.exists()) {
			throw new RuntimeException("Make sure " + HDFSSITEFILENAME + " exists in the " + hadoopConfDir);
		}
		
		File yarnSite = new File(hadoopConfDir, YARNSITEFILENAME);
		if(!yarnSite.exists()) {
			throw new RuntimeException("Make sure " + YARNSITEFILENAME + " exists in the " + hadoopConfDir);
		}
		
		File coreSite = new File(hadoopConfDir, CORESITEFILENAME);
		if(!coreSite.exists()) {
			throw new RuntimeException("Make sure " + CORESITEFILENAME + " exists in the " + hadoopConfDir);
		}
		
		File sparkConfDir = new File(this.conf.getSparkHome(), "conf");
		
		File sparkMetrics = new File(sparkConfDir, METRICSPROPERTIES_FILENAME);
		if(!sparkMetrics.exists()) {
			throw new RuntimeException(
					"Make sure " + METRICSPROPERTIES_FILENAME + " exists in the " + sparkConfDir.getPath());
		}
		
		File defaultConf = new File(sparkConfDir, SPARK_DEFAULT_CONF_FILENAME);
		if(!defaultConf.exists()) {
			throw new RuntimeException(
					"Make sure " + SPARK_DEFAULT_CONF_FILENAME + " exists in the " + sparkConfDir.getPath());
		}
	}
	
	private void mkdir(File folderPath){
		if(!folderPath.exists()){
			folderPath.mkdir();
		}
	}

	public Configuration loadHadoopConfiguration(){
		Configuration config = new Configuration();
		File varOneConfPath = new File(this.conf.getHadoopConfDir());
		for(File file : varOneConfPath.listFiles()){
			if(file.getName().endsWith(".xml")){
				config.addResource(new Path(file.getAbsolutePath()));
			}
		}
		
		return config;
	}	
}
