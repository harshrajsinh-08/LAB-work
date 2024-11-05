#include <stdio.h>
#include <stdbool.h>

struct Process
{
    int pid;
    int arv;
    int brst;
    int pri;
    int comp;
    int turnar;
    int wait;
    int start;
    bool execu;
};

int findNextProcess(struct Process proc[], int n, int currentTime)
{
    int highestpri = -1;
    int minpri = 100000;
    
    for (int i = 0; i < n; i++)
    {
        if (proc[i].arv <= currentTime && !proc[i].execu && proc[i].pri < minpri)
        {
            minpri = proc[i].pri;
            highestpri = i;
        }
    }
    return highestpri;
}

void priScheduling(struct Process proc[], int n)
{
    int currentTime = 0;
    int totalturnar = 0, totalwait = 0;
    int completed = 0;
    int order[n]; 

    while (completed < n)
    {
        int idx = findNextProcess(proc, n, currentTime);
        
        if (idx == -1)
        {
            currentTime++;
            continue;
        }

        proc[idx].start = currentTime;
        proc[idx].comp = currentTime + proc[idx].brst;
        proc[idx].turnar = proc[idx].comp - proc[idx].arv;
        proc[idx].wait = proc[idx].turnar - proc[idx].brst;
        totalturnar += proc[idx].turnar;
        totalwait += proc[idx].wait;
        currentTime = proc[idx].comp;
        proc[idx].execu = true;
        order[completed] = idx;
        completed++;
    }

    printf("\nPID\tarv\tbrst\tpri\tcomp\tturnar\twait\n");
    for (int i = 0; i < n; i++)
    {
        printf("%d\t%d\t%d\t%d\t%d\t%d\t%d\n",
               proc[i].pid, proc[i].arv, proc[i].brst, proc[i].pri,
               proc[i].comp, proc[i].turnar, proc[i].wait);
    }

    printf("\nAverage turnaround Time: %.2f\n", (float)totalturnar / n);
    printf("Average wait Time: %.2f\n", (float)totalwait / n);
}

int main()
{
    int n;
    struct Process proc[100];

    printf("Enter number of processes: ");
    scanf("%d", &n);

    for (int i = 0; i < n; i++)
    {
        proc[i].pid = i + 1;
        proc[i].execu = false;
        printf("Enter arival Time, burst Time, and priority of process %d: ", proc[i].pid);
        scanf("%d %d %d", &proc[i].arv, &proc[i].brst, &proc[i].pri);
    }

    priScheduling(proc, n);

    return 0;
}