#!/bin/bash

if [ "$1" == "-compile" ] ; then
	echo "Compiling java files"
	hadoop com.sun.tools.javac.Main unigram/*.java
fi

if [ "$1" == "-clean" ] ; then
	echo "Cleaning java files"
	rm unigram/*.class
fi

if [ "$1" == "-hadoop" ] ; then
	echo "Running hadoop tasks"
	hadoop jar unigram.jar unigram.UnigramDriver CS435-SP-2018-PA1-Sample.txt $2
fi

if [ "$1" == "-runall" ] ; then
	hadoop com.sun.tools.javac.Main unigram/*.java
	jar cf unigram.jar unigram/*.class
	hadoop jar unigram.jar unigram.UnigramDriver CS435-SP-2018-PA1-Sample.txt out
fi

if [ "$1" == "-cleanall" ] ; then
	rm unigram/*.class
	rm unigram.jar
	rm -rf out*
fi
