/**
 * 
 */
package com.haredb.sparkmonitor.node;

import java.util.List;

/**
 * @author allen
 *
 */
public class MetricTuple {
	private long time;
	private String value;
	
	public MetricTuple(String pairStr){
		String[] pair = pairStr.split(",");
		this.setTime(Long.parseLong(pair[0]));
		this.setValue(pair[1]);
	}
	
	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
