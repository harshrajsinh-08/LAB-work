#include <stdio.h>
#include <stdbool.h>
void calc(int need[][10],int max[][10],int allocation[][10],int m,int n){
    for(int i=0;i<m;i++){
        for(int j=0;j<n;j++){
            need[i][j]=max[i][j]-allocation[i][j];
        }
    }
}

bool isSafe(int process[],int available[],int max[][10],int alloc[][10],int m,int n){

    int need[10][10];
    calc(need,max,alloc,m,n);
    bool finish[10]={0};
    int safeseq[10];
    int work[10];
    for(int i=0;i<n;i++){
        work[i]=available[i];
    }
    int count=0;
    while(count<m){
        bool found=false;
        for(int p=0;p<m;p++){
            if(finish[p]==0){
                int j;
                for(int j=0;j<n;j++){
                    if(need[p][j]>work[j]){
                        break;
                    }
                    if(j==n){
                        for(int k=0;k<n;k++){
                            work[k]+=alloc[p][k];
                        }
                        safeseq[count++]=process[p];
                        finish[p]=1;
                        found=true;
                    }
                }
            }
        }
        if(found==false){
            printf("System is not in a safe state.\n");
            return false;
        }
    }
    printf("System is in a safe state.\nSafe sequence is: ");
    for(int i=0;i<m;i++){
        printf("P%d ",safeseq[i]);
    }
}