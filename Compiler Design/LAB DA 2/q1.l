%{
#include <stdio.h>

int vc = 0;
int conso = 0;
%}

%%
[aeiouAEIOU]      { vc++; }
[a-zA-Z]          { conso++; }
.                 { }
\n                { }
%%

int main(void) {
    printf("Enter a string: ");
    yylex();
    printf("Number of vowels: %d\n", vc);
    printf("Number of consonants: %d\n", conso);
    return 0;
}

int yywrap() {
    return 1;
}