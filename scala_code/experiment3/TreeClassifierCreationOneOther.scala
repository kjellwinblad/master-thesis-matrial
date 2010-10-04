package experiment3

import experiment2._
import scala.io.Source
import java.io.FileWriter
import java.io.File



object TreeClassifierCreationOneOther extends Application{

  
  case class Tree()
  
  case class TreeNode(val liblinearAccuracy:Double, val divisionAccuracy:Double, val fileSize:Int, val branches:List[Tree]) extends Tree
  
  case class OtherTreeNode(val fileName:String) extends Tree
  
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
    val otherFileName =  inputFile + "_treeClassifierOther"
	val t = getTreeForFile(inputFile,otherFileName, List(2,1,3,4,5,6))

 
    val otherFileNameOutputWriter = new FileWriter(otherFileName, true)
 
    def collectOtherFiles(t:Tree) {
      t match{
        case OtherTreeNode(fileName) => {
          val otherSource = Source.fromFile(new File(fileName))
          println("Other node found")
          for(line <- otherSource.getLines())
            otherFileNameOutputWriter.write(line + "\n")
        } 
        case TreeNode(_, _, _, branches) => {
          branches.map(collectOtherFiles(_))
          ()
        }
      }
      
    }
    collectOtherFiles(t) 
    
    otherFileNameOutputWriter.close()
    
    
    val (otherFileSize, otherProcentageCorrectLibLinear)  = try{
	 getLiblinearCorrectnessForFile(otherFileName)
    }catch{
      case _:Exception =>{ println("No other file collected")
      (0,0.0)                     
      }
    }
    println(t)
    println("otherFileSize " + otherFileSize+ " otherProcentageCorrectLibLinear "+ otherProcentageCorrectLibLinear)
    
    //Create the content for a dot file
    
    println("DOT file")
    

println("""
digraph G
   {

node [	fontsize = 10 ];
""");
    
    def createDotFileNodeStructure(t:Tree, l:Int ,parentNodeName:String) {
      
      val TreeNode(_, _, _, branches) = t
      var count = 0
      for(b <- branches  if(b.isInstanceOf[TreeNode])){
        
        val TreeNode(liblinearAccuracy, divisionAccuracy, fileSize, _) = b
        //Define b
        val nodeName = parentNodeName+ "_nl" + l + "b" + count
        println(nodeName + " ;")
        //Define bs label
        println(nodeName + " ["+ (if(liblinearAccuracy>divisionAccuracy) "color = red" else "")  +" label=\"l:"+liblinearAccuracy + "%\\nd:"+ divisionAccuracy+"%\\ns:"+fileSize  +"\"] ;")
        //if(liblinearAccuracy>divisionAccuracy)//if it is a leaf node
          
        //Connect parent to node name
        println(parentNodeName + " -> " + nodeName + " ;" )
        val TreeNode(_, _, _, bsBranches) = t
        //Define all childrens
        createDotFileNodeStructure(b,l+1, nodeName)
        count+=1
      }
      

    }
      val TreeNode(liblinearAccuracyR, divisionAccuracyR, fileSizeR, branchesR) = t
      //Define parent
      println("root ;")
      println("root [label=\"l:"+liblinearAccuracyR + "%\\nd:"+ divisionAccuracyR+"%\\ns:"+fileSizeR  +" do:"+((divisionAccuracyR*fileSizeR + otherProcentageCorrectLibLinear*otherFileSize)/(fileSizeR + otherFileSize))  +"%\"] ;")
      
      println("other ;")
      println("other [label=\"OTHER l:"+otherProcentageCorrectLibLinear + "%\\nd:"+ 0+"%\\ns:"+otherFileSize  +"\"] ;")
      
      println("root -> other ;" )
      
      createDotFileNodeStructure(t,1, "root") 
      
      println("}")
    
    
  }
  
  def getLiblinearCorrectnessForFile(inputFile:String) = {
        
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
       (size, procentageCorrectLibLinear)
  }
  
  
  def getTreeForFile(inputFile:String, otherFileName:String, divideByList:List[Int]):Tree = {
    
    println("Node file=" +inputFile + " divide by=" +divideByList.mkString(","))
    
        //1. Train it with cross validation

       val (size, procentageCorrectLibLinear) = getLiblinearCorrectnessForFile(inputFile)
       
       //2. Divide by the top divide by column in the list
       
       val divideBy::divideByListNew = divideByList
       
       if(divideByListNew!=Nil && size>1000){
       
         DivideByColumn.main(Array(inputFile, divideBy.toString, 1000.toString))
       
         //3. Do recursive training on all parts and get the total accuracy
       
         val filesPrefix = inputFile + "_c"+divideBy +"_t"+1000 + "_cat"

         

       
         var index = 0
         var childrenFileNames = Nil:List[String]
         var otherTreeNode = Tree()
         val dontCreateSeparateOtherFile = !(new File(inputFile + "_other_exists")).exists
         var otherSize = 0
         while(new File(filesPrefix+index).exists){
        
          if(dontCreateSeparateOtherFile || new File(filesPrefix+(index+1)).exists)
          	childrenFileNames = (filesPrefix+index)::childrenFileNames
          else{
            //put it into special container
            otherTreeNode = OtherTreeNode(filesPrefix+index)
            //Get the size of the other node
            val otherNodeInputSource = Source.fromFile(new File(filesPrefix+index))
            for(line <- otherNodeInputSource.getLines())
            	otherSize+=1
            
          }
        
        
          index+=1
         }
       

       
       
       val childrenWithoutOther = 
         if(index>1) 
          childrenFileNames.map(childFileName=>getTreeForFile(childFileName, otherFileName ,divideByListNew))
         else
            Nil
       
       val children = 
         if (otherTreeNode.isInstanceOf[OtherTreeNode]) 
           otherTreeNode::childrenWithoutOther 
         else childrenWithoutOther
       
       //Get the total accuracy of the children
       
      val totalChildrenSize =  
        children.foldLeft(0) ((totalSize, tree)=>
         tree match {
           case TreeNode(_, _, fileSize, _) =>
             totalSize + fileSize
           case _ => totalSize
         }
       )
         
       val totalChildrenAccuracy =
       children.foldLeft(0.0) ((totalAccuracy, tree)=>
         tree match {
           case TreeNode(liblinearAccuracy, divisionAccuracy, fileSize, _)  if(liblinearAccuracy>divisionAccuracy)=>
             totalAccuracy + liblinearAccuracy*(fileSize.toDouble/totalChildrenSize)
           case TreeNode(_, divisionAccuracy, fileSize, _)  =>
             totalAccuracy + divisionAccuracy*(fileSize.toDouble/totalChildrenSize)
           case _ => totalAccuracy
         }
       )
       
       if(totalChildrenAccuracy> procentageCorrectLibLinear)
         TreeNode(procentageCorrectLibLinear, totalChildrenAccuracy, totalChildrenSize, children)
       else
         TreeNode(procentageCorrectLibLinear, totalChildrenAccuracy, size, Nil)
       
    }else
         TreeNode(procentageCorrectLibLinear, 0.0, size, Nil)
  }
  
  

}
