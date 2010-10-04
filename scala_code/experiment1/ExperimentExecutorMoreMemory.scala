package experiment1

import java.io.{BufferedOutputStream, File, FileOutputStream, FileWriter}
import scala.io.Source
import scala.util.matching.Regex

/*
[configuaration_file experiment_name training_file execution_time_in_hours]...
 
 ex

#!/bin/sh
module load java 

java -cp scala-library.jar:. experiment1/ExperimentExecutor \
 experiment1_liblinear_no_division.xml exp1swe swedish_talbanken05_train.conll 2:0:0 2000\
 experiment2_liblinear_with_division.xml exp2swe swedish_talbanken05_train.conll 2:0:0 2000\
 experiment3_libsvn_no_division.xml exp3swe swedish_talbanken05_train.conll 2:0:0 2000\
 experiment4_libsvn_with_division.xml exp4swe swedish_talbanken05_train.conll 2:0:0 2000
 configurationFile::                  name::  tf::                            et::  memory::nrOfTrainings
#!/bin/sh
module load java 

java -cp scala-library.jar:. experiment1/ExperimentExecutor \
 experiment1_liblinear_no_division.xml exp1ger german_tiger_train.conll 72:0:0 10000\
 experiment2_liblinear_with_division.xml exp2ger german_tiger_train.conll 72:0:0 10000\
 experiment3_libsvn_no_division.xml exp3ger german_tiger_train.conll 72:0:0 10000\
 experiment4_libsvn_with_division.xml exp4ger german_tiger_train.conll 72:0:0 10000

#!/bin/sh
module load java 
java -cp scala-library.jar:. experiment1/ExperimentExecutor \
 experiment1_liblinear_no_division.xml exp1chi chinese_sinica_train.conll 36:0:0 5000\
 experiment2_liblinear_with_division.xml exp2chi chinese_sinica_train.conll 36:0:0 5000\
 experiment3_libsvn_no_division.xml exp3chi chinese_sinica_train.conll 36:0:0 5000\
 experiment4_libsvn_with_division.xml exp4chi chinese_sinica_train.conll 36:0:0 5000



 
 */ 
object ExperimentExecutorMoreMemory extends Application{
	
   
  def doExperiment(configurationFile:String,name:String,experimentFile:java.io.File,time:String, memoryString:String, trainings:Int, testFile:File, useAsTrainingData:Int){
    
	  println("DO EXPERIMENT")
    
    if (trainings==0)
      return;
    
    val tf = """(\d+):(\d+):(\d+)""".r
    
    object TimeFormatted {
      def unapply(line: String): Option[(Int, Int, Int)] =
        for (List(hours, minutes, seconds) <- tf.unapplySeq(line))
          yield (hours.toInt, minutes.toInt, seconds.toInt)
    }
    val memoryF = memoryString.toDouble
    

      
      //Generate training file
    

      val useAsTrainingDatatMult= if(useAsTrainingData==1)useAsTrainingData.toDouble else 1.0/(useAsTrainingData.toDouble*0.6)
      val memory = if((memoryF*useAsTrainingDatatMult)<128) 128 else (memoryF*useAsTrainingDatatMult)
      //Create experiment training file and test file
      val experimentName=name+trainings
      
      var trainingFile = new java.io.File(experimentName+"training" + ".conll")
      val trainingFileOS = new FileWriter(trainingFile.getAbsolutePath)
      
      

      
      val src = Source.fromFile(experimentFile)

      var count = 1
      var includeCount = 0
      

      
      
      
      for(line <-src.getLines()){
        if(count%useAsTrainingData==0){
          
            //Write to training file
            trainingFileOS.write(line + "\n")
        
          if(line.trim.length==0)
            includeCount=includeCount+1
          
        }
        if(line.trim.length==0)
            count=count+1

      }
      
      trainingFileOS.close
      
      
      
      
      val TimeFormatted(h,m,s)=time
      
      val hours = (h.doubleValue/useAsTrainingData).intValue
      val minutes = ((h.doubleValue/useAsTrainingData)*60).intValue%60
      val seconds = 0
      val timeNew = if(hours==0 && minutes<15) "0:15:0" else hours + ":" + minutes + ":" +seconds
      
      doSubExperiment(configurationFile, experimentName,trainingFile, testFile,timeNew, memory.toInt)
      
    doExperiment(configurationFile,name,trainingFile,timeNew, memory.toString, trainings-1, testFile, 2)
    
  }
  
  def doSubExperiment(configurationFile:String, name:String,trainingFile:java.io.File, testFile:java.io.File,time:String, memory:Int){
    
    
    // Argument 1 the direcory where the experiment shall be run
    // Argument 2 the path to the CONLL file to train on
    // Argument 3 the path to the CONLL file to test on
    // Argument 4 the configuaration file of the experiment
    // Argument 5 name of the experiment
    // Argument 6 memory in mb required by Java
    val execString ="qsub -N" + " " + name + " " +
       "-l h_rt=" + time + " " +
      "-l mem=" + memory + "M" + " " +
       "./execute_experiment1.sh" + " " +
      "./glob/exp1MainDir" + " " +
      trainingFile.getAbsolutePath  + " " +
      testFile.getAbsolutePath + " " + 
      configurationFile + " " +   
      name + " " + 
      (memory -1);
   println(execString)
    
    val process = Runtime.getRuntime().exec(execString)
    
    process.waitFor
    
    val processInputStream =  process.getInputStream;
    val processErrorStream =  process.getErrorStream;
    
    val srcIn = Source.fromInputStream(processInputStream)
    val srcErr = Source.fromInputStream(processInputStream)
    
    

    for(line <-srcIn.getLines()) 
      print(line + "\n")
    
    for(line <-srcErr.getLines()) 
      print(line + "\n")
    
    
    
  }
  
  override def main(args: Array[String]) {
    
    println("Received the following arguments:" + args.mkString("", ", ", "."))
    
   def createTuplesOfParam(list:List[String]):List[(String, String,java.io.File,String,String,Int)] = list match{
     case configurationFile::name::tf::et::memory::nrOfTrainings::rest
     =>(configurationFile, name, new java.io.File(tf), et, memory,nrOfTrainings.toInt)::createTuplesOfParam(rest)
     case _ =>Nil 
   }
    
    
   val list = createTuplesOfParam(args.toList)
    
   
  println("CREATED LIST TUPLE " + list)
      
   
   for(experiment <- list){
     
     //Create first test and trainging data
     
     val (testFile,trainingFile) = createFirstTestAndTrainingData(experiment._2,experiment._3)
     
     doExperiment(experiment._1,experiment._2, trainingFile, experiment._4, experiment._5, experiment._6, testFile,  1);
   }
   
   
    0
  }
  
  def createFirstTestAndTrainingData(name:String,trainingFileBig:File) ={
        //Generate test file
      var trainingFile = new java.io.File(name+"training" + ".conll")
      val trainingFileOS = new FileWriter(trainingFile.getAbsolutePath)

      var testFile = new java.io.File(name+"test" + ".conll")
      val testFileOS = new FileWriter(testFile.getAbsolutePath)
      val srcCTF = Source.fromFile(trainingFileBig)
      

      var includeCount = 0
      

      
      
      
      for(line <-srcCTF.getLines()){

          
          if(includeCount%10==0) //write to test file 
            testFileOS.write(line + "\n")
          else
            trainingFileOS.write(line + "\n")
            
 
        
          if(line.trim.length==0)
            includeCount=includeCount+1
          

      }
      testFileOS.close()
      trainingFileOS.close()
    
    
    (testFile, trainingFile)

  }
  
  
}
