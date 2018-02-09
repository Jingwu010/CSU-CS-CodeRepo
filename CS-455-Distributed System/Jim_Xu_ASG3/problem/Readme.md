# **Author : Jim Xu**

#### 0. ReadMe

​	The code provides a general idea of how the mapreduce works.

​	[helpful link](http://stackoverflow.com/questions/17218412/hadoop-produce-multiple-values-for-a-single-key#answer-17219250)

#### 1. Setup

0. **SSH mechine**

   ~~~~shell
   ssh jimx@dover.cs.colostate.edu
   ~~~~

1. **Start dfs**

   ~~~~shell
   $HADOOP_HOME/sbin/start-dfs.sh
   ~~~~


2. **Start yarn**

   ~~~~shell
   $HADOOP_HOME/sbin/start-yarn.sh
   ~~~~

3. **Stop yarn**

   ~~~~shell
   $HADOOP_HOME/sbin/stop-yarn.sh
   ~~~~

4. **Stop dfs**

   ~~~~shell
   $HADOOP_HOME/sbin/stop-dfs.sh
   ~~~~

5. **List-status**

   ~~~~shell
   $HADOOP_HOME/bin/hadoop dfsadmin -report
   ~~~~

#### 2. Deploy the File

1. **Make dir**

   ~~~~shell
   $HADOOP_HOME/bin/hdfs dfs -mkdir /cs455
   $HADOOP_HOME/bin/hdfs dfs -mkdir /cs455/dataset
   ~~~~

2. **Copy File to dir**

   ~~~~shell
   $HADOOP_HOME/bin/hdfs dfs -put * /cs455/dataset
   $HADOOP_HOME/bin/hdfs dfs -put * /data/census
   ~~~~

3. **Copy Result to dir**

   ~~~~shell
   $HADOOP_HOME/bin/hadoop dfs -copyToLocal /cs455/dataset-out/problem/firstOut/part-r-00000 ~/hw3/problem/text.txt
   ~~~~
   ~~~~shell
   $HADOOP_HOME/bin/hadoop dfs -copyToLocal /home/output-1/firstOut/part-r-00000 ~/hw3/problem/text.txt
   ~~~~

   ~~~~shell
   $HADOOP_HOME/bin/hadoop dfs -copyToLocal /home/output-1/secondOut/part-r-00000 ~/hw3/problem/text2.txt
   ~~~~

4. **List File in dir**

   ~~~~shell
   $HADOOP_HOME/bin/hdfs dfs -ls /cs455/dataset/
   ~~~~
   ~~~~shell
   $HADOOP_HOME/bin/hdfs dfs -ls /cs455/dataset-out/
   ~~~~

5. **Delete Output File**

   ~~~~shell
   $HADOOP_HOME/bin/hadoop fs -rm -r /cs455/dataset-out/problem
   ~~~~

   ~~~~shell
   $HADOOP_HOME/bin/hadoop fs -rm -r /home/output-1
   ~~~~

#### 3. Run the MapReduce

1.  **Run the local map-reduce:**

    ~~~~shell
    $HADOOP_HOME/bin/hadoop jar dist/dataAnalysis.jar cs455.hadoop.dataAnalysis.DataAnalysisJob /cs455/dataset /cs455/dataset-out/problem
    ~~~~

2. **Run the shared map-reduce **

    ~~~~shell
    $HADOOP_HOME/bin/hadoop jar dist/dataAnalysis.jar cs455.hadoop.dataAnalysis.DataAnalysisJob /data/census /home/output-1
    ~~~~


----

#### 4. Problems

Q1. On a per-state basis provide a breakdown of the percentage of residences that were rented vs. owned.



Q2. On a per-state basis what percentage of the population never married? Report this for both males and females. Note: The US Census data tracks this information for persons with ages 15 years and over.



Q3. On a per-state basis, analyze the age distribution (of the population that identifies themselves as Hispanic) based on gender.

(a).Percentage of people below 18 years (inclusive) old.

(b).Percentage of people between 19 (inclusive) and 29 (inclusive) years old. 

(c). Percentage of people between 30 (inclusive) and 39 (inclusive) years old.



Q4. On a per-state basis, analyze the distribution of rural households vs. urban households.



Q5. On a per-state basis, what is the median value of the house that occupied by owners?



Q6. On a per-state basis, what is median rent paid by households?



Q7. What is the 95th percentile of the average number of rooms per house across all states?



Q8. Which state has the highest percentage of elderly people (age > 85) in their population?



Q9.  Which is the majority race (except White) in each state?