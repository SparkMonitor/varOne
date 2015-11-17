export default {
  node:{
    summary: {
      NODE_QTY: "Node Qty",
      RUNNING_JOB: "Running Job Qty",
      RUNNING_EXECUTOR: "Running Executor Qty",
      RUNNING_TASK: "Running Task Qty"
    }
  },

  job:{
    summary: {
      EXECUTOR_QTY: "Executor Qty",
      TASK_QTY: "Total Task Qty",
      COMPLETED_TASK_QTY: "Completed Task Qty",
      FAILED_TASK_QTY: "Failed Task Qty"
    }
  },

  history: {
    tab: {
      HISTORY_TAB: 'history',
      JOB_TAB: 'job',
      STAGE_TAB: 'stage'
    }
  },

  menu: {
    cluster: 'Cluster',
    nodes:   'Nodes',
    runningJobs: 'Running Jobs',
    history: 'History'
  },

  metrics:[
    {name: 'Local file largeRead ops', value: 'EXEC_FS_LOCAL_LARGEREAD_OPS'},
    {name: 'Local file read bytes', value: 'EXEC_FS_LOCAL_READ_BYTES'},
    {name: 'Local file read ops', value: 'EXEC_FS_LOCAL_READ_OPS'},
    {name: 'HDFS file largeRead ops', value: 'EXEC_FS_HDFS_LARGEREAD_OPS'},
    {name: 'HDFS file read bytes', value: 'EXEC_FS_HDFS_READ_BYTES'},
    {name: 'HDFS file read ops', value: 'EXEC_FS_HDFS_READ_OPS'},

    {name: 'Local file write bytes', value: 'EXEC_FS_LOCAL_WRITE_BYTES'},
    {name: 'Local file write bytes', value: 'EXEC_FS_LOCAL_WRITE_OPS'},
    {name: 'HDFS file write bytes', value: 'EXEC_FS_HDFS_WRITE_BYTES'},
    {name: 'HDFS file write bytes', value: 'EXEC_FS_HDFS_WRITE_OPS'},

    {name: 'Thread pool active tasks', value: 'EXEC_THREADPOOL_ACTIVETASK'},
    {name: 'Thread pool complete tasks', value: 'EXEC_THREADPOOL_COMPLETETASK'},
    {name: 'Thread pool current pool size', value: 'EXEC_THREADPOOL_CURRPOOL_SIZE'},
    {name: 'Thread pool max pool size', value: 'EXEC_THREADPOOL_MAXPOOL_SIZE'},


    {name: 'jvm heap commited', value: 'JVM_HEAP_COMMITED'},
    {name: 'jvm heap init', value: 'JVM_HEAP_INIT'},
    {name: 'jvm heap max', value: 'JVM_HEAP_MAX'},
    {name: 'jvm heap usage', value: 'JVM_HEAP_USAGE'},
    {name: 'jvm heap used', value: 'JVM_HEAP_USED'},

    {name: 'jvm non-heap commited', value: 'JVM_NON_HEAP_COMMITED'},
    {name: 'jvm non-heap init', value: 'JVM_NON_HEAP_INIT'},
    {name: 'jvm non-heap max', value: 'JVM_NON_HEAP_MAX'},
    {name: 'jvm non-heap usage', value: 'JVM_NON_HEAP_USAGE'},
    {name: 'jvm non-heap used', value: 'JVM_NON_HEAP_USED'},

    {name: 'jvm Code-Cache usage', value: 'JVM_POOLS_CODE_CACHE_USAGE'},
    {name: 'jvm Eden-Space usage', value: 'JVM_POOLS_PS_EDEN_SPACE_USAGE'},
    {name: 'jvm Old-Gen usage', value: 'JVM_POOLS_PS_OLD_GEN_USAGE'},
    {name: 'jvm Perm-Gen usage', value: 'JVM_POOLS_PS_PERM_GEN_USAGE'},
    {name: 'jvm Survivor-Space usage', value: 'JVM_POOLS_PS_SURVIVOR_SPACE_USAGE'},

    {name: 'jvm GC MarkSweep count', value: 'JVM_PS_MARKSWEEP_COUNT'},
    {name: 'jvm GC MarkSweep time', value: 'JVM_PS_MARKSWEEP_TIME'},
    {name: 'jvm GC Scavenge count', value: 'JVM_PS_SCAVENGE_COUNT'},
    {name: 'jvm GC Scavenge time', value: 'JVM_PS_SCAVENGE_TIME'}
  ]

}
