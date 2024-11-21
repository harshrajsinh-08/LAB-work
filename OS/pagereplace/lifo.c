// Function to implement LIFO page replacement
void lifo(int pages[], int n, int frameSize) {
    int frame[frameSize], pageFaults = 0, top = -1;

    for (int i = 0; i < frameSize; i++) frame[i] = -1; // Initialize frame to -1

    printf("LIFO:\n");
    for (int i = 0; i < n; i++) {
        if (!isPageInFrame(frame, frameSize, pages[i])) {
            // Replace the most recently added page (LIFO logic)
            top = (top + 1) % frameSize; // Use a circular stack to replace pages
            frame[top] = pages[i];
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
    lifo(pages, n, frameSize);

    return 0;
}