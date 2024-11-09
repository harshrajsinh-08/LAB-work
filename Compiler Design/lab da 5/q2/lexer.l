%{
#include "parser.tab.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
%}
%%
[0-9]+      { yylval.num = atoi(yytext); return NUMBER; }
[a-zA-Z]    { 
              strcpy(yylval.var_name, yytext);
              return ID; 
            }
"+"         { return PLUS; }
"-"         { return MINUS; }
"*"         { return MULTIPLY; }
"/"         { return DIVIDE; }
"("         { return LPAREN; }
")"         { return RPAREN; }
[ \t\n]     ;
.           { return *yytext; }
%%