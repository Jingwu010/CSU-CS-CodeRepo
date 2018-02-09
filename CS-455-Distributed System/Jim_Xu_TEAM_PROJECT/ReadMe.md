#GPA Prediction Based onStudent’s Lifestyle

> Minzhuo Jin, Jim Xu, Ivy Min

###OVERVIEW

According to the general acknowledgement, each individual’s lifestyle in college can have aninfluence on one’s academic performance. We are curious about this phenomenon and want toexplore further on how individual’s lifestyle can influence his GPA. We plan to use decision treealgorithm with machine learning powered by Mlib and Spark to make reasonable GPA predictionfor a  specific student's lifestyle . Later on, we can visually present our result to user and advise useron how to getting into a better lifestyle in order to achieve academic success.



###DATASET

We plan to use the dataset provided by the StudentLife Study in Dartmouth College. The datasetis approximately 12 GB in size, and well formatted in csv and db style. It covers a broad range ofdataset about student college lifestyles, making it possible and reasonable to implement decisiontree algorithm with machine learning.

- \-  Data Documentation :  http://studentlife.cs.dartmouth.edu/dataset.html

- \-  Data Accessibility :  http://studentlife.cs.dartmouth.edu/

  ​

###PROBLEM CHARACTERIZATION

College Life is colorful. Students need to seek a balance between  extracurricular  activities andstudy. Many of us could not figure out what kind of lifestyle we live will have a potential influenceon their academic performance, such as sleeping, working, entertaining, studying and mentalstatus.  How does lifestyle affect a student’s academic performance? Can we extract useful informationfrom students’ smartphone sensor data and further predict their academic performance using theknowledge about their eating habits, sleep quality, activities, etc.?  Our problem is derived from those issues and tries to figure out how those potential factors contribute to individual’s GPA. Wedecide to analysis the StudentLife Study’s dataset in the manner of decision tree algorithm tobuild a reasonable relationship between one’s lifestyle and his predicted GPA by using machinelearning.



###CURRENT RELEASE

The newest release from StudentLife Study is a simple model based on linear regression withlasso regularization that can accurately predict cumulative GPA. The predicted GPA stronglycorrelates with the ground truth from students’ transcripts (r = 0.81 and p < 0.001) and predictsGPA within ±0.179 of the reported grades.

\- SmartGPA Study  :  http://studentlife.cs.dartmouth.edu/smartgpa.pdfANALYTIC 



###TASKS

1. Extract information from raw sensor data

2. Pair each student’s lifestyle data, survey results, EMA results with their academic

   performance indicator, GPA in this case.

3. Split the dataset into 2 parts. One is for training the model. The other is for model

   evaluation.

4. Train a Decision Tree regression model that can estimate a student’s GPA given lifestyle

   information.

5. Evaluate the model to see its accuracy, throughput, etc.



###SOLUTION EVALUATION

The metrics we will use to assess the solution include throughputs, mean squared errors. Thethroughput will be evaluated for both the raw data extraction part and the training part.