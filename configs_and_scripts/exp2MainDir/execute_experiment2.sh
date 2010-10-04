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
cd exp2MainDir

#Do the training

module load java


java -Xmx10000M -cp .:./scala/lib/scala-library.jar experiment2/DivideByColumn output_unique_$1 2 1000


java -Xmx1000M -cp .:./scala/lib/scala-library.jar experiment2/RunLiblinearCrossValidationOnFiles 10 output_unique_$1_c2_t1000_cat > result_one_div_$1_new_params



java -Xmx10000M -cp .:./scala/lib/scala-library.jar experiment2/DivideByTwoColumns output_unique_$1 2 1 1000

java -Xmx1000M -cp .:./scala/lib/scala-library.jar experiment2/RunLiblinearCrossValidationOnFiles 10 output_unique_$1_c2-1_t1000_cat > result_two_div_$1_new_params

