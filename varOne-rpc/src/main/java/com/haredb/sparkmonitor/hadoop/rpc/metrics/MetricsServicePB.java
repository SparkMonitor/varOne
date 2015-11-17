/**
 * 
 */
package com.haredb.sparkmonitor.hadoop.rpc.metrics;

import org.apache.hadoop.ipc.ProtocolInfo;

import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsServiceProtos.MetricsService.BlockingInterface;


/**
 * @author allen
 *
 */
@ProtocolInfo(
	    protocolName = "com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsRpcService",
	    protocolVersion = 1)
public interface MetricsServicePB extends BlockingInterface {

}
