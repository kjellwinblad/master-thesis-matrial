#proc getdata
  data: 
A 77.3029
A 88.3639
A 83.1852
A 98.07
A 87.3362
A 92.3796
A 98.8288
A 86.4994
A 86.3986
A 89.9428
A 83.97
A 85.0321
A 87.317
A 96.683
A 88.3038
A 96.2523
A 98.7678
A 88.5947
A 86.0239
A 99.3539
A 93.2015
A 81.5411
A 90.7303
A 90.7324
A 87.0597
A 93.4512
A 78.5149
A 99.0124
A 90.9128
A 93.8625
B 87.4755
B 98.9603
B 88.3633
B 84.8848
B 90.6618
B 85.7402
B 84.1213
B 85.0299
B 94.2373
B 82.879
B 89.7518
B 84.4882
B 96.517
B 92.4528
B 87.8085
B 91.2664
B 96.067
B 85.5491
B 98.834
B 87.6171
B 86.9973
B 93.2268
B 99.192
B 98.3209
B 91.6711
B 98.5209
B 89.0323
B 99.5694
B 96.7712
B 85.3327
B 89.0487
B 98.5396
B 85.6548
B 99.6851
B 99.2096
B 93.4813
B 95.5827
B 93.2449
B 87.5076
B 85.8676
B 99.4342
B 99.281
B 88.2083
B 89.4689
B 86.7201
B 88.7281
B 99.3145
B 91.698
B 91.0861
B 85.2789
B 88.3534
B 87.9203
B 95.2534
B 92.3394
B 99.6751
B 93.7729
B 90.1815
B 96.5166
B 89.2249
B 97.2148
B 99.4479
B 92.8202
B 98.1562
B 92.4303
B 89.1918
B 91.8833
B 90.4602
B 89.0362
B 93.3285
B 89.8929
B 91.6909
B 89.8555
B 89.4068
B 90.2945
B 99.0606
B 89.4382
C 93.2432
C 97.4895
C 98.3333
C 81.25
C 94.6816
C 98.4615
C 99.177
C 91.3446
C 96.1855
C 99.813
C 95.7127
C 86.8029
C 95.8959
C 99.8002
C 86.6079
C 98.5971
C 94.8183
C 99.6005
C 89.657
C 91.7853
C 95.5603
C 99.6429
C 94.4361
C 93.0855
C 81.7458
C 91.8496
C 88.6741
C 91.7129
C 87.0289
C 90.6672
C 91.9367
C 84.2538
C 93.5306
C 92.2674
C 95.0387
C 91.9575
C 99.0758
C 80.8171
C 99.642
C 93.4043
C 94.4343
 
 
#proc categories
  axis: y
  datafield: 1
 
#proc areadef
  rectangle: 1 1 4 4
  xrange: 75 100
  yrange: categories
  xaxis.stubs: inc
  xaxis.grid: color=gray(0.9)
  yaxis.stubs: usecategories
  yaxis.tics: none
 
#proc processdata
  action: summaryplus
  fields: 1
  valfield: 2
 
 
 
#proc categories
  axis: y
  slideamount: -0.15
 
#proc boxplot
  // uses the summaryplus output fieldnames by default...
  orientation: horizontal
  locfield: id1
  barwidth: 0.3
  //color: xffebff
  legendlabel: median-based //median-based mean-based
  mediansym: line
  printn: no
  
 
 
//#proc categories
//  axis: y
//  slideamount: 0.15
// 
//#proc boxplot
//  orientation: horizontal
//  basis: mean
//  locfield: id1
//  barwidth: 0.07
//  color: xfff6e1
//  legendlabel: mean-based
//  mediansym: dot
//  printn: no
// 
//#proc legend
//  format: across
//  location: min+0.2 min-0.4 
//  outlinecolors: yes
