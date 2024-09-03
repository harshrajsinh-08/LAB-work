%{
#include <stdio.h>
#include <string.h>

int op = 0;
int id = 0;
int kw = 0;
int spc_sym = 0;

char *keywords[] = {
    "auto", "break", "case", "char", "const", "continue", "default", "do", "double", 
    "else", "enum", "extern", "float", "for", "goto", "if", "inline", "int", "long", 
    "register", "restrict", "return", "short", "signed", "sizeof", "static", "struct", 
    "switch", "typedef", "union", "unsigned", "void", "volatile", "while", "_Alignas", 
    "_Alignof", "_Atomic", "_Bool", "_Complex", "_Generic", "_Imaginary", "_Noreturn", 
    "_Static_assert", "_Thread_local"
};

int is_keyword(char *str) {
    for (int i = 0; i < sizeof(keywords) / sizeof(char*); i++) {
        if (strcmp(str, keywords[i]) == 0) {
            return 1;
        }
    }
    return 0;
}
%}

%%

"+"|"-"|"*"|"/"|"%"|"="|"<"|">"|"!"|"&"|"|"|"^"|"~" { op++; }
"++"|"--"|"=="|"!="|"<="|">="|"&&"|"||"             { op++; }

[a-zA-Z_][a-zA-Z0-9_]* { 
    if (is_keyword(yytext)) 
        kw++;
    else 
        id++;
}

"{"|"}"|"("|")"|"["|"]"|";"|","|"."|"#"             { spc_sym++; }

[ \t\n]            {}

.                  {}

%%

int main(int argc, char **argv) {
    if (argc > 1) {
        FILE *file = fopen(argv[1], "r");
        yyin = file;
    } else {
        printf("Usage: %s <filename>\n", argv[0]);
        return 1;
    }

    yylex();
    printf("Number of operators: %d\n", op);
    printf("Number of identifiers: %d\n", id);
    printf("Number of keywords: %d\n", kw);
    printf("Number of special symbols: %d\n", spc_sym);
    return 0;
}

int yywrap() {
    return 1;
}