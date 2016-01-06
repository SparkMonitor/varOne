**RESTful Document**
===================

# RESTful APIs
varOne give four dimensions for user to monitor spark applications,
* [Cluster](#cluster)
* [Node](#node)
* [Job](#job)
* [History](#history)

The RESTful APIs for each dimension described in the following:

## **Cluster**
### ***&gt; GET /rest/cluster***
To get the all spark applications metrics by cluster.
#### Resource Information
| **Information**  | **Description** |
|---|---|
| Resource URL  | http://localhost:8080/varOne-web/rest/cluster |
| Request Method | GET |
| Path Parameter |  |
| Request Parameter | metrics, period |
| Response format | JSON |
#### Parameters
| **Information**  | **Description** |
|---|---|
| metrics(optional)  | Use **,** to separate each metrics. All of the available metrics at the bottom of this docs |
| period(require) | Assign the time period data of metrics |
## **Node**
### ***&gt; GET /rest/nodes***
To get all node name in cluster, This method use YARN API to get the nodes information.
#### Resource Information
| **Information**  | **Description** |
|---|---|
| Resource URL  | http://localhost:8080/varOne-web/rest/nodes |
| Request Method | GET |
| Path Parameter |  |
| Request Parameter |  |
| Response format | JSON |
### ***&gt; GET /rest/nodes/:node_name***
To get the all spark applications metrics per node.
#### Resource Information
| **Information**  | **Description** |
|---|---|
| Resource URL  | http://localhost:8080/varOne-web/rest/nodes/host-1 |
| Request Method | GET |
| Path Parameter | node_name |
| Request Parameter | metrics, period |
| Response format | JSON |
#### Parameters
| **Information**  | **Description** |
|---|---|
| node_name(require) | Assign a node which you want to monitor |
| metrics(optional)  | Use **,** to separate each metrics. All of the available metrics at the bottom of this docs |
| period(require) | Assign the time period data of metrics |
## **Job**
### ***&gt; GET /rest/job***
Fetch all running spark applications
#### Resource Information
| **Information**  | **Description** |
|---|---|
| Resource URL  | http://localhost:8080/varOne-web/rest/job |
| Request Method | GET |
| Path Parameter |  |
| Request Parameter | |
| Response format | JSON |
### ***&gt; GET /rest/job/:application_id***
Get the metrics of a specified application
#### Resource Information
| **Information**  | **Description** |
|---|---|
| Resource URL  | http://localhost:8080/varOne-web/rest/job/application_1449569227858_0343 |
| Request Method | GET |
| Path Parameter | application_id |
| Request Parameter | metrics, period |
| Response format | JSON |
#### Parameters
| **Information**  | **Description** |
|---|---|
| application_id(require) | Assign a application id which you want to monitor |
| metrics(optional)  | Use **,** to separate each metrics. All of the available metrics at the bottom of this docs |
| period(require) | Assign the time period data of metrics |
## **History**
### ***&gt; GET /rest/history***
Get all of the history applications, this method find these applications based on the records of event logs.
#### Resource Information
| **Information**  | **Description** |
|---|---|
| Resource URL  | http://localhost:8080/varOne-web/rest/history |
| Request Method | GET |
| Path Parameter |  |
| Request Parameter |  |
| Response format | JSON |
### ***&gt; GET /rest/history/:application_id/jobs***
Get all of the jobs of a specified application.
#### Resource Information
| **Information**  | **Description** |
|---|---|
| Resource URL  | http://localhost:8080/varOne-web/rest/history/application_1449569227858_0343/jobs |
| Request Method | GET |
| Path Parameter | application_id |
| Request Parameter |  |
| Response format | JSON |
#### Parameters
| **Information**  | **Description** |
|---|---|
| application_id(require) | Assign a application id which you want to monitor |
### ***&gt; GET /rest/history/:application_id/:job_id/stages***
Get all of the stages of a specified application and job.
#### Resource Information
| **Information**  | **Description** |
|---|---|
| Resource URL  | http://localhost:8080/varOne-web/rest/history/application_1449569227858_0343/0/stages |
| Request Method | GET |
| Path Parameter | application_id, job_id|
| Request Parameter |  |
| Response format | JSON |
#### Parameters
| **Information**  | **Description** |
|---|---|
| application_id(require) | Assign a application id which you want to monitor |
| job_id(require) | Assign a job id which you want to monitor |
### ***&gt; GET /rest/history/:application_id/:job_id/:stage_id***
Get all of the detail analysis of a specified application and job and stage.
#### Resource Information
| **Information**  | **Description** |
|---|---|
| Resource URL  | http://localhost:8080/varOne-web/rest/history/application_1449569227858_0343/0/0 |
| Request Method | GET |
| Path Parameter | application_id, job_id, stage_id|
| Request Parameter |  |
| Response format | JSON |
#### Parameters
| **Information**  | **Description** |
|---|---|
| application_id(require) | Assign a application id which you want to monitor |
| job_id(require) | Assign a job id which you want to monitor |
| stage_id(require) | Assign a stage id which you want to monitor |



# Available metrics
EXEC_FS_LOCAL_LARGEREAD_OPS</br>
EXEC_FS_LOCAL_READ_BYTES</br>
EXEC_FS_LOCAL_READ_OPS</br>
EXEC_FS_LOCAL_WRITE_BYTES</br>
EXEC_FS_LOCAL_WRITE_OPS</br>
</br>
EXEC_FS_HDFS_LARGEREAD_OPS</br>
EXEC_FS_HDFS_READ_BYTES</br>
EXEC_FS_HDFS_READ_OPS</br>
EXEC_FS_HDFS_WRITE_BYTES</br>
EXEC_FS_HDFS_WRITE_OPS</br>
</br>
EXEC_THREADPOOL_ACTIVETASK</br>
EXEC_THREADPOOL_COMPLETETASK</br>
EXEC_THREADPOOL_CURRPOOL_SIZE</br>
EXEC_THREADPOOL_MAXPOOL_SIZE</br>
</br>
JVM_HEAP_COMMITED</br>
JVM_HEAP_INIT</br>
JVM_HEAP_MAX</br>
JVM_HEAP_USAGE</br>
JVM_HEAP_USED</br>
</br>
JVM_NON_HEAP_COMMITED</br>
JVM_NON_HEAP_INIT</br>
JVM_NON_HEAP_MAX</br>
JVM_NON_HEAP_USAGE</br>
JVM_NON_HEAP_USED</br>
</br>
JVM_POOLS_CODE_CACHE_USAGE</br>
JVM_POOLS_PS_EDEN_SPACE_USAGE</br>
JVM_POOLS_PS_OLD_GEN_USAGE</br>
JVM_POOLS_PS_PERM_GEN_USAGE</br>
JVM_POOLS_PS_SURVIVOR_SPACE_USAGE</br>
</br>
JVM_PS_MARKSWEEP_COUNT</br>
JVM_PS_MARKSWEEP_TIME</br>
JVM_PS_SCAVENGE_COUNT</br>
JVM_PS_SCAVENGE_TIME</br>
