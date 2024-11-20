%{
#include <stdio.h>
int integer_count = 0;
int real_count = 0;
int exponential_count = 0;
%}

INTEGER     [+-]?[0-9]+
REAL        [+-]?[0-9]+\.[0-9]+
EXP         [+-]?[0-9]+(\.[0-9]+)?[eE][+-]?[0-9]+

%%

{INTEGER}   {
    printf("Integer: %s\n", yytext);
    integer_count++;
}

{REAL}      {
    printf("Real Number: %s\n", yytext);
    real_count++; 
}

{EXP}       {
    printf("Exponential Number: %s\n", yytext);
    exponential_count++;
}

[ \t\n]+    { }   

.           { }   // Ignore other characters

%%

int main() {
    printf("Enter numbers (Ctrl+D to end):\n");
    yylex();
    printf("\nSummary:\n");
    printf("Total Integers: %d\n", integer_count);
    printf("Total Real Numbers: %d\n", real_count);
    printf("Total Exponential Numbers: %d\n", exponential_count);
    return 0;
}

int yywrap() {
    return 1;
}