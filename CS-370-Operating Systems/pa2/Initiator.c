#include "head.h"

// Jim Xu

int main(int argc, char *argv[]){
	
    pid_t cur_pid, pid;
    int status, exstat1, exstat2, exstat3;
    char *fn;
    if (argc < 2) fn = "Nums.txt";
    else fn = argv[1];
    
    //Mode
    cur_pid = fork();
    if (cur_pid < 0){
    	fprintf(stderr, "Fork Failed\n");
    	exit(-1);
    } else if( cur_pid == 0){
    	execlp("./Mode.o", fn, NULL);
    }else{
    	printf("Initiator: forked process with ID %d.\n", cur_pid);
    	printf("Initiator: waiting for process [%d].\n", cur_pid);
        pid = wait(&status);
        exstat1 = WEXITSTATUS(status);
    	printf("Initiator: child process %d returned %d.\n", cur_pid , exstat1 == 255 ? -1 : exstat1);
    }
    
    //Mean
    cur_pid = fork();
    if (cur_pid < 0){
        fprintf(stderr, "Fork Failed\n");
        exit(-1);
    } else if( cur_pid == 0){
        execlp("./Mean.o", fn, NULL);
    }else{
        printf("Initiator: forked process with ID %d.\n", cur_pid);
        printf("Initiator: waiting for process [%d].\n", cur_pid);
        pid = wait(&status);
        exstat3 = WEXITSTATUS(status);
        printf("Initiator: child process %d returned %d.\n", cur_pid , exstat3 == 255 ? -1 : exstat3);
    }   
    
    //Median
    cur_pid = fork();
    if (cur_pid < 0){
        fprintf(stderr, "Fork Failed\n");
        exit(-1);
    } else if( cur_pid == 0){
        execlp("./Median.o", fn, NULL);
    }else{
        printf("Initiator: forked process with ID %d.\n", cur_pid);
        printf("Initiator: waiting for process [%d].\n", cur_pid);
        pid = wait(&status);
        exstat2 = WEXITSTATUS(status);
        printf("Initiator: child process %d returned %d.\n", cur_pid , exstat2 == 255 ? -1 : exstat2);
    }
    
    
    printf("The mode is "); exstat1 == 1 ? printf("positive\n") : exstat1 == 0 ? printf("zero\n") : printf("negative\n"); 
    printf("The median is "); exstat2 == 1 ? printf("positive\n") : exstat2 == 0 ? printf("zero\n") : printf("negative\n"); 
    printf("The mean is "); exstat3 == 1 ? printf("positive\n") : exstat3 == 0 ? printf("zero\n") : printf("negative\n"); 






}
