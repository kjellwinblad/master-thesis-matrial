package experiment1

import scala.io._
import scalala.Scalala._;
import scalala.tensor.{Tensor,Tensor1,Tensor2,Vector,Matrix};
import scalala.tensor.dense.{DenseVector,DenseMatrix};
import scalala.tensor.sparse._;

object CreateGraph extends Application{

  
  def drawGraph(columnToCreateGraphOf:Int,
                totalNumberOfColmnsPerExperiment:Int,
                numberOfExperimentRows:Int,
                numberOfExperimentLanguages:Int,
  				sourceFile:String){
                  
        
      val source = Source.fromFile( new File(sourceFile))
      //val source = Source.
     //(args.toList.drop(4).head)
    
     val m = DenseMatrix(numberOfExperimentRows,numberOfExperimentLanguages)(0)
     
     var count = 0;
     for(line <- source.getLines){

       var lineValues = line.split("""\s""").drop(1).map(_.toDouble)
       
       //println("line valuses " + lineValues)
       for(i <- 0 to (numberOfExperimentLanguages-1)){
        //println("a" ) 
         //println(m(count, i) ) 
        //println(columnToCreateGraphOf + i*totalNumberOfColmnsPerExperiment)
    	   m(count, i) = lineValues.apply(columnToCreateGraphOf + i*totalNumberOfColmnsPerExperiment)
       }
       count = count +1
         
     }
     
     println(m)
     
     
     val x = DenseVector(numberOfExperimentRows)(0)
     
     x(0 to (numberOfExperimentRows-1))=(1 to numberOfExperimentRows).map(_.toDouble)
     
     for(column <- 0 to (m.cols-1)){
    	 plot(x, m.getCol(column))
    	 hold(true)
      
     }
    
    
    
                }
  
  
  //Arg 1: Column to create graph of
  //Arg 2 total number of columns per experiment
  //Arg 3: Number of experiment rows
  //Arg 3: Number of experiment languages
    override def main(args: Array[String]) {
     val columnToCreateGraphOf = args.toList.head.toInt
     val totalNumberOfColmnsPerExperiment = args.toList.drop(1).head.toInt
     val numberOfExperimentRows = args.toList.drop(2).head.toInt
     val numberOfExperimentLanguages = args.toList.drop(3).head.toInt  
     val sourceFile = args.toList.drop(4).head
     
     drawGraph(columnToCreateGraphOf,
                totalNumberOfColmnsPerExperiment,
                numberOfExperimentRows,
                numberOfExperimentLanguages,
  				sourceFile)
     
    }
  
  
}

//CreateGraph.main(Array("1","5","8", "3", "exp1")) 
