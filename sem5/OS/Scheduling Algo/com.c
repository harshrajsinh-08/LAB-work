#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(){
    pid_t pid;
    int status;

    pid=fork();
    if(fork<0){
        fprintf(stderr,"fork failed");
        return 1;
    }else if(fork==0){
        printf("child process with pid %d\n",getpid());
        printf("child process is doing some work..\n");
        sleep(10);
       printf("child process exiting..with status 42\n");
       exit(42);
       return 0;
    }else{
        printf("parent process pid: %d\n",getpid());
        pid_t childpid = wait(&status);
        if(childpid==-1){
            perror("wait failed");
            return 1;
        }
        if(WIFEXITED(status)){
            printf("child exited with status :%d\n",WEXITSTATUS(status));
        }
        else{
            printf("child process didnot exited normally");
        }

    }
    return 0;
}