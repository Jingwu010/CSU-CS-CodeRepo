#include "Readfile.c"

// Jim Xu

float Median(int n, int x[]) {
    float temp;
    int i, j;
    // sort the array x in ascending order
    for(i=0; i<n-1; i++) {
        for(j=i+1; j<n; j++) {
            if(x[j] < x[i]) {
                // swap 
                temp = x[i];
                x[i] = x[j];
                x[j] = temp;
            }
        }
    }
 
    if(n%2==0) {
        // if  even number of elements, return mean of the two elements in the middle
        return((x[n/2] + x[n/2 - 1]) / 2.0);
    } else {
        // else return the element in the middle
        return x[n/2];
    }
}

int main(int argc, char *argv[]){

   printf("Median process [%d]: Starting.\n", getpid());
    char *fn;
    if (argc < 2) fn = "Nums.txt";
    else fn = argv[1];
    int n;
    int *num = Readfile(fn, &n);
    double median = Median(n, num);
    
    printf("Median process [%d]: Median is %f.\n", getpid(), median);
    printf("Median process [%d]: Stopping.\n", getpid());
    if (median == 0) exit(0);
    else if (median > 0) exit(1);
    else exit(-1);
    
    
}
