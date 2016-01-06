/**
 * 
 */
package com.varone;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;

import com.varone.hadoop.rpc.metrics.MetricsServiceRequest;
import com.varone.hadoop.rpc.metrics.MetricsServiceResponse;
import com.varone.hadoop.rpc.metrics.impl.MetricsServiceRequestPBImpl;
import com.varone.hadoop.rpc.metrics.impl.pb.client.MetricsServicePBClientImpl;
import com.varone.hadoop.rpc.protos.MetricsProtos.MetricsTypeProto;

/**
 * @author allen
 *
 */
public class MetricsMockClient {

	/**
	 * @throws Exception 
	 * 
	 */
	public static void main(String[] args) throws Exception {
		Configuration config = new Configuration();
//		config.set("hbase.zookeeper.quorum", "server-a1");
////        hiveConf.set("hbase.zookeeper.quorum", "server-b1");
//		config.set("hbase.zookeeper.property.clientPort", "2181");
//		config.set("fs.default.name", "hdfs://server-a1:9000");
//		config.set("yarn.resourcemanager.address", "server-a1:8032");
//		config.set("yarn.resourcemanager.scheduler.address", "server-a1:8030");
//		config.set("yarn.resourcemanager.resource-tracker.address", "server-a1:8031");
//        config.set("yarn.resourcemanager.admin.address", "server-a1:8033");
//        config.set("mapreduce.framework.name", "yarn");
//        config.set("mapreduce.johistory.address", "server-a1:10020");
//        config.set("yarn.nodemanager.aux-services", "mapreduce_shuffle");
        
		MetricsServicePBClientImpl client = new MetricsServicePBClientImpl(1, new InetSocketAddress("user1-pc", 8088), config);
		
		MetricsServiceRequest request = new MetricsServiceRequestPBImpl();
		request.setApplicationId("application_123456789");
		
		List<MetricsTypeProto> metrics = new ArrayList<MetricsTypeProto>();
		metrics.add(MetricsTypeProto.FS);
		request.setMetrics(metrics);
		
		MetricsServiceResponse response = client.getMetrics(request);
		
		System.out.println(response.getResult().get(0).getMetricsName());
		
	}

}
