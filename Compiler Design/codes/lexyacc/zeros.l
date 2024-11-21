%{
#include <stdio.h>
int match=0;
%}

%%
[01]*10$   {  
    printf("Valid String ends with 10\n");
    printf("STRING : %s\n", yytext);
    match=1;
}

.|\n {}
%%

int main() {
    yylex();
    if(!match){
        printf("did not match");
    }
    return 0;
}

int yywrap() {
    return 1;
}