%{
    #include "run.h"
    void yyerror(const char *s);
    int yylex(void);

    Result result;
%}
%union{
    int ival;
    char sval[MAX_LEN];
}

%token <ival> NUMBER
%token <sval> VARIABLE
%token PLUS MINUS MULT DIV LPAREN RPAREN
%type <sval> expr term factor

%%

input: 
    expr{
        printf("Postfix: %s\n", result.postfix); printf("Prefix: %s\n", result.prefix);
    };
expr:
    expr PLUS term{
        sprintf(result.prefix,"%s %s +", $1,$3);
        sprintf(result.prefix,"+ %s %s",$1,$3);
        strcpy($$, result.postfix);
    }
    | expr MINUS term {
        sprintf(result.postfix, "%s %s -", $1, $3);
        sprintf(result.prefix, "- %s %s", $1, $3);
        strcpy($$, result.postfix);  
    }
    | term { strcpy($$, $1); }
    ;

term:
    term MULT factor {
        sprintf(result.postfix, "%s %s *", $1, $3);
        sprintf(result.prefix, "* %s %s", $1, $3);
        strcpy($$, result.postfix);  
    }
    | term DIV factor {
        sprintf(result.postfix, "%s %s /", $1, $3);
        sprintf(result.prefix, "/ %s %s", $1, $3);
        strcpy($$, result.postfix);  
    }
    | factor { strcpy($$, $1); }
    ;

factor:
    NUMBER { 
        sprintf(result.postfix, "%d", $1);
        sprintf(result.prefix, "%d", $1);
        strcpy($$, result.postfix);  
    }
    | VARIABLE { 
        sprintf(result.postfix, "%s", $1);
        sprintf(result.prefix, "%s", $1);
        strcpy($$, result.postfix);  
    }
    | LPAREN expr RPAREN { strcpy($$, $2); }
    ;

%%

void yyerror(const char *s){
    fprintf(stderr,"Error %s\n",s);
}

int main(){
    printf("Enter infix expression: ");
    yyparse();
    return 0;
}