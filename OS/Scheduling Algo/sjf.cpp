#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

struct Process {
    int id;         // Process ID
    int arrival;    // Arrival Time
    int burst;      // Burst Time
    int wait;       // Waiting Time
    int turnaround; // Turnaround Time
};

bool compareByArrival(const Process &a, const Process &b) {
    return a.arrival < b.arrival;
}

bool compareByBurst(const Process &a, const Process &b) {
    return a.burst < b.burst;
}

int main() {
    int n;
    cout << "Enter number of processes: ";
    cin >> n;

    vector<Process> processes(n);

    cout << "Enter Arrival Time and Burst Time:" << endl;
    for (int i = 0; i < n; i++) {
        cout << "P" << i + 1 << " Arrival Time: ";
        cin >> processes[i].arrival;
        cout << "P" << i + 1 << " Burst Time: ";
        cin >> processes[i].burst;
        processes[i].id = i + 1;
    }

    // Sort processes by Arrival Time
    sort(processes.begin(), processes.end(), compareByArrival);

    int currentTime = 0;
    int total_wt = 0, total_tat = 0;
    vector<bool> completed(n, false);
    int completedCount = 0;

    while (completedCount < n) {
        int idx = -1;
        int min_burst = INT_MAX;

        for (int i = 0; i < n; i++) {
            if (!completed[i] && processes[i].arrival <= currentTime && processes[i].burst < min_burst) {
                min_burst = processes[i].burst;
                idx = i;
            }
        }

        if (idx == -1) {
            currentTime++;
            continue;
        }

        // Calculate wait and turnaround time for the selected process
        processes[idx].wait = currentTime - processes[idx].arrival;
        processes[idx].turnaround = processes[idx].wait + processes[idx].burst;
        currentTime += processes[idx].burst;

        total_wt += processes[idx].wait;
        total_tat += processes[idx].turnaround;

        completed[idx] = true;
        completedCount++;
    }

    cout << "P\tAT\tBT\tWT\tTAT" << endl;
    for (const auto& p : processes) {
        cout << "P" << p.id << "\t" << p.arrival << "\t" << p.burst << "\t"
             << p.wait << "\t" << p.turnaround << endl;
    }

    cout << "Average Waiting Time= " << (float)total_wt / n << endl;
    cout << "Average Turnaround Time= " << (float)total_tat / n << endl;

    return 0;
}