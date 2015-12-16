/**
 * 
 */
package com.varone.web.util;

import static org.junit.Assert.*;

import org.apache.hadoop.conf.Configuration;
import org.junit.Before;
import org.junit.Test;

import com.varone.node.MetricsProperties;

/**
 * @author allen
 *
 */
public class VarOneConfigurationTest {
	
	private VarOneConfiguration varOneConf;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.varOneConf = new VarOneConfiguration();
	}

	/**
	 * Test method for {@link com.varone.web.util.VarOneConfiguration#VarOneConfiguration()}.
	 */
	@Test
	public void testVarOneConfiguration() {
		assertNotNull(this.varOneConf);
	}

	/**
	 * Test method for {@link com.varone.web.util.VarOneConfiguration#loadHadoopConfiguration()}.
	 */
	@Test
	public void testLoadHadoopConfiguration() {
		Configuration config = this.varOneConf.loadHadoopConfiguration();
		assertNotNull(config);
		assertNotNull(config.get("fs.default.name"));
		assertNotNull(config.get("yarn.resourcemanager.address"));
		assertNotNull(config.get("spark.eventLog.dir"));
	}

	/**
	 * Test method for {@link com.varone.web.util.VarOneConfiguration#loadMetricsConfiguration()}.
	 */
	@Test
	public void testLoadMetricsConfiguration() {
		MetricsProperties metricsConfiguration = this.varOneConf.loadMetricsConfiguration();
		assertNotNull(metricsConfiguration);
		assertNotNull(metricsConfiguration.getCsvDir());
		assertNotNull(metricsConfiguration.getCsvPeriod());
		assertNotNull(metricsConfiguration.getCsvUnit());
	}

}
