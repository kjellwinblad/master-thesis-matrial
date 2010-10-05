set term postscript eps
set output 'ger.eps'
set title 'Partition Size Accuracy Relationship German'
set xlabel 'Partition Size (Logarithmic Scale)'
set ylabel 'Cross Validation Score'
set xrange [0.00085:0.25]
set yrange [70.0:100.0]
set key left
set logscale x
#set xtics 0.2
#set ytics 3
 
plot 'german.dat' using 1:2 notitle 'Partition Size Accuracy Relationship German' with impulses
