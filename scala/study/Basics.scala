package study

import scala.io.Source
import java.io.FileInputStream
import java.io.File
import java.io.FileOutputStream

class Greeter(prefix: String, suffix: String) {
  def greet(name: String): Unit =
    println(prefix + name + suffix)
}

case class Point(x: Int, y: Int)

object IdFactory {
  private var counter = 0
  def create(): Int = {
    counter += 1
    counter
  }
}

trait Say {
  def greet(name: String): Unit =
    println("Hello, " + name + "!")
  def say(name: String): Unit =
    this.greet(name)
}

class SayImpl extends Say{
  override def say(name: String): Unit =
    println("How are you, " + name + "!")
}

object Test {
  def main(args: Array[String]): Unit = {
    // class 
    new Greeter("Hello, ", "!").greet("Scala developer")
    // case class 
    println(Point(1,2)==Point(1,2)) //true
    // object
    println(IdFactory.create(),IdFactory.create())
    // trait
    new SayImpl().greet("anglar")
    new SayImpl().say("anglar")
    // tuples
    val planets =
    List(("Mercury", 57.9), ("Venus", 108.2), ("Earth", 149.6),
         ("Mars", 227.9), ("Jupiter", 778.3))
    planets.foreach{
      case ("Earth", distance) =>
        println(s"Our planet is $distance million kilometers from the sun")
      case _ =>
    }
    val numPairs = List((2, 5), (3, -7), (20, 56))
    for ((a, b) <- numPairs) {
      println(a * b)
    }
    val (name, quantity) = ("apple",100)
    println(name) // apple
    println(quantity) // 100
    
    // Mixing
    
    // function
    
    // nested methods
    /*
    def factorial(x: Int): Int = {
      def fact(x: Int, accumulator: Int): Int = {
        if (x <= 1) accumulator
        else fact(x - 1, x * accumulator)
      }  
    	fact(x, 1)
    }*/
    
    //偏函数
    val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(numbers.foldLeft(0)((m, n) => m + n))
   
    val numberFunc = numbers.foldLeft(List[Int]()) _
    val squares = numberFunc((xs, x) => xs :+ x*x)
    println(squares)
    //隐式
    
    //case class
    
    //pattern matching
    import scala.util.Random

    val x: Int = Random.nextInt(10)
    def f(p:String = "not match"):String=
      x match {
        case 0 => "zero"
        case 1 => "one"
        case 2 => "two"
        case _ => p
      }
    println(f())
    // regex
    import scala.util.matching.Regex

    val keyValPattern: Regex = "([0-9a-zA-Z-#() ]+): ([0-9a-zA-Z-#() ]+)".r

    val input: String =
      """background-color: #A03300;
        |background-image: url(img/header100.png);
        |background-position: top center;
        |background-repeat: repeat-x;
        |background-size: 2160px 108px;
        |margin: 0;
        |height: 108px;
        |width: 100%;"""

    for (patternMatch <- keyValPattern.findAllMatchIn(input))
      println(s"key: ${patternMatch.group(1)} value: ${patternMatch.group(2)}")
    
    //yeild
    def foo(n: Int, v: Int) =
    for (i <- 0 until n;j <- 0 until n if i + j == v)
    yield (i, j)

    foo(10, 10) foreach {
      case (i, j) =>
        println(s"($i, $j) ")  
    }  
    
    //option
    val sites = Map("runoob" -> "www.runoob.com", "google" -> "www.google.com")
    var ret1 = sites.get("runoob") ; var ret2 = sites.get("baidu")
    println(ret1.isEmpty)
    println(ret2.isEmpty)
    println(ret1.get)
    try{      
    	println(ret2.get) // throw NoSuchElementException
    }catch{
      case ex:NoSuchElementException => ex.getStackTrace.foreach(println(_))
      case _ => println("exception raised.")
    }
    
    //文件io
    val source= Source.fromURL("http://www.baidu.com","UTF-8")
    val lineIterator =source.getLines
    for(l<-lineIterator){
      println(l.toString())
    }
    
    var file = new File("src/unified-types-diagram.svg")
    var in = new FileInputStream(file)
    var b = new Array[Byte](file.length.toInt)
    in.read(b)
    var dest = new File("src/unified-types-diagram_dest.svg")
    var out = new FileOutputStream(dest)
    out.write(b)
    out.flush()
    out.close()
    in.close()
  }
}