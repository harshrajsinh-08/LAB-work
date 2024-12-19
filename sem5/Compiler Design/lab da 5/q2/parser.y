
%{
    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>

    void yyerror(char *s);
    int yylex(void);
    int yywrap(void);

    #define MAX_QUAD 100
    #define MAX_POSTFIX 100

    struct quadruple {
        char op;
        char arg1[5]; 
        char arg2[5];
        char result[5];
    };

    struct quadruple quad[MAX_QUAD];
    int quad_index = 0;
    int temp_var = 1;
    char postfix[MAX_POSTFIX];
    int postfix_index = 0;

    char* new_temp();
    void emit(char op, const char* arg1, const char* arg2, const char* result);
    void quad();
    void machinecode();
    char last_temp[5];
%}

%union {
    char var_name[5];  
    int num;
}

%token <num> NUMBER
%token <var_name> ID
%token PLUS MINUS MULTIPLY DIVIDE
%token LPAREN RPAREN
%type <var_name> expr term factor
%%

start: expr    { printf("\nPostfix expression : %s\n", postfix); 
                 quad();
                 machinecode(); }
     ;

expr: expr PLUS term    { 
                         char* t = new_temp();
                         emit('+', $1, $3, t);
                         strcpy($$, t);
                         postfix[postfix_index++] = '+';
                       }
    | expr MINUS term   { 
                         char* t = new_temp();
                         emit('-', $1, $3, t);
                         strcpy($$, t);
                         postfix[postfix_index++] = '-';
                       }
    | term             { strcpy($$, $1); }
    ;

term: term MULTIPLY factor    { 
                               char* t = new_temp();
                               emit('*', $1, $3, t);
                               strcpy($$, t);
                               postfix[postfix_index++] = '*';
                             }
    | term DIVIDE factor      { 
                               char* t = new_temp();
                               emit('/', $1, $3, t);
                               strcpy($$, t);
                               postfix[postfix_index++] = '/';
                             }
    | factor                  { strcpy($$, $1); }
    ;

factor: LPAREN expr RPAREN    { strcpy($$, $2); }
      | ID                    { 
                               strcpy($$, $1);
                               postfix[postfix_index++] = $1[0];
                             }
      | NUMBER               { 
                              char num_str[5];
                              sprintf(num_str, "%d", $1);
                              char* t = new_temp();
                              emit('=', num_str, "_", t);
                              strcpy($$, t);
                              postfix[postfix_index++] = num_str[0];
                            }
      ;

%%

char* new_temp() {
    static char temp[5];
    sprintf(temp, "t%d", temp_var++);
    return temp;
}

void emit(char op, const char* arg1, const char* arg2, const char* result) {
    if (quad_index >= MAX_QUAD) {
        fprintf(stderr, "Error: Quadruple array full\n");
        exit(1);
    }
    quad[quad_index].op = op;
    strcpy(quad[quad_index].arg1, arg1);
    strcpy(quad[quad_index].arg2, arg2);
    strcpy(quad[quad_index].result, result);
    quad_index++;
}

void quad() {
    printf("\nQuadruple table :\n");
    for(int i = 0; i < quad_index; i++) {
        if (quad[i].op == '=') {
            printf("%d) %c %s %s %s\n", i, 
                   quad[i].op,
                   quad[i].arg1, 
                   quad[i].arg2, 
                   quad[i].result);
        } else {
            printf("%d) %c %s %s %s\n", i, 
                   quad[i].op,
                   quad[i].arg1, 
                   quad[i].arg2, 
                   quad[i].result);
        }
    }
}

void machinecode() {
    printf("\nAssembly code:\n");
    for(int i = 0; i < quad_index; i++) {
        switch(quad[i].op) {
            case '+':
                printf("MOV R0, %s\n", quad[i].arg1);
                printf("MOV R1, %s\n", quad[i].arg2);
                printf("ADD R2, R0, R1\n");
                printf("MOV %s, R2\n", quad[i].result);
                break;
            case '-':
                printf("MOV R0, %s\n", quad[i].arg1);
                printf("MOV R1, %s\n", quad[i].arg2);
                printf("SUB R2, R0, R1\n");
                printf("MOV %s, R2\n", quad[i].result);
                break;
            case '*':
                printf("MOV R0, %s\n", quad[i].arg1);
                printf("MOV R1, %s\n", quad[i].arg2);
                printf("MUL R2, R0, R1\n");
                printf("MOV %s, R2\n", quad[i].result);
                break;
            case '/':
                printf("MOV R0, %s\n", quad[i].arg1);
                printf("MOV R1, %s\n", quad[i].arg2);
                printf("DIV R2, R0, R1\n");
                printf("MOV %s, R2\n", quad[i].result);
                break;
            case '=':
                printf("MOV %s, %s\n", quad[i].result, quad[i].arg1);
                break;
        }
    }
}

void yyerror(char *s) {
    fprintf(stderr, "Error: %s\n", s);
}

int yywrap(void) {
    return 1;
}

int main() {
    printf("Enter an infix expression: ");
    yyparse();
    return 0;
}