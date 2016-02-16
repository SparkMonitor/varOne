/**
 * 
 */
package com.varone.web.reader.metrics.stub;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;

import com.varone.hadoop.rpc.metrics.MetricsServiceRequest;
import com.varone.hadoop.rpc.metrics.MetricsServiceResponse;
import com.varone.hadoop.rpc.metrics.impl.MetricsServiceRequestPBImpl;
import com.varone.hadoop.rpc.metrics.impl.pb.client.MetricsServicePBClientImpl;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;

/**
 * @author allen
 *
 */
public class MetricsStub implements Runnable{
	private Logger logger = Logger.getLogger(MetricsStub.class.getName());
	
	private String address;
	private int port;
	private String applicationId;
	private RpcThreadListener listener;
	private MetricsServiceResponse response;
	private List<MetricsTypeProto> metricsType;
	
	public MetricsStub(String address, int port, 
			String applicationId, List<MetricsTypeProto> metricsType, 
			RpcThreadListener rpcThreadListener) {
		logger.debug("MetricsStub constructor, address=" + address + " port=" + port 
		          + " applicationId=" + applicationId + " metricsTypeSize=" + metricsType.size() 
		          + " rpcThreadLister is complete=" + rpcThreadListener.isAllComplete());
		
		this.port = port;
		this.address = address;
		this.metricsType = metricsType;
		this.applicationId = applicationId;
		this.listener = rpcThreadListener;
		this.listener.increaseRpcThreadCount();
	}
	
	public MetricsStub(String address, int port, 
			String applicationId, List<MetricsTypeProto> metricsType) {
		logger.debug("MetricsStub constructor, address=" + address + " port=" + port 
				          + " applicationId=" + applicationId + " metricsTypeSize=" + metricsType.size());
		
		this.port = port;
		this.address = address;
		this.metricsType = metricsType;
		this.applicationId = applicationId;
	}

	
	public MetricsServiceResponse getNodeMetrics(){
		return this.response;
	}


	@Override
	public void run() {
		logger.info("run method");
		Configuration config = new Configuration();
		MetricsServicePBClientImpl client;
		try {
			client = new MetricsServicePBClientImpl(1, new InetSocketAddress(address, port), config);
			MetricsServiceRequest request = new MetricsServiceRequestPBImpl();
			request.setApplicationId(this.applicationId);
			request.setMetrics(this.metricsType);
			
			this.response = client.getMetrics(request);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			logger.info("finally run method");
			if(this.listener != null)
				this.listener.decreaseRpcThreadCount();
		}
	}
	
	public String hostName(){
		logger.info("MetricsStub hostName Method, hostName=" + this.address);
		return this.address;
	}
}
