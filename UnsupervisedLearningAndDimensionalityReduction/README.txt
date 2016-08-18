
1. The software package used in this project includes Weka (version 3.7.1) and package Studentfilters 

2. Weka (version 3.7.1) can be downloaded from http://www.cs.waikato.ac.nz/~ml/weka/downloading.html 

3. The package Studentfilters includes FastICA for Independent Component Analysis. The package can be obtained from https://github.com/cgearhart/students-filters/ or installed in Weka(3.7) using package manager

4. Weka GUI was used for the experiment and no source code included.

5. The are two sets of data, one is called "IPO", the other called "Yeast", here are the list and brief description for file naming 
 1)IPO_train_EM_[x]cluster.arff---ipo dataset after apply EM as dimensionality reduction algorithm, x is the number of clusters 
 2) IPO_train_KM_[x]cluster.arff---ipo dataset after apply K-Means as dimensionality reduction algorithm, x is the number of clusters
 2)[datasetname]_[PCA/ICA/RP/CFS][x](_y).arff-----dataset after dimensionality reduction, x is the number of attributes in projected data.  y is the index( only for RP)  
 3)ipo_train_num.arff----original IPO data with underwriter attribute converted to several binary attributes.
 4)yeast.arff ---yeast data 
 

6. ipo data includes following attributes
•	1_day( price change (%) over offering price at first day closing)
•	30_day(price change (%) over offering price at 30 day)
•	60_day(price change (%) over offering price at 60 day)
•	Month( month of IPO)
•	Underwriter ( underwriter group) , converted to binary 
•	Grade1(class : T—price at 6 month is higher than first day closing , F—Price at 6 month is lower than first day closing) 

7 for description of yeast data and download, please see UCI repository at 
http://archive.ics.uci.edu/ml/datasets/Yeast 