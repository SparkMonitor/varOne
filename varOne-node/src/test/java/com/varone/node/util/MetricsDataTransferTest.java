/**
 * 
 */
package com.varone.node.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto.TupleProto;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;
import com.varone.node.MetricTuple;
import com.varone.node.MetricsType;
import com.varone.node.utils.MetricsDataTransfer;

/**
 * @author allen
 *
 */
public class MetricsDataTransferTest extends TestCase {
	
	/**
	 * @param name
	 */
	public MetricsDataTransferTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link com.varone.node.utils.MetricsDataTransfer#MetricsDataTransfer()}.
	 */
	public void testMetricsDataTransfer() {
		MetricsDataTransfer metricsDataTransfer = new MetricsDataTransfer();
		assertNotNull(metricsDataTransfer);
	}

	/**
	 * Test method for {@link com.varone.node.utils.MetricsDataTransfer#decodeMetricsType(java.util.List)}.
	 * @throws Exception 
	 */
	public void testDecodeMetricsType() throws Exception {
		MetricsDataTransfer metricsDataTransfer = new MetricsDataTransfer();
		
		List<MetricsTypeProto> metricsTypeProtos = new ArrayList<MetricsTypeProto>();
		metricsTypeProtos.add(MetricsTypeProto.FS);
		metricsTypeProtos.add(MetricsTypeProto.JVM);
		metricsTypeProtos.add(MetricsTypeProto.THREAD_POOL);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_FS_HDFS_LARGEREAD_OPS);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_FS_HDFS_READ_BYTES);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_FS_HDFS_READ_OPS);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_FS_HDFS_WRITE_BYTES);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_FS_HDFS_WRITE_OPS);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_FS_LOCAL_LARGEREAD_OPS);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_FS_LOCAL_READ_BYTES);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_FS_LOCAL_READ_OPS);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_FS_LOCAL_WRITE_BYTES);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_FS_LOCAL_WRITE_OPS);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_THREADPOOL_ACTIVETASK);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_THREADPOOL_COMPLETETASK);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_THREADPOOL_CURRPOOL_SIZE);
		metricsTypeProtos.add(MetricsTypeProto.EXEC_THREADPOOL_MAXPOOL_SIZE);
		metricsTypeProtos.add(MetricsTypeProto.JVM_HEAP_COMMITED);
		metricsTypeProtos.add(MetricsTypeProto.JVM_HEAP_INIT);
		metricsTypeProtos.add(MetricsTypeProto.JVM_HEAP_MAX);
		metricsTypeProtos.add(MetricsTypeProto.JVM_HEAP_USAGE);
		metricsTypeProtos.add(MetricsTypeProto.JVM_HEAP_USED);
		metricsTypeProtos.add(MetricsTypeProto.JVM_NON_HEAP_COMMITED);
		metricsTypeProtos.add(MetricsTypeProto.JVM_NON_HEAP_INIT);
		metricsTypeProtos.add(MetricsTypeProto.JVM_NON_HEAP_MAX);
		metricsTypeProtos.add(MetricsTypeProto.JVM_NON_HEAP_USAGE);
		metricsTypeProtos.add(MetricsTypeProto.JVM_NON_HEAP_USED);
		metricsTypeProtos.add(MetricsTypeProto.JVM_POOLS_CODE_CACHE_USAGE);
		metricsTypeProtos.add(MetricsTypeProto.JVM_POOLS_PS_EDEN_SPACE_USAGE);
		metricsTypeProtos.add(MetricsTypeProto.JVM_POOLS_PS_OLD_GEN_USAGE);
		metricsTypeProtos.add(MetricsTypeProto.JVM_POOLS_PS_PERM_GEN_USAGE);
		metricsTypeProtos.add(MetricsTypeProto.JVM_POOLS_PS_SURVIVOR_SPACE_USAGE);
		metricsTypeProtos.add(MetricsTypeProto.JVM_PS_MARKSWEEP_COUNT);
		metricsTypeProtos.add(MetricsTypeProto.JVM_PS_MARKSWEEP_TIME);
		metricsTypeProtos.add(MetricsTypeProto.JVM_PS_SCAVENGE_COUNT);
		metricsTypeProtos.add(MetricsTypeProto.JVM_PS_SCAVENGE_TIME);
		
		
		List<MetricsType> decodeMetricsType = metricsDataTransfer.decodeMetricsType(metricsTypeProtos);
		
		assertEquals(decodeMetricsType.size(), 36);
		assertTrue(decodeMetricsType.contains(MetricsType.FS));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM));
		assertTrue(decodeMetricsType.contains(MetricsType.THREAD_POOL));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_FS_HDFS_LARGEREAD_OPS));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_FS_HDFS_READ_BYTES));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_FS_HDFS_READ_OPS));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_FS_HDFS_WRITE_BYTES));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_FS_HDFS_WRITE_OPS));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_FS_LOCAL_LARGEREAD_OPS));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_FS_LOCAL_READ_BYTES));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_FS_LOCAL_READ_OPS));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_FS_LOCAL_WRITE_BYTES));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_FS_LOCAL_WRITE_OPS));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_THREADPOOL_ACTIVETASK));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_THREADPOOL_COMPLETETASK));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_THREADPOOL_CURRPOOL_SIZE));
		assertTrue(decodeMetricsType.contains(MetricsType.EXEC_THREADPOOL_MAXPOOL_SIZE));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_HEAP_COMMITED));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_HEAP_INIT));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_HEAP_MAX));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_HEAP_USAGE));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_HEAP_USED));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_NON_HEAP_COMMITED));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_NON_HEAP_INIT));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_NON_HEAP_MAX));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_NON_HEAP_USAGE));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_NON_HEAP_USED));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_POOLS_CODE_CACHE_USAGE));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_POOLS_PS_EDEN_SPACE_USAGE));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_POOLS_PS_OLD_GEN_USAGE));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_POOLS_PS_PERM_GEN_USAGE));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_POOLS_PS_SURVIVOR_SPACE_USAGE));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_PS_MARKSWEEP_COUNT));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_PS_MARKSWEEP_TIME));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_PS_SCAVENGE_COUNT));
		assertTrue(decodeMetricsType.contains(MetricsType.JVM_PS_SCAVENGE_TIME));
	}

	/**
	 * Test method for {@link com.varone.node.utils.MetricsDataTransfer#encodeMetricsData(java.util.Map)}.
	 */
	public void testEncodeMetricsData() {
		MetricsDataTransfer metricsDataTransfer = new MetricsDataTransfer();
		
		Map<String, List<MetricTuple>> metricsData = new LinkedHashMap<String, List<MetricTuple>>();
		
		String key1 = "key1";
		List<MetricTuple> metric1 = new ArrayList<MetricTuple>();
		metric1.add(new MetricTuple("1442285869,123"));
		metric1.add(new MetricTuple("1442285870,456"));
		metric1.add(new MetricTuple("1442285871,789"));
		
		String key2 = "key2";
		List<MetricTuple> metric2 = new ArrayList<MetricTuple>();
		metric2.add(new MetricTuple("1442285869,abc"));
		metric2.add(new MetricTuple("1442285870,def"));
		metric2.add(new MetricTuple("1442285871,ghi"));
		
		metricsData.put(key1, metric1);
		metricsData.put(key2, metric2);
		
		List<MetricsMapProto> encodeMetricsData = metricsDataTransfer.encodeMetricsData(metricsData);
		
		assertEquals(encodeMetricsData.size(), 2);
		
		for(MetricsMapProto metricsMapProto: encodeMetricsData){
			assertTrue(metricsData.containsKey(metricsMapProto.getMetricsName()));
			List<MetricTuple> tuples = metricsData.get(metricsMapProto.getMetricsName());
			assertEquals(tuples.size(), metricsMapProto.getMetricsValuesCount());
			int i=0;
			for(TupleProto tupleProto: metricsMapProto.getMetricsValuesList()){
				MetricTuple metricTuple = tuples.get(i);
				assertEquals(metricTuple.getTime(), tupleProto.getTime());
				assertEquals(metricTuple.getValue(), tupleProto.getValue());
				i++;
			}
		}
	}
	
	/**
	 * Test method for {@link com.varone.node.utils.MetricsDataTransfer#encodeMetricsType(java.util.List)}.
	 * @throws Exception 
	 */
	public void testEncodeMetricsType() throws Exception {
		MetricsDataTransfer metricsDataTransfer = new MetricsDataTransfer();
		
		List<String> metricsType = new ArrayList<String>();
		metricsType.add("FS");
		metricsType.add("JVM");
		
		metricsType.add("EXEC_FS_LOCAL_LARGEREAD_OPS");
		metricsType.add("EXEC_FS_LOCAL_READ_BYTES");
		metricsType.add("EXEC_FS_LOCAL_READ_OPS");
		metricsType.add("EXEC_FS_LOCAL_WRITE_BYTES");
		metricsType.add("EXEC_FS_LOCAL_WRITE_OPS");
		
		
		metricsType.add("EXEC_FS_HDFS_LARGEREAD_OPS");
		metricsType.add("EXEC_FS_HDFS_READ_BYTES");
		metricsType.add("EXEC_FS_HDFS_READ_OPS");
		metricsType.add("EXEC_FS_HDFS_WRITE_BYTES");
		metricsType.add("EXEC_FS_HDFS_WRITE_OPS");
		
		metricsType.add("EXEC_THREADPOOL_ACTIVETASK");
		metricsType.add("EXEC_THREADPOOL_COMPLETETASK");
		metricsType.add("EXEC_THREADPOOL_CURRPOOL_SIZE");
		metricsType.add("EXEC_THREADPOOL_MAXPOOL_SIZE");
		
		metricsType.add("JVM_HEAP_COMMITED");
		metricsType.add("JVM_HEAP_INIT");
		metricsType.add("JVM_HEAP_MAX");
		metricsType.add("JVM_HEAP_USAGE");
		metricsType.add("JVM_HEAP_USED");
		
		metricsType.add("JVM_NON_HEAP_COMMITED");
		metricsType.add("JVM_NON_HEAP_INIT");
		metricsType.add("JVM_NON_HEAP_MAX");
		metricsType.add("JVM_NON_HEAP_USAGE");
		metricsType.add("JVM_NON_HEAP_USED");
		
		metricsType.add("JVM_POOLS_CODE_CACHE_USAGE");
		metricsType.add("JVM_POOLS_PS_EDEN_SPACE_USAGE");
		metricsType.add("JVM_POOLS_PS_OLD_GEN_USAGE");
		metricsType.add("JVM_POOLS_PS_PERM_GEN_USAGE");
		metricsType.add("JVM_POOLS_PS_SURVIVOR_SPACE_USAGE");
		
		
		metricsType.add("JVM_PS_MARKSWEEP_COUNT");
		metricsType.add("JVM_PS_MARKSWEEP_TIME");
		metricsType.add("JVM_PS_SCAVENGE_COUNT");
		metricsType.add("JVM_PS_SCAVENGE_TIME");
		
		List<MetricsTypeProto> encodeMetricsType = metricsDataTransfer.encodeMetricsType(metricsType);
		
		assertEquals(encodeMetricsType.size(), metricsType.size());
		
		int i=0;
		for(MetricsTypeProto proto: encodeMetricsType){
			assertEquals(proto.name(), metricsType.get(i++));
		}
	}

}
