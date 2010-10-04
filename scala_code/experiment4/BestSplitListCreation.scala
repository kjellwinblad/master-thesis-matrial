package experiment4

import java.util.Scanner
import scala.io.Source
import scala.util.Sorting
import java.io.File

import scala.math._

object BestSplitListCreation {
	def main(args : Array[String]) : Unit = {

			val fileName = args(0)

			val s = Source.fromFile(new File(fileName))
			val buf = new StringBuffer()
			for(line <- s.getLines()) 
				buf.append(line + "\n")

				val fileContent = buf.toString

				val list = getBestSplitList(fileContent)

				println(list)
	}


	def getGainRatioList(fileName:String) = {
		val s = Source.fromFile(new File(fileName))
		val buf = new StringBuffer()
		for(line <- s.getLines()) 
			buf.append(line + "\n")

			val fileContent = buf.toString

			val lists = BestSplitListCreation.getBestSplitList(fileContent)

			val (_, gainRatioListWithValues) = lists

			val gainRatioList = gainRatioListWithValues.map(_._1 + 1)


			gainRatioList
	}

	def getBestSplitList(fileContent:String) = {





		// for all columns find the nodes


		val columnScanner = new Scanner(fileContent.take(fileContent.indexOf("\n")) )

		// We are not interested in the first one
		columnScanner.next()
		var splitColumn = 0
		var splitValuesInformationGain = Nil:List[(Int, Double)]
		var splitValuesGainRatio = Nil:List[(Int, Double)]                                          	

		val rootEnt = rootEntropy(fileContent)

		println("rootEnt " + rootEnt);
		println("log2 20 " + log2(20));
		
		while(columnScanner.hasNext){
		      columnScanner.next()

		     val instancesPlusNodes = findNodes(splitColumn, fileContent)

		     val (nrOfInstances, nodes) = instancesPlusNodes
		     //Calculate split value
		     val informationGain = getSplitValueInformationGain(nrOfInstances, rootEnt,nodes)
		     splitValuesInformationGain = (splitColumn, informationGain)::splitValuesInformationGain
		     val gainRatio = getSplitValueGainRatio(nrOfInstances, nodes, informationGain)
		     splitValuesGainRatio = (splitColumn, gainRatio)::splitValuesGainRatio
		     splitColumn+=1
		}

		val comparator = (a:(Int,Double),b:(Int,Double))=>{
			val (a1, a2) = a
			val (b1, b2) = b

			a2<b2
		}  

		(splitValuesInformationGain.sortWith(comparator),
				splitValuesGainRatio.sortWith(comparator))
	}

	def elementsInNode(node:(String, List[(Int,Int)])) = {
		val (_, nodeList) = node

		nodeList.foldLeft(0)((a,b)=>{
			val (_, count) = b
			a+count
		})
	}

	def log2(n:Double)=log(n)/log(2.0)

	def getSplitValueGainRatio(nrOfInstances:Int, nodes:List[(String, List[(Int,Int)])], informationGain:Double):Double ={

		val splitInfo = nodes.foldLeft(0.0)((total, node)=>{

			val elementsInTheNode = elementsInNode(node)

			val fraction=elementsInTheNode.toDouble / nrOfInstances

			total +  fraction*log2(fraction)

		})

		informationGain/splitInfo

	}


	def getSplitValueInformationGain(nrOfInstances:Int,rootEntropy:Double, nodes:List[(String, List[(Int,Int)])]):Double ={


		val value = nodes.foldLeft(0.0)((sum,node)=>{

			def log2(n:Double)=log(n)/log(2.0)

			val elementsInTheNode = elementsInNode(node)
			val (nodeId,nodeElements)=node


			val impurityMesure = -nodeElements.foldLeft(0.0)((sum2,nodeElement)=>{
				val (_,nodeElementCount)=nodeElement
				val fractionOfNodeBelongingToClass = nodeElementCount.toDouble/elementsInTheNode


				sum2+fractionOfNodeBelongingToClass*log2(fractionOfNodeBelongingToClass)
			})

			/*
			println("Elements in part with node id " + nodeId + " = " +elementsInTheNode.toDouble)
			println("Elements in node = " +nrOfInstances)
			println("Fration in node = " + (elementsInTheNode.toDouble/nrOfInstances))
			println("Impurity mesure = " + impurityMesure)
			println()
			 */
			sum + (elementsInTheNode.toDouble/nrOfInstances)*impurityMesure


		})

		rootEntropy - value
	}


	def rootEntropy(fileContent:String):Double={

		val source = Source.fromString(fileContent)

		var nrOfInstances = 0
		val classToNrOfMap = scala.collection.mutable.HashMap[Int,Int]()
		for(line <-source.getLines()){

			val classOnLine = (new Scanner(line).nextInt())

			classToNrOfMap.get(classOnLine) match {
			case Some(nrOf)=> classToNrOfMap.update(classOnLine, nrOf+1)
			case None => classToNrOfMap.put(classOnLine, 1)
			}

			nrOfInstances+=1

		}

		val entropy = - classToNrOfMap.foldLeft(0.0)((total, classCountPair)=>{
			val (_,count) = classCountPair

			val fraction = count.toDouble / nrOfInstances

			total + fraction*log2(fraction)
		})

		entropy

	}


	def findNodes(splitColumn:Int, fileContent:String) = {

		//find class column value pairs
		val contentSouce = Source.fromString(fileContent)

		var instancesCounter = 0
		val attributeClassPairs =       
			(for(line <- contentSouce.getLines()) yield{

				val columnScanner = new Scanner(line.trim)
				columnScanner.useDelimiter("""\s""")

				val classId = columnScanner.nextInt()

				//Scan to the column value
				var counter = 0
				var columnValue:String=null
				while(counter<=splitColumn){
					columnValue = columnScanner.next()
					counter+=1
				}

				instancesCounter+=1

				(columnValue, classId)

			}).toList.toArray




			class OrderedPair extends Ordering[(String, Int)]{
			override def compare(val1: (String, Int),val2: (String, Int)) =
				if(val1._1 != val2._1) 
					val1._1.compare(val2._1)
					else
						val1._2 - val2._2
		}


		Sorting.quickSort(attributeClassPairs) (new OrderedPair()) 








		val (columnValue,classValue) = attributeClassPairs(0)

		(instancesCounter, fromSortedClassValuePairsToNodeMap(
				attributeClassPairs.toList,
				Nil,
				columnValue,
				classValue,
				0,
				Nil))

	}


	def fromSortedClassValuePairsToNodeMap(
			sortedValueClassPairs:List[(String, Int)],
			currentNodeList:List[(String, List[(Int,Int)])],
			currentColumnValue:String,
			currentClassValue:Int,
			currentColumnClassValueCount:Int,
			currentClassCountList:List[(Int,Int)]):List[(String, List[(Int,Int)])] = {
		sortedValueClassPairs match {
		case Nil => 
		(currentColumnValue, 
				(currentClassValue,currentColumnClassValueCount)::currentClassCountList)::currentNodeList
		case (eColumnValue,eClass)::rest if( eColumnValue==currentColumnValue && eClass==currentClassValue) => 
		fromSortedClassValuePairsToNodeMap(rest, currentNodeList, eColumnValue, eClass, currentColumnClassValueCount+1,currentClassCountList)
		case (eColumnValue,eClass)::rest if(eColumnValue==currentColumnValue) => 
		fromSortedClassValuePairsToNodeMap(rest, currentNodeList, eColumnValue, eClass, 1,(currentClassValue,currentColumnClassValueCount)::currentClassCountList)
		case (eColumnValue,eClass)::rest =>
		fromSortedClassValuePairsToNodeMap(
				(eColumnValue,eClass)::rest, 
				(currentColumnValue, (currentClassValue,currentColumnClassValueCount)::currentClassCountList)::currentNodeList, 
				eColumnValue, eClass, 0,Nil)
		}
	}



}







import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers


class BestSplitListCreationSpec extends FlatSpec with ShouldMatchers {

	"A best split list" should "return a list with the correct format INFORMATION GAIN" in {
		val (nodes, _) = BestSplitListCreation.getBestSplitList(
				"""1 1 1 1
				2 2 1 1
				1 1 1 1
				2 2 1 1
		3 1 2 1""")


		nodes.map((pair)=>pair._1) should equal (List(0,1,2))

	}

	"A best split list" should "return a list with the correct format in all situations INFORMATION GAIN" in {
		val (nodes, _)  = BestSplitListCreation.getBestSplitList(
				"""1 1 1 1 1
				2 2 1 1 2
				1 1 1 1 1
				2 2 1 1 2
		3 1 2 1 3""")
		nodes.map((pair)=>pair._1) should equal (List(3,0,1,2))

	}

	"A best split list" should "return a list with the correct format GAIN RATIO" in {

		val (k, nodes) = BestSplitListCreation.getBestSplitList(
				"""1 1 1 1
				2 2 1 1
				1 1 1 1
				2 2 1 1
		3 1 2 1""")


		nodes.map((pair)=>pair._1) should equal (List(0,1,2))

	}

	"A best split list" should "return a list with the correct format in all situations GAIN RATIO" in {
		val (_, nodes)  = BestSplitListCreation.getBestSplitList(
				"""1 1 1 1 1
				2 2 1 1 2
				1 1 1 1 1
				2 2 1 1 2
		3 1 2 1 3""")
		
		nodes.map((pair)=>pair._1) should equal (List(3,0,1,2))

	}


	"findNodes" should "return a correctly formated node list" in {
		val nodes = BestSplitListCreation.findNodes(0, 
				"""1 1 1 1
				2 2 1 1
				1 1 1 1 
				2 2 1 1
		3 1 2 1""") 

		nodes should equal (5,(List(("1",List((3,1),(1,2))),("2",List((2,2)))).reverse))

		val nodes2 = BestSplitListCreation.findNodes(0, 
				"""1 1 1 1
				2 2 1 1
				1 1 1 1
				2 2 1 1 
				3 1 2 1
				1 2 1 1
				1 2 1 1
				1 2 1 1
				1 2 1 1
				1 2 1 1
				1 2 1 1
				1 2 1 1
				1 3 1 1
		1 3 1 1""") 

		nodes2 should equal (14,(List(("1",List((1,2),(3,1)).reverse),("2",List((1,7),(2,2)).reverse),("3",List((1,2)))).reverse))
	}


}

