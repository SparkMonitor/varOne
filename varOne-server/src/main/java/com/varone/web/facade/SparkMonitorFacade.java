/**
 * 
 */
package com.varone.web.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;

import com.varone.conf.ConfVars;
import com.varone.conf.VarOneConfiguration;
import com.varone.node.MetricsProperties;
import com.varone.node.MetricsType;
import com.varone.web.aggregator.UIDataAggregator;
import com.varone.web.aggregator.timeperiod.TimePeriodHandler;
import com.varone.web.eventlog.bean.SparkEventLogBean;
import com.varone.web.eventlog.bean.SparkEventLogBean.AppStart;
import com.varone.web.metrics.bean.MetricBean;
import com.varone.web.metrics.bean.NodeBean;
import com.varone.web.reader.eventlog.EventLogReader;
import com.varone.web.reader.eventlog.impl.EventLogHdfsReaderImpl;
import com.varone.web.reader.metrics.MetricsReader;
import com.varone.web.reader.metrics.impl.MetricsRpcReaderImpl;
import com.varone.web.util.VarOneEnv;
import com.varone.web.vo.DefaultApplicationVO;
import com.varone.web.vo.DefaultNodeVO;
import com.varone.web.vo.DefaultTotalNodeVO;
import com.varone.web.vo.HistoryDetailStageVO;
import com.varone.web.vo.HistoryVO;
import com.varone.web.vo.JobVO;
import com.varone.web.vo.StageVO;
import com.varone.web.yarn.service.AbstractDeployModeService;
import com.varone.web.yarn.service.StandaloneService;
import com.varone.web.yarn.service.YarnService;

/**
 * @author allen
 *
 */
public class SparkMonitorFacade {
	
	private int varOneNodePort;
	private VarOneEnv env;
	private Configuration config;
	private MetricsProperties metricsProperties;
	
	public SparkMonitorFacade() throws IOException {
		VarOneConfiguration conf = VarOneConfiguration.create();
		this.env = new VarOneEnv(conf);
		this.config = env.loadHadoopConfiguration();
		this.varOneNodePort = conf.getInt(ConfVars.VARONE_NODE_PORT);
		this.metricsProperties = env.loadMetricsConfiguration();
	}
	
	public DefaultTotalNodeVO getDefaultClusterDashBoard(List<String> metrics, String periodExpression) throws Exception{
		DefaultTotalNodeVO result = null;
		TimePeriodHandler timePeriodHandler = new TimePeriodHandler(this.metricsProperties);
		AbstractDeployModeService deployModeService = this.getDeployModeService();
				
		Map<String, List<NodeBean>> nodeMetricsByAppId = new LinkedHashMap<String, List<NodeBean>>();
		
		try {
			long[] startAndEndTime = timePeriodHandler.transferToLongPeriod(periodExpression);
			List<String> allNodeHost = this.env.getDaemonHosts();
			int runningAppNum = deployModeService.getRunningSparkApplications().size();
			List<String> periodSparkAppId = deployModeService.getSparkApplicationsByPeriod(startAndEndTime[0], startAndEndTime[1]);
			
			EventLogReader eventLogReader = new EventLogHdfsReaderImpl(this.config, this.env.getEventLogDir());
			MetricsReader metricsReader = new MetricsRpcReaderImpl(allNodeHost, this.varOneNodePort); 
			
			List<Long> plotPointInPeriod = timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
			
			for(String applicationId: periodSparkAppId){				
				List<NodeBean> allNodeMetrics = metricsReader.getAllNodeMetrics(applicationId, metrics);
				for(NodeBean nodeBean: allNodeMetrics){
					for(MetricBean metricBean: nodeBean.getMetrics()){
						metricBean.setValues(timePeriodHandler.ingestPeriodData(
								metricBean.getValues(), plotPointInPeriod));
					}
				}
				nodeMetricsByAppId.put(applicationId, allNodeMetrics);
			}
			
			Map<String, SparkEventLogBean> inProgressEventLogByAppId = eventLogReader.getAllInProgressLog();
			
			result = new UIDataAggregator().aggregateClusterDashBoard(metrics, runningAppNum, periodSparkAppId, allNodeHost, 
							nodeMetricsByAppId, inProgressEventLogByAppId, plotPointInPeriod);
			
		} finally{
			deployModeService.close();
		}
		
		return result;
	}
	
	
	public DefaultApplicationVO getJobDashBoard(String applicationId, 
			List<String> metrics, String periodExpression) throws Exception{
		DefaultApplicationVO result = null;
		AbstractDeployModeService deployModeService = this.getDeployModeService();
		TimePeriodHandler timePeriodHandler = new TimePeriodHandler(this.metricsProperties);
		
		if(!metrics.contains(MetricsType.EXEC_THREADPOOL_COMPLETETASK))
			metrics.add(MetricsType.EXEC_THREADPOOL_COMPLETETASK.name());
		
		try{
			List<String> allNodeHost = this.env.getDaemonHosts();
			
			if(deployModeService.isStartRunningSparkApplication(applicationId)){
				
				long[] startAndEndTime = timePeriodHandler.transferToLongPeriod(periodExpression);
				List<Long> plotPointInPeriod = timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
				
				EventLogReader eventLogReader = new EventLogHdfsReaderImpl(this.config, this.env.getEventLogDir());
				MetricsReader metricsReader = new MetricsRpcReaderImpl(allNodeHost, this.varOneNodePort); 
				
				SparkEventLogBean inProgressLog = eventLogReader.getInProgressLog(applicationId);
				List<NodeBean> nodeMetrics = metricsReader.getAllNodeMetrics(applicationId, metrics);
				for(NodeBean nodeBean: nodeMetrics){
					for(MetricBean metricBean: nodeBean.getMetrics()){
						metricBean.setValues(timePeriodHandler.ingestPeriodData(
								metricBean.getValues(), plotPointInPeriod));
					}
				}
				
				result = new UIDataAggregator().aggregateJobDashBoard(metrics, allNodeHost, 
						inProgressLog, nodeMetrics, plotPointInPeriod);
			}
		} finally{
			deployModeService.close();
		}
		return result;
	}
	
	public DefaultNodeVO getNodeDashBoard(String node, 
			List<String> metrics, String periodExpression) throws Exception {
		DefaultNodeVO result = null;
		AbstractDeployModeService deployModeService = this.getDeployModeService();
		TimePeriodHandler timePeriodHandler = new TimePeriodHandler(this.metricsProperties);
		Map<String, NodeBean> nodeMetricsByAppId = new LinkedHashMap<String, NodeBean>();
		
		MetricsReader metricsReader = new MetricsRpcReaderImpl(this.varOneNodePort); 
		try {
			long[] startAndEndTime = timePeriodHandler.transferToLongPeriod(periodExpression);
			List<Long> plotPointInPeriod = timePeriodHandler.getDefaultPlotPointInPeriod(startAndEndTime);
			
			List<String> periodSparkAppId = deployModeService.getSparkApplicationsByPeriod(startAndEndTime[0], startAndEndTime[1]);
			
			for(String applicationId: periodSparkAppId){
				NodeBean nodeBean = metricsReader.getNodeMetrics(node, applicationId, metrics);
				for(MetricBean metricBean: nodeBean.getMetrics()){
					metricBean.setValues(timePeriodHandler.ingestPeriodData(
							metricBean.getValues(), plotPointInPeriod));
				}
				nodeMetricsByAppId.put(applicationId, nodeBean);
			}
						
			result = new UIDataAggregator().aggregateNodeDashBoard(metrics, node, nodeMetricsByAppId, plotPointInPeriod);
			
		} finally{
			deployModeService.close();
		}
		return result;
	}
	
	public HistoryDetailStageVO getHistoryDetailStageTask(String applicationId, int stageId){
		try{
			EventLogHdfsReaderImpl hdfsReader = new EventLogHdfsReaderImpl(config, this.env.getEventLogDir());
			SparkEventLogBean sparkEventLog = hdfsReader.getHistoryStageDetails(applicationId);
	
			UIDataAggregator aggregator = new UIDataAggregator();
			return aggregator.aggregateHistoryDetialStage(sparkEventLog, stageId);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
	
	public List<String> getRunningJobs() throws Exception {
		AbstractDeployModeService deployModeService = this.getDeployModeService();
		return deployModeService.getRunningSparkApplications();
	}

	public List<String> getNodeLists() throws Exception{
		List<String> nodes = new ArrayList<String>();
		return this.env.getDaemonHosts();
	}

	public List<HistoryVO> getAllSparkApplication() throws Exception {
		List<HistoryVO> histories = new ArrayList<HistoryVO>();
		EventLogReader eventLogReader = new EventLogHdfsReaderImpl(this.config, this.env.getEventLogDir());
		List<SparkEventLogBean> allSparkAppLog = eventLogReader.getAllSparkAppLog();
		
		for(SparkEventLogBean eventLog: allSparkAppLog){
			AppStart appStart = eventLog.getAppStart();
			HistoryVO history = new HistoryVO();
			history.setId(appStart.getId());
			history.setName(appStart.getName());
			history.setStartTime(appStart.getTimestamp()+"");
			if(eventLog.getAppEnd() != null){
				history.setEndTime(eventLog.getAppEnd().getTimestamp()+"");
			}
			history.setUser(appStart.getUser());
			histories.add(history);
		}
		
		return histories;
	}

	public List<JobVO> getSparkApplicationJobs(String applicationId) throws Exception {
		EventLogReader eventLogReader = new EventLogHdfsReaderImpl(this.config, this.env.getEventLogDir());
		SparkEventLogBean eventLog = eventLogReader.getApplicationJobs(applicationId);
		
		return new UIDataAggregator().aggregateApplicationJobs(applicationId, eventLog);
	}

	public List<StageVO> getSparkJobStages(String applicationId, String jobId) throws Exception {
		EventLogReader eventLogReader = new EventLogHdfsReaderImpl(this.config, this.env.getEventLogDir());
		SparkEventLogBean eventLog = eventLogReader.getJobStages(applicationId, jobId);
		
		return new UIDataAggregator().aggregateJobStages(applicationId, jobId, eventLog);
	}
	
	private AbstractDeployModeService getDeployModeService() {
		VarOneConfiguration conf = VarOneConfiguration.create();
		String deployMode = conf.getSparkDeployMode();
		if(deployMode.equals("standalone")){
			return new StandaloneService(this.config);
		}else if(deployMode.equals("yarn")){
			return new YarnService(this.config);
		}else{
			throw new RuntimeException(ConfVars.SPARK_DEPLOY_MODE.getVarName() + " setting not yarn or standalone");
		}
	}
}
