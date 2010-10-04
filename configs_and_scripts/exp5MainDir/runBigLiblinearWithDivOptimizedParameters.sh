#!/bin/sh


#chinese_sinica_train.conll                     CoNLL2009-ST-English-train.CONVERTED-GOLD.conll  german_tiger_train.conll
#CoNLL2009-ST-Czech-train.CONVERTED-GOLD.conll  CoNLL2009-ST-German-train.CONVERTED-GOLD.conll   swedish_talbanken05_train.conll

#Liblinear no div
java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutorMoreMemory \
 experiment18SWE_liblinear_with_division.xml exp18swe swedish_talbanken05_train.conll 2:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutorMoreMemory \
 experiment18SWE_liblinear_with_division.xml exp18chi chinese_sinica_train.conll 4:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutorMoreMemory \
 experiment18SWE_liblinear_with_division.xml exp18ger_small german_tiger_train.conll 6:0:0 16000 8
