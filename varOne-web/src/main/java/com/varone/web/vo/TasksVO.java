package com.varone.web.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TasksVO {
	private int index;
	private int id;
	private int attempt;
	private String launchTime;
	private String finishTime;
	private String status;
	private String duration;
	private String locality;
	private String gcTime;
	private long resultSize;
	private String inputSize;
	private String executorIDAndHost;
	private String inputSizeAndRecords;
	private long runTime;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAttempt() {
		return attempt;
	}
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	public String getLaunchTime() {
		return launchTime;
	}
	public void setLaunchTime(String launchTime) {
		this.launchTime = launchTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getGcTime() {
		return gcTime;
	}
	public void setGcTime(String gcTime) {
		this.gcTime = gcTime;
	}

	public String getInputSize() {
		return inputSize;
	}
	public void setInputSize(String inputSize) {
		this.inputSize = inputSize;
	}
	public String getExecutorIDAndHost() {
		return executorIDAndHost;
	}
	public void setExecutorIDAndHost(String executorIDAndHost) {
		this.executorIDAndHost = executorIDAndHost;
	}
	public long getRunTime() {
		return runTime;
	}
	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}
	public long getResultSize() {
		return resultSize;
	}
	public void setResultSize(long resultSize) {
		this.resultSize = resultSize;
	}
	public String getInputSizeAndRecords() {
		return inputSizeAndRecords;
	}
	public void setInputSizeAndRecords(String inputSizeAndRecords) {
		this.inputSizeAndRecords = inputSizeAndRecords;
	}
	
	
	
}
