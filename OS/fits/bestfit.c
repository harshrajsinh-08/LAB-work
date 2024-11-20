#include <stdio.h>

// Function to implement Best Fit memory allocation
void implementBestFit(int blockSize[], int blocks, int processSize[], int processes) {
    // This will store the block ID of the allocated block to a process
    int allocation[processes];
    int occupied[blocks];

    // Initially assigning -1 to all allocation indexes (indicates no allocation)
    for (int i = 0; i < processes; i++) {
        allocation[i] = -1;
    }

    // Mark all blocks as unoccupied
    for (int i = 0; i < blocks; i++) {
        occupied[i] = 0;
    }

    // Iterate over each process
    for (int i = 0; i < processes; i++) {
        int indexPlaced = -1;  // Variable to track the best fit block

        // Iterate over all blocks to find the best fit for the current process
        for (int j = 0; j < blocks; j++) {
            if (blockSize[j] >= processSize[i] && !occupied[j]) {
                // If this is the first suitable block, or a smaller suitable block is found
                if (indexPlaced == -1 || blockSize[j] < blockSize[indexPlaced]) {
                    indexPlaced = j;
                }
            }
        }

        // If a suitable block is found
        if (indexPlaced != -1) {
            // Allocate this block to the current process
            allocation[i] = indexPlaced;

            // Mark the block as occupied
            occupied[indexPlaced] = 1;
        }
    }

    // Display allocation results
    printf("\nProcess No.\tProcess Size\tBlock no.\n");
    for (int i = 0; i < processes; i++) {
        printf("%d\t\t%d\t\t", i + 1, processSize[i]);
        if (allocation[i] != -1) {
            printf("%d\n", allocation[i] + 1);  // Display block index + 1
        } else {
            printf("Not Allocated\n");
        }
    }
}

int main() {
    int blockSize[] = {100,50,30,120,35};
    int processSize[] = {40,10,30,60};
    int blocks = sizeof(blockSize) / sizeof(blockSize[0]);
    int processes = sizeof(processSize) / sizeof(processSize[0]);

    // Call the function to implement Best Fit
    implementBestFit(blockSize, blocks, processSize, processes);

    return 0;
}