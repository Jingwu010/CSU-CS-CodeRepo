#!/bin/bash

if [ "$1" == "-compile" ] ; then
	echo "Compiling java files"
	javac A1/*/*.java
fi

if [ "$1" == "-clean" ]; then
	echo "Cleaning class files"
	rm A1/*/*.class
fi

if [ "$1" == "-suite" ]; then
	echo "Running chess board example"
	java A1.test.ChessSuite 
fi

if [ "$1" == "-run" ]; then
	echo "Running chess board example"
	java A1.src.ChessProgram 
fi

if [ "$1" == "-test" ]; then
	echo "Running JUnit Tests"
	for f in A1/test/*.class
	do
		echo "Processing $f"
		a=${f%.*}
		java org.junit.runner.JUnitCore "A1.test."${a##*/}
	done
fi

echo "Finished"
exit 0
