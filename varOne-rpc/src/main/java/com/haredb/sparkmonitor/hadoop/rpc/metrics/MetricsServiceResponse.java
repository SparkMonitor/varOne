/**
 * 
 */
package com.haredb.sparkmonitor.hadoop.rpc.metrics;

import java.util.List;

import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto;

/**
 * @author allen
 *
 */
public interface MetricsServiceResponse {
	List<MetricsMapProto> getResult();
	void setResult(List<MetricsMapProto> result);
}
