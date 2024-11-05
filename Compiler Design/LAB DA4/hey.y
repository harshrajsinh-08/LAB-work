%{
#include<stdio.h>
#include "hey.tab.h"
#include<stdlib.h>

void yyerror(char *);
int yylex();
%}

%token NUM
%%
S : ExprList { /* Do nothing */ };

ExprList : ExprList E '\n' { printf("Ans= %d\n", $2); }
         | E '\n' { printf("Ans= %d\n", $1); }
         | E { printf("Ans= %d\n", $1); }
         ;

E : E '+' T { $$ = $1 + $3; }
  | E '-' T { $$ = $1 - $3; }
  | T { $$ = $1; }
  ;

T : T '*' F { $$ = $1 * $3; }
  | T '/' F { $$ = $1 / $3; }
  | F { $$ = $1; }
  ;

F : '(' E ')' { $$ = $2; }
  | NUM { $$ = $1; }
  ;
%%
int main(int argc, char **argv)
{
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <input_file>\n", argv[0]);
        exit(1);
    }

    // Open the file for reading input
    FILE *input_file = fopen(argv[1], "r");
    if (!input_file) {
        perror("Failed to open input file");
        exit(1);
    }

    // Redirect the input stream to the file
    freopen(argv[1], "r", stdin);

    printf("Reading from file: %s\n", argv[1]);
    yyparse();

    fclose(input_file);  // Close the file after parsing
    return 0;
}

int yywrap()
{
    return 1;
}

void yyerror(char *err)
{
    fprintf(stderr, "Syntax error: %s\n", err);
}