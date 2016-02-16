/**
 * 
 */
package com.varone.web.metrics.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author allen
 *
 */
public class NodeBean {
	private String host;
	private List<MetricBean> metrics;
	
	public NodeBean(){
		this.metrics = new ArrayList<MetricBean>();
	}
	
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the metrics
	 */
	public List<MetricBean> getMetrics() {
		return metrics;
	}
	/**
	 * @param metrics the metrics to set
	 */
	public void setMetrics(List<MetricBean> metrics) {
		this.metrics = metrics;
	}
	
	public void addMetrics(MetricBean metric){
		this.metrics.add(metric);
	}
	
}
