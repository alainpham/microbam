# MICROBAM 
# JBoss empowered Microservices for SLA management and Business Activity Monitoring

This is an example of microservices for collecting/correlating business events with JBoss Fuse, JBoss Data Grid and soon to come JBoss Data Virtualization .
It includes a real time dashboard using Patternfly to follow metrics and Service Level Agreements.

Hopefully, it will give you an idea of what real time monitoring with "the microservice way" can bring to your Digital Transformation program.

## Project descriptions

![overview](https://raw.githubusercontent.com/alainpham/microbam/master/architectureSchema.png)

##### event-collector

Starts the following resources (note that these could have been externalized but to simplify the demo installation, it is embedded): 
- Starts a h2 database to store individual events
- Starts up a JBoss Data Grid to store and calculate statistics in realtime 

What this module does : 
- Collects events and triggers statistical calculations
- Results are stored and updated in real time on a JBoss Data Grid (Infinispan)

##### trade-service

This is an example of Business Indicator that need to be tracked and analyzed in Real Time. Here we will be tracking the processing time of trade orders.

What this module does : 
- Receives business events (JSON messages)
- Stores the event for deeper analysis and drill down
- Maps data to a generic format
- Send the generic format to the event-collector

##### dashapp

This is the dashboard that shows metrics in realtime and allows to analyze stored events

##### generic-model

Java classes that represent the generic model consumed by the event-collector to calculate statistics

##### trade-model

Example of model that extends the generic model to add business related data to the events.


This is the dashboard that shows metrics in realtime and allows to analyze stored events


## To run the examples

1. compile and package 
```
mvn install
```
2. run apps with hawt-app packaging
```
./runApp.sh
```
3. open browser at 
```
http://localhost:8888
```
4. simulate events
```
./play.sh
```
5. stop events
```
./pause.sh
```
6. stop applications
```
./stopApp.sh
```


## Deploy on Openshift  : 
1. go into folder openshift of this project
```
cd openshift
```
2. create fis image streams if needed
```
oc create -f fis-image-streams.json
```

3. create configMap to change app.properties
```
oc create configmap --from-file=app.properties sla-solution-config
```
4. create application template. 
```
oc create -f sla-solution.yaml
```
5. login to openshift and deploy the application template

/!\ Change maven MAVEN_ARGS : for faster builds it is recommended that you setup a nexus2 locally to avoid downloading maven artifacts each time 
change the value http://172.17.0.1:8081/nexus/content/groups/public/ to any local nexus instance that is configured to be mirror of central and fuse repositories
Otherwise if you don't want to use this option change the maven settings.xml file in the root folder of this project.

6. to run the simulation simply acces the url 
```
http://dashboardURL/sim/sim/20/5
```

![overview](https://raw.githubusercontent.com/alainpham/microbam/master/screenshot.png)



## Install on Fuse Karaf : 
```
features:install camel-infinispan jdbc camel-sql camel-netty4-http camel-jackson camel-jsonpath
osgi:install -s mvn:com.h2database/h2/1.4.193
osgi:install -s mvn:org.apache.commons/commons-math3/3.6.1
osgi:install -s mvn:microbam/generic-model/CURRENT_VERSION
osgi:install -s mvn:microbam/trade-model/CURRENT_VERSION
osgi:install -s mvn:microbam/event-collector/CURRENT_VERSION
osgi:install -s mvn:microbam/trade-service/CURRENT_VERSION
osgi:install -s mvn:microbam/dashapp/CURRENT_VERSION
```
