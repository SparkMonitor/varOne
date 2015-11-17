/**
 * 
 */
package com.haredb.sparkmonitor.node.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.haredb.sparkmonitor.node.MetricTuple;
import com.haredb.sparkmonitor.node.MetricsType;

/**
 * @author allen
 *
 */
public class NodeMetricsReaderTest extends TestCase {

	/**
	 * @param name
	 */
	public NodeMetricsReaderTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.NodeMetricsReader#readMetricsByApplicationId(java.lang.String, java.util.List)}.
	 * @throws Exception 
	 */
	public void testReadFSAndTPMetrics() throws Exception {
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		String applicationId = "application_1439169262151_0237";
		List<MetricsType> metricsType = new ArrayList<MetricsType>();
		
		metricsType.add(MetricsType.FS);
		metricsType.add(MetricsType.THREAD_POOL);
		
		NodeMetricsReader reader = new NodeMetricsReader(metricsDir);
		
		Map<String, List<MetricTuple>> metricsData = reader.readMetricsByApplicationId(applicationId, metricsType);
		
		assertEquals(metricsData.size(), 28);
		
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.NodeMetricsReader#readMetricsByApplicationId(java.lang.String, java.util.List)}.
	 * @throws Exception 
	 */
	public void testReadFSAndJVMMetrics() throws Exception {
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		String applicationId = "application_1439169262151_0237";
		List<MetricsType> metricsType = new ArrayList<MetricsType>();
		
		metricsType.add(MetricsType.FS);
		metricsType.add(MetricsType.JVM);
		
		NodeMetricsReader reader = new NodeMetricsReader(metricsDir);
		
		Map<String, List<MetricTuple>> metricsData = reader.readMetricsByApplicationId(applicationId, metricsType);
		
		assertEquals(metricsData.size(), 58);
		
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.NodeMetricsReader#readMetricsByApplicationId(java.lang.String, java.util.List)}.
	 * @throws Exception 
	 */
	public void testReadAllByGroup() throws Exception {
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		String applicationId = "application_1439169262151_0237";
		List<MetricsType> metricsType = new ArrayList<MetricsType>();
		
		metricsType.add(MetricsType.FS);
		metricsType.add(MetricsType.JVM);
		metricsType.add(MetricsType.THREAD_POOL);
		
		NodeMetricsReader reader = new NodeMetricsReader(metricsDir);
		
		Map<String, List<MetricTuple>> metricsData = reader.readMetricsByApplicationId(applicationId, metricsType);
		
		assertEquals(metricsData.size(), 66);
		
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.NodeMetricsReader#readMetricsByApplicationId(java.lang.String, java.util.List)}.
	 * @throws Exception 
	 */
	public void testReadFSWithDuplcateMetrics() throws Exception {
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		String applicationId = "application_1439169262151_0237";
		List<MetricsType> metricsType = new ArrayList<MetricsType>();
		
		metricsType.add(MetricsType.FS);
		metricsType.add(MetricsType.EXEC_FS_HDFS_LARGEREAD_OPS);
		metricsType.add(MetricsType.EXEC_FS_HDFS_WRITE_OPS);
		
		NodeMetricsReader reader = new NodeMetricsReader(metricsDir);
		
		Map<String, List<MetricTuple>> metricsData = reader.readMetricsByApplicationId(applicationId, metricsType);
		
		assertEquals(metricsData.size(), 20);
		
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.NodeMetricsReader#readMetricsByApplicationId(java.lang.String, java.util.List)}.
	 * @throws Exception 
	 */
	public void testReadJVMWithDuplcateMetrics() throws Exception {
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		String applicationId = "application_1439169262151_0237";
		List<MetricsType> metricsType = new ArrayList<MetricsType>();
		
		metricsType.add(MetricsType.JVM);
		metricsType.add(MetricsType.JVM_HEAP_COMMITED);
		metricsType.add(MetricsType.JVM_HEAP_USAGE);
		metricsType.add(MetricsType.JVM_HEAP_INIT);
		
		NodeMetricsReader reader = new NodeMetricsReader(metricsDir);
		
		Map<String, List<MetricTuple>> metricsData = reader.readMetricsByApplicationId(applicationId, metricsType);
		
		assertEquals(metricsData.size(), 38);
		
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.NodeMetricsReader#readMetricsByApplicationId(java.lang.String, java.util.List)}.
	 * @throws Exception 
	 */
	public void testReadThreadPoolWithDuplcateMetrics() throws Exception {
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		String applicationId = "application_1439169262151_0237";
		List<MetricsType> metricsType = new ArrayList<MetricsType>();
		
		metricsType.add(MetricsType.THREAD_POOL);
		metricsType.add(MetricsType.EXEC_THREADPOOL_ACTIVETASK);
		
		NodeMetricsReader reader = new NodeMetricsReader(metricsDir);
		
		Map<String, List<MetricTuple>> metricsData = reader.readMetricsByApplicationId(applicationId, metricsType);
		
		assertEquals(metricsData.size(), 8);
		
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.NodeMetricsReader#readMetricsByApplicationId(java.lang.String, java.util.List)}.
	 * @throws Exception 
	 */
	public void testReadAll() throws Exception {
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		String applicationId = "application_1439169262151_0237";
		List<MetricsType> metricsType = new ArrayList<MetricsType>();
		
		metricsType.add(MetricsType.EXEC_FS_LOCAL_LARGEREAD_OPS);
		metricsType.add(MetricsType.EXEC_FS_LOCAL_READ_BYTES);
		metricsType.add(MetricsType.EXEC_FS_LOCAL_READ_OPS);
		metricsType.add(MetricsType.EXEC_FS_LOCAL_WRITE_BYTES);
		metricsType.add(MetricsType.EXEC_FS_LOCAL_WRITE_OPS);
		
		metricsType.add(MetricsType.EXEC_FS_HDFS_LARGEREAD_OPS);
		metricsType.add(MetricsType.EXEC_FS_HDFS_READ_BYTES);
		metricsType.add(MetricsType.EXEC_FS_HDFS_READ_OPS);
		metricsType.add(MetricsType.EXEC_FS_HDFS_WRITE_BYTES);
		metricsType.add(MetricsType.EXEC_FS_HDFS_WRITE_OPS);
		
		metricsType.add(MetricsType.EXEC_THREADPOOL_ACTIVETASK);
		metricsType.add(MetricsType.EXEC_THREADPOOL_COMPLETETASK);
		metricsType.add(MetricsType.EXEC_THREADPOOL_CURRPOOL_SIZE);
		metricsType.add(MetricsType.EXEC_THREADPOOL_MAXPOOL_SIZE);
		
		metricsType.add(MetricsType.JVM_HEAP_COMMITED);
		metricsType.add(MetricsType.JVM_HEAP_INIT);
		metricsType.add(MetricsType.JVM_HEAP_MAX);
		metricsType.add(MetricsType.JVM_HEAP_USAGE);
		metricsType.add(MetricsType.JVM_HEAP_USED);
		
		metricsType.add(MetricsType.JVM_NON_HEAP_COMMITED);
		metricsType.add(MetricsType.JVM_NON_HEAP_INIT);
		metricsType.add(MetricsType.JVM_NON_HEAP_MAX);
		metricsType.add(MetricsType.JVM_NON_HEAP_USAGE);
		metricsType.add(MetricsType.JVM_NON_HEAP_USED);
		
		metricsType.add(MetricsType.JVM_POOLS_CODE_CACHE_USAGE);
		metricsType.add(MetricsType.JVM_POOLS_PS_EDEN_SPACE_USAGE);
		metricsType.add(MetricsType.JVM_POOLS_PS_OLD_GEN_USAGE);
		metricsType.add(MetricsType.JVM_POOLS_PS_PERM_GEN_USAGE);
		metricsType.add(MetricsType.JVM_POOLS_PS_SURVIVOR_SPACE_USAGE);
		
		metricsType.add(MetricsType.JVM_PS_MARKSWEEP_COUNT);
		metricsType.add(MetricsType.JVM_PS_MARKSWEEP_TIME);
		metricsType.add(MetricsType.JVM_PS_SCAVENGE_COUNT);
		metricsType.add(MetricsType.JVM_PS_SCAVENGE_TIME);
		
		NodeMetricsReader reader = new NodeMetricsReader(metricsDir);
		
		Map<String, List<MetricTuple>> metricsData = reader.readMetricsByApplicationId(applicationId, metricsType);
		
		assertEquals(metricsData.size(), 66);
		
	}

}
