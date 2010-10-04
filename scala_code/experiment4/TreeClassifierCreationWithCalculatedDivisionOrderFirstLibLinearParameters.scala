package experiment4

import experiment3.TreeClassifierCreationFirstLiblinearParameters
import scala.io.Source
import java.io.File


object TreeClassifierCreationWithCalculatedDivisionOrderFirstLibLinearParameters {
  def main(args : Array[String]) : Unit = {
	  
	val fileName = args(0)
	  


	val gainRatioList = BestSplitListCreation.getGainRatioList(fileName)
	

	val t = TreeClassifierCreationFirstLiblinearParameters.getTreeForFile(fileName, gainRatioList)

    println(t)
    
    println("With gain ratio list " + gainRatioList.mkString(" "))
    
    TreeClassifierCreationFirstLiblinearParameters.printDotFileFromTree(t)
	
	  
  }
}
