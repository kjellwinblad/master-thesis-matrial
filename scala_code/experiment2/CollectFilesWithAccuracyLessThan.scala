package experiment2


import scala.io.Source
import java.io.File
/**
Parameters:
 * accuracy
 */

object CollectFilesWithAccuracyLessThan {
  def main(args : Array[String]) : Unit = {
    val leastAccuracy = args(0).toDouble
    val source =  Source.fromInputStream(System.in)
    
    
    val tf = """(\d*).*<-S\s*(\d*\.\d*|\d*).*<-CVD\s+(.*)\s+.*""".r
    
    object ProcentageFormatted {
      def unapply(line: String): Option[(Int,Double,String)] =
        for (List(fileSize,mainPart, fileName) <- tf.unapplySeq(line))
          yield (fileSize.toInt,mainPart.toDouble , fileName)
      
    }
    
    var toBadTotalSize = 0
    var toBadTotalAccurazy = 0.0
    var goodEnoughTotalSize = 0
    var goodEnoughTotalAccurazy = 0.0
    
    var totalCorrect = 0.0
    var totalCount = 0
    var list:List[Double] = Nil
    for(line <- source.getLines()){
      try{
        
        val ProcentageFormatted(count,procentageCorrect, fileName)= line.trim

        if(leastAccuracy>=procentageCorrect){
          //Output the file to system out
          val outputSource = Source.fromFile(new File(fileName))
          
          toBadTotalSize+=count
          toBadTotalAccurazy+=((procentageCorrect/100.0)*count)
          
          for(line <- outputSource.getLines())
            print(line + "\n")
          
                val amountCorrect = procentageCorrect/100
    
    totalCorrect+=count*amountCorrect
    totalCount+=count
    	list=(list:::List(procentageCorrect))
        }else{
        	list=(list:::List(procentageCorrect))
        	    val amountCorrect = procentageCorrect/100
    
    totalCorrect+=count*amountCorrect
    totalCount+=count
          //Save the error and size so the total can be calculated
          goodEnoughTotalSize+=count
          goodEnoughTotalAccurazy+=((procentageCorrect/100.0)*count)
        }
        
        
      }catch{
        case e:Exception=>{println("EXCEPTION ");System.exit(0)}//ignore
      }
      
    }
    
    

    
     //This line has to be removed from the output later
    val procentOfTotalCorrect =totalCorrect/totalCount
    println("good enough total size= " + goodEnoughTotalSize)
    println("good enough correct= " + (goodEnoughTotalAccurazy/goodEnoughTotalSize))
    println("Too bad total size= " + toBadTotalSize)
    println("Too bad total correct= " + (toBadTotalAccurazy/toBadTotalSize))
  }
}
