%{
#include <stdio.h>
%}

%%
[0-9]+ {
    int num = atoi(yytext);
    
    // Check if number is even or odd
    if (num % 2 == 0) {
        printf("%d is an even number.\n", num);
    } else {
        printf("%d is an odd number.\n", num);
        
        // Check if odd number is prime
        int isPrime = 1;
        if (num <= 1) {
            isPrime = 0;
        } else {
            for (int i = 2; i * i <= num; i++) {
                if (num % i == 0) {
                    isPrime = 0;
                    break;
                }
            }
        }
        
        if (isPrime) {
            printf("%d is a prime number.\n", num);
        } else {
            printf("%d is not a prime number.\n", num);
        }
    }
}

. ; // Ignore any other input

%%

int main() {
    printf("Enter a number: ");
    yylex();
    return 0;
}

int yywrap() {
    return 1;
}