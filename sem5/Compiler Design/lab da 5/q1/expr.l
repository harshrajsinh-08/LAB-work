%{
    #include "expr.h"
    #include "expr.tab.h"
%}

%%
[0-9]+        { yylval.ival = atoi(yytext); return NUMBER; }
[a-zA-Z]+     { strcpy(yylval.sval, yytext); return VARIABLE; }
"+"           { return PLUS; }
"-"           { return MINUS; }
"*"           { return MULT; }
"/"           { return DIV; }
"("           { return LPAREN; }
")"           { return RPAREN; }
[ \t\n]+      ;
.             { printf("Unexpected character: %s\n", yytext); return 0; }
%%

int yywrap() {
    return 1;
}
