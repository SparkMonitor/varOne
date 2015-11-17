/**
 * 
 */
package com.haredb.sparkmonitor.vo;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author allen
 *
 */
@XmlRootElement
public class DefaultTotalNodeVO {
	private int nodeNum;
	private int jobNum;
	private int taskNum;
	private int executorNum;
	
	private List<MetricPropVO> metricProps;
	
	
	private Map<String, Integer> taskStartedNumByNode;
	private Map<String, Integer> executorNumByNode;
	
	private Map<String, Map<String, String>> propToMetrics;

	public int getNodeNum() {
		return nodeNum;
	}
	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}
	public int getJobNum() {
		return jobNum;
	}
	public void setJobNum(int jobNum) {
		this.jobNum = jobNum;
	}
	public int getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}
	public int getExecutorNum() {
		return executorNum;
	}
	public void setExecutorNum(int executorNum) {
		this.executorNum = executorNum;
	}
	public Map<String, Integer> getTaskStartedNumByNode() {
		return taskStartedNumByNode;
	}
	public void setTaskStartedNumByNode(Map<String, Integer> taskNumByNode) {
		this.taskStartedNumByNode = taskNumByNode;
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
	public Map<String, Map<String, String>> getPropToMetrics() {
		return propToMetrics;
	}
	public void setPropToMetrics(Map<String, Map<String, String>> propToMetrics) {
		this.propToMetrics = propToMetrics;
	}
		
}
