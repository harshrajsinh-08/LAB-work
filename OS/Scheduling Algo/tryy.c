#include <stdio.h>
struct Process {
    int pid;
    int pr;         // Process ID
    int at;     // Arrival time
    int bt;       // Burst time
    int rt;   // Remaining burst time
    int ct;  // Completion time
    int tat;  // Turnaround time
    int wt;     // Waiting time
};

// Function to sort processes by arrival time
void sortByArrival(struct Process proc[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (proc[j].at > proc[j + 1].at || proc[j].at==proc[j+1].at && proc[j].pr>proc[j+1].pr) {
               struct Process temp = proc[j];
               proc[j]=proc[j+1];
               proc[j+1]=temp;
            }
        }
    }
}


void prio(struct Process proc[],int n){
    //initialize
    int currenttime=0,completed=0;
    int totaltat=0,totalwt=0;
    int iscompleted[n];
    int seq[100],seqindex=0;

    for(int i=0;i<n;i++){
        iscompleted[i]=0;
    }

    //do till all completed

    while(completed<n){
        int minpri=1e8,idx=-1;
        for(int i=0;i<n;i++){
            if(proc[i].at <=currenttime && !iscompleted[i] && proc[i].pr<minpri){
                minpri = proc[i].pr;
                idx=i;
            }
        }
        if(idx!=-1){
            currenttime+= proc[idx].bt;
            proc[idx].ct = currenttime;
            proc[idx].tat=proc[idx].ct-proc[idx].at;
            proc[idx].wt = proc[idx].tat-proc[idx].bt;
            totaltat += proc[idx].tat;
            totalwt += proc[idx].wt;
            iscompleted [idx]=1;
            seq[seqindex]=proc[idx].pid;
            completed++;
        }
        else{
            currenttime++;
        }
}