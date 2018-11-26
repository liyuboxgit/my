[root@master ~]# cat run_sparkappjar_in_sparkcluster 
#标准模式提交spark
#/usr/local/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class liyu.test.SimpleApp --master spark://master:7077 ./simple-project-0.0.1-SNAPSHOT.jar
#yarn-clieat模式提交spark
/usr/local/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class liyu.test.SimpleApp --master yarn ./simple-project-0.0.1-SNAPSHOT.jar
