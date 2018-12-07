[root@master ~]# cat run_sparkappjar_in_sparkcluster 
#spark submit
/usr/local/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class liyu.test.SimpleApp --master spark://master:7077 ./simple-project-0.0.1-SNAPSHOT.jar
#spark submit yarm 
/usr/local/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class liyu.test.SimpleApp --master yarn ./simple-project-0.0.1-SNAPSHOT.jar

spark rdd some usefull function:
map:transform,for example as sc.parallelize(Array(1,2,3)).map(e=>(e,"v"))
flatMap:n*m extend,for example as sc.parallelize(Array("a b","c d e","f")).flatMap(e=>e.split(" ")).count()
filter:sub,for example as sc.parallelize(Array(1,2,3)).filter(e=>e>1).count()
count:rdd length
collect:change rdd to Array
toList(i:Int):index of Array,for example as sc.parallelize(Array(1,2,3)).collect().toList(2)
reduceBykey:reduce by key,for example as sc.parallelize(Array(("a",2),("a",3)))).reduceByKey((a,b)=>a+b)