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

#Divide for the first time

java -Xmx10000M -cp .:./scala/lib/scala-library.jar experiment2/DivideByColumn output_unique_$1 2 1000

#Train with cross validation to get a list of the parts with the accuracy

java -Xmx1000M -cp .:./scala/lib/scala-library.jar experiment2/RunLiblinearCrossValidationOnFiles 10 output_unique_$1_c2_t1000_cat > result_one_div_$1_for2c

#Remove the last line

sed '$d' result_one_div_$1_for2c > result_one_div_$1_for2c_without_last

#Collect the files with less accuracy than the average

java -Xmx10000M -cp .:./scala/lib/scala-library.jar experiment2/CollectFilesWithAccuracyLessThan $2 < result_one_div_$1_for2c_without_last > less_than_avarage_$1

cp less_than_avarage_$1 less_than_avarage_$1_result

sed '$d' less_than_avarage_$1 > less_than_avarage_$1temp
sed '$d' less_than_avarage_$1temp > less_than_avarage_$1
sed '$d' less_than_avarage_$1 > less_than_avarage_$1temp
sed '$d' less_than_avarage_$1temp > less_than_avarage_$1

rm less_than_avarage_$1temp

#Divide it by an other column and check the result

java -Xmx10000M -cp .:./scala/lib/scala-library.jar experiment2/DivideByColumn less_than_avarage_$1 1 1000

java -Xmx1000M -cp .:./scala/lib/scala-library.jar experiment2/RunLiblinearCrossValidationOnFiles 10 less_than_avarage_$1_c1_t1000_cat > result_less_than_avarage_$1_div 

#Train everything as one chunk and check the result

java -Xmx1000M -cp .:./scala/lib/scala-library.jar experiment2/ConvertFeautureVectorWithUniqueFeauturesToLibLinearFormat less_than_avarage_$1  > less_than_avarage_$1_liblinear

./train -s 4 -c 0.1 -v 10  less_than_avarage_$1_liblinear > result_less_than_avarage_$1_one_chunk




##Do everything again but this time with greater than average:

#Collect the files with greater accuracy than the average

java -Xmx10000M -cp .:./scala/lib/scala-library.jar experiment2/CollectFilesWithAccuracyGreaterThan $2 < result_one_div_$1_for2c_without_last > greater_than_avarage_$1

cp greater_than_avarage_$1 greater_than_avarage_$1_result

sed '$d' greater_than_avarage_$1 > greater_than_avarage_$1temp
sed '$d' greater_than_avarage_$1temp > greater_than_avarage_$1
sed '$d' greater_than_avarage_$1 > greater_than_avarage_$1temp
sed '$d' greater_than_avarage_$1temp > greater_than_avarage_$1

rm greater_than_avarage_$1temp

#Divide it by an other column and check the result

java -Xmx10000M -cp .:./scala/lib/scala-library.jar experiment2/DivideByColumn greater_than_avarage_$1 1 1000

java -Xmx1000M -cp .:./scala/lib/scala-library.jar experiment2/RunLiblinearCrossValidationOnFiles 10 greater_than_avarage_$1_c1_t1000_cat > result_greater_than_avarage_$1_div 

#Train everything as one chunk and check the result

java -Xmx1000M -cp .:./scala/lib/scala-library.jar experiment2/ConvertFeautureVectorWithUniqueFeauturesToLibLinearFormat greater_than_avarage_$1  > greater_than_avarage_$1_liblinear

./train -s 4 -c 0.1 -v 10  greater_than_avarage_$1_liblinear > result_greater_than_avarage_$1_one_chunk

