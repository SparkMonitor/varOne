/**
 * 
 */
package com.varone.node.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import com.varone.node.MetricTuple;
import com.varone.node.MetricsType;
import com.varone.node.reader.JvmMetricsReader;
import com.varone.node.reader.MetricsReader;

import junit.framework.TestCase;

/**
 * @author allen
 *
 */
public class JvmMetricsReaderTest extends TestCase {

	private String metricsDir;

	/**
	 * @param name
	 */
	public JvmMetricsReaderTest(String name) {
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
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+).jvm.(?!total).*\\.csv");
		
		assertEquals(result.size(), 38);  
		this.assertContent(filter, result);
	}

	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_HEAP_COMMITED() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_HEAP_COMMITED);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_HEAP_COMMITED.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_HEAP_INIT() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_HEAP_INIT);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_HEAP_INIT.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_HEAP_MAX() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_HEAP_MAX);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_HEAP_MAX.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_HEAP_USAGE() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_HEAP_USAGE);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_HEAP_USAGE.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_HEAP_USED() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_HEAP_USED);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_HEAP_USED.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_NON_HEAP_COMMITED() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_NON_HEAP_COMMITED);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_NON_HEAP_COMMITED.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_NON_HEAP_INIT() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_NON_HEAP_INIT);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_NON_HEAP_INIT.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_NON_HEAP_MAX() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_NON_HEAP_MAX);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_NON_HEAP_MAX.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_NON_HEAP_USAGE() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_NON_HEAP_USAGE);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_NON_HEAP_USAGE.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_NON_HEAP_USED() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_NON_HEAP_USED);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_NON_HEAP_USED.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_POOLS_CODE_CACHE_USAGE() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_POOLS_CODE_CACHE_USAGE);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_POOLS_CODE_CACHE_USAGE.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_POOLS_PS_EDEN_SPACE_USAGE() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_POOLS_PS_EDEN_SPACE_USAGE);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_POOLS_PS_EDEN_SPACE_USAGE.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_POOLS_PS_OLD_GEN_USAGE() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_POOLS_PS_OLD_GEN_USAGE);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_POOLS_PS_OLD_GEN_USAGE.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_POOLS_PS_PERM_GEN_USAGE() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_POOLS_PS_PERM_GEN_USAGE);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_POOLS_PS_PERM_GEN_USAGE.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_POOLS_PS_SURVIVOR_SPACE_USAGE() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_POOLS_PS_SURVIVOR_SPACE_USAGE);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_POOLS_PS_SURVIVOR_SPACE_USAGE.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_PS_MARKSWEEP_COUNT() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_PS_MARKSWEEP_COUNT);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_PS_MARKSWEEP_COUNT.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_PS_MARKSWEEP_TIME() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_PS_MARKSWEEP_TIME);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_PS_MARKSWEEP_TIME.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_PS_SCAVENGE_COUNT() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_PS_SCAVENGE_COUNT);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_PS_SCAVENGE_COUNT.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.varone.node.reader.JvmMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testRead_JVM_PS_SCAVENGE_TIME() throws IOException {
		MetricsReader reader = new JvmMetricsReader(null, MetricsType.JVM_PS_SCAVENGE_TIME);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.JVM_PS_SCAVENGE_TIME.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
}
