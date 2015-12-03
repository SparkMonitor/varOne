/**
 * 
 */
package com.varone.hadoop.rpc.metrics;

/**
 * @author allen
 *
 */
public interface MetricsService {
	MetricsServiceResponse getMetrics(MetricsServiceRequest request) throws Exception;
}
