## Creating N-gram profile for a Wikipedia Corpus

1. `-compile` : compiles unigram java source files
2. `-clean` : clean unigram java class files
3. `-hadoop` : run unigram hadoop job
4. `-runall` : compile, jar files, run hadoop jobs
5. `-cleanall` : remove class files, remove jar file, remove output file



```
First Test Case<====>35226555<====>He is Is IS HE. 

Second Test Case<====>35226556<====>She is she.
```



##### Profile 1

count unique word token

```
He
is
she
```

##### Profile 2

count word token and its occurance based on Document ID, output by descending order

```
35226555	is	3
35226555	he	2
35226556	she	2
35226556	is	1
```

##### Profile 3

count word token and its occurance, output by descending order

```
is	4
he	2
she	2
```

