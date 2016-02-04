/**
 * 
 */
package com.varone.web.vo;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author allen
 *
 */
@XmlRootElement
public class DefaultApplicationVO {
	private int executorNum;
	private int taskNum;
	private int completedTaskNum;
	private int failedTaskNum;
	
	private List<MetricPropVO> metricProps;
	
	private Map<String, String> taskStartedNumByNode;
	private Map<String, Integer> executorNumByNode;
	
	private Map<String, Map<String, List<TimeValueVO>>> propToMetrics;
	
	public int getExecutorNum() {
		return executorNum;
	}
	public void setExecutorNum(int executorNum) {
		this.executorNum = executorNum;
	}
	public int getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}
	public int getCompletedTaskNum() {
		return completedTaskNum;
	}
	public void setCompletedTaskNum(int completedTaskNum) {
		this.completedTaskNum = completedTaskNum;
	}
	public int getFailedTaskNum() {
		return failedTaskNum;
	}
	public void setFailedTaskNum(int failedTaskNum) {
		this.failedTaskNum = failedTaskNum;
	}
	public Map<String, String> getTaskStartedNumByNode() {
		return taskStartedNumByNode;
	}
	public void setTaskStartedNumByNode(Map<String, String> taskStartedNumByNode) {
		this.taskStartedNumByNode = taskStartedNumByNode;
	}
	public Map<String, Integer> getExecutorNumByNode() {
		return executorNumByNode;
	}
	public void setExecutorNumByNode(Map<String, Integer> executorNumByNode) {
		this.executorNumByNode = executorNumByNode;
	}
	public List<MetricPropVO> getMetricProps() {
		return metricProps;
	}
	public void setMetricProps(List<MetricPropVO> metricProps) {
		this.metricProps = metricProps;
	}
	public Map<String, Map<String, List<TimeValueVO>>> getPropToMetrics() {
		return propToMetrics;
	}
	public void setPropToMetrics(Map<String, Map<String, List<TimeValueVO>>> propToMetrics) {
		this.propToMetrics = propToMetrics;
	}
	
}
