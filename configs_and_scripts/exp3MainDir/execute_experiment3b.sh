#!/bin/sh
#
#Argument 1 the language
#
# name of the job
#$ -N experiment
#
# join output (stderr and stdout) to stdout file
#$ -j y
#
# mail user
#$ -M kjellwinblad@gmail.com
#
# mail options: mail at the beginning, end, and abortion of job
#$ -m bea
#
# requested runtime (hours, minutes, seconds)
#$ -l h_rt=0:30:0
#
# requested memory
#$ -l mem=15G
#


set -e

#Go to the test dir
cd exp3MainDir

#Do the training

java -cp .:./scala/lib/scala-library.jar experiment3/TreeClassifierCreationOneOther output_unique_$1 > tree_classifier_one_other_$1

rm output_unique_$1_*

