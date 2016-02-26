/**
 * 
 */
package com.varone.web.yarn.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.exceptions.YarnException;

import com.varone.web.yarn.service.YarnService;

import junit.framework.TestCase;

/**
 * @author user1
 *
 */
public class YarnServiceTest extends TestCase {
	private Configuration config;
	/**
	 * @param name
	 */
	public YarnServiceTest(String name) {
		super(name);
		this.config = new Configuration();
		this.config.set("fs.default.name", "hdfs://server-a1:9000");
		this.config.set("yarn.resourcemanager.address", "server-a1:8032");
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test method for {@link com.varone.web.yarn.service.YarnService#getRunningSparkApplications()}.
	 * @throws IOException 
	 * @throws YarnException 
	 */
	public void testGetRunningSparkApplication() throws YarnException, IOException {
		
		YarnService service = new YarnService(this.config);
		List<String> runningSparkAppId = service.getRunningSparkApplications();	
		//TODO
		//assertEquals(runningSparkAppId.size(), 1);
		service.close();
	}

}
