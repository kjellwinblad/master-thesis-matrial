#!/bin/sh


./scala/bin/scalac -deprecation -classpath ../lib/scalatest-1.0.1-for-scala-2.8.0.RC1-SNAPSHOT.jar -sourcepath ../src/ ../src/experiment4/BestSplitListCreation.scala ../src/experiment4/TreeClassifierCreationWithCalculatedDivisionOrder.scala ../src/experiment2/ConvertFeautureVectorWithUniqueFeauturesToLibLinearFormat.scala ../src/experiment3/TreeClassifierCreationRemoveConstantColumn.scala ../src/experiment3/TreeClassifierCreation.scala ../src/experiment4/TreeClassifierCreationCalculateDivisionOrderInEveryNode.scala ../src/experiment4/TreeClassifierCreationWithCalculatedDivisionOrderFirstLibLinearParameters.scala ../src/experiment3/TreeClassifierCreationFirstLiblinearParameters.scala ../src/experiment3/TreeClassifierCreationOneOther.scala
