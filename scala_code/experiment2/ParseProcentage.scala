package experiment2

import scala.io.Source

object ParseProcentage {

	def printGnuPlotLine(input:String, number:Int){
		
		val tf = """(\d*).*<-S\s*(\d*)\.(\d*).*<-CVD\s+(.*)\s+.*""".r
    
		object ProcentageFormatted {
			def unapply(line: String): Option[(Int,Double,String)] =
				for (List(fileSize,mainPart, decimalPart, fileName) <- tf.unapplySeq(line))
					yield (fileSize.toInt,mainPart.toDouble + (decimalPart.toDouble/( Math.pow(10,(decimalPart.length+1)))), fileName)
		}
	
		val src = Source.fromString(input)
		
		print(number)
		
		for(line <- src.getLines()){
		
			val ProcentageFormatted(fileSize,correctProcentage, fileName)= line.trim
			
			print(" " + correctProcentage)
			
		}
		
		println()
	
	}
}