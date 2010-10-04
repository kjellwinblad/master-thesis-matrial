package experiment1

import scala.io._
import scalala.Scalala._;
import scalala.tensor.{Tensor,Tensor1,Tensor2,Vector,Matrix};
import scalala.tensor.dense.{DenseVector,DenseMatrix};
import scalala.tensor.sparse._;

/*
 What is needed is one diagram for every language
 in all of the diagrams the four test will be displated

 [source_file column_number]...
 */
object CreateGraphs extends Application{
    override def main(args: Array[String]) {  
       if(args.length==0){
         //viz_chi1  viz_chi5  viz_cze1  viz_cze5  viz_eng1  viz_eng5  viz_ger_big1  viz_ger_big5  viz_ger_small1  viz_ger_small5  viz_swe1  viz_swe5
         //viz_chi2  viz_chi6  viz_cze2  viz_cze6  viz_eng2  viz_eng6  viz_ger_big2  viz_ger_big6  viz_ger_small2  viz_ger_small6  viz_swe2  viz_swe6
    	   //
         for{lang <- List("viz_swe","viz_chi","viz_ger_small", "viz_cze", "viz_ger_big", "viz_eng") 
             column <- (0 to 4)
         }{
           val argumentArray = (List(1,2,5,6) flatMap 
             (a=>List(lang + a.toString,column.toString))).toArray
           println("create graphs with " +argumentArray.mkString(", "))
        	 CreateGraphs.main(argumentArray)
         }
       }else{
      if(args.length%2!=0){
        println("[source_file column_number]...")
        return
      }
      println(args)     
      val argsFormatted = ({
        def combine(l:List[String]):List[(String,Int)] =
       l match{
         case Nil=> Nil:List[(String,Int)]
         case e::Nil => throw new Exception("")
         case e1::e2::rest => (e1,e2.toInt)::combine(rest)
       }
        combine
      })(args.toList)
      println("Args formatted " + argsFormatted.mkString(","));
      hold(false)
      for(arg <- argsFormatted){
        val (fileName, columnNumber) = arg
        println("create graph with " +columnNumber + " " + fileName )
        CreateGraph.drawGraph(columnNumber,5,8,1, fileName)
        hold(true)
      }
      Thread.sleep(400)
      saveas((argsFormatted map {_._1}).mkString + argsFormatted.head._2 + ".png")
       hold(false)
       } 
    }
}
//CreateGraphs.main(Array())