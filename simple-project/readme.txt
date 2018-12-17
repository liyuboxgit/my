[root@master ~]# cat run_sparkappjar_in_sparkcluster 
#spark submit
/usr/local/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class liyu.test.SimpleApp --master spark://master:7077 ./simple-project-0.0.1-SNAPSHOT.jar
#spark submit yarm 
/usr/local/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class liyu.test.SimpleApp --master yarn ./simple-project-0.0.1-SNAPSHOT.jar

SPARK RDD SOME USEFULL FUNCTION:
map:transform,for example as sc.parallelize(Array(1,2,3)).map(e=>(e,"v"))
flatMap:n*m extend,for example as sc.parallelize(Array("a b","c d e","f")).flatMap(e=>e.split(" ")).count()
filter:sub,for example as sc.parallelize(Array(1,2,3)).filter(e=>e>1).count()
count:rdd length
collect:change rdd to Array
toList(i:Int):index of Array,for example as sc.parallelize(Array(1,2,3)).collect().toList(2)
reduceBykey:reduce by key,for example as sc.parallelize(Array(("a",2),("a",3)))).reduceByKey((a,b)=>a+b)

PYTHON SPARK:
spark.read.text("text").rdd.map(lambda x:x[0]).map(lambda x:len(x.split(" "))).reduce(max)
scala:spark.read.textFile("text").map(line => line.split(" ").size).reduce((a,b) => java.lang.Math.max(a,b))

spark.read.text("text").rdd.map(lambda x:x[0]).flatMap(lambda x:x.split(" ")).filter(lambda x:x.find("f")>-1).count()