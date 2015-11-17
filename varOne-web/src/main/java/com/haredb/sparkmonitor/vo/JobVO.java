package com.haredb.sparkmonitor.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author allen
 *
 */
@XmlRootElement
public class JobVO {
	private int id;
	private String description;
	private String submitTime;
	private String duration;
	private String stagesSuccessVSTotal;
	private String stagesSuccessPercent;
	private String tasksSuccessVSTotal;
	private String tasksSuccessPercent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStagesSuccessVSTotal() {
		return stagesSuccessVSTotal;
	}
	public void setStagesSuccessVSTotal(String stagesSuccessVSTotal) {
		this.stagesSuccessVSTotal = stagesSuccessVSTotal;
	}
	public String getTasksSuccessVSTotal() {
		return tasksSuccessVSTotal;
	}
	public void setTasksSuccessVSTotal(String tasksSuccessVSTotal) {
		this.tasksSuccessVSTotal = tasksSuccessVSTotal;
	}
	public String getStagesSuccessPercent() {
		return stagesSuccessPercent;
	}
	public void setStagesSuccessPercent(String stagesSuccessPercent) {
		this.stagesSuccessPercent = stagesSuccessPercent;
	}
	public String getTasksSuccessPercent() {
		return tasksSuccessPercent;
	}
	public void setTasksSuccessPercent(String tasksSuccessPercent) {
		this.tasksSuccessPercent = tasksSuccessPercent;
	}	
}
