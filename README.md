# QSARDW Backend

QSARDW is an open source web-based tool that provides a standardized workflow for creating, cleaning, reviewing and sharing a chemical database.
Although it is intended to be used for cleaning datasets for QSAR calculations it really can be used for removing duplicates in every chemical dataset.

The workflow logic is provided by two different elements at the backend a REST API and the Job Scheduler. The API is implemented as a Java web-services 
application and its responsibility is to offer every atomic operation than can be done with a dataset as a remote web service.
The purpose of the Job Scheduler is to perform a batch processing of the datasets that have to be cleaned. For every queued dataset 
the Job Processor takes the dataset from a database, executes the cleaning algorithm and persists the results in the data layer.

## Requirements

The frontend is fully tested with Ubuntu 14.04 LTS with the standard packages. It requires:

* Apache Tomcat 7.x
* OpenJDK 1.7.x
* Mysql 5.5.x
* Maven 3.x

## Installation

### Clone the project
```
sudo git clone https://github.com/qsardw/qsardw-backend.git
```

### Configure the database parameters
Edit the file *mybatis-config.xml* to adjust the database parameters.

```
cd qsardw-backend/datamodel/src/main/resources/mybatis
vim mybatis-config.xml
```

```
...

<dataSource type="POOLED">
    <property name="driver" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/qsardw"/>
    <property name="username" value="your_user_here"/>
    <property name="password" value="your_password_here"/>
</dataSource>

...

```

You should change **url**, **username** and **password**

### Compile the Job Scheduler and the Web Services layer

```
cd qsardw-backend
mvn install
```

After the compilation process you should obtain the following message:

```
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] QSARDW Backend .................................... SUCCESS [1.211s]
[INFO] QSARDW Data Model ................................. SUCCESS [7.952s]
[INFO] QSARDW Backend Daemon ............................. SUCCESS [4.818s]
[INFO] QSARDW REST services Layer ........................ SUCCESS [6.974s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 21.546s
[INFO] Finished at: Fri Jul 24 23:17:08 CEST 2015
[INFO] Final Memory: 27M/289M
[INFO] ------------------------------------------------------------------------

```

### Deploy the war file in your tomcat installation.

The war file is under the folder *rest-services/target/* and is called **qsardw-rest.war**

### Execute the job processor

The jar file is under the folder *daemon/target* and is called **qsardw-job-scheduler.jar** 

```
cd qsardw-backend/daemono/target
java -jar qsardw-job-scheduler.jar
```
