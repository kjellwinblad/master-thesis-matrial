package experiment2

import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import scala.io.Source
/**
 * Arguments:
 * cross_validation_divisions files_prefix
 * 
 */
object RunLiblinearCrossValidationOnFilesOldParams {
  def main(args : Array[String]) : Unit = {
    val crossValidationDivisions = args(0).toInt
    val filesPrefix = args(1)
    var index = 0
    
    var totalCorrect = 0.0
    var totalCount = 0
    while(new File(filesPrefix+index).exists){
      
        val file = new File(filesPrefix+index)
        
    	val fileToTestName = filesPrefix+index


    	val createFileProcess = Runtime.getRuntime().exec(Array("java", "-Xmx500M" ,"-cp", "scala/lib/scala-library.jar:.", "experiment2/ConvertFeautureVectorWithUniqueFeauturesToLibLinearFormat",  fileToTestName))
     
    	val createFileInputStream = Source.fromInputStream(createFileProcess.getInputStream)
     
    	val liblinearFileOutputStream = new FileWriter(fileToTestName + "lib_linear")
       
     
     
    	for(line<- createFileInputStream.getLines())
    		liblinearFileOutputStream.write(line + "\n")
       
       

       liblinearFileOutputStream.close()
     
     

    	createFileProcess.waitFor
     //Changed to "./train -s 4 -c 0.1 -v 10 " from "./train", "-v" ,"10",
    	val p = Runtime.getRuntime().exec("./train -v 10 " + fileToTestName + "lib_linear")



    	val source = Source.fromInputStream(p.getInputStream)
     
     
    	for(line <- source.getLines()){
    	  if(line.contains("Cross Validation Accuracy")){
    	     val is = new FileInputStream(file)
    	     var count = 0
    	     while(is.read()!= -1)
    	    	 count+=1

              val tf = """.*=\s*(\d*\.\d*|\d*)%""".r
    
    object ProcentageFormatted {
      def unapply(line: String): Option[Double] =
        for (List(mainPart) <- tf.unapplySeq(line))
          yield (mainPart.toDouble)
    }
          
    try{
    val ProcentageFormatted(procentageCorrect)=line.trim
    	     print(count+" <-S ")
             print(procentageCorrect + "% <-PC ")
             print(crossValidationDivisions + " <-CVD ")
             println(fileToTestName + " <-TFN")
             
             
               

    
    
    val amountCorrect = procentageCorrect/100
    
    totalCorrect+=count*amountCorrect
    totalCount+=count
}catch{
  case e:Exception=>println("Error when processing line " + line)
}
    	  }
         
    	}
     
          p.waitFor
    	new File(fileToTestName + "lib_linear").delete()
     index+=1
    }
    
    val procentOfTotalCorrect =totalCorrect/totalCount
    println("Procent of total correct: " + (procentOfTotalCorrect*100))
    
    
  }
}
