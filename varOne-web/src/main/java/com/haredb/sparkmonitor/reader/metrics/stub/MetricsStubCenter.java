/**
 * 
 */
package com.haredb.sparkmonitor.reader.metrics.stub;

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
	/**
	 * 
	 */
	public MetricsStubCenter() {
		// TODO Auto-generated constructor stub
	}

	public MetricsStubCenter(List<String> nodes) {
		this.nodes = nodes;
	}

	public Map<String, List<MetricsMapProto>> getAllNodeMetrics(
			String applicationId, List<MetricsTypeProto> encodeMetricsType) throws InterruptedException {
		Map<String, List<MetricsMapProto>> result = new LinkedHashMap<String, List<MetricsMapProto>>();
		
		RpcThreadListener rpcThreadListener = new RpcThreadListener();
		List<MetricsStub> stubs = new ArrayList<MetricsStub>(this.nodes.size());
		
		for(String host: this.nodes){
			MetricsStub stub = new MetricsStub(host, 8888, applicationId, encodeMetricsType, rpcThreadListener);
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
		MetricsStub stub = new MetricsStub(node, 8888, applicationId, encodeMetricsType);
		Thread t1 = new Thread(stub);
		t1.start();
		t1.join();
		
		return stub.getNodeMetrics().getResult();
	}

}
