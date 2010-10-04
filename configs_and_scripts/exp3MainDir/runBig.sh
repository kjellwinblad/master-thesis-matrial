#!/bin/sh

qsub -N execute_experiment$1 -l h_rt=12:0:0 -l mem=10000M  execute_experiment$1.sh cze_stack_lazy
qsub -N execute_experiment$1 -l h_rt=12:0:0 -l mem=10000M  execute_experiment$1.sh cze_stack_proj
qsub -N execute_experiment$1 -l h_rt=12:0:0 -l mem=10000M  execute_experiment$1.sh eng_stack_lazy
qsub -N execute_experiment$1 -l h_rt=12:0:0 -l mem=10000M  execute_experiment$1.sh eng_stack_proj
qsub -N execute_experiment$1 -l h_rt=12:0:0 -l mem=10000M  execute_experiment$1.sh ger_stack_lazy
qsub -N execute_experiment$1 -l h_rt=12:0:0 -l mem=10000M  execute_experiment$1.sh ger_stack_proj






