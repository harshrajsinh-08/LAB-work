%{
#include <stdio.h>

int wc = 0;
%}

%%
[cC][a-zA-Z]*[tT] {
    wc++;
    printf("Word: %s\n", yytext);
}
[ \t\n]+    {}
.           {}
%%

int main(int argc, char **argv) {
    if (argc > 1) {
        FILE *file = fopen(argv[1], "r");
        yyin = file;
    } else {
        printf("Enter text :\n");
    }

    yylex();
    printf("Number of words starting with 'c' or 'C' and ending with 't' or 'T': %d\n", wc);

    if (argc > 1) {
        fclose(yyin);
    }

    return 0;
}

int yywrap() {
    return 1;
}