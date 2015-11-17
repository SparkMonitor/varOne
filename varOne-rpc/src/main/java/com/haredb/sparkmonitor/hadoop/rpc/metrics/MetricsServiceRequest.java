/**
 * 
 */
package com.haredb.sparkmonitor.hadoop.rpc.metrics;

import java.util.List;

import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;

/**
 * @author allen
 *
 */
public interface MetricsServiceRequest {
	String getApplicationId();
	void setApplicationId(String applicationId);
	
	List<MetricsTypeProto> getMetrics();
	void setMetrics(List<MetricsTypeProto> metrics);
}
