
1. The software package used in this project includes Weka (version 3.6.1) and wrapper class LibSVM for SVM 

2. Weka (version 3.6.1) can be downloaded from http://www.cs.waikato.ac.nz/~ml/weka/downloading.html 

3. The wrapper class libsvm can be downed from http://www.csie.ntu.edu.tw/~cjlin/libsvm/
Installation instruction can be found here http://weka.wikispaces.com/LibSVM

4. Weka GUI was used and no code included

5. The are two sets of data, one is called "adult", then other called "ipo", here are the list and brief description
 1)*_test.arff----test set 
 2)*_train.arff---training set
 3)*_train[x].arff---[x]0% sample with replacement from training set

6. ipo data includes following attributes
•	1_day( price change (%) over offering price at first day closing)
•	30_day(price change (%) over offering price at 30 day)
•	60_day(price change (%) over offering price at 60 day)
•	Month( month of IPO)
•	Underwriter ( underwriter group) 
•	Grade1(class : T—price at 6 month is higher than first day closing , F—Price at 6 month is lower than first day closing) 

7 for description of adult data, please see UCI repository at 
http://archive.ics.uci.edu/ml/datasets/Adult