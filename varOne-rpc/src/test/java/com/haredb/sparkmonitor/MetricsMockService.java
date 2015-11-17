/**
 * 
 */
package com.haredb.sparkmonitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;

import com.haredb.sparkmonitor.hadoop.rpc.AbstractServer;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsService;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServiceRequest;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServiceResponse;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.impl.MetricsServiceResponsePBImpl;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto.TupleProto;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;

/**
 * @author allen
 *
 */
public class MetricsMockService extends AbstractServer implements MetricsService {

	/**
	 * @throws Exception 
	 * 
	 */
	public MetricsMockService(Configuration config) throws Exception {
		this.constructServer(config);
	}

	/* (non-Javadoc)
	 * @see com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsService#getMetrics(com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServiceRequest)
	 */
	@Override
	public MetricsServiceResponse getMetrics(MetricsServiceRequest request)
			throws Exception {
		MetricsServiceResponse response = new MetricsServiceResponsePBImpl();
		
		List<MetricsMapProto> result = new ArrayList<MetricsMapProto>();
		MetricsMapProto.Builder metricsMapBuilder = MetricsMapProto.newBuilder();
		TupleProto.Builder tupleBuilder = TupleProto.newBuilder();
		
		metricsMapBuilder.setMetricsName(MetricsTypeProto.FS.name());
		
		tupleBuilder.setTime(System.currentTimeMillis());
		tupleBuilder.setValue("121");
		TupleProto tuple1 = tupleBuilder.build();
		metricsMapBuilder.addMetricsValues(tuple1);
		
		tupleBuilder.clear();
		
		tupleBuilder.setTime(System.currentTimeMillis());
		tupleBuilder.setValue("78");
		TupleProto tuple2 = tupleBuilder.build();
		metricsMapBuilder.addMetricsValues(tuple2);
		
		result.add(metricsMapBuilder.build());
		
		response.setResult(result);
		return response;
	}

	/* (non-Javadoc)
	 * @see com.haredb.sparkmonitor.hadoop.rpc.AbstractServer#constructServer(org.apache.hadoop.conf.Configuration)
	 */
	@Override
	public void constructServer(Configuration config) throws Exception {
		super.constructHadoopServer(MetricsService.class, config, this, 5, 8088);
	}

}
