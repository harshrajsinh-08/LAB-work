#include <stdio.h>
struct Process
{
    int pid;
    int bt;
    int at;
    int ct;
    int tat;
    int wt;
    int rt;
};

void findWaitingTime(struct Process proc[], int n)
{
    int complete = 0, t = 0, minm = 10000;
    int shortest = 0, finish_time;
    int check = 0;
    
    for (int i = 0; i < n; i++)
    {
        proc[i].rt = proc[i].bt;
    }

    while (complete != n)
    {
        for (int j = 0; j < n; j++)
        {
            if ((proc[j].at <= t) && (proc[j].rt < minm) && proc[j].rt > 0)
            {
                minm = proc[j].rt;
                shortest = j;
                check = 1;
            }
        }

        if (check == 0)
        {
            t++;
            continue;
        }

        proc[shortest].rt--;
        minm = proc[shortest].rt;

        if (minm == 0)
        {
            minm = 10000;
        }

        if (proc[shortest].rt == 0)
        {
            complete++;
            check = 0;
            finish_time = t + 1;
            proc[shortest].wt = finish_time - proc[shortest].bt - proc[shortest].at;

            if (proc[shortest].wt < 0)
                proc[shortest].wt = 0;
        }

        t++;
    }
}

void findTurnAroundTime(struct Process proc[], int n)
{
    for (int i = 0; i < n; i++)
    {
        proc[i].tat = proc[i].bt + proc[i].wt;
    }
}

void findavgTime(struct Process proc[], int n)
{
    int total_wt = 0, total_tat = 0;

    findWaitingTime(proc, n);
    findTurnAroundTime(proc, n);

    printf("Processes Arrival Time Burst Time Waiting Time Turnaround Time\n");

    for (int i = 0; i < n; i++)
    {
        total_wt += proc[i].wt;
        total_tat += proc[i].tat;
        printf(" %d\t\t%d\t %d\t\t%d\t\t%d\n", proc[i].pid, proc[i].at, proc[i].bt, proc[i].wt, proc[i].tat);
    }

    printf("\nAverage waiting time = %.2f", (float)total_wt / n);
    printf("\nAverage turnaround time = %.2f\n", (float)total_tat / n);
}

int main()
{
    int n;
    printf("Enter number of processes: ");
    scanf("%d", &n);
    struct Process proc[n];

    for (int i = 0; i < n; i++)
    {
        printf("Enter arrival time and burst time for process %d: ", i + 1);
        scanf("%d %d", &proc[i].at, &proc[i].bt);
        proc[i].pid = i + 1;
    }

    findavgTime(proc, n);
    return 0;
}