#!/bin/bash

if [ "$1" == "-compile" ] ; then
	if [ -z "$2" ]; then
		echo "Compiling java files"
		hadoop com.sun.tools.javac.Main tfidf/*.java -Xlint
	else
		echo "Compiling part $2 java files"
		hadoop com.sun.tools.javac.Main tfidf/Tfidf*"$2".java
		hadoop com.sun.tools.javac.Main tfidf/TfidfDriver.java
	fi
	jar cf tfidf.jar tfidf/*.class
fi

if [ "$1" == "-clean" ] ; then
	echo "Cleaning java class files"
	rm tfidf/*.class
	echo "Cleaning jar files"
	rm tfidf.jar
	echo "Cleaning output files"
	rm -rf out*
fi

if [ "$1" == "-hadoop" ] ; then
	rm -rf out*
	echo "Compiling java files"
	hadoop jar tfidf.jar tfidf.TfidfDriver CS435-SP-2018-PA1-LocalSample.txt $2
fi