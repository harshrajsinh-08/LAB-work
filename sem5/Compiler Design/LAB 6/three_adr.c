#include <stdio.h>
#include <string.h>

int main() {
    char expr[100];
    char tempVar[10];
    int tempCount = 1;

    printf("Enter an expression : ");
    fgets(expr, sizeof(expr), stdin);
    expr[strcspn(expr, "\n")] = '\0';

    char *lhs = strtok(expr, "=");
    char *rhs = strtok(NULL, "=");

    if (lhs == NULL || rhs == NULL) {
        printf("Invalid expression format.\n");
        return 1;
    }

    while (*lhs == ' ')
        lhs++;
    char *end = lhs + strlen(lhs) - 1;
    while (end > lhs && *end == ' ')
        end--;
    *(end + 1) = '\0';

    char *token = strtok(rhs, " +");
    char prevTemp[10] = "";

    while (token != NULL) {
        char currentTemp[10];
        if (tempCount == 1) {
            snprintf(prevTemp, sizeof(prevTemp), "%s", token);
        } else {
            snprintf(currentTemp, sizeof(currentTemp), "t%d", tempCount);
            printf("%s = %s + %s\n", currentTemp, prevTemp, token);
            snprintf(prevTemp, sizeof(prevTemp), "%s", currentTemp);
        }
        tempCount++;
        token = strtok(NULL, " +");
    }

    printf("%s = %s\n", lhs, prevTemp);

    return 0;
}