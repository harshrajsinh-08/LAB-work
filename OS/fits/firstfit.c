#include <stdio.h>

// Function to implement First Fit memory allocation
void implementFirstFit(int blockSize[], int blocks, int processSize[], int processes) {
    // This will store the block id of the allocated block to a process
    int allocate[processes];
    int occupied[blocks];

    // Initially assigning -1 to all allocation indexes (indicates no allocation)
    for (int i = 0; i < processes; i++) { 
        allocate[i] = -1;
    }

    // Mark all blocks as unoccupied
    for (int i = 0; i < blocks; i++) {
        occupied[i] = 0;
    }

    // Take each process one by one and find the first block that can accommodate it
    for (int i = 0; i < processes; i++) {
        for (int j = 0; j < blocks; j++) {
            if (!occupied[j] && blockSize[j] >= processSize[i]) {
                // Allocate block j to process i
                allocate[i] = j;
                occupied[j] = 1;  // Mark block as occupied
                break;
            }
        }
    }

    // Display allocation details
    printf("\nProcess No.\tProcess Size\tBlock no.\n");
    for (int i = 0; i < processes; i++) {
        printf("%d\t\t%d\t\t", i + 1, processSize[i]);
        if (allocate[i] != -1) {
            printf("%d\n", allocate[i] + 1);  // Display block index + 1
        } else {
            printf("Not Allocated\n");
        }
    }
}

int main() {
    int blockSize[] = {30, 5, 10};
    int processSize[] = {10, 6, 9};
    int blocks = sizeof(blockSize) / sizeof(blockSize[0]);
    int processes = sizeof(processSize) / sizeof(processSize[0]);

    // Call the function to implement First Fit
    implementFirstFit(blockSize, blocks, processSize, processes);

    return 0;
}