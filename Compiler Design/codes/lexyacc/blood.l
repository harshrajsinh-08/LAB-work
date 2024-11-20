%{
#include <stdio.h>
int valid_count = 0;
int invalid_count = 0;
%}

%%
(A|B|O|AB)[+-]    { 
    valid_count++; 
    printf("Valid Blood Group: %s\n", yytext);
}

[^ \t\n]+    { 
    invalid_count++; 
    printf("Invalid Blood Group: %s\n", yytext);
}

[ \t\n]+    ;  /* Ignore whitespace */

%%

int main() {
    printf("Enter blood groups (Ctrl+D to end):\n");
    yylex();
    printf("\nValid Blood Groups: %d\n", valid_count);
    printf("Invalid Blood Groups: %d\n", invalid_count);
    return 0;
}

int yywrap() {
    return 1;
}