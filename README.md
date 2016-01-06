**varOne**
===================


This is an [Apache Spark](http://spark.apache.org/) monitor tools, named **```varOne```**.

**```varOne```** provide a web UI for you to watch the metrics of Spark applications and make you monitor your spark application more efficiency and easy. In fact, **```varOne```** ingest the the spark **event logs** and **metrics** data then summarize these data as rich charts. In addition, **```varOne```** give some useful metric charts for you to monitor. If you don't want to use this web UI, you can use the [RESTful APIs](/docs/api.md) provided by **```varOne```** and custom one by yourself.





Usage
-------------

## Prerequests
- JDK 7 and later
- metrics.properties should enable CsvSink for all instances, you can follow the config in the below:
<div>
*.sink.csv.class=org.apache.spark.metrics.sink.CsvSink</br>
*.sink.csv.period=1</br>
*.sink.csv.unit=seconds</br>
*.sink.csv.directory=/path/to/CSV_SINK</br>
driver.source.jvm.class=org.apache.spark.metrics.source.JvmSource</br>
executor.source.jvm.class=org.apache.spark.metrics.source.JvmSource</br>
</div>

> ```metrics.properties``` exists in **$SPARK_HOME/conf**.


### a. Download
### b. Start varOne daemond
Deploy the ```varOned-{version}.jar``` to each node in your cluster and start it by following command:
```bash
$ java -jar varOned-{version}.jar -d {SPARK_CONF_PATH} -p {PORT}
```

> *SPARK_CONF_PATH*(required) is the path of **$SPARK_HOME/conf** and make sure ```metrics.properties``` exists in that folder.
> *PORT*(required) is the port number for varOne daemond.

### c. Start varOne server
Before start varOne server, make sure the following configuration files exist in **$HOME/.varOne/conf**
* yarn-site.xml
* core-site.xml
* hdfs-site.xml
* metrics.properties

Start up the varOne RESTful server by following command:
```bash
$ java -jar varOne-web-{version}.jar -p {PORT} -l {LOG_DIR}
```
> *PORT*(optional) is the port number for varOne web server, default is 8080.
> *LOG_DIR*(optional) is the directory which placed your custom log4j.properties.


After running, will open the browser automatically and redirect to http://localhost:8080/varOne-web/index.html

If you get the following error when startup, please make sure the following configuration files exist in **$HOME/.varOne/conf**
```java
java.lang.RuntimeException: Please confirm these files core-site.xml,hdfs-site.xml,yarn-site.xml,metrics.properties exist in the /home/user1/.varone/conf
```
If lanuch to varOne web at first time successfully, varOne will ask you to input the port of varOne daemonds which you started before.
