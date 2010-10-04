package reporttools

import scala.io.Source

object ExtractProcentageaa {
  def main(args : Array[String]) : Unit = {
	  
	val tf = """(\d*).*<-S\s*(\d*)\.(\d*).*<-CVD\s+(.*)\s+.*""".r
    
    object ProcentageFormatted {
      def unapply(line: String): Option[(Int,Double,String)] =
        for (List(fileSize,mainPart, decimalPart, fileName) <- tf.unapplySeq(line))
          yield (fileSize.toInt,mainPart.toDouble + (decimalPart.toDouble/( scala.math.pow(10,(decimalPart.length+1)))), fileName)
      
    }
	  
	  val s = Source.fromInputStream(System.in)
	  
	  for( line <- s.getLines() ){
	 	val ProcentageFormatted(_,procentage,_)=line
	 	println(args(0) + "  " + procentage)
  	  }
	   
	  
	   
  }
}
