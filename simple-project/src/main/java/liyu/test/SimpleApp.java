package liyu.test;

import org.apache.spark.api.java.*;

import java.util.ArrayList;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
public class SimpleApp {
	public static void main(String[] args) {
	    String logFile = "/usr/local/spark-2.1.0-bin-hadoop2.7/README.md"; // Should be some file on your system
	    SparkConf conf = new SparkConf().setAppName("Simple Application");
	    JavaSparkContext sc = new JavaSparkContext(conf);
	    JavaRDD<String> logData = sc.textFile(logFile).cache();

	    long numAs = logData.filter(new Function<String, Boolean>() {
	      public Boolean call(String s) { return s.contains("a"); }
	    }).count();

	    long numBs = logData.filter(new Function<String, Boolean>() {
	      public Boolean call(String s) { return s.contains("b"); }
	    }).count();
	    
	    ArrayList<String> list = new ArrayList<String>();
	    list.add("Lines with a: " + numAs + ", lines with b: " + numBs);
	    sc.parallelize(list).saveAsTextFile("/usr/local/spark-2.1.0-bin-hadoop2.7/README");
	    
	    System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
	    sc.stop();
	}
	
	/*
	package liyu.test

	import scala.math.random
	import org.apache.spark.sql.SparkSession
	import java.util.ArrayList
	import org.apache.spark.SparkContext
	import org.apache.spark.SparkConf

	object Spark {
	  def main(args: Array[String]) {
		 var logFile = "README.md"; 
		 var conf = new SparkConf().setAppName("JavaSpark");
		 var sc = SparkContext.getOrCreate(conf);
		 var data = sc.textFile(logFile).cache();
		 
		 var a = data.filter(l => l.contains("a")).count();
		 var b = data.filter(l => l.contains("b")).count();
		 
		 sc.parallelize(List("Lines with a: " + a + ", lines with b: " + b)).saveAsTextFile("README");
		 
		 sc.stop();
	  }
	}
	*/
	  
}
