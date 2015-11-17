/**
 * 
 */
package com.haredb.sparkmonitor.vo;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author allen
 *
 */
@XmlRootElement
public class HistoryVO {
	private String name;
	private String id;
	private String startTime;
	private String endTime;
	private String user;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}	
}
