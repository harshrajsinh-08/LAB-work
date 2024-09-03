%{
#include <stdio.h>
%}

%%

"<"[^">"]+">" {
    printf("Tag: %s\n", yytext);
}

[ \t\n]+    {}

.           {}

%%

int main(int argc, char **argv) {
    if (argc > 1) {
        FILE *file = fopen(argv[1], "r");
        yyin = file;
    }
    yylex();

    if (argc > 1) {
        fclose(yyin);
    }
    return 0;
}

int yywrap() {
    return 1;
}