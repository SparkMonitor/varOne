/**
 * 
 */
package com.haredb.sparkmonitor.hadoop.rpc.metrics.impl.pb.service;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsService;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServicePB;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServiceResponse;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.impl.MetricsServiceRequestPBImpl;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.impl.MetricsServiceResponsePBImpl;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsRequestProto;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto;

/**
 * @author allen
 *
 */
public class MetricsServicePBServiceImpl implements MetricsServicePB {
	
	protected MetricsService metricsService;
	
	/**
	 * 
	 */
	public MetricsServicePBServiceImpl(MetricsService metricsService) {
		this.metricsService = metricsService;
	}

	/* (non-Javadoc)
	 * @see com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsServiceProtos.Metrics.BlockingInterface#getMetrics(com.google.protobuf.RpcController, com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsRequestProto)
	 */
	@Override
	public MetricsResponseProto getMetrics(RpcController controller,
			MetricsRequestProto proto) throws ServiceException {
		MetricsServiceRequestPBImpl request = new MetricsServiceRequestPBImpl(proto);
		try{
			MetricsServiceResponse response = this.metricsService.getMetrics(request);
			return ((MetricsServiceResponsePBImpl)response).getProto();
		} catch (Exception e) {
			throw new ServiceException(e);
	    }
	}

}
