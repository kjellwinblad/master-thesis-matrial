#!/bin/sh


#chinese_sinica_train.conll                     CoNLL2009-ST-English-train.CONVERTED-GOLD.conll  german_tiger_train.conll
#CoNLL2009-ST-Czech-train.CONVERTED-GOLD.conll  CoNLL2009-ST-German-train.CONVERTED-GOLD.conll   swedish_talbanken05_train.conll

#Liblinear no div
java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment8_liblinear_automatic_div_with_treshold_50.xml exp8swe swedish_talbanken05_train.conll 2:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment8_liblinear_automatic_div_with_treshold_50.xml exp8chi chinese_sinica_train.conll 4:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment8_liblinear_automatic_div_with_treshold_50.xml exp8ger_small german_tiger_train.conll 6:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment8_liblinear_automatic_div_with_treshold_50.xml exp8cze CoNLL2009-ST-Czech-train.CONVERTED-GOLD.conll 8:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment8_liblinear_automatic_div_with_treshold_50.xml exp8ger_big CoNLL2009-ST-German-train.CONVERTED-GOLD.conll 10:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment8_liblinear_automatic_div_with_treshold_50.xml exp8eng CoNLL2009-ST-English-train.CONVERTED-GOLD.conll 12:0:0 16000 8



