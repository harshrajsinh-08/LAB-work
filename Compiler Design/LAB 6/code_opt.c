#include <stdio.h>
#include <string.h>
#define MAX 100

typedef struct {
    char left[10];
    char right[20];
} Code;

Code codes[MAX];
int n;

void inputcode() {
    printf("Enter the Number of Values: ");
    scanf("%d", &n);
    getchar();
    for (int i = 0; i < n; i++) {
        printf("left: ");
        fgets(codes[i].left, 10, stdin);
        codes[i].left[strcspn(codes[i].left, "\n")] = '\0';
        printf("right: ");
        fgets(codes[i].right, 20, stdin);
        codes[i].right[strcspn(codes[i].right, "\n")] = '\0';
    }
}

void displaycode() {
    for (int i = 0; i < n; i++) {
        printf("%s = %s\n", codes[i].left, codes[i].right);
    }
}

void deadCodeElimination() {
    int used[MAX] = {0};
    for (int i = n - 1; i >= 0; i--) {
        if (used[i] == 0) {
            for (int j = i + 1; j < n; j++) {
                if (strstr(codes[j].right, codes[i].left)) {
                    used[i] = 1;
                    break;
                }
            }
        }
    }
    printf("\nAfter Dead Code Elimination\n");
    for (int i = 0; i < n; i++) {
        if (used[i] || i == n - 1) {
            printf("%s = %s\n", codes[i].left, codes[i].right);
        }
    }
}

void commonSubExpressionElimination() {
    printf("\nEliminate Common Expression\n");
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (strcmp(codes[i].right, codes[j].right) == 0) {
                strcpy(codes[j].right, codes[i].left);
            }
        }
    }
    for (int i = 0; i < n; i++) {
        printf("%s = %s\n", codes[i].left, codes[i].right);
    }
}

int main() {
    inputcode();
    printf("\nIntermediate Code\n");
    displaycode();
    deadCodeElimination();
    commonSubExpressionElimination();
    printf("\nOptimized Code\n");
    displaycode();
    return 0;
}