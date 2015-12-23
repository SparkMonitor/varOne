package com.varone.web.vo;

public class SummaryExecutorVO {
	private String executeId;
	private String address;
	private Long taskTime;
	private int totalTasks;
	private int failedTasks;
	private int succeededTasks;
	private String inputSizeAndrecords;
	private long maxMemory;
	
	public String getExecuteId() {
		return executeId;
	}
	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Long taskTime) {
		this.taskTime = taskTime;
	}
	public int getTotalTasks() {
		return totalTasks;
	}
	public void setTotalTasks(int totalTasks) {
		this.totalTasks = totalTasks;
	}
	public int getFailedTasks() {
		return failedTasks;
	}
	public void setFailedTasks(int failedTasks) {
		this.failedTasks = failedTasks;
	}
	public int getSucceededTasks() {
		return succeededTasks;
	}
	public void setSucceededTasks(int succeededTasks) {
		this.succeededTasks = succeededTasks;
	}
	public String getInputSizeAndrecords() {
		return inputSizeAndrecords;
	}
	public void setInputSizeAndrecords(String inputSizeAndrecords) {
		this.inputSizeAndrecords = inputSizeAndrecords;
	}
	public long getMaxMemory() {
		return maxMemory;
	}
	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}
	
	
	

}
