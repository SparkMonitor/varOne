/**
 * 
 */
package com.varone.conf;

import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.log4j.Logger;

/**
 * @author allen
 *
 */
public class VarOneConfiguration extends XMLConfiguration {
	private static final Logger LOG = Logger.getLogger(VarOneConfiguration.class);
	private static final String VARONE_SITE_XML = "varOne-site.xml";
	private static final long serialVersionUID = 4749305895693853035L;
	private static VarOneConfiguration conf;
	/**
	 * 
	 */
	public VarOneConfiguration() {
		ConfVars[] vars = ConfVars.values();
	    for (ConfVars v : vars) {
	    	if (v.getType() == ConfVars.VarType.BOOLEAN) {
	    		this.setProperty(v.getVarName(), v.getBooleanValue());
	    	} else if (v.getType() == ConfVars.VarType.LONG) {
	    		this.setProperty(v.getVarName(), v.getLongValue());
	    	} else if (v.getType() == ConfVars.VarType.INT) {
	    		this.setProperty(v.getVarName(), v.getIntValue());
	    	} else if (v.getType() == ConfVars.VarType.FLOAT) {
	    		this.setProperty(v.getVarName(), v.getFloatValue());
	    	} else if (v.getType() == ConfVars.VarType.STRING) {
	    		this.setProperty(v.getVarName(), v.getStringValue());
	    	} else {
	    		throw new RuntimeException("Unsupported VarType");
	    	}
	    }
	}
	
	/**
	 * @param url
	 * @throws ConfigurationException
	 */
	public VarOneConfiguration(URL url) throws ConfigurationException {
		setDelimiterParsingDisabled(true);
	    load(url);
	}
	
	public void verifyEnv() {
		if (this.getHadoopConfDir() == null) {
			throw new RuntimeException("Please export HADOOP_CONF_DIR as environment variable");
		}
		
		if (this.getSparkHome() == null) {
			throw new RuntimeException("Please export SPARK_HOME as environment variable");
		}
	}
	
	public String getHadoopConfDir() {
		return System.getenv("HADOOP_CONF_DIR");
	}
	
	public String getSparkHome() {
		return System.getenv("SPARK_HOME");
	}
	
	public static VarOneConfiguration create() {
		if (conf != null) {
			return conf;
	    }
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    URL url;
	    
	    url = VarOneConfiguration.class.getResource(VARONE_SITE_XML);
	    
	    if (url == null) {
	    	ClassLoader cl = VarOneConfiguration.class.getClassLoader();
	    	if (cl != null) {
	    		url = cl.getResource(VARONE_SITE_XML);
	    	}
	    }
	    
	    if (url == null) {
	    	url = classLoader.getResource(VARONE_SITE_XML);
	    }
	    
	    if (url == null) {
	        LOG.warn("Failed to load configuration, proceeding with a default");
	        conf = new VarOneConfiguration();
	    } else {
	    	try {
	    		LOG.info("Load configuration from " + url);
	    		conf = new VarOneConfiguration(url);
	        } catch (ConfigurationException e) {
	        	LOG.warn("Failed to load configuration from " + url + " proceeding with a default", e);
	        	conf = new VarOneConfiguration();
	        }
	    }
	    
	    return conf;
	}
	
	private String getStringValue(String name, String d) {
		List<ConfigurationNode> properties = getRootNode().getChildren();
		if (properties == null || properties.size() == 0) {
			return d;
		}
		for (ConfigurationNode p : properties) {
			if (p.getChildren("name") != null && p.getChildren("name").size() > 0 
					&& name.equals(((ConfigurationNode) p.getChildren("name").get(0)).getValue())) {
				return (String) ((ConfigurationNode) p.getChildren("value").get(0)).getValue();
			}
		}
		return d;
	}

	private int getIntValue(String name, int d) {
		List<ConfigurationNode> properties = getRootNode().getChildren();
		if (properties == null || properties.size() == 0) {
			return d;
		}
		for (ConfigurationNode p : properties) {
			if (p.getChildren("name") != null && p.getChildren("name").size() > 0 
					&& name.equals(((ConfigurationNode) p.getChildren("name").get(0)).getValue())) {
				return Integer.parseInt((String) ((ConfigurationNode) p.getChildren("value").get(0)).getValue());
			}
		}
		return d;	
	}

	private long getLongValue(String name, long d) {
		List<ConfigurationNode> properties = getRootNode().getChildren();
		if (properties == null || properties.size() == 0) {
			return d;
		}
		for (ConfigurationNode p : properties) {
			if (p.getChildren("name") != null && p.getChildren("name").size() > 0 
					&& name.equals(((ConfigurationNode) p.getChildren("name").get(0)).getValue())) {
				return Long.parseLong((String) ((ConfigurationNode) p.getChildren("value").get(0)).getValue());
			}
		}
		return d;	
	}

	private float getFloatValue(String name, float d) {
		List<ConfigurationNode> properties = getRootNode().getChildren();
		if (properties == null || properties.size() == 0) {
			return d;
		}
		for (ConfigurationNode p : properties) {
			if (p.getChildren("name") != null && p.getChildren("name").size() > 0 
					&& name.equals(((ConfigurationNode) p.getChildren("name").get(0)).getValue())) {
				return Float.parseFloat((String) ((ConfigurationNode) p.getChildren("value").get(0)).getValue());
			}
		}
		return d;	
	}
	
	private boolean getBooleanValue(String name, boolean d) {
		List<ConfigurationNode> properties = getRootNode().getChildren();
		if (properties == null || properties.size() == 0) {
			return d;
		}
		for (ConfigurationNode p : properties) {
			if (p.getChildren("name") != null && p.getChildren("name").size() > 0 
					&& name.equals(((ConfigurationNode) p.getChildren("name").get(0)).getValue())) {
				return Boolean.parseBoolean((String) ((ConfigurationNode) p.getChildren("value").get(0)).getValue());	
			}	
		}
		return d;	
	}

	public String getString(ConfVars c) {
		return getString(c.name(), c.getVarName(), c.getStringValue());
	}

	public String getString(String envName, String propertyName, String defaultValue) {
		if (System.getenv(envName) != null) {
			return System.getenv(envName);
		}
		if (System.getProperty(propertyName) != null) {
			return System.getProperty(propertyName);
		}
		return getStringValue(propertyName, defaultValue);
	}

	public int getInt(ConfVars c) {
		return getInt(c.name(), c.getVarName(), c.getIntValue());
	}

	public int getInt(String envName, String propertyName, int defaultValue) {
		if (System.getenv(envName) != null) {
			return Integer.parseInt(System.getenv(envName));
		}
		if (System.getProperty(propertyName) != null) {
			return Integer.parseInt(System.getProperty(propertyName));
		}
		return getIntValue(propertyName, defaultValue);
	}

	public long getLong(ConfVars c) {
		return getLong(c.name(), c.getVarName(), c.getLongValue());
	}

	public long getLong(String envName, String propertyName, long defaultValue) {
		if (System.getenv(envName) != null) {
			return Long.parseLong(System.getenv(envName));
		}
		if (System.getProperty(propertyName) != null) {
			return Long.parseLong(System.getProperty(propertyName));
		}
		return getLongValue(propertyName, defaultValue);
	}

	public float getFloat(ConfVars c) {
		return getFloat(c.name(), c.getVarName(), c.getFloatValue());
	}

	public float getFloat(String envName, String propertyName, float defaultValue) {
		if (System.getenv(envName) != null) {
			return Float.parseFloat(System.getenv(envName));
		}
		if (System.getProperty(propertyName) != null) {
			return Float.parseFloat(System.getProperty(propertyName));
		}
		return getFloatValue(propertyName, defaultValue);
	}

	public boolean getBoolean(ConfVars c) {
		return getBoolean(c.name(), c.getVarName(), c.getBooleanValue());
	}

	public boolean getBoolean(String envName, String propertyName, boolean defaultValue) {
		if (System.getenv(envName) != null) {
			return Boolean.parseBoolean(System.getenv(envName));
		}
		if (System.getProperty(propertyName) != null) {
			return Boolean.parseBoolean(System.getProperty(propertyName));
		}
		return getBooleanValue(propertyName, defaultValue);
	}

	public String getServerContextPath() {
		return getString(ConfVars.VARONE_SERVER_CONTEXT_PATH);
	}

	public String getRelativeDir(ConfVars c) {
		return getRelativeDir(getString(c));
	}
	
	public String getRelativeDir(String path) {
		if (path != null && path.startsWith("/") || isWindowsPath(path)) {
			return path;
		} else {
			return getString(ConfVars.VARONE_HOME) + "/" + path;
		}
	}
	
	public boolean isWindowsPath(String path){
		return path.matches("^[A-Za-z]:\\\\.*");
	}

	public String getServerAddress() {
		return getString(ConfVars.VARONE_SERVER_ADDR);
	}

	public String getTimerInterval() {
		return getString(ConfVars.VARONE_SERVER_TIMER_INTERVAL);
	}
	
	public String getSparkDeployMode() {
		return getString(ConfVars.SPARK_DEPLOY_MODE);
	}
	
	public int getServerPort() {
		return getInt(ConfVars.VARONE_SERVER_PORT);
	}
}
