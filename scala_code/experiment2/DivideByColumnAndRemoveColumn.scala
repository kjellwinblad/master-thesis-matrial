package experiment2

import java.util.Scanner
import scala.io.Source
import java.io.FileWriter
import java.io.File
/**
 * Arguments:
 * inputFileName separateByColumn Treshhould
 * 
 * 
 * 
 */
object DivideByColumnAndRemoveColumn {
  def main(args : Array[String]) : Unit = {
    
    val inputFileName = args(0)
    
    val columnToSeparateBy = args(1).toInt
    
    val treshhold= args(2).toInt
    
    val source = Source.fromFile(new File(inputFileName))
    

    
    val categoryMap = scala.collection.mutable.HashMap.empty[Int, List[String]]
    var count = 0
    for(line <- source.getLines()){
      val scanner = new Scanner(line)
      var columnValueTemp = 0;
      try{
        
        for(i <-0 to columnToSeparateBy){
          columnValueTemp = scanner.nextInt()
        }
        
        
        
      }catch{
        case e:Exception => ;//Do nothing
      }
      
      val columnValue = columnValueTemp
      
      val categoryList = categoryMap.getOrElseUpdate(columnValue, Nil)
      
      
      
      val splitArray = line.split("""\s""")
      
      val newLine = 
        if (splitArray.size<3) line	
        else{
        	
        	
        	splitArray(0).trim + " " + splitArray.slice(1, columnToSeparateBy).mkString(" ") + " " + 
        	  (if(columnToSeparateBy==splitArray.size) "" else splitArray.drop(columnToSeparateBy+1).mkString(" "))
        }
       
      
      categoryMap.update(columnValue, newLine::categoryList)
      
      count+=1
      
      //if(count%10000==0)
        //println("Procesed line: " + count)
    }
    
    var notOverTrachhold:List[String] = Nil
    
    var categoryCounter = 0;
    for((key, value) <- categoryMap){
      if(value.size<treshhold)
        notOverTrachhold = notOverTrachhold:::value
      else{
          printStringListToFile(inputFileName + "_c"+columnToSeparateBy +"_t"+treshhold + "_cat"+categoryCounter, value)
          categoryCounter+=1
      }
      
    }
    if(notOverTrachhold!=Nil){
    	printStringListToFile(inputFileName + "_c"+columnToSeparateBy +"_t"+treshhold + "_cat"+(categoryCounter), notOverTrachhold)
    	(new File(inputFileName + "_other_exists")).createNewFile() 
     }
    
    
  }
  
  def printStringListToFile(outputFile:String, stringList:List[String]){
    val outputWriter = new FileWriter(outputFile)
    
    for(line<-stringList)
      outputWriter.write(line + "\n")
    
    outputWriter.close()
    
    
  }
}
