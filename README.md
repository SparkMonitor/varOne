**varOne**
===================

[![Join the chat at https://gitter.im/SparkMonitor/varOne](https://badges.gitter.im/SparkMonitor/varOne.svg)](https://gitter.im/SparkMonitor/varOne?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
<img src='http://sparkmonitor.github.io/varOne/images/demo1.png' />

This is an [Apache Spark](http://spark.apache.org/) monitoring tool, named **```varOne```**.

**```varOne```** provides a web UI for you to monitor the metrics of Spark applications more efficiently and easily. varOne ingests the spark **event logs** and **metric** data to summarizes them as rich charts. If you don't want to use this web UI, you can use the [RESTful APIs](/docs/api.md) provided by varOne and custom one by yourself.


# **Usage**

## Prerequisites
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


### a. Download

Click [here](http://sparkmonitor.github.io/varOne/varOne-0.1.0-beta.zip) to download ```varOne-0.1.0-beta```

### b. Start varOne daemond
Deploy the ```varOned-{version}.jar``` to each node in your cluster and start it by following command:
```bash
$ java -jar varOned-{version}.jar -d {SPARK_CONF_PATH} -p {PORT}
```
> **Notes:**   
>    
> - **SPARK_CONF_PATH(required)**: This is the path of **$SPARK_HOME/conf** and make sure ```metrics.properties``` exists in that folder.</br>
> - **PORT(required)**: is the port number for varOne daemond.

### c. Start varOne server
Before starting the varOne server, make sure the following configuration files exist in **$HOME/.varOne/conf**
* yarn-site.xml
* core-site.xml
* hdfs-site.xml
* metrics.properties

Start up the varOne RESTful server by following command:
```bash
$ java -jar varOne-web-{version}.war -p {PORT} -l {LOG_DIR}
```
> **Notes:**   
>     
> - **PORT(optional)**: is the port number for varOne web server, default is 8080.   
> - **LOG_DIR(optional)**: is the directory which placed your custom log4j.properties.


After running, the browser will automatically open and redirect to http://localhost:8080/varOne-web/index.html

If you get the following error during startup, please make sure the following configuration files exist in **$HOME/.varOne/conf**
```java
java.lang.RuntimeException: Please confirm these files core-site.xml,hdfs-site.xml,yarn-site.xml,metrics.properties exist in the /home/user1/.varone/conf
```
If first launch is successful, varOne will ask you to input the port of varOne daemonds which you started before.


# **Development**

## Prerequisites
- JDK 7 and later
- Node.js v4.0.0 and later
- Gradle(for building Java project)

## Brief Introduction 
varOne uses Java to implement the backend service(include RPC, RESTful, etc) and React.js as our frontend solution.   

The varOne architecture is pictured below: 
<img src='http://sparkmonitor.github.io/varOne/images/varOne_arch.png'/>

## Start development
```bash
$ git clone https://github.com/SparkMonitor/varOne.git
$ cd varOne
$ gradle clean eclipse
$ cd varOne-web/src/main/webapp
$ npm install

## Currently, you can import varOne to your IDE(eclipse or intelliJ).
```

If you import varOne to your IDE, you will see there are four java base project in the root folder:
* **varOne-exec** (It's tomcat standalone execution for varOne)
* **varOne-node** (It's varOne daemond)
* **varOne-rpc**  (It's varOne rpc protocol implementation between web and daemond)
* **varOne-web**  (It's varOne web server)

Once, these initializations are finished without error; you can run varOne on your locally   

varOne uses <code>webpack</code> build up a development server for react hot loading, so if you want to run varOne web on local, please follow below steps:
* Start web server in IDE(Deploy varOne-web to tomcat and start up)
* Go to varOne-web/src/main/webapp, and run ```npm run dev```(It will start a webpack dev server on port 3001)
* Open browser, go to http://localhost:8080/varOne-web/index.html

> **Notes:**   
> Before you run varOne web on your local, 
> please make sure the varOne daemonds has been started up on your cluster and 
> make sure your local directory **$HOME/.varOne/conf** contains the following files
> - yarn-site.xml
> - core-site.xml
> - hdfs-site.xml
> - metrics.properties

## Production
Run the following command for varOne in production
```bash
$ cd varOne
$ gradle clean build shadowJar 
```
After,   
The <code>varOne-web-{version}.war</code> will be generated in varOne-web/build/libs   
The <code>varOned-{version}.jar</code> will be generated in varOne-node/build/libs
