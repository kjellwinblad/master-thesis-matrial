#!/bin/sh

qsub -N execute_experiment$1 -l h_rt=3:0:0 -l mem=13000M  execute_experiment$1.sh swe
qsub -N execute_experiment$1 -l h_rt=3:0:0 -l mem=13000M  execute_experiment$1.sh chi
qsub -N execute_experiment$1 -l h_rt=3:0:0 -l mem=13000M  execute_experiment$1.sh ger

#qsub -N execute_experiment$1 -l h_rt=8:0:0 -l mem=13000M  execute_experiment$1.sh cze_stack_lazy
#qsub -N execute_experiment$1 -l h_rt=8:0:0 -l mem=13000M  execute_experiment$1.sh cze_stack_proj
#qsub -N execute_experiment$1 -l h_rt=8:0:0 -l mem=13000M  execute_experiment$1.sh eng_stack_lazy
#qsub -N execute_experiment$1 -l h_rt=8:0:0 -l mem=13000M  execute_experiment$1.sh eng_stack_proj
#qsub -N execute_experiment$1 -l h_rt=8:0:0 -l mem=13000M  execute_experiment$1.sh ger_stack_lazy
#qsub -N execute_experiment$1 -l h_rt=8:0:0 -l mem=13000M  execute_experiment$1.sh ger_stack_proj






