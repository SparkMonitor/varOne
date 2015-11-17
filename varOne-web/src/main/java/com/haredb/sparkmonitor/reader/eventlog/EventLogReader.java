/**
 * 
 */
package com.haredb.sparkmonitor.reader.eventlog;

import java.util.List;
import java.util.Map;

import com.haredb.sparkmonitor.eventlog.bean.SparkEventLogBean;

/**
 * @author allen
 *
 */
public interface EventLogReader {
	
	public List<SparkEventLogBean> getAllSparkAppLog() throws Exception;
	
	public Map<String, SparkEventLogBean> getAllInProgressLog() throws Exception;
	
	public SparkEventLogBean getInProgressLog(String applicationId) throws Exception;
	
	public SparkEventLogBean getApplicationJobs(String applicationId) throws Exception;
	
	public SparkEventLogBean getJobStages(String applicationId, String jobId) throws Exception;
}
