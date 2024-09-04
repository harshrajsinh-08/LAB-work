#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

struct Process {
    int id;           // Process ID
    int arrival;      // Arrival Time
    int burst;        // Burst Time
    int priority;     // Priority
    int wait;         // Waiting Time
    int turnaround;   // Turnaround Time
};

// Comparator to sort by arrival time
bool compareByArrival(const Process &a, const Process &b) {
    return a.arrival < b.arrival;
}

// Comparator to sort by priority (lower number means higher priority)
bool compareByPriority(const Process &a, const Process &b) {
    return a.priority < b.priority;
}

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
    }

    // Sort processes by Arrival Time
    sort(processes.begin(), processes.end(), compareByArrival);

    int currentTime = 0;
    int total_wt = 0, total_tat = 0;
    vector<bool> completed(n, false);
    int completedCount = 0;

    while (completedCount < n) {
        // Find the process with the highest priority that has arrived
        Process *currentProcess = nullptr;
        for (int i = 0; i < n; i++) {
            if (!completed[i] && processes[i].arrival <= currentTime) {
                if (!currentProcess || processes[i].priority < currentProcess->priority) {
                    currentProcess = &processes[i];
                }
            }
        }

        if (!currentProcess) {
            currentTime++;
            continue;
        }

        // Process the selected process
        currentProcess->wait = currentTime - currentProcess->arrival;
        currentProcess->turnaround = currentProcess->wait + currentProcess->burst;
        currentTime += currentProcess->burst;

        total_wt += currentProcess->wait;
        total_tat += currentProcess->turnaround;

        completed[currentProcess->id - 1] = true;
        completedCount++;
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