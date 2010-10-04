#!/bin/sh
#
#Argument 1 the direcory where the experiment shall be run
#Argument 2 the path to the CONLL file to train on
#Argument 3 the path to the CONLL file to test on
#Argument 4 the configuaration file of the experiment
#Argument 5 name of the experiment
#Argument 6 memory in mb required by Java
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

malt="java -Xmx$6m -jar malt/malt.jar"

module load java

#Go to the test dir
cd $1

#Do the training

echo TRAINING STARTED 2>&1 | tee -a  $5.log
date 2>&1 | tee -a  $5.log

${malt}  -f $4 \
-lli true -c $5_trained_model -i $2 -m learn  -v debug \
2>&1 | tee $5.log

echo TRAINING FINISHED 2>&1  | tee -a  $5.log
date 2>&1 | tee -a  $5.log

sleep 15

#Do the test parsing

echo TEST PARSING STARTED 2>&1 | tee -a  $5.log
date  2>&1 | tee -a  $5.log
${malt} \
    -f $4 \
    -c $5_trained_model \
    -m parse \
    -i $3 \
     -v debug \
    -o $5_testout.conll \
2>&1 | tee -a  $5.log
echo TEST PARSING FINISHED  2>&1 | tee -a  $5.log
date 2>&1 | tee -a  $5.log

sleep 5
#Print the result

echo PRINT THE TEST SCORE  2>&1 | tee -a  $5.log
date 2>&1 | tee -a  $5.log

./eval07.pl -p -q -g $3 -s $5_testout.conll  2>&1 | tee -a  $5.log

#unpack the model

${malt}  -f $4 \
-lli true -c $5_trained_model -i $2 -m unpack
