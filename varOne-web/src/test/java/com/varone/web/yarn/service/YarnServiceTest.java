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
	 * Test method for {@link com.varone.web.yarn.service.YarnService#getAllNodeHost()}.
	 * @throws IOException 
	 * @throws YarnException 
	 */
	public void testGetAllNodeHost() throws YarnException, IOException {
		
		List<String> hosts = Arrays.asList(new String[]{"server-a2","server-a3","server-a4","server-a5","server-a1"});
		
		YarnService service = new YarnService(this.config);
		List<String> allNodeHost = service.getAllNodeHost();	
		assertNotNull(allNodeHost);
		assertTrue(allNodeHost.size() > 0);
		 
		for(String hostname: allNodeHost){
			assertTrue(hosts.contains(hostname));
		}
		 
		service.close();
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
