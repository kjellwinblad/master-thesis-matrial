set term postscript eps
set output 'swe.eps'
set title 'Label Attachment Score Swedish'
set xlabel 'Training Set Size'
set ylabel 'Label Attachment Score (LAS)'
set xrange [0.007:1.1]
set yrange [53.0:85.0]
set key left
set logscale x 2
#set xtics 0.2
#set ytics 3
 
plot 'swe_data.dat' using 1:2 title 'Linear SVM Division' with lines, \
     'swe_data.dat' using 1:3 title 'Linear SVM' with lines, \
     'swe_data.dat' using 1:4 title 'Nonlinear SVM Division' with lines, \
     'swe_data.dat' using 1:5 title 'Nonlinear SVM' with lines

