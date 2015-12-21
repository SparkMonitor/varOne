package com.varone.web.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HistoryDetailStageVO {

	private List<TasksVO> tasks;

	public List<TasksVO> getTasks() {
		return tasks;
	}

	public void setTasks(List<TasksVO> tasks) {
		this.tasks = tasks;
	}
	
	
	
}
