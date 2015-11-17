/**
 * 
 */
package com.haredb.sparkmonitor;

import org.apache.hadoop.conf.Configuration;

/**
 * @author allen
 *
 */
public class MetricsMockNode {

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
		
		MetricsMockService service = new MetricsMockService(config);
		service.start();
	}

}
