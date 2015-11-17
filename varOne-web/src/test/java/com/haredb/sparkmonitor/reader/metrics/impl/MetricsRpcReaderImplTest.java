/**
 * 
 */
package com.haredb.sparkmonitor.reader.metrics.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.haredb.sparkmonitor.metrics.bean.MetricBean;
import com.haredb.sparkmonitor.metrics.bean.NodeBean;
import com.haredb.sparkmonitor.reader.metrics.MetricsReader;
import com.haredb.sparkmonitor.reader.metrics.impl.MetricsRpcReaderImpl;

/**
 * @author allen
 *
 */
public class MetricsRpcReaderImplTest extends TestCase {
	final static List<String> HOSTS = new ArrayList<String>(){{add("server-a2");add("server-a3");add("server-a4");add("server-a5");}};
	/**
	 * @param name
	 */
	public MetricsRpcReaderImplTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link com.haredb.sparkmonitor.reader.metrics.impl.MetricsRpcReaderImpl#getAllNodeMetrics(java.lang.String, java.util.List)}.
	 * @throws Exception 
	 */
	public void testGetNodeMetrics() throws Exception {
		MetricsReader reader = new MetricsRpcReaderImpl(HOSTS);
		String applicationId = "application_1439169262151_0237";
		List<String> metricsType = new ArrayList<String>();
		
		metricsType.add("FS");
		
		List<NodeBean> nodeMetrics = reader.getAllNodeMetrics(applicationId, metricsType);
		
		int totalMetricsNum = 0;
		assertEquals(nodeMetrics.size(), HOSTS.size());
		for(NodeBean nodeVO: nodeMetrics){
			assertTrue(HOSTS.contains(nodeVO.getHost()));
			assertNotNull(nodeVO.getMetrics());
			assertTrue(nodeVO.getMetrics().size() > 0);
			totalMetricsNum += nodeVO.getMetrics().size();
			for(MetricBean metric: nodeVO.getMetrics()){
				System.out.println(metric.getName());
			}
		}
		
		assertEquals(totalMetricsNum, 50);
	}

}
