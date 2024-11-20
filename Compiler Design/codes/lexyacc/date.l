%{
#include <stdio.h>
%}

D    [0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]

%%

{D}   {
    printf("Valid date: %s\n", yytext);
}

[^ \t\n]+ {
    printf("Invalid input: %s\n", yytext);
}

[\n]     {}

%%

int main() {
    printf("Enter input:\n");
    yylex();
    return 0;
}

int yywrap() {
    return 1;
}