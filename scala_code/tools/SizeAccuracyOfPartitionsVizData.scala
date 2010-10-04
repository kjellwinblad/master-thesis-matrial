package tools

import scala.io.Source

object SizeAccuracyOfPartitionsVizData extends Application{
	
	    override def main(args: Array[String]) {
	    	
	    	val src = Source.fromInputStream(System.in)
	    	
	    	val sizeAccuracyList = (for(line <- src.getLines()) yield {
	    		val splitedLine = line.split("""\s+""")
	    		val sizeOnLine = splitedLine(0).toLong
	    		val accuracyOnLine = splitedLine(2).take(splitedLine(2).length-1).toDouble

	    		(sizeOnLine, accuracyOnLine)
	    	}).toList
	    	
	    	val totalSize = sizeAccuracyList.foldLeft(0L)((sum,value)=>sum+value._1)

	    	val relativeSizeAccuracyList = for((size, accuracy) <- sizeAccuracyList) yield {

	    		(size.toDouble/totalSize.toDouble, accuracy)
	    	}
	    	
	    	//Print the list
	    	for((relativeSize, accuracy) <- relativeSizeAccuracyList){

	    		println(relativeSize + "\t" + accuracy)
	    	}
	    	
	    	
	    }
	

}