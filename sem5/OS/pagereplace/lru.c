
// Function to implement LRU page replacement
void lru(int pages[], int n, int frameSize) {
    int frame[frameSize], pageFaults = 0, time[frameSize];

    for (int i = 0; i < frameSize; i++) {
        frame[i] = -1;  // Initialize frame to -1
        time[i] = -1;   // Initialize access times to -1
    }

    printf("LRU:\n");
    for (int i = 0; i < n; i++) {
        if (!isPageInFrame(frame, frameSize, pages[i])) {
            // Find the least recently used page
            int lruIndex = 0;
            for (int j = 1; j < frameSize; j++) {
                if (time[j] < time[lruIndex]) {
                    lruIndex = j;
                }
            }
            frame[lruIndex] = pages[i];
            time[lruIndex] = i; // Update the time of use
            pageFaults++;
        } else {
            // Update time for the page
            for (int j = 0; j < frameSize; j++) {
                if (frame[j] == pages[i]) {
                    time[j] = i;
                    break;
                }
            }
        }

        // Print current frame status
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
    lru(pages, n, frameSize);

    return 0;
}