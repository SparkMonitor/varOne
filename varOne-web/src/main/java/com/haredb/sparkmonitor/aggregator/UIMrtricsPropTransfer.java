/**
 * 
 */
package com.haredb.sparkmonitor.aggregator;

import java.util.LinkedHashMap;
import java.util.Map;

import com.varone.node.MetricsType;

/**
 * @author allen
 *
 */
public class UIMrtricsPropTransfer {

	public static Map<String, String> metricValue2jsProp;
	public static Map<String, String> metricName2jsProp;
	
	static {
		metricValue2jsProp = new LinkedHashMap<String, String>();   //metrics file prefix to javascript property name
		metricName2jsProp = new LinkedHashMap<String, String>();    //metrics name to javascript property name and UI title
		metricValue2jsProp.put(MetricsType.EXEC_FS_LOCAL_LARGEREAD_OPS.type(), "localLROps");
		metricValue2jsProp.put(MetricsType.EXEC_FS_LOCAL_READ_BYTES.type(), "localReadAmount");
		metricValue2jsProp.put(MetricsType.EXEC_FS_LOCAL_READ_OPS.type(), "localReadOps");
		metricValue2jsProp.put(MetricsType.EXEC_FS_LOCAL_WRITE_BYTES.type(), "localWriteAmount");
		metricValue2jsProp.put(MetricsType.EXEC_FS_LOCAL_WRITE_OPS.type(), "localWriteAOps");
		
		metricValue2jsProp.put(MetricsType.EXEC_FS_HDFS_LARGEREAD_OPS.type(), "hdfsLROps");
		metricValue2jsProp.put(MetricsType.EXEC_FS_HDFS_READ_BYTES.type(), "hdfsReadAmount");
		metricValue2jsProp.put(MetricsType.EXEC_FS_HDFS_READ_OPS.type(), "hdfsReadOps");
		metricValue2jsProp.put(MetricsType.EXEC_FS_HDFS_WRITE_BYTES.type(), "hdfsWriteAmount");
		metricValue2jsProp.put(MetricsType.EXEC_FS_HDFS_WRITE_OPS.type(), "hdfsWriteOps");
		
		metricValue2jsProp.put(MetricsType.EXEC_THREADPOOL_ACTIVETASK.type(), "activeTask");
		metricValue2jsProp.put(MetricsType.EXEC_THREADPOOL_COMPLETETASK.type(), "completeTask");
		metricValue2jsProp.put(MetricsType.EXEC_THREADPOOL_CURRPOOL_SIZE.type(), "currPoolSize");
		metricValue2jsProp.put(MetricsType.EXEC_THREADPOOL_MAXPOOL_SIZE.type(), "maxPoolSize");
		
		metricValue2jsProp.put(MetricsType.JVM_HEAP_COMMITED.type(), "commitedHeap");
		metricValue2jsProp.put(MetricsType.JVM_HEAP_INIT.type(), "initHeap");
		metricValue2jsProp.put(MetricsType.JVM_HEAP_MAX.type(), "maxHeap");
		metricValue2jsProp.put(MetricsType.JVM_HEAP_USAGE.type(), "heapUsage");
		metricValue2jsProp.put(MetricsType.JVM_HEAP_USED.type(), "usedHeap");
		
		metricValue2jsProp.put(MetricsType.JVM_NON_HEAP_COMMITED.type(), "commitedNonHeap");
		metricValue2jsProp.put(MetricsType.JVM_NON_HEAP_INIT.type(), "initNonHeap");
		metricValue2jsProp.put(MetricsType.JVM_NON_HEAP_MAX.type(), "maxNonHeap");
		metricValue2jsProp.put(MetricsType.JVM_NON_HEAP_USAGE.type(), "nonHeapUsage");
		metricValue2jsProp.put(MetricsType.JVM_NON_HEAP_USED.type(), "usedNonHeap");
		
		metricValue2jsProp.put(MetricsType.JVM_POOLS_CODE_CACHE_USAGE.type(), "codeCacheUsage");
		metricValue2jsProp.put(MetricsType.JVM_POOLS_PS_EDEN_SPACE_USAGE.type(), "edenSpaceUsage");
		metricValue2jsProp.put(MetricsType.JVM_POOLS_PS_OLD_GEN_USAGE.type(), "oldGenUsage");
		metricValue2jsProp.put(MetricsType.JVM_POOLS_PS_PERM_GEN_USAGE.type(), "permGenUsage");
		metricValue2jsProp.put(MetricsType.JVM_POOLS_PS_SURVIVOR_SPACE_USAGE.type(), "survivorSpaceUsage");
		
		metricValue2jsProp.put(MetricsType.JVM_PS_MARKSWEEP_COUNT.type(), "markSweepCount");
		metricValue2jsProp.put(MetricsType.JVM_PS_MARKSWEEP_TIME.type(), "markSweepTime");
		metricValue2jsProp.put(MetricsType.JVM_PS_SCAVENGE_COUNT.type(), "scavengeCount");
		metricValue2jsProp.put(MetricsType.JVM_PS_SCAVENGE_TIME.type(), "scavengeTime");
		
		
		
		////
		metricName2jsProp.put(MetricsType.EXEC_FS_LOCAL_LARGEREAD_OPS.name(), "localLROps|LocalFS Large Read Ops");
		metricName2jsProp.put(MetricsType.EXEC_FS_LOCAL_READ_BYTES.name(), "localReadAmount|LocalFS Read Bytes");
		metricName2jsProp.put(MetricsType.EXEC_FS_LOCAL_READ_OPS.name(), "localReadOps|LocalFS Read Ops");
		metricName2jsProp.put(MetricsType.EXEC_FS_LOCAL_WRITE_BYTES.name(), "localWriteAmount|LocalFS Write Bytes");
		metricName2jsProp.put(MetricsType.EXEC_FS_LOCAL_WRITE_OPS.name(), "localWriteAOps|LocalFS Write Ops");
		
		metricName2jsProp.put(MetricsType.EXEC_FS_HDFS_LARGEREAD_OPS.name(), "hdfsLROps|HDFS Large Read Ops");
		metricName2jsProp.put(MetricsType.EXEC_FS_HDFS_READ_BYTES.name(), "hdfsReadAmount|HDFS Read Bytes");
		metricName2jsProp.put(MetricsType.EXEC_FS_HDFS_READ_OPS.name(), "hdfsReadOps|HDFS Read Ops");
		metricName2jsProp.put(MetricsType.EXEC_FS_HDFS_WRITE_BYTES.name(), "hdfsWriteAmount|HDFS Write Bytes");
		metricName2jsProp.put(MetricsType.EXEC_FS_HDFS_WRITE_OPS.name(), "hdfsWriteOps|HDFS Write Ops");
		
		metricName2jsProp.put(MetricsType.EXEC_THREADPOOL_ACTIVETASK.name(), "activeTask|Executor Avtice Task");
		metricName2jsProp.put(MetricsType.EXEC_THREADPOOL_COMPLETETASK.name(), "completeTask|Executor Complete Task");
		metricName2jsProp.put(MetricsType.EXEC_THREADPOOL_CURRPOOL_SIZE.name(), "currPoolSize|Executor Current Pool Size");
		metricName2jsProp.put(MetricsType.EXEC_THREADPOOL_MAXPOOL_SIZE.name(), "maxPoolSize|Executor Max Pool Size");
		
		metricName2jsProp.put(MetricsType.JVM_HEAP_COMMITED.name(), "commitedHeap|JVM Heap Commited");
		metricName2jsProp.put(MetricsType.JVM_HEAP_INIT.name(), "initHeap|JVM Heap Init");
		metricName2jsProp.put(MetricsType.JVM_HEAP_MAX.name(), "maxHeap|JVM Heap Max");
		metricName2jsProp.put(MetricsType.JVM_HEAP_USAGE.name(), "heapUsage|JVM Heap Usage");
		metricName2jsProp.put(MetricsType.JVM_HEAP_USED.name(), "usedHeap|JVM Heap Used");
		
		metricName2jsProp.put(MetricsType.JVM_NON_HEAP_COMMITED.name(), "commitedNonHeap|JVM non Heap Commited");
		metricName2jsProp.put(MetricsType.JVM_NON_HEAP_INIT.name(), "initNonHeap|JVM non Heap Init");
		metricName2jsProp.put(MetricsType.JVM_NON_HEAP_MAX.name(), "maxNonHeap|JVM non Heap Max");
		metricName2jsProp.put(MetricsType.JVM_NON_HEAP_USAGE.name(), "nonHeapUsage|JVM non Heap Usage");
		metricName2jsProp.put(MetricsType.JVM_NON_HEAP_USED.name(), "usedNonHeap|JVM non Heap Used");
		
		metricName2jsProp.put(MetricsType.JVM_POOLS_CODE_CACHE_USAGE.name(), "codeCacheUsage|JVM Pools Code Cache Usage");
		metricName2jsProp.put(MetricsType.JVM_POOLS_PS_EDEN_SPACE_USAGE.name(), "edenSpaceUsage|JVM Pools Eden Space Usage");
		metricName2jsProp.put(MetricsType.JVM_POOLS_PS_OLD_GEN_USAGE.name(), "oldGenUsage|JVM Pools Old Gen Usage");
		metricName2jsProp.put(MetricsType.JVM_POOLS_PS_PERM_GEN_USAGE.name(), "permGenUsage|JVM Pools Perm Gen Usage");
		metricName2jsProp.put(MetricsType.JVM_POOLS_PS_SURVIVOR_SPACE_USAGE.name(), "survivorSpaceUsage|JVM Pools Survivor Space Usage");
		
		metricName2jsProp.put(MetricsType.JVM_PS_MARKSWEEP_COUNT.name(), "markSweepCount|JVM Mark Sweep Count");
		metricName2jsProp.put(MetricsType.JVM_PS_MARKSWEEP_TIME.name(), "markSweepTime|JVM Mark Sweep Time");
		metricName2jsProp.put(MetricsType.JVM_PS_SCAVENGE_COUNT.name(), "scavengeCount|JVm Scavenge Count");
		metricName2jsProp.put(MetricsType.JVM_PS_SCAVENGE_TIME.name(), "scavengeTime|JVM Scavenge Time");
	}
	
	public static String getUIMetricPropertyByMetricValue(String metric){
		String result = null;
		for(String key: metricValue2jsProp.keySet()){
			if(metric.indexOf(key) != -1){
				result = metricValue2jsProp.get(key);
				break;
			}
		}
		return result;
	}
	
	public static String[] getUIMetricPropertyByMetricName(String metric){
		return metricName2jsProp.get(metric).split("\\|");
	}
	
	
}
