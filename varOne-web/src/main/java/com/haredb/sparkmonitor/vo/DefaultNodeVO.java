/**
 * 
 */
package com.haredb.sparkmonitor.vo;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author allen
 *
 */
@XmlRootElement
public class DefaultNodeVO {
	private List<MetricPropVO> metricProps;
	private Map<String, String> propToMetrics;
	
	public List<MetricPropVO> getMetricProps() {
		return metricProps;
	}
	public void setMetricProps(List<MetricPropVO> metricProps) {
		this.metricProps = metricProps;
	}
	public Map<String, String> getPropToMetrics() {
		return propToMetrics;
	}
	public void setPropToMetrics(Map<String, String> propToMetrics) {
		this.propToMetrics = propToMetrics;
	}
	
	
}
