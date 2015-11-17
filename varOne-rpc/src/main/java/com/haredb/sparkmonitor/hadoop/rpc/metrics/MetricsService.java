/**
 * 
 */
package com.haredb.sparkmonitor.hadoop.rpc.metrics;

/**
 * @author allen
 *
 */
public interface MetricsService {
	MetricsServiceResponse getMetrics(MetricsServiceRequest request) throws Exception;
}
