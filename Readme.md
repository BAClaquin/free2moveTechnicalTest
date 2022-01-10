# Execution

## Build the project
```bash
mvn clean install
```

## Start the job
```bash
SPARK_MAJOR_VERSION=2 spark-submit --master yarn --conf spark.driver.maxResultSize=4g --queue default\
  --deploy-mode cluster --driver-memory 5g --num-executors 4 --executor-memory 5g --executor-cores 5\  
   free2moveTechnicalTest-1.0-SNAPSHOT.jar 
   application.properties \
   2020-01-01
```
   
## Command line arguments
* ``args[0]`` : filepath of the properties file
* ``args[1]`` : investigation date (format ``yyyy-MM-dd``, default: yesterday)  

# Input Data

## Main data source
* ``orders.csv`` : a list of customer orders on the website.

## Referentials
* ``customer.csv`` : a list of customers.
* ``items.csv`` :  a list of items.
* ``products.csv`` :  a list of available products.

## Configuration
Specify the filepath of these files in the ``application.properties`` file
* ``dir.customer``
* ``dir.orders``
* ``dir.products``
* ``dir.items``

# Output Data

## Summary statistics

This job produces a csv file ``summary_statistics_yyyy_MM_dd.csv`` 

* ``customer_unique_id`` : unique id of the customer
* ``\# of orders made`` : number of orders made by the customer
* ``\# of products ordered`` : number of products ordered by the customer
* ``total amount spent`` : total amount spent by the customer
* ``total freight value`` : total freight value spent by the customer
* ``product categories`` : 
* ``is repeater`` : has the customer already bought something in the past


## Configuration
Specify the filepath of these files in the ``application.properties`` file
* ``dir.output``
