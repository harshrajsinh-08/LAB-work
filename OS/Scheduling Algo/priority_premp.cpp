#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;

struct Process {
    int id;           // Process ID
    int arrival;      // Arrival Time
    int burst;        // Burst Time
    int priority;     // Priority
    int remaining;    // Remaining Burst Time
    int wait;         // Waiting Time
    int turnaround;   // Turnaround Time
};

// Comparator to sort by arrival time
bool compareByArrival(const Process &a, const Process &b) {
    return a.arrival < b.arrival;
}

// Comparator to sort by priority (lower number means higher priority)
struct ComparePriority {
    bool operator()(const Process &a, const Process &b) {
        return a.priority > b.priority;  // Lower number means higher priority
    }
};

int main() {
    int n;
    float avg_wt, avg_tat;
    vector<Process> processes;

    cout << "Enter number of processes: ";
    cin >> n;

    processes.resize(n);

    cout << "Enter Arrival Time, Burst Time, and Priority:" << endl;
    for (int i = 0; i < n; i++) {
        cout << "P" << i + 1 << " Arrival Time: ";
        cin >> processes[i].arrival;
        cout << "P" << i + 1 << " Burst Time: ";
        cin >> processes[i].burst;
        cout << "P" << i + 1 << " Priority: ";
        cin >> processes[i].priority;
        processes[i].id = i + 1;
        processes[i].remaining = processes[i].burst;
    }

    // Sort processes by Arrival Time
    sort(processes.begin(), processes.end(), compareByArrival);

    int currentTime = 0;
    int total_wt = 0, total_tat = 0;
    vector<bool> completed(n, false);
    int completedCount = 0;

    priority_queue<Process, vector<Process>, ComparePriority> pq;
    int index = 0;

    while (completedCount < n) {
        // Add all processes that have arrived to the priority queue
        while (index < n && processes[index].arrival <= currentTime) {
            pq.push(processes[index]);
            index++;
        }

        if (pq.empty()) {
            currentTime++;
            continue;
        }

        // Process the highest priority process
        Process currentProcess = pq.top();
        pq.pop();

        // Process the selected process
        currentProcess.remaining--;
        currentTime++;

        // Check if the process is completed
        if (currentProcess.remaining == 0) {
            currentProcess.turnaround = currentTime - currentProcess.arrival;
            currentProcess.wait = currentProcess.turnaround - currentProcess.burst;
            total_wt += currentProcess.wait;
            total_tat += currentProcess.turnaround;
            processes[currentProcess.id - 1] = currentProcess; // Update the process in the original vector
            completed[currentProcess.id - 1] = true;
            completedCount++;
        } else {
            // If the process is not completed, push it back to the priority queue
            pq.push(currentProcess);
        }
    }

    avg_wt = (float)total_wt / n;
    avg_tat = (float)total_tat / n;

    cout << "P\tAT\tBT\tPriority\tWT\tTAT" << endl;
    for (const auto& p : processes) {
        cout << "P" << p.id << "\t" << p.arrival << "\t" << p.burst << "\t"
             << p.priority << "\t\t" << p.wait << "\t" << p.turnaround << endl;
    }

    cout << "Average Waiting Time= " << avg_wt << endl;
    cout << "Average Turnaround Time= " << avg_tat << endl;

    return 0;
}