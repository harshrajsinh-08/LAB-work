%{
#include <stdio.h>
#include <string.h>

int word_count = 0;

// Function to count vowels in a string
int count_vowels(char *word) {
    int count = 0;
    for(int i = 0; word[i] != '\0'; i++) {
        if(word[i] == 'a' || word[i] == 'e' || word[i] == 'i' || 
           word[i] == 'o' || word[i] == 'u' || word[i] == 'A' || 
           word[i] == 'E' || word[i] == 'I' || word[i] == 'O' || 
           word[i] == 'U') {
            count++;
        }
    }
    return count;
}
%}

%%

[a-zA-Z]+ {
    int vowels = count_vowels(yytext);
    if (vowels >= 2) {
        word_count++;
        printf("Found word with %d vowels: %s\n", vowels, yytext);
    }
}

[ \t\n]+    /* Skip whitespace */

.           /* Ignore other characters */

%%

int main(void) {
    yylex();
    printf("\nTotal words with at least two vowels: %d\n", word_count);
    return 0;
}

int yywrap() {
    return 1;
}