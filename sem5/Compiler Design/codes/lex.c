#include <stdio.h>
#include <ctype.h>
#include <string.h>

#define MAX_LEN 100
int isKeyword(const char *str) {
    const char *keywords[] = {"if", "else", "while", "for", "int", "float"};
    for (int i = 0; i < 6; i++) {
        if (strcmp(str, keywords[i]) == 0)
            return 1;
    }
    return 0;
}

// Function to tokenize the input string
void lexicalAnalyzer(const char *input) {
    char token[MAX_LEN];
    int i = 0, j = 0;

    while (input[i] != '\0') {
        if (isspace(input[i])) {
            i++;
            continue;
        }

        // Handle numbers
        if (isdigit(input[i])) {
            j = 0;
            while (isdigit(input[i])) {
                token[j++] = input[i++];
            }
            token[j] = '\0';
            printf("Number: %s\n", token);
            continue;
        }

        // Handle identifiers and keywords
        if (isalpha(input[i])) {
            j = 0;
            while (isalnum(input[i]) || input[i] == '_') {
                token[j++] = input[i++];
            }
            token[j] = '\0';
            if (isKeyword(token)) {
                printf("Keyword: %s\n", token);
            } else {
                printf("Identifier: %s\n", token);
            }
            continue;
        }

        // Handle operators
        if (strchr("()+-*/=", input[i])) {
            printf("Operator: %c\n", input[i]);
            i++;
            continue;
        }

        // Handle invalid characters
        printf("Invalid: %c\n", input[i]);
        i++;
    }
}

int main() {
    char input[MAX_LEN];

    printf("Enter a string: ");
    fgets(input, MAX_LEN, stdin);

    lexicalAnalyzer(input);

    return 0;
}