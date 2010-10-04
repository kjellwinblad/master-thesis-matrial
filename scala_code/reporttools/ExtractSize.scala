package reporttools

import scala.io.Source

object ExtractProcentage {
  def main(args : Array[String]) : Unit = {
	  
	val tf = """(\d*).*<-S\s*(\d*)\.(\d*).*<-CVD\s+(.*)\s+.*""".r
    
    object ProcentageFormatted {
      def unapply(line: String): Option[(Int,Double,String)] =
        for (List(fileSize,mainPart, decimalPart, fileName) <- tf.unapplySeq(line))
          yield (fileSize.toInt,mainPart.toDouble + (decimalPart.toDouble/( scala.math.pow(10,(decimalPart.length+1)))), fileName)
      
    }
	  
	 val s = Source.fromInputStream(System.in)
	  
	  
	val sizeList = 
		  for( line <- s.getLines() ) yield {
	 	    val ProcentageFormatted(size,_,_)=line
	 	    size
  	      }
	 
	 println("Number of elements " + sizeList.size)
	 println("Total size " + sizeList.mkString("+"))
	 println("Total size " + sizeList.foldLeft(0)(_+_))
	   
	   
	  
	   
  }
}
