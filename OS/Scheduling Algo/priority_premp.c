#include <stdio.h>

// Structure to represent a process
struct Process {
    int pid;         // Process ID
    int arrival;     // Arrival time
    int burst;       // Total burst time
    int remaining;   // Remaining burst time
    int priority;    // Priority (lower value = higher priority)
    int completion;  // Completion time
    int turnaround;  // Turnaround time
    int waiting;     // Waiting time
};

// Function to implement Preemptive Priority Scheduling
void preemptivePriorityScheduling(struct Process proc[], int n) {
    int currentTime = 0, completed = 0;
    int totalTurnaround = 0, totalWaiting = 0;
    int sequence[100], seqIndex = 0;

    while (completed < n) {
        int minPriority = 1e9, idx = -1;

        // Find the process with the highest priority that has arrived and has remaining time
        for (int i = 0; i < n; i++) {
            if (proc[i].arrival <= currentTime && proc[i].remaining > 0 && proc[i].priority < minPriority) {
                minPriority = proc[i].priority;
                idx = i;
            }
        }

        if (idx != -1) {
            // Execute the selected process for 1 time unit
            proc[idx].remaining--;
            sequence[seqIndex++] = proc[idx].pid;

            // If the process is completed
            if (proc[idx].remaining == 0) {
                completed++;
                proc[idx].completion = currentTime + 1;
                proc[idx].turnaround = proc[idx].completion - proc[idx].arrival;
                proc[idx].waiting = proc[idx].turnaround - proc[idx].burst;

                totalTurnaround += proc[idx].turnaround;
                totalWaiting += proc[idx].waiting;
            }

            currentTime++;
        } else {
            // If no process is ready, increment the current time
            currentTime++;
        }
    }

    // Display process details
    printf("PID\tArrival\tBurst\tPriority\tCompletion\tTurnaround\tWaiting\n");
    for (int i = 0; i < n; i++) {
        printf("%d\t%d\t%d\t%d\t\t%d\t\t%d\t\t%d\n",
               proc[i].pid, proc[i].arrival, proc[i].burst,
               proc[i].priority, proc[i].completion,
               proc[i].turnaround, proc[i].waiting);
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
    int n;

    // Read the number of processes
    printf("Enter the number of processes: ");
    scanf("%d", &n);

    struct Process proc[n];

    // Read process details
    printf("Enter arrival time, burst time, and priority for each process:\n");
    for (int i = 0; i < n; i++) {
        proc[i].pid = i + 1;
        printf("Process %d: ", proc[i].pid);
        scanf("%d %d %d", &proc[i].arrival, &proc[i].burst, &proc[i].priority);
        proc[i].remaining = proc[i].burst; // Initialize remaining time
    }

    // Call the scheduling function
    preemptivePriorityScheduling(proc, n);

    return 0;
}