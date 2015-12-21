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

import org.apache.commons.lang.NumberUtils;

import com.varone.web.aggregator.unit.TimeUnitTransfer;
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
import com.varone.web.vo.TimeValueVO;

/**
 * @author allen
 *
 */
public class UIDataAggregator {
	
	private TimeUnitTransfer timeUnitTransfer;
	
	/**
	 * 
	 */
	public UIDataAggregator() {
		this.timeUnitTransfer = new TimeUnitTransfer();
	}
	
	
	public DefaultTotalNodeVO aggregateClusterDashBoard(List<String> metrics, int runningAppNum, List<String> periodSparkAppId, 
			List<String> allNodeHost, Map<String, List<NodeBean>> nodeMetricsByAppId, 
			Map<String, SparkEventLogBean> inProgressEventLogByAppId, List<Long> plotPointInPeriod) throws Exception{
		DefaultTotalNodeVO result = new DefaultTotalNodeVO();
		int nodeNum = allNodeHost.size();
		int jobNum  = runningAppNum;
		int taskNum = 0, executorNum = 0;
		
		List<MetricPropVO> metricProps = new ArrayList<MetricPropVO>();
		Map<String, Integer> taskStartedNumByNode = new LinkedHashMap<String, Integer>();
		Map<String, Integer> executorNumByNode = new LinkedHashMap<String, Integer>();
		Map<String, Map<String, List<TimeValueVO>>> propToMetrics = new LinkedHashMap<String, Map<String, List<TimeValueVO>>>();
		
		for(String metric: metrics){
			String[] metricInfo = UIMrtricsPropTransfer.getUIMetricPropertyByMetricName(metric);
			MetricPropVO metricPropVO = new MetricPropVO();
			metricPropVO.setProperty(metricInfo[0]);
			metricPropVO.setTitle(metricInfo[1]);
			metricPropVO.setFormat(metricInfo[2]);
			metricProps.add(metricPropVO);
			Map<String, List<TimeValueVO>> host2Metrics = new LinkedHashMap<String, List<TimeValueVO>>();
			for(String host: allNodeHost){
				
				List<TimeValueVO> defaultValues = new ArrayList<TimeValueVO>();
				for(Long time: plotPointInPeriod){
					TimeValueVO pair = new TimeValueVO();
					pair.setTime(this.timeUnitTransfer.transferToAxisX(time));
					if(metricInfo[2].equals("PERCENTAGE") || 
					   metricInfo[2].equals("MILLIS") || 
					   metricInfo[2].equals("OPS")){
						pair.setValue("0,0");
					} else {
						pair.setValue("0");
					}
					defaultValues.add(pair);
				}
				
				host2Metrics.put(host, defaultValues);
			}
			propToMetrics.put(metricInfo[0], host2Metrics);
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
					String propertyName = UIMrtricsPropTransfer.getUIMetricPropertyByMetricValue(metric.getName());
					String format = UIMrtricsPropTransfer.getUIMetricPropertyByJsProp(propertyName)[2];
					Map<String, List<TimeValueVO>> host2Metric = propToMetrics.get(propertyName);
					
					// get a new one
					List<TimeValuePairBean> newValues = metric.getValues();
					// get current value
					List<TimeValueVO> currValues = host2Metric.get(host);
					// aggregate and store
					
					for(TimeValuePairBean newPair: newValues){
						for(TimeValueVO currPair: currValues){
							if(this.timeUnitTransfer.transferToAxisX(
									newPair.getTime()).equals(currPair.getTime())){
								if(format.equals("BYTE") || format.equals("NONE")){
									long newValue = Long.parseLong(newPair.getValue());
									long currValue = Long.parseLong(currPair.getValue());
									currPair.setValue(String.valueOf(newValue+currValue));
								} else if(format.equals("PERCENTAGE")){
									String[] valueAndCount = currPair.getValue().split(",");
									double newValue = Double.parseDouble(newPair.getValue());
									double currValue = Double.parseDouble(valueAndCount[0]);
									valueAndCount[1] = Integer.valueOf(valueAndCount[1])+1+"";
									currPair.setValue(String.valueOf(newValue+currValue)+","+valueAndCount[1]);
								} else {
									//MILLIS or OPS
									String[] valueAndCount = currPair.getValue().split(",");
									long newValue = Long.parseLong(newPair.getValue());
									long currValue = Long.parseLong(valueAndCount[0]);
									valueAndCount[1] = Integer.valueOf(valueAndCount[1])+1+"";
									currPair.setValue(String.valueOf(newValue+currValue)+","+valueAndCount[1]);
								}
								
								break;
							}
						}
					}
				}
			}
		}
		
		for(MetricPropVO metricPropVO: metricProps){
			if(metricPropVO.getFormat().equals("PERCENTAGE")){
				Map<String, List<TimeValueVO>> map = propToMetrics.get(metricPropVO.getProperty());
				for(List<TimeValueVO> periodData: map.values()){
					for(TimeValueVO timeValue: periodData){
						String[] valueAndCount = timeValue.getValue().split(",");
						double sum = Double.parseDouble(valueAndCount[0]);
						if(sum > 0){
							double avg = sum / Integer.parseInt(valueAndCount[1]);
							timeValue.setValue(String.valueOf(avg));
						} else {
							timeValue.setValue("0");
						}
					}
				}
			} else if(metricPropVO.getFormat().equals("MILLIS") || metricPropVO.getFormat().equals("OPS")) {
				Map<String, List<TimeValueVO>> map = propToMetrics.get(metricPropVO.getProperty());
				for(List<TimeValueVO> periodData: map.values()){
					for(TimeValueVO timeValue: periodData){
						String[] valueAndCount = timeValue.getValue().split(",");
						long sum = Long.parseLong(valueAndCount[0]);
						if(sum > 0){
							long avg = sum / Integer.parseInt(valueAndCount[1]);
							timeValue.setValue(String.valueOf(avg));
						} else {
							timeValue.setValue("0");
						}
					}
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
			SparkEventLogBean inProgressLog, List<NodeBean> nodeMetrics, List<Long> plotPointInPeriod){
		DefaultApplicationVO result = new DefaultApplicationVO();
		
		List<MetricPropVO> metricProps = new ArrayList<MetricPropVO>();
		Map<String, String> taskStartedNumByNode = new LinkedHashMap<String, String>();
		Map<String, Integer> executorNumByNode = new LinkedHashMap<String, Integer>();
		Map<String, Map<String, List<TimeValueVO>>> propToMetrics = new LinkedHashMap<String, Map<String, List<TimeValueVO>>>();
		
		for(String metric: metrics){
			String[] metricInfo = UIMrtricsPropTransfer.getUIMetricPropertyByMetricName(metric);
			MetricPropVO metricPropVO = new MetricPropVO();
			metricPropVO.setProperty(metricInfo[0]);
			metricPropVO.setTitle(metricInfo[1]);
			metricPropVO.setFormat(metricInfo[2]);
			metricProps.add(metricPropVO);
			Map<String, List<TimeValueVO>> host2Metrics = new LinkedHashMap<String, List<TimeValueVO>>();
			for(String host: allNodeHost){
				
				List<TimeValueVO> defaultValues = new ArrayList<TimeValueVO>();
				for(Long time: plotPointInPeriod){
					TimeValueVO pair = new TimeValueVO();
					pair.setTime(this.timeUnitTransfer.transferToAxisX(time));
					if(metricInfo[2].equals("PERCENTAGE") || 
					   metricInfo[2].equals("MILLIS") || 
					   metricInfo[2].equals("OPS")){
						pair.setValue("0,0");
					} else {
						pair.setValue("0");
					}
					defaultValues.add(pair);
				}
				
				host2Metrics.put(host, defaultValues);
			}
			propToMetrics.put(metricInfo[0], host2Metrics);
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
				String propertyName = UIMrtricsPropTransfer.getUIMetricPropertyByMetricValue(metric.getName());
				String format = UIMrtricsPropTransfer.getUIMetricPropertyByJsProp(propertyName)[2];
				Map<String, List<TimeValueVO>> host2Metric = propToMetrics.get(propertyName);
				
				// get a new one
				List<TimeValuePairBean> newValues = metric.getValues();
				// get current value
				List<TimeValueVO> currValues = host2Metric.get(host);
				// aggregate and store
				
				for(TimeValuePairBean newPair: newValues){
					for(TimeValueVO currPair: currValues){
						if(this.timeUnitTransfer.transferToAxisX(
								newPair.getTime()).equals(currPair.getTime())){
							if(format.equals("BYTE") || format.equals("NONE")){
								long newValue = Long.parseLong(newPair.getValue());
								long currValue = Long.parseLong(currPair.getValue());
								currPair.setValue(String.valueOf(newValue+currValue));
							} else if(format.equals("PERCENTAGE")){
								String[] valueAndCount = currPair.getValue().split(",");
								double newValue = Double.parseDouble(newPair.getValue());
								double currValue = Double.parseDouble(valueAndCount[0]);
								valueAndCount[1] = Integer.valueOf(valueAndCount[1])+1+"";
								currPair.setValue(String.valueOf(newValue+currValue)+","+valueAndCount[1]);
							} else {
								//MILLIS or OPS
								String[] valueAndCount = currPair.getValue().split(",");
								long newValue = Long.parseLong(newPair.getValue());
								long currValue = Long.parseLong(valueAndCount[0]);
								valueAndCount[1] = Integer.valueOf(valueAndCount[1])+1+"";
								currPair.setValue(String.valueOf(newValue+currValue)+","+valueAndCount[1]);
							}
							break;
						}
					}
				}
								
				if(metric.getName().indexOf("completeTasks") != -1){					
					String newestValue = currValues.get(currValues.size()-1).getValue();
					if(currValues.size() > 2 && newestValue.equals("0")){
						newestValue = currValues.get(currValues.size()-2).getValue();
					} 
					taskStartedNumByNode.put(host, newestValue);
					completedTaskNum += Integer.valueOf(newestValue);
				}
					
			}
		}
		
		for(MetricPropVO metricPropVO: metricProps){
			if(metricPropVO.getFormat().equals("PERCENTAGE")){
				Map<String, List<TimeValueVO>> map = propToMetrics.get(metricPropVO.getProperty());
				for(List<TimeValueVO> periodData: map.values()){
					for(TimeValueVO timeValue: periodData){
						String[] valueAndCount = timeValue.getValue().split(",");
						double sum = Double.parseDouble(valueAndCount[0]);
						if(sum > 0){
							double avg = sum / Integer.parseInt(valueAndCount[1]);
							timeValue.setValue(String.valueOf(avg));
						} else {
							timeValue.setValue("0");
						}
					}
				}
			} else if(metricPropVO.getFormat().equals("MILLIS") || metricPropVO.getFormat().equals("OPS")) {
				Map<String, List<TimeValueVO>> map = propToMetrics.get(metricPropVO.getProperty());
				for(List<TimeValueVO> periodData: map.values()){
					for(TimeValueVO timeValue: periodData){
						String[] valueAndCount = timeValue.getValue().split(",");
						long sum = Long.parseLong(valueAndCount[0]);
						if(sum > 0){
							long avg = sum / Integer.parseInt(valueAndCount[1]);
							timeValue.setValue(String.valueOf(avg));
						} else {
							timeValue.setValue("0");
						}
					}
				}
			}
		}
		
		for(TaskEnd taskEnd: inProgressLog.getTaskEnd()){
			failedTaskNum += taskEnd.getInfo().isFailed()?1:0;
			// calculate the completedTaskNum on here, but it's too slow
//			if(taskEnd.getInfo().isFailed())
//				failedTaskNum++;
//			else
//				completedTaskNum++;
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


	public DefaultNodeVO aggregateNodeDashBoard(List<String> metrics, String node,
			Map<String, NodeBean> nodeMetricsByAppId, List<Long> plotPointInPeriod) {
		DefaultNodeVO result = new DefaultNodeVO();
		List<MetricPropVO> metricProps = new ArrayList<MetricPropVO>();
		Map<String, List<TimeValueVO>> propToMetrics = new LinkedHashMap<String, List<TimeValueVO>>();
		
		for(String metric: metrics){
			String[] metricInfo = UIMrtricsPropTransfer.getUIMetricPropertyByMetricName(metric);
			MetricPropVO metricPropVO = new MetricPropVO();
			metricPropVO.setProperty(metricInfo[0]);
			metricPropVO.setTitle(metricInfo[1]);
			metricProps.add(metricPropVO);
			
			List<TimeValueVO> defaultValues = new ArrayList<TimeValueVO>();
			for(Long time: plotPointInPeriod){
				TimeValueVO pair = new TimeValueVO();
				pair.setTime(this.timeUnitTransfer.transferToAxisX(time));
				pair.setValue("0");
				defaultValues.add(pair);
			}
			
			propToMetrics.put(metricInfo[0], defaultValues);
		}
		
		for(Entry<String, NodeBean> entry: nodeMetricsByAppId.entrySet()){
			NodeBean nodeBean = entry.getValue();
			for(MetricBean metricBean: nodeBean.getMetrics()){
				String propertyName = UIMrtricsPropTransfer.getUIMetricPropertyByMetricValue(metricBean.getName());
				
				// get a new one
				List<TimeValuePairBean> newValues = metricBean.getValues();
				// get current value
				List<TimeValueVO> currValues = propToMetrics.get(propertyName);
				// aggregate and store
				
				for(TimeValuePairBean newPair: newValues){
					for(TimeValueVO currPair: currValues){
						if(this.timeUnitTransfer.transferToAxisX(
								newPair.getTime()).equals(currPair.getTime())){
							long newValue = Long.parseLong(newPair.getValue());
							long currValue = Long.parseLong(currPair.getValue());
							currPair.setValue(String.valueOf(newValue+currValue));
							break;
						}
					}
				}
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
