# **Readme**

ARTIFICIAL INTELLIGENCE : Knn, Naive Bayes & KMean

Few things about this project -
1. test.txt, train.txt, ex1_train.csv, ex1_test.csv etc. are our sample test case. Input file should be in the root directory of the project.
2. The execution arguments can be : 
    In case of KNN : -train [train.txt] -test [test.txt] -K [valueOfK]
    In case of Naive Bayes : -train [train.csv] -test [test.csv] -C [valueOfC]
    In case of KMean in case of manhattan: -train [km1.txt] -d [manh] -centroids [0,0 200,200 500,500] (Centroids should be seperated by space)
    In case of KMean in case of euclidean: -train [km1.txt] -d [eh] -centroids [0,0 200,200 500,500] (Centroids should be seperated by space)
    In case of KMean default case (euclidean): -train [km1.txt] -centroids [0,0 200,200 500,500] (Centroids should be seperated by space)

3. '#' and empty lines will get executed and treated as comments.

To execute the project do run the following commands in the project root directory :

1. mvn clean install
2. java -jar target/AILab4-1.0-SNAPSHOT.jar [Arguments]




