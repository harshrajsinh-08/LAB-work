%{
    #include <stdio.h>
    int flag=0;
%}

DOB  16-08-2004

%%
{DOB}   {
    printf("Correct birthdate");
    flag=1;
}

.+    {}

%%
int main(){
    yylex();
    if(flag==0){
        printf("Not my birthday");
    }
    return 0;
}
int yywrap(){
    return 1;
}