%{
    #include "parser.tab.h"
    #include <stdio.h>
%}

%%
[a] { return A;}
[b] { return B;}
[ \t\n] {}
. {}


%%
int yywrap ()
{
return 1;
}