package experiment4

import experiment3.TreeClassifierCreation
import scala.io.Source
import java.io.File


object TreeClassifierCreationWithCalculatedDivisionOrder {
  def main(args : Array[String]) : Unit = {
	  
	val fileName = args(0)
	  


	val gainRatioList = BestSplitListCreation.getGainRatioList(fileName)
	

	val t = TreeClassifierCreation.getTreeForFile(fileName, gainRatioList)

    println(t)
    
    println("With gain ratio list " + gainRatioList.mkString(" "))
    
    TreeClassifierCreation.printDotFileFromTree(t)
	
	  
  }
}
