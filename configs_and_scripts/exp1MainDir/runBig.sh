#!/bin/sh


#chinese_sinica_train.conll                     CoNLL2009-ST-English-train.CONVERTED-GOLD.conll  german_tiger_train.conll
#CoNLL2009-ST-Czech-train.CONVERTED-GOLD.conll  CoNLL2009-ST-German-train.CONVERTED-GOLD.conll   swedish_talbanken05_train.conll

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment5_liblinear_decision_tree_test.xml exp5swe swedish_talbanken05_train.conll 2:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment5_liblinear_decision_tree_test.xml exp5chi chinese_sinica_train.conll 5:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment5_liblinear_decision_tree_test.xml exp5ger_small german_tiger_train.conll 6:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment5_liblinear_decision_tree_test.xml exp5cze CoNLL2009-ST-Czech-train.CONVERTED-GOLD.conll 8:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment5_liblinear_decision_tree_test.xml exp5ger_big CoNLL2009-ST-German-train.CONVERTED-GOLD.conll 10:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment5_liblinear_decision_tree_test.xml exp5eng CoNLL2009-ST-English-train.CONVERTED-GOLD.conll 12:0:0 16000 8
