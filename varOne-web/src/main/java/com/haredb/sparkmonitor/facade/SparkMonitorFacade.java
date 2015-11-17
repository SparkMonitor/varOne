/**
 * 
 */
package com.haredb.sparkmonitor.facade;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;

import com.haredb.sparkmonitor.aggregator.UIDataAggregator;
import com.haredb.sparkmonitor.eventlog.bean.SparkEventLogBean;
import com.haredb.sparkmonitor.eventlog.bean.SparkEventLogBean.AppStart;
import com.haredb.sparkmonitor.metrics.bean.NodeBean;
import com.haredb.sparkmonitor.node.MetricsType;
import com.haredb.sparkmonitor.reader.eventlog.EventLogReader;
import com.haredb.sparkmonitor.reader.eventlog.impl.EventLogHdfsReaderImpl;
import com.haredb.sparkmonitor.reader.metrics.MetricsReader;
import com.haredb.sparkmonitor.reader.metrics.impl.MetricsRpcReaderImpl;
import com.haredb.sparkmonitor.vo.DefaultApplicationVO;
import com.haredb.sparkmonitor.vo.DefaultNodeVO;
import com.haredb.sparkmonitor.vo.DefaultTotalNodeVO;
import com.haredb.sparkmonitor.vo.HistoryVO;
import com.haredb.sparkmonitor.vo.JobVO;
import com.haredb.sparkmonitor.vo.StageVO;
import com.haredb.sparkmonitor.yarn.service.YarnService;

/**
 * @author allen
 *
 */
public class SparkMonitorFacade {
	
	private Configuration config;
	
	/**
	 * 
	 */
	public SparkMonitorFacade() {
		this.config = new Configuration();
		this.config.set("fs.default.name", "hdfs://server-a1:9000");
		this.config.set("yarn.resourcemanager.address", "server-a1:8032");
		this.config.set("spark.eventLog.dir", "/sparklogs");
	}
	
	public DefaultTotalNodeVO getDefaultClusterDashBoard(List<String> metrics) throws Exception{
		DefaultTotalNodeVO result = null;
		YarnService yarnService = new YarnService(this.config);
				
		Map<String, List<NodeBean>> nodeMetricsByAppId = new LinkedHashMap<String, List<NodeBean>>();
		
		try {
			List<String> allNodeHost = yarnService.getAllNodeHost();
			List<String> runningSparkAppId = yarnService.getRunningSparkApplications();
			
			EventLogReader eventLogReader = new EventLogHdfsReaderImpl(this.config);
			MetricsReader metricsReader = new MetricsRpcReaderImpl(allNodeHost); 
			
			for(String applicationId: runningSparkAppId){
				nodeMetricsByAppId.put(applicationId, 
						metricsReader.getAllNodeMetrics(applicationId, metrics));
			}
			
			Map<String, SparkEventLogBean> inProgressEventLogByAppId = eventLogReader.getAllInProgressLog();
			
			result = new UIDataAggregator().aggregateClusterDashBoard(metrics, runningSparkAppId, allNodeHost, 
							nodeMetricsByAppId, inProgressEventLogByAppId);
			
		} finally{
			yarnService.close();
		}
		
		return result;
	}
	
	
	public DefaultApplicationVO getJobDashBoard(String applicationId, List<String> metrics) throws Exception{
		DefaultApplicationVO result = null;
		YarnService yarnService = new YarnService(this.config);
		
		if(!metrics.contains(MetricsType.EXEC_THREADPOOL_COMPLETETASK))
			metrics.add(MetricsType.EXEC_THREADPOOL_COMPLETETASK.name());
		
		try{
			List<String> allNodeHost = yarnService.getAllNodeHost();
			
			if(yarnService.isStartRunningSparkApplication(applicationId)){
				EventLogReader eventLogReader = new EventLogHdfsReaderImpl(this.config);
				MetricsReader metricsReader = new MetricsRpcReaderImpl(allNodeHost); 
				
				SparkEventLogBean inProgressLog = eventLogReader.getInProgressLog(applicationId);
				List<NodeBean> nodeMetrics = metricsReader.getAllNodeMetrics(applicationId, metrics);
				
				result = new UIDataAggregator().aggregateJobDashBoard(metrics, allNodeHost, inProgressLog, nodeMetrics);
			}
		} finally{
			yarnService.close();
		}
		return result;
	}
	
	public DefaultNodeVO getNodeDashBoard(String node, List<String> metrics) throws Exception {
		DefaultNodeVO result = null;
		YarnService yarnService = new YarnService(this.config);
		Map<String, NodeBean> nodeMetricsByAppId = new LinkedHashMap<String, NodeBean>();
		
		MetricsReader metricsReader = new MetricsRpcReaderImpl(); 
		try {
			List<String> runningSparkAppId = yarnService.getRunningSparkApplications();
			for(String applicationId: runningSparkAppId){
				nodeMetricsByAppId.put(applicationId, 
						metricsReader.getNodeMetrics(node, applicationId, metrics));
			}
						
			result = new UIDataAggregator().aggregateNodeDashBoard(metrics, runningSparkAppId, node, nodeMetricsByAppId);
			
		} finally{
			yarnService.close();
		}
		return result;
	}
	
	public List<String> getRunningJobs() throws Exception {
		YarnService yarnService = new YarnService(this.config);
		return yarnService.getRunningSparkApplications();
	}

	public List<String> getNodeLists() throws Exception{
		List<String> nodes = new ArrayList<String>();
		YarnService yarnService = new YarnService(this.config);
		try{
			nodes = yarnService.getAllNodeHost();
		} finally {
			yarnService.close();
		}
		
		return nodes;
	}

	public List<HistoryVO> getAllSparkApplication() throws Exception {
		List<HistoryVO> histories = new ArrayList<HistoryVO>();
		EventLogReader eventLogReader = new EventLogHdfsReaderImpl(this.config);
		List<SparkEventLogBean> allSparkAppLog = eventLogReader.getAllSparkAppLog();
		
		for(SparkEventLogBean eventLog: allSparkAppLog){
			AppStart appStart = eventLog.getAppStart();
			HistoryVO history = new HistoryVO();
			history.setId(appStart.getId());
			history.setName(appStart.getName());
			history.setStartTime(appStart.getTimestamp()+"");
			history.setEndTime(eventLog.getAppEnd().getTimestamp()+"");
			history.setUser(appStart.getUser());
			histories.add(history);
		}
		
		return histories;
	}

	public List<JobVO> getSparkApplicationJobs(String applicationId) throws Exception {
		EventLogReader eventLogReader = new EventLogHdfsReaderImpl(this.config);
		SparkEventLogBean eventLog = eventLogReader.getApplicationJobs(applicationId);
		
		return new UIDataAggregator().aggregateApplicationJobs(applicationId, eventLog);
	}

	public List<StageVO> getSparkJobStages(String applicationId, String jobId) throws Exception {
		EventLogReader eventLogReader = new EventLogHdfsReaderImpl(this.config);
		SparkEventLogBean eventLog = eventLogReader.getJobStages(applicationId, jobId);
		
		return new UIDataAggregator().aggregateJobStages(applicationId, jobId, eventLog);
	}
}
