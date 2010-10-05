package tools

import scala.io.Source

object ProduceLatexTableDataFromVizData {
	
	def main(args:Array[String]){
		
//Input Example		
//1 6245 2991 61.77 72.70 67.57
//2 8268 2613 65.58 75.85 70.82
//3 21808 3050 70.23 79.84 74.75
//4 42453 3363 73.98 82.87 77.77
//5 106364 3775 76.40 84.48 79.97
//6 347324 3828 78.73 85.90 82.07
//7 279950 4447 80.29 86.89 83.63
//8 577951 5282 81.45 87.55 84.62
		
//Output Example
// & \textbf{TR} & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ \\
// & \textbf{TE} & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ \\
// & \textbf{AC} & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ \\ \hline
// & \textbf{AC 2} & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ \\
// & \textbf{AC 3} & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ & $80.55$ \\
		
		
		//Collect input
		
		val s = Source.fromInputStream(System.in)
		
		val input = (s.getLines().foldLeft(Nil:List[String])((list,line)=>(line::list))).reverse
		
		def readColumn(column:Int) = {
			def readColumnTmp(column:Int, input:List[String]):List[String] = input match  {
				case Nil => Nil
				case line::rest =>{				
					(line.split("""\s""")(column))::readColumnTmp(column, rest)
				}
			}
			readColumnTmp(column, input)
		}
		
		val rowsDataTmp = (for(column <- 1 to 5) yield readColumn(column)).toList
		
		//Convert the content of the first and second row to hours instead of seconds
		
		val k = (((((1.toDouble)/1000.0))/60)/60)
		
		val timeRows = rowsDataTmp.take(2).map(_.map((a)=>"%.2f".format(((((a.toDouble)/100.0))/60)/60))).toList
		
		val rowsData = timeRows ::: rowsDataTmp.drop(2)
		
		val rowNames = List("TR","TE","AC","AC 2","AC 3")
		
		for((rowName, index) <- rowNames.zipWithIndex )
			println(""" & \textbf{""" + rowName + "} & " + rowsData(index).mkString("$", "$ & $", """$ \\"""))
		
		
	}

}
