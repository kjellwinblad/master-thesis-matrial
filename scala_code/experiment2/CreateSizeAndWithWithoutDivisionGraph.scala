package experiment2

import scala.io.Source

import scala.io._
import scalala.Scalala._;
import scalala.tensor.{Tensor,Tensor1,Tensor2,Vector,Matrix};
import scalala.tensor.dense.{DenseVector,DenseMatrix};
import scalala.tensor.sparse._;
/**
 * Reads the graph info from standard input and produce a graph with the name specified as input 1
 * 
*/
object CreateSizeAndWithWithoutDivisionGraph {
  def main(args : Array[String]) : Unit = {
    
    val outputGraphName = args(0)
    
    val input = Source.fromInputStream(System.in)

    val strBuf = new StringBuffer("") 
    for(line <- input.getLines()){
      strBuf.append(line)
    }
      
    val sourceString = strBuf.toString
    
    val (xValues, withDivisions, withoutDivisions) = getGraphValues(sourceString)
    
    //Create the graph
    val x = DenseVector(xValues.size)(0)
    x(0 to (xValues.size-1))=xValues
    
    val withDivisionsV = DenseVector(withDivisions.size)(0)
    withDivisionsV(0 to (withDivisions.size-1))=withDivisions
    
    val withoutDivisionsV = DenseVector(withoutDivisions.size)(0)
    withoutDivisionsV(0 to (withoutDivisions.size-1))=withoutDivisions
    
    //Now plot the graphs
    
    plot(x, withDivisionsV)
    hold(true)
    plot(x, withoutDivisionsV)
    xlabel("Training file's size")
    ylabel("Cross validation accuracy (10 divisions)")
    saveas(outputGraphName + ".eps")
    saveas(outputGraphName + ".png")
    
  }
  
  def getGraphValues(sourceString:String) ={
    def getGraphValuesR(sourceString:String,xValues:List[Double],withDivisions:List[Double], withoutDivisions:List[Double]):(List[Double],List[Double],List[Double]) ={
    
      val splitValues = sourceString.split("--------------------------------------------",2)
      val thisInstance = splitValues(0)
      
      val newXValues =
        ("""(\d*)""".r.findFirstIn(
          """\d* <-S""".r.findFirstIn(thisInstance) match{
            case None=>"0.0"
            case Some(x)=>x
          }
        )match{
            case None=>"0.0"
            case Some(x)=>x
          }).toDouble::xValues

            val newWithoutDivisions =
        ("""\d*\.\d*""".r.findFirstIn(
          """\d*\.\d*% <-PC""".r.findFirstIn(thisInstance) match{
            case None=>"0.0"
            case Some(x)=>x
          }
        )match{
            case None=>"0.0"
            case Some(x)=>x
          }).toDouble::withoutDivisions
            

                  val newWithDivisions =
        ("""\d*\.\d*""".r.findFirstIn(
          """Procent of total correct: \d*\.\d*""".r.findFirstIn(thisInstance) match{
            case None=>"0.0"
            case Some(x)=>x
          }
        )match{
            case None=>"0.0"
            case Some(x)=>x
          }).toDouble::withDivisions

      if(splitValues == null)
        (newXValues, newWithDivisions, newWithoutDivisions)
      else if(splitValues.size < 2)
        (newXValues, newWithDivisions, newWithoutDivisions)  
      else if(splitValues(1).length < 5)
        (newXValues, newWithDivisions, newWithoutDivisions)
      else
          getGraphValuesR(splitValues(1),newXValues, newWithDivisions, newWithoutDivisions)
      
    }
    
    val (newXValues, newWithDivisions, newWithoutDivisions) =getGraphValuesR(sourceString,Nil,Nil,Nil)
    
    (newXValues, newWithDivisions, newWithoutDivisions)
  }
}
