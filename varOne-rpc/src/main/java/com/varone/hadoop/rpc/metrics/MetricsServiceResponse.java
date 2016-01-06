/**
 * 
 */
package com.varone.hadoop.rpc.metrics;

import java.util.List;

import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto;

/**
 * @author allen
 *
 */
public interface MetricsServiceResponse {
	List<MetricsMapProto> getResult();
	void setResult(List<MetricsMapProto> result);
}
