/**
 * 
 */
package com.varone.node;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;

import com.varone.hadoop.rpc.AbstractServer;
import com.varone.hadoop.rpc.metrics.MetricsService;
import com.varone.hadoop.rpc.metrics.MetricsServiceRequest;
import com.varone.hadoop.rpc.metrics.MetricsServiceResponse;
import com.varone.hadoop.rpc.metrics.impl.MetricsServiceResponsePBImpl;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto;
import com.varone.node.reader.NodeMetricsReader;
import com.varone.node.utils.Consts;
import com.varone.node.utils.MetricsDataTransfer;

/**
 * @author allen
 *
 */
public class NodeMetricsService extends AbstractServer implements MetricsService {
	private static final Logger LOG = Logger.getLogger(NodeMetricsService.class);
	private NodeMetricsReader reader;
	
	
	/**
	 * @throws Exception 
	 * 
	 */
	public NodeMetricsService(Configuration config) throws Exception {
		this.constructServer(config);
		this.reader = new NodeMetricsReader(config.get(Consts.SPARK_METRICS_CSV_DIR));
	}

	/* (non-Javadoc)
	 * @see com.varone.hadoop.rpc.metrics.MetricsService#getMetrics(com.varone.hadoop.rpc.metrics.MetricsServiceRequest)
	 */
	@Override
	public MetricsServiceResponse getMetrics(MetricsServiceRequest request)
			throws Exception {
		String applicationId = null;
		try{
			applicationId = request.getApplicationId();
			List<MetricsTypeProto> metricsTypeProtos = request.getMetrics();
			
			MetricsDataTransfer transfer = new MetricsDataTransfer();
			
			List<MetricsType> decodeMetricsType = transfer.decodeMetricsType(metricsTypeProtos);
			Map<String, List<MetricTuple>> metricsByApplicationId = 
					this.reader.readMetricsByApplicationId(applicationId, decodeMetricsType);
			
			List<MetricsMapProto> metricsProtos = transfer.encodeMetricsData(metricsByApplicationId);
			
			MetricsServiceResponse response = new MetricsServiceResponsePBImpl();
			response.setResult(metricsProtos);
			return response;
		} catch(Exception e) {
			LOG.error("Fetch metrics of [" + applicationId + "] failed on VarOned", e);
			throw e;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.varone.hadoop.rpc.AbstractServer#constructServer(org.apache.hadoop.conf.Configuration)
	 */
	@Override
	public void constructServer(Configuration config) throws Exception {
		super.constructHadoopServer(
				MetricsService.class, config, this, 
				Integer.parseInt(config.get(Consts.VARONE_NODE_THREAD_NUM)), 
				Integer.parseInt(config.get(Consts.VARONE_NODE_PORT)));
	}

}
