/**
 * 
 */
package com.haredb.sparkmonitor.node;


/**
 * @author allen
 *
 */
public enum Metric1 {
	EXEC_FS_LOCAL_LARGEREAD_OPS("executor.filesystem.file.largeRead_ops"),
	EXEC_FS_LOCAL_READ_BYTES("executor.filesystem.file.read_bytes"),
	EXEC_FS_LOCAL_READ_OPS("executor.filesystem.file.read_ops"),
	EXEC_FS_LOCAL_WRITE_BYTES("executor.filesystem.file.write_bytes"),
	EXEC_FS_LOCAL_WRITE_OPS("executor.filesystem.file.write_ops"),
	
	
	EXEC_FS_HDFS_LARGEREAD_OPS("executor.filesystem.hdfs.largeRead_ops"),
	EXEC_FS_HDFS_READ_BYTES("executor.filesystem.hdfs.read_bytes"),
	EXEC_FS_HDFS_READ_OPS("executor.filesystem.hdfs.read_ops"),
	EXEC_FS_HDFS_WRITE_BYTES("executor.filesystem.hdfs.write_bytes"),
	EXEC_FS_HDFS_WRITE_OPS("executor.filesystem.hdfs.write_ops"),
	
	
	EXEC_THREADPOOL_ACTIVETASK("executor.threadpool.activeTasks"),
	EXEC_THREADPOOL_COMPLETETASK("executor.threadpool.completeTasks"),
	EXEC_THREADPOOL_CURRPOOL_SIZE("executor.threadpool.currentPool_size"),
	EXEC_THREADPOOL_MAXPOOL_SIZE("executor.threadpool.maxPool_size"),
	
	
	JVM_HEAP_COMMITED("jvm.heap.committed"),
	JVM_HEAP_INIT("jvm.heap.init"),
	JVM_HEAP_MAX("jvm.heap.max"),
	JVM_HEAP_USAGE("jvm.heap.usage"),
	JVM_HEAP_USED("jvm.heap.used"),
	
	
	JVM_NON_HEAP_COMMITED("jvm.non-heap.committed"),
	JVM_NON_HEAP_INIT("jvm.non-heap.init"),
	JVM_NON_HEAP_MAX("jvm.non-heap.max"),
	JVM_NON_HEAP_USAGE("jvm.non-heap.usage"),
	JVM_NON_HEAP_USED("jvm.non-heap.used"),
	
	
	JVM_POOLS_CODE_CACHE_USAGE("jvm.pools.Code-Cache.usage"),
	JVM_POOLS_PS_EDEN_SPACE_USAGE("jvm.pools.PS-Eden-Space.usage"),
	JVM_POOLS_PS_OLD_GEN_USAGE("jvm.pools.PS-Old-Gen.usage"),
	JVM_POOLS_PS_PERM_GEN_USAGE("jvm.pools.PS-Perm-Gen.usage"),
	JVM_POOLS_PS_SURVIVOR_SPACE_USAGE("jvm.pools.PS-Survivor-Space.usage"),
	
	
	JVM_PS_MARKSWEEP_COUNT("jvm.PS-MarkSweep.count"),
	JVM_PS_MARKSWEEP_TIME("jvm.PS-MarkSweep.time"),
	JVM_PS_SCAVENGE_COUNT("jvm.PS-Scavenge.count"),
	JVM_PS_SCAVENGE_TIME("jvm.PS-Scavenge.time");
	
		
	private String name;
	
	Metric1(String name){
		this.name = name;
	}
	
	
	
	
//	
//	public static Metric[] FS_GROUP = new Metric[]{EXEC_FS_LOCAL_LARGEREAD_OPS,
//											EXEC_FS_LOCAL_READ_BYTES, 
//											EXEC_FS_LOCAL_READ_OPS, 
//											EXEC_FS_LOCAL_WRITE_BYTES, 
//											EXEC_FS_LOCAL_WRITE_OPS, 
//											EXEC_FS_HDFS_LARGEREAD_OPS, 
//											EXEC_FS_HDFS_READ_BYTES,
//											EXEC_FS_HDFS_READ_OPS,
//											EXEC_FS_HDFS_WRITE_BYTES,
//											EXEC_FS_HDFS_WRITE_OPS};
//	
//	public static Metric[] THREADPOOL_GROUP = new Metric[]{EXEC_THREADPOOL_ACTIVETASK, 
//											  		EXEC_THREADPOOL_COMPLETETASK,
//											  		EXEC_THREADPOOL_CURRPOOL_SIZE,
//											  		EXEC_THREADPOOL_MAXPOOL_SIZE};
//	
//	public static Metric[] JVM_GROUP = new Metric[]{JVM_HEAP_COMMITED,
//											JVM_HEAP_INIT,
//											JVM_HEAP_MAX,
//											JVM_HEAP_USAGE,
//											JVM_HEAP_USED,
//											JVM_NON_HEAP_COMMITED,
//											JVM_NON_HEAP_INIT,
//											JVM_NON_HEAP_MAX,
//											JVM_NON_HEAP_USAGE,
//											JVM_NON_HEAP_USED,
//											JVM_POOLS_CODE_CACHE_USAGE,
//											JVM_POOLS_PS_EDEN_SPACE_USAGE,
//											JVM_POOLS_PS_OLD_GEN_USAGE,
//											JVM_POOLS_PS_PERM_GEN_USAGE,
//											JVM_POOLS_PS_SURVIVOR_SPACE_USAGE,
//											JVM_PS_MARKSWEEP_COUNT,
//											JVM_PS_MARKSWEEP_TIME,
//											JVM_PS_SCAVENGE_COUNT,
//											JVM_PS_SCAVENGE_TIME};
	
	
}
