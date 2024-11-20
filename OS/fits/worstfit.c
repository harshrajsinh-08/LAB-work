#include <stdio.h>

void implementWorstFit(int blockSize[], int blocks, int processSize[], int processes) {
    // This will store the block id of the allocated block to a process
    int allocation[processes];

    // Initially assigning -1 to all allocation indexes
    // Means nothing is allocated currently
    for (int i = 0; i < processes; i++) {
        allocation[i] = -1;
    }

    // Pick each process and find suitable blocks according to its size and assign to it
    for (int i = 0; i < processes; i++) {
        int indexPlaced = -1;

        // Look for the worst fit for the current process (i)
        for (int j = 0; j < blocks; j++) {
            // Check if the block can accommodate the process and is not yet allocated
            if (blockSize[j] >= processSize[i] && blockSize[j] != -1) {
                // Place it at the first suitable block
                if (indexPlaced == -1) {
                    indexPlaced = j;
                }
                // If any future block is larger than the current block, update indexPlaced
                else if (blockSize[j] > blockSize[indexPlaced]) {
                    indexPlaced = j;
                }
            }
        }

        // If a suitable block is found
        if (indexPlaced != -1) {
            // Allocate this block to the process
            allocation[i] = indexPlaced;

            // Reduce the block size by the size of the process
            blockSize[indexPlaced] -= processSize[i];

            // If the block size becomes 0, mark it as fully occupied (no further processes can fit)
            if (blockSize[indexPlaced] == 0) {
                blockSize[indexPlaced] = -1; // Block is now fully occupied
            }
        }
    }

    // Print allocation results
    printf("\nProcess No.\tProcess Size\tBlock No.\n");
    for (int i = 0; i < processes; i++) {
        printf("%d\t\t%d\t\t", i + 1, processSize[i]);
        if (allocation[i] != -1) {
            printf("%d\n", allocation[i] + 1); // Block numbers are 1-based
        } else {
            printf("Not Allocated\n");
        }
    }
}

int main() {
    int blockSize[] = {100, 50, 30, 120, 35};  // Block sizes
    int processSize[] = {40, 10, 30, 60};     // Process sizes
    int blocks = sizeof(blockSize) / sizeof(blockSize[0]);
    int processes = sizeof(processSize) / sizeof(processSize[0]);

    implementWorstFit(blockSize, blocks, processSize, processes);

    return 0;
}