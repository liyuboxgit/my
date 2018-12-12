package liyu.test

import scala.math.random
import org.apache.spark.sql.SparkSession
import java.util.ArrayList
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.storage.StorageLevel


//http://spark.apache.org/docs/latest/api/scala/index.html#package
object Spark {
  def main(args: Array[String]) {
     /*var logFile = "README.md"; 
     var conf = new SparkConf().setAppName("JavaSpark");
     var sc = SparkContext.getOrCreate(conf);
     var data = sc.textFile(logFile).cache();
     
     var a = data.filter(l => l.contains("a")).count();
     var b = data.filter(l => l.contains("b")).count();
     
     sc.parallelize(List("Lines with a: " + a + ", lines with b: " + b)).saveAsTextFile("README");
     
     sc.stop();*/
    
     //wordcount
     wordcount_stream
  }
  
  def wordcount(){
    var arr = "hello,how are you baby!".split(" ");
    var conf = new SparkConf()
       .set("spark.shuffle.consolidateFiles", "true")
       .set("spark.driver.memory","5g");
    var sc = SparkContext.getOrCreate(conf);
    var ret = sc.parallelize(arr).map(el=>(el,1)).reduceByKey((a,b)=>a+b).collect();
    
    ret.foreach(f=>{
      println(f)
    })
  }
  //nc -lk 9999
  def wordcount_stream(){
    val sparkConf = new SparkConf().setAppName("NetworkWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val lines = ssc.socketTextStream("desk", 9999, StorageLevel.MEMORY_AND_DISK_SER)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}

