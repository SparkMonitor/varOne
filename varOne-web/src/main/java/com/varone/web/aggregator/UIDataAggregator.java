/**
 * 
 */
package com.varone.web.aggregator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.varone.web.eventlog.bean.SparkEventLogBean;
import com.varone.web.eventlog.bean.SparkEventLogBean.ExecutorAdded;
import com.varone.web.eventlog.bean.SparkEventLogBean.JobEnd;
import com.varone.web.eventlog.bean.SparkEventLogBean.JobStart;
import com.varone.web.eventlog.bean.SparkEventLogBean.StageCompleted;
import com.varone.web.eventlog.bean.SparkEventLogBean.StageInfos;
import com.varone.web.eventlog.bean.SparkEventLogBean.StageSubmit;
import com.varone.web.eventlog.bean.SparkEventLogBean.TaskEnd;
import com.varone.web.eventlog.bean.SparkEventLogBean.TaskStart;
import com.varone.web.eventlog.bean.SparkEventLogBean.TaskEnd.TaskMetrics;
import com.varone.web.metrics.bean.MetricBean;
import com.varone.web.metrics.bean.NodeBean;
import com.varone.web.metrics.bean.TimeValuePairBean;
import com.varone.web.vo.DefaultApplicationVO;
import com.varone.web.vo.DefaultNodeVO;
import com.varone.web.vo.DefaultTotalNodeVO;
import com.varone.web.vo.JobVO;
import com.varone.web.vo.MetricPropVO;
import com.varone.web.vo.StageVO;

/**
 * @author allen
 *
 */
public class UIDataAggregator {

	/**
	 * 
	 */
	public UIDataAggregator() {
		// TODO Auto-generated constructor stub
	}
	
	
	public DefaultTotalNodeVO aggregateClusterDashBoard(List<String> metrics, List<String> runningSparkAppId, 
			List<String> allNodeHost, Map<String, List<NodeBean>> nodeMetricsByAppId, 
			Map<String, SparkEventLogBean> inProgressEventLogByAppId) throws Exception{
		DefaultTotalNodeVO result = new DefaultTotalNodeVO();
		int nodeNum = allNodeHost.size();
		int jobNum  = runningSparkAppId.size();
		int taskNum = 0, executorNum = 0;
		
		List<MetricPropVO> metricProps = new ArrayList<MetricPropVO>();
		Map<String, Integer> taskStartedNumByNode = new LinkedHashMap<String, Integer>();
		Map<String, Integer> executorNumByNode = new LinkedHashMap<String, Integer>();
		Map<String, Map<String, String>> propToMetrics = new LinkedHashMap<String, Map<String, String>>();
		
		for(String metric: metrics){
			String[] propertyAndTitle = UIMrtricsPropTransfer.getUIMetricPropertyByMetricName(metric);
			MetricPropVO metricPropVO = new MetricPropVO();
			metricPropVO.setProperty(propertyAndTitle[0]);
			metricPropVO.setTitle(propertyAndTitle[1]);
			metricProps.add(metricPropVO);
			Map<String, String> host2Metrics = new LinkedHashMap<String, String>();
			for(String host: allNodeHost)
				host2Metrics.put(host, "0");
			propToMetrics.put(propertyAndTitle[0], host2Metrics);
		}
		
		for(String host: allNodeHost){
			executorNumByNode.put(host, 0);
			taskStartedNumByNode.put(host, 0);
		}

		
		
		for(Entry<String, SparkEventLogBean> entry: inProgressEventLogByAppId.entrySet()){
			SparkEventLogBean eventLog = entry.getValue();
			taskNum += (eventLog.getTaskStart().size() - eventLog.getTaskEnd().size());
			executorNum += eventLog.getExecutorAdd().size();
			
			for(TaskStart taskStart: eventLog.getTaskStart()){
				int n = taskStartedNumByNode.get(taskStart.getInfo().getHost());
				taskStartedNumByNode.put(taskStart.getInfo().getHost(), ++n);
			}
			
			for(ExecutorAdded executorAdd: eventLog.getExecutorAdd()){
				int n = executorNumByNode.get(executorAdd.getInfo().getHost());
				executorNumByNode.put(executorAdd.getInfo().getHost(), ++n);
			}
		}
		
		for(Entry<String, List<NodeBean>> entry: nodeMetricsByAppId.entrySet()){
			for(NodeBean node: entry.getValue()){
				String host = node.getHost();
				for(MetricBean metric: node.getMetrics()){
//					if(metric.getName().indexOf("heap.used") != -1){
//						//get last one metric
//						TimeValuePairBean timeValuePairBean = metric.getValues().get(metric.getValues().size()-1);
//						long value = Long.parseLong(timeValuePairBean.getValue());
//						jvmUsedByNode.put(host, jvmUsedByNode.get(host)+value);
//					}
					String propertyName = UIMrtricsPropTransfer.getUIMetricPropertyByMetricValue(metric.getName());
					
					Map<String, String> host2Metric = propToMetrics.get(propertyName);
					
					//get last one metric
					TimeValuePairBean timeValuePairBean = metric.getValues().get(metric.getValues().size()-1);
					long value = Long.parseLong(timeValuePairBean.getValue());
					//get current metric value
					long currValue = Long.parseLong(host2Metric.get(host));
					//aggregate and store
					host2Metric.put(host, String.valueOf(value + currValue));
				}
			}
		}
		result.setExecutorNumByNode(executorNumByNode);
		result.setExecutorNum(executorNum);
		result.setJobNum(jobNum);
		result.setNodeNum(nodeNum);
		result.setTaskNum(taskNum);
		result.setTaskStartedNumByNode(taskStartedNumByNode);
		result.setPropToMetrics(propToMetrics);
		result.setMetricProps(metricProps);
		return result;
	}
	
	public DefaultApplicationVO aggregateJobDashBoard(List<String> metrics, List<String> allNodeHost, 
			SparkEventLogBean inProgressLog, List<NodeBean> nodeMetrics){
		DefaultApplicationVO result = new DefaultApplicationVO();
		
		List<MetricPropVO> metricProps = new ArrayList<MetricPropVO>();
		Map<String, String> taskStartedNumByNode = new LinkedHashMap<String, String>();
		Map<String, Integer> executorNumByNode = new LinkedHashMap<String, Integer>();
		Map<String, Map<String, String>> propToMetrics = new LinkedHashMap<String, Map<String, String>>();
		
		for(String metric: metrics){
			String[] propertyAndTitle = UIMrtricsPropTransfer.getUIMetricPropertyByMetricName(metric);
			MetricPropVO metricPropVO = new MetricPropVO();
			metricPropVO.setProperty(propertyAndTitle[0]);
			metricPropVO.setTitle(propertyAndTitle[1]);
			metricProps.add(metricPropVO);
			Map<String, String> host2Metrics = new LinkedHashMap<String, String>();
			for(String host: allNodeHost)
				host2Metrics.put(host, "0");
			propToMetrics.put(propertyAndTitle[0], host2Metrics);
		}
		
		for(String host: allNodeHost) executorNumByNode.put(host, 0);
		for(String host: allNodeHost) taskStartedNumByNode.put(host, "0");
		
		result.setExecutorNumByNode(executorNumByNode);
		result.setTaskStartedNumByNode(taskStartedNumByNode);
		
		int executorNum      = inProgressLog.getExecutorAdd().size();
		int taskNum          = 0;
		int completedTaskNum = 0;
		int failedTaskNum    = 0;
						
		for(JobStart jobStart: inProgressLog.getJobStart()){
			for(StageInfos stageInfo: jobStart.getStageInfos()){
				taskNum += stageInfo.getTaskNum();
			}
		}
		
		for(NodeBean node: nodeMetrics){
			String host = node.getHost();
			for(MetricBean metric: node.getMetrics()){
//				if(metric.getName().indexOf("completeTasks") != -1){
//					//get last one metric
//					TimeValuePairBean timeValuePairBean = metric.getValues().get(metric.getValues().size()-1);
//					int value = Integer.parseInt(timeValuePairBean.getValue());
//					completedTaskNum += value;
//					
//					int n = taskStartedNumByNode.get(host) + value;
//					taskStartedNumByNode.put(host, n);
//					
//				} else if(metric.getName().indexOf("heap.used") != -1){
//					//get last one metric
//					TimeValuePairBean timeValuePairBean = metric.getValues().get(metric.getValues().size()-1);
//					long value = Long.parseLong(timeValuePairBean.getValue());
////					jvmUsedByNode.put(host, jvmUsedByNode.get(host)+value);
//				}
				String propertyName = UIMrtricsPropTransfer.getUIMetricPropertyByMetricValue(metric.getName());
				Map<String, String> host2Metric = propToMetrics.get(propertyName);
				
				//get last one metric
				TimeValuePairBean timeValuePairBean = metric.getValues().get(metric.getValues().size()-1);
				long value = Long.parseLong(timeValuePairBean.getValue());
				//get current metric value
				long currValue = Long.parseLong(host2Metric.get(host));
				//aggregate and store
				host2Metric.put(host, String.valueOf(value + currValue));
//				if(metric.getName())
				
				if(metric.getName().indexOf("completeTasks") != -1){
					completedTaskNum += value;
					taskStartedNumByNode.put(host, host2Metric.get(host));
				}
					
			}
		}
		
		for(TaskEnd taskEnd: inProgressLog.getTaskEnd()){
			failedTaskNum += taskEnd.getInfo().isFailed()?1:0;
		}
		
		for(ExecutorAdded executorAdded: inProgressLog.getExecutorAdd()){
			int n = executorNumByNode.get(executorAdded.getInfo().getHost());
			executorNumByNode.put(executorAdded.getInfo().getHost(), ++n);
		}
		
		result.setExecutorNum(executorNum);
		result.setTaskNum(taskNum);
		result.setCompletedTaskNum(completedTaskNum);
		result.setFailedTaskNum(failedTaskNum);
		result.setPropToMetrics(propToMetrics);
		result.setMetricProps(metricProps);
		
		return result;
	}


	public DefaultNodeVO aggregateNodeDashBoard(List<String> metrics,
			List<String> runningSparkAppId, String node,
			Map<String, NodeBean> nodeMetricsByAppId) {
		DefaultNodeVO result = new DefaultNodeVO();
		List<MetricPropVO> metricProps = new ArrayList<MetricPropVO>();
		Map<String, String> propToMetrics = new LinkedHashMap<String, String>();
		
		for(String metric: metrics){
			String[] propertyAndTitle = UIMrtricsPropTransfer.getUIMetricPropertyByMetricName(metric);
			MetricPropVO metricPropVO = new MetricPropVO();
			metricPropVO.setProperty(propertyAndTitle[0]);
			metricPropVO.setTitle(propertyAndTitle[1]);
			metricProps.add(metricPropVO);

			propToMetrics.put(propertyAndTitle[0], "0");
		}
		
		for(Entry<String, NodeBean> entry: nodeMetricsByAppId.entrySet()){
			NodeBean nodeBean = entry.getValue();
			for(MetricBean metricBean: nodeBean.getMetrics()){
				String propertyName = UIMrtricsPropTransfer.getUIMetricPropertyByMetricValue(metricBean.getName());
				TimeValuePairBean timeValuePairBean = metricBean.getValues().get(metricBean.getValues().size()-1);
				propToMetrics.put(propertyName, timeValuePairBean.getValue());
			}
		}
		
		result.setPropToMetrics(propToMetrics);
		result.setMetricProps(metricProps);
		return result;
	}


	public List<JobVO> aggregateApplicationJobs(String applicationId,
			SparkEventLogBean eventLog) {
		List<JobVO> result = new ArrayList<JobVO>();
		
		List<JobStart> jobStarts = eventLog.getJobStart();
		List<JobEnd> jobEnds     = eventLog.getJobEnd();
		
		for(JobStart jobStart: jobStarts){
			JobVO job = new JobVO();
			List<SparkEventLogBean.StageInfos> stageInfos = jobStart.getStageInfos();
			if(!stageInfos.isEmpty()){
				job.setDescription(jobStart.getStageInfos().get(0).getName());
			}
			
			int totalTasks  = 0;
			int successTasks = 0;
			int totalStages = jobStart.getStageInfos().size();
			int successStages = 0;
			
			Set<Integer> jobStages = new TreeSet<Integer>();
			for(SparkEventLogBean.StageInfos stageInfo: stageInfos){
				jobStages.add(stageInfo.getId());
				totalTasks+=stageInfo.getTaskNum();
			}
			
			for(TaskEnd taskEnd: eventLog.getTaskEnd()){
				if(jobStages.contains(taskEnd.getStageId()) 
						&& taskEnd.getReason().getReason().equals("Success")){
					successTasks++;
				}
			}
			
			for(StageCompleted stageCompleted: eventLog.getStageComplete()){
				if(jobStages.contains(stageCompleted.getStageInfo().getId()) 
						&& (null == stageCompleted.getStageInfo().getFailureReason() ||
							"".equals(stageCompleted.getStageInfo().getFailureReason()))){
					successStages++;
				}
			}
			String duration = "";
			for(JobEnd jobEnd: jobEnds){
				boolean found = false;
				if(jobStart.getJobId() == jobEnd.getId()){
					found = true;
					duration = String.valueOf(jobEnd.getCompleteTime() - jobStart.getSubmitTime());
					break;
				}
				if(found) break;
			}
			
			job.setDuration(duration);
			job.setSubmitTime(jobStart.getSubmitTime()+"");
			job.setStagesSuccessVSTotal(successStages+"/"+totalStages);
			job.setStagesSuccessPercent((successStages*100/totalStages) + "%");
			job.setTasksSuccessVSTotal(successTasks+"/"+totalTasks);
			job.setTasksSuccessPercent((successTasks*100/totalTasks) + "%");
			job.setId(jobStart.getJobId());
			result.add(job);
		}
		
		return result;
	}


	public List<StageVO> aggregateJobStages(String applicationId, String jobId,
			SparkEventLogBean eventLog) {
		List<StageVO> result = new ArrayList<StageVO>();
		List<Integer> stageIds = new ArrayList<Integer>();
		
		
		for(JobStart jobStart: eventLog.getJobStart()){
			if(String.valueOf(jobStart.getJobId()).equals(jobId)){
				for(StageInfos stageInfo: jobStart.getStageInfos()){
					stageIds.add(stageInfo.getId());
				}
				break;
			}
		}
		
		for(StageSubmit stageSubmit: eventLog.getStageSubmit()){
			StageInfos stageSubmitInfo = stageSubmit.getStageInfo();
			if(stageIds.contains(stageSubmitInfo.getId())){
				StageVO stageVO = new StageVO();
				stageVO.setId(stageSubmitInfo.getId());
				stageVO.setDescription(stageSubmitInfo.getName());
				stageVO.setSubmitTime(String.valueOf(stageSubmitInfo.getSubmitTime()));
				
				for(StageCompleted stageComplete: eventLog.getStageComplete()){
					StageInfos stageCompleteInfo = stageComplete.getStageInfo();
					if(stageSubmitInfo.getId() == stageCompleteInfo.getId()){
						stageVO.setDuration(String.valueOf(
								stageSubmitInfo.getCompleteTime() - stageSubmitInfo.getSubmitTime()));
						int successTasks = 0;
						long readAmount = 0;
						long writeAmount = 0;
						for(TaskEnd taskEnd: eventLog.getTaskEnd()){
							if(stageCompleteInfo.getId() == taskEnd.getStageId() 
									&& taskEnd.getReason().getReason().equals("Success")){
								successTasks++;
								TaskMetrics metrics = taskEnd.getMetrics();
								if(metrics.getInputMetrics() != null)
									readAmount += metrics.getInputMetrics().getReadByte();
								if(metrics.getOutputMetrics() != null)
									writeAmount += metrics.getOutputMetrics().getWriteByte();
							}
						}
						stageVO.setReadAmount(readAmount+"");
						stageVO.setWriteAmount(writeAmount+"");
						stageVO.setTasksSuccessVSTotal(successTasks+"/"+stageSubmitInfo.getTaskNum());
						stageVO.setTasksSuccessPercent((successTasks*100/stageSubmitInfo.getTaskNum()) + "%");
						break;
					}
				}
				result.add(stageVO);
			}
		}
		
		return result;
	}

}
