package tools

import scala.io.Source
import java.io.File

object FetchSentenceAndWordCountFromConll {
	
	def main(args:Array[String]){

		val src =Source.fromFile(new File(args(0)))
				
		val (sentences,tokens, specialSymbols) = src.getLines().foldLeft((0,0,0)) {
			case ((sentences,tokens, specialSymbols),line) => 
				if(line.contains(",") || 
				   line.contains(".") || 
				   line.contains("!") || 
				   line.contains("?")) (sentences,tokens, specialSymbols+1)
				else if (line.trim.length!=0) (sentences,tokens+1, specialSymbols)
				else (sentences+1,tokens, specialSymbols)
		}
		
		println("Information for ConLL file: " + args(0))
		println("Number of sentences: " + sentences)
		println("Number of tokens: " + tokens)
		println("Number of words: " + (tokens-specialSymbols))
		println("Number of special symbols: " + specialSymbols)
				
				
		
	}

}