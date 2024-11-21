#include <stdio.h>
#include <limits.h>

// Function to check if a page is already in the frame
int isPageInFrame(int frame[], int frameSize, int page) {
    for (int i = 0; i < frameSize; i++) {
        if (frame[i] == page) {
            return 1; 
        }
    }
    return 0;
}

// Function to implement FIFO page replacement
void fifo(int pages[], int n, int frameSize) {
    int frame[frameSize], pageFaults = 0, index = 0;

    for (int i = 0; i < frameSize; i++) frame[i] = -1; // Initialize frame to -1

    printf("FIFO:\n");
    for (int i = 0; i < n; i++) {
        if (!isPageInFrame(frame, frameSize, pages[i])) {
            // Replace the oldest page in the frame (FIFO logic)
            frame[index] = pages[i];
            index = (index + 1) % frameSize; // Move to the next frame position in circular fashion
            pageFaults++;
        }

        printf("Page %d -> Frame: ", pages[i]);
        for (int j = 0; j < frameSize; j++) {
            if (frame[j] != -1) printf("%d ", frame[j]);
            else printf("- ");
        }
        printf("\n");
    }
    printf("Total Page Faults: %d\n\n", pageFaults);
}

int main() {
    int n, frameSize;

    // Input the number of pages and the pages themselves
    printf("Enter the number of pages: ");
    scanf("%d", &n);
    int pages[n];
    printf("Enter the page reference string: ");
    for (int i = 0; i < n; i++) {
        scanf("%d", &pages[i]);
    }
    printf("Enter the frame size: ");
    scanf("%d", &frameSize);
    fifo(pages, n, frameSize);

    return 0;
}