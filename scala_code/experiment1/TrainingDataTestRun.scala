package experiment1

object TrainingDataTestRun extends Application{

  /*
	test_data_file [training_data_beginnnings]
 
  */
  
  override def main(args: Array[String]) {
    
    println("Received the following arguments:" + args.mkString("", ", ", "."))
     
    
   
    val testDataFile = args.toList.head
    
 
     
    val trainingData = args.toList.drop(1).flatMap(trainingDataBeginning => (1 to 8).map(trainingDataBeginning + _ + ""));
    
    for(trainingDataFile <- trainingData){
      val nameString = trainingDataFile + 2;
      val execCommand = Array("qsub","-N",nameString/*,"-l", memoryParam, "-l", timeParam*/,"./doParsingAndCreateResult.sh","./" , testDataFile, trainingDataFile + "_trained_model.mco", nameString)
      try{
            println("Executing: " + execCommand.mkString(" "))
      
            Runtime.getRuntime.exec(execCommand);

      }catch{
        case e:Exception=> println("Could not exequte program")
      }

   
    } 	
   
    exit(0)
  }
  
}
