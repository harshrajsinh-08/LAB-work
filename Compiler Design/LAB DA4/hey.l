%{
#include<stdio.h>
#include "hey.tab.h"
extern int yylval;
#include<stdlib.h>
%}

D [0-9]+
NUM {D}+
AOP [-+*/]
PO [()]
%%
{NUM} {yylval=atoi(yytext); return(NUM);}
{AOP} return *yytext;
\n return *yytext;
{PO} return *yytext;
%%