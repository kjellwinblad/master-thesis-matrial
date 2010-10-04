package tools


import scala.io.Source
import java.io.File
import java.io.PrintStream
import java.io.FileOutputStream
/*
arg 1: number of training sizes
arg 2... [firstPartOfFileNameOfLogWithTheSameConfiguration]
 
 
*/

object ProduceVisualisationData  extends Application {

	def dataFromLog(logFileName:String):List[String] = {
	val src =try {	
	   Source.fromFile(new File(logFileName))
   
	}catch{
     case e: Exception => Source.fromString("nothing" )
   }
   
   
	  val sb = new StringBuffer
	  for(line <-src.getLines())
		  sb.append(line + "\n")
          
      val fileContent = sb.toString
/*
Creating Liblinear model odm0.liblinear.mod
Learning time: 00:08:50 (530490 ms)
Finished: Tue Feb 02 09:57:58 CET 2010
Parsing time: 00:00:12 (12249 ms)
Finished: Tue Feb 02 09:58:12 CET 2010
Tue Feb  2 09:58:12 CET 2010
  Labeled   attachment score: 26828 / 33353 * 100 = 80.44 %
  Unlabeled attachment score: 28592 / 33353 * 100 = 85.73 %
  Label accuracy score:       27741 / 33353 * 100 = 83.17 %
*/
     val pattern  = """(?s).*Learning time:.*\((\d+).*.*Parsing time:.*\((\d+).*Labeled\s*attachment\s*score.*=\s*(\d+\.\d+)\s.*Unlabeled\s*attachment\s*score.*=\s*(\d+\.\d+)\s.*Label\s*accuracy\s*score.*=\s*(\d+\.\d+)\s.*""".r

     var result = List[String]();
   try { 
     val pattern(trainingTimeS,parsingTimeS,labelAttachemntScoreS,unlabeledAttachementScoreS,labelAccuracyScore)=fileContent
      
     result =List(trainingTimeS,parsingTimeS,labelAttachemntScoreS,unlabeledAttachementScoreS,labelAccuracyScore)
   } catch {
     case e: Exception =>  result = List("0","0","0","0","0")
   }
   
    



result
	}
  
    override def main(args: Array[String]) {
    

      val trainingConfigurations =  List(args.toList.drop(1).head)
      val fileName =  args.toList.drop(2).head
      var rowsList = List[List[String]]()
      val rows = args(0).toInt

      
      val out = new PrintStream(new FileOutputStream("viz_" + fileName))

      for(i <- (1 to rows).reverse){


        var row = List(i.toString)
        for(tConf <- trainingConfigurations)
          row = row:::dataFromLog(tConf + i  + ".log")
        rowsList=row::rowsList
      }



      //println()
      var fullList = List("x")
            for(trainingConf <- trainingConfigurations)
            	fullList = fullList:::List("trainingTime","learningTime", "labeled_a_s","unlabeled_a_s", "label_accuracy_s").map(trainingConf +"_"+_)

            
        //    print(fullList.mkString("[",",", "]"))
      //println()
      //for(trainingConf <- trainingConfigurations)
    //	  print (List("trainingTime","learningTime", "labeled_a_s","unlabeled_a_s", "label_accuracy_s").map(trainingConf +"_"+_).mkString(" "," ", ""))

      println()
        
        
        
      for(rowList <- rowsList){
        out.println(rowList.mkString(" "));
        println(rowList.mkString(" "));
      }
  
        /*
import scalala.Scalala._;
import scalala.tensor.{Tensor,Tensor1,Tensor2,Vector,Matrix};
import scalala.tensor.dense.{DenseVector,DenseMatrix};
import scalala.tensor.sparse._;
*/


val intList = for(rowList <- rowsList) yield {for(e <- rowList) yield e.toDouble}
  

out.close()

   
   
     0
  }
  
  
  
}

object ProduceVisualisationDataAll  extends Application {

/*
produce visualisation data for everything
*/

  
  override def main(args: Array[String]) {
	List("swe", "chi", "ger") foreach (lang=> {
	 
	    println("Visualisation data for language: "  + lang)
     
		(1 to 4) foreach (num =>ProduceVisualisationData.main(Array("8","exp" + num + lang)))
  
	})
  }
  
  
  
}

object ProduceVisualisationDataAll2  extends Application {

/*
produce visualisation data for everything
*/

  
  override def main(args: Array[String]) {
	List("swe", "chi", "ger_small", "cze", "ger_big","eng") foreach (lang=> {
	 
	    println("Visualisation data for language: "  + lang)
     
		List(1,2,5,6) foreach (num =>ProduceVisualisationData.main(Array("8","exp" + num + lang)))
  
	})
  }
  
  
  
}

object ProduceVisualisationDataAll3  extends Application {

/*
produce visualisation data for everything
*/

  
  override def main(args: Array[String]) {

	  List("swe", "chi", "ger_small", "cze", "ger_big","eng") foreach (lang=> {
	 
	    println("Visualisation data for language: "  + lang)
     
		
		
		

		List(1,2,5,6,7,8) foreach (num =>{
			
			
			ProduceVisualisationData.main(Array("8","exp" + num + lang, lang+ num))
			
			
		})
  
	})
  }
  
  
  
}

object ProduceVisualisationDataAll4  extends Application {

/*
produce visualisation data for everything
*/

  
  override def main(args: Array[String]) {

	  List("swe", "chi", "ger_small", "cze", "ger_big","eng") foreach (lang=> {
	 
	    println("Visualisation data for language: "  + lang)
     
		
		
		

		List(7,9,10,11,12) foreach (num =>{
			
			
			ProduceVisualisationData.main(Array("8","exp" + num + lang, lang+ num))
			
			
		})
  
	})
  }
  
  
  
}


object ProduceVisualisationDataAll5  extends Application {

/*
produce visualisation data for everything
*/

  
  override def main(args: Array[String]) {

	  List("swe", "chi", "ger_small") foreach (lang=> {
	 
	    println("Visualisation data for language: "  + lang)
     
		
		
		
		List(7,13,14,15) foreach (num =>{
			
			
			ProduceVisualisationData.main(Array("8","exp" + num + lang, lang+ num))
			
			
		})
  
	})
  }
    
}


object ProduceVisualisationDataAll6  extends Application {

/*
produce visualisation data for everything
*/

  
  override def main(args: Array[String]) {

	  List("swe", "chi", "ger_small") foreach (lang=> {
	 
	    println("Visualisation data for language: "  + lang)
     
		
		
		
		List(2,5,6,7,8,16,17,18) foreach (num =>{
			
			
			ProduceVisualisationData.main(Array("8","exp" + num + lang, lang+ num))
			
			
		})
  
	})
  }
    
}

object ProduceVisualisationDataAll7  extends Application {

/*
produce visualisation data for everything
*/

  
  override def main(args: Array[String]) {

	  List("swe", "chi", "ger_small") foreach (lang=> {
	 
	    println("Visualisation data for language: "  + lang)
     
		
		
		
		List(19) foreach (num =>{
			
			
			ProduceVisualisationData.main(Array("8","exp" + num + lang, lang+ num))
			
			
		})
  
	})
  }
    
}

