[root@master ~]# cat run_sparkappjar_in_sparkcluster 
#��׼ģʽ�ύspark
#/usr/local/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class liyu.test.SimpleApp --master spark://master:7077 ./simple-project-0.0.1-SNAPSHOT.jar
#yarn-clieatģʽ�ύspark
/usr/local/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class liyu.test.SimpleApp --master yarn ./simple-project-0.0.1-SNAPSHOT.jar
