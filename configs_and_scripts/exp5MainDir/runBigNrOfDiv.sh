#!/bin/sh


#chinese_sinica_train.conll                     CoNLL2009-ST-English-train.CONVERTED-GOLD.conll  german_tiger_train.conll
#CoNLL2009-ST-Czech-train.CONVERTED-GOLD.conll  CoNLL2009-ST-German-train.CONVERTED-GOLD.conll   swedish_talbanken05_train.conll

#experiment13_tree_nr_of_cross_validations_4_test.xml experiment14_tree_nr_of_cross_validations_8_test.xml experiment15_tree_nr_of_cross_validations_16_test.xml


java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment13_tree_nr_of_cross_validations_4_test.xml exp13swe swedish_talbanken05_train.conll 4:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment13_tree_nr_of_cross_validations_4_test.xml exp13chi chinese_sinica_train.conll 6:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment13_tree_nr_of_cross_validations_4_test.xml exp13ger_small german_tiger_train.conll 8:0:0 16000 8


java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment14_tree_nr_of_cross_validations_8_test.xml exp14swe swedish_talbanken05_train.conll 4:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment14_tree_nr_of_cross_validations_8_test.xml exp14chi chinese_sinica_train.conll 6:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment14_tree_nr_of_cross_validations_8_test.xml exp14ger_small german_tiger_train.conll 8:0:0 16000 8


java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment15_tree_nr_of_cross_validations_16_test.xml exp15swe swedish_talbanken05_train.conll 14:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment15_tree_nr_of_cross_validations_16_test.xml exp15chi chinese_sinica_train.conll 16:0:0 16000 8

java -cp scala/lib/scala-library.jar:. experiment1/ExperimentExecutor \
 experiment15_tree_nr_of_cross_validations_16_test.xml exp15ger_small german_tiger_train.conll 22:0:0 16000 8

