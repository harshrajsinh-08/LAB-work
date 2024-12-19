%{
#include "y.tab.h"  // Include Yacc token definitions
%}

a     { return 'a'; }  // Return token 'a'
b     { return 'b'; }  // Return token 'b'
\n    { return '\n'; } // Return token for newline
.     { printf("Invalid character: %s\n", yytext); exit(1); }  // Handle invalid characters
%%