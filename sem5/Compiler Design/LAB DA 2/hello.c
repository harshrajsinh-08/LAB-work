#include <stdio.h>
#include <stdbool.h>

bool isPrime(int num) {
    if (num <= 1) {
        return false;}
    for (int i = 2; i * i <= num; i++) {
        if (num % i == 0) {
            return false;
        }
    }
    return true;
}
void primes(int limit) {
    printf("Prime numbers up to %d:\n", limit);
    for (int i = 2; i <= limit; i++) {
        if (isPrime(i)) {
            printf("%d ", i);
        }
    }
    printf("\n");
}
int main() {
    int num;
    printf("Enter a number: ");
    scanf("%d", &num);
    
    primes(num);
    
    return 0;
}