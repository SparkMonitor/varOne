/**
 * 
 */
package com.varone.web.eventlog.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * @author allen
 *
 */
public class SparkEventLogBean {
	private List<JobStart> jobStart;
	private List<StageSubmit> stageSubmit;
	private AppStart appStart;
	private List<ExecutorAdded> executorAdd;
	private List<TaskStart> taskStart;
	private List<TaskEnd> taskEnd;
	private List<StageCompleted> stageComplete;
	private List<JobEnd> jobEnd;
	private AppEnd appEnd;
	private List<BlockManager> blockManager; 
	
	
	public SparkEventLogBean(){
		this.setExecutorAdd(new ArrayList<ExecutorAdded>());
		this.setTaskStart(new ArrayList<TaskStart>());
		this.setTaskEnd(new ArrayList<TaskEnd>());
		this.setJobStart(new ArrayList<JobStart>());
		this.setJobEnd(new ArrayList<JobEnd>());
		this.setStageSubmit(new ArrayList<StageSubmit>());
		this.setStageComplete(new ArrayList<StageCompleted>());
		this.setBlockManager(new ArrayList<BlockManager>());
	}

	public List<ExecutorAdded> getExecutorAdd() {
		return executorAdd;
	}

	public void setExecutorAdd(List<ExecutorAdded> executorAdd) {
		this.executorAdd = executorAdd;
	}
	
	public void addExecutorAdd(ExecutorAdded executorAdd) {
		this.executorAdd.add(executorAdd);
	}

	public List<TaskStart> getTaskStart() {
		return taskStart;
	}

	public void setTaskStart(List<TaskStart> taskStart) {
		this.taskStart = taskStart;
	}
	
	public void addTaskStart(TaskStart taskStart) {
		this.taskStart.add(taskStart);
	}

	public List<TaskEnd> getTaskEnd() {
		return taskEnd;
	}

	public void setTaskEnd(List<TaskEnd> taskEnd) {
		this.taskEnd = taskEnd;
	}
	
	public void addTaskEnd(TaskEnd taskEnd) {
		this.taskEnd.add(taskEnd);
	}

	public List<JobStart> getJobStart() {
		return this.jobStart;
	}

	public void setJobStart(List<JobStart> jobStart) {
		this.jobStart = jobStart;
	}
	
	public void addJobStart(JobStart jobStart) {
		this.jobStart.add(jobStart);
	}

	public AppStart getAppStart() {
		return appStart;
	}

	public void setAppStart(AppStart appStart) {
		this.appStart = appStart;
	}

	public AppEnd getAppEnd() {
		return appEnd;
	}

	public void setAppEnd(AppEnd appEnd) {
		this.appEnd = appEnd;
	}

	public void addJobEnd(JobEnd jobEnd){
		this.jobEnd.add(jobEnd);
	}

	public List<JobEnd> getJobEnd() {
		return jobEnd;
	}

	public void setJobEnd(List<JobEnd> jobEnd) {
		this.jobEnd = jobEnd;
	}
	
	public void addStageSubmit(StageSubmit stageSubmit){
		this.stageSubmit.add(stageSubmit);
	}

	public List<StageSubmit> getStageSubmit() {
		return stageSubmit;
	}

	public void setStageSubmit(List<StageSubmit> stageSubmit) {
		this.stageSubmit = stageSubmit;
	}

	public void addStageComplete(StageCompleted stageComplete){
		this.stageComplete.add(stageComplete);
	}
	
	public List<StageCompleted> getStageComplete() {
		return stageComplete;
	}

	public void setStageComplete(List<StageCompleted> stageComplete) {
		this.stageComplete = stageComplete;
	}

	public void addBlocKManager(BlockManager blockManager){
		this.blockManager.add(blockManager);
	}

	public void setBlockManager(List<BlockManager> blockManager){
		this.blockManager = blockManager;
	}
	
	public List<BlockManager> getBlockManager(){
		return this.blockManager;
	}
	
	public class StageInfos{ 
		@SerializedName("Stage ID")private int id;
		@SerializedName("Stage Name")private String name;
		@SerializedName("Number of Tasks")private int taskNum;
		@SerializedName("Parent IDs")private List<String> parentIds;
		@SerializedName("Submission Time")private long submitTime;
		@SerializedName("Completion Time")private long completeTime;
		@SerializedName("Failure Reason")private String failureReason;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getTaskNum() {
			return taskNum;
		}
		public void setTaskNum(int taskNum) {
			this.taskNum = taskNum;
		}
		public List<String> getParentIds() {
			return parentIds;
		}
		public void setParentIds(List<String> parentIds) {
			this.parentIds = parentIds;
		}
		public long getSubmitTime() {
			return submitTime;
		}
		public void setSubmitTime(long submitTime) {
			this.submitTime = submitTime;
		}
		public long getCompleteTime() {
			return completeTime;
		}
		public void setCompleteTime(long completeTime) {
			this.completeTime = completeTime;
		}
		public String getFailureReason() {
			return failureReason;
		}
		public void setFailureReason(String failureReason) {
			this.failureReason = failureReason;
		}
	}


	//{"Event":"SparkListenerApplicationStart","App Name":"HBase_Query_with_RDD","App ID":"application_1444612850687_0240","Timestamp":1446000188003,"User":"user1"}
	public class AppStart{
		@SerializedName("App Name")private String name;
		@SerializedName("App ID")private String id;
		@SerializedName("Timestamp")private long timestamp;
		@SerializedName("User")private String user;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
	}

	
	public class StageSubmit{
		@SerializedName("Stage Info")private StageInfos stageInfo;

		public StageInfos getStageInfo() {
			return stageInfo;
		}

		public void setStageInfo(StageInfos stageInfo) {
			this.stageInfo = stageInfo;
		}
	}
	
	
	//{"Event":"SparkListenerExecutorAdded","Timestamp":1440399206528,"Executor ID":"1","Executor Info":{"Host":"server-a5","Total Cores":1,"Log Urls":{"stdout":"http://server-a5:8042/node/containerlogs/container_1439169262151_0181_01_000002/user1/stdout?start=0","stderr":"http://server-a5:8042/node/containerlogs/container_1439169262151_0181_01_000002/user1/stderr?start=0"}}}
	public class ExecutorAdded{
		public class ExecutorInfo{
			@SerializedName("Host")private String host;
			@SerializedName("Total Cores")private String cores;
			public String getHost() {
				return host;
			}
			public void setHost(String host) {
				this.host = host;
			}
			public String getCores() {
				return cores;
			}
			public void setCores(String cores) {
				this.cores = cores;
			}
		}
		
		@SerializedName("Timestamp")private String time;
		@SerializedName("Executor ID")private String id;
		@SerializedName("Executor Info")private ExecutorInfo info;
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public ExecutorInfo getInfo() {
			return info;
		}
		public void setInfo(ExecutorInfo info) {
			this.info = info;
		}
	}
	
	//{"Event":"SparkListenerTaskStart","Stage ID":0,"Stage Attempt ID":0,"Task Info":{"Task ID":0,"Index":2,"Attempt":0,"Launch Time":1440399209585,"Executor ID":"1","Host":"server-a5","Locality":"NODE_LOCAL","Speculative":false,"Getting Result Time":0,"Finish Time":0,"Failed":false,"Accumulables":[]}}
	public class TaskStart{
		public class TaskInfo{
			@SerializedName("Task ID")private int id;
			@SerializedName("Index")private int index;
			@SerializedName("Attempt")private int attempt;
			@SerializedName("Launch Time")private long lanuchTime;
			@SerializedName("Executor ID")private String executorId;
			@SerializedName("Host")private String host;
			@SerializedName("Locality")private String locality;
			@SerializedName("Speculative")private boolean speculative;
			@SerializedName("Failed")private boolean failed;
			public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			public int getIndex() {
				return index;
			}
			public void setIndex(int index) {
				this.index = index;
			}
			public int getAttempt() {
				return attempt;
			}
			public void setAttempt(int attempt) {
				this.attempt = attempt;
			}
			public long getLanuchTime() {
				return lanuchTime;
			}
			public void setLanuchTime(long lanuchTime) {
				this.lanuchTime = lanuchTime;
			}
			public String getExecutorId() {
				return executorId;
			}
			public void setExecutorId(String executorId) {
				this.executorId = executorId;
			}
			public String getHost() {
				return host;
			}
			public void setHost(String host) {
				this.host = host;
			}
			public String getLocality() {
				return locality;
			}
			public void setLocality(String locality) {
				this.locality = locality;
			}
			public boolean isSpeculative() {
				return speculative;
			}
			public void setSpeculative(boolean speculative) {
				this.speculative = speculative;
			}
			public boolean isFailed() {
				return failed;
			}
			public void setFailed(boolean failed) {
				this.failed = failed;
			}
		}
		
		@SerializedName("Stage ID")private int stageId;
		@SerializedName("Stage Attempt ID")private int stageAttemptId;
		@SerializedName("Task Info")private TaskInfo info;
		public int getStageId() {
			return stageId;
		}
		public void setStageId(int stageId) {
			this.stageId = stageId;
		}
		public int getStageAttemptId() {
			return stageAttemptId;
		}
		public void setStageAttemptId(int stageAttemptId) {
			this.stageAttemptId = stageAttemptId;
		}
		public TaskInfo getInfo() {
			return info;
		}
		public void setInfo(TaskInfo info) {
			this.info = info;
		}
	}
	
	//{"Event":"SparkListenerTaskEnd","Stage ID":0,"Stage Attempt ID":0,"Task Type":"ResultTask","Task End Reason":{"Reason":"Success"},
	//"Task Info":{"Task ID":0,"Index":2,"Attempt":0,"Launch Time":1440399209585,"Executor ID":"1","Host":"server-a5","Locality":"NODE_LOCAL","Speculative":false,"Getting Result Time":0,"Finish Time":1440399212318,"Failed":false,"Accumulables":[]},
		//"Task Metrics":{"Host Name":"server-a5","Executor Deserialize Time":644,"Executor Run Time":2011,"Result Size":1830,"JVM GC Time":69,"Result Serialization Time":1,"Memory Bytes Spilled":0,"Disk Bytes Spilled":0,
			//"Input Metrics":{"Data Read Method":"Hadoop","Bytes Read":134217728,"Records Read":834225}}}
	public class TaskEnd{
		public class Reason{
			@SerializedName("Reason")private String reason;
			public String getReason() {
				return reason;
			}
			public void setReason(String reason) {
				this.reason = reason;
			}
		}
		public class TaskInfo{
			@SerializedName("Task ID")private int id;
			@SerializedName("Index")private int index;
			@SerializedName("Attempt")private int attempt;
			@SerializedName("Launch Time")private long lanuchTime;
			@SerializedName("Executor ID")private String executorId;
			@SerializedName("Host")private String host;
			@SerializedName("Locality")private String locality;
			@SerializedName("Speculative")private boolean speculative;
			@SerializedName("Failed")private boolean failed;
			@SerializedName("Getting Result Time")private long resultTime;
			@SerializedName("Finish Time")private long finishTime;
			public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			public int getIndex() {
				return index;
			}
			public void setIndex(int index) {
				this.index = index;
			}
			public int getAttempt() {
				return attempt;
			}
			public void setAttempt(int attempt) {
				this.attempt = attempt;
			}
			public long getLanuchTime() {
				return lanuchTime;
			}
			public void setLanuchTime(long lanuchTime) {
				this.lanuchTime = lanuchTime;
			}
			public String getExecutorId() {
				return executorId;
			}
			public void setExecutorId(String executorId) {
				this.executorId = executorId;
			}
			public String getHost() {
				return host;
			}
			public void setHost(String host) {
				this.host = host;
			}
			public String getLocality() {
				return locality;
			}
			public void setLocality(String locality) {
				this.locality = locality;
			}
			public boolean isSpeculative() {
				return speculative;
			}
			public void setSpeculative(boolean speculative) {
				this.speculative = speculative;
			}
			public boolean isFailed() {
				return failed;
			}
			public void setFailed(boolean failed) {
				this.failed = failed;
			}
			public long getResultTime() {
				return resultTime;
			}
			public void setResultTime(long resultTime) {
				this.resultTime = resultTime;
			}
			public long getFinishTime() {
				return finishTime;
			}
			public void setFinishTime(long finishTime) {
				this.finishTime = finishTime;
			}
		}
		public class TaskMetrics{
			public class InputMetrics{
				@SerializedName("Data Read Method")private String readMethod;
				@SerializedName("Bytes Read")private long readByte;
				@SerializedName("Records Read")private long recordRead;
				public String getReadMethod() {
					return readMethod;
				}
				public void setReadMethod(String readMethod) {
					this.readMethod = readMethod;
				}
				public long getReadByte() {
					return readByte;
				}
				public void setReadByte(long readByte) {
					this.readByte = readByte;
				}
				public long getRecordRead() {
					return recordRead;
				}
				public void setRecordRead(long recordRead) {
					this.recordRead = recordRead;
				}
			}
			public class OutputMetrics{
				@SerializedName("Data Write Method")private String writeMethod;
				@SerializedName("Bytes Written")private long writeByte;
				@SerializedName("Records Written")private long recordWrite;
				public String getWriteMethod() {
					return writeMethod;
				}
				public void setWriteMethod(String writeMethod) {
					this.writeMethod = writeMethod;
				}
				public long getWriteByte() {
					return writeByte;
				}
				public void setWriteByte(long writeByte) {
					this.writeByte = writeByte;
				}
				public long getRecordWrite() {
					return recordWrite;
				}
				public void setRecordWrite(long recordWrite) {
					this.recordWrite = recordWrite;
				}
			}
			@SerializedName("Host Name")private String host;
			@SerializedName("Executor Deserialize Time")private long dserTime;
			@SerializedName("Executor Run Time")private long runTime;
			@SerializedName("Result Size")private long resultSize;
			@SerializedName("JVM GC Time")private long gcTime;
			@SerializedName("Result Serialization Time")private long serTime;
			@SerializedName("Input Metrics")private InputMetrics inputMetrics;
			@SerializedName("Output Metrics")private OutputMetrics outputMetrics;
			public String getHost() {
				return host;
			}
			public void setHost(String host) {
				this.host = host;
			}
			public long getDserTime() {
				return dserTime;
			}
			public void setDserTime(long dserTime) {
				this.dserTime = dserTime;
			}
			public long getRunTime() {
				return runTime;
			}
			public void setRunTime(long runTime) {
				this.runTime = runTime;
			}
			public long getResultSize() {
				return resultSize;
			}
			public void setResultSize(long resultSize) {
				this.resultSize = resultSize;
			}
			public long getGcTime() {
				return gcTime;
			}
			public void setGcTime(long gcTime) {
				this.gcTime = gcTime;
			}
			public long getSerTime() {
				return serTime;
			}
			public void setSerTime(long serTime) {
				this.serTime = serTime;
			}
			public InputMetrics getInputMetrics() {
				return inputMetrics;
			}
			public void setInputMetrics(InputMetrics inputMetrics) {
				this.inputMetrics = inputMetrics;
			}
			public OutputMetrics getOutputMetrics() {
				return outputMetrics;
			}
			public void setOutputMetrics(OutputMetrics outputMetrics) {
				this.outputMetrics = outputMetrics;
			}
		}
		
		@SerializedName("Stage ID")private int stageId;
		@SerializedName("Stage Attempt ID")private int stageAttemptId;
		@SerializedName("Task Type")private String type;
		@SerializedName("Task End Reason")private Reason reason;
		@SerializedName("Task Info")private TaskInfo info;
		@SerializedName("Task Metrics")private TaskMetrics metrics;
		public int getStageId() {
			return stageId;
		}
		public void setStageId(int stageId) {
			this.stageId = stageId;
		}
		public int getStageAttemptId() {
			return stageAttemptId;
		}
		public void setStageAttemptId(int stageAttemptId) {
			this.stageAttemptId = stageAttemptId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Reason getReason() {
			return reason;
		}
		public void setReason(Reason reason) {
			this.reason = reason;
		}
		public TaskInfo getInfo() {
			return info;
		}
		public void setInfo(TaskInfo info) {
			this.info = info;
		}
		public TaskMetrics getMetrics() {
			return metrics;
		}
		public void setMetrics(TaskMetrics metrics) {
			this.metrics = metrics;
		}
	}
	
	public class JobStart{
				
		@SerializedName("Job ID")private int jobId;
		@SerializedName("Submission Time")private long submitTime;
		@SerializedName("Stage Infos") private List<StageInfos> stageInfos;
		public int getJobId() {
			return jobId;
		}
		public void setJobId(int jobId) {
			this.jobId = jobId;
		}
		public long getSubmitTime() {
			return submitTime;
		}
		public void setSubmitTime(long submitTime) {
			this.submitTime = submitTime;
		}
		public List<StageInfos> getStageInfos() {
			return stageInfos;
		}
		public void setStageInfos(List<StageInfos> stageInfos) {
			this.stageInfos = stageInfos;
		}
	}
	
//	{"Event":"SparkListenerStageCompleted","Stage Info":{"Stage ID":0,"Stage Attempt ID":0,"Stage Name":"count at HBaseQueryWithRDD.scala:39",
//	"Number of Tasks":600,"RDD Info":[{"RDD ID":1,"Name":"MapPartitionsRDD","Scope":"{\"id\":\"0\",\"name\":\"textFile\"}","Parent IDs":[0],
//	"Storage Level":{"Use Disk":false,"Use Memory":false,"Use ExternalBlockStore":false,"Deserialized":false,"Replication":1},
//	"Number of Partitions":600,"Number of Cached Partitions":0,"Memory Size":0,"ExternalBlockStore Size":0,"Disk Size":0},
//	{"RDD ID":0,"Name":"hdfs://server-a1:9000/longKey5e.txt","Scope":"{\"id\":\"0\",\"name\":\"textFile\"}","Parent IDs":[],
//	"Storage Level":{"Use Disk":false,"Use Memory":false,"Use ExternalBlockStore":false,"Deserialized":false,"Replication":1},
//	"Number of Partitions":600,"Number of Cached Partitions":0,"Memory Size":0,"ExternalBlockStore Size":0,"Disk Size":0}],
//	"Parent IDs":[],"Details":"org.apache.spark.rdd.RDD.count(RDD.scala:1095)\ncom.hbase.HBaseQueryWithRDD$.main(HBaseQueryWithRDD.scala:39)\ncom.hbase.HBaseQueryWithRDD.main(HBaseQueryWithRDD.scala)\nsun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\nsun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)\nsun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\njava.lang.reflect.Method.invoke(Method.java:606)\norg.apache.spark.deploy.SparkSubmit$.org$apache$spark$deploy$SparkSubmit$$runMain(SparkSubmit.scala:664)\norg.apache.spark.deploy.SparkSubmit$.doRunMain$1(SparkSubmit.scala:169)\norg.apache.spark.deploy.SparkSubmit$.submit(SparkSubmit.scala:192)\norg.apache.spark.deploy.SparkSubmit$.main(SparkSubmit.scala:111)\norg.apache.spark.deploy.SparkSubmit.main(SparkSubmit.scala)",
//	"Submission Time":1446000221538,"Completion Time":1446000363639,"Accumulables":[]}}
	public class StageCompleted{
		@SerializedName("Stage Info")private StageInfos stageInfo;

		public StageInfos getStageInfo() {
			return stageInfo;
		}

		public void setStageInfo(StageInfos stageInfo) {
			this.stageInfo = stageInfo;
		}
	}
	
	//{"Event":"SparkListenerJobEnd","Job ID":0,"Completion Time":1446000363648,"Job Result":{"Result":"JobSucceeded"}}
	public class JobEnd{
		public class JobResult{
			@SerializedName("Result")private String result;
			public String getResult() {
				return result;
			}

			public void setResult(String result) {
				this.result = result;
			}
		}
		
		@SerializedName("Job ID")private int id;
		@SerializedName("Completion Time")private long completeTime;
		@SerializedName("Job Result")private JobResult jobResult;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public long getCompleteTime() {
			return completeTime;
		}
		public void setCompleteTime(long completeTime) {
			this.completeTime = completeTime;
		}
		public JobResult getJobResult() {
			return jobResult;
		}
		public void setJobResult(JobResult jobResult) {
			this.jobResult = jobResult;
		}
	}
		
	//{"Event":"SparkListenerApplicationEnd","Timestamp":1446000363655}
	public class AppEnd{
		@SerializedName("Timestamp")private long timestamp;

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		
	}
	//{"Event":"SparkListenerBlockManagerAdded","Block Manager ID":{"Executor ID":"1","Host":"server-a2","Port":34086},"Maximum Memory":556038881,"Timestamp":1450686022248}
	public class BlockManager{
		public class BlockManagerID{
			@SerializedName("Executor ID")private String id;
			@SerializedName("Host")private String host;
			@SerializedName("Port")private int port;
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getHost() {
				return host;
			}
			public void setHost(String host) {
				this.host = host;
			}
			public int getPort() {
				return port;
			}
			public void setPort(int port) {
				this.port = port;
			}
		}
		@SerializedName("Block Manager ID")private BlockManagerID blockManagerID;
		@SerializedName("Maximum Memory")private long maxMemory;
		@SerializedName("Timestamp")private long timestamp;
		public long getMaxMemory() {
			return maxMemory;
		}
		public void setMaxMemory(long maxMemory) {
			this.maxMemory = maxMemory;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		public BlockManagerID getBlockManagerID() {
			return blockManagerID;
		}
		public void setBlockManagerID(BlockManagerID blockManagerID) {
			this.blockManagerID = blockManagerID;
		}
	}
	
	
	
}
