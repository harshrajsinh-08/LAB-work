#include <stdio.h>
#include <stdbool.h>
void calc(int need[][10], int max[][10], int allocation[][10], int m, int n)
{
    for (int i = 0; i < m; i++)
    {
        for (int j = 0; j < n; j++)
        {
            need[i][j] = max[i][j] - allocation[i][j];
        }
    }
}
bool isSafe(int processes[], int available[], int max[][10], int alloc[][10], int m, int n)
{
    int need[10][10];
    calc(need, max, alloc, m, n);
    bool finish[10] = {0};
    int safeSeq[10];
    int work[10];
    for (int i = 0; i < n; i++)
        work[i] = available[i];
    int count = 0;
    while (count < m)
    {
        bool found = false;
        for (int p = 0; p < m; p++)
        {
            if (finish[p] == 0)
            {
                int j;
                for (j = 0; j < n; j++)
                    if (need[p][j] > work[j])
                        break;
                if (j == n)
                {
                    for (int k = 0; k < n; k++)
                        work[k] += alloc[p][k];
                    safeSeq[count++] = processes[p];
                    finish[p] = 1;
                    found = true;
                }
            }
        }
        if (found == false)
        {
            printf("System is not in a safe state.\n");
            return false;
        }
    }
    printf("System is in a safe state.\nSafe sequence is: ");
    for (int i = 0; i < m; i++)
        printf("P%d ", safeSeq[i]);
    printf("\n");
    return true;
}
int main()
{

    int m, n;
    printf("Enter the number of processes: ");
    scanf("%d", &m);
    printf("Enter the number of resource types: ");
    scanf("%d", &n);
    int processes[m];
    for (int i = 0; i < m; i++)
        processes[i] = i;
    int allocation[10][10], max[10][10], available[10];
    printf("Enter the Allocation Matrix:\n");
    for (int i = 0; i < m; i++)
    {
        for (int j = 0; j < n; j++)
        {
            scanf("%d", &allocation[i][j]);
        }
    }
    printf("Enter the Maximum Matrix:\n");
    for (int i = 0; i < m; i++)
    {
        for (int j = 0; j < n; j++)
        {
            scanf("%d", &max[i][j]);
        }
    }
    printf("Enter the available Resources:\n");
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &available[i]);
    }
    isSafe(processes, available, max, allocation, m, n);
    return 0;
}
