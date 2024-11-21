%{
#include <stdio.h>
int word_count = 0;
%}

%%
[a-zA-Z]*ab    { 
    word_count++; 
    printf("Found word: %s\n", yytext);
}

[ \t\n]+    ;  
.           ; 

%%

int main() {
    printf("Enter text (Ctrl+D to end):\n");
    yylex();
    printf("\nTotal number of words ending with 'ab': %d\n", word_count);
    return 0;
}

int yywrap() {
    return 1;
}