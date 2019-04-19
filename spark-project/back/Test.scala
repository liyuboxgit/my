package liyu.test

import java.util.Date
import java.util.Locale
import java.text.DateFormat
import java.text.DateFormat._
import scala.io.Source
import java.sql.DriverManager
import java.io.Closeable
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Test {
  class Reference[T] {
    private var contents: T = _
    def set(value: T) { contents = value }
    def get: T = contents
  }
  
  def close(c:Array[AutoCloseable]){
    c.foreach(el=>el.close())
  }
  
  def main(args:Array[String]){
    /*val now = new Date
    val df = getDateInstance(LONG, Locale.FRANCE)
    println(df format now) 调用java*/
    
   
    /* val cell = new Reference[Date]
    cell.set(new Date)
    println("Reference contains the half of " + (cell.get)) 调用泛型class*/
    
    /*val source  = Source.stdin
    for(el<-source.getLines()){
      println(el)
    } 标准输入*/
    
    /*val source= Source.fromURL("https://app.rthdtax.com/rtaxhelp","UTF-8")
    val lineIterator =source.getLines
    for(l<-lineIterator){
      println(l.toString())
    } 网络输入*/
    
    /*var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "liyuff")
    var stat = conn.createStatement()
    var ret = stat.executeQuery("select user from user")
    
    while(ret.next()){
      var row = ret.getString(1)
      println(row)
    }
    
    close(Array(ret,stat,conn)) 数据库jdbc mysql*/
    
    /*var arr = "hello,how are you baby, you are right!".split(" ");
    var sc = SparkContext.getOrCreate(new SparkConf());
    var ret = sc.parallelize(arr).map(el=>(el,1)).reduceByKey((a,b)=>a+b).collect()
    
    ret.foreach(e=>println(e)) 单词计数 */
    
    /*rdd.collect().toList(1).split(",").toList(2) 
    sc.parallelize(List("ab","bc","dd")).filter(e=>e.contains("b")).collect().toList(1) 显示打印*/
  }
}

