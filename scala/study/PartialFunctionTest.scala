package study

import java.util.Date

object PartialFunctionTest{
  def log(date: Date, message: String)  = {
     println(date + "----" + message)
  }
  def main(args: Array[String]): Unit = {
     val now = new Date
		 val logWithDateBound = log(now, _ : String)
     logWithDateBound("msg1")
     logWithDateBound("msg2")
     logWithDateBound("msg3")
  }
}