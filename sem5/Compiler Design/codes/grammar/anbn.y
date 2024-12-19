%{
#include <stdio.h>
#include <stdlib.h>

void yyerror(const char *s);
%}

%token a b  // Tokens for 'a' and 'b'

%%
S : A '\n' { printf("Valid string\n"); }
  | '\n' { printf("Empty string is valid\n"); }
  ;

A : A 'a' 'b'
  | 'a' 'b'
  ;
%%

int main() {
    yyparse();
    return 0;
}

void yyerror(const char *s) {
    fprintf(stderr, "Syntax error: %s\n", s);
}