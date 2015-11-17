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
public class FsMetricsReaderTest extends TestCase {
	private String metricsDir;
	/**
	 * @param name
	 */
	public FsMetricsReaderTest(String name) {
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
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_FS() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.FS);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+).executor.filesystem.*\\.csv");
		
		assertEquals(result.size(), 20);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_EXEC_FS_HDFS_LARGEREAD_OPS() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.EXEC_FS_HDFS_LARGEREAD_OPS);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_FS_HDFS_LARGEREAD_OPS.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_EXEC_FS_HDFS_READ_BYTES() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.EXEC_FS_HDFS_READ_BYTES);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_FS_HDFS_READ_BYTES.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_EXEC_FS_HDFS_READ_OPS() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.EXEC_FS_HDFS_READ_OPS);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_FS_HDFS_READ_OPS.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_EXEC_FS_HDFS_WRITE_BYTES() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.EXEC_FS_HDFS_WRITE_BYTES);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_FS_HDFS_WRITE_BYTES.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_EXEC_FS_HDFS_WRITE_OPS() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.EXEC_FS_HDFS_WRITE_OPS);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_FS_HDFS_WRITE_OPS.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_EXEC_FS_LOCAL_LARGEREAD_OPS() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.EXEC_FS_LOCAL_LARGEREAD_OPS);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_FS_LOCAL_LARGEREAD_OPS.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_EXEC_FS_LOCAL_READ_BYTES() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.EXEC_FS_LOCAL_READ_BYTES);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_FS_LOCAL_READ_BYTES.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_EXEC_FS_LOCAL_READ_OPS() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.EXEC_FS_LOCAL_READ_OPS);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_FS_LOCAL_READ_OPS.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_EXEC_FS_LOCAL_WRITE_BYTES() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.EXEC_FS_LOCAL_WRITE_BYTES);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_FS_LOCAL_WRITE_BYTES.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}
	
	/**
	 * Test method for {@link com.haredb.sparkmonitor.node.reader.FsMetricsReader#read(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	public void testReadWith_EXEC_FS_LOCAL_WRITE_OPS() throws IOException {
		MetricsReader reader = new FsMetricsReader(null, MetricsType.EXEC_FS_LOCAL_WRITE_OPS);
		
		String applicationId = "application_1439169262151_0237";
		
		Map<String, List<MetricTuple>> result = reader.read(applicationId, metricsDir);
		IOFileFilter filter = new RegexFileFilter(applicationId+"(.\\d+)."+MetricsType.EXEC_FS_LOCAL_WRITE_OPS.type()+".csv");
		
		assertEquals(result.size(), 2);  
		this.assertContent(filter, result);
	}

}
