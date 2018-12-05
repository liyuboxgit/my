package liyu.test

import scala.math.random
import org.apache.spark.sql.SparkSession
import java.util.ArrayList
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
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
    
     wordcount
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
}

