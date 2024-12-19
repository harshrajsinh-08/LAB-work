#include <stdio.h>

// Structure to represent a process
struct Process {
    int pid;         // Process ID
    int arrival;     // Arrival time
    int burst;       // Burst time
    int remaining;   // Remaining burst time
    int completion;  // Completion time
    int turnaround;  // Turnaround time
    int waiting;     // Waiting time
};

// Function to sort processes by arrival time
void sortByArrival(struct Process proc[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (proc[j].arrival > proc[j + 1].arrival) {
               struct Process temp = proc[j];
               proc[j]=proc[j+1];
               proc[j+1]=temp;
            }
        }
    }
}

// Function to implement Round Robin scheduling
void roundRobin(struct Process proc[], int n, int quantum) {
    int currentTime = 0, completed = 0;
    int totalTurnaround = 0, totalWaiting = 0;
    int sequence[1000], seqIndex = 0;

    while (completed < n) {
        int executedInCycle = 0;

        for (int i = 0; i < n; i++) {
            if (proc[i].arrival <= currentTime && proc[i].remaining > 0) {
                // Add process ID to execution sequence
                sequence[seqIndex++] = proc[i].pid;

                if (proc[i].remaining <= quantum) {
                    // Process completes in this cycle
                    currentTime += proc[i].remaining;
                    proc[i].remaining = 0;
                    proc[i].completion = currentTime;
                    completed++;
                    executedInCycle = 1;
                } else {
                    // Process does not complete; only a part is executed
                    currentTime += quantum;
                    proc[i].remaining -= quantum;
                    executedInCycle = 1;
                }
            }
        }

        if (!executedInCycle) {
            // If no process was executed, increment the current time
            currentTime++;
        }
    }

    // Calculate turnaround and waiting times
    for (int i = 0; i < n; i++) {
        proc[i].turnaround = proc[i].completion - proc[i].arrival;
        proc[i].waiting = proc[i].turnaround - proc[i].burst;
        totalTurnaround += proc[i].turnaround;
        totalWaiting += proc[i].waiting;
    }

    // Display process details
    printf("PID\tArrival\tBurst\tCompletion\tTurnaround\tWaiting\n");
    for (int i = 0; i < n; i++) {
        printf("%d\t%d\t%d\t%d\t\t%d\t\t%d\n",
               proc[i].pid, proc[i].arrival, proc[i].burst,
               proc[i].completion, proc[i].turnaround, proc[i].waiting);
    }

    // Calculate and display averages
    printf("\nAverage Turnaround Time: %.2f\n", (float)totalTurnaround / n);
    printf("Average Waiting Time: %.2f\n", (float)totalWaiting / n);

    // Display execution sequence
    printf("\nExecution Sequence: ");
    for (int i = 0; i < seqIndex; i++) {
        printf("%d ", sequence[i]);
    }
    printf("\n");
}

int main() {
    int n, quantum;

    // Read the number of processes
    printf("Enter the number of processes: ");
    scanf("%d", &n);

    struct Process proc[n];

    // Read process details
    printf("Enter arrival time and burst time for each process:\n");
    for (int i = 0; i < n; i++) {
        proc[i].pid = i + 1;
        printf("Process %d: ", proc[i].pid);
        scanf("%d %d", &proc[i].arrival, &proc[i].burst);
        proc[i].remaining = proc[i].burst; // Initialize remaining time
    }

    // Read time quantum
    printf("Enter time quantum: ");
    scanf("%d", &quantum);

    // Sort processes by arrival time
    sortByArrival(proc, n);

    // Call the Round Robin scheduling function
    roundRobin(proc, n, quantum);

    return 0;
}