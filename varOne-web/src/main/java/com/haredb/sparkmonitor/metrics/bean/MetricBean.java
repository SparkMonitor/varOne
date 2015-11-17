/**
 * 
 */
package com.haredb.sparkmonitor.metrics.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author allen
 *
 */
public class MetricBean {
	private String name;
	private List<TimeValuePairBean> values;
	
	public MetricBean(){
		this.values = new ArrayList<TimeValuePairBean>();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the values
	 */
	public List<TimeValuePairBean> getValues() {
		return values;
	}
	/**
	 * @param values the values to set
	 */
	public void setValues(List<TimeValuePairBean> values) {
		this.values = values;
	}
	
	public void addPair(TimeValuePairBean pair){
		this.values.add(pair);
	}
}
