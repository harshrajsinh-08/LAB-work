#include <stdio.h>

struct Process {
    int pid;         // Process ID
    int arrival;     // Arrival Time
    int burst;       // Burst Time
    int completion;  // Completion Time
    int turnaround;  // Turnaround Time
    int waiting;     // Waiting Time
};

// Function to sort processes by their arrival time
void sortByArrival(struct Process proc[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            if (proc[i].arrival > proc[j].arrival) {
                // Swap processes
                struct Process temp = proc[i];
                proc[i] = proc[j];
                proc[j] = temp;
            }
        }
    }
}

// Function to implement FCFS scheduling
void firstComeFirstServe(struct Process proc[], int n) {
    int currentTime = 0;
    int totalTurnaround = 0, totalWaiting = 0;

    // Calculate completion, turnaround, and waiting times
    for (int i = 0; i < n; i++) {
        if (currentTime < proc[i].arrival) {
            currentTime = proc[i].arrival;  // Wait until process arrives
        }
        proc[i].completion = currentTime + proc[i].burst;
        proc[i].turnaround = proc[i].completion - proc[i].arrival;
        proc[i].waiting = proc[i].turnaround - proc[i].burst;

        currentTime = proc[i].completion;  // Update current time
        totalTurnaround += proc[i].turnaround;
        totalWaiting += proc[i].waiting;
    }

    // Print process details and calculate averages
    printf("PID\tArrival\tBurst\tCompletion\tTurnaround\tWaiting\n");
    for (int i = 0; i < n; i++) {
        printf("%d\t%d\t%d\t%d\t\t%d\t\t%d\n",
               proc[i].pid, proc[i].arrival, proc[i].burst,
               proc[i].completion, proc[i].turnaround, proc[i].waiting);
    }

    printf("\nAverage Turnaround Time: %.2f\n", (float)totalTurnaround / n);
    printf("Average Waiting Time: %.2f\n", (float)totalWaiting / n);
}

int main() {
    int n;

    printf("Enter the number of processes: ");
    scanf("%d", &n);

    struct Process proc[n];
    for (int i = 0; i < n; i++) {
        printf("Enter arrival time and burst time for process %d: ", i + 1);
        proc[i].pid = i + 1;  
        scanf("%d %d", &proc[i].arrival, &proc[i].burst);
    }
    sortByArrival(proc, n);
    firstComeFirstServe(proc, n);

    return 0;
}