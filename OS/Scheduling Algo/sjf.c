#include <stdio.h>

// Structure to represent a process
struct Process {
    int pid;         // Process ID
    int arrival;     // Arrival time
    int burst;       // Burst time
    int completion;  // Completion time
    int turnaround;  // Turnaround time
    int waiting;     // Waiting time
    int isCompleted; // Flag to check if the process is completed
};

// Function to sort processes based on arrival time and burst time
void sortProcesses(struct Process proc[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (proc[j].arrival > proc[j + 1].arrival || 
                (proc[j].arrival == proc[j + 1].arrival && proc[j].burst > proc[j + 1].burst)) {
                // Swap processes
                struct Process temp = proc[j];
                proc[j] = proc[j + 1];
                proc[j + 1] = temp;
            }
        }
    }
}

// Function to implement Non-Preemptive SJF Scheduling
void nonPreemptiveSJF(struct Process proc[], int n) {
    int currentTime = 0, completed = 0;
    int totalTurnaround = 0, totalWaiting = 0;
    int sequence[n], seqIndex = 0;

    while (completed < n) {
        // Find the process with the shortest burst time that has arrived and is not completed
        int shortest = -1;
        int minBurst = 1e9;

        for (int i = 0; i < n; i++) {
            if (proc[i].arrival <= currentTime && !proc[i].isCompleted && proc[i].burst < minBurst) {
                minBurst = proc[i].burst;
                shortest = i;
            }
        }

        if (shortest == -1) {
            // No process is available; increment time
            currentTime++;
        } else {
            // Process the selected shortest process
            proc[shortest].completion = currentTime + proc[shortest].burst;
            proc[shortest].turnaround = proc[shortest].completion - proc[shortest].arrival;
            proc[shortest].waiting = proc[shortest].turnaround - proc[shortest].burst;

            totalTurnaround += proc[shortest].turnaround;
            totalWaiting += proc[shortest].waiting;

            proc[shortest].isCompleted = 1;
            sequence[seqIndex++] = proc[shortest].pid;

            currentTime = proc[shortest].completion;
            completed++;
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
        proc[i].isCompleted = 0; // Initialize isCompleted to 0
    }

    // Sort the processes by arrival time and burst time
    sortProcesses(proc, n);

    // Call the scheduling function
    nonPreemptiveSJF(proc, n);

    return 0;
}