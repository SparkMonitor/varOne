package com.varone.web.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HistoryDetailStageVO {

	private int completeTaskSize;
	private List<SummaryExecutorVO> aggregatorExecutor;
	private List<TasksVO> tasks;
	private List<MetricCompletedTasksVO> metricCompletedTasks;
	
	public List<SummaryExecutorVO> getAggregatorExecutor() {
		return aggregatorExecutor;
	}

	public void setAggregatorExecutor(List<SummaryExecutorVO> aggregatorExecutor) {
		this.aggregatorExecutor = aggregatorExecutor;
	}

	public List<TasksVO> getTasks() {
		return tasks;
	}

	public void setTasks(List<TasksVO> tasks) {
		this.tasks = tasks;
	}

	public int getCompleteTaskSize() {
		return completeTaskSize;
	}

	public void setCompleteTaskSize(int completeTaskSize) {
		this.completeTaskSize = completeTaskSize;
	}

	public List<MetricCompletedTasksVO> getMetricCompletedTasks() {
		return metricCompletedTasks;
	}

	public void setMetricCompletedTasks(
			List<MetricCompletedTasksVO> metricCompletedTasks) {
		this.metricCompletedTasks = metricCompletedTasks;
	}
	
	
}
