/**
 * 
 */
package com.varone.node.reader;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.varone.node.MetricTuple;
import com.varone.node.MetricsType;

/**
 * @author allen
 *
 */
public class NodeMetricsReader {
	private String metricsDir;
	/**
	 * 
	 */
	public NodeMetricsReader(String metricsDir) {
		this.metricsDir = metricsDir;
	}
	
	public Map<String, List<MetricTuple>> readMetricsByApplicationId(String applicationId, List<MetricsType> metricsType) throws Exception{
		MetricsReader reader = null;
		
		boolean isAllFsMetrics = false, isAllJvmMetrics = false, isAllThreadPoolMetrics = false;
		
		if((isAllFsMetrics=metricsType.contains(MetricsType.FS))) 
			reader = new FsMetricsReader(reader, MetricsType.FS);
		
		if((isAllJvmMetrics=metricsType.contains(MetricsType.JVM))) 
			reader = new JvmMetricsReader(reader, MetricsType.JVM);
		
		if((isAllThreadPoolMetrics=metricsType.contains(MetricsType.THREAD_POOL))) 
			reader = new ThreadPoolMetricsReader(reader, MetricsType.THREAD_POOL);
		
		for(MetricsType type: metricsType){
			
			switch(type){
				
				case EXEC_FS_LOCAL_LARGEREAD_OPS:
				case EXEC_FS_LOCAL_READ_BYTES:
				case EXEC_FS_LOCAL_READ_OPS:
				case EXEC_FS_LOCAL_WRITE_BYTES:
				case EXEC_FS_LOCAL_WRITE_OPS:
				case EXEC_FS_HDFS_LARGEREAD_OPS:
				case EXEC_FS_HDFS_READ_BYTES:
				case EXEC_FS_HDFS_READ_OPS:
				case EXEC_FS_HDFS_WRITE_BYTES:
				case EXEC_FS_HDFS_WRITE_OPS:
					if(!isAllFsMetrics)
						reader = new FsMetricsReader(reader, type);
					break;
				
				case EXEC_THREADPOOL_ACTIVETASK:
				case EXEC_THREADPOOL_COMPLETETASK:
				case EXEC_THREADPOOL_CURRPOOL_SIZE:
				case EXEC_THREADPOOL_MAXPOOL_SIZE:
					if(!isAllThreadPoolMetrics)
						reader = new ThreadPoolMetricsReader(reader, type);
					break;
					
				case JVM_HEAP_COMMITED:
				case JVM_HEAP_INIT:
				case JVM_HEAP_MAX:
				case JVM_HEAP_USAGE:
				case JVM_HEAP_USED:
				case JVM_NON_HEAP_COMMITED:
				case JVM_NON_HEAP_INIT:
				case JVM_NON_HEAP_MAX:
				case JVM_NON_HEAP_USAGE:
				case JVM_NON_HEAP_USED:
				case JVM_POOLS_CODE_CACHE_USAGE:
				case JVM_POOLS_PS_EDEN_SPACE_USAGE:
				case JVM_POOLS_PS_OLD_GEN_USAGE:
				case JVM_POOLS_PS_PERM_GEN_USAGE:
				case JVM_POOLS_PS_SURVIVOR_SPACE_USAGE:
				case JVM_PS_MARKSWEEP_COUNT:
				case JVM_PS_MARKSWEEP_TIME:
				case JVM_PS_SCAVENGE_COUNT:
				case JVM_PS_SCAVENGE_TIME:
					if(!isAllJvmMetrics)
						reader = new JvmMetricsReader(reader, type);
					break;
					
				case FS:
				case JVM:
				case THREAD_POOL:
					//processed at before.
					break;
				default:
					throw new Exception("The Metrics is not match(FS, JVM, TP)");
			}
		}
		
		if(reader == null){
			return new LinkedHashMap<String, List<MetricTuple>>();
		}
		
		return reader.read(applicationId, this.metricsDir);
	}
	

}
