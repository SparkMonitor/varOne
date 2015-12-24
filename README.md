**varOne**
===================


This is an [Apache Spark](http://spark.apache.org/) monitor tools, named **```varOne```**.

**```varOne```** provide a web UI for you to watch the metrics of Spark applications and make you monitor your spark application more efficiency and easy. In fact, **```varOne```** ingest the the spark **event logs** and **metrics** data then summarize these data as rich charts. In addition, **```varOne```** give some useful metric charts for you to monitor. If you don't want to use this web UI, you can use the [RESTful APIs](/docs/api.md) provided by **```varOne```** and custom one by yourself.





Usage
-------------

## Prerequests
- JDK 6 and later
- metrics.properties should enable CsvSink for all instances, you can follow the config in the below:
<div>
*.sink.csv.class=org.apache.spark.metrics.sink.CsvSink</br>
*.sink.csv.period=1</br>
*.sink.csv.unit=seconds</br>
*.sink.csv.directory=/path/to/CSV_SINK</br>
driver.source.jvm.class=org.apache.spark.metrics.source.JvmSource</br>
executor.source.jvm.class=org.apache.spark.metrics.source.JvmSource</br>
</div>


### a. Download
### b. Start varOne daemond
Deploy the ```varOne-node-{version}.jar``` to each node in your cluster and start it by following command:
```bash
$ java -jar varOne-node-0.1.jar -d {SPARK_CONF_PATH} -p {PORT}
```

> *SPARK_CONF_PATH* is the path of **$SPARK_HOME/conf** and make sure ```metrics.properties``` exists in that folder.
> *PORT* is the port number for varOne daemond.

### c. Start varOne server
Before start varOne server, make sure the following configuration files exist in **$HOME/.varOne/conf**
* yarn-site.xml
* core-site.xml
* hdfs-site.xml
* metrics.properties

Start up the varOne RESTful server by following command:
```bash
$ java -jar varOne-web-0.1.jar
```

if no error occurred, open the browser and go to http://localhost:8080/varOne-web
