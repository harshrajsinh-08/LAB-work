#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

int delimiter(char ch) {
    return (ch == ' ' || ch == '\t' || ch == '\n');
}

int operator(char ch) {
    return (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '=' || ch == '<' || ch == '>');
}

int Keywrd(const char* str) {
    const char* keywords[] = {"int", "float", "if", "else", "while", "for", "return", "include"};
    for (int i = 0; i < sizeof(keywords) / sizeof(keywords[0]); i++) {
        if (strcmp(keywords[i], str) == 0) {
            return 1;
        }
    }
    return 0;
}

void read_file(const char* filename) {
    FILE* fp;
    char ch;
    fp = fopen(filename, "r");

    if (fp == NULL) {
        printf("Error: File not found!\n");
        exit(0);
    }

    while ((ch = fgetc(fp)) != EOF) {
        if (delimiter(ch)) {
            continue;
        }

        else if (operator(ch)) {
            printf("Operator : '%c'\n", ch);
        }

        else if (isalpha(ch)) {
            char word[100];
            int i = 0;

            word[i++] = ch;
            while ((ch = fgetc(fp)) != EOF && (isalnum(ch) || ch == '_')) {
                word[i++] = ch;
            }
            word[i] = '\0';

            if (Keywrd(word)) {
                printf("Keyword : \"%s\"\n", word);
            } else {
                printf("Identifier : \"%s\"\n", word);
            }
            
            ungetc(ch, fp);
        }

        else if (isdigit(ch)) {
            char number[100];
            int i = 0;

            number[i++] = ch;
            while ((ch = fgetc(fp)) != EOF && isdigit(ch)) {
                number[i++] = ch;
            }
            number[i] = '\0';

            printf("Number : \"%s\"\n", number);

            ungetc(ch, fp);
        }
    }

    fclose(fp);
}

int main() {
    char filename[] = "input.txt";
    read_file(filename);
    return 0;
}
