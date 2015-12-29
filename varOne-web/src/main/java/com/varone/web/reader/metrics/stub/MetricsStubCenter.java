/**
 * 
 */
package com.varone.web.reader.metrics.stub;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto;

/**
 * @author allen
 *
 */
public class MetricsStubCenter {
	private List<String> nodes;
	private int port;
	/**
	 * 
	 */
	public MetricsStubCenter(String port) {
		this.port = Integer.valueOf(port);
	}

	public MetricsStubCenter(List<String> nodes, String port) {
		this.port = Integer.valueOf(port);
		this.nodes = nodes;
	}

	public Map<String, List<MetricsMapProto>> getAllNodeMetrics(
			String applicationId, List<MetricsTypeProto> encodeMetricsType) throws InterruptedException {
		Map<String, List<MetricsMapProto>> result = new LinkedHashMap<String, List<MetricsMapProto>>();
		
		RpcThreadListener rpcThreadListener = new RpcThreadListener();
		List<MetricsStub> stubs = new ArrayList<MetricsStub>(this.nodes.size());
		
		for(String host: this.nodes){
			MetricsStub stub = new MetricsStub(host, this.port, applicationId, encodeMetricsType, rpcThreadListener);
			stubs.add(stub);
			Thread t1 = new Thread(stub);
			t1.start();
		}
		
		synchronized(rpcThreadListener){
			if(!rpcThreadListener.isAllComplete()){
				rpcThreadListener.wait();
			}
		}
		
		for(MetricsStub stub: stubs){
			String host = stub.hostName();
			result.put(host, stub.getNodeMetrics().getResult());
		}
		
		return result;
	}

	public List<MetricsMapProto> getNodeMetrics(String node,
			String applicationId, List<MetricsTypeProto> encodeMetricsType) throws InterruptedException{
		MetricsStub stub = new MetricsStub(node, this.port, applicationId, encodeMetricsType);
		Thread t1 = new Thread(stub);
		t1.start();
		t1.join();
		
		return stub.getNodeMetrics().getResult();
	}

}
