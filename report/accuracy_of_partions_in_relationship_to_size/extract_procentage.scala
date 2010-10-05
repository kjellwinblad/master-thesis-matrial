package tools

import scala.io.Source

object ExtractProcentage {
  def main(args : Array[String]) : Unit = {
	  
		val src = Source.fromInputStream(System.in)
	    	
	    	for(line <- src.getLines()) {
	    		val splitedLine = line.split("""\s+""")
	    		val accuracyOnLine = splitedLine(2).take(splitedLine(2).length-1).toDouble

	    		println(args(0) + " " +  accuracyOnLine)
	    	}
	  
	  
  }
}
