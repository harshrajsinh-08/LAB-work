%{
#include <stdio.h>
#include <stdlib.h>

int isLeapYear(int year) {
    return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
}

int validateDate(int day, int month, int year) {
    int daysInMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    if (month < 1 || month > 12) return 0;
    if (month == 2 && isLeapYear(year)) {
        daysInMonth[1] = 29;
    }
    if (day < 1 || day > daysInMonth[month - 1]) return 0;
    return 1;
}
%}

%%
([0-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-([0-9]{4}) {
    int day = atoi(yytext);
    int month = atoi(yytext + 3);
    int year = atoi(yytext + 6);

    if (validateDate(day, month, year)) {
        printf("Valid DOB: %s\n", yytext);
    } else {
        printf("Invalid DOB: %s\n", yytext);
    }
}

[\n]  ; 

.     { printf("Invalid input: %s\n", yytext); } 

%%

int main() {
    printf("Enter dates in DD-MM-YYYY format (Ctrl+D to exit):\n");
    yylex();
    return 0;
}

int yywrap() {
    return 1; 
}