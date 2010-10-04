#!/bin/sh


#chinese_sinica_train.conll                     CoNLL2009-ST-English-train.CONVERTED-GOLD.conll  german_tiger_train.conll
#CoNLL2009-ST-Czech-train.CONVERTED-GOLD.conll  CoNLL2009-ST-German-train.CONVERTED-GOLD.conll   swedish_talbanken05_train.conll


#Run standard div

#java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutorMoreMemory \
# experiment16SWE_liblinear_decision_tree_test.xml exp16swe swedish_talbanken05_train.conll 2:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutorMoreMemory \
 experiment16CHI_liblinear_decision_tree_test.xml exp16chi chinese_sinica_train.conll 5:0:0 16000 8

#java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutorMoreMemory \
# experiment16GER_liblinear_decision_tree_test.xml exp16ger_small german_tiger_train.conll 6:0:0 16000 8


#Run automatic div

#java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutorMoreMemory \
# experiment17SWE_liblinear_cross_validation_automatic_div.xml exp17swe swedish_talbanken05_train.conll 2:0:0 16000 8

#java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutorMoreMemory \
# experiment17CHI_liblinear_cross_validation_automatic_div.xml exp17chi chinese_sinica_train.conll 5:0:0 16000 8

#java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutorMoreMemory \
# experiment17GER_liblinear_cross_validation_automatic_div.xml exp17ger_small german_tiger_train.conll 6:0:0 16000 8
