/**
 * 
 */
package com.varone.web.vo;

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
	private Map<String, List<TimeValueVO>> propToMetrics;
	
	public List<MetricPropVO> getMetricProps() {
		return metricProps;
	}
	public void setMetricProps(List<MetricPropVO> metricProps) {
		this.metricProps = metricProps;
	}
	public Map<String, List<TimeValueVO>> getPropToMetrics() {
		return propToMetrics;
	}
	public void setPropToMetrics(Map<String, List<TimeValueVO>> propToMetrics) {
		this.propToMetrics = propToMetrics;
	}
	
	
}
