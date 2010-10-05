set term postscript eps
set output 'ger.eps'
set title 'Label Attachment Score for LIBLINEAR Decision Tree German'
set xlabel 'Training Set Size'
set ylabel 'Label Attachment Score (LAS)'
set xrange [0.007:1.1]
set yrange [53.0:87.5]
set key left
set logscale x 2
#set xtics 0.2
#set ytics 3
 
plot 'ger.dat' using 1:2 title 'Linear SVM' with lines, \
     'ger.dat' using 1:3 title 'Linear SVM Division' with lines, \
     'ger.dat' using 1:4 title 'Decision Tree Intuitive' with lines, \
     'ger.dat' using 1:5 title 'Decision Tree Gain Ratio' with lines

