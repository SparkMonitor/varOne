/**
 * 
 */
package com.varone.web.aggregator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.varone.web.aggregator.unit.AbstractUnitAggregator;
import com.varone.web.aggregator.unit.AverageAggregator;
import com.varone.web.aggregator.unit.QuantityAggregator;
import com.varone.web.aggregator.unit.TimeUnitTransfer;
import com.varone.web.eventlog.bean.SparkEventLogBean;
import com.varone.web.eventlog.bean.SparkEventLogBean.BlockManager;
import com.varone.web.eventlog.bean.SparkEventLogBean.ExecutorAdded;
import com.varone.web.eventlog.bean.SparkEventLogBean.JobEnd;
import com.varone.web.eventlog.bean.SparkEventLogBean.JobStart;
import com.varone.web.eventlog.bean.SparkEventLogBean.StageCompleted;
import com.varone.web.eventlog.bean.SparkEventLogBean.StageInfos;
import com.varone.web.eventlog.bean.SparkEventLogBean.StageSubmit;
import com.varone.web.eventlog.bean.SparkEventLogBean.TaskEnd;
import com.varone.web.eventlog.bean.SparkEventLogBean.TaskEnd.TaskMetrics;
import com.varone.web.eventlog.bean.SparkEventLogBean.TaskStart;
import com.varone.web.eventlog.bean.SparkEventLogBean.TaskStart.TaskInfo;
import com.varone.web.metrics.bean.MetricBean;
import com.varone.web.metrics.bean.NodeBean;
import com.varone.web.metrics.bean.TimeValuePairBean;
import com.varone.web.vo.DefaultApplicationVO;
import com.varone.web.vo.DefaultNodeVO;
import com.varone.web.vo.DefaultTotalNodeVO;
import com.varone.web.vo.HistoryDetailStageVO;
import com.varone.web.vo.JobVO;
import com.varone.web.vo.MetricCompletedTasksVO;
import com.varone.web.vo.MetricPropVO;
import com.varone.web.vo.StageVO;
import com.varone.web.vo.SummaryExecutorVO;
import com.varone.web.vo.TasksVO;
import com.varone.web.vo.TimeValueVO;

/**
 * @author allen
 *
 */
public class UIDataAggregator {
	
	private TimeUnitTransfer timeUnitTransfer;
	private AbstractUnitAggregator quantityAggregator;
	private AbstractUnitAggregator averageAggregator;
	
	/**
	 * 
	 */
	public UIDataAggregator() {
		this.timeUnitTransfer = new TimeUnitTransfer();
		this.quantityAggregator = new QuantityAggregator();
		this.averageAggregator = new AverageAggregator();
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
					this.aggregrateNewMetricToCurrentMetric(format, newValues, currValues);
				}
			}
		}
		
		this.calculateAvgWithMultiNode(metricProps, propToMetrics);

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
				this.aggregrateNewMetricToCurrentMetric(format, newValues, currValues);
								
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
		
		this.calculateAvgWithMultiNode(metricProps, propToMetrics);
		
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
			metricPropVO.setFormat(metricInfo[2]);
			metricProps.add(metricPropVO);
			
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
			
			propToMetrics.put(metricInfo[0], defaultValues);
		}
		
		for(Entry<String, NodeBean> entry: nodeMetricsByAppId.entrySet()){
			NodeBean nodeBean = entry.getValue();
			for(MetricBean metricBean: nodeBean.getMetrics()){
				String propertyName = UIMrtricsPropTransfer.getUIMetricPropertyByMetricValue(metricBean.getName());
				String format = UIMrtricsPropTransfer.getUIMetricPropertyByJsProp(propertyName)[2];
				// get a new one
				List<TimeValuePairBean> newValues = metricBean.getValues();
				// get current value
				List<TimeValueVO> currValues = propToMetrics.get(propertyName);
				// aggregate and store
				this.aggregrateNewMetricToCurrentMetric(format, newValues, currValues);
			}
		}
		
		this.calculateAvgWithSingleNode(metricProps, propToMetrics);
		
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
	
	public HistoryDetailStageVO aggregateHistoryDetialStage(SparkEventLogBean eventLog, int stageId){
		HistoryDetailStageVO historyStage = new HistoryDetailStageVO();
		
		
		List<TaskStart> taskStartList = eventLog.getTaskStart();
		List<TaskEnd> taskEndList = eventLog.getTaskEnd();
	
		
		Map<Integer, TaskStart> tempMapTaskStart = new HashMap<Integer, TaskStart>();
		
		Map<String, Integer> totalTasks = new HashMap<String, Integer>();
		int totalCompletedTask = 0;
		
		long mindurationTask = Integer.MAX_VALUE;
		long minGCTime = Integer.MAX_VALUE;
		long minInputSize = Integer.MAX_VALUE;
		long minRecord = Integer.MAX_VALUE;
		
		long maxdurationTask = 0;
		long maxGCTime = 0;
		long maxInputSize = 0;
		long maxRecord = 0;
		
		List<Long> durationTaskList = new ArrayList<Long>();
		List<Long> gcTimeList = new ArrayList<Long>();
		List<Long> inputSizeList = new ArrayList<Long>();
		List<Long> recordList = new ArrayList<Long>();
		
		
		Map<String, Integer> failedTotalTasks = new HashMap<String, Integer>();
		Map<String, Integer> succeededTotalTasks = new HashMap<String, Integer>();
		Map<String, Long> taskTotalTaskTime = new HashMap<String, Long>();
		Map<String, Long> inputSizeTotalTasks = new HashMap<String, Long>();
		Map<String, Long> recordTotalTasks = new HashMap<String, Long>();
		
		for(TaskStart taskStart : taskStartList){
			tempMapTaskStart.put(taskStart.getInfo().getIndex(), taskStart);
		}
		
		Map<Integer, TaskEnd> tempMapTaskEnd = new HashMap<Integer, TaskEnd>();
		for(TaskEnd taskEnd : taskEndList){
			tempMapTaskEnd.put(taskEnd.getInfo().getIndex(), taskEnd);
		}
		
		List<TasksVO> taskVOList = new ArrayList<TasksVO>();
		for(int i = 0 ; i < tempMapTaskStart.size(); i++){
			TasksVO taskVO = new TasksVO();
			TaskStart taskStart = tempMapTaskStart.get(i);
			TaskEnd taskEnd = tempMapTaskEnd.get(i);
			
			if(taskStart != null && taskStart.getStageId() == stageId){
				taskVO.setAttempt(taskStart.getInfo().getAttempt());
				taskVO.setIndex(taskStart.getInfo().getIndex());
				taskVO.setId(taskStart.getInfo().getId());
				taskVO.setLaunchTime(String.valueOf(taskStart.getInfo().getLanuchTime()));
				taskVO.setLocality(taskStart.getInfo().getLocality());
				
				String host = taskStart.getInfo().getHost();
				taskVO.setExecutorID(taskStart.getInfo().getExecutorId());
				taskVO.setHost(host);
			
				//sum total tasks
				if(totalTasks.get(host) != null){
					int taskNumber = totalTasks.get(host);
					totalTasks.put(host, taskNumber + 1);
				}else{
					totalTasks.put(host, 1);
				}
				totalCompletedTask++;
			
			}
		
			if(taskEnd != null && taskEnd.getStageId() == stageId){
				taskVO.setFinishTime(String.valueOf(taskEnd.getInfo().getFinishTime()));
				taskVO.setGcTime(String.valueOf(taskEnd.getMetrics().getGcTime()));
				minGCTime = this.minValue(minGCTime, taskEnd.getMetrics().getGcTime());
				maxGCTime = this.maxValue(maxGCTime,  taskEnd.getMetrics().getGcTime());
				gcTimeList.add(taskEnd.getMetrics().getGcTime());
				
				taskVO.setResultSize(taskEnd.getMetrics().getResultSize());
				taskVO.setRunTime(taskEnd.getMetrics().getRunTime());
				taskVO.setStatus(taskEnd.getReason().getReason());
				
				//sum task end failed
				if(taskEnd.getInfo().isFailed() == true){
					if(failedTotalTasks.get(taskEnd.getMetrics().getHost()) != null){
						Integer failedNumber = failedTotalTasks.get(taskEnd.getMetrics().getHost());
						failedTotalTasks.put(taskEnd.getMetrics().getHost(), failedNumber + 1);
					}else{
						failedTotalTasks.put(taskEnd.getMetrics().getHost(), 1);
					}
				}
				//sum succeeded task
				if(taskEnd.getReason().getReason().equals("Success")){
					if(succeededTotalTasks.get(taskEnd.getMetrics().getHost()) != null){
						Integer succeededNumber = succeededTotalTasks.get(taskEnd.getMetrics().getHost());
						succeededTotalTasks.put(taskEnd.getMetrics().getHost(), succeededNumber + 1);
					}else{
						succeededTotalTasks.put(taskEnd.getMetrics().getHost(), 1);
					}
				}
				//sum runtime 
				if(taskTotalTaskTime.get(taskEnd.getMetrics().getHost()) != null){
					Long taskTotalTimeNumber = taskTotalTaskTime.get(taskEnd.getMetrics().getHost());
					taskTotalTaskTime.put(taskEnd.getMetrics().getHost(), taskTotalTimeNumber + taskEnd.getMetrics().getRunTime());
				}else{
					taskTotalTaskTime.put(taskEnd.getMetrics().getHost(), taskEnd.getMetrics().getRunTime());
				}
				mindurationTask = minValue(mindurationTask, taskEnd.getMetrics().getRunTime());
				maxdurationTask = maxValue(maxdurationTask, taskEnd.getMetrics().getRunTime());
				durationTaskList.add(taskEnd.getMetrics().getRunTime());
				
			}
			if(taskEnd.getMetrics() != null && taskEnd.getMetrics().getInputMetrics() != null && taskEnd.getStageId() == stageId){
				taskVO.setInputSize(String.valueOf(taskEnd.getMetrics().getInputMetrics().getReadByte()));
				taskVO.setRecords(String.valueOf(taskEnd.getMetrics().getInputMetrics().getRecordRead()));

				//sum input size
				if(inputSizeTotalTasks.get(taskEnd.getMetrics().getHost()) != null){
					Long inputSizeTotalNumber = inputSizeTotalTasks.get(taskEnd.getMetrics().getHost());
					inputSizeTotalTasks.put(taskEnd.getMetrics().getHost(), inputSizeTotalNumber + taskEnd.getMetrics().getInputMetrics().getReadByte());
				}else{
					inputSizeTotalTasks.put(taskEnd.getMetrics().getHost(), taskEnd.getMetrics().getInputMetrics().getReadByte());
				}
				minInputSize = minValue(minInputSize, taskEnd.getMetrics().getInputMetrics().getReadByte());
				maxInputSize = maxValue(maxInputSize, taskEnd.getMetrics().getInputMetrics().getReadByte());
				inputSizeList.add(taskEnd.getMetrics().getInputMetrics().getReadByte());
				
				//sum records
				if(recordTotalTasks.get(taskEnd.getMetrics().getHost()) != null){
					Long recordTotalNumber = recordTotalTasks.get(taskEnd.getMetrics().getHost());
					recordTotalTasks.put(taskEnd.getMetrics().getHost(), recordTotalNumber + taskEnd.getMetrics().getInputMetrics().getRecordRead());
				}else{
					recordTotalTasks.put(taskEnd.getMetrics().getHost(), taskEnd.getMetrics().getInputMetrics().getRecordRead());
				}
				minRecord = minValue(minRecord, taskEnd.getMetrics().getInputMetrics().getRecordRead());
				maxRecord = maxValue(maxRecord, taskEnd.getMetrics().getInputMetrics().getRecordRead());
				recordList.add(taskEnd.getMetrics().getInputMetrics().getRecordRead());
			}
			
			
			taskVOList.add(taskVO);
		}
		
		
		historyStage.setTasks(taskVOList);
		
		
		List<BlockManager> blockManagerList = eventLog.getBlockManager();
		List<SummaryExecutorVO> aggregatorExecutor = new ArrayList<SummaryExecutorVO>();
		for(BlockManager blockManager : blockManagerList){
			SummaryExecutorVO summaryExecutorVO = new SummaryExecutorVO();
			summaryExecutorVO.setAddress(blockManager.getBlockManagerID().getHost() + ":" + blockManager.getBlockManagerID().getPort());
			if(totalTasks.get(blockManager.getBlockManagerID().getHost()) != null){
				Integer resultTotalTasks = totalTasks.get(blockManager.getBlockManagerID().getHost());
				summaryExecutorVO.setTotalTasks(resultTotalTasks);
			}
			if(failedTotalTasks.get(blockManager.getBlockManagerID().getHost()) != null){
				Integer resultFailedTotalTasks = failedTotalTasks.get(blockManager.getBlockManagerID().getHost());
				summaryExecutorVO.setFailedTasks(resultFailedTotalTasks);
			}
			if(succeededTotalTasks.get(blockManager.getBlockManagerID().getHost()) != null){
				Integer resultSucceededTotalTasks = succeededTotalTasks.get(blockManager.getBlockManagerID().getHost());
				summaryExecutorVO.setSucceededTasks(resultSucceededTotalTasks);
			}
			if(taskTotalTaskTime.get(blockManager.getBlockManagerID().getHost()) != null){
				Long resultTaskTotalTaskTime = taskTotalTaskTime.get(blockManager.getBlockManagerID().getHost());
				summaryExecutorVO.setTaskTime(resultTaskTotalTaskTime);
			}
			if(inputSizeTotalTasks.get(blockManager.getBlockManagerID().getHost()) != null){
				Long inputTotalTask = inputSizeTotalTasks.get(blockManager.getBlockManagerID().getHost());
				summaryExecutorVO.setInputSize(String.valueOf(inputTotalTask));
			}
			if(recordTotalTasks.get(blockManager.getBlockManagerID().getHost()) != null){
				Long recordTotalTask = recordTotalTasks.get(blockManager.getBlockManagerID().getHost());
				summaryExecutorVO.setRecords(String.valueOf(recordTotalTask));
			}
			summaryExecutorVO.setExecuteId(String.valueOf(blockManager.getBlockManagerID().getId()));
			summaryExecutorVO.setMaxMemory(blockManager.getMaxMemory());
			
			aggregatorExecutor.add(summaryExecutorVO);
		}
		historyStage.setAggregatorExecutor(aggregatorExecutor);
		historyStage.setCompleteTaskSize(totalCompletedTask);
		
		
		List<MetricCompletedTasksVO> metricCompletedTasks = new ArrayList<MetricCompletedTasksVO>();
		
		MetricCompletedTasksVO metricCompletedDuration = new MetricCompletedTasksVO();
		metricCompletedDuration.setMetric("Duration");
	
		if(durationTaskList.toArray().length > 0){
			metricCompletedDuration.setMin(mindurationTask);
			metricCompletedDuration.setMax(maxdurationTask);
			metricCompletedDuration.setMedian(this.medianValue(durationTaskList.toArray()));
		}
		MetricCompletedTasksVO metricCompletedGCTime = new MetricCompletedTasksVO();
		metricCompletedGCTime.setMetric("GC Time");
		if(gcTimeList.toArray().length > 0){
			metricCompletedGCTime.setMin(minGCTime);
			metricCompletedGCTime.setMax(maxGCTime);
			metricCompletedGCTime.setMedian(this.medianValue(gcTimeList.toArray()));
		}
		
		MetricCompletedTasksVO metricCompletedInputSize = new MetricCompletedTasksVO();
		metricCompletedInputSize.setMetric("Input Size");
		if(inputSizeList.toArray().length > 0){
			metricCompletedInputSize.setMin(minInputSize);
			metricCompletedInputSize.setMax(maxInputSize);
			metricCompletedInputSize.setMedian(this.medianValue(inputSizeList.toArray()));
		}
		
		
		
		MetricCompletedTasksVO metricRecords = new MetricCompletedTasksVO();
		metricRecords.setMetric("Record");
		if(recordList.toArray().length > 0){
			metricRecords.setMin(minRecord);
			metricRecords.setMax(maxRecord);
			metricRecords.setMedian(this.medianValue(recordList.toArray()));
		}
		
		metricCompletedTasks.add(metricCompletedDuration);
		metricCompletedTasks.add(metricCompletedGCTime);
		metricCompletedTasks.add(metricCompletedInputSize);
		metricCompletedTasks.add(metricRecords);
		
		
		historyStage.setMetricCompletedTasks(metricCompletedTasks);
		
		return historyStage;
	}
	
	public double medianValue(Object array[]){
		double result = 0;
		int n = array.length;

		if((array.length % 2) == 0){
			result = ((Long)array[n / 2] + (Long)array[(n / 2) - 1]) / 2.0;
		}else{
			result = (Long)array[(n - 1) / 2];
		}
		return result;
	}
	
	
	private long minValue(long value1, long value2){
		if(value1 < value2){
			return value1;
		}else{
			return value2;
		}
	}
	
	private long maxValue(long value1, long value2){
		if(value1 > value2){
			return value1;
		}else{
			return value2;
		}
		
	}
	

	private void calculateAvgWithSingleNode(List<MetricPropVO> metricProps, Map<String, List<TimeValueVO>> propToMetrics){
		String format;
		AverageAggregator avgAggregaror = (AverageAggregator)this.averageAggregator;
		for(MetricPropVO metricPropVO: metricProps){
			format = metricPropVO.getFormat();
			if(format.equals("PERCENTAGE") || format.equals("MILLIS") || format.equals("OPS")) {
				List<TimeValueVO> periodData = propToMetrics.get(metricPropVO.getProperty());
				avgAggregaror.calculateAvg(periodData);
			}
		}
	}
	
	private void calculateAvgWithMultiNode(List<MetricPropVO> metricProps, 
			Map<String, Map<String, List<TimeValueVO>>> propToMetrics){
		String format;
		AverageAggregator avgAggregaror = (AverageAggregator)this.averageAggregator;
		for(MetricPropVO metricPropVO: metricProps){
			format = metricPropVO.getFormat();
			if(format.equals("PERCENTAGE") || format.equals("MILLIS") || format.equals("OPS")) {
				Map<String, List<TimeValueVO>> node2Metrics = propToMetrics.get(metricPropVO.getProperty());
				for(List<TimeValueVO> periodData: node2Metrics.values()){
					avgAggregaror.calculateAvg(periodData);
				}
			}
		}
	}
	
	/**
	 * This function will summary the value from newPair to currPair
	 * **/
	private void aggregrateNewMetricToCurrentMetric(String format, List<TimeValuePairBean> newValues, List<TimeValueVO> currValues){
		for(TimeValuePairBean newPair: newValues){
			for(TimeValueVO currPair: currValues){
				if(this.timeUnitTransfer.transferToAxisX(newPair.getTime())
						.equals(currPair.getTime())){
					if(format.equals("BYTE") || format.equals("NONE")){
						this.quantityAggregator.aggregate(format, newPair, currPair);
					} else {
						this.averageAggregator.aggregate(format, newPair, currPair);
					}
					break;
				}
			}
		}
	}

}
