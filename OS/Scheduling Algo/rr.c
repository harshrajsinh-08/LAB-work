#include <stdio.h>
#include <stdbool.h>

struct Process {
    int pid;
    int arrival;
    int burst;
    int remaining;
    int completion;
    int turnaround;
    int waiting;
};

void roundRobin(struct Process proc[], int n, int quantum) {
    int currentTime = 0;
    int totalTurnaround = 0, totalWaiting = 0;
    int completed = 0;
    int order[100];
    int orderTimes[100];
    int idx = 0;
    
    while (completed < n) {
        bool executed = false;
        
        for (int i = 0; i < n; i++) {
            if (proc[i].arrival <= currentTime && proc[i].remaining > 0) {
                executed = true;
                
                if (proc[i].remaining > quantum) {
                    currentTime += quantum;
                    proc[i].remaining -= quantum;
                } else {
                    currentTime += proc[i].remaining;
                    proc[i].completion = currentTime;
                    proc[i].turnaround = proc[i].completion - proc[i].arrival;
                    proc[i].waiting = proc[i].turnaround - proc[i].burst;
                    
                    totalTurnaround += proc[i].turnaround;
                    totalWaiting += proc[i].waiting;
                    
                    proc[i].remaining = 0;
                    completed++;
                    
                    order[idx] = proc[i].pid;
                    orderTimes[idx] = proc[i].completion;
                    idx++;
                }
            }
        }
        
        if (!executed) {
            currentTime++;
        }
    }  
    printf("\nPID\tArrival\tBurst\tCompletion\tTurnaround\tWaiting\n");
    for (int i = 0; i < n; i++) {
        printf("%d\t%d\t%d\t%d\t\t%d\t\t%d\n", proc[i].pid, proc[i].arrival, proc[i].burst, proc[i].completion, proc[i].turnaround, proc[i].waiting);
    }
    
    printf("\nAverage Turnaround Time: %.2f\n", (float)totalTurnaround / n);
    printf("Average Waiting Time: %.2f\n", (float)totalWaiting / n);
}

int main() {
    int n, quantum;
    struct Process proc[100];
    
    printf("Enter number of processes: ");
    scanf("%d", &n);
    
    printf("Enter time quantum: ");
    scanf("%d", &quantum);
    
    for (int i = 0; i < n; i++) {
        proc[i].pid = i + 1;
        printf("Enter Arrival Time and Burst Time of process %d: ", proc[i].pid);
        scanf("%d %d", &proc[i].arrival, &proc[i].burst);
        proc[i].remaining = proc[i].burst;
    }
    
    roundRobin(proc, n, quantum);
    
    return 0;
}