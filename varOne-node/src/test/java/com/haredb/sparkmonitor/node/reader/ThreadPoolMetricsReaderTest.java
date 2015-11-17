/**
 * 
 */
package com.haredb.sparkmonitor.node.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import com.haredb.sparkmonitor.node.MetricTuple;
import com.haredb.sparkmonitor.node.MetricsType;

/**
 * @author allen
 *
 */
public class ThreadPoolMetricsReaderTest extends TestCase {

	private String metricsDir;

	/**
	 * @param name
	 */
	public ThreadPoolMetricsReaderTest(String name) {
		super(name);
		this.metricsDir = this.getClass().getResource("/csvsink").getPath();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	private void assertContent(IOFileFilter filter, Map<String, List<MetricTuple>> result) throws IOException{
		for(Entry<String, List<MetricTuple>> entry: result.entrySet()){
			File logFile =  new File(metricsDir+"//"+entry.getKey());
			assertTrue(filter.accept(logFile));
			int lineNum = 0;
			List<MetricTuple> tuples = entry.getValue();
			List<String> lines = FileUtils.readLines(logFile);
			lines.remove(0);
			for(MetricTuple tuple: tuples){
				String[] pair = lines.get(lineNum).split(",");
				assertEquals(pair[0], String.valueOf(tuple.getTime()));
				assertEquals(pair[1], tuple.getValue());
				lineNum++;
			}
		}
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.ThreadPoolMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_THREAD_POOL() throws IOException {
		MetricsReader reader = new ThreadPoolMetricsReader(null, MetricsType.THREAD_POOL);
		
		String applicationId = "application_1439169262151_0237";
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+).executor.threadpool.*\\.csv");
		
		assertEquals(result.size(), 8);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.ThreadPoolMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_EXEC_THREADPOOL_ACTIVETASK() throws IOException {
		MetricsReader reader = new ThreadPoolMetricsReader(null, MetricsType.EXEC_THREADPOOL_ACTIVETASK);
		
		String applicationId = "application_1439169262151_0237";
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_THREADPOOL_ACTIVETASK.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.ThreadPoolMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_EXEC_THREADPOOL_COMPLETETASK() throws IOException {
		MetricsReader reader = new ThreadPoolMetricsReader(null, MetricsType.EXEC_THREADPOOL_COMPLETETASK);
		
		String applicationId = "application_1439169262151_0237";
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_THREADPOOL_COMPLETETASK.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.ThreadPoolMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_EXEC_THREADPOOL_CURRPOOL_SIZE() throws IOException {
		MetricsReader reader = new ThreadPoolMetricsReader(null, MetricsType.EXEC_THREADPOOL_CURRPOOL_SIZE);
		
		String applicationId = "application_1439169262151_0237";
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_THREADPOOL_CURRPOOL_SIZE.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.ThreadPoolMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_EXEC_THREADPOOL_MAXPOOL_SIZE() throws IOException {
		MetricsReader reader = new ThreadPoolMetricsReader(null, MetricsType.EXEC_THREADPOOL_MAXPOOL_SIZE);
		
		String applicationId = "application_1439169262151_0237";
		String metricsDir = this.getClass().getResource("/csvsink").getPath();
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_THREADPOOL_MAXPOOL_SIZE.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}

}
