#!/bin/sh


#chinese_sinica_train.conll                     CoNLL2009-ST-English-train.CONVERTED-GOLD.conll  german_tiger_train.conll
#CoNLL2009-ST-Czech-train.CONVERTED-GOLD.conll  CoNLL2009-ST-German-train.CONVERTED-GOLD.conll   swedish_talbanken05_train.conll

#Liblinear no div
java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment1_liblinear_no_division.xml exp1swe swedish_talbanken05_train.conll 2:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment1_liblinear_no_division.xml exp1chi chinese_sinica_train.conll 4:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment1_liblinear_no_division.xml exp1ger_small german_tiger_train.conll 6:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment1_liblinear_no_division.xml exp1cze CoNLL2009-ST-Czech-train.CONVERTED-GOLD.conll 8:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment1_liblinear_no_division.xml exp1ger_big CoNLL2009-ST-German-train.CONVERTED-GOLD.conll 10:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment1_liblinear_no_division.xml exp1eng CoNLL2009-ST-English-train.CONVERTED-GOLD.conll 12:0:0 16000 8


#Liblinear with div
java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment2_liblinear_with_division.xml exp2swe swedish_talbanken05_train.conll 2:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment2_liblinear_with_division.xml exp2chi chinese_sinica_train.conll 4:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment2_liblinear_with_division.xml exp2ger_small german_tiger_train.conll 6:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment2_liblinear_with_division.xml exp2cze CoNLL2009-ST-Czech-train.CONVERTED-GOLD.conll 8:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment2_liblinear_with_division.xml exp2ger_big CoNLL2009-ST-German-train.CONVERTED-GOLD.conll 10:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment2_liblinear_with_division.xml exp2eng CoNLL2009-ST-English-train.CONVERTED-GOLD.conll 12:0:0 16000 8

