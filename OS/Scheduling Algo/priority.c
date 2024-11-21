#include <stdio.h>

// Structure to represent a process
struct Process {
    int pid;         // Process ID
    int arrival;     // Arrival time
    int burst;       // Burst time
    int priority;    // Priority (lower value = higher priority)
    int completion;  // Completion time
    int turnaround;  // Turnaround time
    int waiting;     // Waiting time
};

// Helper function to swap two process structures
void swap(struct Process *a, struct Process *b) {
    struct Process temp = *a;
    *a = *b;
    *b = temp;
}

// Function to sort processes by arrival time and priority
void sortByArrivalAndPriority(struct Process proc[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (proc[j].arrival > proc[j + 1].arrival || 
               (proc[j].arrival == proc[j + 1].arrival && proc[j].priority > proc[j + 1].priority)) {
                swap(&proc[j], &proc[j + 1]);
            }
        }
    }
}

// Function to implement Priority Scheduling (Non-preemptive)
void priorityScheduling(struct Process proc[], int n) {
    int currentTime = 0, completed = 0;
    int totalTurnaround = 0, totalWaiting = 0;
    int isCompleted[n];
    int sequence[n], seqIndex = 0;

    // Initialize isCompleted array
    for (int i = 0; i < n; i++) {
        isCompleted[i] = 0;
    }

    while (completed < n) {
        int minPriority = 1e9, idx = -1;

        // Find the highest priority process that has arrived and is not completed
        for (int i = 0; i < n; i++) {
            if (proc[i].arrival <= currentTime && !isCompleted[i] && proc[i].priority < minPriority) {
                minPriority = proc[i].priority;
                idx = i;
            }
        }

        if (idx != -1) {
            // Execute the selected process
            currentTime += proc[idx].burst;
            proc[idx].completion = currentTime;
            proc[idx].turnaround = proc[idx].completion - proc[idx].arrival;
            proc[idx].waiting = proc[idx].turnaround - proc[idx].burst;

            totalTurnaround += proc[idx].turnaround;
            totalWaiting += proc[idx].waiting;
            isCompleted[idx] = 1;
            sequence[seqIndex++] = proc[idx].pid;
            completed++;
        } else {
            // If no process is found, increment the current time
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
    }

    // Sort processes by arrival time and priority
    sortByArrivalAndPriority(proc, n);

    // Call the priority scheduling function
    priorityScheduling(proc, n);

    return 0;
}