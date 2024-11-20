#include <stdio.h>

void bestfit(int bsize[],int blocks,int psize[],int process){
    int allocate[process];
    int occupied[blocks];
    for(int i=0;i<process;i++){
        allocate[i]=-1;
    }
    for(int i=0;i<blocks;i++){
        occupied[i]=0;
    }

    for (int i = 0; i < process; i++) {
    int indexPlaced = -1; // Initialize index of best block as -1
    for (int j = 0; j < blocks; j++) {
        if (bsize[j] >= psize[i] && !occupied[j]) {
            // If this block is smaller than the currently tracked best block
            if (indexPlaced == -1 || bsize[j] < bsize[indexPlaced]) {
                indexPlaced = j;
            }
        }
    }
    // If a suitable block is found
    if (indexPlaced != -1) {
        allocate[i] = indexPlaced;      // Assign the block to the process
        occupied[indexPlaced] = 1;       // Mark block as occupied
    }
}

    printf("\nProcess No.\tProcess Size\tBlock no.\n");
    for(int i=0;i<process;i++){
        printf("%d\t\t%d\t\t",i+1,psize[i]);
        if(allocate[i]!=-1){
            printf("%d\n",allocate[i]+1);
        }
        else{
            printf("Not allocated");
        }
    }
}
int main() {
    int blockSize[] = {100,50,30,120,35};
    int processSize[] = {40,10,30,60};
    int blocks = sizeof(blockSize) / sizeof(blockSize[0]);
    int processes = sizeof(processSize) / sizeof(processSize[0]);

    // Call the function to implement First Fit
    bestfit(blockSize, blocks, processSize, processes);

    return 0;
}