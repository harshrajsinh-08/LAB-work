email  [a-zA-Z0-9._%+-]+@vit(student)?\.ac\.in
num [6-9]?[0-9]{9}
reg [0-9]{2}(BCE|BDS|BKT|BCB)[0-9]{4}
%%
{email} {printf("Valid mail address");}
{num} {printf("Valid number");}
{reg} {printf("Valid registration number");}
.*       {printf("Invalid"); }
%%
int main()
{
printf("Enter the statement\n");
yylex();
return 0;
}
int yywrap()
{
return 1;
}