set term postscript eps
set output 'chi.eps'
set title 'Label Attachment Score Chinese'
set xlabel 'Training Set Size'
set ylabel 'Label Attachment Score (LAS)'
set xrange [0.007:1.1]
set yrange [53.0:85.0]
set key left
set logscale x 2
#set xtics 0.2
#set ytics 3
 
plot 'chi_data.dat' using 1:2 title 'Linear SVM Division' with lines, \
     'chi_data.dat' using 1:3 title 'Linear SVM' with lines, \
     'chi_data.dat' using 1:4 title 'Nonlinear SVM Division' with lines, \
     'chi_data.dat' using 1:5 title 'Nonlinear SVM' with lines

