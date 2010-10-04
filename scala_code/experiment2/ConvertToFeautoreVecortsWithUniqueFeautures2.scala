package experiment2

import java.io.File

import java.io.{BufferedOutputStream, File, FileOutputStream, FileWriter}
import scala.io.Source
import java.util.Scanner

/**
 * Arguments:
 * feauture_vecor_file cardinality_file
 * 
 */

object ConvertToFeautoreVecortsWithUniqueFeautures2 {
  def main(args : Array[String]) : Unit = {
    
    val feautureVecorFileSource = Source.fromFile(new File(args(0)))
    val cardinalityFileSource = Source.fromFile(new File(args(1)))
    
    val columnsToRemove = args.zipWithIndex.filter(p=>p._2>1).map(p=>p._1.toInt)
    
    val carArray = cardinalityFileSource.getLines().toList(0).split(",").map(_.trim).map(_.toInt)
    
//    var columnCounterList = new Array[Int](0)
    

    
//var columnArgumentToNumberMapList = new Array[scala.collection.jcl.TreeMap[String,Int]](0)
    var columns = 0
    
    //load everything from disk
    val feautureVecorFileSourceOpt = feautureVecorFileSource.getLines().toList
    
    
    for(line <- feautureVecorFileSourceOpt){
      val feautureVecorScanner = new Scanner(line.trim)
     // val cardinalityScanner = new Scanner(carString)
     // cardinalityScanner.useDelimiter(",")
      //Print the class
     
      print(feautureVecorScanner.nextInt() + "\t")
     
      var cardinalitySum = 1
      var columnNr = -1
      while(feautureVecorScanner.hasNext){
        columnNr += 1
        
        if(!columnsToRemove.exists(a=>(columnNr==a))){
        

        
        val columnValue = feautureVecorScanner.nextInt()

        
        

        
        
        print(columnValue + cardinalitySum  + "\t")
        try{
        cardinalitySum += carArray(columnNr)
        }catch{
          case _ => ;
        }
        }else{
          feautureVecorScanner.next()
        }
      }
      
      println()
      
    }
    
    
    //exit(0)
  }
}
//ConvertToFeautoreVecortsWithUniqueFeautures.main(Array("fv","cf"))
