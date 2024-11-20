%{
#include <stdio.h>
#include <string.h>
#include <ctype.h>

// Function to convert string to uppercase
void convert_to_upper(char *str) {
    while (*str) {
        *str = toupper((unsigned char)*str);
        str++;
    }
}
%}

%%

"auto"|"break"|"case"|"char"|"const"|"continue"|"default"|"do"|"double"|"else"|"enum"|"extern"|"float"|"for"|"goto"|"if"|"int"|"long"|"register"|"return"|"short"|"signed"|"sizeof"|"static"|"struct"|"switch"|"typedef"|"union"|"unsigned"|"void"|"volatile"|"while" {
    char temp[100];
    strcpy(temp, yytext);
    convert_to_upper(temp);
    printf("%s", temp);
}

[a-zA-Z_][a-zA-Z0-9_]* {
    printf("%s", yytext);  /* Print identifiers as is */
}

[ \t\n]     { printf("%s", yytext); }  /* Print whitespace as is */
.           { printf("%s", yytext); }  /* Print other characters as is */

%%

int main() {
    printf("Enter C code (press Ctrl+D when done):\n");
    yylex();
    return 0;
}

int yywrap() {
    return 1;
}