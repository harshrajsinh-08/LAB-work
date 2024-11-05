%{
#include <stdio.h>
#include <stdlib.h>
int yylex();
void yyerror (char *s);

const char* my_name = "HASRHRAJSINH ZALA 22BCE2238";
%}

%token A B

%%

S: A Q N { printf("Valid string- HASRHRAJSINH ZALA 22BCE2238\n"); }
;
Q : Q B {}
| {}
;
N : {}
;

%%
int main() {
printf("Enter the string: \n");
yyparse();
return 0;
}
void yyerror (char *s) {
fprintf(stderr, "Invalid string\n");
fprintf(stderr, "Error detected by %s\n", my_name);
}