#include <iostream>
#include <vector>
#include <queue>
using namespace std;

struct Process {
    int id;           // Process ID
    int arrival;      // Arrival Time
    int burst;        // Burst Time
    int remaining;    // Remaining Burst Time
    int wait;         // Waiting Time
    int turnaround;   // Turnaround Time
};

int main() {
    int n, quantum;
    cout << "Enter number of processes: ";
    cin >> n;

    vector<Process> processes(n);

    cout << "Enter Arrival Time, Burst Time:" << endl;
    for (int i = 0; i < n; ++i) {
        cout << "P" << i + 1 << " Arrival Time: ";
        cin >> processes[i].arrival;
        cout << "P" << i + 1 << " Burst Time: ";
        cin >> processes[i].burst;
        processes[i].id = i + 1;
        processes[i].remaining = processes[i].burst;
        processes[i].wait = 0;
        processes[i].turnaround = 0;
    }

    cout << "Enter Time Quantum: ";
    cin >> quantum;

    int currentTime = 0;
    int total_wt = 0, total_tat = 0;
    queue<int> readyQueue;
    vector<bool> inQueue(n, false);
    int completed = 0;

    while (completed < n) {
        // Add all processes that have arrived to the queue
        for (int i = 0; i < n; ++i) {
            if (processes[i].arrival <= currentTime && !inQueue[i] && processes[i].remaining > 0) {
                readyQueue.push(i);
                inQueue[i] = true; // Mark as added to queue
            }
        }

        if (readyQueue.empty()) {
            ++currentTime; // Move time forward if no process is ready
            continue;
        }

        // Process the process at the front of the queue
        int idx = readyQueue.front();
        readyQueue.pop();

        int timeSlice = min(quantum, processes[idx].remaining);
        processes[idx].remaining -= timeSlice;
        currentTime += timeSlice;

        // Check if the process is completed
        if (processes[idx].remaining == 0) {
            processes[idx].turnaround = currentTime - processes[idx].arrival;
            processes[idx].wait = processes[idx].turnaround - processes[idx].burst;
            total_wt += processes[idx].wait;
            total_tat += processes[idx].turnaround;
            completed++;
        } else {
            // If not completed, add it back to the queue
            readyQueue.push(idx);
        }
    }

    // Output results
    cout << "P\tAT\tBT\tWT\tTAT" << endl;
    for (const auto& p : processes) {
        cout << "P" << p.id << "\t" << p.arrival << "\t" << p.burst << "\t"
             << p.wait << "\t" << p.turnaround << endl;
    }

    float avg_wt = static_cast<float>(total_wt) / n;
    float avg_tat = static_cast<float>(total_tat) / n;

    cout << "Average Waiting Time = " << avg_wt << endl;
    cout << "Average Turnaround Time = " << avg_tat << endl;

    return 0;
}