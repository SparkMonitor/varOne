/**
 * 
 */
package com.haredb.sparkmonitor.metrics.client;

import java.util.ArrayList;
import java.util.List;

import com.haredb.sparkmonitor.reader.metrics.stub.MetricsStub;
import com.haredb.sparkmonitor.reader.metrics.stub.RpcThreadListener;
import com.varone.hadoop.rpc.metrics.MetricsServiceResponse;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsResponseProto.MetricsMapProto;


/**
 * @author allen
 *
 */
public class MetricsStubMultiThreadTest{

	final static String[] HOSTS = new String[]{"server-a2","server-a3","server-a4","server-a5"};
	
	public static void main(String[] args) throws InterruptedException{
		String applicationId = "application_1439169262151_0539";
		
		List<MetricsTypeProto> metricsTypeProto = new ArrayList<MetricsTypeProto>();
		metricsTypeProto.add(MetricsTypeProto.EXEC_FS_HDFS_READ_BYTES);
		
		RpcThreadListener rpcThreadListener = new RpcThreadListener();
		List<MetricsStub> stubs = new ArrayList<MetricsStub>(HOSTS.length);
		
		
		for(String host: HOSTS){
			MetricsStub stub = new MetricsStub(host, 8888, applicationId, metricsTypeProto, rpcThreadListener);
			stubs.add(stub);
			Thread t1 = new Thread(stub);
			t1.start();
		}
		
		synchronized(rpcThreadListener){
			if(!rpcThreadListener.isAllComplete()){
				rpcThreadListener.wait();
			}
		}
		
		System.out.println("finish !!!!!!!!!");
		
		for(MetricsStub stub: stubs){
		
			MetricsServiceResponse response = stub.getNodeMetrics();
			
			List<MetricsMapProto> result = response.getResult();
			System.out.println(result.size());
			
//			for(MetricsMapProto proto: result){
//				System.out.println(proto.getMetricsName());
//				System.out.println("========");
//				List<TupleProto> metricsValuesList = proto.getMetricsValuesList();
//				for(TupleProto tuple: metricsValuesList){
//					System.out.println(tuple.getTime()+"=>"+tuple.getValue());
//				}
//				
//			}
		}
	}

}
