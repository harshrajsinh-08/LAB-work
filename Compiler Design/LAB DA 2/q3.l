%{
#include <stdio.h>
#include <ctype.h>
#include <string.h>

int wc = 0;
int vow_count(char *word) {
    int count = 0;
    for (int i = 0; i < strlen(word); i++) {
        char ch = tolower(word[i]);
        if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
            count++;
        }
    }
    return count;
}
%}

%%

[a-zA-Z]+ {
    if (vow_count(yytext) >= 2) {
        wc++;
        printf("Word: %s\n", yytext);
    }
}

[ \t\n]+    {}
.           {}

%%

int main(int argc, char **argv) {
    if (argc > 1) {
        FILE *file = fopen(argv[1], "r");
        yyin = file;
    } else {
        printf("Enter text:\n");
    }

    yylex();
    printf("Number of words with at least two vowels: %d\n", wc);

    if (argc > 1) {
        fclose(yyin);
    }
    
    return 0;
}

int yywrap() {
    return 1;
}