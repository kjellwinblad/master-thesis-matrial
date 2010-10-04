package experiment4

import experiment2._
import scala.io.Source
import java.io.FileWriter
import java.io.File



object TreeClassifierCreationCalculateDivisionOrderInEveryNode extends Application{
  
  case class Tree(val liblinearAccuracy:Double, val divisionAccuracy:Double, val fileSize:Int, val branches:List[Tree])
  case class OtherTree(override val liblinearAccuracy:Double,override val divisionAccuracy:Double,override val fileSize:Int,override val branches:List[Tree]) 
    extends Tree (liblinearAccuracy, divisionAccuracy, fileSize, branches)
  
  override def main(args:Array[String]){
    val inputFile = args(0)
/*
InputColumn(CPOSTAG, Input[0]) 2
InputColumn(CPOSTAG, Stack[0]) 1
InputColumn(CPOSTAG, Input[1]) 3
InputColumn(CPOSTAG, Input[2]) 4
InputColumn(CPOSTAG, Input[3]) 5
InputColumn(CPOSTAG, Stack[1]) 6
*/
	val t = getTreeForFile(inputFile)//, List(2,1,3,4,5,6)

    println(t)
    printDotFileFromTree(t)
  
    
    
  }
  
  def printDotFileFromTree(t:Tree) = {

    
    //Create the content for a dot file
    
    println("DOT file")
    

println("""
digraph G
   {

node [	fontsize = 10 ];
""");
    
    def createDotFileNodeStructure(t:Tree, l:Int ,parentNodeName:String) {
      
      val Tree(_, _, _, branches) = t
      var count = 0
      for(b <- branches){
        
        val Tree(liblinearAccuracy, divisionAccuracy, fileSize, _) = b
        //Define b
        val nodeName = parentNodeName+ "_nl" + l + "b" + count
        println(nodeName + " ;")
        //Define bs label
        println(nodeName + " ["+ (if(liblinearAccuracy>divisionAccuracy) "color = red" else "")  +" label=\""+ (if(b.isInstanceOf[OtherTree]) "OTHER " else "")  +"l:"+liblinearAccuracy + "%\\nd:"+ divisionAccuracy+"%\\ns:"+fileSize  +"\"] ;")
        //if(liblinearAccuracy>divisionAccuracy)//if it is a leaf node
          
        //Connect parent to node name
        println(parentNodeName + " -> " + nodeName + " ;" )
        val Tree(_, _, _, bsBranches) = t
        //Define all childrens
        createDotFileNodeStructure(b,l+1, nodeName)
        count+=1
      }
      

    }
      val Tree(liblinearAccuracyR, divisionAccuracyR, fileSizeR, branchesR) = t
      //Define parent
      println("root ;")
      println("root [label=\"l:"+liblinearAccuracyR + "%\\nd:"+ divisionAccuracyR+"%\\ns:"+fileSizeR  +"\"] ;")
      
      createDotFileNodeStructure(t,1, "root") 
      
      println("}")
  }
  
  def getTreeForFile(inputFile:String):Tree = {
    
	  val divideByList = BestSplitListCreation.getGainRatioList(inputFile)
	  
    println("Node file=" +inputFile + " divide by=" +divideByList.mkString(","))
    
    //1. Train it with cross validation
    
    //Convert to Liblinear
    
    val p = Runtime.getRuntime.exec(
      "java -Xmx500M -cp scala/lib/scala-library.jar:. experiment2/ConvertFeautureVectorWithUniqueFeauturesToLibLinearFormat " 
      + inputFile)
    
    val source = Source.fromInputStream(p.getInputStream)

    val liblinearTrainingFileName = inputFile + "_liblinear"
    val writer = new FileWriter(liblinearTrainingFileName)
    var size=0
    for(line <- source.getLines()){
      writer.write(line + "\n")
      size+=1
    }
    
    writer.close()
    
    //Do the training and collect the result
    
    val pTrain = Runtime.getRuntime().exec("./train -s 4 -c 0.1 -v 10 " + liblinearTrainingFileName)
    
    val libLinearSource = Source.fromInputStream(pTrain.getInputStream)
    
    var procentageCorrectLibLinear=0.0
       for(line <- libLinearSource.getLines()){

    	  if(line.contains("Cross Validation Accuracy")){


              val tf = """.*=\s*(\d*\.\d*|\d*)%""".r
    
    object ProcentageFormatted {
      def unapply(line: String): Option[Double] =
        for (List(mainPart) <- tf.unapplySeq(line))
          yield (mainPart.toDouble)
    }
          

    procentageCorrectLibLinear=try{
      val ProcentageFormatted(procentageCorrect)=line.trim
      procentageCorrect
    }catch{
      case _:Exception => 0.0
    }
    
    
    

 
    
    
    
    }
       }
       
       //2. Divide by the top divide by column in the list
       
       val divideBy::divideByListNewTmp = divideByList
       
       val divideByListNew = divideByListNewTmp.map(
         (value)=>
           if (value>divideBy) value-1
           else value)
       
       if(divideByListNew!=Nil && size>1000){
       DivideByColumnAndRemoveColumn.main(Array(inputFile, divideBy.toString, 1000.toString))
       
       //3. Do recursive training on all parts and get the total accuracy
       
       val filesPrefix = inputFile + "_c"+divideBy +"_t"+1000 + "_cat"
       
       var index = 0
       var childrenFileNames = Nil:List[String]
       while(new File(filesPrefix+index).exists){
      
        childrenFileNames = (filesPrefix+index)::childrenFileNames
        
        
        index+=1
       }
       
       
       
       val childrenTmp = 
         if(index>1) 
          childrenFileNames.map(childFileName=>getTreeForFile(childFileName))
         else
            Nil
            
            
       val otherExists = (new File(inputFile + "_other_exists")).exists
       

       val children = childrenTmp match {
         case other::rest if (otherExists)=>OtherTree(other.liblinearAccuracy, other.divisionAccuracy, other.fileSize, other.branches)::rest
         case elements=>elements
       }
       

       
       //Get the total accuracy of the children
       
       val totalChildrenAccuracy =
       children.foldLeft(0.0) ((totalAccuracy, tree)=>
         tree match {
           case Tree(liblinearAccuracy, divisionAccuracy, fileSize, _)  if(liblinearAccuracy>divisionAccuracy)=>
             totalAccuracy + liblinearAccuracy*(fileSize.toDouble/size)
           case Tree(_, divisionAccuracy, fileSize, _)  =>
             totalAccuracy + divisionAccuracy*(fileSize.toDouble/size)
         }
       )
       
       if(totalChildrenAccuracy> procentageCorrectLibLinear)
         Tree(procentageCorrectLibLinear, totalChildrenAccuracy, size, children)
       else
         Tree(procentageCorrectLibLinear, totalChildrenAccuracy, size, Nil)
       
    }else
         Tree(procentageCorrectLibLinear, 0.0, size, Nil)
  }
  
  

}
