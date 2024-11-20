#include <stdio.h>
#include <limits.h>

// Structure to represent a process
struct Process {
    int pid;         // Process ID
    int arrival;     // Arrival time
    int burst;       // Burst time
    int remaining;   // Remaining time
    int completion;  // Completion time
    int turnaround;  // Turnaround time
    int waiting;     // Waiting time
};

// Function to find the process with the shortest remaining time
int findShortestJob(struct Process proc[], int n, int currentTime) {
    int minTime = INT_MAX;
    int shortest = -1;

    for (int i = 0; i < n; i++) {
        if (proc[i].arrival <= currentTime && proc[i].remaining > 0 && proc[i].remaining < minTime) {
            minTime = proc[i].remaining;
            shortest = i;
        }
    }

    return shortest;
}

// Function to implement Preemptive Shortest Job First (SJF) Scheduling
void preemptiveSJF(struct Process proc[], int n) {
    int currentTime = 0, completed = 0;
    int totalTurnaround = 0, totalWaiting = 0;
    int sequence[1000], seqIndex = 0;

    while (completed < n) {
        // Find the shortest job available at the current time
        int shortest = findShortestJob(proc, n, currentTime);

        if (shortest == -1) {
            // No process is available; increment time
            currentTime++;
        } else {
            // Execute the shortest process for 1 unit of time
            proc[shortest].remaining--;
            sequence[seqIndex++] = proc[shortest].pid;

            // If the process is completed
            if (proc[shortest].remaining == 0) {
                completed++;
                proc[shortest].completion = currentTime + 1;
                proc[shortest].turnaround = proc[shortest].completion - proc[shortest].arrival;
                proc[shortest].waiting = proc[shortest].turnaround - proc[shortest].burst;

                totalTurnaround += proc[shortest].turnaround;
                totalWaiting += proc[shortest].waiting;
            }

            currentTime++;
        }
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
    int n;

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

    // Call the scheduling function
    preemptiveSJF(proc, n);

    return 0;
}