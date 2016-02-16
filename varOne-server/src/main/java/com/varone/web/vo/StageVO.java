/**
 * 
 */
package com.varone.web.vo;

/**
 * @author allen
 *
 */
public class StageVO {
	private int id;
	private String description;
	private String submitTime;
	private String duration;
	private String tasksSuccessVSTotal;
	private String tasksSuccessPercent;
	private String readAmount;
	private String writeAmount;

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

	public String getTasksSuccessVSTotal() {
		return tasksSuccessVSTotal;
	}

	public void setTasksSuccessVSTotal(String tasksSuccessVSTotal) {
		this.tasksSuccessVSTotal = tasksSuccessVSTotal;
	}

	public String getTasksSuccessPercent() {
		return tasksSuccessPercent;
	}

	public void setTasksSuccessPercent(String tasksSuccessPercent) {
		this.tasksSuccessPercent = tasksSuccessPercent;
	}

	public String getReadAmount() {
		return readAmount;
	}

	public void setReadAmount(String readAmount) {
		this.readAmount = readAmount;
	}

	public String getWriteAmount() {
		return writeAmount;
	}

	public void setWriteAmount(String writeAmount) {
		this.writeAmount = writeAmount;
	}
	
}
