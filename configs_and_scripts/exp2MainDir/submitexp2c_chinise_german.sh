#!/bin/sh

qsub -l h_rt=0:50:0 -l mem=10000M ./execute_experiment2c.sh swe 91.1496

qsub -l h_rt=0:50:0 -l mem=10000M ./execute_experiment2c.sh chi 92.0889

qsub -l h_rt=1:0:0 -l mem=10000M ./execute_experiment2c.sh ger 92.6778
