/**
 * 
 */
package com.varone.node;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;

import com.haredb.sparkmonitor.hadoop.rpc.AbstractServer;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsService;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServiceRequest;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServiceResponse;
import com.haredb.sparkmonitor.hadoop.rpc.metrics.impl.MetricsServiceResponsePBImpl;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto;
import com.haredb.sparkmonitor.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;
import com.varone.node.reader.NodeMetricsReader;
import com.varone.node.utils.Consts;
import com.varone.node.utils.MetricsDataTransfer;

/**
 * @author allen
 *
 */
public class NodeMetricsService extends AbstractServer implements MetricsService {
	
	private NodeMetricsReader reader;
	
	
	/**
	 * @throws Exception 
	 * 
	 */
	public NodeMetricsService(Configuration config) throws Exception {
		this.constructServer(config);
		this.reader = new NodeMetricsReader(config.get(Consts.HARE_SPARK_METRICS_CSV_DIR));
	}

	/* (non-Javadoc)
	 * @see com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsService#getMetrics(com.haredb.sparkmonitor.hadoop.rpc.metrics.MetricsServiceRequest)
	 */
	@Override
	public MetricsServiceResponse getMetrics(MetricsServiceRequest request)
			throws Exception {
		String applicationId = request.getApplicationId();
		List<MetricsTypeProto> metricsTypeProtos = request.getMetrics();
		
		MetricsDataTransfer transfer = new MetricsDataTransfer();
		
		List<MetricsType> decodeMetricsType = transfer.decodeMetricsType(metricsTypeProtos);
		Map<String, List<MetricTuple>> metricsByApplicationId = 
				this.reader.readMetricsByApplicationId(applicationId, decodeMetricsType);
		
		List<MetricsMapProto> metricsProtos = transfer.encodeMetricsData(metricsByApplicationId);
		
		MetricsServiceResponse response = new MetricsServiceResponsePBImpl();
		response.setResult(metricsProtos);
		
		return response;
	}

	/* (non-Javadoc)
	 * @see com.haredb.sparkmonitor.hadoop.rpc.AbstractServer#constructServer(org.apache.hadoop.conf.Configuration)
	 */
	@Override
	public void constructServer(Configuration config) throws Exception {
		super.constructHadoopServer(
				MetricsService.class, config, this, 
				Integer.parseInt(config.get(Consts.HARE_SPARK_MONITOR_NODE_THREAD)), 
				Integer.parseInt(config.get(Consts.HARE_SPARK_MONITOR_NODE_PORT)));
	}

}
