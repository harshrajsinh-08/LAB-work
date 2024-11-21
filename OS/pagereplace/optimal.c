// Function to implement Optimal page replacement
void optimal(int pages[], int n, int frameSize) {
    int frame[frameSize], pageFaults = 0;

    for (int i = 0; i < frameSize; i++) frame[i] = -1; // Initialize frame to -1

    printf("Optimal:\n");
    for (int i = 0; i < n; i++) {
        if (!isPageInFrame(frame, frameSize, pages[i])) {
            if (i < frameSize) {
                frame[i] = pages[i]; // Directly insert the first few pages
            } else {
                // Determine which page to replace
                int farthest = -1, replaceIndex = -1;
                for (int j = 0; j < frameSize; j++) {
                    int k;
                    for (k = i + 1; k < n; k++) {
                        if (frame[j] == pages[k]) break;
                    }
                    if (k > farthest) {
                        farthest = k;
                        replaceIndex = j;
                    }
                }
                frame[replaceIndex] = pages[i];
            }
            pageFaults++;
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
    optimal(pages, n, frameSize);

    return 0;
}