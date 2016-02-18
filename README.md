**varOne**
===================

[![Join the chat at https://gitter.im/SparkMonitor/varOne](https://badges.gitter.im/SparkMonitor/varOne.svg)](https://gitter.im/SparkMonitor/varOne?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
<img src='https://camo.githubusercontent.com/f13f65be9c204005ea436187041a7145e70885f8/687474703a2f2f692e696d6775722e636f6d2f6d726f733944762e706e67' />

This is an [Apache Spark](http://spark.apache.org/) web monitoring tool, named **```varOne```**.

**```varOne```** provides a web UI for you to monitor the metrics of Spark applications more efficiently and easily. varOne ingests the spark **event logs** and **metric** data to summarizes them as rich charts. If you don't want to use this web UI, you can use the [RESTful APIs](/docs/api.md) provided by varOne and custom one by yourself.


# **Usage**

## Prerequisites
- Spark on YARN(**We will loose this restriction in near future**)
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

> **Tip:** ```metrics.properties``` exists in **$SPARK_HOME/conf**.

- Enable Spark event log, in $SPARK_HOME/conf/spark-defaults.conf
  * Set ```spark.eventLog.enabled``` to true
  * Give ```spark.eventLog.dir``` to a HDFS folder

> **Tip:** varOne only support eventLog be storaged on HDFS currently, we will loose this restriction in near future.


### a. Download

Click [here](http://sparkmonitor.github.io/varOne/varOne-0.1.0.tgz) to download ```varOne-0.1.0```

### b. Start varOne daemond
Deploy the ```varOne-0.1.0.tgz``` to each node in your cluster and untar all of it   
Then pick one node to start all daemonds by following instructions:
* Configure ```varOne-site.xml``` in ```$VARONE_HOME/conf``` directory
* Configure ```varOne-env.sh``` in ```$VARONE_HOME/conf``` directory
  - Make sure you have set ```SPARK_HOME```
* Configure ```varonedaemond``` in ```$VARONE_HOME/conf``` directory
  - List your each hostname(one host per line)
* Run: ```./bin/varOned-all.sh start```

After running, you can check whether ```VarOned``` process listed by ```jps```   
In addition, you can stop all varOne daemond as this command: ```./bin/varOned-all.sh stop```

### c. Start varOne web server
Follow below steps to start varOne web server:
* Configure ```varOne-site.xml``` in ```$VARONE_HOME/conf``` directory
* Configure ```varOne-env.sh``` in ```$VARONE_HOME/conf``` directory
* Run: ```./bin/varOne.sh```

After running, the browser will automatically open and redirect to http://localhost:{PORT}/varOne-web/index.html

# **Development**
### Check [this](https://github.com/SparkMonitor/varOne/blob/master/docs/development.md) document
