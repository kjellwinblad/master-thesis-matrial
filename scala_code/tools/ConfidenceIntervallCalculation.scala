import scala.math._

object ConfidenceIntervallCalculation extends Application {

  def solve(f: ((Double) => Double), x: Double, start: Double = -10, end: Double = 10, precision: Double = 0.00000001) = {

    def solveInt(start: Double, end: Double): Double =
      if ((end - start) < precision)
        start + (end - start) / 2
      else {

        val middle = start + (end - start) / 2

        val res = f(middle)

        if (x < res)
          solveInt(start, middle)
        else
          solveInt(middle, end)
      }

    solveInt(start, end)
  }



  def numInt(f: ((Double) => Double), from: Double, to: Double, nrOfSlices: Int = 200): Double =
    if (from == to)
      0.0
    else {

      val stepSize = (to - from) / (if (nrOfSlices == 0) 1.0 else nrOfSlices)

      val sliceIntervals = new Iterable[(Double, Double)] {
        val iterator = new Iterator[(Double, Double)] {
          private var currentStart = from

          def next = {

            val start = currentStart

            val end = currentStart + stepSize

            currentStart = end

            (start, end)
          }

          def hasNext = currentStart <= to
        }
      }

      sliceIntervals.foldLeft(0.0)((sum, interval) => {

        val (from, to) = interval

        val value1 = f(from)

        val value2 = f(to)

        val area =
          if ((value1 < 0 && value2 > 0) || (value2 < 0 && value1 > 0)) {
            0.0 //Approximation
          } else if (value1 > 0) {

            val baseArea = value1.min(value2) * stepSize

            val topArea = (abs(value1 - value2) * stepSize) / 2

            baseArea + topArea

          } else {

            val baseArea = value1.max(value2) * stepSize

            val topArea = -(abs(value1 - value2) * stepSize) / 2

            baseArea + topArea

          }

        area + sum

      })
    }

  def normalDistribution(x: Double, u: Double = 0.0, o: Double = 1.0) = {

    def f(x: Double) = pow(E, -pow(x - u, 2.0) / (2 * pow(o, 2.0))) / sqrt(2 * Pi * pow(o, 2.0))

    numInt(f, -10.0, x)

  }

  def zADiv2Old(confidence: Double) = {

    confidence match {
      case 0.99 => 2.58
      case 0.98 => 2.33
      case 0.95 => 1.96
      case 0.9 => 1.65
      case 0.8 => 1.28
      case 0.7 => 1.04
      case 0.5 => 0.67
      case _ => throw new IllegalArgumentException("zA/2 is only known for some confidences.")
    }
  }

  def zADiv2(confidence: Double) = {
    val u = 0.0
    val o = 1.0
    def f(x: Double) = pow(E, -pow(x - u, 2.0) / (2 * pow(o, 2.0))) / sqrt(2 * Pi * pow(o, 2.0))

    def y(x: Double) = numInt(f, 0.0, x)

    solve(y, confidence / 2)

  }

//  println(zADiv2(0.99))

 // println("0.99 " + zADiv2(0.99))
 // println("0.98 " + zADiv2(0.98))
 // println("0.95 " + zADiv2(0.95))
 // println("0.9 " + zADiv2(0.9))
 // println("0.8735666756504656 " + zADiv2(0.8735666756504656))
 // println("0.8 " + zADiv2(0.8))
//  println("0.7 " + zADiv2(0.7))
//  println("0.5 " + zADiv2(0.5))
  def confidenceInterval(n: Double, acc: Double, confidence: Double) = {

    val zAD2P = zADiv2(confidence)

    val zAD2P2 = pow(zAD2P, 2.0)

    def interval(sign: Double) =
      (2 * n * acc + zAD2P2 + sign * zAD2P * sqrt(zAD2P2 + 4 * n * acc - 4 * n * pow(acc, 2.0))) / (2 * (n + zAD2P2))

    (interval(-1), interval(1))
  }

  //println(confidenceInterval(32313, 0.909162, 0.95))

  def compareTwoModels(n1: Int, n2: Int, e1: Double, e2: Double, confidence: Double) = {

    val d = abs(e1 - e2)

    val variance = (e1 * (1 - e1)) / n1 + (e2 * (1 - e2)) / n2

    def interval(sign: Double) =
      d + sign * zADiv2(confidence) * sqrt(variance)

    (interval(-1), interval(1))
  }

  def greaterThanMaxConf(n1: Int, n2: Int, e1: Double, e2: Double) = {

    val d = abs(e1 - e2)

    val variancePow2 = (e1 * (1 - e1)) / n1 + (e2 * (1 - e2)) / n2

    //println(d/sqrt(variancePow2))
    solve(zADiv2, d/sqrt(variancePow2))

    
  }
  
 // println(compareTwoModels(32313, 32313, 1 - 0.8228, 1 - 0.8028, 0.95))
 // println(compareTwoModels(30, 5000, 0.15, 0.25, 0.80))

 // println("GReater than test " + greaterThanMaxConf(30, 5000, 0.15, 0.25))
 // println("zADiv2(0.8735666756504656) " + zADiv2(0.8735666756504656))
 // println("zADiv2(0.936) " + zADiv2(0.936))
  /*
[kjell@grad1 exp2MainDir]$ cat output_unique_swe | wc -l
323136
[kjell@grad1 exp2MainDir]$ cat output_unique_chi | wc -l
511869
[kjell@grad1 exp2MainDir]$ cat output_unique_ger | wc -l
1188558
[kjell@grad1 exp2MainDir]$ cat output_unique_cze_stack_lazy | wc -l
1331838
[kjell@grad1 exp2MainDir]$ cat output_unique_cze_stack_proj | wc -l
1305088
[kjell@grad1 exp2MainDir]$ cat output_unique_eng_stack_lazy | wc -l
1925826
[kjell@grad1 exp2MainDir]$ cat output_unique_eng_stack_proj | wc -l
1916334
   */
  

  
println("Table 4.4 between nodiv 1 div")
println("SWE       " + greaterThanMaxConf(323136/10, 323136/10, 1 - 0.909162, 1 - 0.911496))
println("CHI       " + greaterThanMaxConf(511869/10, 511869/10, 1 - 0.920441, 1 - 0.920889))
println("GER       " + greaterThanMaxConf(1188558/10, 1188558/10, 1 - 0.910386, 1 - 0.926778))
println("CZE LAZY  " + greaterThanMaxConf(1331838/10, 1331838/10, 1 - 0.899787, 1 - 0.911751))
println("CHI STACK " + greaterThanMaxConf(1305088/10, 1305088/10, 1 - 0.897828, 1 - 0.910158))
println("ENG LAZY  " + greaterThanMaxConf(1925826/10, 1925826/10, 1 - 0.943736, 1 - 0.947557))
println("ENG STACK " + greaterThanMaxConf(1916334/10, 1916334/10, 1 - 0.944000, 1 - 0.947626))

println("Table 4.4 between 1 div and 2 div")
println("SWE       " + greaterThanMaxConf(323136/10, 323136/10, 1 - 0.911496, 1 - 0.909787))
println("CHI       " + greaterThanMaxConf(511869/10, 511869/10, 1 - 0.920889, 1 - 0.921678))
println("GER       " + greaterThanMaxConf(1188558/10, 1188558/10, 1 - 0.926778, 1 - 0.927075))
println("CZE LAZY  " + greaterThanMaxConf(1331838/10, 1331838/10, 1 - 0.911751, 1 - 0.918522))
println("CHI STACK " + greaterThanMaxConf(1305088/10, 1305088/10, 1 - 0.910158, 1 - 0.916156))
println("ENG LAZY  " + greaterThanMaxConf(1925826/10, 1925826/10, 1 - 0.947557, 1 - 0.949537))
println("ENG STACK " + greaterThanMaxConf(1916334/10, 1916334/10, 1 - 0.947626, 1 - 0.950080))

println("Table 4.7 between 2 div and Gain Ratio")
println("SWE       " + greaterThanMaxConf(323136/10, 323136/10,   1 - 0.909787, 1 - 0.909472))
println("CHI       " + greaterThanMaxConf(511869/10, 511869/10,   1 - 0.921678, 1 - 0.921349))
println("GER       " + greaterThanMaxConf(1188558/10, 1188558/10, 1 - 0.927075, 1 - 0.929364))
println("CZE LAZY  " + greaterThanMaxConf(1331838/10, 1331838/10, 1 - 0.918522, 1 - 0.919473))
println("CHI STACK " + greaterThanMaxConf(1305088/10, 1305088/10, 1 - 0.916156, 1 - 0.917707))
println("ENG LAZY  " + greaterThanMaxConf(1925826/10, 1925826/10, 1 - 0.949537, 1 - 0.951203))
println("ENG STACK " + greaterThanMaxConf(1916334/10, 1916334/10, 1 - 0.950080, 1 - 0.951578))

println("Table 4.7 between 2 div and Intuitive")
println("SWE       " + greaterThanMaxConf(323136/10,  323136/10,  1 - 0.909787, 1 - 0.911675))
println("CHI       " + greaterThanMaxConf(511869/10,  511869/10,  1 - 0.921678, 1 - 0.921179))
println("GER       " + greaterThanMaxConf(1188558/10, 1188558/10, 1 - 0.927075, 1 - 0.931317))
println("CZE LAZY  " + greaterThanMaxConf(1331838/10, 1331838/10, 1 - 0.918522, 1 - 0.918662))
println("CHI STACK " + greaterThanMaxConf(1305088/10, 1305088/10, 1 - 0.916156, 1 - 0.916537))
println("ENG LAZY  " + greaterThanMaxConf(1925826/10, 1925826/10, 1 - 0.949537, 1 - 0.950205))
println("ENG STACK " + greaterThanMaxConf(1916334/10, 1916334/10, 1 - 0.950080, 1 - 0.950770))

println("MaltParser Decision tree between Intuitive and Division")
println("SWE       " + greaterThanMaxConf(173466/10, 173466/10,   1 - 0.8228, 1 - 0.8218))
println("CHI       " + greaterThanMaxConf(337159/10, 337159/10,  1 - 0.8254, 1 - 0.8256))
println("GER       " + greaterThanMaxConf(625539/10, 625539/10, 1 - 0.8161, 1 - 0.8298))
println("CZE       " + greaterThanMaxConf(564446/10, 564446/10, 1 - 0.6910, 1 - 0.7006))
println("ENG       " + greaterThanMaxConf(848743/10, 848743/10, 1 - 0.8677, 1 - 0.8732))

println("MaltParser Decision tree between Division and Gain Ratio")
println("SWE       " + greaterThanMaxConf(173466/10, 173466/10,   1 - 0.8228, 1 - 0.8206))
println("CHI       " + greaterThanMaxConf(337159/10, 337159/10,  1 - 0.8254, 1 - 0.8154))
println("GER       " + greaterThanMaxConf(625539/10, 625539/10, 1 - 0.8161, 1 - 0.8116))
println("CZE       " + greaterThanMaxConf(564446/10, 564446/10, 1 - 0.6910, 1 - 0.6956))
println("ENG       " + greaterThanMaxConf(848743/10, 848743/10, 1 - 0.8677, 1 - 0.8714))

/*
Table 4.4 between nodiv 1 div
SWE       0.7018132554367185
CHI       0.20984800066798925
GER       9.999999995343387
CZE LAZY  9.999999995343387
CHI STACK 9.999999995343387
ENG LAZY  0.9999998332932591
ENG STACK 0.9999993676319718
Table 4.4 between 1 div and 2 div
SWE       0.5558592779561877
CHI       0.362316551618278
GER       0.2192334784194827
CZE LAZY  1.0000000009313226
CHI STACK 0.9999999450519681
ENG LAZY  0.9947643661871552
ENG STACK 0.9994488907977939
Table 4.7 between 2 div and Gain Ratio
SWE       0.11115868110209703
CHI       0.15561606269329786
GER       0.9699152735993266
CZE LAZY  0.6316970149055123
CHI STACK 0.8509001927450299
ENG LAZY  0.9832543274387717
ENG STACK 0.9687795722857118
Table 4.7 between 2 div and Intuitive
SWE       0.6017357716336846
CHI       0.23371759336441755
GER       0.9999473253265023
CZE LAZY  0.10540730785578489
CHI STACK 0.27483353856951
ENG LAZY  0.6601460045203567
ENG STACK 0.6760388845577836
MaltParser Decision tree between Intuitive and Division
SWE       0.19334835465997458
CHI       0.05456117447465658
GER       1.0000000009313226
CZE       0.9995718160644174
ENG       0.9992720419541001
MaltParser Decision tree between Division and Gain Ratio
SWE       0.4075423674657941
CHI       0.9993220819160342
GER       0.9601097134873271
CZE       0.9067039890214801
ENG       0.9766167728230357

 * 
 */
  
// println(compareTwoModels(32313, 32313, 1 - 0.909162, 1 - 0.911496, 0.70))

//  println(greaterThanMaxConf(30, 5000, 0.15, 0.25))
  
//  println(compareTwoModels(30, 5000, 0.15, 0.25, 0.87))
}