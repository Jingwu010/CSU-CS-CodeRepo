#include "Readfile.c"

// Jim Xu

int main(int argc, char *argv[]){
	printf("Mean process [%d]: Starting.\n", getpid());
	char *fn;
	if (argc < 2) fn = "Nums.txt";
	else fn = argv[1];
	int n;
	int *num = Readfile(fn, &n);
    // sort the array num in ascending order
	double sum = 0;
	for (int i = 0; i < n; i++)
		sum += num[i];
	double mean = sum / n;
	printf("Mean process [%d]: Mean is %f.\n", getpid(), mean);
    printf("Mean process [%d]: Stopping.\n", getpid());
    if (mean == 0) exit(0);
    else if (mean > 0) exit(1);
    else exit(-1);
	
	
}
