**varOne**
===================
<img src='http://sparkmonitor.github.io/varOne/images/demo1.png' />

This is an [Apache Spark](http://spark.apache.org/) monitor tools, named **```varOne```**.

**```varOne```** provide a web UI for you to watch the metrics of Spark applications and make you monitor your spark application more efficiency and easy. In fact, varOne ingest the the spark **event logs** and **metrics** data then summarize these data as rich charts. In addition, varOne give some useful metric charts for you to monitor. If you don't want to use this web UI, you can use the [RESTful APIs](/docs/api.md) provided by varOne and custom one by yourself.


# **Usage**

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

> **Tip:** ```metrics.properties``` exists in **$SPARK_HOME/conf**.


### a. Download
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
Before start varOne server, make sure the following configuration files exist in **$HOME/.varOne/conf**
* yarn-site.xml
* core-site.xml
* hdfs-site.xml
* metrics.properties

Start up the varOne RESTful server by following command:
```bash
$ java -jar varOne-web-{version}.jar -p {PORT} -l {LOG_DIR}
```
> **Notes:**   
>     
> - **PORT(optional)**: is the port number for varOne web server, default is 8080.   
> - **LOG_DIR(optional)**: is the directory which placed your custom log4j.properties.


After running, will open the browser automatically and redirect to http://localhost:8080/varOne-web/index.html

If you get the following error when startup, please make sure the following configuration files exist in **$HOME/.varOne/conf**
```java
java.lang.RuntimeException: Please confirm these files core-site.xml,hdfs-site.xml,yarn-site.xml,metrics.properties exist in the /home/user1/.varone/conf
```
If lanuch to varOne web at first time successfully, varOne will ask you to input the port of varOne daemonds which you started before.


# **Development**

## Prerequests
- JDK 7 and later
- Node.js v4.0.0 and later
- Gradle(for building Java project)

## Brief Introduction 
varOne use Java to implement the backend service(include RPC, RESTful ..etc) and take React.js as our frontend soultion.   

The varOne architecture is in the below picture: 
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

However, finish these initialization without error, you can run varOne on your local   

varOne use <code>webpack</code> build up a development server for react hot loading, so if you want to run varOne web on local, please follow below steps:
* Start web server in IDE(Deploy varOne-web to tomcat and start up)
* Go to varOne-web/src/main/webapp, and run ```npm run dev```(It will start a webpack dev server on port 3001)
* Open browser, go to http://localhost:8080/varOne-web/index.html

> **Notes:**   
> Like previous said, before you run varOne web on your local, 
> please make sure the varOne daemonds has been startup on your cluster and 
> make sure your local directory **$HOME/.varOne/conf** exists following files
> - yarn-site.xml
> - core-site.xml
> - hdfs-site.xml
> - metrics.properties

## Production
Run the following command for package varOne as production
```bash
$ cd varOne
$ gradle clean build shadowJar 
```
After it, 
The <code>varOne-web-{version}.war</code> generated in varOne-web/build/libs   
The <code>varOned-{version}.jar</code> generated in varOne-node/build/libs
