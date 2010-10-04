package experiment2

import scala.io.Source
import java.util.Scanner
import java.io.FileWriter
import java.io.File

object DivideByTwoColumns {
  def main(args : Array[String]) : Unit = {
    
    val inputFileName = args(0)
    
    val columnToSeparateBy1 = args(1).toInt
    
    val columnToSeparateBy2 = args(2).toInt
    
    val treshhold= args(3).toInt
    
    val source = Source.fromFile(new File(inputFileName))
    

    
    val categoryMap = scala.collection.mutable.HashMap.empty[(Int,Int), List[String]]
    var count = 0
    for(line <- source.getLines()){

      
      val columnValue = (getColumnValue(line, columnToSeparateBy1), getColumnValue(line, columnToSeparateBy2))
      
      
      val categoryList = categoryMap.getOrElseUpdate(columnValue, Nil)
      
      categoryMap.update(columnValue, line::categoryList)
      
      count+=1
      
      if(count%10000==0)
        println("Procesed line: " + count)
    }
    
    var notOverTrachhold:List[String] = Nil
    
    var categoryCounter = 0;
    for((key, value) <- categoryMap){
      if(value.size<treshhold)
        notOverTrachhold = notOverTrachhold:::value
      else{
          printStringListToFile(inputFileName + "_c"+columnToSeparateBy1 + "-" + columnToSeparateBy2 +"_t"+treshhold + "_cat"+categoryCounter, value)
          categoryCounter+=1
      }
      
    }
    printStringListToFile(inputFileName + "_c" +  columnToSeparateBy1 + "-" + columnToSeparateBy2 +"_t"+treshhold + "_cat"+categoryCounter, notOverTrachhold)
    
    
    
  }
  
  def printStringListToFile(outputFile:String, stringList:List[String]){
    val outputWriter = new FileWriter(outputFile)
    
    for(line<-stringList)
      outputWriter.write(line  + "\n")
    
    outputWriter.close()
    
    
  }
  
  def getColumnValue(line:String, columnToSeparateBy:Int)={
      
      val scanner = new Scanner(line)
      var columnValueTemp = 0;
      try{
        
        for(i <-0 to columnToSeparateBy){
          columnValueTemp = scanner.nextInt()
        }
        
 
      }catch{
        case e:Exception => ;//Do nothing
      }
      columnValueTemp
  }
}
