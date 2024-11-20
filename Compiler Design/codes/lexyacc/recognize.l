%{
#include <stdio.h>
int line_count = 1;

void print_token(char* token_type, char* value) {
    printf("Line %d: <%s, %s>\n", line_count, token_type, value);
}
%}

%%

"->"    { print_token("PRODUCTION_ARROW", yytext); }
"|"     { print_token("ALTERNATION", yytext); }
"{"     { print_token("LEFT_BRACE", yytext); }
"}"     { print_token("RIGHT_BRACE", yytext); }
"["     { print_token("LEFT_BRACKET", yytext); }
"]"     { print_token("RIGHT_BRACKET", yytext); }
"("     { print_token("LEFT_PAREN", yytext); }
")"     { print_token("RIGHT_PAREN", yytext); }
"+"     { print_token("ONE_OR_MORE", yytext); }
"*"     { print_token("ZERO_OR_MORE", yytext); }
"?"     { print_token("OPTIONAL", yytext); }

[A-Z][A-Z0-9_]*  { print_token("NON_TERMINAL", yytext); }
[a-z][a-z0-9_]*  { print_token("TERMINAL", yytext); }
\'[^']*\'        { print_token("LITERAL", yytext); }
\"[^"]*\"        { print_token("LITERAL", yytext); }

[\n]    { line_count++; }
[ \t]   ; /* Skip whitespace */
.       { printf("Line %d: Unexpected character: %s\n", line_count, yytext); }

%%

int main() {
    printf("Enter grammar (press Ctrl+D when done):\n");
    printf("Note: Use UPPERCASE for non-terminals and lowercase for terminals\n\n");
    yylex();
    return 0;
}

int yywrap() {
    return 1;
}