#include "Readfile.c"

// Jim Xu

int Mode(int *a,int n) {
    int maxs = 0, count = 0;
    for (int i = 0; i < n; i++){
      int tmp = 0;
      for (int j = 0; j < n; ++j) {
         if (a[j] == a[i])
            tmp++;
      }
      if (tmp > count) {
         count = tmp;
         maxs = a[i];
      }
   }
   return maxs;
}

int main(int argc, char *argv[]){
	printf("Mode process [%d]: Starting.\n", getpid());
	char *fn;
	if (argc < 2) fn = "Nums.txt";
	else fn = argv[1];
	int n, mode;
	int *num = Readfile(fn, &n);
    // sort the array num in ascending order
	printf("Mode process [%d]: Mode is %d.\n", getpid(), (mode = Mode(num,n)));
    printf("Mode process [%d]: Stopping.\n", getpid());
    if (mode == 0) exit(0);
    else if (mode > 0) exit(1);
    else exit(-1);
}
