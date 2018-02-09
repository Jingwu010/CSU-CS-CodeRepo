#include "head.h"

// Jim Xu

int * Readfile(char *filename, int *count){
	int* num = malloc(sizeof(int) * 256);
	FILE *fp;
    int tmp, n = 0;
    if((fp = fopen(filename,"r")) == NULL) {
        printf("Error reading file\n");
        exit(1);
    } else{
    	while(fscanf(fp, "%d", &tmp) > 0){
    		num[n++] = tmp;
    	}
    }
    
    *count = n;
    fclose(fp);
    return num;
}
