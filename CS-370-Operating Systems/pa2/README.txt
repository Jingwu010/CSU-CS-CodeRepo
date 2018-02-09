The PA2 contains head.h Initiator.c Mode.c Mean.c Median.c Readfile.c Nums.txt

Initiator.c:
	The main file containing the parent process and fork 3 new child processes.
Mode.c:
	Mode c file get filename and calculate the Mode of Numbers in a file.
Mean.c:
	Mean c file get filename and calculate the Mean of Numbers in a file.
Median.c:
	Median c file get filename and calculate the Median of Numbers in a file.
Readfile.c
	Readfile c file mainly used to get numbers array from a file.


Sample run:(first cd to the Document)

make (default) : 
	excute make all and make run;
make all :
	compile all the *.c file into excutable *.o file.
make run :
	excute the Initiator.o
make clean :
	rm excutable file *.o.