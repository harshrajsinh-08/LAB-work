#include <iostream>
#include <vector>
#include <algorithm>
#include <climits> // For INT_MAX
using namespace std;

struct Process {
    int id;           // Process ID
    int arrival;      // Arrival Time
    int burst;        // Burst Time
    int remaining;    // Remaining Burst Time
    int wait;         // Waiting Time
    int turnaround;   // Turnaround Time
};

// Comparator to sort by arrival time
bool compareByArrival(const Process &a, const Process &b) {
    return a.arrival < b.arrival;
}

int main() {
    int n;
    float avg_wt, avg_tat;
    vector<Process> processes;

    cout << "Enter number of processes: ";
    cin >> n;

    processes.resize(n);

    cout << "Enter Arrival Time and Burst Time:" << endl;
    for (int i = 0; i < n; i++) {
        cout << "P" << i + 1 << " Arrival Time: ";
        cin >> processes[i].arrival;
        cout << "P" << i + 1 << " Burst Time: ";
        cin >> processes[i].burst;
        processes[i].id = i + 1;
        processes[i].remaining = processes[i].burst;
    }

    // Sort processes by Arrival Time
    sort(processes.begin(), processes.end(), compareByArrival);

    int currentTime = 0;
    int total_wt = 0, total_tat = 0;
    vector<bool> completed(n, false);
    int completedCount = 0;

    while (completedCount < n) {
        int idx = -1;
        int min_remaining = INT_MAX;

        // Find the process with the smallest remaining time that has arrived
        for (int i = 0; i < n; i++) {
            if (!completed[i] && processes[i].arrival <= currentTime && processes[i].remaining < min_remaining) {
                min_remaining = processes[i].remaining;
                idx = i;
            }
        }

        if (idx == -1) {
            currentTime++;
            continue;
        }

        // Process the selected process
        processes[idx].remaining--;
        currentTime++;

        // Check if the process is completed
        if (processes[idx].remaining == 0) {
            processes[idx].turnaround = currentTime - processes[idx].arrival;
            processes[idx].wait = processes[idx].turnaround - processes[idx].burst;
            total_wt += processes[idx].wait;
            total_tat += processes[idx].turnaround;
            completed[idx] = true;
            completedCount++;
        }
    }

    avg_wt = (float)total_wt / n;
    avg_tat = (float)total_tat / n;

    cout << "P\tAT\tBT\tWT\tTAT" << endl;
    for (const auto& p : processes) {
        cout << "P" << p.id << "\t" << p.arrival << "\t" << p.burst << "\t"
             << p.wait << "\t" << p.turnaround << endl;
    }

    cout << "Average Waiting Time= " << avg_wt << endl;
    cout << "Average Turnaround Time= " << avg_tat << endl;

    return 0;
}