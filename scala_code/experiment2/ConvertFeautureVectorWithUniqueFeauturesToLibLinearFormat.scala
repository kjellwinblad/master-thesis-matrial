package experiment2
import java.io.File

import java.io.{BufferedOutputStream, File, FileOutputStream, FileWriter}
import scala.io.Source
import java.util.Scanner

/**
 * Parameters
 * input_file
 */
object ConvertFeautureVectorWithUniqueFeauturesToLibLinearFormat {
  def main(args : Array[String]) : Unit = {
    
    val inputFile = args(0)
    
    val feautureVecorsFileSource = Source.fromFile(new File(args(0)))
    
      for(line <- feautureVecorsFileSource.getLines()){
        val feautureVecorScanner = new Scanner(line.trim)
      
        print(feautureVecorScanner.nextInt + " ")
        
        while(feautureVecorScanner.hasNext){
          print(feautureVecorScanner.nextInt() + ":1.0")
          if(feautureVecorScanner.hasNext)
            print(" ")
        }
        
        println()
      }
    
  }
}
