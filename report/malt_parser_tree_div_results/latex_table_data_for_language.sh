#/bin/sh

lang=$1

echo $lang

scala tools.ProduceLatexTableDataFromVizData < viz_$lang"19"


scala tools.ProduceLatexTableDataFromVizData < viz_$lang"6"


scala tools.ProduceLatexTableDataFromVizData < viz_$lang"8"
