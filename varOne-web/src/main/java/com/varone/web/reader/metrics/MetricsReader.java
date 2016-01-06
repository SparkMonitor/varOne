/**
 * 
 */
package com.varone.web.reader.metrics;

import java.util.List;

import com.varone.web.metrics.bean.NodeBean;

/**
 * @author allen
 *
 */
public interface MetricsReader {
	public List<NodeBean> getAllNodeMetrics(String applicationId, List<String> metricsType) throws Exception;
	
	public NodeBean getNodeMetrics(String node, String applicationId, List<String> metricsType) throws Exception;
}
