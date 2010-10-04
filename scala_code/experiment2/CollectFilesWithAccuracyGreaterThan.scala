package experiment2


import scala.io.Source
import java.io.File
/**
Parameters:
 * accuracy
 */

object CollectFilesWithAccuracyGreaterThan {
  def main(args : Array[String]) : Unit = {
    val leastAccuracy = args(0).toDouble
    val source =  Source.fromInputStream(System.in)
    
    
    val tf = """(\d*).*<-S\s*(\d*\.\d*|\d*).*<-CVD\s+(.*)\s+.*""".r
    
    object ProcentageFormatted {
      def unapply(line: String): Option[(Int,Double,String)] =
        for (List(fileSize,mainPart, fileName) <- tf.unapplySeq(line))
          yield (fileSize.toInt,mainPart.toDouble, fileName)
      
    }
    
    var toBadTotalSize = 0
    var toBadTotalAccurazy = 0.0
    var goodEnoughTotalSize = 0
    var goodEnoughTotalAccurazy = 0.0
    
    for(line <- source.getLines()){
      try{
        
        val ProcentageFormatted(fileSize,correctProcentage, fileName)= line.trim

        if(leastAccuracy<correctProcentage){
          //Output the file to system out
          val outputSource = Source.fromFile(new File(fileName))
          
          toBadTotalSize+=fileSize
          toBadTotalAccurazy+=((correctProcentage/100.0)*fileSize)
          
          for(line <- outputSource.getLines())
            print(line + "\n")
          
        }else{
          //Save the error and size so the total can be calculated
          goodEnoughTotalSize+=fileSize
          goodEnoughTotalAccurazy+=((correctProcentage/100.0)*fileSize)
          
              
        }
        
        
      }catch{
        case e:Exception=>;//ignore
      }
      
    }
    //This line has to be removed from the output later
    println("good enough total size= " + toBadTotalSize)
    println("good enough correct= " + (toBadTotalAccurazy/toBadTotalSize))
    println("Too bad total size= " + goodEnoughTotalSize)
    println("Too bad total correct= " + (goodEnoughTotalAccurazy/goodEnoughTotalSize))
    

  }
}
