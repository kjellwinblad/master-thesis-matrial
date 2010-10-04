package experiment2

import scala.io.Source
/**
 Reads list of files in the format
790784 <-S 90.01624% <-PC 10 <-CVD input0divSwedish_less_than_mean_c1_t1000_cat22 <-TFN
807127 <-S 97.00688% <-PC 10 <-CVD input0divSwedish_less_than_mean_c1_t1000_cat8 <-TFN
1022524 <-S 91.0391% <-PC 10 <-CVD input0divSwedish_less_than_mean_c1_t1000_cat13 <-TFN
1421762 <-S 73.04513% <-PC 10 <-CVD input0divSwedish_less_than_mean_c1_t1000_cat2 <-TFN
1676645 <-S 82.07625% <-PC 10 <-CVD input0divSwedish_less_than_mean_c1_t1000_cat7 <-TFN
From standard input
Split all files and name them
name_split_nr by the column specified as parameter 1
Output crossvalidation training output in the following format:

790784 <-S 90.01624% <-PC 10 <-CVD input0divSwedish_less_than_mean_c1_t1000_cat22 <-TFN

1000 <-S 90.01624% <-PC 10 <-CVD input0divSwedish_less_than_mean_c1_t1000_cat22_split_1 <-TFN
1000 <-S 90.01624% <-PC 10 <-CVD input0divSwedish_less_than_mean_c1_t1000_cat22_split_2 <-TFN
...
Avarange accuaracy


...
 

 */
object DivideAgainAndCompareResult {
  def main(args : Array[String]) : Unit = {
    val splitColumn = args(0)
        val source =  Source.fromInputStream(System.in)
    
    
    val tf = """(\d*).*<-S\s*(\d*)\.(\d*).*<-CVD\s+(.*)\s+<-.*""".r
    
    object ProcentageFormatted {
      def unapply(line: String): Option[(Int,Double,String)] =
        for (List(fileSize,mainPart, decimalPart, fileName) <- tf.unapplySeq(line))
          yield (fileSize.toInt,mainPart.toDouble + (decimalPart.toDouble/( Math.pow(10,(decimalPart.length+1)))), fileName)
      
    }
    
    for(line <- source.getLines()){
      println(line.trim)
      println()
      
      val ProcentageFormatted(fileSize,correctProcentage, fileName)=line.trim
      
      val smallestCat= "1000"
      
      DivideByColumn.main(Array( fileName, splitColumn.toString, smallestCat))
      RunLiblinearCrossValidationOnFiles.main(Array( "10",fileName + "_c" + splitColumn + "_t" + smallestCat +"_cat"))
      println("--------------------------------------------")
    }
    
    
  }
}
