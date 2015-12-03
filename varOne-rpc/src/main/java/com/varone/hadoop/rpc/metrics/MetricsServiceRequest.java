/**
 * 
 */
package com.varone.hadoop.rpc.metrics;

import java.util.List;

import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;

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
