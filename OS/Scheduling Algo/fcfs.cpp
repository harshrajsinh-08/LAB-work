#include <iostream>
using namespace std;

int main() {

    // Matrix for storing Process Id, Arrival Time,
    // Burst Time, Waiting Time, and Turnaround Time.
    int A[100][5];
    int i, n, total = 0;
    float avg_wt, avg_tat;

    cout << "Enter number of processes: ";
    cin >> n;

    cout << "Enter Arrival Time and Burst Time:" << endl;

    // User Input Arrival Time and Burst Time, and assigning Process Id.
    for (i = 0; i < n; i++) {
        cout << "P" << i + 1 << " Arrival Time: ";
        cin >> A[i][1];  // Arrival Time

        cout << "P" << i + 1 << " Burst Time: ";
        cin >> A[i][2];  // Burst Time

        A[i][0] = i + 1;  // Process ID
    }

    // Sorting processes based on Arrival Time to simulate FCFS behavior
    for (i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (A[i][1] > A[j][1]) {
                for (int k = 0; k < 5; k++) {
                    swap(A[i][k], A[j][k]);
                }
            }
        }
    }

    // Calculation of Waiting Times
    A[0][3] = 0;  // Waiting time for the first process is 0

    for (i = 1; i < n; i++) {
        A[i][3] = A[i-1][3] + A[i-1][2];
        if (A[i][3] < A[i][1]) {
            A[i][3] = A[i][1] - A[i][1];
        } else {
            A[i][3] -= A[i][1]; // Adjusting waiting time considering arrival time
        }
        total += A[i][3];
    }

    avg_wt = (float)total / n;
    total = 0;

    cout << "P\tAT\tBT\tWT\tTAT" << endl;

    // Calculation of Turnaround Time and printing the data.
    for (i = 0; i < n; i++) {
        A[i][4] = A[i][2] + A[i][3];  // Turnaround time = Burst Time + Waiting Time
        total += A[i][4];
        cout << "P" << A[i][0] << "\t" << A[i][1] << "\t" << A[i][2] << "\t" << A[i][3] << "\t" << A[i][4] << endl;
    }

    avg_tat = (float)total / n;

    cout << "Average Waiting Time= " << avg_wt << endl;
    cout << "Average Turnaround Time= " << avg_tat << endl;

    return 0;
}