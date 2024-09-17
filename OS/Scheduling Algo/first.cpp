#include <stdio.h>

struct Process {
    int pid;       
    int arrival;   
    int burst;     
    int completion;
    int turnaround;
    int waiting;   
};
void sortByArrival(struct Process proc[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            if (proc[i].arrival > proc[j].arrival) {
                struct Process temp = proc[i];
                proc[i] = proc[j];
                proc[j] = temp;
            }
        }
    }
}

void firstComeFirstServe(struct Process proc[], int n) {
    int currentTime = 0;
    int totalTurnaround = 0, totalWaiting = 0;
    for (int i = 0; i < n; i++) {
        if (currentTime < proc[i].arrival) {
            currentTime = proc[i].arrival;
        }
        proc[i].completion = currentTime + proc[i].burst;
        proc[i].turnaround = proc[i].completion - proc[i].arrival;
        proc[i].waiting = proc[i].turnaround - proc[i].burst;

        totalTurnaround += proc[i].turnaround;
        totalWaiting += proc[i].waiting;

        currentTime = proc[i].completion;
    }
    printf("\nProcess Execution Sequence: ");
    for (int i = 0; i < n; i++) {
        printf("P%d ", proc[i].pid);
    }

    printf("\n\nPID\tArrival\tBurst\tCompletion\tTurnaround\tWaiting\n");
    for (int i = 0; i < n; i++) {
        printf("%d\t%d\t%d\t%d\t\t%d\t\t%d\n", proc[i].pid, proc[i].arrival, proc[i].burst, 
               proc[i].completion, proc[i].turnaround, proc[i].waiting);
    }

    printf("\nAverage Turnaround Time: %.2f\n", (float)totalTurnaround / n);
    printf("Average Waiting Time: %.2f\n", (float)totalWaiting / n);
}

int main() {
    int n;
    struct Process proc[100];

    printf("Enter number of processes: ");
    scanf("%d", &n);

    for (int i = 0; i < n; i++) {
        proc[i].pid = i + 1;
        printf("Enter Arrival Time and Burst Time of process %d: ", proc[i].pid);
        scanf("%d %d", &proc[i].arrival, &proc[i].burst);
    }

    sortByArrival(proc, n);
    firstComeFirstServe(proc, n);

    return 0;
}
