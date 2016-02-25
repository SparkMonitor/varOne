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
```

## Import varOne to your IDE(eclipse or intelliJ).

If you import varOne to your IDE, you will see there are four java base project in the root folder:
* **varOne-server** (It's Jetty standalone execution for varOne, here is all of the web server code)
* **varOne-node** (It's varOne daemond)
* **varOne-rpc**  (It's varOne rpc protocol implementation between web and daemond)
* **varOne-web**  (It's varOne web, only include javascript and css codes)

Once, these initializations are finished without error; you can run varOne web server on your locally,   
there are few ways you can choice to develop   

#### IDE
For example, eclipse, you can follow these steps in the below:
* Prepare your ```varOne-site.xml``` and ```varonedaemond``` in ```$VARONE_HOME/conf``` directory
* In eclipse, add ```$VARONE_HOME/conf``` directory to the java build path or classpath of ```varOne-server```
* In eclipse, run ```com.varone.server.VarOneServer``` with following environment variables
  * HADOOP_CONF_DIR
  * SPARK_HOME
* Go to varOne-web/src/main/webapp, and run ```npm run dev```(It will start a webpack dev server on port 3001)
* Open your browser and go to http://localhost:{PORT}/varOne-web/index.html

After these instructions, you should work well on your IDE, actually, you can pass step2 for adding ```$VARONE_HOME/conf```   
to java build path or classpath in ```varOne-server```. If losing ```varOne-site.xml```, varOne server will take default values to run up.   

#### Gradle
You can use gradle and remote debug to run varOne web server:
* Prepare your ```varOne-site.xml``` and ```varonedaemond``` in ```$VARONE_HOME/conf``` directory
* Run: HADOOP_CONF_DIR=path_hadoop_conf_dir SPARK_HOME=path_to_spark gradle execVarOneServer
* Go to varOne-web/src/main/webapp, and run ```npm run dev```(It will start a webpack dev server on port 3001)
* Open your browser and go to http://localhost:{PORT}/varOne-web/index.html

> **Notes:**   
> varOne uses <code>webpack</code> build up a development server for react hot loading, so
> please ensure you have start up webpack dev server before you open varOne web page in browser

**Please remember to start up all varOne daemonds on your cluster, HowTo: [README](https://github.com/SparkMonitor/varOne).**
