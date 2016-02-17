/**
 * 
 */
package com.varone.node;

import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;

/**
 * @author allen
 *
 */
public class VarOnedConfiguration extends XMLConfiguration {
	private static final String VARONE_SITE_XML = "varOne-site.xml";
	private static final long serialVersionUID = 4749305895693853035L;
	private static VarOnedConfiguration conf;
	
	/**
	 * 
	 */
	public VarOnedConfiguration() {
	}
	
	/**
	 * @param url
	 * @throws ConfigurationException
	 */
	public VarOnedConfiguration(URL url) throws ConfigurationException {
		setDelimiterParsingDisabled(true);
	    load(url);
	}
	
	public static VarOnedConfiguration create() {
		if (conf != null) {
			return conf;
	    }
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    URL url;
	    
	    url = VarOnedConfiguration.class.getResource(VARONE_SITE_XML);
	    
	    if (url == null) {
	    	ClassLoader cl = VarOnedConfiguration.class.getClassLoader();
	    	if (cl != null) {
	    		url = cl.getResource(VARONE_SITE_XML);
	    	}
	    }
	    
	    if (url == null) {
	    	url = classLoader.getResource(VARONE_SITE_XML);
	    }
	    
	    if (url == null) {
	        System.out.println("Failed to load configuration, proceeding with a default");
	        conf = new VarOnedConfiguration();
	    } else {
	    	try {
	    		System.out.println("Load configuration from " + url);
	    		conf = new VarOnedConfiguration(url);
	        } catch (ConfigurationException e) {
	        	System.out.println("Failed to load configuration from " + url + " proceeding with a default " + e.getMessage());
	        	conf = new VarOnedConfiguration();
	        }
	    }
	    
	    return conf;
	}
	
	
	public String getStringValue(String name, String d) {
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
}
