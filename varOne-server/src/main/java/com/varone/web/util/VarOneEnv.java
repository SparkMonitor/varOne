package com.varone.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import com.varone.conf.VarOneConfiguration;
import com.varone.node.MetricsProperties;
import com.varone.node.utils.MetricsPropertiesParser;

public class VarOneEnv {
	public static String VARONE_PROPTIES = "varOne.properties";
	public static String VARONE_NODE_PORT = "varOne.node.port";
	
	public static String YARNSITEFILENAME = "yarn-site.xml";
	public static String HDFSSITEFILENAME = "hdfs-site.xml";
	public static String CORESITEFILENAME = "core-site.xml";
	public static String METRICSPROPERTIES_FILENAME = "metrics.properties";
	public static String SPARK_DEFAULT_CONF_FILENAME = "spark-defaults.conf";
	public static String VARONEDAEMON = "varonedaemond";
	
	private VarOneConfiguration conf;
	
	public VarOneEnv(VarOneConfiguration conf) {
		this.conf = conf;
	}
	
	public MetricsProperties loadMetricsConfiguration(){
		File sparkConfDir = new File(this.conf.getSparkHome(), "conf");
		MetricsProperties loadProperties = MetricsPropertiesParser.loadProperties(
				new File(sparkConfDir, METRICSPROPERTIES_FILENAME).getPath());
		return loadProperties;
	}
	
	public List<String> getDaemonHosts() throws IOException {
		List<String> hostNames = new ArrayList<String>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    URL url;
	    
	    url = VarOneEnv.class.getResource(VARONEDAEMON);
	    
	    if (url == null) {
	    	ClassLoader cl = VarOneEnv.class.getClassLoader();
	    	if (cl != null) {
	    		url = cl.getResource(VARONEDAEMON);
	    	}
	    }
	    
	    if (url == null) {
	    	url = classLoader.getResource(VARONEDAEMON);
	    }
	    
	    if (url == null) {
	        throw new IOException("Make sure " + VARONEDAEMON + " exists in java build path or classpath.");
	    } else {
	    	File daemonFile = new File(url.getPath());
	    	BufferedReader br = new BufferedReader(new FileReader(daemonFile));
	    	String line = null;
	    	while ((line = br.readLine()) != null) {
	    		hostNames.add(line);
	    	}
	    	br.close();
	    }
		
		return hostNames;
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
